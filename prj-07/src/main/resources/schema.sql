CREATE TABLE users
(
  username character varying(20) NOT NULL,
  password character varying(200) NOT NULL,
  enabled boolean NOT NULL DEFAULT false,
  CONSTRAINT users_pkey PRIMARY KEY (username)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE users
  OWNER TO postgres;
  

CREATE TABLE user_roles
(
  user_role_id serial NOT NULL,
  username character varying(20) NOT NULL,
  role character varying(20) NOT NULL,
  CONSTRAINT user_roles_pkey PRIMARY KEY (user_role_id),
  CONSTRAINT user_roles_username_fkey FOREIGN KEY (username)
      REFERENCES users (username) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT user_roles_username_role_key UNIQUE (username, role)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE user_roles
  OWNER TO postgres;
  
  
  