drop table fruit if exists;
drop table animal if exists;

create table fruit (
  id int,
  name varchar(20),
  qty int
);

create table animal (
  type varchar(20),
  num int,
  loc varchar(20)
);

insert into fruit (id, name, qty) values
(1, 'Apple', 2),
(2, 'Orange', 3),
(3, 'Banana', 5);

insert into animal (type, num, loc) values
('monkey', 2000, 'London'),
('dog', 3000, 'New York'),
('cat', 8000, 'LA');

-- @DELIMITER |
create or replace procedure test_proc (
  in p1 varchar(20),
  in p2 varchar(20)
)
dynamic result sets 2
begin
  declare c1 cursor with return to caller for
  select * from animal;
  declare c2 cursor with return to caller for
  select * from fruit;
  open c2;
  open c1;
end
|
-- @DELIMITER ;
