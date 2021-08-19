package com.finda.server.aop;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.finda.services.finda.common.util.JwtUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;

@Component
@Aspect
public class RequestTokenAuthenticateAspect {

	@Value("${mydata.mgmt.oauth.secretkey}")
    private String secretKey;
	
	@Value("${mydata.mgmt.oauth.scope}")
	private String scope;
	
	@Before("@annotation(com.finda.server.annotation.custom.RequiredToken)")
	public void beforeComponentMethod(JoinPoint jp) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String token = null;
		try {
			token = request.getHeader("Authorization");
		} catch (Exception e) {
			throw new RuntimeException("invalid_request", e);
		}
		
		if (!StringUtils.isEmpty(token)) {
			authenticate(token);
		} else {
			throw new RuntimeException("invalid_request");
		}
		
	}
	
	private void authenticate(String token) {
		try {
			Claims claims = JwtUtils.getTokenClaims(token, secretKey);
			String reqScope = claims.get("scope").toString();
			if (!this.scope.equals(reqScope)) throw new RuntimeException("invalid_scope");
		} catch (ExpiredJwtException e) {
			throw new RuntimeException("invalid_grant", e);
		} catch (Exception e) {
			throw new RuntimeException("invalid client", e);
		}
	}
}
