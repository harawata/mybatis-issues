-- @DELIMITER $
CREATE OR REPLACE TYPE TYPE_CALC AS OBJECT (
  modelField   VARCHAR2(5 CHAR),
  sysField     VARCHAR2(5 CHAR),
  hexField     VARCHAR2(5 CHAR)
);
$
-- @DELIMITER ;

-- @DELIMITER $
create or replace package pkg_24426393 as
  PROCEDURE pr_start(
    io_calc IN OUT TYPE_CALC,
    in_restart BOOLEAN DEFAULT TRUE,
    in_user    VARCHAR2 DEFAULT NULL
  );
end;
$
-- @DELIMITER ;

-- @DELIMITER $
create or replace package body pkg_24426393 as
  PROCEDURE pr_start(
    io_calc IN OUT TYPE_CALC,
    in_restart BOOLEAN DEFAULT TRUE,
    in_user    VARCHAR2 DEFAULT NULL
    )
  is
  begin
    select 'foo' into io_calc.hexField from dual;
  end;
end;
$
-- @DELIMITER ;
