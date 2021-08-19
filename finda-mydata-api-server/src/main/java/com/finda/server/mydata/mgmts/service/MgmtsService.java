package com.finda.server.mydata.mgmts.service;

import com.finda.server.mydata.common.dto.response.ResBodyDto;
import com.finda.server.mydata.mgmts.dto.request.*;
import com.finda.server.mydata.mgmts.dto.response.*;

public interface MgmtsService {
	
	MgmtsAuthResDto getMgmtsAuthToken001();

    MgmtsOrgResDto insertOrgsInfo();

    ResBodyDto sendStatistics(int type);
    
    MgmtsAuthResDto getMgmtsAuthToken(MgmtsAuthReqDto mgmtsAuthReqDto);
    
    MgmtsConsentsResDto getMgmtsConsents(MgmtsConsentsReqDto mgmtsConsentsReqDto);


}
