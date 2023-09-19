package com.tipsy.common;


import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lys
 * @Date 2023/9/8
 **/
public class BeanCopyUtil {
    public static <S,T> T copyBean(S source,Class<T> clazz) {
        T result = null;
        try {
            result = clazz.newInstance();
            BeanUtils.copyProperties(source,result);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
    public static <S,T> List<T> copyBeanList(List<S> sourceList, Class<T> clazz) {
      return sourceList.stream().map(o->copyBean(o,clazz)).collect(Collectors.toList());
    }
}
