drop table if exists user_role;
CREATE TABLE `user_role` (
                        `RID` int(11) NOT NULL AUTO_INCREMENT,
                        `UID` int(11) DEFAULT NULL,
                        PRIMARY KEY (`RID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;