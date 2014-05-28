-- MySQL dump 10.13  Distrib 5.1.41, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: clahrc2
-- ------------------------------------------------------
-- Server version	5.1.41-3ubuntu12.7

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `clahrc2`
--

/*!40000 DROP DATABASE IF EXISTS `clahrc2`*/;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `clahrc2` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `clahrc2`;

--
-- User account owning 'clahrc2' DB
--
CREATE USER 'clahrc'@'localhost' IDENTIFIED BY 'clahrc';
GRANT ALL PRIVILEGES ON clahrc2 . * TO 'clahrc'@'localhost';

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Project_ID` int(11) NOT NULL,
  `Event_Date` date NOT NULL,
  `Comment` varchar(1000) DEFAULT NULL,
  `User_ID` int(11) NOT NULL,
  `Rc_Timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  KEY `fk_comment_project_id` (`Project_ID`),
  KEY `fk_comment_user_id` (`User_ID`),
  CONSTRAINT `fk_comment_project_id` FOREIGN KEY (`Project_ID`) REFERENCES `projects` (`ID`),
  CONSTRAINT `fk_comment_user_id` FOREIGN KEY (`User_ID`) REFERENCES `users` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `login_audit`
--

DROP TABLE IF EXISTS `login_audit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `login_audit` (
  `User_ID` int(11) NOT NULL,
  `Session_ID` varchar(45) NOT NULL,
  `IP_Address` varchar(15) NOT NULL,
  `LoggedIn_DateTime` datetime NOT NULL,
  `LoggedOut_DateTime` datetime DEFAULT NULL,
  `SessionExpired_DateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`User_ID`,`Session_ID`,`IP_Address`,`LoggedIn_DateTime`),
  UNIQUE KEY `uk_login_audit_session_id` (`Session_ID`),
  CONSTRAINT `fk_login_audit_user_id` FOREIGN KEY (`User_ID`) REFERENCES `users` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `milestone`
--

DROP TABLE IF EXISTS `milestone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `milestone` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Milestone_Date` date NOT NULL,
  `Label` varchar(45) DEFAULT NULL,
  `Description` varchar(45) DEFAULT NULL,
  `Milestone_Type` varchar(45) NOT NULL,
  `Rc_Timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pdsa`
--

