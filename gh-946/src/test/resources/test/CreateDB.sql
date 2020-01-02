drop table person;

create table person (
  id int,
  name varchar(20),
  friend_id int
);

insert into person (id, name, friend_id) values (1, 'john', 2);
insert into person (id, name, friend_id) values (2, 'mary', 1);

-- @DELIMITER |

create or replace procedure single_curs(person_id in int, person_curs out sys_refcursor) is
begin
  open person_curs for select * from person where id = person_id;
end;
|

-- @DELIMITER ;
