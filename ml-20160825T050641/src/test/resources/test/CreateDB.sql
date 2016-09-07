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

drop table if exists unit;

create table unit (
id INT,
name VARCHAR(32),
rate DOUBLE,
subunit_name VARCHAR(32),
is_exception BIT,
unit_type INT,
type1_price INT,
type2_price INT
);

insert into unit
(id, name, rate, subunit_name, is_exception, unit_type, type1_price, type2_price)
values
(1, 'Unit 1', 1.23, 'Module A', 1, 1, 1500, null),
(2, 'Unit 2', 1.41, 'Module B', 0, 1, 300, null),
(3, 'Unit 3', 7.89, 'Module C', 1, 2, null, 1200);
