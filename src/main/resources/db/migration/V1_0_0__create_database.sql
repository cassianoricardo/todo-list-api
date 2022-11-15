drop table if exists user CASCADE;
drop table if exists role CASCADE;
drop table if exists task CASCADE;

CREATE TABLE role (
  id bigint NOT NULL AUTO_INCREMENT,
  name varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE user (
  id bigint NOT NULL AUTO_INCREMENT,
  email varchar(255) NOT NULL ,
  name varchar(255) DEFAULT NULL,
  password varchar(255) DEFAULT NULL,
  role_id bigint NOT NULL,
  PRIMARY KEY (id),
  constraint uk_email unique (email),
  constraint fk_role_id foreign key (role_id) references role(id)
);

CREATE TABLE task (
  id bigint NOT NULL AUTO_INCREMENT,
  date_created datetime DEFAULT NULL,
  date_last_update datetime DEFAULT NULL,
  description varchar(255) DEFAULT NULL,
  status int DEFAULT NULL,
  summary varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
);

INSERT INTO role(name)VALUES('USER');
INSERT INTO role(name)VALUES('ADMIN');

INSERT INTO user(email,name,password,role_id)VALUES('cassiano_ricardo@hotmail.com','cassiano','$2a$10$CQMsc74IBr0cQkLD.c96kOHmTXUxNCkqYyQCxlB7ys6ue81/gvMXe',1);
INSERT INTO user(email,name,password,role_id)VALUES('cassiano_ricardo@gmail.com','cassiano','$2a$10$CQMsc74IBr0cQkLD.c96kOHmTXUxNCkqYyQCxlB7ys6ue81/gvMXe',2);