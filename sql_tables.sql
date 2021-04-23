create schema ;

create table users (

userId bigint unique,
name varchar(40),
identification_number  varchar(15) unique,
emailId varchar(30) primary key,
password varchar(100)
);

insert into users values (101,"Alex O'donnell","19821264","alex@email.com","alex");
insert into users values (102,"Sophia West","55980089","sophia@email.com","sophia");
insert into users values (103,"Jake Webster","92442273","jake@email.com","jake");
insert into users values (104,"Shana Steele","29318206","shana@email.com","shana");
insert into users values (105,"Michael Fisher","20775686","michael@email.com","michael");



create table stocks(
stockId bigint primary key not null auto_increment,
stockName varchar(100),
stockSymbol varchar(15),
marketCap varchar (10)
);

select * from stocks;


