drop table tb_a if exists;

create table tb_a (
  id int,
  album_id int,
  videolibrary_id int
);

insert into tb_a (id, album_id, videolibrary_id) values
(1, 100, 1000),
(2, 101, 1001);
