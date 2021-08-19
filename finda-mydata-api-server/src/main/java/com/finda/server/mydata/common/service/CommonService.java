package com.finda.server.mydata.common.service;

import com.finda.server.mydata.common.dto.request.OrgReqDto;
import com.finda.server.mydata.common.dto.response.OrgApiResDto;
import com.finda.server.mydata.common.dto.response.OrgConsentsResDto;

public interface CommonService {

    OrgApiResDto searchApiList(String industry,OrgReqDto orgReqDto);

    OrgConsentsResDto searchConsents(String industry,Long authUserId,OrgReqDto orgReqDto);
}
