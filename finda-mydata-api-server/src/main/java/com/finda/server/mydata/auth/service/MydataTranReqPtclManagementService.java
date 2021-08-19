package com.finda.server.mydata.auth.service;

public interface MydataTranReqPtclManagementService {
    void requestAndSave(String userCi, String orgCode, String accessToken);
    void delete(String userCi, String orgCode);
}
