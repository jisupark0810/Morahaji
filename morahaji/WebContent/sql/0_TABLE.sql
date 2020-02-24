DROP TABLE lastActivity;
DROP TABLE reportcount;
DROP TABLE count;
DROP TABLE hashtag_post;
DROP TABLE hashtag;
DROP TABLE reply;
DROP TABLE board;
DROP TABLE word;
DROP TABLE users;


CREATE TABLE users (
  user_key int PRIMARY KEY,
  user_id varchar2(20) NOT NULL,
  user_name varchar2(50) NOT NULL,
  user_email varchar2(50) NOT NULL,
  user_password varchar2(20) NOT NULL,
  user_ageRange varchar2(20),
  user_profilePhoto varchar2(250),
  user_status CHAR(1)
);

CREATE TABLE word (
  word_key int PRIMARY KEY,
  user_key int NOT NULL,
  word_title varchar2(50) NOT NULL,
  word_content varchar2(250) NOT NULL,
  word_exSentence varchar2(250) NOT NULL,
  word_date date NOT NULL,
  word_gif varchar2(250)
);

CREATE TABLE board (
  board_key int PRIMARY KEY,
  user_key int NOT NULL,
  board_title varchar2(100) NOT NULL,
  board_content varchar2(250) NOT NULL,
  board_gif varchar2(250),
  board_date date NOT NULL,
  BOARD_READCOUNT int default 0
);

CREATE TABLE reply (
  reply_key int PRIMARY KEY,
  user_key int NOT NULL,
  board_key int NOT NULL,
  reply_content varchar2(250) NOT NULL,
  reply_date date NOT NULL,
  reply_re_ref int,
  reply_re_lev int,
  REPLY_RE_SEQ int
);

CREATE TABLE hashtag (
  hash_key int PRIMARY KEY,
  hash_title varchar2(100)
);

CREATE TABLE hashtag_post (
  hash_key int NOT NULL,
  word_key int
);

CREATE TABLE count (
  user_key int,
  count_type varchar2(10) NOT NULL,
  post_type varchar2(10) NOT NULL,
  count_date date NOT NULL,
  board_key int,
  word_key int
);

CREATE TABLE reportcount (
  user_key int,
  report_date date,
  report_reason varchar2(250),
  board_key int,
  word_key int
);

CREATE TABLE lastActivity (
  user_key int,
  lastAct date NOT NULL
);

ALTER TABLE word ADD FOREIGN KEY (user_key) REFERENCES users (user_key);

ALTER TABLE board ADD FOREIGN KEY (user_key) REFERENCES users (user_key);

ALTER TABLE reply ADD FOREIGN KEY (user_key) REFERENCES users (user_key);

ALTER TABLE reply ADD FOREIGN KEY (board_key) REFERENCES board (board_key) on delete cascade;

ALTER TABLE hashtag_post ADD FOREIGN KEY (hash_key) REFERENCES hashtag (hash_key);

ALTER TABLE hashtag_post ADD FOREIGN KEY (word_key) REFERENCES word (word_key);

ALTER TABLE count ADD FOREIGN KEY (user_key) REFERENCES users (user_key);

ALTER TABLE count ADD FOREIGN KEY (board_key) REFERENCES board (board_key) on delete cascade;

ALTER TABLE count ADD FOREIGN KEY (word_key) REFERENCES word (word_key);

ALTER TABLE reportcount ADD FOREIGN KEY (user_key) REFERENCES users (user_key);

ALTER TABLE reportcount ADD FOREIGN KEY (board_key) REFERENCES board (board_key) on delete cascade;

ALTER TABLE reportcount ADD FOREIGN KEY (word_key) REFERENCES word (word_key);

ALTER TABLE lastActivity ADD FOREIGN KEY (user_key) REFERENCES users (user_key);

CREATE OR REPLACE VIEW WORDLIST AS
SELECT W.WORD_KEY, W.USER_KEY, W.WORD_TITLE, W.WORD_CONTENT, W.WORD_EXSENTENCE, W.WORD_DATE, W.WORD_GIF,
CL.LIKECOUNT, CH.HATECOUNT
FROM WORD W 
LEFT OUTER JOIN (SELECT COUNT(*) AS LIKECOUNT, WORD_KEY FROM COUNT WHERE POST_TYPE = 'word' AND COUNT_TYPE = 'like' GROUP BY WORD_KEY) CL
ON CL.WORD_KEY = W.WORD_KEY
LEFT OUTER JOIN (SELECT COUNT(*) AS HATECOUNT, WORD_KEY FROM COUNT WHERE POST_TYPE = 'word' AND COUNT_TYPE = 'hate' GROUP BY WORD_KEY) CH
ON CH.WORD_KEY = W.WORD_KEY;

COMMIT;