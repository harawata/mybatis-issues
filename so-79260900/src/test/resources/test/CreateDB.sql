drop table if exists task_list;

create table task_list (
  region_name varchar(20),
  office_name varchar(20),
  task_finish_time varchar(10)
);

insert into task_list values
('A', 'A1', '2022-01-03'),
('A', 'A1', '2022-01-04'),
('A', 'A1', '2022-02-04'),
('A', 'A2', '2022-02-04'),
('B', 'B1', '2023-01-06'),
('B', 'B1', '2023-02-03'),
('B', 'B1', '2023-02-16');