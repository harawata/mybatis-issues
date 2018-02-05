--
--    Copyright 2009-2016 the original author or authors.
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

drop table person;

create table person (
  id int,
  name varchar(20),
  friend_id int
);

insert into person (id, name, friend_id) values (1, 'john', 2);
insert into person (id, name, friend_id) values (2, 'mary', 1);

-- @DELIMITER |

create or replace procedure single_curs(person_id in int, person_curs out sys_refcursor) is
begin
  open person_curs for select * from person where id = person_id;
end;
|

-- @DELIMITER ;
