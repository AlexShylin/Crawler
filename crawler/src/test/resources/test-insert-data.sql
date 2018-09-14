SET SCHEMA crawler;

/* inserting test values */

SET AUTOCOMMIT FALSE;
PREPARE COMMIT inserting;

insert into result (status, date_launch, date_finish, site_url, threads, delay)
    values ('finished', '2017-03-28 17:46:00.000', '2017-03-28 17:49:00.000', 'https://google.com', 1, 50);
insert into result (status, date_launch, date_finish, site_url, threads, delay)
    values ('canceled', '2017-03-28 18:26:00.000', '2017-03-28 18:41:00.000', 'https://ya.ru', 1, 50);

insert into scrap_result (id_result, url, status, sourceurl, response_time)
    values (1, 'https://google.com/search', '200', 'https://google.com', 1000);
insert into scrap_result (id_result, url, status, sourceurl, response_time)
    values (1, 'https://google.com/mirror', '404', 'https://google.com', 135);
insert into scrap_result (id_result, url, status, sourceurl, response_time)
    values (2, 'https://ya.ru/search', '200', 'https://ya.ru', 645);
insert into scrap_result (id_result, url, status, sourceurl, response_time)
    values (2, 'https://ya.ru/test', '403', 'https://ya.ru', 25);
insert into scrap_result (id_result, url, status, sourceurl, response_time)
    values (2, 'https://ya.ru/search2', '404', 'https://ya.ru', 52);


COMMIT TRANSACTION inserting;
SET AUTOCOMMIT TRUE;
