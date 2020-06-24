-- @DELIMITER $
BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE users';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
$
-- @DELIMITER ;


create table users (
  id int,
  name1 varchar(20),
  name2 varchar(20),
  name3 varchar(20),
  name4 varchar(20),
  name5 varchar(20),
  name6 varchar(20),
  name7 varchar(20),
  name8 varchar(20),
  name9 varchar(20),
  name10 varchar(20),
  name11 varchar(20),
  name12 varchar(20),
  name13 varchar(20),
  name14 varchar(20)
);