DROP TABLE IF EXISTS `pdsa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pdsa` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Project_ID` int(11) NOT NULL,
  `Event_Date` date NOT NULL,
  `Description` varchar(5000) DEFAULT NULL,
  `Do_Description` varchar(5000) DEFAULT NULL,
  `Study_Description` varchar(5000) DEFAULT NULL,
  `Act_Description` varchar(5000) DEFAULT NULL,
  `Plan_Team` varchar(5000) DEFAULT NULL,
  `Study_Team` varchar(5000) DEFAULT NULL,
  `Do_Team` varchar(5000) DEFAULT NULL,
  `Act_Team` varchar(5000) DEFAULT NULL,
  `Do_Date` date DEFAULT NULL,
  `Study_Date` date DEFAULT NULL,
  `Act_Date` date DEFAULT NULL,
  `Cycle_Title` varchar(2000) DEFAULT NULL,
  `User_ID` int(11) NOT NULL,
  `Rc_Timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  KEY `fk_pdsa_project_id` (`Project_ID`),
  KEY `fk_pdsa_user_id` (`User_ID`),
  CONSTRAINT `fk_pdsa_project_id` FOREIGN KEY (`Project_ID`) REFERENCES `projects` (`ID`),
  CONSTRAINT `fk_pdsa_user_id` FOREIGN KEY (`User_ID`) REFERENCES `users` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ppi`
--

DROP TABLE IF EXISTS `ppi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ppi` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Entry_Date` date NOT NULL,
  `Role` varchar(45) NOT NULL,
  `Receptive` varchar(200) NOT NULL,
  `Difference` varchar(200) NOT NULL,
  `Plans` varchar(200) NOT NULL,
  `Comments` varchar(1000) NOT NULL,
  `User_ID` int(11) NOT NULL,
  `Project_ID` int(11) NOT NULL,
  `Rc_Timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  KEY `fk_ppi_project_id` (`Project_ID`),
  KEY `fk_ppi_user_id` (`User_ID`),
  CONSTRAINT `fk_ppi_project_id` FOREIGN KEY (`Project_ID`) REFERENCES `projects` (`ID`),
  CONSTRAINT `fk_ppi_user_id` FOREIGN KEY (`User_ID`) REFERENCES `users` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `project_tables`
--

DROP TABLE IF EXISTS `project_tables`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_tables` (
  `Project_ID` int(11) NOT NULL,
  `Table_Name` varchar(45) NOT NULL,
  `Table_Label` varchar(45) NOT NULL,
  PRIMARY KEY (`Project_ID`,`Table_Name`),
  UNIQUE KEY `uk_project_tables_table_name` (`Table_Name`),
  UNIQUE KEY `uk_project_tables_table_label` (`Table_Label`),
  KEY `fk_project_tables_project_id` (`Project_ID`),
  CONSTRAINT `fk_project_tables_project_id` FOREIGN KEY (`Project_ID`) REFERENCES `projects` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `project_tables` WRITE;
/*!40000 ALTER TABLE `project_tables` DISABLE KEYS */;
INSERT INTO `project_tables` VALUES 
(1,'clahrc_wish','clahrc_wish');
/*!40000 ALTER TABLE `project_tables` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `projects`
--

DROP TABLE IF EXISTS `projects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `projects` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) NOT NULL,
  `Start_Date` date DEFAULT NULL,
  `Implementation_Start_Date` date DEFAULT NULL,
  `End_Date` date DEFAULT NULL,
  `Short_Name` varchar(45) NOT NULL,
  `Description` varchar(10000) DEFAULT NULL,
  `Host_Organization` varchar(45) DEFAULT NULL,
  `Site_Structure` varchar(45) DEFAULT NULL,
  `Type` varchar(45) DEFAULT NULL,
  `Active` tinyint(1) NOT NULL,
  `Grouping_ID` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `uk_projects_name` (`Name`),
  UNIQUE KEY `uk_projects_short_name` (`Short_Name`),
  KEY `fk_projects_grouping_id` (`Grouping_ID`),
  CONSTRAINT `fk_projects_grouping_id` FOREIGN KEY (`Grouping_ID`) REFERENCES `project_groupings` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `projects` WRITE;
/*!40000 ALTER TABLE `projects` DISABLE KEYS */;
INSERT INTO `projects` VALUES 
(1,'CLAHRC_WISH',NULL,'2011-11-04',NULL,'CLAHRC_WISH','Dummy project to make top admin roles work - DO NOT DELETE.',NULL,NULL,NULL,0,'none');
/*!40000 ALTER TABLE `projects` ENABLE KEYS */;
UNLOCK TABLES;
--
-- Table structure for table `project_groupings`
--

DROP TABLE IF EXISTS `project_groupings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_groupings` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) NOT NULL,
  `Description` varchar(10000) DEFAULT NULL,
  `Theme` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `uk_project_groupings_name` (`Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `project_groupings` WRITE;
/*!40000 ALTER TABLE `project_groupings` DISABLE KEYS */;
INSERT INTO `project_groupings` VALUES 
(1,'none','Dummy grouping to make the WISH admin project work - DO NOT DELETE.','Administration');
/*!40000 ALTER TABLE `project_groupings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) NOT NULL,
  `Description` varchar(10000) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `uk_roles_name` (`Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES 
(1,'system_administrator','Top level admin role superseding all other roles.'),
(2,'programme_administrator','WRT admin role superseding project_admin role.'),
(3,'programme_lead','Programme user role for users responsible for those coordinating a number of projects. Higher access rights than project admin.'),
(4,'project_administrator','Project admin role superseding project_member role.'),
(5,'information_lead','Project user role for the local information lead. Higher access rights for managing project data.'),
(6,'project_member','Project user role for regular users.'),
(7,'programme_evaluator','Programme user role for users only entitled to view data and reports.'),
(8,'project_viewer','Project user role for users only entitled to view data and reports.');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;
--
-- Table structure for table `sustainability`
--

DROP TABLE IF EXISTS `sustainability`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sustainability` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Project_ID` int(11) NOT NULL,
  `Event_Date` date NOT NULL,
  `Score_1` int(11) NOT NULL,
  `Score_2` int(11) NOT NULL,
  `Score_3` int(11) NOT NULL,
  `Score_4` int(11) NOT NULL,
  `Score_5` int(11) NOT NULL,
  `Score_6` int(11) NOT NULL,
  `Score_7` int(11) NOT NULL,
  `Score_8` int(11) NOT NULL,
  `Score_9` int(11) NOT NULL,
  `Score_10` int(11) NOT NULL,
  `Comment` varchar(1000) DEFAULT NULL,
  `User_ID` int(11) NOT NULL,
  `Rc_Timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  KEY `fk_sustainability_project_id` (`Project_ID`),
  KEY `fk_sustainability_user_id` (`User_ID`),
  CONSTRAINT `fk_sustainability_project_id` FOREIGN KEY (`Project_ID`) REFERENCES `projects` (`ID`),
  CONSTRAINT `fk_sustainability_user_id` FOREIGN KEY (`User_ID`) REFERENCES `users` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_messages`
--

DROP TABLE IF EXISTS `user_messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_messages` (
  `User_ID` int(11) NOT NULL,
  `Project_ID` int(11) NOT NULL,
  `Message` varchar(1000) NOT NULL,
  `Comment_ID` int(11) DEFAULT NULL,
  `Rc_Timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`User_ID`,`Project_ID`,`Rc_Timestamp`),
  KEY `fk_user_messages_project_id` (`Project_ID`),
  KEY `fk_user_messages_user_id` (`User_ID`),
  KEY `fk_user_messages_comment_id` (`Comment_ID`),
  CONSTRAINT `fk_user_messages_comment_id` FOREIGN KEY (`Comment_ID`) REFERENCES `comment` (`ID`) ON DELETE SET NULL,
  CONSTRAINT `fk_user_messages_project_id` FOREIGN KEY (`Project_ID`) REFERENCES `projects` (`ID`),
  CONSTRAINT `fk_user_messages_user_id` FOREIGN KEY (`User_ID`) REFERENCES `users` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_project_role_requests`
--

DROP TABLE IF EXISTS `user_project_role_requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_project_role_requests` (
  `User_ID` int(11) NOT NULL,
  `Project_ID` int(11) NOT NULL,
  `Role_ID` int(11) NOT NULL,
  `Rc_Timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`User_ID`,`Project_ID`),
  KEY `fk_user_project_role_requests_user_id` (`User_ID`),
  KEY `fk_user_project_role_requests_project_id` (`Project_ID`),
  KEY `fk_user_project_role_requests_role_id` (`Role_ID`),
  CONSTRAINT `fk_user_project_role_requests_project_id` FOREIGN KEY (`Project_ID`) REFERENCES `projects` (`ID`),
  CONSTRAINT `fk_user_project_role_requests_role_id` FOREIGN KEY (`Role_ID`) REFERENCES `roles` (`ID`),
  CONSTRAINT `fk_user_project_role_requests_user_id` FOREIGN KEY (`User_ID`) REFERENCES `users` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_project_roles`
--

DROP TABLE IF EXISTS `user_project_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_project_roles` (
  `User_ID` int(11) NOT NULL,
  `Project_ID` int(11) NOT NULL,
  `Role_ID` int(11) NOT NULL,
  PRIMARY KEY (`User_ID`,`Project_ID`),
  KEY `fk_user_project_roles_user_id` (`User_ID`),
  KEY `fk_user_project_roles_project_id` (`Project_ID`),
  KEY `fk_user_project_roles_role_id` (`Role_ID`),
  CONSTRAINT `fk_user_project_roles_project_id` FOREIGN KEY (`Project_ID`) REFERENCES `projects` (`ID`),
  CONSTRAINT `fk_user_project_roles_role_id` FOREIGN KEY (`Role_ID`) REFERENCES `roles` (`ID`),
  CONSTRAINT `fk_user_project_roles_user_id` FOREIGN KEY (`User_ID`) REFERENCES `users` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `user_project_roles` WRITE;
/*!40000 ALTER TABLE `user_project_roles` DISABLE KEYS */;
INSERT INTO `user_project_roles` VALUES 
(1,1,1);
/*!40000 ALTER TABLE `user_project_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_project_roles_audit`
--

DROP TABLE IF EXISTS `user_project_roles_audit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_project_roles_audit` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `User_ID` int(11) NOT NULL,
  `Project_ID` int(11) NOT NULL,
  `Role_ID` int(11) NOT NULL,
  `Req_Timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `Action` varchar(7) NOT NULL,
  `Res_Timestamp` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `Admin_ID` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_user_project_roles_audit_user_id` (`User_ID`),
  KEY `fk_user_project_roles_audit_project_id` (`Project_ID`),
  KEY `fk_user_project_roles_audit_role_id` (`Role_ID`),
  KEY `fk_user_project_roles_audit_admin_id` (`Admin_ID`),
  CONSTRAINT `fk_user_project_roles_audit_admin_id` FOREIGN KEY (`Admin_ID`) REFERENCES `users` (`ID`),
  CONSTRAINT `fk_user_project_roles_audit_project_id` FOREIGN KEY (`Project_ID`) REFERENCES `projects` (`ID`),
  CONSTRAINT `fk_user_project_roles_audit_role_id` FOREIGN KEY (`Role_ID`) REFERENCES `roles` (`ID`),
  CONSTRAINT `fk_user_project_roles_audit_user_id` FOREIGN KEY (`User_ID`) REFERENCES `users` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `First_Name` varchar(45) NOT NULL,
  `Last_Name` varchar(45) NOT NULL,
  `Registration_Date` date NOT NULL,
  `Active` tinyint(1) NOT NULL,
  `Login_Name` varchar(45) NOT NULL,
  `Password` varchar(45) NOT NULL,
  `Email` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `uk_users_login_name` (`Login_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES 
