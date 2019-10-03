-- @DELIMITER $
create or replace function f_foo_function(
  foo in varchar, 
  foo_output out integer, 
  error out varchar2
) return integer is
begin
	foo_output := 1;
	error := 'No error';
  return 99;
end;
$
-- @DELIMITER ;
