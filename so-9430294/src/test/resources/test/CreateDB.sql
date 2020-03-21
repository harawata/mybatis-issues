drop schema tenant1 if exists cascade;
drop schema tenant2 if exists cascade;
drop schema tenant3 if exists cascade;

create schema tenant1 authorization dba
  create table tenant1.users (
    id int,
    name varchar(20)
  );
create schema tenant2 authorization dba
  create table tenant2.users (
    id int,
    name varchar(20)
  );
create schema tenant3 authorization dba
  create table tenant3.users (
    id int,
    name varchar(20)
  );

insert into tenant1.users (id, name) values
(1, 'User 1-1'),
(2, 'User 1-2');
insert into tenant2.users (id, name) values
(1, 'User 2-1'),
(2, 'User 2-2');
insert into tenant3.users (id, name) values
(1, 'User 3-1'),
(2, 'User 3-2');
