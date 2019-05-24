drop table if exists some_table_name;

create table some_table_name (
  id int,
  long_varibary_column long varbinary
);

insert into some_table_name (id, long_varibary_column) values
(1, 'abc');
