INSERT INTO user(email, name, password)VALUES('cassiano_ricardo@hotmail.com','cassiano','$2a$10$CQMsc74IBr0cQkLD.c96kOHmTXUxNCkqYyQCxlB7ys6ue81/gvMXe');
INSERT INTO user(email, name, password)VALUES('cassiano_ricardo@gmail.com','cassiano','$2a$10$CQMsc74IBr0cQkLD.c96kOHmTXUxNCkqYyQCxlB7ys6ue81/gvMXe');

INSERT INTO role(name)VALUES('ROLE_USER');
INSERT INTO role(name)VALUES('ROLE_ADMIN');

INSERT INTO user_role(user_id, role_id)VALUES(1,1);
INSERT INTO user_role(user_id, role_id)VALUES(2,1);
INSERT INTO user_role(user_id, role_id)VALUES(2,2);