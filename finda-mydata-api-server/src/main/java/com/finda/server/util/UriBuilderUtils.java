package com.finda.server.util;

import java.net.URI;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UriBuilderUtils {

	public static URI getUri(String url, Object o) {
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);
		
		Map<String, Object> map = (Map<String, Object>) new ObjectMapper().convertValue(o, Map.class);
    	if (map != null && !map.isEmpty()) {
			for (Entry<String, Object> entry : map.entrySet()) {
				final String key = entry.getKey();
				final Object value = entry.getValue();
				if (value != null) 
					uriBuilder.queryParam(key, value);
			}
		}
    	
    	return uriBuilder.build().encode().toUri();
	}
}
