-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: todo
-- ------------------------------------------------------
-- Server version	8.0.32

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

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `category_code` bigint NOT NULL AUTO_INCREMENT,
  `category_name` varchar(20) NOT NULL,
  `category_is_private` bit(1) NOT NULL,
  `category_user_code` bigint NOT NULL,
  PRIMARY KEY (`category_code`),
  KEY `category_user_code_idx` (`category_user_code`),
  CONSTRAINT `category_user_code` FOREIGN KEY (`category_user_code`) REFERENCES `user` (`user_code`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'코딩',_binary '\0',5),(2,'게임',_binary '\0',5),(3,'강의',_binary '',5),(4,'공부',_binary '',6),(5,'약속',_binary '\0',5),(6,'일',_binary '\0',6);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task` (
  `task_code` bigint NOT NULL AUTO_INCREMENT,
  `task_content` varchar(200) NOT NULL,
  `task_start_date` date NOT NULL,
  `task_end_date` date NOT NULL,
  `task_state` bit(1) NOT NULL DEFAULT b'0',
  `task_user_code` bigint NOT NULL,
  `task_category_code` bigint DEFAULT NULL,
  PRIMARY KEY (`task_code`),
  KEY `task_user_code_idx` (`task_user_code`),
  KEY `task_category_code_idx` (`task_category_code`),
  CONSTRAINT `task_category_code` FOREIGN KEY (`task_category_code`) REFERENCES `category` (`category_code`),
  CONSTRAINT `task_user_code` FOREIGN KEY (`task_user_code`) REFERENCES `user` (`user_code`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task`
--

LOCK TABLES `task` WRITE;
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
INSERT INTO `task` VALUES (26,' \'회의 준비\'','2024-09-10','2024-09-10',_binary '\0',5,1),(28,' \'코드 리뷰\'','2024-09-22','2024-09-26',_binary '\0',5,1),(29,' \'팀 회의\'','2024-10-01','2024-10-03',_binary '\0',5,1),(30,' \'UI 디자인 수정\'','2024-10-01','2024-10-03',_binary '\0',5,1),(31,' \'서버 유지보수\'','2024-10-01','2024-10-03',_binary '\0',5,1),(32,' \'버그 수정\'','2024-10-01','2024-10-03',_binary '\0',5,1),(33,' \'문서 작성\'','2024-10-01','2024-10-03',_binary '\0',5,1),(34,' \'기능 테스트\'','2024-10-01','2024-10-03',_binary '\0',5,1),(35,' \'배포 준비\'','2024-10-01','2024-10-03',_binary '\0',5,1),(36,' \'고객 피드백 분석\'','2024-10-01','2024-10-03',_binary '\0',5,1),(37,' \'보안 점검\'','2024-10-01','2024-10-03',_binary '\0',6,1),(38,' \'API 문서 업데이트\'','2024-10-01','2024-10-03',_binary '\0',5,1),(39,' \'팀 빌딩 행사\'','2024-10-01','2024-10-03',_binary '\0',6,1),(40,' \'회의록 정리\'','2024-10-01','2024-10-03',_binary '\0',3,1),(41,' \'디자인 리뷰\'','2024-10-01','2024-10-03',_binary '\0',4,1),(43,' \'프로젝트 데모 준비\'','2024-10-01','2024-10-03',_binary '\0',6,1),(44,' \'사용자 교육\'','2024-10-01','2024-10-03',_binary '\0',6,1),(45,'과제 제출성공?','2024-09-10','2024-09-10',_binary '\0',5,1),(54,'내 생일','2024-08-17','2024-08-17',_binary '\0',5,1);
/*!40000 ALTER TABLE `task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_code` bigint NOT NULL AUTO_INCREMENT,
  `user_id` varchar(20) NOT NULL,
  `user_pw` varchar(255) NOT NULL,
  `user_role` varchar(255) NOT NULL,
  `user_nickname` varchar(45) NOT NULL,
  `user_profile_img` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_code`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (3,'yy','$2a$10$S7C15d9brJ7LErLz9fMvmednVk7Q5OS1v5PF0.1fLsymgWLmp3Zsq','ROLE_USER','테스트',NULL),(4,'wpqkf','$2a$10$jtHmvpPTOIFzjY/mkEOZV./oTs18BIxBNuRSMUgVi0PDAzsuvSmVu','ROLE_USER','테스트2',NULL),(5,'wjdwltjq','$2a$10$WNT43fuWJdXf1OPYHwh6AOyuoQf0I2Smbn9s3y1/K/w5Ci/tE0hxq','ROLE_USER','정지섭','50c6afe6-20fd-4f95-801f-402c9ee447c1_Floyd-Morris-Remake-1024x879-1.jpg'),(6,'윤하','$2a$10$zY.A0iib4D56k8yD4L9IIOYmnbRUCEugzPCbY9ETwVzD4mMXKPx3e','ROLE_USER','차윤하',NULL),(7,'qwqw13245','$2a$10$7KxWDTuJHeoS.6ML3kaHGexOgBiXBiD1GhhobwbR5TnPK5J3ijJ52','ROLE_USER','관리자',NULL),(8,'asd','$2a$10$aVcnH3F.uN5S/vUE8QTYvOdSI5oFpkTFHWEbl391YBzxPBVVGsb9W','ROLE_USER','aaa',NULL),(9,'test','$2a$10$vz6ldgWOvaHld9jFdPnHK..FtYRa8vC/C2xDk4QIOiilSiDhI3PfW','ROLE_USER','랄라라','48e03102-5c10-48c8-be6b-34d53c705b2d_Floyd-Morris-Remake-1024x879-1.jpg');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-09-19 15:22:12
