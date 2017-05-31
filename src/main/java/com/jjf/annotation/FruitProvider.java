package com.jjf.annotation;

import java.lang.annotation.*;

/**
 * Created by jjf_lenovo on 2017/5/30.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FruitProvider {
    /**
     * ��Ӧ�̱��
     * @return
     */
    public int id() default -1;

    /**
     * ��Ӧ������
     * @return
     */
    public String name() default "";

    /**
     * ��Ӧ�̵�ַ
     * @return
     */
    public String address() default "";
}
