CREATE USER 'user'@'localhost' IDENTIFIED BY 'user';
GRANT ALL PRIVILEGES ON 'cliente'.* TO 'user'@'localhost';
GRANT ALL PRIVILEGES ON 'cliente_teste'.* TO 'user'@'localhost';
FLUSH PRIVILEGES;