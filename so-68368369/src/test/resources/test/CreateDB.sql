drop table users if exists;

create table users (
  id int,
  name varchar(20)
);

CREATE OR REPLACE TYPE USERNAMES AS VARCHAR(10) ARRAY[5];


-- @DELIMITER |
create or replace procedure testproc(
  in names USERNAMES
)
language sql
begin
  declare i, n integer;  
  set n = CARDINALITY(names);
  set i = 1; 
  while (i <= n) do 
    insert into users (id, name) values (i, names[i]); 
    set i = i + 1; 
  end while;
end
|
-- @DELIMITER ;
