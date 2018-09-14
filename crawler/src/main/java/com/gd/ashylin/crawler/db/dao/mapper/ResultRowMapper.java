package com.gd.ashylin.crawler.db.dao.mapper;

import com.gd.ashylin.crawler.db.entity.Result;
import com.gd.ashylin.crawler.db.entity.ScrapResult;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * @author Alexander Shylin
 */
public class ResultRowMapper implements RowMapper<Result> {
    @Override
    public Result mapRow(ResultSet rs, int rowNum) throws SQLException {
        Result result;

        long id = rs.getLong(1);
        String status = rs.getString(2);
        Timestamp dateLaunch = rs.getTimestamp(3);
        Timestamp dateFinish = rs.getTimestamp(4);
        String url = rs.getString(5);
        int threads = rs.getInt(6);
        long delay = rs.getLong(7);
        result = new Result(id, status, dateLaunch, dateFinish, url, threads, delay);

        do {
            long scrapId = rs.getLong(8);
            url = rs.getString(9);
            String source = rs.getString(10);
            status = rs.getString(11);
            long responseTime = rs.getLong(12);
            result.add(new ScrapResult(scrapId, url, source, status, responseTime));
        } while (rs.next());


        return result;
    }
}
