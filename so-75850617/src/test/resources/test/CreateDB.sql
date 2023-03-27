DROP TABLE IF EXISTS `event`;

CREATE TABLE `event` (
    `id` INTEGER PRIMARY KEY,
    `time` DATETIME NOT NULL,
    `duration` INT NOT NULL,
    `summary_id` INT NOT NULL
);
