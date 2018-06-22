CREATE DATABASE community_game_changer;

USE community_game_changer;

CREATE TABLE community_game_changer.user_account (
  `user_account_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY(`user_account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE community_game_changer.user_profile (
  `user_profile_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_account_id` int(11) NOT NULL,
  `card_id` varchar(255),
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `date_of_birth` varchar(255) NOT NULl,
  `gender` varchar(255) NOT NULl,
  `province_code` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `postal_code` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  CONSTRAINT `user_profile_ibfk_1` FOREIGN KEY (`user_account_id`) REFERENCES `user_account` (`user_account_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  PRIMARY KEY (`user_profile_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `user_profile` ADD FULLTEXT(`first_name`, `last_name`, `card_id`, `province_code`, `city`, `address`, `phone`, `email`, `postal_code`);

-- SELECT *,
--      MATCH ( `first_name`, `last_name`, `province_code`, `city`, `address`, `postal_code`, `phone`, `email`)
--      AGAINST ('+test' IN BOOLEAN MODE) as `rel`
-- FROM `user_profile`
-- WHERE MATCH ( `first_name`, `last_name`, `province_code`, `city`, `address`, `postal_code`, `phone`, `email`)
--      AGAINST ('+test' IN BOOLEAN MODE)
-- ORDER BY `rel` DESC

CREATE TABLE community_game_changer.family_member (
    `member_id` int(11) NOT NULL AUTO_INCREMENT,
    `user_profile_id` int(11) NOT NULL,
    `first_name` varchar(255) NOT NULL,
    `last_name` varchar(255) NOT NULL,
    `date_of_birth` varchar(255) NOT NULl,
    CONSTRAINT `family_member_ibfk_1` FOREIGN KEY (`user_profile_id`) REFERENCES `user_profile` (`user_profile_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE community_game_changer.business_account (
  `business_account_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY(`business_account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE community_game_changer.business_profile (
  `business_profile_id` int(11) NOT NULL AUTO_INCREMENT,
  `business_account_id` int(11) NOT NULL,
  `province_code` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `postal_code` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `business_name` varchar(255) NOT NULL,
  `main_contact` varchar(255) NOT NULL,
  CONSTRAINT `business_profile_ibfk_1` FOREIGN KEY (`business_account_id`) REFERENCES `business_account` (`business_account_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  PRIMARY KEY (`business_profile_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE community_game_changer.business_preference (
  `business_profile_id` int(11) NOT NULL,
  `preference_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `debit_percent` DECIMAL(3, 3) NOT NULL,
  `cash_percent` DECIMAL(3, 3) NOT NULL,
  `credit_percent` DECIMAL(3, 3) NOT NULL,
  `sale_clearance_percent` DECIMAL(3, 3) NOT NULL,
  CONSTRAINT `business_profile_ibfk_2` FOREIGN KEY (`business_profile_id`) REFERENCES `business_profile` (`business_profile_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  PRIMARY KEY (`preference_id`)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE community_game_changer.association_account(
  `association_account_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`association_account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE community_game_changer.sport_association (
  `association_id` int(11) NOT NULL,
  `province_code` varchar(255) NOT NULL,
  `community` varchar(255) NOT NULL,
  `sport` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `association_account_id` int(11),
  PRIMARY KEY (`association_id`),
  CONSTRAINT `sport_profile_ibfk_1` FOREIGN KEY (`association_account_id`) REFERENCES `association_account` (`association_account_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE community_game_changer.sport_association_team (
  `association_id` int(11) NOT NULL,
  `team_id` int(11) NOT NULL AUTO_INCREMENT,
  `division` varchar(255) NOT NULL,
  `gender` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `start_date` varchar(255),
  `end_date` varchar(255),
  PRIMARY KEY (`team_id`),
  CONSTRAINT `association_ibfk_1` FOREIGN KEY (`association_id`) REFERENCES `sport_association` (`association_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE community_game_changer.sport_association_players (
  `player_id` int(11) NOT NULL AUTO_INCREMENT,
  `team_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`player_id`),
  CONSTRAINT `player_ibfk_1` FOREIGN KEY (`team_id`) REFERENCES `sport_association_team` (`team_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE community_game_changer.charity_association (
  `association_id` int(11) NOT NULL,
  `province_code` varchar(255) NOT NULL,
  `community` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `association_account_id` int(11),
  PRIMARY KEY (`association_id`),
  CONSTRAINT `charity_profile_ibfk_1` FOREIGN KEY (`association_account_id`) REFERENCES `association_account` (`association_account_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE community_game_changer.nonprof_association (
  `association_id` int(11) NOT NULL,
  `province_code` varchar(255) NOT NULL,
  `community` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `association_account_id` int(11),
  PRIMARY KEY (`association_id`),
  CONSTRAINT `nonprof_profile_ibfk_1` FOREIGN KEY (`association_account_id`) REFERENCES `association_account` (`association_account_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE community_game_changer.user_association (
  `team_id` int(11),
  `association_id` int(11),
  `player_id` int(11),
  `charity_id` int(11),
  `nonprof_id` int(11),
  `user_association_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_profile_id` int(11) NOT NULL,
  `donation_amount` DECIMAL(3, 2) NOT NULL,
  `chairty_recipts` BOOLEAN DEFAULT 0,
  PRIMARY KEY (`user_association_id`),
  CONSTRAINT `user_association_ibfk_1` FOREIGN KEY (`association_id`) REFERENCES `sport_association` (`association_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_association_ibfk_2` FOREIGN KEY (`user_profile_id`) REFERENCES `user_profile` (`user_profile_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_association_ibfk_3` FOREIGN KEY (`team_id`) REFERENCES `sport_association_team` (`team_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_association_ibfk_4` FOREIGN KEY (`charity_id`) REFERENCES `charity_association` (`association_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_association_ibfk_5` FOREIGN KEY (`nonprof_id`) REFERENCES `nonprof_association` (`association_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_association_ibfk_6` FOREIGN KEY (`player_id`) REFERENCES `sport_association_players` (`player_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE community_game_changer.transaction (
  `transaction_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_profile_id` int(11) NOT NULL,
  `business_profile_id` int(11) NOT NULL,
  `total` DECIMAL(13,2),
  `precent_total` DECIMAL(13,2),
  `date_of_purchase` DATETIME NOT NULL,
  PRIMARY KEY (`transaction_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE community_game_changer.transaction_details(
  `transaction_details_id` int(11) NOT NULL AUTO_INCREMENT,
  `transaction_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `amount` DECIMAL(13,2) NOT NULL,
  `transaction_type` int(11) NOT NULL,
  `transaction_rate` DECIMAL(13,2) NOT NULL,
  `method_of_pyment` varchar(255) NOT NULL,
  `precent_amount` DECIMAL(13,2) NOT NULL,
  PRIMARY KEY (`transaction_details_id`),
  CONSTRAINT `transaction_details_ibfk_1` FOREIGN KEY (`transaction_id`) REFERENCES `transaction` (`transaction_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `transaction_details_ibfk_2` FOREIGN KEY (`transaction_type`) REFERENCES `business_preference` (`preference_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `business_account` (`username`, `password`) VALUES('test_business','$2a$10$nbDM8gwHuQ9l2QvR80bMguJJLE7vNGjkLOqCKoP6/pEuZmjv7CEjW');

INSERT INTO `business_profile` (`business_account_id`, `province_code`, `city`, `address`, `postal_code`, `phone`, `email`, `business_name`, `main_contact`) VALUES (LAST_INSERT_ID(), 'PE', 'Charlottetown', '94 watts ave', 's1s1s1', '(980) 123 1234', 'test@testing.com', 'Vishion Research', 'John Doe');

SET @business_profile_id = (SELECT LAST_INSERT_ID());

INSERT INTO `business_preference` (`business_profile_id`, `name`, `debit_percent`, `cash_percent`, `credit_percent`, `sale_clearance_percent`) VALUES (@business_profile_id, 'Clothing', 0.02, 0.02, 0.01, 0.03);
INSERT INTO `business_preference` (`business_profile_id`, `name`, `debit_percent`, `cash_percent`, `credit_percent`, `sale_clearance_percent`) VALUES (@business_profile_id, 'Convenience Store',0.02, 0.02, 0.01, 0.03);
INSERT INTO `business_preference` (`business_profile_id`, `name`, `debit_percent`, `cash_percent`, `credit_percent`, `sale_clearance_percent`) VALUES (@business_profile_id, 'Food & Beverage', 0.02, 0.02, 0.01, 0.03);
INSERT INTO `business_preference` (`business_profile_id`, `name`, `debit_percent`, `cash_percent`, `credit_percent`, `sale_clearance_percent`) VALUES (@business_profile_id, 'Furniture', 0.02, 0.02, 0.01, 0.03);
INSERT INTO `business_preference` (`business_profile_id`, `name`, `debit_percent`, `cash_percent`, `credit_percent`, `sale_clearance_percent`) VALUES (@business_profile_id, 'Gasoline & Fuel', 0.02, 0.02, 0.01, 0.03);
INSERT INTO `business_preference` (`business_profile_id`, `name`, `debit_percent`, `cash_percent`, `credit_percent`, `sale_clearance_percent`) VALUES (@business_profile_id, 'Hardware', 0.02, 0.02, 0.01, 0.03);
INSERT INTO `business_preference` (`business_profile_id`, `name`, `debit_percent`, `cash_percent`, `credit_percent`, `sale_clearance_percent`) VALUES (@business_profile_id, 'General Merchandise', 0.02, 0.02, 0.01, 0.03);
INSERT INTO `business_preference` (`business_profile_id`, `name`, `debit_percent`, `cash_percent`, `credit_percent`, `sale_clearance_percent`) VALUES (@business_profile_id, 'Professional Services', 0.02, 0.02, 0.01, 0.03);
INSERT INTO `business_preference` (`business_profile_id`, `name`, `debit_percent`, `cash_percent`, `credit_percent`, `sale_clearance_percent`) VALUES (@business_profile_id, 'Sale & Clearance', 0.02, 0.02, 0.01, 0.03);

INSERT INTO `user_account` (`user_account_id`, `username`, `password`) VALUES
(2, 'test_user', '$2a$10$ARQgZlDPu26AmJ5r6yeii.E.ZD9i5VVFyIK605WdGgM9f9vqOclm.');

--
-- Dumping data for table `user_profile`
--

INSERT INTO `user_profile` (`user_profile_id`, `user_account_id`, `card_id`, `first_name`, `last_name`, `date_of_birth`, `gender`, `province_code`, `city`, `address`, `postal_code`, `phone`, `email`) VALUES
(2, 2, 'A000200', 'Test', 'Tester', '17/06/2002', 'M', 'PE', 'CHARLOTTETOWN', '94 Watts ave', 's1s1s1', '(938) 383-8383', 'kjlds@kljfd.com');


INSERT INTO `association_account` (`username`, `password`) VALUES ('test_charity', '$2a$10$EZjGWaVZCOhQ3mLSjIcueuLBUkqp2OYWTxiugY9BdlnYyoA9dPD12');
INSERT INTO `charity_association` (`association_id`, `province_code`, `community`, `type`, `name`, `association_account_id`) VALUES ( 1, 'PEI', 'Charlottetown', 'charity', 'PEI Council of People with Disabilities', LAST_INSERT_ID());
INSERT INTO `charity_association` (`association_id`, `province_code`, `community`, `type`, `name`) VALUES ( 2, 'PEI', 'Charlottetown', 'charity', 'Sport Pei');
INSERT INTO `charity_association` (`association_id`, `province_code`, `community`, `type`, `name`) VALUES ( 3, 'PEI', 'Charlottetown', 'charity', 'Habitat For Humanity PEI');
INSERT INTO `charity_association` (`association_id`, `province_code`, `community`, `type`, `name`) VALUES ( 4, 'PEI', 'Charlottetown', 'charity', 'Alzheimer Society of PEI');
INSERT INTO `charity_association` (`association_id`, `province_code`, `community`, `type`, `name`) VALUES ( 5, 'PEI', 'Charlottetown', 'charity', 'Alzheimer Society of PEI');
INSERT INTO `charity_association` (`association_id`, `province_code`, `community`, `type`, `name`) VALUES ( 6, 'PEI', 'Summerside', 'charity', 'Family Service PE');
INSERT INTO `charity_association` (`association_id`, `province_code`, `community`, `type`, `name`) VALUES ( 7, 'PEI', 'Summerside', 'charity', 'Boys & Girls Club of Summerside');
INSERT INTO `charity_association` (`association_id`, `province_code`, `community`, `type`, `name`) VALUES ( 8, 'PEI', 'Summerside', 'charity', 'PEI Council of People with Disabilities');
INSERT INTO `charity_association` (`association_id`, `province_code`, `community`, `type`, `name`) VALUES ( 9, 'PEI', 'Summerside', 'charity', "Mi'kmaq Confederacy of PEI");
INSERT INTO `charity_association` (`association_id`, `province_code`, `community`, `type`, `name`) VALUES ( 10, 'PEI', 'Summerside', 'charity', "St. Paul's Parish Office");
INSERT INTO `charity_association` (`association_id`, `province_code`, `community`, `type`, `name`) VALUES ( 11, 'PEI', 'Kensington', 'charity', 'MADD');

INSERT INTO `association_account` (`username`, `password`) VALUES ('test_nonprof', '$2a$10$b6n/jwZ8fK2HSbxmDBRTTOmHAlUgurYteZ3Wa0uDVO7IWQPlmd2VC');
INSERT INTO `nonprof_association` (`association_id`, `province_code`, `community`, `type`, `name`, `association_account_id`) VALUES ( 1, 'PEI', 'Charlottetown', 'nonProf', 'Canadian Red Cross', LAST_INSERT_ID());
INSERT INTO `nonprof_association` (`association_id`, `province_code`, `community`, `type`, `name`) VALUES ( 2, 'PEI', 'Charlottetown', 'nonProf', 'L.M. Montgomery Land Trust Inc.');
INSERT INTO `nonprof_association` (`association_id`, `province_code`, `community`, `type`, `name`) VALUES ( 3, 'PEI', 'Charlottetown', 'nonProf', 'Boys & Girls Club of Charlottetown');
INSERT INTO `nonprof_association` (`association_id`, `province_code`, `community`, `type`, `name`) VALUES ( 4, 'PEI', 'Charlottetown', 'nonProf', 'PEI Humane Society');
INSERT INTO `nonprof_association` (`association_id`, `province_code`, `community`, `type`, `name`) VALUES ( 5, 'PEI', 'Summerside', 'nonProf', 'Big Brothers-Big Sisters Of PEI');
INSERT INTO `nonprof_association` (`association_id`, `province_code`, `community`, `type`, `name`) VALUES ( 6, 'PEI', 'Summerside', 'nonProf', 'Summerside Port Corporation Inc.');
INSERT INTO `nonprof_association` (`association_id`, `province_code`, `community`, `type`, `name`) VALUES ( 7, 'PEI', 'Summerside', 'nonProf', 'The Silver Fox Entertainment Complex');

INSERT INTO `association_account` (`username`, `password`) VALUES ('test_sport', '$2a$10$YY3rsQ5khgOWGPDCiKJObujJykyYoXRJKQwzyDt94heii7HBZ8mIK');
INSERT INTO `sport_association` (`association_id`, `province_code`, `community`, `sport`, `name`, `association_account_id`) VALUES ( 1, 'PEI', 'Charlottetown', 'Hockey', 'Charlottetown Minor Hockey Association', LAST_INSERT_ID());
INSERT INTO `sport_association_team` (`team_id`, `association_id`, `division`, `gender`, `name`) VALUES (1, 1, 'Initiation', 'M', 'Initiation Program Sharks');
INSERT INTO `sport_association_players` (`team_id`, `name`) VALUES (1, 'Larry D. Espinosa');
INSERT INTO `sport_association_players` (`team_id`, `name`) VALUES (1, 'Brad L. Smith');
INSERT INTO `sport_association_players` (`team_id`, `name`) VALUES (1, 'James M. Vincent');
INSERT INTO `sport_association_players` (`team_id`, `name`) VALUES (1, 'Eduardo S. McCracken');
INSERT INTO `sport_association_players` (`team_id`, `name`) VALUES (1, 'John M. Eskew');
INSERT INTO `sport_association_players` (`team_id`, `name`) VALUES (1, 'Edwin V. Williams');
INSERT INTO `sport_association_players` (`team_id`, `name`) VALUES (1, 'Chris K. Hock');
INSERT INTO `sport_association_players` (`team_id`, `name`) VALUES (1, 'Roland M. Barros');
INSERT INTO `sport_association_players` (`team_id`, `name`) VALUES (1, 'Michael L. Scott');
INSERT INTO `sport_association_players` (`team_id`, `name`) VALUES (1, 'William B. Fabian');
INSERT INTO `sport_association_players` (`team_id`, `name`) VALUES (1, 'Walter S. Stokes');
INSERT INTO `sport_association_players` (`team_id`, `name`) VALUES (1, 'Chad S. Foster');

INSERT INTO `sport_association_team` (`team_id`, `association_id`, `division`, `gender`, `name`) VALUES (2, 1, 'Initiation', 'M', 'Initiation Program Jets');
INSERT INTO `sport_association_players` (`team_id`, `name`) VALUES (2, 'Chad S. Foster');
INSERT INTO `sport_association_players` (`team_id`, `name`) VALUES (2, 'Jason P. Friend');
INSERT INTO `sport_association_players` (`team_id`, `name`) VALUES (2, 'Leslie S. Young');
INSERT INTO `sport_association_players` (`team_id`, `name`) VALUES (2, "Juan B. O'Donnell");
INSERT INTO `sport_association_players` (`team_id`, `name`) VALUES (2, 'Donald A. Stone');
INSERT INTO `sport_association_players` (`team_id`, `name`) VALUES (2, 'Nicholas B. Crumley');
INSERT INTO `sport_association_players` (`team_id`, `name`) VALUES (2, 'John C. Bergstrom');
INSERT INTO `sport_association_players` (`team_id`, `name`) VALUES (2, 'Elbert B. Patton');
INSERT INTO `sport_association_players` (`team_id`, `name`) VALUES (2, 'Edwin V. Williams');
INSERT INTO `sport_association_players` (`team_id`, `name`) VALUES (2, 'Chris K. Hock');
INSERT INTO `sport_association_players` (`team_id`, `name`) VALUES (2, 'Roland M. Barros');
INSERT INTO `sport_association_players` (`team_id`, `name`) VALUES (2, 'Michael L. Scott');

INSERT INTO `sport_association_team` (`team_id`, `association_id`, `division`, `gender`, `name`) VALUES (3, 1, 'Initiation', 'M', 'Initiation Program Senators');
INSERT INTO `sport_association_players` (`team_id`, `name`) VALUES (3, 'Leslie S. Young');
INSERT INTO `sport_association_players` (`team_id`, `name`) VALUES (3, "Juan B. O'Donnell");
INSERT INTO `sport_association_players` (`team_id`, `name`) VALUES (3, 'Donald A. Stone');
INSERT INTO `sport_association_players` (`team_id`, `name`) VALUES (3, 'Nicholas B. Crumley');
INSERT INTO `sport_association_players` (`team_id`, `name`) VALUES (3, 'Larry D. Espinosa');
INSERT INTO `sport_association_players` (`team_id`, `name`) VALUES (3, 'Brad L. Smith');
INSERT INTO `sport_association_players` (`team_id`, `name`) VALUES (3, 'James M. Vincent');
INSERT INTO `sport_association_players` (`team_id`, `name`) VALUES (3, 'Eduardo S. McCracken');
INSERT INTO `sport_association_players` (`team_id`, `name`) VALUES (3, 'Roland M. Barros');
INSERT INTO `sport_association_players` (`team_id`, `name`) VALUES (3, 'Michael L. Scott');

INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (1, 'Initiation', 'M', 'Initiation Program Penguins');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (1, 'Novice', 'M', 'Novice AA');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (1, 'Novice', 'M', 'Novice A Aqua');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (1, 'Novice', 'M', 'Novice A Blue');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (1, 'Novice', 'M', 'Novice A Red');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (1, 'Novice', 'M', 'Novice A Black');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (1, 'Novice', 'M', 'Novice A Orange Crush');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (1, 'Atom', 'M', 'Atom AAA');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (1, 'Atom', 'M', 'Atom AA');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (1, 'Atom', 'M', 'Atom A');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (1, 'PeeWee', 'M', 'PeeWee AAA');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (1, 'PeeWee', 'M', 'PeeWee AA');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (1, 'PeeWee', 'M', 'PeeWee A');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (1, 'Bantam', 'M', 'Centeral Attack Bantam AAA');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (1, 'Bantam', 'M', 'Bantam AA');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (1, 'Bantam', 'M', 'Bantam A');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (1, 'Midget', 'M', 'Midget AAA');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (1, 'Midget', 'M', 'Midget AA');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (1, 'Midget', 'M', 'Midget A1');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (1, 'Midget', 'M', 'Midget A2');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (1, 'Female', 'F', 'Midget A Female');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (1, 'Female', 'F', 'Pee Wee A Female');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (1, 'Female', 'F', 'Novice A Female');

INSERT INTO `sport_association` (`association_id`, `province_code`, `community`, `sport`, `name`) VALUES ( 2, 'PEI', 'Sherwood', 'Hockey', 'Sherwood Parkdale Minor Hockey Association');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (2, 'Pre-Novice', 'M', 'Pre-Novice Flyers');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (2, 'Pre-Novice', 'M', 'Pre-Novice Kings');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (2, 'Pre-Novice', 'M', 'Pre-Novice Oilers');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (2, 'Pre-Novice', 'M', 'Pre-Novice Flames');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (2, 'Novice', 'M', 'Novice Bruins');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (2, 'Novice', 'M', 'Novice Canadiens');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (2, 'Novice', 'M', 'Novice Hawks');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (2, 'Novice', 'M', 'Novice Sharks');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (2, 'Novice', 'M', 'Novice AA');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (2, 'Atom', 'M', 'Atom A Team 1');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (2, 'Atom', 'M', 'Atom A Team 2');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (2, 'Atom', 'M', 'Atom A Team 3');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (2, 'Atom', 'M', 'Atom AA');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (2, 'Atom', 'M', 'Atom AAA');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (2, 'Atom', 'M', 'Atom Capital District Atom AA Cyclones');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (2, 'Atom', 'F', 'Atom Centeral Storm Atom AA Female');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (2, 'PeeWee', 'M', 'PeeWee 1');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (2, 'PeeWee', 'M', 'PeeWee 2');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (2, 'PeeWee', 'M', 'PeeWee AA');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (2, 'PeeWee', 'M', 'PeeWee AAA');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (2, 'PeeWee', 'F', 'Centeral Storm PeeWee AAA Female');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (2, 'Bantom', 'M', 'Bantom 1');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (2, 'Bantom', 'M', 'Bantom 2');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (2, 'Bantom', 'M', 'Bantom 3');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (2, 'Bantom', 'M', 'Centeral Attack Bantom AA');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (2, 'Bantom', 'F', 'Centeral Storm Bantom AAA Female');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (2, 'Bantom', 'F', 'Centeral Storm Bantom AAA Female');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (2, 'Midget', 'M', 'Midget 1');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (2, 'Midget', 'M', 'Midget 2');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (2, 'Midget', 'M', 'Centeral Attack Midget AAA');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (2, 'Midget', 'F', 'Centeral Storm Midget AAA Female');


INSERT INTO `sport_association` (`association_id`, `province_code`, `community`, `sport`, `name`) VALUES ( 3, 'PEI', 'Summerside', 'Hockey', 'Summerside and Area Minor Hockey Association');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (3, 'Midget', 'F', 'Wildcats Midget AAA');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (3, 'Midget', 'F', 'Midget A');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (3, 'Bantam', 'F', 'Western Wind Bantam AAA');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (3, 'Bantam', 'F', 'Bantam A Wildcats');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (3, 'PeeWee', 'F', 'PeeWee A Wildcats');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (3, 'Atom', 'F', 'Wildcats Atom AAA');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (3, 'Atom', 'F', 'Atom A Wildcats');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (3, 'Midget', 'M', 'Midget AAA');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (3, 'Midget', 'M', 'Midget AA');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (3, 'Midget', 'M', 'Midget A Team 1');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (3, 'Midget', 'M', 'Midget A Team 2');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (3, 'Bantam', 'M', 'Bantam AAA Prince County Warriors');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (3, 'Bantam', 'M', 'Bantam AA');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (3, 'Bantam', 'M', 'Bantam A Team 1');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (3, 'Bantam', 'M', 'Bantam A Team 2');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (3, 'PeeWee', 'M', 'PeeWee AAA');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (3, 'PeeWee', 'M', 'PeeWee AA');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (3, 'PeeWee', 'M', 'PeeWee A Team 1');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (3, 'PeeWee', 'M', 'PeeWee A Team 2');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (3, 'Atom', 'M', 'Atom AAA');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (3, 'Atom', 'M', 'Atom AA');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (3, 'Atom', 'M', 'Atom A Team 1');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (3, 'Atom', 'M', 'Atom A Team 2');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (3, 'Atom', 'M', 'Atom A Team 3');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (3, 'Atom', 'M', 'Atom A Team 4');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (3, 'Novice', 'M', 'Novice AA');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (3, 'Novice', 'M', 'Novice A Team 1');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (3, 'Novice', 'M', 'Novice A Team 2');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (3, 'Novice', 'M', 'Novice A Team 3');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (3, 'Novice', 'M', 'Novice A Team 4');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (3, 'Skills', 'M', 'Skills First A');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (3, 'Skills', 'M', 'Skills First B');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (3, 'Novice', 'M', 'Novice A Tournament');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (3, 'Novice', 'M', 'Novice AA Province');

INSERT INTO `sport_association` (`association_id`, `province_code`, `community`, `sport`, `name`) VALUES ( 4, 'PEI', 'Pownal', 'Hockey', 'Pownal Minor Hockey Association');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (4, 'Initiation', 'F', 'Initiation Female');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (4, 'Initiation', 'M', 'Initiation Grey');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (4, 'Initiation', 'M', 'Initiation Blue');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (4, 'Initiation', 'M', 'Initiation Green');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (4, 'Initiation', 'M', 'Initiation White');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (4, 'Initiation', 'M', 'Initiation Yellow');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (4, 'Initiation', 'M', 'Initiation Red');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (4, 'Initiation', 'M', 'Initiation Orange');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (4, 'Initiation', 'M', 'Initiation Black');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (4, 'Novice', 'M', 'Novice AA Team 1');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (4, 'Novice', 'M', 'Novice AA Team 2');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (4, 'Novice', 'M', 'Novice A Team 1');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (4, 'Novice', 'M', 'Novice A Team 2');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (4, 'Novice', 'M', 'Novice A Team 3');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (4, 'Novice', 'M', 'Novice A Team 4');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (4, 'Novice', 'M', 'Novice A Team 5');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (4, 'Novice', 'F', 'Novice A Female Team 1');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (4, 'Novice', 'F', 'Novice A Female Team 2');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (4, 'Atom', 'M', 'Atom AAA');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (4, 'Atom', 'M', 'Atom AA Team 1');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (4, 'Atom', 'M', 'Atom AA Team 2');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (4, 'Atom', 'M', 'Atom A Team 1');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (4, 'Atom', 'M', 'Atom A Team 2');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (4, 'Atom', 'M', 'Atom A Team 3');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (4, 'Atom', 'F', 'Atom AAA Female');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (4, 'Atom', 'F', 'Atom A Female');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (4, 'PeeWee', 'M', 'PeeWee AAA');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (4, 'PeeWee', 'M', 'PeeWee AA Team 1');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (4, 'PeeWee', 'M', 'PeeWee AA Team 2');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (4, 'PeeWee', 'M', 'PeeWee A Team 1');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (4, 'PeeWee', 'M', 'PeeWee A Team 2');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (4, 'PeeWee', 'M', 'PeeWee A Team 3');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (4, 'PeeWee', 'F', 'PeeWee AAA Female');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (4, 'PeeWee', 'F', 'PeeWee A Female');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (4, 'Bantam', 'M', 'Bantam AA');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (4, 'Bantam', 'M', 'Bantam A Team 1');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (4, 'Bantam', 'M', 'Bantam A Team 2');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (4, 'Bantam', 'M', 'Bantam A Team 3');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (4, 'Bantam', 'M', 'Bantam A Team 4');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (4, 'Bantam', 'F', 'Bantam AAA Female - Centeral Store');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (4, 'Bantam', 'F', 'Bantam A Female');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (4, 'Midget', 'M', 'Midget AA');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (4, 'Midget', 'M', 'Midget A Team 1');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (4, 'Midget', 'M', 'Midget A Team 2');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (4, 'Midget', 'M', 'Midget A Pownal 3');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (4, 'Midget', 'F', 'Midget AAA Female - Centeral Storm');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (4, 'Midget', 'F', 'Midget A Female');

INSERT INTO `sport_association` (`association_id`, `province_code`, `community`, `sport`, `name`) VALUES ( 5, 'PEI', 'Charlottetown', 'Rignette', 'Charlottetown Ringette');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (5, 'U8', 'M', 'U8 Team 1');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (5, 'U8', 'M', 'U8 Team 2');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (5, 'U8', 'M', 'U8 Team 3');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (5, 'U8', 'M', 'U8 Team 4');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (5, 'U8', 'M', 'U8 Team 5');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (5, 'U10', 'M', 'U10 Team 1');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (5, 'U10', 'M', 'U10 Team 2');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (5, 'U10', 'M', 'U10 Team 3');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (5, 'U10', 'M', 'U10 Team 4');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (5, 'U10', 'M', 'U10 Team 5');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (5, 'U10', 'M', 'U10 Team 6');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (5, 'U10', 'M', 'U10 Team 7');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (5, 'U12', 'M', 'U12 Team 1');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (5, 'U12', 'M', 'U12 Team 2');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (5, 'U12', 'M', 'U12 Team 3');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (5, 'U12', 'M', 'U12 Team 4');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (5, 'U12', 'M', 'U12 Team 5');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (5, 'U12', 'M', 'U12 Team 6');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (5, 'U14', 'M', 'U14 Team 1');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (5, 'U14', 'M', 'U14 Team 2');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (5, 'U14', 'M', 'U14 Team 3');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (5, 'U14', 'M', 'U14 Team 4');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (5, 'U14', 'M', 'U14 Team 5');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (5, 'U16', 'M', 'U16 Team 1');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (5, 'U16', 'M', 'U16 Team 2');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (5, 'U16', 'M', 'U16 Team 3');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (5, 'U16', 'M', 'U16 Team 4');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (5, 'U16', 'M', 'U16 Team 5');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (5, 'U16', 'M', 'U16 Team 6');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (5, 'U19', 'M', 'U19 Team 1');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (5, 'U19', 'M', 'U19 Team 2');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (5, 'U19', 'M', 'U19 Team 3');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (5, 'U19', 'M', 'U19 Team 4');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (5, 'U19', 'M', 'U19 Team 5');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (5, 'U19', 'M', 'U19 Team 6');

INSERT INTO `sport_association` (`association_id`, `province_code`, `community`, `sport`, `name`) VALUES ( 6, 'PEI', 'Souris', 'Rignette', 'Souris Ringette');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (6, 'U8', 'M', 'U8 Souris Ring Rockets');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (6, 'U8', 'F', 'U8 Souris Ice Queens');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (6, 'U10', 'M', 'U10 Souris Tornadoes');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (6, 'U10', 'F', 'U10 Souris Shooting Stars');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (6, 'U12', 'M', 'U12 Souris Slamm');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (6, 'U12', 'F', 'U12 Souris Stealers');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (6, 'U14', 'M', 'U14 Souris Senators');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (6, 'U14', 'F', 'U14 Souris Cyclones');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (6, 'U16', 'M', 'U16 Souris Savers');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (6, 'U19', 'F', 'U19 Souris Savers');

INSERT INTO `sport_association` (`association_id`, `province_code`, `community`, `sport`, `name`) VALUES ( 7, 'PEI', 'Summerside', 'Rignette', 'Summerside Ringette');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (7, 'U8', 'M', 'U8');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (7, 'U10', 'M', 'U10 Storm');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (7, 'U12', 'M', 'U12 Storm');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (7, 'U14', 'M', 'U14 Storm');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (7, 'U16', 'M', 'U16 Storm');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (7, 'U19', 'M', 'U19 Storm');

INSERT INTO `sport_association` (`association_id`, `province_code`, `community`, `sport`, `name`) VALUES ( 8, 'PEI', 'Charlottetown', 'Soccer', 'Winsloe Charlottetown Royals FC');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (8, 'U5', 'M', 'U5 Simmons');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (8, 'U5', 'M', 'U5 Winsloe');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (8, 'U5', 'M', 'U5 Team 7');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (8, 'U5', 'M', 'U5 Team 8');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (8, 'U7', 'F', 'U7 Purple Simmons');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (8, 'U7', 'F', 'U7 Red Simmons');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (8, 'U7', 'F', 'U7 Green Simmons');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (8, 'U7', 'F', 'U7 Grey WWR');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (8, 'U7', 'F', 'U7 Burgendy WWR');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (8, 'U7', 'F', 'U7 White WWR');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (8, 'U7', 'M', 'U7 Royal Blue WWR');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (8, 'U7', 'M', 'U7 Burgendy Simmons');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (8, 'U7', 'M', 'U7 Blue Simmons');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (8, 'U7', 'M', 'U7 Simmons');
INSERT INTO `sport_association_team` (`association_id`, `division`, `gender`, `name`) VALUES (8, 'U7', 'M', 'U7 Winsloe');

--
-- Dumping data for table `user_association`
--

INSERT INTO `user_association` (`team_id`, `association_id`, `player_id`, `charity_id`, `nonprof_id`, `user_association_id`, `user_profile_id`, `donation_amount`, `chairty_recipts`) VALUES
(1, 1, NULL, NULL, NULL, 5, 2, '0.30', NULL),
(NULL, NULL, NULL, 1, NULL, 6, 2, '0.30', 1),
(NULL, NULL, NULL, NULL, 1, 7, 2, '0.30', NULL),
(NULL, NULL, NULL, 4, NULL, 8, 2, '0.10', 0);
