package org.ricky.common.validation.number;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.ricky.common.exception.MyException;

import static org.ricky.common.exception.ErrorCodeEnum.INVALID_EPS;
import static org.ricky.common.utils.ValidationUtils.isNull;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/26
 * @className FloatNumberValidator
 * @desc
 */
public class FloatNumberValidator implements ConstraintValidator<FloatNumber, Double> {

    private double min;
    private double max;
    private double eps;

    @Override
    public void initialize(FloatNumber floatNumber) {
        this.min = floatNumber.min();
        this.max = floatNumber.max();
        this.eps = floatNumber.eps();
    }

    @Override
    public boolean isValid(Double num, ConstraintValidatorContext constraintValidatorContext) {
        if (isNull(num)) {
            return true;
        }

        return withinRangeInEPS(num, min, max, eps);
    }

    public static boolean withinRangeInEPS(double num, double min, double max, double eps) {
        if (eps <= 0) {
            throw new MyException(INVALID_EPS, "The error threshold must be greater than 0", "eps", eps);
        }
        return num >= (min - eps) && num <= (max + eps);
    }

}
