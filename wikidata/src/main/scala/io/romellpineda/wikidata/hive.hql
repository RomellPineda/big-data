-- 1. Which English wikipedia article got the most traffic on January 20, 2021?

CREATE DATABASE wiki_data;

USE wiki_data;
CREATE TABLE pageview (
  domain_code STRING,
  page_title STRING,
  count_views INT,
  total_response_size INT
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ' '
-- TBLPROPERTIES("skip.header.line.count"="1");
;

LOAD DATA LOCAL INPATH '/Users/roml/WorkSpace/Rev/pageviews-20210120-000000' INTO TABLE pageview;
LOAD DATA LOCAL INPATH '/Users/roml/WorkSpace/Rev/pageviews-20210120-????' INTO TABLE pageview;
LOAD DATA LOCAL INPATH '/Users/roml/WorkSpace/Rev/pageviews/pageviews-20210120-120000' INTO TABLE pageview;
LOAD DATA LOCAL INPATH '/Users/roml/WorkSpace/Rev/pageviews/pageviews-20210120-180000' INTO TABLE pageview;
LOAD DATA LOCAL INPATH '/Users/roml/WorkSpace/Rev/pageviews/pageviews-20210120-190000' INTO TABLE pageview;
LOAD DATA LOCAL INPATH '/Users/roml/WorkSpace/Rev/pageviews/pageviews-20210120-220000' INTO TABLE pageview;

INSERT OVERWRITE DIRECTORY '/user/hive/q1'
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '\t'
SELECT page_title, SUM(count_views) AS total FROM pageview WHERE domain_code = 'en' OR domain_code = 'en.m' GROUP BY page_title ORDER BY total DESC LIMIT 5;



-- 2. What English wikipedia article has the largest fraction of its readers follow an internal link to another wikipedia article?

CREATE TABLE stream (
  referrer STRING,
  resource STRING,
  type STRING,
  occurrences INT
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '\t';

LOAD DATA LOCAL INPATH '/Users/roml/WorkSpace/Rev/clickstream/clickstream-enwiki-2020-04.tsv' INTO TABLE stream;
LOAD DATA LOCAL INPATH '/Users/roml/WorkSpace/Rev/clickstream/clickstream-enwiki-2020-06.tsv' INTO TABLE stream;
LOAD DATA LOCAL INPATH '/Users/roml/WorkSpace/Rev/clickstream/clickstream-enwiki-2020-10.tsv' INTO TABLE stream;
LOAD DATA LOCAL INPATH '/Users/roml/WorkSpace/Rev/clickstream/clickstream-enwiki-2020-12.tsv' INTO TABLE stream;

LOAD DATA LOCAL INPATH '/Users/roml/WorkSpace/Rev/pageviews/pageviews-20201205-190000' INTO TABLE pageview;

-- n: the number of occurrences of the (referrer, resource) pair

-- numerator
SELECT referrer, SUM(occurrences) AS outbound_traffic FROM stream WHERE referrer = 'Joe_Exotic' GROUP BY referrer;
SELECT referrer, SUM(occurrences) AS outbound_traffic FROM stream WHERE referrer = 'Carole_Baskin' GROUP BY referrer;
SELECT referrer, SUM(occurrences) AS outbound_traffic FROM stream WHERE referrer = 'Borat_Subsequent_Moviefilm' GROUP BY referrer;

-- denominator
SELECT page_title, SUM(count_views) AS total FROM pageview WHERE page_title = 'Joe_Exotic' GROUP BY page_title;
SELECT page_title, SUM(count_views) AS total FROM pageview WHERE page_title = 'Carole_Baskin' GROUP BY page_title;
SELECT page_title, SUM(count_views) AS total FROM pageview WHERE page_title = 'Borat_Subsequent_Moviefilm' GROUP BY page_title;



-- 3. What series of wikipedia articles, starting with Hotel California, keeps the largest fraction of its readers clicking on internal links? This is similar to (2),
-- but you should continue the analysis past the first article. There are multiple ways you can count this fraction,
-- be careful to be clear about the method you find most appropriate.

SELECT * FROM stream WHERE referrer = 'Hotel_California' ORDER BY occurrences DESC LIMIT 100;
SELECT resource, SUM(occurrences) AS totes FROM stream WHERE referrer = 'Hotel_California' GROUP BY resource ORDER BY totes DESC LIMIT 100;

SELECT resource, SUM(occurrences) AS totals FROM stream WHERE referrer = 'Don_Felder' GROUP BY resource ORDER BY totals DESC LIMIT 100;
SELECT resource, SUM(occurrences) AS totals FROM stream WHERE referrer = 'Eagles_(band)' GROUP BY resource ORDER BY totals DESC LIMIT 100;
SELECT resource, SUM(occurrences) AS totals FROM stream WHERE referrer = 'Don_Henley' GROUP BY resource ORDER BY totals DESC LIMIT 100;



-- 4.  Find an example of an English wikipedia article that is relatively more popular in the Americas than elsewhere.
-- There is no location data associated with the wikipedia pageviews data, but there are timestamps.
-- You'll need to make some assumptions about internet usage over the hours of the day.

-- Initial glance at data
SELECT domain_code, SUM(count_views) AS total_views FROM pageview WHERE page_title = "Tom_Brady" GROUP BY domain_code ORDER BY total_views DESC limit 300;

-- Grouping count_views by Americas and non Americas
INSERT OVERWRITE DIRECTORY '/user/hive/q4_americas'
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '\t'
SELECT domain_code, page_title, SUM(count_views) AS total_views FROM pageview WHERE page_title = "Tom_Brady" AND domain_code LIKE "en%" GROUP BY domain_code, page_title ORDER BY total_views DESC limit 300;
INSERT OVERWRITE DIRECTORY '/user/hive/q4_world'
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '\t'
SELECT SUM(count_views) AS total_views FROM pageview WHERE page_title = "Tom_Brady" AND domain_code NOT LIKE "en%" ORDER BY total_views DESC limit 300;



-- 5. Analyze how many users will see the average vandalized wikipedia page before the offending edit is reversed.
CREATE TABLE revision (
  wiki_db STRING,
  event_entity STRING,
  event_type STRING,
  event_timestamp STRING,
  event_comment_escaped STRING,
  event_user_id INT,
  event_user_text_historical_escaped STRING,
  event_user_text_escaped STRING,
  event_user_blocks_historical_string STRING,
  event_user_blocks_string STRING,
  event_user_groups_historical_string STRING,
  event_user_groups_string STRING,
  event_user_is_bot_by_historical_string STRING,
  event_user_is_bot_by_string STRING,
  event_user_is_created_by_self BOOLEAN,
  event_user_is_created_by_system BOOLEAN,
  event_user_is_created_by_peer BOOLEAN,
  event_user_is_anonymous BOOLEAN,
  event_user_registration_timestamp STRING,
  event_user_creation_timestamp STRING,
  event_user_first_edit_timestamp STRING,
  event_user_revision_count INT,
  event_user_seconds_since_previous_revision INT,
  page_id INT,
  page_title_historical_escaped STRING,
  page_title_escaped STRING,
  page_namespace_historical INT,
  page_namespace_is_content_historical BOOLEAN,
  page_namespace INT,
  page_namespace_is_content BOOLEAN,
  page_is_redirect BOOLEAN,
  page_is_deleted BOOLEAN,
  page_creation_timestamp STRING,
  page_first_edit_timestamp STRING,
  page_revision_count INT,
  page_seconds_since_previous_revision INT,
  user_id INT,
  user_text_historical_escaped STRING,
  user_text_escaped STRING,
  user_blocks_historical_string STRING,
  user_blocks_string STRING,
  user_groups_historical_string STRING,
  user_groups_string STRING,
  user_is_bot_by_historical_string STRING,
  user_is_bot_by_string STRING,
  user_is_created_by_self BOOLEAN,
  user_is_created_by_system BOOLEAN,
  user_is_created_by_peer BOOLEAN,
  user_is_anonymous BOOLEAN,
  user_registration_timestamp STRING,
  user_creation_timestamp STRING,
  user_first_edit_timestamp STRING,
  revision_id INT,
  revision_parent_id INT,
  revision_minor_edit BOOLEAN,
  revision_deleted_parts_string STRING,
  revision_deleted_parts_are_suppressed BOOLEAN,
  revision_text_bytes INT,
  revision_text_bytes_diff INT,
  revision_text_sha1 STRING,
  revision_content_model STRING,
  revision_content_format STRING,
  revision_is_deleted_by_page_deletion BOOLEAN,
  revision_deleted_by_page_deletion_timestamp STRING,
  revision_is_identity_reverted BOOLEAN,
  revision_first_identity_reverting_revision_id INT,
  revision_seconds_to_identity_revert INT,
  revision_is_identity_revert BOOLEAN,
  revision_is_from_before_page_creation BOOLEAN,
  revision_tags_string STRING
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '\t';

LOAD DATA LOCAL INPATH '/Users/roml/WorkSpace/Rev/pagerevision/2020-12.enwiki.2007-07.tsv' INTO TABLE revision;
LOAD DATA LOCAL INPATH '/Users/roml/WorkSpace/Rev/pagerevision/2020-12.enwiki.2017-03.tsv' INTO TABLE revision;
LOAD DATA LOCAL INPATH '/Users/roml/WorkSpace/Rev/pagerevision/2020-12.enwiki.2018-09.tsv' INTO TABLE revision;
LOAD DATA LOCAL INPATH '/Users/roml/WorkSpace/Rev/pagerevision/2020-12.enwiki.2020-11.tsv' INTO TABLE revision;

-- Peek at vandalism data
SELECT * FROM revision WHERE revision_tags_string LIKE '%vandal%';

-- Intermediate table to obtain total number of seconds of vandalism per page 
CREATE TEMPORARY TABLE average_vandalism AS SELECT page_id, SUM(revision_seconds_to_identity_revert) AS total_seconds FROM revision WHERE revision_tags_string LIKE '%vandal%' GROUP BY page_id;

-- Total number of vandalized pages with total of all seconds vandalized
INSERT OVERWRITE DIRECTORY '/user/hive/q5_sec_per_page'
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '\t'
SELECT count(page_id) AS total_pages, SUM(total_seconds) AS total_seconds FROM average_vandalism;


-- Intermediate table to collect total number of pages and respective count_views
CREATE TEMPORARY TABLE avg_visitor_per_page AS SELECT page_title, SUM(count_views) AS total_views FROM pageview GROUP BY page_title;

-- Total number of pages and views
INSERT OVERWRITE DIRECTORY '/user/hive/q5_views_per_page'
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '\t'
SELECT COUNT(page_title) AS total_pages, SUM(total_views) AS total_views FROM avg_visitor_per_page;



-- 6. Which National Park received the most traffic on August 25th (National Park Service Day/Birthday)
DROP TABLE pageview;
LOAD DATA LOCAL INPATH '/Users/roml/WorkSpace/Rev/nps/pageviews-20200825-000000' INTO TABLE pageview;
LOAD DATA LOCAL INPATH '/Users/roml/WorkSpace/Rev/nps/pageviews-20200825-110000' INTO TABLE pageview;
LOAD DATA LOCAL INPATH '/Users/roml/WorkSpace/Rev/nps/pageviews-20200825-170000' INTO TABLE pageview;
LOAD DATA LOCAL INPATH '/Users/roml/WorkSpace/Rev/nps/pageviews-20200825-220000' INTO TABLE pageview;


SELECT page_title, SUM(count_views) AS total_views FROM pageview WHERE page_title LIKE '%National_Park' GROUP BY page_title ORDER BY total_views DESC LIMIT 100;
