package com.shenxin.core.control.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.shenxin.core.control.Enum.FiledAlias;
import com.shenxin.core.control.sysexception.ValidatroException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.reflect.FieldUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class BeanUtils {

	private static ObjectMapper mapper = new ObjectMapper();

	static {
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	}

	public static <T> T json2Object(String json, Class<T> c) {
		try {
			return mapper.readValue(json, c);
		} catch (IOException e) {
			log.error("json 转换 {} 异常",c.getName(), e);
		}
		return null;
	}

	public static String object2Json(Object o){
		try {
			return mapper.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			log.error("object:[{}] 转换 string 异常",o, e);
		}
		return null;
	}

	public static void validateJSR303(Validator validator, Object o) throws ValidatroException{
		BindingResult result = new BeanPropertyBindingResult(o, o.getClass().getName());
		validator.validate(o, result);
		List<ObjectError> allErrors = result.getAllErrors();
		if (allErrors != null && allErrors.size() > 0) {
			String errorMessage = "";
			for (ObjectError error : allErrors) {
				errorMessage += "【" + error.getDefaultMessage() + "】";
			}
			throw new ValidatroException(errorMessage);
		}
	}

	public static void copy(Object src, Object desc) throws Exception {
		PropertyDescriptor descriptors[] = PropertyUtils.getPropertyDescriptors(desc);
		for (int i = 0; i < descriptors.length; i++) {
			String name = descriptors[i].getName();
			if ("class".equals(name)) {
				continue;
			}
			String srcName = name;
			try {
				Field descField = FieldUtils.getDeclaredField(desc.getClass(), name, true);
				if (descField.isAnnotationPresent(FiledAlias.class)) {
					srcName = descField.getAnnotation(FiledAlias.class).value();
				}
			}catch (Exception e){}
			if (PropertyUtils.isReadable(src, srcName) && PropertyUtils.isWriteable(desc, name)) {
				try {
					Object value = PropertyUtils.getSimpleProperty(src, srcName);
					if (value != null) {
						org.apache.commons.beanutils.BeanUtils.copyProperty(desc, name, value);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}


	public static <T> List copyList(List src, Class<T> c) {
		List<T> desc = new ArrayList<>();
		src.stream().forEach(o -> {
			try {
				T t = c.newInstance();
				copy(o, t);
				desc.add(t);
			}catch(Exception e){
				e.printStackTrace();
			}
		});
		return desc;
	}

	public static Map<String, Object> beanFiledValue(Object src) throws Exception {
		Map<String, Object> fieldValue = new HashMap<>();
		PropertyDescriptor origDescriptors[] = PropertyUtils.getPropertyDescriptors(src);
		for (int i = 0; i < origDescriptors.length; i++) {
			String name = origDescriptors[i].getName();
			if ("class".equals(name)) {
				continue;
			}
			try {
				if(FieldUtils.getField(src.getClass(),name).isAnnotationPresent(NotSearchField.class)){
					continue;
				}
				Object value = PropertyUtils.getSimpleProperty(src, name);
				if (value != null) {
					fieldValue.put(name, value);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return fieldValue;
	}

	public static boolean filedNotNull(Object o) {
		if (o == null) {
			return false;
		} else if (o instanceof String && "".equals(((String) o).trim())) {
			return false;
		}
		return true;
	}
	public static boolean filedNull(Object o) {
		return !filedNotNull(0);
	}
}
