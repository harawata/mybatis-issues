-- @DELIMITER $
BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE my_table';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
$
-- @DELIMITER ;

create table my_table (
  product_id int,
  valid_from date,
  valid_till date
);

insert into my_table (product_id, valid_from, valid_till) values (1, '2019-01-01', '2019-01-04');
insert into my_table (product_id, valid_from, valid_till) values (2, '2019-01-02', '2019-01-04');
insert into my_table (product_id, valid_from, valid_till) values (3, '2019-01-06', '2019-01-07');
