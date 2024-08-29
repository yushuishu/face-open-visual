package com.shuishu.face.openvisual.server.entity.base;

import org.springframework.beans.BeanUtils;

/**
 * @Author ：谁书-ss
 * @Date   ： 2024-08-27 16:44
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
public class BaseDto<T extends BasePo> {
    /**
     * Dto转化为Po，进行后续业务处理
     *
     * @param clazz -
     * @return Po
     */
    public T toPo(Class<T> clazz) {
        T t = BeanUtils.instantiateClass(clazz);
        BeanUtils.copyProperties(this, t);
        return t;
    }
}
