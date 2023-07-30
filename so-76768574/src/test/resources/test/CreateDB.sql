create or replace type ARR_TEXT_OUT as varray(20000) of varchar(200);

-- @DELIMITER $
create or replace procedure RESOLVE_76768574(
  IN_id_processo IN NUMBER,
  OUT_text OUT ARR_TEXT_OUT
) is
begin
	select ARR_TEXT_OUT('aa','bb','cc') into OUT_text from dual;
end;
$
-- @DELIMITER ;
