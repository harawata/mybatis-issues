-- @DELIMITER |
begin
  execute immediate 'drop sequence test_seq';
exception
  when others then
    if sqlcode != -942 then
      raise;
    end if;
end;
|
begin
  execute immediate 'drop table user1';
exception
  when others then
    if sqlcode != -942 then
      raise;
    end if;
end;
|
begin
  execute immediate 'drop table user2';
exception
  when others then
    if sqlcode != -942 then
      raise;
    end if;
end;
|
begin
  execute immediate 'drop table user3';
exception
  when others then
    if sqlcode != -942 then
      raise;
    end if;
end;
|
-- @DELIMITER ;

create sequence test_seq increment by 1 start with 1;

create table user1 (
  id int,
  name varchar(10)
);

create table user2 (
  id int default test_seq.nextval,
  name varchar(10)
);

create table user3 (
  id int generated always as identity,
  name varchar(10)
);
