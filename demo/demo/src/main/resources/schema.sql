drop table USER if exists;
drop table TRANSACTION_RECORD if exists;

CREATE TABLE USER
(
    `id`       bigint NOT NULL comment 'ID ',
    `username` varchar(50) comment 'USER_NAME',
    `password` varchar(50) comment 'PASSWORD',
    `balance`  double default null,
    `token`    varchar(50) comment 'Authentication Token',
    PRIMARY KEY (`id`)
);

CREATE TABLE TRANSACTION_RECORD
(
    `id`                 int NOT NULL AUTO_INCREMENT,
    `username`           varchar(50) default null,
    `transaction_time`   TIMESTAMP   DEFAULT now(),
    `old_balance`        double      default null,
    `transaction_amount` double      default null,
    `new_balance`        double      default null,
    `target_account`     varchar(50) default null,
    `transaction_type`   varchar(50) default null,
    PRIMARY KEY (`id`)
);