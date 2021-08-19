package com.finda.server.mydata.auth.service;

import com.finda.server.mydata.auth.domain.entity.IntegrationAuthNonceEntity;
import com.finda.server.mydata.auth.domain.repository.IntegrationAuthNonceRepository;
import com.finda.server.mydata.auth.dto.ConsentInfo;
import com.finda.server.mydata.auth.dto.TargetInfoDto;
import com.finda.server.mydata.auth.dto.UcpidRequestInfo;
import com.finda.server.mydata.auth.dto.UnsignedSignatureDto;
import com.finda.server.mydata.auth.dto.request.MydataTranReqPtclDto;
import com.finda.server.mydata.mgmts.dto.OrgInfoDto;
import com.finda.server.mydata.mgmts.service.MgmtsQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Component
public class UnsignedSignatureFactory {
    private static final String ASSET_LIST = "자산목록";

    @Value("${mydata.service.domain}")
    private String findaServiceDomain;

    @Value("${mydata.code.findaOrgCode}")
    private String findaOrgCode;

    private final NonceGenerator nonceGenerator;
    private final MgmtsQueryService mgmtsQueryService;
    private final IntegrationAuthNonceRepository integrationAuthNonceRepository;

    @Transactional
    public List<UnsignedSignatureDto> create(String session, String userCi, List<String> orgCode) {
        List<OrgInfoDto> orgInfoDtos = mgmtsQueryService.findAllOrgInfoByOrgCode(orgCode);

        return orgInfoDtos.stream()
                .map(dto -> {
                    IntegrationAuthNonceEntity integrationAuthNonce = generateIntegrationAuthNonceAndSave(session, userCi, dto);

                    UcpidRequestInfo ucpidRequestInfo = createUcpidRequestInfo(integrationAuthNonce.getUcpidNonce());
                    ConsentInfo consentInfo = createConsentInfoForAssetList(dto, integrationAuthNonce.getConsentNonce());

                    return UnsignedSignatureDto.builder()
                            .orgCode(dto.getOrgCode())
                            .ucpidRequestInfo(ucpidRequestInfo)
                            .consentInfo(consentInfo)
                            .build();
                }).collect(toList());
    }

    private IntegrationAuthNonceEntity generateIntegrationAuthNonceAndSave(String session, String userCi, OrgInfoDto dto) {
        String consentNonce = nonceGenerator.apply();
        String ucpidNonce = nonceGenerator.apply();
        IntegrationAuthNonceEntity integrationAuthNonceEntity = IntegrationAuthNonceEntity.builder()
                .session(session)
                .userCi(userCi)
                .orgCode(dto.getOrgCode())
                .consentNonce(consentNonce)
                .ucpidNonce(ucpidNonce)
                .build();

        integrationAuthNonceRepository.save(integrationAuthNonceEntity);
        return integrationAuthNonceEntity;
    }

    private UcpidRequestInfo createUcpidRequestInfo(String ucpidNonce) {
        return new UcpidRequestInfo(
                "본인확인 이용약관: 본인확인 요청에 동의합니다.",
                findaServiceDomain,
                ucpidNonce
        );
    }

    private ConsentInfo createConsentInfoForAssetList(OrgInfoDto orgInfoDto, String consentNonce) {
        return new ConsentInfo(
                MydataTranReqPtclDto.builder()
                        .sndOrgCode(orgInfoDto.getOrgCode())
                        .rcvOrgCode(findaOrgCode)
                        .purpose(ASSET_LIST)
                        .holdingPeriod(LocalDateTime.now().plusDays(7L))
                        .targetInfoDtos(new TargetInfoDto(orgInfoDto.getIndustry() + ".list", new ArrayList<>()))
                        .build(),
                consentNonce
        );
    }
}
