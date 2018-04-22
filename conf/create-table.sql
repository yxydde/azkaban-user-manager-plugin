DROP TABLE IF EXISTS groups;
CREATE TABLE groups (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  PRIMARY KEY (id)
);

INSERT INTO groups VALUES ('1', 'admin');
INSERT INTO groups VALUES ('2', 'azkaban');

DROP TABLE IF EXISTS roles;
CREATE TABLE roles (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  PRIMARY KEY (id)
);

INSERT INTO roles VALUES ('1', 'admin');
INSERT INTO roles VALUES ('2', 'azkaban');

DROP TABLE IF EXISTS users;
CREATE TABLE users (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  email varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
);

INSERT INTO users VALUES ('1', 'admin', 'admin', null);
INSERT INTO users VALUES ('2', 'azkaban', 'azkaban', null);


DROP TABLE IF EXISTS user_groups;
CREATE TABLE user_groups (
  id int(11) NOT NULL AUTO_INCREMENT,
  group_id int(11) DEFAULT NULL,
  user_id int(11) DEFAULT NULL,
  PRIMARY KEY (id)
);

INSERT INTO user_groups VALUES ('1', '1', '1');
INSERT INTO user_groups VALUES ('2', '2', '2');

