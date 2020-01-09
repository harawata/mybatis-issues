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
  id int GENERATED ALWAYS as IDENTITY primary key,
  name varchar(20)
);
