drop table employee if exists;
drop table address if exists;

create table employee (
  emplId int,
  name varchar(20)
);
create table address (
  addrId int,
  emplId int,
  addressLineOne varchar(20),
  addressLineTwo varchar(20),
  city varchar(20)
);

insert into employee (emplId, name) values
(1, 'Employee1'),
(2, 'Employee2');

insert into address (addrId, emplId, addressLineOne, addressLineTwo, city) values
(1, 1, 'a', 'aa', 'city1'),
(2, 2, 'b', 'bb', 'city2'),
(3, 1, 'c', 'cc', 'city3'),
(4, 1, 'd', 'dd', 'city4');
