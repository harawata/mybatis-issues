drop table if exists table_a;

create table table_a (
  id int,
  column_1 jsonb
);

insert into table_a (id, column_1) values
(1, '{"key_alpha": 0}');