(1,'WISH','Admin','2013-03-04',1,'wish.admin','changeme','clahrc-wrt@imperial.ac.uk');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clahrc_wish`
--

DROP TABLE IF EXISTS `clahrc_wish`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clahrc_wish` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Entry_Date` date NULL,
  `Comment` varchar(45) NULL DEFAULT 'Dummy table to make the OAR report work',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary table structure for view `users_user_project_roles`
--

DROP TABLE IF EXISTS `users_user_project_roles`;
/*!50001 DROP VIEW IF EXISTS `users_user_project_roles`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `users_user_project_roles` (
  `ID` int(11),
  `First_Name` varchar(45),
  `Last_Name` varchar(45),
  `Registration_Date` date,
  `Active` tinyint(1),
  `Login_Name` varchar(45),
  `Password` varchar(45),
  `Email` varchar(45),
  `User_ID` int(11),
  `Project_ID` int(11),
  `Role_ID` int(11)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Current Database: `clahrc2`
--

USE `clahrc2`;

--
-- Final view structure for view `users_user_project_roles`
--

/*!50001 DROP TABLE IF EXISTS `users_user_project_roles`*/;
/*!50001 DROP VIEW IF EXISTS `users_user_project_roles`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = latin1 */;
/*!50001 SET character_set_results     = latin1 */;
/*!50001 SET collation_connection      = latin1_swedish_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`clahrc`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `users_user_project_roles` AS select `users`.`ID` AS `ID`,`users`.`First_Name` AS `First_Name`,`users`.`Last_Name` AS `Last_Name`,`users`.`Registration_Date` AS `Registration_Date`,`users`.`Active` AS `Active`,`users`.`Login_Name` AS `Login_Name`,`users`.`Password` AS `Password`,`users`.`Email` AS `Email`,`user_project_roles`.`User_ID` AS `User_ID`,`user_project_roles`.`Project_ID` AS `Project_ID`,`user_project_roles`.`Role_ID` AS `Role_ID` from (`users` join `user_project_roles`) where (`users`.`ID` = `user_project_roles`.`User_ID`) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-03-05 16:13:32