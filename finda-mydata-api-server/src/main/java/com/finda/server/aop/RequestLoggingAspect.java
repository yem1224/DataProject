package com.finda.server.aop;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;

import lombok.RequiredArgsConstructor;

@Component
@Aspect
@RequiredArgsConstructor
public class RequestLoggingAspect {

	private final Logger logger = LoggerFactory.getLogger("mydata");
	
	private String paramMapToString(Map<String, String[]> paramMap) {
		if (paramMap.isEmpty()) return StringUtils.EMPTY;
		String params = paramMap.entrySet().stream()
				.map(entry -> String.format("{%s=%s}",
						entry.getKey(), Joiner.on(",").join(entry.getValue())))
				.collect(Collectors.joining(", "));
		params = "Param:" + params;
		return params;
	}
	
	@Pointcut("within(com.finda.server.mydata.*.controller..*)")
	public void onRequest() {}
	
	@Pointcut("within(com.finda.server.mydata.*.controller..*)")
	public void onResponse() {}
	
	@Before("com.finda.server.aop.RequestLoggingAspect.onRequest()")
	public void beforeComponentMethod(JoinPoint jp) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		Map<String, String[]> paramMap = request.getParameterMap();
		String params = paramMapToString(paramMap);
		logger.info("[{}] Request: {} {} {} < {}", LocalDateTime.now(), request.getMethod(), request.getRequestURI(),
				request.getRemoteHost(), params);
	}
	
	@AfterReturning(value = "com.finda.server.aop.RequestLoggingAspect.onResponse()", returning = "obj")
	public void afterReturningComponent1Method(JoinPoint jp, ResponseEntity obj) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		Map<String, String[]> paramMap = request.getParameterMap();
		String params = paramMapToString(paramMap);
		
		Map<String, Object> map = (Map<String, Object>) new ObjectMapper().convertValue(obj, Map.class);
		logger.info("[{}] Response: {} {} {} < {} Body:{}", LocalDateTime.now(), request.getMethod(), 
				request.getRemoteHost(), request.getRequestURI(), params, map.toString());
	}
}
