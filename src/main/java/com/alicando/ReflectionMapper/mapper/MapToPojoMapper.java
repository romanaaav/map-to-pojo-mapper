package com.alicando.ReflectionMapper.mapper;

import com.alicando.ReflectionMapper.annotation.ResultMapping;
import com.alicando.ReflectionMapper.exception.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component
public class MapToPojoMapper {

    Logger logger = LoggerFactory.getLogger(MapToPojoMapper.class);

    public <T> T map(Map<String, Object> sourceMap, Class<T> objectClass) throws MappingException {
        return map(sourceMap,objectClass,"");
    }

    private <T> T map(Map<String, Object> sourceMap, Class<T> objectClass, String prefix) throws MappingException {
        try {
            T returnValue = objectClass.getDeclaredConstructor().newInstance();
            Field[] fields = objectClass.getDeclaredFields();
            for (Field field: fields) {
                if (field.isAnnotationPresent(ResultMapping.class)) {
                    populateField(field,returnValue,sourceMap, prefix);
                }
            }
            return returnValue;
        } catch (Exception e) {
            logger.error("An error has occurred while trying to map the class " + objectClass.getName(), e);
            throw new MappingException("An error has occurred while trying to map the class " + objectClass.getName(),e);
        }
    }

    private <T> void populateField(Field field, T objectToPopulate, Map <String,Object> sourceMap, String prefix) throws IllegalAccessException, MappingException {
        ResultMapping resultMappingAnnotation = field.getAnnotation(ResultMapping.class);
        Object mapValue = sourceMap.get(resultMappingAnnotation.alias());
        Class<?> type = field.getType();
        if (Collection.class.isAssignableFrom(mapValue.getClass()))
            populateFieldWithCollectionValue(field, objectToPopulate, resultMappingAnnotation, (Collection<Map<String, Object>>) mapValue);
        else
            populateFieldWithSingleValue(field, objectToPopulate, sourceMap, type, resultMappingAnnotation, mapValue.toString());
    }

    private <T> void populateFieldWithCollectionValue(Field field, T objectToPopulate, ResultMapping resultMappingAnnotation, Collection<Map<String,Object>> mapValue) throws MappingException, IllegalAccessException {
        ParameterizedType listType = (ParameterizedType) field.getGenericType();
        Class<?> listClass = (Class<?>) listType.getActualTypeArguments()[0];

        List<Object> mappedListElements = new ArrayList<>();

        field.setAccessible(true);
        try {
            for (Map<String,Object> internalSourceMap: mapValue) {
                Object mappedInstance = map(internalSourceMap,listClass, resultMappingAnnotation.alias());
                mappedListElements.add(mappedInstance);
            }
            field.set(objectToPopulate,mappedListElements);
        } finally {
            field.setAccessible(false);
        }
    }

    private static <T> void populateFieldWithSingleValue(Field field, T objectToPopulate, Map<String, Object> sourceMap, Class<?> type, ResultMapping resultMappingAnnotation, String mapValue) throws IllegalAccessException {
        field.setAccessible(true);
        try {
            if (type.equals(String.class)) {
                field.set(objectToPopulate, sourceMap.get(resultMappingAnnotation.alias()));
            }

            if (type.isAssignableFrom(Integer.class)) {
                field.set(objectToPopulate, Integer.parseInt(mapValue));
            }
        } finally {
            field.setAccessible(false);
        }
    }

}
