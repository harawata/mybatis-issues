--
--    Copyright 2009-2017 the original author or authors.
--
--    Licensed under the Apache License, Version 2.0 (the "License");
--    you may not use this file except in compliance with the License.
--    You may obtain a copy of the License at
--
--       http://www.apache.org/licenses/LICENSE-2.0
--
--    Unless required by applicable law or agreed to in writing, software
--    distributed under the License is distributed on an "AS IS" BASIS,
--    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
--    See the License for the specific language governing permissions and
--    limitations under the License.
--

drop type S_BANK_INFO_LST;
drop table TESTA;

create or replace type S_BANK_INFO as object
(
  a1 varchar2(20),
  a2 varchar2(30),
  a3 varchar2(30)
);

create type S_BANK_INFO_LST is table of S_BANK_INFO;

create table TESTA
(
  a   VARCHAR2(30),
  b   VARCHAR2(30),
  c   VARCHAR2(30)
);

-- @DELIMITER |

create or replace procedure proc_bank_info_sync(s_banks IN S_BANK_INFO_LST) is
v_idx integer;
begin
  for v_idx in s_banks.first .. s_banks.last loop
    insert into testa(a,b,c) values(v_idx,s_banks(v_idx).a2,s_banks(v_idx).a3);
  end loop;
end proc_bank_info_sync;
|
-- @DELIMITER ;
