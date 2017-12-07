package com.gd.ashylin.crawler.db.dao;

import com.gd.ashylin.crawler.db.dao.mapper.ResultRowMapper;
import com.gd.ashylin.crawler.db.entity.Result;
import com.gd.ashylin.crawler.db.entity.ScrapResult;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author Alexander Shylin
 */
public class H2CrawlerDao implements CrawlerDao {

    private static final String INSERT_SCRAP = "insert into scrap_result (id_result, url, sourceurl, status, response_time) values (?, ?, ?, ?, ?)";

    private static final String SEARCH_SCRAPS = "select * from scrap_result where %s like ? and id_result=?";

    private static final String INSERT_CRAWLER = "insert into result (status, date_launch, date_finish, site_url, threads, delay) values (?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_CRAWLER_STATUS_FINISH = "update result set status=?, date_finish=? where id=?";

    private static final String SELECT_CRAWLER_STATUS = "select result.status from result where result.id=?";

    private static final String SELECT_CRAWLER_BY_ID =
            "select res.*, sr.id, sr.url, sr.sourceurl, sr.status, sr.response_time from result as res\n " +
                    "join scrap_result as sr on res.id=sr.id_result\n" +
                    "where res.id=? order by sr.url %s limit ? offset ?;";

    private JdbcTemplate jdbcTemplate;

    /*
     * Implemented methods
     */

    @Override
    public Result getCrawlerById(long id, int offset, int pageSize, String sort) {
        String query = String.format(SELECT_CRAWLER_BY_ID, sort);
        return jdbcTemplate.queryForObject(query, new Object[]{id, pageSize, offset}, new ResultRowMapper());
    }

    @Override
    public String getCrawlerStatus(long id) {
        return jdbcTemplate.queryForObject(SELECT_CRAWLER_STATUS, new Object[]{id}, String.class);
    }

    @Override
    public long insertCrawler(Result result) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement preparedStatement = con.prepareStatement(INSERT_CRAWLER);
                preparedStatement.setString(1, result.getStatus());
                preparedStatement.setTimestamp(2, result.getTimestampLaunch());
                preparedStatement.setTimestamp(3, result.getTimestampFinish());
                preparedStatement.setString(4, result.getUrl());
                preparedStatement.setInt(5, result.getThreads());
                preparedStatement.setLong(6, result.getDelay());
                return preparedStatement;
            }
        }, keyHolder);

        return (long) keyHolder.getKey();
    }

    @Override
    public void changeCrawlerStatusDateFinish(long id, String status, Timestamp finish) {
        jdbcTemplate.update(UPDATE_CRAWLER_STATUS_FINISH, status, finish, id);
    }

    @Override
    public List<ScrapResult> getScrapResult(long crawlerId, String search, String field) {
        String query = String.format(SEARCH_SCRAPS, field);
        return jdbcTemplate.query(query, new Object[] {"%" + search + "%", crawlerId}, new BeanPropertyRowMapper<>(ScrapResult.class));
    }

    @Override
    public void insertScraps(List<ScrapResult> scrapResults, long id) {
        jdbcTemplate.batchUpdate(INSERT_SCRAP, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ScrapResult scrapResult = scrapResults.get(i);
                ps.setLong(1, id);
                ps.setString(2, scrapResult.getUrl());
                ps.setString(3, scrapResult.getSourceurl());
                ps.setString(4, scrapResult.getStatus());
                ps.setLong(5, scrapResult.getResponseTime());
            }

            @Override
            public int getBatchSize() {
                return scrapResults.size();
            }
        });
    }

    public void setDataSource(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }
}
