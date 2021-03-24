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
  id int,
  name varchar(20),
  xml XMLTYPE
);

insert into users (id, name, xml) values
(1, 'User1', sys.xmltype.createxml('<foo>bar</foo>'));
