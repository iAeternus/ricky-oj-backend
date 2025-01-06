package org.ricky.common.validation.ip;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/6
 * @className IP
 * @desc
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = IPValidator.class)
@Documented
public @interface IP {

    String message() default "IP format is incorrect.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
