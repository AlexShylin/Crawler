SET SCHEMA crawler;

/* drop tables */
drop table if exists scrap_result;
drop table if exists result;

/* creating tables */
CREATE TABLE if not exists result (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    status varchar(20),
    date_launch TIMESTAMP,
    date_finish TIMESTAMP,
    site_url varchar(1536),
    threads INT,
    delay BIGINT
);

CREATE TABLE if not exists scrap_result (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_result BIGINT,
    url varchar(1536),
    sourceurl varchar(1536),
    status varchar(20),
    response_time BIGINT,
    FOREIGN KEY (id_result) REFERENCES result(id)
);