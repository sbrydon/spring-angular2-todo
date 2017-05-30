CREATE USER 'todo-rest'@'localhost';
UPDATE mysql.user SET Password=PASSWORD('password') WHERE User='todo-rest' AND Host='localhost';
GRANT Delete ON todo.* TO 'todo-rest'@'localhost';
GRANT Insert ON todo.* TO 'todo-rest'@'localhost';
GRANT Select ON todo.* TO 'todo-rest'@'localhost';
GRANT Update ON todo.* TO 'todo-rest'@'localhost';
FLUSH PRIVILEGES;


INSERT INTO role (name) VALUES ('ROLE_USER'), ('ROLE_ADMIN');