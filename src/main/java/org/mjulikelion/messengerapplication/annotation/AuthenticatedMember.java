package org.mjulikelion.messengerapplication.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)          //어노테이션이 사용될 곳
@Retention(RetentionPolicy.RUNTIME)     //어노테이션의 라이프 사이클
public @interface AuthenticatedMember {
}
