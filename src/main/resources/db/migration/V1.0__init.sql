---CREATE SCHEMA IF NOT EXISTS test;

--- Types & Tables

CREATE TABLE IF NOT EXISTS app_role (
  id            bigserial NOT NULL PRIMARY KEY,
  description   varchar(255) DEFAULT NULL,
  role_name     varchar(255) DEFAULT NULL
) WITH (OIDS=FALSE);

CREATE TABLE IF NOT EXISTS app_user (
  id           bigserial NOT NULL PRIMARY KEY,
  first_name   varchar(255) NOT NULL,
  last_name    varchar(255) NOT NULL,
  password     varchar(255) NOT NULL,
  username     varchar(255) NOT NULL
) WITH (OIDS=FALSE);


CREATE TABLE IF NOT EXISTS user_role (
  user_id       bigserial NOT NULL,
  role_id       bigserial NOT NULL,
  CONSTRAINT FK859n2jvi8ivhui0rl0esws6o FOREIGN KEY (user_id) REFERENCES app_user (id),
  CONSTRAINT FKa68196081fvovjhkek5m97n3y FOREIGN KEY (role_id) REFERENCES app_role (id)
) WITH (OIDS=FALSE);