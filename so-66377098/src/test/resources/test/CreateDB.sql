-- @DELIMITER |
begin
  execute immediate 'drop table users';
exception
  when others then
    if sqlcode != -942 then
      raise;
    end if;
end;
|
-- @DELIMITER ;

create table users (
  id int generated always as identity,
  name varchar(20)
);

