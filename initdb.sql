set foreign_key_checks = 0;
truncate table orders;
truncate table taxis;
truncate table users;
truncate table orders_shares;
truncate table shares;
truncate table roles;
truncate table car_types;

set foreign_key_checks  = 1;

insert into roles (role_name, description) values ("ADMIN", "Administrator");
insert into roles (role_name, description) values ("CLIENT", "Client (customer of taxi)");
insert into roles (role_name, description) values ("DRIVER", "Driver (client that supplies taxi service, may have one or more cars (taxi))");

insert into users (user_name, password, phone, id_role) values ("root", "root", "0672184141", 1);
