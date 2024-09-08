drop table author if exists;
drop table blog if exists;
drop table post if exists;

create table author (
  id int,
  username varchar(20)
);

create table blog (
  id int,
  title varchar(20),
  author_id int
);

create table post (
  id int,
  subject varchar(20),
  blog_id int,
  author_id int
);

insert into author values
(1, 'Mia'),
(2, 'Bob');

insert into blog values
(1, 'Blog 1', 1),
(2, 'Blog 2', 2),
(3, 'Blog 3', 1);

insert into post values
(1, 'Post 1', 1, 1),
(2, 'Post 2', 2, 1),
(3, 'Post 3', 1, 2),
(4, 'Post 4', 2, 1),
(5, 'Post 5', 1, 1);

