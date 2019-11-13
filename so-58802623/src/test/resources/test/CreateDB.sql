drop table if exists users;
drop procedure if exists get_discount_value;

create table users (
  id int,
  name varchar(20),
  DiscountValue varchar(20)
)

insert into users (id, name, DiscountValue)
  values (1, 'Joe', 'Jolly good fellow');

-- @DELIMITER $
create procedure get_discount_value 
  @firstname nvarchar(50),
  @lastname nvarchar(10),
  @gender nvarchar(1),
  @dateOfBirth date,
  @age bigint
as
begin
  select * from users where name = @firstname;
end
$
-- @DELIMITER ;
