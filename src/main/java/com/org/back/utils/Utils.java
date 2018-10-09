package com.org.back.utils;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
 
 
public class Utils {

	public static <T> T convertValue(Map<String, Object> map, Class<T> type) {
		final ObjectMapper mapper = new ObjectMapper();
		return mapper.convertValue(map, type);
	}

	public static String writeValueAsString(Object object) {
		final ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Map<String, Object> writeValueAsMap(Object object) {
		final ObjectMapper mapper = new ObjectMapper();
		return mapper.convertValue(object, new TypeReference<Map<String, Object>>() {
		});
	}

 

	public static HashMap<String, Object> addFilter(HashMap<String, Object> oldFilter,
			HashMap<String, Object> requestFilter) {
		HashMap<String, Object> mx = new HashMap<>(oldFilter);
		mx.putAll(requestFilter);
		return mx;
	}

 
	 
	
	public static boolean isNumeric(String str) {
		NumberFormat formatter = NumberFormat.getInstance();
		ParsePosition pos = new ParsePosition(0);
		formatter.parse(str, pos);
		return str.length() == pos.getIndex();
	} 
	
}
