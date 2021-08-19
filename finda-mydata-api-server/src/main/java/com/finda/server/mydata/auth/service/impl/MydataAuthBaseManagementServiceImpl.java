package com.finda.server.mydata.auth.service.impl;

import com.finda.server.mydata.auth.domain.entity.AuthDv;
import com.finda.server.mydata.auth.domain.entity.MydataAuthBaseEntity;
import com.finda.server.mydata.auth.domain.entity.id.MydataAuthBaseId;
import com.finda.server.mydata.auth.domain.repository.MydataAuthBaseRepository;
import com.finda.server.mydata.auth.domain.repository.MydataAuthHistRepository;
import com.finda.server.mydata.auth.dto.response.AuthRenewResDto;
import com.finda.server.mydata.auth.dto.response.AuthTokenDto;
import com.finda.server.mydata.auth.service.MydataAuthBaseManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class MydataAuthBaseManagementServiceImpl implements MydataAuthBaseManagementService {
    private final MydataAuthBaseRepository mydataAuthBaseRepository;
    private final MydataAuthHistRepository mydataAuthHistRepository;

    @Override
    public Optional<MydataAuthBaseEntity> findByUserIdAndOrgCode(String userCi, String orgCode) {
        return mydataAuthBaseRepository.findByUserCiAndOrgCode(userCi, orgCode);
    }

    @Transactional(readOnly = true)
    @Override
    public List<MydataAuthBaseEntity> findAllByUserCiAndOrgCodes(String userCi, List<String> orgCodes) {
        return mydataAuthBaseRepository.findAllByUserCiAndOrgCodes(userCi, orgCodes);
    }

    @Transactional
    @Override
    public void generate(String userCi, String orgCode) {
        MydataAuthBaseEntity mydataAuthBaseEntity = mydataAuthBaseRepository.findById(MydataAuthBaseId.create(userCi, orgCode))
                .orElseGet(() -> MydataAuthBaseEntity.builder()
                        .orgCode(orgCode)
                        .userCi(userCi)
                        .authStatus(MydataAuthBaseEntity.AuthStatus.AUTH00)
                        .build());
        mydataAuthBaseRepository.save(mydataAuthBaseEntity);
    }

    @Transactional
    @Override
    public void generate(String userCi, List<String> orgCodes) {
        List<MydataAuthBaseId> mydataAuthBaseIds = createMydataAuthBaseIds(userCi, orgCodes);
        List<MydataAuthBaseEntity> mydataAuthBaseEntities = mydataAuthBaseRepository.findAllById(mydataAuthBaseIds);
        List<MydataAuthBaseEntity> notExistsAuthBaseEntities =
                createNotExistsAuthBaseEntities(userCi, orgCodes, mydataAuthBaseEntities);
        mydataAuthBaseEntities.forEach(e -> e.updateAuthStatus(MydataAuthBaseEntity.AuthStatus.AUTH00));

        mydataAuthBaseRepository.saveAll(mydataAuthBaseEntities);
        mydataAuthBaseRepository.saveAll(notExistsAuthBaseEntities);
    }

    private List<MydataAuthBaseId> createMydataAuthBaseIds(String userCi, List<String> orgCodes) {
        return orgCodes.stream()
                .map(orgCode -> MydataAuthBaseId.create(orgCode, userCi))
                .collect(toList());
    }

    private List<MydataAuthBaseEntity> createNotExistsAuthBaseEntities(String userCi, List<String> orgCodes, List<MydataAuthBaseEntity> mydataAuthBaseEntities) {
        List<String> notExistsAuthBaseOrgCodes = findNotExistsAuthBaseOrgCodes(orgCodes, mydataAuthBaseEntities);
        return notExistsAuthBaseOrgCodes.stream()
                .map(orgCode -> MydataAuthBaseEntity.builder()
                        .orgCode(orgCode)
                        .userCi(userCi)
                        .authStatus(MydataAuthBaseEntity.AuthStatus.AUTH00)
                        .build())
                .collect(toList());
    }

    private List<String> findNotExistsAuthBaseOrgCodes(List<String> orgCodes, List<MydataAuthBaseEntity> mydataAuthBaseEntities) {
        return orgCodes.stream()
                .filter(orgCode -> mydataAuthBaseEntities.stream()
                        .noneMatch(entity -> entity.getOrgCode().equals(orgCode))
                ).collect(toList());
    }

    @Transactional
    public void issueToken(String userCi, String orgCode, AuthTokenDto authTokenDto, LocalDateTime authTime, String caOrgCode) {
        MydataAuthBaseEntity mydataAuthBaseEntity = mydataAuthBaseRepository.findById(MydataAuthBaseId.create(orgCode, userCi))
                .orElseThrow(() -> new EntityNotFoundException(""));

        mydataAuthBaseEntity.registerToken(
                authTokenDto.getRefreshToken(),
                authTokenDto.getAccessToken(),
                AuthDv.INTEGRATION,
                caOrgCode,
                authTime,
                authTokenDto.getScope(),
                authTime.plusSeconds(authTokenDto.getExpiresIn()),
                authTime.plusSeconds(authTokenDto.getRefreshTokenExpiresIn()),
                MydataAuthBaseEntity.AuthStatus.AUTH01
        );

        mydataAuthBaseRepository.save(mydataAuthBaseEntity);
        mydataAuthHistRepository.save(mydataAuthBaseEntity.toMydataAuthHist());
    }

    @Transactional
    @Override
    public void failedIssueToken(String userCi, String orgCode) {
        mydataAuthBaseRepository.findById(MydataAuthBaseId.create(userCi, orgCode))
                .ifPresent(entity -> {
                    entity.updateAuthStatus(MydataAuthBaseEntity.AuthStatus.AUTH98);
                    mydataAuthBaseRepository.save(entity);
                });
    }

    @Transactional
    public void deleteByAccessToken(String accessToken) {
        Optional<MydataAuthBaseEntity> result = mydataAuthBaseRepository.findByAccessToken(accessToken);
        if (!result.isPresent()) {
            throw new EntityNotFoundException("AccessToken is not exists.");
        }

        MydataAuthBaseEntity mydataAuthBaseEntity = result.get();
        mydataAuthBaseRepository.delete(mydataAuthBaseEntity);
    }

    @Transactional
    public void deleteByRefreshToken(String refreshToken) {
        Optional<MydataAuthBaseEntity> result = mydataAuthBaseRepository.findByRefreshToken(refreshToken);
        if (!result.isPresent()) {
            throw new EntityNotFoundException("RefreshToken is not exists.");
        }

        MydataAuthBaseEntity mydataAuthBaseEntity = result.get();
        mydataAuthBaseRepository.delete(mydataAuthBaseEntity);
    }

    @Transactional
    @Override
    public void renew(MydataAuthBaseEntity mydataAuthBaseEntity, AuthRenewResDto authRenewResDto) {
        mydataAuthBaseEntity.renewAccessToken(
                authRenewResDto.getAccessToken(),
                LocalDateTime.now().plusSeconds(authRenewResDto.getExpiresIn())
        );

        mydataAuthBaseRepository.save(mydataAuthBaseEntity);
    }
}
