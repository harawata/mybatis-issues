-- @DELIMITER $
BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE student_audit';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
$
-- @DELIMITER ;

CREATE TABLE student_audit (
  id NUMBER(8) NOT NULL,
  name VARCHAR2(20 BYTE) NOT NULL,
  audit_timestamp TIMESTAMP NULL,
  CONSTRAINT pk_stdnt_aud PRIMARY KEY (id)
);

insert into student_audit (id, name, audit_timestamp) values
(1, 'John', TO_TIMESTAMP('2022-06-22 07:23:59.515462', 'YYYY-MM-DD HH24:MI:SS.FF'));
