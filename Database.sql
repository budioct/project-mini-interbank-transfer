show databases;

create database banking_transfer;

use banking_transfer;

show tables;

create table accounts
(
    id             bigint auto_increment,
    account_number varchar(255)   not null,
    balance        decimal(38, 2) not null,
    bank           varchar(255)   not null,
    primary key (id)
);

describe accounts;

INSERT INTO `accounts` VALUES (1,'1111122222',10000.00,'MANDIRI'),
                              (2,'1111133333',10000.00,'BCA'),
                              (4,'1111133333',10000.00,'BRI'),
                              (3,'1111144444',10000.00,'BNI'),
                              (5,'1111133333',10000.00,'PERMATA')
                             ;

select * from accounts;