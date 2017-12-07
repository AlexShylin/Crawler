package com.gd.ashylin.crawler.service.validator;

import com.gd.ashylin.crawler.exception.ValidationException;
import com.gd.ashylin.crawler.service.constant.Field;
import com.gd.ashylin.crawler.service.constant.Sort;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Alexander Shylin
 */
public class ValidatorUtilTest {
    private ValidatorUtilImpl validatorUtilImpl = new ValidatorUtilImpl();

    @Test
    public void testNotNullPositive() {
        validatorUtilImpl.notNull(new Object(), "object");
        Assert.assertTrue(true);
    }

    @Test(expected = ValidationException.class)
    public void testNotNullNegative() {
        validatorUtilImpl.notNull(null, "null");
    }

    @Test
    public void testValidateSearchPositive() {
        validatorUtilImpl.validateSearch("search", "search");
        Assert.assertTrue(true);
    }

    @Test(expected = ValidationException.class)
    public void testValidateSearchNegative() {
        validatorUtilImpl.validateSearch(null, "search");
    }

    @Test
    public void testValidateFieldPositive() {
        validatorUtilImpl.validateField(Field.URL.toString(), "field");
        Assert.assertTrue(true);
    }

    @Test(expected = ValidationException.class)
    public void testValidateFieldNegative() {
        validatorUtilImpl.validateField("", "field");
    }

    @Test(expected = ValidationException.class)
    public void testValidateFieldNegativeNull() {
        validatorUtilImpl.validateField(null, "field");
    }

    @Test
    public void testValidateSortPositive() {
        validatorUtilImpl.validateSort(Sort.ASC.toString(), "sort");
        Assert.assertTrue(true);
    }

    @Test(expected = ValidationException.class)
    public void testValidateSortNegative() {
        validatorUtilImpl.validateSort("", "sort");
        Assert.assertTrue(true);
    }

    @Test(expected = ValidationException.class)
    public void testValidateSortNegativeNull() {
        validatorUtilImpl.validateSort(null, "sort");
        Assert.assertTrue(true);
    }

    @Test
    public void testValidateUrlPositive() {
        validatorUtilImpl.validateUrl("http://", "url");
        Assert.assertTrue(true);
    }

    @Test
    public void testValidateUrlPositiveSecure() {
        validatorUtilImpl.validateUrl("https://", "url");
        Assert.assertTrue(true);
    }

    @Test(expected = ValidationException.class)
    public void testValidateUrlNegative() {
        validatorUtilImpl.validateUrl("ftp://", "url");
    }

    @Test(expected = ValidationException.class)
    public void testValidateUrlNegativeNull() {
        validatorUtilImpl.validateUrl(null, "url");
    }

    @Test
    public void testIsZeroOrHigher() {
        validatorUtilImpl.isZeroOrHigher(0L, "num");
        Assert.assertTrue(true);
    }

    @Test(expected = ValidationException.class)
    public void testIsZeroOrHigherNegative() {
        validatorUtilImpl.isZeroOrHigher(-1L, "num");
    }

    @Test
    public void testValidateNumberPositive() {
        validatorUtilImpl.validateNumber(0L, "num");
        Assert.assertTrue(true);
    }

    @Test(expected = ValidationException.class)
    public void testValidateNumberNegative() {
        validatorUtilImpl.validateNumber(-1L, "num");
    }

    @Test(expected = ValidationException.class)
    public void testValidateNumberNegativeNull() {
        validatorUtilImpl.validateNumber(null, "num");
    }
}
