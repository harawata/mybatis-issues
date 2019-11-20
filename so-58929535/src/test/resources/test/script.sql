create table daiql_test2 (
    id int,
    name varchar(100)
);

insert into daiql_test2 (id, name) value (1, 'test1');
update daiql_test2 set name = 'test2' where id = 1;

gold bless no bug...;
