--
--    Copyright 2019 the original author or authors.
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

drop table if exists `test_1`;

CREATE TABLE `test_1` (
  `账单号` varchar(255) COMMENT 'null',
  `账单号.1` varchar(255) DEFAULT NULL COMMENT 'null'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `test_1` VALUES ('0','0');
