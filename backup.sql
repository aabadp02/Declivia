-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: localhost    Database: declivia1.0
-- ------------------------------------------------------
-- Server version	8.0.18

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `courses`
--

DROP TABLE IF EXISTS `courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courses` (
  `CourseID` int(11) NOT NULL AUTO_INCREMENT,
  `CourseName` varchar(30) DEFAULT NULL,
  `UserName` varchar(30) DEFAULT NULL,
  `Dscription` varchar(200) DEFAULT NULL,
  `SubjectCounter` int(11) DEFAULT '0',
  `FileCounter` int(11) DEFAULT '0',
  `CoursesAccesses` int(11) DEFAULT '0',
  PRIMARY KEY (`CourseID`),
  KEY `UserName` (`UserName`),
  CONSTRAINT `courses_ibfk_1` FOREIGN KEY (`UserName`) REFERENCES `standarduser` (`StandardUserName`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
INSERT INTO `courses` VALUES (1,'Curso1','Naim','EL curso 1 trata de bicicletas de descenso así como bicicletas y descenso',0,0,0),(2,'Curso2','Naim','El segundo curso es sobre animales vertebrados',0,0,0),(3,'Curso3','JD','Este curso es el primero de la carrera',0,0,0),(4,'Curso4','JD','Este curso es el segundo de la carrera',0,0,0),(5,'Curso5','JD','Este curso es el tercero de la carrera',0,0,0),(6,'Curso6','Alex','Este es este también.',0,0,0),(7,'Curso7','Alex','Este curso es este curso.',0,0,0),(11,'afdsa','alexin','',0,0,0),(12,'sdajkjg','Alexin','',0,0,0);
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `diagram`
--

DROP TABLE IF EXISTS `diagram`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `diagram` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `StandardUserName` varchar(30) DEFAULT NULL,
  `valor` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `StandardUserName` (`StandardUserName`),
  CONSTRAINT `diagram_ibfk_1` FOREIGN KEY (`StandardUserName`) REFERENCES `standarduser` (`StandardUserName`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `diagram`
--

LOCK TABLES `diagram` WRITE;
/*!40000 ALTER TABLE `diagram` DISABLE KEYS */;
INSERT INTO `diagram` VALUES (1,'Naim',4),(2,'Naim',2),(3,'Naim',7),(4,'Naim',0),(5,'Naim',0),(6,'Naim',0),(7,'Naim',0),(8,'Naim',0),(9,'Naim',0),(10,'Naim',0),(11,'Alexin',0),(12,'Alexin',0),(13,'Alexin',0),(14,'Alexin',0),(15,'Alexin',0),(16,'Alexin',4),(17,'Alexin',0),(18,'Alexin',0),(19,'Alexin',3),(20,'Alexin',2),(21,'Alexin',3),(22,'Alexin',0),(23,'alexin',3),(24,'alexin',4),(25,'alexin',3),(26,'alexin',6),(27,'Alexin',0),(28,'Alexin',4),(29,'Alexin',1),(30,'Alexin',0),(31,'Alexin',2),(32,'Alexin',0),(33,'Pepon',0),(34,'Alexin',0),(35,'Alexin',0),(36,'Alexin',0),(37,'Alexin',0),(38,'Alexin',0),(39,'Alexin',0);
/*!40000 ALTER TABLE `diagram` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `files`
--

DROP TABLE IF EXISTS `files`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `files` (
  `FileID` int(11) NOT NULL AUTO_INCREMENT,
  `NameFile` varchar(30) DEFAULT NULL,
  `URI` varchar(1000) NOT NULL,
  `SubjectID` int(11) DEFAULT NULL,
  `Description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`FileID`),
  KEY `SubjectID` (`SubjectID`),
  CONSTRAINT `files_ibfk_1` FOREIGN KEY (`SubjectID`) REFERENCES `subjects` (`SubjectID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `files`
--

LOCK TABLES `files` WRITE;
/*!40000 ALTER TABLE `files` DISABLE KEYS */;
INSERT INTO `files` VALUES (1,'rentabilidad1','C:\\Users\\Alex\\Declivia\\traspas-RENTABILIDAD-reducido.pdf',1,'Esto es de rentabilidad de DP.v1'),(2,'rentabilidad2','C:\\Users\\Alex\\Declivia\\traspas-RENTABILIDAD-reducido.pdf',2,'Esto es de rentabilidad de DP.v2'),(3,'rentabilidad3','C:\\Users\\Alex\\Declivia\\traspas-RENTABILIDAD-reducido.pdf',13,'Esto es de rentabilidad de DP.v3'),(4,'rentabilidad4','C:\\Users\\Alex\\Declivia\\traspas-RENTABILIDAD-reducido.pdf',12,'Esto es de rentabilidad de DP.v4'),(5,'rentabilidad5','C:\\Users\\Alex\\Declivia\\traspas-RENTABILIDAD-reducido.pdf',11,'Esto es de rentabilidad de DP.v5'),(6,'Pert1','C:\\Users\\Alex\\Declivia\\problemas-pert(1).pdf',16,'Los graficos pert version 1'),(7,'Pert2','C:\\Users\\Alex\\Declivia\\problemas-pert(1).pdf',17,'Los graficos pert version 2'),(8,'fsdafsafsa','C:\\Users\\Alex\\Downloads\\1.Analisis-del-valor-ganado.pdf',20,'Descripción por defecto');
/*!40000 ALTER TABLE `files` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `standarduser`
--

DROP TABLE IF EXISTS `standarduser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `standarduser` (
  `StandardUserName` varchar(30) NOT NULL,
  `Passwd` varchar(100) NOT NULL,
  `Email` varchar(1500) NOT NULL,
  `AdminMode` int(11) DEFAULT '1',
  `ApplicationAccesses` int(11) DEFAULT '0',
  `numberStatistics` int(11) DEFAULT NULL,
  PRIMARY KEY (`StandardUserName`),
  UNIQUE KEY `StandardUserName` (`StandardUserName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `standarduser`
--

LOCK TABLES `standarduser` WRITE;
/*!40000 ALTER TABLE `standarduser` DISABLE KEYS */;
INSERT INTO `standarduser` VALUES ('Alex','0cc175b9c0f1b6a831c399e269772661','alexito@gmail.com',1,0,NULL),('Alexin','4124bc0a9335c27f086f24ba207a4912','aabadp02@estudiantes.unileon.es',1,0,NULL),('JD','JDRandom','JD@gmail.com',1,0,NULL),('Naim','abc','aabadp02@estudiantes.unileon.es',1,0,NULL),('Pepon','0cc175b9c0f1b6a831c399e269772661','',1,0,NULL);
/*!40000 ALTER TABLE `standarduser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subjects`
--

DROP TABLE IF EXISTS `subjects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subjects` (
  `SubjectID` int(11) NOT NULL AUTO_INCREMENT,
  `SubjectName` varchar(30) DEFAULT NULL,
  `FavouriteSubject` int(11) DEFAULT NULL,
  `CourseID` int(11) NOT NULL,
  `TeacherEmail` varchar(50) DEFAULT NULL,
  `SubjectCredits` int(11) DEFAULT '6',
  `SubjectsAccesses` int(11) DEFAULT '0',
  `FileCounter` int(11) DEFAULT '0',
  `Dificulty` int(11) DEFAULT '5',
  PRIMARY KEY (`SubjectID`),
  KEY `CourseID` (`CourseID`),
  CONSTRAINT `subjects_ibfk_1` FOREIGN KEY (`CourseID`) REFERENCES `courses` (`CourseID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subjects`
--

LOCK TABLES `subjects` WRITE;
/*!40000 ALTER TABLE `subjects` DISABLE KEYS */;
INSERT INTO `subjects` VALUES (1,'Calculo',0,1,'a@b',6,3,0,5),(2,'SO',0,2,'a@b',6,1,0,5),(3,'Prg2',0,2,'a@b',6,0,0,5),(4,'Metodos Numericos',0,3,'a@b',6,0,0,5),(5,'Mat. Discreta',0,3,'a@b',6,0,0,5),(6,'Prg1',0,3,'a@b',6,0,0,5),(7,'ISI',0,4,'a@b',6,0,0,5),(8,'SI',0,4,'a@b',6,0,0,5),(9,'DB',0,4,'a@b',6,0,0,5),(10,'ASO',0,4,'a@b',6,0,0,5),(11,'INSO',0,5,'a@b',6,0,0,5),(12,'INCO',0,5,'a@b',6,0,0,5),(13,'HADOOP',0,5,'a@b',6,0,0,5),(14,'SI',0,5,'a@b',6,0,0,5),(15,'DP',0,5,'a@b',6,0,0,5),(16,'Animacion',1,6,'as@b',6,3,0,5),(17,'PRogramacion I',0,6,'a@b',6,0,0,5),(18,'XD',0,7,'a@rb',6,0,0,5),(20,'fdsafsda',0,11,'example@example.com',0,8,1,2);
/*!40000 ALTER TABLE `subjects` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `superuser`
--

DROP TABLE IF EXISTS `superuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `superuser` (
  `SuperUserName` varchar(30) NOT NULL,
  `Passwd` varchar(100) NOT NULL,
  `Email` varchar(30) NOT NULL,
  PRIMARY KEY (`SuperUserName`),
  UNIQUE KEY `SuperUserName` (`SuperUserName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `superuser`
--

LOCK TABLES `superuser` WRITE;
/*!40000 ALTER TABLE `superuser` DISABLE KEYS */;
INSERT INTO `superuser` VALUES ('admin','0cc175b9c0f1b6a831c399e269772661','a@a');
/*!40000 ALTER TABLE `superuser` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-01-14 18:32:27
