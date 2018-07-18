DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id,localdatetime,description,calories) VALUES
  (100001,TIMESTAMP '2015-06-01 14:00:00','Админ ланч',510),
  (100001,TIMESTAMP '2015-06-01 21:00:00','Админ ужин',1500);

