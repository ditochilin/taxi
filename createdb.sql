/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     06.08.2018 20:15:44                          */
/*==============================================================*/
use taxidb;
SET FOREIGN_KEY_CHECKS=0;

drop table if exists car_types;

drop table if exists orders;

drop table if exists orders_shares;

drop table if exists roles;

drop table if exists shares;

drop table if exists taxis;

drop table if exists users;
SET FOREIGN_KEY_CHECKS=1;
/*==============================================================*/
create table car_types
(
	id_car_type bigint auto_increment
		primary key,
	type_name   varchar(50) not null,
	price       decimal     null
);

create table roles
(
	id_role     bigint auto_increment
		primary key,
	description varchar(500) null,
	role_name   varchar(30)  not null,
	constraint roles_role_name_uindex
	unique (role_name)
);

create table shares
(
	id_share   bigint auto_increment
		primary key,
	share_name varchar(256)           not null,
	is_loyalty tinyint(1)             null,
	sum        decimal                null,
	percent    float                  null,
	is_on      tinyint(1) default '0' not null
);

create table users
(
	id_user   bigint auto_increment
		primary key,
	id_role   bigint       not null,
	phone     varchar(20)  not null,
	password  varchar(20)  not null,
	user_name varchar(100) not null,
	constraint users_phone_uindex
	unique (phone),
	constraint users_user_name_uindex
	unique (user_name),
	constraint fk_users_userrole_roles
	foreign key (id_role) references roles (id_role)
);

create table taxis
(
	id_taxi     bigint auto_increment
		primary key,
	id_user     bigint       not null,
	id_car_type bigint       null,
	car_name    varchar(256) not null,
	car_number  varchar(20)  not null,
	busy        tinyint(1)   null
	comment '0',
	constraint fk_taxis_usertaxi_users
	foreign key (id_user) references users (id_user),
	constraint fk_taxis_taxicarty_car_type
	foreign key (id_car_type) references car_types (id_car_type)
);

create table orders
(
	id_order     bigint auto_increment
		primary key,
	id_taxi      bigint                        null,
	id_user      bigint                        not null,
	order_date   datetime                      not null,
	start_point  varchar(500)                  not null,
	end_point    varchar(500)                  not null,
	distance     int                           null,
	cost         decimal                       null,
	status_order varchar(20) default 'CREATED' not null,
	waiting_time int                           null,
	feed_time    datetime                      null,
	constraint fk_orders_taxiorder_taxis
	foreign key (id_taxi) references taxis (id_taxi),
	constraint fk_orders_userorder_users
	foreign key (id_user) references users (id_user)
);

create index fk_orders_taxiorder_taxis
	on orders (id_taxi);

create index fk_orders_userorder_users
	on orders (id_user);

create table orders_shares
(
	id_orders_shares bigint auto_increment
		primary key,
	id_share         bigint null,
	id_order         bigint null,
	constraint fk_orders_s_shareorde_shares
	foreign key (id_share) references shares (id_share),
	constraint fk_orders_s_ordershar_orders
	foreign key (id_order) references orders (id_order)
);

create index fk_orders_s_ordershar_orders
	on orders_shares (id_order);

create index fk_orders_s_shareorde_shares
	on orders_shares (id_share);

create index fk_taxis_taxicarty_car_type
	on taxis (id_car_type);

create index fk_taxis_usertaxi_users
	on taxis (id_user);

create index fk_users_userrole_roles
	on users (id_role);

