package com.finda.server.mydata.auth.service.impl;

import com.finda.server.mydata.auth.domain.entity.MydataUserEntity;
import com.finda.server.mydata.auth.domain.repository.IntegrationAuthNonceRepository;
import com.finda.server.mydata.auth.domain.repository.MydataUserRepository;
import com.finda.server.mydata.auth.dto.AuthProgressDto;
import com.finda.server.mydata.auth.dto.AuthSignatureDto;
import com.finda.server.mydata.auth.dto.SignedSignatureDto;
import com.finda.server.mydata.auth.dto.UnsignedSignatureDto;
import com.finda.server.mydata.auth.service.IntegrationAuthProgressService;
import com.finda.server.mydata.auth.service.IntegrationAuthService;
import com.finda.server.mydata.auth.service.IntegrationAuthTransactionExecutor;
import com.finda.server.mydata.auth.service.UnsignedSignatureFactory;
import com.finda.server.mydata.mgmts.domain.repository.MgmtsOrgRepository;
import com.finda.server.mydata.mgmts.dto.OrgInfoDto;
import com.finda.services.finda.common.exception.EntityNotFoundException;
import com.finda.services.finda.common.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class IntegrationAuthServiceImpl implements IntegrationAuthService {

    private final IntegrationAuthProgressService integrationAuthProgressService;
    private final MgmtsOrgRepository mgmtsOrgRepository;
    private final IntegrationAuthTransactionExecutor integrationAuthTransactionExecutor;
    private final UnsignedSignatureFactory unsignedSignatureFactory;
    private final MydataUserRepository mydataUserRepository;

    @Transactional
    @Override
    public List<UnsignedSignatureDto> createUnsignedSignature(String session, Long userId, List<String> orgCodes) {
        MydataUserEntity mydataUser = findMydataUserByUserId(userId);
        return unsignedSignatureFactory.create(session, mydataUser.getUserCi(), orgCodes);
    }

    @Transactional
    @Override
    public AuthProgressDto authenticate(String session, Long userId, AuthSignatureDto authSignatureDto) {
        MydataUserEntity mydataUserEntity = findMydataUserByUserId(userId);
        integrationAuthTransactionExecutor.authSessionCheck(session);

        Long proceedId = integrationAuthProgressService.createProgress(authSignatureDto, mydataUserEntity.getUserCi());

        String caOrgCode = findCaOrgCodeByCertIssuerDn(authSignatureDto.getCaOrg());
        Map<OrgInfoDto, SignedSignatureDto> orgInfoMap = mappingOrgInfoToSignedSignature(authSignatureDto);

        for (Map.Entry<OrgInfoDto, SignedSignatureDto> entry : orgInfoMap.entrySet()) {
            integrationAuthTransactionExecutor.execute(
                    session,
                    mydataUserEntity.getUserCi(),
                    caOrgCode,
                    entry.getKey().getDomain(),
                    entry.getValue(),
                    LocalDateTime.now()
            );
        }

        List<AuthProgressDto.Task> tasks = authSignatureDto.getSignedSignatureDtoList().stream()
                .map(dto -> new AuthProgressDto.Task(dto.getOrgCode(), AuthProgressDto.Status.IN_PROGRESS))
                .collect(toList());

        return new AuthProgressDto(proceedId, tasks);
    }

    private String findCaOrgCodeByCertIssuerDn(String certIssuerDn) {
        return mgmtsOrgRepository.findOrgCodeByCertIssuerDn(certIssuerDn)
                .orElseThrow(() -> new EntityNotFoundException(
                                ServiceException.ErrorType.ClientSystem,
                                "Integration Authentication caOrg",
                                "caOrg is not exists orgCode",
                                null
                        )
                );
    }

    private Map<OrgInfoDto, SignedSignatureDto> mappingOrgInfoToSignedSignature(AuthSignatureDto authSignatureDto) {
        Map<OrgInfoDto, SignedSignatureDto> map = new HashMap<>();
        List<OrgInfoDto> orgInfos = mgmtsOrgRepository.findOrgInfosByOrgCodes(
                authSignatureDto.getSignedSignatureDtoList().stream()
                        .map(SignedSignatureDto::getOrgCode)
                        .collect(toList())
        );
        for (OrgInfoDto orgInfo : orgInfos) {
            for (SignedSignatureDto signedSignatureDto : authSignatureDto.getSignedSignatureDtoList()) {
                if (orgInfo.getOrgCode().equals(signedSignatureDto.getOrgCode())) {
                    map.put(orgInfo, signedSignatureDto);
                    break;
                }
            }
        }

        return map;
    }

    @Transactional(readOnly = true)
    @Override
    public AuthProgressDto getProgress(Long userId, Long progressId) {
        MydataUserEntity mydataUserEntity = findMydataUserByUserId(userId);
        return integrationAuthProgressService
                .getTaskStatusByProgressIdAndUserCi(progressId, mydataUserEntity.getUserCi());


    }

    private MydataUserEntity findMydataUserByUserId(Long userId) {
        return mydataUserRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ServiceException.ErrorType.ClientSystem,
                        "Mydata Integration Authentication",
                        String.format("%d is not registered id", userId),
                        null
                ));
    }
}
