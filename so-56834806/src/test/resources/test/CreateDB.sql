-- @DELIMITER $
begin
  execute immediate 'drop table users';
exception
  when others then
    if sqlcode != -942 then
      raise;
    end if;
end;
$
-- @DELIMITER ;

drop type S_USER_OBJ_LIST;

create or replace type S_USER_OBJ as object (
  id integer,
  name varchar(20)
);

create or replace type S_USER_OBJ_LIST as table of S_USER_OBJ;

create table users (
  id integer,
  name varchar(20)
);

-- @DELIMITER $
create or replace procedure SAVE_USERS(
  single_user in S_USER_OBJ,
  user_list in S_USER_OBJ_LIST,
  name_prefix in varchar,
  user_count out number,
  user_struct out S_USER_OBJ,
  user_struct_array out S_USER_OBJ_LIST,
  user_curs out sys_refcursor
) is
begin

	insert into users (id, name) values (single_user.id, name_prefix || single_user.name);

  for i in user_list.first .. user_list.last loop
    insert into users (id, name)
      values (user_list(i).id, name_prefix || user_list(i).name);
  end loop;

  select count(*) into user_count from users;

  user_struct := S_USER_OBJ(null, null);
  select S_USER_OBJ(u.id, u.name) into user_struct from users u where u.id = 1;

  select * bulk collect into user_struct_array
    from (select S_USER_OBJ(u.id, u.name) from users u order by u.id);

  open user_curs for select * from users order by id;

end;
$
-- @DELIMITER ;
