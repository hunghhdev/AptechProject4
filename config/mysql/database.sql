-- MySQL dump 10.13  Distrib 8.0.18, for Linux (x86_64)
--
-- Host: database.hunghh.xyz    Database: p4_db
-- ------------------------------------------------------
-- Server version	5.7.25-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ '';

--
-- Table structure for table `t_booking`
--

DROP TABLE IF EXISTS `t_booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_booking` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `updated_by` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `from_date` datetime DEFAULT NULL,
  `room_id` int(11) DEFAULT NULL,
  `to_date` datetime DEFAULT NULL,
  `total_date` int(11) DEFAULT NULL,
  `total_price` int(11) DEFAULT NULL,
  `customer_id` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_booking`
--

LOCK TABLES `t_booking` WRITE;
/*!40000 ALTER TABLE `t_booking` DISABLE KEYS */;
INSERT INTO `t_booking` VALUES (2,'system','2019-11-17 15:35:37',0,NULL,'2019-11-17 15:35:37','2019-11-16 17:00:00',36,'2019-11-18 17:00:00',2,200,8,1),(3,'system','2019-11-18 17:18:01',0,NULL,'2019-11-18 17:18:01','2019-11-18 17:00:00',38,'2019-11-26 17:00:00',8,1600,7,0),(4,'system','2019-11-18 17:18:24',0,NULL,'2019-11-18 17:18:24','2019-11-18 17:00:00',38,'2019-11-26 17:00:00',8,1600,7,0);
/*!40000 ALTER TABLE `t_booking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_customer`
--

DROP TABLE IF EXISTS `t_customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `updated_by` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `customer_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone_number` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_customer`
--

LOCK TABLES `t_customer` WRITE;
/*!40000 ALTER TABLE `t_customer` DISABLE KEYS */;
INSERT INTO `t_customer` VALUES (5,'system','2019-11-15 16:24:43',1,'system','2019-11-16 02:49:09','Diệu Nhi','0987654321'),(6,'system','2019-11-15 16:26:26',1,'system','2019-11-17 11:36:12','Linh Nhi','0987654322'),(7,'system','2019-11-17 11:35:34',0,NULL,'2019-11-17 11:35:34','Trúc Mai','0987654321'),(8,'system','2019-11-17 11:37:00',0,NULL,'2019-11-17 11:37:00','Thu Quỳnh','0987654322'),(9,'system','2019-11-17 11:37:23',0,NULL,'2019-11-17 11:37:23','Trâm Anh','0987654323'),(10,'system','2019-11-17 11:38:30',0,NULL,'2019-11-17 11:38:30','Em Nhã','0987654324');
/*!40000 ALTER TABLE `t_customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_permission`
--

DROP TABLE IF EXISTS `t_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `permission_key` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `permission_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjpo0wq99a03xl6oxm1gcwmbol` (`parent_id`)
) ENGINE=MyISAM AUTO_INCREMENT=9003 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_permission`
--

LOCK TABLES `t_permission` WRITE;
/*!40000 ALTER TABLE `t_permission` DISABLE KEYS */;
INSERT INTO `t_permission` VALUES (1000,NULL,0,'Nhân viên'),(1001,'PERM_USER_CREATE',1000,'Thêm nhân viên'),(1002,'PERM_USER_READ',1000,'Xem nhân viên'),(1003,'PERM_USER_UPDATE',1000,'Sửa nhân viên'),(1004,'PERM_USER_DELETE',1000,'Xóa nhân viên'),(2000,NULL,0,'Chức năng'),(2001,'PERM_ROLE_CREATE',2000,'Thêm chức năng'),(2002,'PERM_ROLE_READ',2000,'Xem chức năng'),(2003,'PERM_ROLE_UPDATE',2000,'Sửa chức năng'),(2004,'PERM_ROLE_DELETE',2000,'Xóa chức năng'),(5000,NULL,0,'Vật tư'),(5001,'PERM_SUPPLIES_CREATE',5000,'Thêm vật tư'),(5002,'PERM_SUPPLIES_READ',5000,'Xem vật tư'),(5003,'PERM_SUPPLIES_UPDATE',5000,'Sửa vật tư'),(5004,'PERM_SUPPLIES_DELETE',5000,'Xóa vật tư'),(6000,NULL,0,'Phòng'),(6001,'PERM_ROOM_CREATE',6000,'Thêm phòng'),(6002,'PERM_ROOM_READ',6000,'Xem phòng'),(6003,'PERM_ROOM_UPDATE',6000,'Sửa phòng'),(6004,'PERM_ROOM_DELETE',6000,'Xóa phòng'),(7000,NULL,0,'Đặt phòng'),(7001,'PERM_BOOK_ROOM',7000,'Đặt phòng'),(7002,'PERM_BOOK_CHECKIN',7000,'Check In'),(7003,'PERM_BOOK_CHECKOUT',7000,'Check Out'),(8000,NULL,0,'Khách hàng'),(8002,'PERM_CUSTOMER_READ',8000,'Xem khách hàng'),(9000,NULL,0,'Quyền truy cập'),(9001,'PERM_PERMISSION_CREATE',9000,'Tạo quyền truy cập'),(9002,'PERM_PERMISSION_READ',9000,'Xem quyền truy cập'),(8003,'PERM_CUSTOMER_UPDATE',8000,'Sửa khách hàng'),(8004,'PERM_CUSTOMER_DELETE',8000,'Xóa khách hàng'),(8001,'PERM_CUSTOMER_CREATE',8000,'Thêm khách hàng');
/*!40000 ALTER TABLE `t_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_role`
--

DROP TABLE IF EXISTS `t_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `updated_by` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `role_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `personnel_level` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_role`
--

