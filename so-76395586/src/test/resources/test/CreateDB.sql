drop database if exists unit_test_database_two;

create database unit_test_database_two;

create table unit_test_database_two.order_items (
  id int primary key,
  name varchar(20) unique
);



drop database if exists unit_test_database_three;

create database unit_test_database_three;

create table unit_test_database_three.order_items (
  id int primary key,
  name varchar(20) unique
);