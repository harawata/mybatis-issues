drop table if exists t_service;
drop table if exists t_rate;

create table t_service (
  id uuid,
  name varchar(20)
);
create table t_rate (
  date_from date,
  date_to date,
  currency int,
  service_id uuid
);

insert into t_service (id, name) values
('386f2c7f-3988-438b-af21-fb3562330ac5', 'Service1'),
('03a906a4-9648-46ae-b2bd-cb8a35a808e6', 'Service2');

insert into t_rate (service_id, date_from, date_to, currency) values
('386f2c7f-3988-438b-af21-fb3562330ac5', '2000-01-01', '2007-12-31', 100),
('386f2c7f-3988-438b-af21-fb3562330ac5', '2008-01-01', '2009-12-31', 200),
('386f2c7f-3988-438b-af21-fb3562330ac5', '2010-01-01', '2015-12-31', 300),
('03a906a4-9648-46ae-b2bd-cb8a35a808e6', '2000-01-01', '2018-12-31', 2500);
