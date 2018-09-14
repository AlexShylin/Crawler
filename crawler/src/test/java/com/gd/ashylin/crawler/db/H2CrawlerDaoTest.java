package com.gd.ashylin.crawler.db;

import com.gd.ashylin.crawler.db.dao.H2CrawlerDao;
import com.gd.ashylin.crawler.db.entity.Result;
import com.gd.ashylin.crawler.db.entity.ScrapResult;
import com.gd.ashylin.crawler.service.constant.Field;
import com.gd.ashylin.crawler.service.constant.Sort;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * @author Alexander Shylin
 */
public class H2CrawlerDaoTest {

    private H2CrawlerDao crawlerDao = new H2CrawlerDao();
    private BasicDataSource dataSource = new BasicDataSource();
    private JdbcTemplate jdbcTemplate;

    /**
     * Before starting tests get data from .properties file with
     * contains datasource information. Connection string has directives
     * to run 'test-create-db.sql' and 'test-insert-data.sql' scripts in RAM.
     * <p>
     * 'test-create-db.sql' - creates schema and tables
     * 'test-insert-data.sql' - inserts test data
     *
     * @throws IOException
     */
    @Before
    @SuppressWarnings("Duplicates")
    public void init() throws IOException {
        Properties properties = new Properties();

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("h2-test-connect-data.properties").getFile());
        properties.load(new FileInputStream(file));

        dataSource.setUrl(properties.getProperty("connection"));
        dataSource.setUsername(properties.getProperty("user"));
        dataSource.setPassword(properties.getProperty("password"));
        dataSource.setDriverClassName(properties.getProperty("driver"));

        jdbcTemplate = new JdbcTemplate(dataSource);
        crawlerDao.setDataSource(dataSource);
    }

    @Test
    public void insertCrawlerTest() {
        long insertedId = crawlerDao.insertCrawler(new Result("url", 1, 20));
        Assert.assertEquals("Assert inserted crawler index", 3L, insertedId);
    }

    @Test
    public void insertScrapsTestTest() throws SQLException {
        List<ScrapResult> scrapResults = new ArrayList<>();
        scrapResults.add(new ScrapResult(1, "site", "source", "200", 0));
        scrapResults.add(new ScrapResult(1, "site2", "source2", "404", 0));
        crawlerDao.insertScraps(scrapResults, 1);

        String query = "select id_result, url, sourceurl, status from scrap_result where id_result = 1";

        List<ScrapResult> rows = jdbcTemplate.query(query, new BeanPropertyRowMapper<>(ScrapResult.class));

//        Assert.assertEquals(1L, rows.get(2).getId());
        Assert.assertEquals("site", rows.get(2).getUrl());
        Assert.assertEquals("source", rows.get(2).getSourceurl());
        Assert.assertEquals("200", rows.get(2).getStatus());

//        Assert.assertEquals(1L, rows.get(3).getId());
        Assert.assertEquals("site2", rows.get(3).getUrl());
        Assert.assertEquals("source2", rows.get(3).getSourceurl());
        Assert.assertEquals("404", rows.get(3).getStatus());
    }

    @Test
    public void selectCrawlerByIdTest() {
        Result result = crawlerDao.getCrawlerById(2L, 1, 1, Sort.ASC.toString());
        Assert.assertEquals("https://ya.ru/search2", result.getResults().get(0).getUrl());
    }

    @Test
    public void selectCrawlerStatusTest() {
        String status = crawlerDao.getCrawlerStatus(1L);
        Assert.assertEquals(Result.STATUS_FINISHED, status);
    }

    @Test
    public void selectScrapsResultsTest() {
        List<ScrapResult> scrapResults = crawlerDao.getScrapResult(2L, "te", Field.URL.toString());
        Assert.assertEquals("https://ya.ru/test", scrapResults.get(0).getUrl());
    }

    @Test
    public void updateCrawlersStatusDateFinishTest() {
        crawlerDao.changeCrawlerStatusDateFinish(2L, Result.STATUS_FINISHED, new Timestamp(new Date().getTime()));
        Result result = crawlerDao.getCrawlerById(2L, 0, 1, Sort.ASC.toString());
        Assert.assertEquals(Result.STATUS_FINISHED, result.getStatus());
    }
}
