drop table if exists person;
drop table if exists pet;
drop procedure if exists get_person_pet;
drop procedure if exists get_all_person_pet;

create table person (
  id int,
  name varchar(20)
)
create table pet (
  id int,
  owner_id int,
  name varchar(20)
)

insert into person (id, name)
  values (1, 'Mary');
insert into person (id, name)
  values (2, 'Joe');

insert into pet (id, owner_id, name)
  values (1, 1, 'Cat');
insert into pet (id, owner_id, name)
  values (2, 2, 'Dog');
insert into pet (id, owner_id, name)
  values (3, 1, 'Snake');

-- @DELIMITER $

create procedure get_person_pet 
  @personId int
as
begin
  select * from person where id = @personId;
  select * from pet where owner_id = @personId order by id;
  select * from pet where owner_id = @personId order by id;
end
$

create procedure get_all_person_pet 
as
begin
  select * from person order by id;
  select * from pet order by owner_id, id;
  select * from pet order by owner_id, id;
end
$
-- @DELIMITER ;
