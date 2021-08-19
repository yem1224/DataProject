package com.finda.server.mydata.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TargetInfoDto {

    private String scope;   // 전송요구 정보에 해당하는 scope

    @JsonProperty("asset_list")
    private List<AssetDto> assetList = new ArrayList<>();   // 전송요구 대상 계좌(상품) 식별자 목록

    @Getter
    @NoArgsConstructor
    public static class AssetDto {
        private String asset;
    }
}
