package com.finda.server.mydata.auth.service;

import com.finda.server.mydata.auth.domain.entity.IntegrationAuthProgressEntity;
import com.finda.server.mydata.auth.domain.entity.IntegrationAuthTaskEntity;
import com.finda.server.mydata.auth.domain.entity.MydataAuthBaseEntity;
import com.finda.server.mydata.auth.domain.repository.IntegrationAuthProgressRepository;
import com.finda.server.mydata.auth.dto.AuthProgressDto;
import com.finda.server.mydata.auth.dto.AuthSignatureDto;
import com.finda.server.mydata.auth.dto.SignedSignatureDto;
import com.finda.services.finda.common.exception.EntityNotFoundException;
import com.finda.services.finda.common.exception.EntityNotPermittedException;
import com.finda.services.finda.common.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class IntegrationAuthProgressService {

    private final IntegrationAuthProgressRepository integrationAuthProgressRepository;
    private final MydataAuthBaseManagementService mydataAuthBaseManagementService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Long createProgress(AuthSignatureDto authSignatureDto, String userCi) {
        generateMydataAuthBase(userCi, authSignatureDto);

        List<IntegrationAuthTaskEntity> integrationAuthTaskEntities = new ArrayList<>();
        for (SignedSignatureDto signedSignatureDto : authSignatureDto.getSignedSignatureDtoList()) {
            integrationAuthTaskEntities.add(
                    IntegrationAuthTaskEntity.builder()
                            .orgCode(signedSignatureDto.getOrgCode())
                            .userCi(userCi)
                            .build()
            );
        }
        IntegrationAuthProgressEntity integrationAuthProgressEntity = new IntegrationAuthProgressEntity(userCi);
        integrationAuthProgressEntity.addAllTasks(integrationAuthTaskEntities);

        integrationAuthProgressRepository.save(integrationAuthProgressEntity);
        return integrationAuthProgressEntity.getId();
    }

    private void generateMydataAuthBase(String userCi, AuthSignatureDto authSignatureDto) {
        List<String> orgCodes = authSignatureDto.getSignedSignatureDtoList().stream()
                .map(SignedSignatureDto::getOrgCode)
                .collect(toList());

        mydataAuthBaseManagementService.generate(userCi, orgCodes);
    }

    @Transactional(readOnly = true)
    public AuthProgressDto getTaskStatusByProgressIdAndUserCi(Long progressId, String userCi) {
        IntegrationAuthProgressEntity integrationAuthProgressEntity = integrationAuthProgressRepository.findByIdFetchTaskAndAuthBase(progressId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ServiceException.ErrorType.ClientSystem,
                        "Integration Authentication Progress Check",
                        String.format("ProgressEntity is not exists by [%s]", progressId.toString()),
                        null
                ));

        if (!integrationAuthProgressEntity.getUserCi().equals(userCi)) {
            throw new EntityNotPermittedException("UserCi", String.format("Progress is not Permitted by userCi [%s]", userCi), null);
        }

        List<String> orgCodes = integrationAuthProgressEntity.getIntegrationAuthTaskEntities().stream()
                .map(IntegrationAuthTaskEntity::getOrgCode)
                .collect(toList());

        List<MydataAuthBaseEntity> mydataAuthBaseEntities = mydataAuthBaseManagementService
                .findAllByUserCiAndOrgCodes(userCi, orgCodes);

        return AuthProgressDto.create(progressId, mydataAuthBaseEntities);
    }
}
