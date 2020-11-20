drop table if exists users;

create table users (
  id int,
  name varchar(20),
  roles varchar(20)[]
);

insert into users (id, name, roles) values
(1, 'User1', array['aa','bb']),
(2, 'User2', array['cc']);
