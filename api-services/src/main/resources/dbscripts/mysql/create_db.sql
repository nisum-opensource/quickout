--Create database statements
create database quickout;
CREATE USER 'quickout'@'%' IDENTIFIED BY 'quickout';
CREATE USER 'quickout'@'localhost' IDENTIFIED BY 'quickout';
grant all privileges on quickout.* to 'quickout'@'localhost';
grant all privileges on quickout.* to 'quickout'@'%';
FLUSH PRIVILEGES;
