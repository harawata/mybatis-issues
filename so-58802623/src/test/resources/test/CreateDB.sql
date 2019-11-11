drop table if exists users;
drop procedure if exists MY_PROC;

create table users (
  id int,
  name varchar(20),
  note varchar(20)
)

insert into users (id, name, note)
  values (1, 'John', 'Jolly good fellow');

-- @DELIMITER $
create procedure MY_PROC 
  @ARG1 varchar(10)
as
begin
  select * from users where name = @ARG1;
end
$
-- @DELIMITER ;
