begin
  execute immediate 'drop table test';
exception
  when others then
    if sqlcode != -942 then
      raise;
    end if;
end;
|

create table test(name varchar2(30), filename varchar2(30))|

create or replace type stringArray as table of varchar2(30)|

create or replace procedure create_deliverable(
  in_p_name varchar2,
  in_p_filename stringArray,
  outparam out stringArray
) as
  ret_ID number;
begin
  for i in 1 .. in_p_filename.count loop
    insert into test(name, filename) values (in_p_name, in_p_filename(i));
  end loop;
  outparam := stringArray('1','2');
end;
|
