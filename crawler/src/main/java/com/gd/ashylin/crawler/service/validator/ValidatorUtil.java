package com.gd.ashylin.crawler.service.validator;

/**
 * @author Alexander Shylin
 */
public interface ValidatorUtil {
    void validateNumber(Long num, String nameParam);

    void isZeroOrHigher(Long num, String nameParam);

    void validateUrl(String url, String nameParam);

    void validateSort(String sort, String nameParam);

    void validateField(String field, String nameParam);

    void validateSearch(String search, String nameParam);

    void notNull(Object object, String name);
}
