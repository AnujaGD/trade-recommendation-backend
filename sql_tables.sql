create schema user_stock_data;

create table users (
userId int primary key,
name varchar(40),
identification_number varchar(15),
username varchar(20) ,
password varchar(100)
);

insert into users values (101,"Alex Odonnell","19821264","alex","alex");
insert into users values (102,"Sophia West","55980089","sophia","sophia");
insert into users values (103,"Jake Webster","92442273","jake","jake");
insert into users values (104,"Shana Steele","29318206","shana","shana");
insert into users values (105,"Michael Fisher","20775686","michael","michael");

create table user_stocks(
userId int primary key, 
stockName varchar(15) ,
quantity int,
currentPrice int,
foreign key (userId) references users(userId)
)