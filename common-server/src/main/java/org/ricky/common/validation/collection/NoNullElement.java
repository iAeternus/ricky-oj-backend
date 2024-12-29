package org.ricky.common.validation.collection;

import jakarta.validation.Constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/9/9
 * @className NoNullElement
 * @desc 列表没有空元素
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NoNullElementValidator.class)
@Documented
public @interface NoNullElement {
}
