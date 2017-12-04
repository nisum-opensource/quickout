use quickout;
drop table shipment_info;
drop table cart_details;
drop table address;
drop table cart;
drop table customer;
DROP USER 'quickout'@'localhost';
DROP USER 'quickout'@'%';
FLUSH PRIVILEGES;