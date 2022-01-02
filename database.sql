
create table `countries` (
	`id` int not null primary key auto_increment,
	`name` varchar(64) not null
);
insert into `countries`(`id`, `name`) values(null, 'Maroc');

create table `cities` (
	`id` int not null primary key auto_increment,
	`name` varchar(64) not null
);
insert into `cities`(`id`, `name`) values(null, 'Kenitra');
insert into `cities`(`id`, `name`) values(null, 'Rabat');
insert into `cities`(`id`, `name`) values(null, 'Skhirate');
insert into `cities`(`id`, `name`) values(null, 'Temara');

create table `addresses` (
	`id` int not null primary key auto_increment,
	`street` varchar(128) not null,
	`country_id` int,
	`city_id` int,
	CONSTRAINT `fk_country` foreign key (`country_id`) references `countries`(`id`) on delete set null,
	CONSTRAINT `fk_city` foreign key (`city_id`) references `cities`(`id`) on delete set null
);

create table `users` (
	`id` int not null primary key auto_increment,
	`name` varchar(64) not null,
	`password` varchar(128) not null,
	`email` varchar(128) not null,
	`job` varchar(64) not null,
	`address_id` int,
	`experience` int,
	`diploma` varchar(64),
	constraint `fk_address` foreign key (`address_id`) references `addresses`(`id`) on delete set null
)


% new design
create table `users` (
	`id` int not null primary key auto_increment,
	`name` varchar(64) not null,
	`username` varchar(64) not null,
	`password` varchar(128) not null,
	`email` varchar(128) not null,
	`job` varchar(64) not null,
	`experience` int,
	`diploma` varchar(64)
)
