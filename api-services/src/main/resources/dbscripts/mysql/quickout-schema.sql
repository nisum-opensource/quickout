--Create tables. 
use quickout;
create table cart (
	id varchar(255) not null, 
	create_date datetime, 
	last_udpated datetime, 
	account_code varchar(255) not null, 
	status integer not null, 
	primary key (id)
);

create table cart_details (
	id bigint not null auto_increment, 
    create_date datetime,
    last_udpated datetime,
	img_src varchar(255), 
	list_price float, 
	quantity integer not null, 
	selling_price float, 
	sku_id varchar(255) not null, 
	sku_name varchar(255), 
	cart_id varchar(255), 
	primary key (id)
);
alter table cart_details add constraint FK_CARTDETAILS_CART foreign key (cart_id) references cart (id);
alter table cart_details add constraint UK_SKU_CARTID unique (sku_id, cart_id);
create table address (
    id bigint not null auto_increment,
    create_date datetime,
    last_udpated datetime,
    address varchar(255) not null,
    address2 varchar(255),
    city varchar(255),
    state varchar(255),
    zip_code varchar(255),
    customer_id bigint not null,
    primary key (id)
);
create table customer (
    id bigint not null auto_increment,
    create_date datetime,
    last_udpated datetime,
    email varchar(255),
    first_name varchar(255),
    last_name varchar(255),
    phone varchar(255),
    primary key (id)
);
create table shipment_info (
    id bigint not null auto_increment,
    create_date datetime,
    last_udpated datetime,
    customer_id bigint not null,
    cart_id varchar(255) not null,
    primary key (id)
);
alter table shipment_info add constraint UK_SHIPINFO_CART unique (cart_id);
alter table shipment_info add constraint FK_SHIPINFO_ADDR foreign key (customer_id) references customer (id);
alter table shipment_info add constraint FK_SHIPINFO_CART foreign key (cart_id) references cart (id);
alter table address add constraint FK_ADDR_CUSTOMER foreign key (customer_id) references customer (id);

INSERT INTO cart
(id, create_date, last_udpated, account_code, status)
VALUES
('2ef0ea3c-6306-4faf-8543-2966d54928e9', now(), now(), 'fixed', 0);

INSERT INTO cart_details
(id, create_date, last_udpated, img_src, list_price, quantity, selling_price, sku_id, sku_name, cart_id)
VALUES
(1, now(), now(), 'http://ec2-52-41-159-161.us-west-2.compute.amazonaws.com/pub/media/catalog/product/cache/1/thumbnail/88x110/beff4985b56e3afdbeabfc89641a4582/m/j/mj01-yellow_main.jpg', 
  42.90, 1, 42.90, 1, 'Beaumont Summit Kit', '2ef0ea3c-6306-4faf-8543-2966d54928e9');

INSERT INTO cart_details
(id, create_date, last_udpated, img_src, list_price, quantity, selling_price, sku_id, sku_name, cart_id)
VALUES
(2, now(), now(), 'http://ec2-52-41-159-161.us-west-2.compute.amazonaws.com/pub/media/catalog/product/cache/1/thumbnail/88x110/beff4985b56e3afdbeabfc89641a4582/m/j/mj03-black_main.jpg', 
  42.09, 1, 42.09, 2, 'Montana Wind Jacket', '2ef0ea3c-6306-4faf-8543-2966d54928e9');
  
INSERT INTO cart_details
(id, create_date, last_udpated, img_src, list_price, quantity, selling_price, sku_id, sku_name, cart_id)
VALUES
(3, now(), now(), 'http://ec2-52-41-159-161.us-west-2.compute.amazonaws.com/pub/media/catalog/product/cache/1/thumbnail/88x110/beff4985b56e3afdbeabfc89641a4582/m/j/mj10-red_main.jpg', 
  66.99, 1, 66.99, 3, 'Mars HeatTech™ Pullover', '2ef0ea3c-6306-4faf-8543-2966d54928e9');

INSERT INTO customer
(id, create_date, last_udpated, email, first_name, last_name, phone)
VALUES
(1, now(), now(), '', '', '', '');

INSERT INTO quickout.address
(id, create_date, last_udpated, address, address2, city, state, zip_code, customer_id)
VALUES
(1, now(), now(), '', '', '', '', '', 1);

INSERT INTO shipment_info
(id, create_date, last_udpated, customer_id, cart_id)
VALUES
(1, now(), now(), 1, '2ef0ea3c-6306-4faf-8543-2966d54928e9');