LOCK TABLES `t_role` WRITE;
/*!40000 ALTER TABLE `t_role` DISABLE KEYS */;
INSERT INTO `t_role` VALUES (1,'system','2019-08-27 15:31:26',0,'system','2019-11-15 09:27:47','full permissions','System',1),(7,'system','2019-08-28 04:48:27',0,'system','2019-09-17 09:55:10','Full quyền nhân viên','Quản lý nhân viên',10);
/*!40000 ALTER TABLE `t_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_role_permission`
--

DROP TABLE IF EXISTS `t_role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `permission_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjobmrl6dorhlfite4u34hciik` (`permission_id`),
  KEY `FK90j038mnbnthgkc17mqnoilu9` (`role_id`)
) ENGINE=MyISAM AUTO_INCREMENT=125 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_role_permission`
--

LOCK TABLES `t_role_permission` WRITE;
/*!40000 ALTER TABLE `t_role_permission` DISABLE KEYS */;
INSERT INTO `t_role_permission` VALUES (1,1,1004),(2,1,1003),(3,1,1002),(4,1,1001),(26,1,2004),(24,1,2001),(22,1,2003),(25,1,2002),(31,1,3002),(32,1,3004),(33,1,3003),(34,1,3001),(39,1,5004),(40,1,5001),(41,1,5003),(42,1,5002),(43,1,6004),(44,1,6003),(45,1,6001),(46,1,6002),(71,10,3004),(72,10,1001),(74,10,3001),(75,10,1002),(78,10,1003),(79,10,3003),(81,10,1004),(82,10,3002),(103,1,7002),(104,1,7003),(105,1,7001),(117,1,8001),(121,1,9002),(120,1,9001),(122,1,8003),(123,1,8002),(124,1,8004);
/*!40000 ALTER TABLE `t_role_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_room`
--

DROP TABLE IF EXISTS `t_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_room` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `updated_by` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `room_code` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `supplies` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `size` int(11) DEFAULT NULL,
  `room_type` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_room`
--

LOCK TABLES `t_room` WRITE;
/*!40000 ALTER TABLE `t_room` DISABLE KEYS */;
INSERT INTO `t_room` VALUES (36,'system','2019-11-13 17:39:18',0,'system','2019-11-13 17:39:43','','001','Đã đặt',NULL,100,1,'Standard'),(37,'system','2019-11-13 17:48:40',1,'system','2019-11-13 17:57:39','','002','Trống',NULL,400,4,'Standard'),(38,'system','2019-11-18 17:13:03',0,NULL,'2019-11-18 17:13:03','','002','Đã đặt',NULL,200,2,'Standard');
/*!40000 ALTER TABLE `t_room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_room_supplies`
--

DROP TABLE IF EXISTS `t_room_supplies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_room_supplies` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `room_id` int(11) NOT NULL,
  `supplies_id` int(11) NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `updated_by` varchar(255) DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `INDEX` (`id`),
  KEY `FK7aqhpelonlewpjcconcsl0a5g` (`supplies_id`),
  KEY `FKjmmpwmid53t7dfas7g06lvbt8` (`room_id`)
) ENGINE=MyISAM AUTO_INCREMENT=29 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_room_supplies`
--

LOCK TABLES `t_room_supplies` WRITE;
/*!40000 ALTER TABLE `t_room_supplies` DISABLE KEYS */;
INSERT INTO `t_room_supplies` VALUES (22,36,23,NULL,NULL,0,NULL,NULL),(23,37,22,NULL,NULL,0,NULL,NULL),(24,37,21,NULL,NULL,0,NULL,NULL),(25,37,23,NULL,NULL,0,NULL,NULL),(26,37,24,NULL,NULL,0,NULL,NULL),(27,38,22,NULL,NULL,0,NULL,NULL),(28,38,21,NULL,NULL,0,NULL,NULL);
/*!40000 ALTER TABLE `t_room_supplies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_supplies`
--

DROP TABLE IF EXISTS `t_supplies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_supplies` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `updated_by` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `note` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `used` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_supplies`
--

LOCK TABLES `t_supplies` WRITE;
/*!40000 ALTER TABLE `t_supplies` DISABLE KEYS */;
INSERT INTO `t_supplies` VALUES (21,'system','2019-10-27 14:44:33',0,'system','2019-11-12 17:00:29','Ghế tantra',NULL,15,1),(22,'system','2019-10-28 15:25:12',0,'system','2019-11-12 17:00:33','Giường đôi',NULL,10,1),(23,'system','2019-10-28 16:17:21',0,'system','2019-10-29 15:31:10','Giường đơn','',15,1),(24,'system','2019-10-28 16:30:37',0,'system','2019-10-28 16:48:44','Gương','note test',25,0);
/*!40000 ALTER TABLE `t_supplies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user`
--

DROP TABLE IF EXISTS `t_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `updated_by` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `avatar` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `jwt_key` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `role_id` int(11) NOT NULL,
  `username` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `full_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `personnel_level` tinyint(4) DEFAULT '0',
  `branch_place_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user`
--

LOCK TABLES `t_user` WRITE;
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;
INSERT INTO `t_user` VALUES (1,'system','2019-08-24 03:21:27',0,'system','2019-08-24 03:21:40','https://s3.us-east-2.amazonaws.com/hunghh.bucket/p4/avatar/1566650085441_hihi.jpg','eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NzQ1Mjk4MTEsInVzZXJuYW1lIjoic3lzdGVtIn0.a4Umv2Icodkb8mDrgyOj99DCoZybOCwTsAFMTSmw_O8','786bff75f594ba710b63449034d1d9bc',1,'system','admin@hunghh.xyz','Hoàng Huy Hùng',1,1),(8,'system','2019-09-08 08:05:41',0,'system','2019-09-08 08:11:39','https://s3.us-east-2.amazonaws.com/hunghh.bucket/p4/avatar/1567929891638_1.jpg','eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1Njg3NTE5NDgsInVzZXJuYW1lIjoibmdvY250YiJ9.OWswbs_NTEIA2jsJUEcfQQCGdT5uaNZFiuhmoquAc70','786bff75f594ba710b63449034d1d9bc',10,'ngocntb','ngocntb@hunghh.xyz','Nguyễn Thị Bích Ngọc',10,2),(9,'system','2019-09-09 04:42:13',0,'nhannh','2019-09-09 10:48:31','https://s3.us-east-2.amazonaws.com/hunghh.bucket/p4/avatar/1568004011845_2.jpg','eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NzQ1Mjk4MjAsInVzZXJuYW1lIjoibmhhbm5oIn0.kSoTffqjkVECpMr2VMacVBG5oPf8-2ER2hhB9Lrs25w','786bff75f594ba710b63449034d1d9bc',1,'nhannh','nhannh@hunghh.xyz','Nguyễn Ngọc Hương Nhã',1,1),(10,'system','2019-09-09 04:47:02',0,'nhannh','2019-09-09 10:46:27','https://s3.us-east-2.amazonaws.com/hunghh.bucket/p4/avatar/1568004355031_3.jpg','eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NjgwNzE5ODQsInVzZXJuYW1lIjoidGh1eWhwIn0.8gABqgN4E9Lm4Pm7V8M6JZaiZkMuoi4W5WgJzYA_TaU','786bff75f594ba710b63449034d1d9bc',10,'thuyhp','thuyhp@hunghh.xyz','Hoàng Phương Thúy',10,2);
/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;
UNLOCK TABLES;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-11-27 11:57:33
