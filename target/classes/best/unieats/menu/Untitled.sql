CREATE TABLE `users` (
  `userid` integer PRIMARY KEY,
  `name` varchar(255),
  `lastname` varchar(255),
  `email` varchar(255),
  `address` varchar(255),
  `username` varchar(255),
  `password` varchar(255),
  `role` int
);

CREATE TABLE `menu` (
  `menuid` interger PRIMARY KEY,
  `dishid` integer,
  `date` date
);

CREATE TABLE `dish` (
  `dishid` integer PRIMARY KEY,
  `name` varchar(255),
  `description` varchar(255),
  `price` float,
  `quantity` integer
);

CREATE TABLE `basket` (
  `basketid` integer PRIMARY KEY,
  `userid` integer,
  `price` float,
  `status` integer
);

CREATE TABLE `dishinbasket` (
  `basketid` integer,
  `dishid` integer,
  PRIMARY KEY (`basketid`, `dishid`)
);

CREATE TABLE `order` (
  `orderid` intiger PRIMARY KEY,
  `basketid` integer,
  `date` date,
  `status` integer
);

CREATE TABLE `delivery` (
  `deliveryid` varchar(255) PRIMARY KEY,
  `basketid` integer,
  `date` date,
  `status` integer
);

ALTER TABLE `basket` ADD FOREIGN KEY (`userid`) REFERENCES `users` (`userid`);

ALTER TABLE `menu` ADD FOREIGN KEY (`dishid`) REFERENCES `dish` (`dishid`);

ALTER TABLE `order` ADD FOREIGN KEY (`basketid`) REFERENCES `basket` (`basketid`);

ALTER TABLE `dishinbasket` ADD FOREIGN KEY (`basketid`) REFERENCES `basket` (`basketid`);

ALTER TABLE `dishinbasket` ADD FOREIGN KEY (`dishid`) REFERENCES `dish` (`dishid`);

ALTER TABLE `delivery` ADD FOREIGN KEY (`basketid`) REFERENCES `basket` (`basketid`);
