package com.gd.ashylin.crawler.service.validator;

import com.gd.ashylin.crawler.exception.ValidationException;
import com.gd.ashylin.crawler.exception.ErrorNamesCodes;
import com.gd.ashylin.crawler.service.constant.Field;
import com.gd.ashylin.crawler.service.constant.Sort;

/**
 * @author Alexander Shylin
 */
public class ValidatorUtilImpl implements ValidatorUtil {
    private static final String MESSAGE_PARAM_IS_NULL = "Parameter '%s' is missing or null";
    private static final String MESSAGE_PARAM_VAL_IS_WRONG = "Parameter '%s' can't be %s";

    public void validateNumber(Long num, String nameParam) {
        notNull(num, nameParam);
        isZeroOrHigher(num, nameParam);
    }

    public void isZeroOrHigher(Long num, String nameParam) {
        if (num < 0) {
            throwWrongValue(num, nameParam);
        }
    }

    public void validateUrl(String url, String nameParam) {
        notNull(url, nameParam);
        if (!(url.startsWith("http://") || url.startsWith("https://"))) {
            throwWrongValue(url, nameParam);
        }
    }

    public void validateSort(String sort, String nameParam) {
        notNull(sort, nameParam);

        Sort[] values = Sort.values();
        for (Sort type : values) {
            if (sort.equals(type.toString())) {
                return;
            }
        }
        throwWrongValue(sort, nameParam);
    }

    public void validateField(String field, String nameParam) {
        notNull(field, nameParam);

        Field[] values = Field.values();
        for (Field type : values) {
            if (field.equals(type.toString())) {
                return;
            }
        }
        throwWrongValue(field, nameParam);
    }

    public void validateSearch(String search, String nameParam) {
        notNull(search, nameParam);
    }

    public void notNull(Object object, String name) {
        if (object == null) {
            throw new ValidationException(ErrorNamesCodes.WRONG_PARAMETERS_NAME, ErrorNamesCodes.WRONG_PARAMETERS_CODE, String.format(MESSAGE_PARAM_IS_NULL, name));
        }
    }

    private void throwWrongValue(Object num, String nameParam) {
        throw new ValidationException(ErrorNamesCodes.WRONG_PARAMETERS_NAME, ErrorNamesCodes.WRONG_PARAMETERS_CODE, String.format(MESSAGE_PARAM_VAL_IS_WRONG, nameParam, num));
    }
}
