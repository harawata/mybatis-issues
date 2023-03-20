drop table user_table if exists;

create table user_table (
  id bigint,
  name varchar(20),
  age int,
  address varchar(20)
);

insert into user_table values
(1, 'jin', 20, null);
