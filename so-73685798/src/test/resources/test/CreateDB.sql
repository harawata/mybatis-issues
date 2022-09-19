drop table if exists Movie;
drop table if exists Artist;

create table Movie (
  id_ int,
  name_ varchar(20),
  year_ int
);
create table Artist (
  id_ int,
  name_ varchar(20)
);

insert into Movie (id_, name_, year_) values (1, 'Movie1', 2020);
insert into Movie (id_, name_, year_) values (2, 'Movie2', 2008);
insert into Movie (id_, name_, year_) values (3, 'Movie3', 1988);

insert into Artist (id_, name_) values (1, 'John');
insert into Artist (id_, name_) values (2, 'Jane');
