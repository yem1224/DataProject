package com.finda.server.annotation.custom;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RequestParamToDtoResolver implements HandlerMethodArgumentResolver {
	@Autowired
    private ObjectMapper mapper;

    @Override
    public boolean supportsParameter(final MethodParameter methodParameter) {
        return methodParameter.getParameterAnnotation(RequestParamToDto.class) != null;
    }

    @Override
    public Object resolveArgument(final MethodParameter methodParameter,
                                  final ModelAndViewContainer modelAndViewContainer,
                                  final NativeWebRequest nativeWebRequest,
                                  final WebDataBinderFactory webDataBinderFactory) throws Exception {

        final HttpServletRequest request = (HttpServletRequest) nativeWebRequest.getNativeRequest();
        final String json = this.queryStringToJson(request.getQueryString());
        final Object object = this.mapper.readValue(json, methodParameter.getParameterType());
        return object;
    }

    private String queryStringToJson(String a) {
    	final int length = a.length();
    	StringBuilder sb = new StringBuilder();
    	sb.append("{\"");
        for (int i = 0; i < length; i++) {
            if (a.charAt(i) == '=') {
            	sb.append("\"" + ":" + "\"");
            } else if (a.charAt(i) == '&') {
            	sb.append("\"" + "," + "\"");
            } else {
            	sb.append(a.charAt(i));
            }
        }
        sb.append("\"" + "}");
        return sb.toString();
    }
	
}
