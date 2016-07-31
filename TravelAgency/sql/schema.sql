create table client (
	uid serial primary key,
	surname varchar(20),
	name varchar(20),
	patronymic varchar(20),
	phoneMobile varchar(20),
	phoneHome varchar(20),
	address varchar(40),
	birthDate varchar(20),
	passport_id int not null references passport(uid)
);

create table passport (
	uid serial primary key,
	series varchar(20),
	number varchar(20),
	received varchar(40),
	issueDate varchar(20),
	expiryDate varchar(20)
);

create table tour (
	uid serial primary key,
	nameTour varchar(20),
	dateBegin varchar(20),
	dateEnd varchar(20),
	dayNumber varchar(20),
	personNumber varchar(20),
	tourOperator varchar(40),
	hotel varchar(20),
	typeNumber varchar(20),
	food varchar(20),
	tourСost varchar(20),
	route_id int not null references route(uid)
);

create table route (
	uid serial primary key,
	transportName varchar(20),
	cityDeparture varchar(20),
	cityCome varchar(20),
	countryCome varchar(20),
	dateDeparture varchar(20),
	dateCome varchar(20),
	dateReturn varchar(20)
);