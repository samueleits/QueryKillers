-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: tbr
-- ------------------------------------------------------
-- Server version	8.0.42

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
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `books` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `author` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `genre` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `year` int DEFAULT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books`
--

LOCK TABLES `books` WRITE;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` VALUES (1,'1984','George Orwell','Distopia',1949,'Un romanzo distopico che esplora i temi del totalitarismo e del controllo sociale.'),(2,'Il piccolo principe','Antoine de Saint-Exupéry','Narrativa',1943,'Una fiaba poetica che affronta temi profondi come l’amicizia e il senso della vita.'),(3,'Il nome della rosa','Umberto Eco','Giallo storico',1980,'Un mistero ambientato in un monastero medievale, ricco di simbolismi e riferimenti filosofici.'),(4,'Orgoglio e pregiudizio','Jane Austen','Romanzo',1813,'Una critica sottile alla società inglese del XIX secolo attraverso una storia d’amore.'),(5,'Cent\'anni di solitudine','Gabriel García Márquez','Realismo magico',1967,'La saga della famiglia Buendía in un villaggio immaginario, tra realtà e fantasia.'),(6,'Il giovane Holden','J.D. Salinger','Narrativa',1951,'Il racconto di un adolescente ribelle che cerca il suo posto nel mondo.'),(7,'Cime tempestose','Emily Brontë','Romanzo',1847,'Una storia di passioni intense e vendette ambientata nelle brughiere inglesi.'),(8,'Guerra e pace','Lev Tolstoj','Romanzo storico',1869,'Un affresco epico della società russa durante le guerre napoleoniche.'),(9,'Il ritratto di Dorian Gray','Oscar Wilde','Romanzo',1890,'Un giovane che vende la sua anima per l’eterna giovinezza, esplorando temi di moralità e decadenza.'),(10,'Fahrenheit 451','Ray Bradbury','Fantascienza',1953,'In un futuro distopico, i libri sono proibiti e bruciati per mantenere l’ordine sociale.'),(11,'Il conte di Montecristo','Alexandre Dumas','Romanzo storico',1844,'La storia di Edmond Dantès e della sua vendetta contro chi lo ha tradito.'),(12,'Madame Bovary','Gustave Flaubert','Romanzo',1857,'La tragica vicenda di Emma Bovary, in cerca di una vita più appagante.'),(13,'Delitto e castigo','Fëdor Dostoevskij','Romanzo psicologico',1866,'Un giovane studente commette un omicidio e affronta le conseguenze morali.'),(14,'Anna Karenina','Lev Tolstoj','Romanzo',1877,'La storia di una donna aristocratica russa e la sua tragica storia d’amore.'),(15,'Il grande Gatsby','F. Scott Fitzgerald','Romanzo',1925,'La vita sfarzosa e tragica di Jay Gatsby nell’America degli anni ’20.'),(16,'Ulisse','James Joyce','Romanzo sperimentale',1922,'Un giorno nella vita di Leopold Bloom a Dublino, narrato con tecniche innovative.'),(17,'Il processo','Franz Kafka','Romanzo',1925,'Un uomo viene arrestato e processato da un’autorità sconosciuta per un crimine ignoto.'),(18,'Il piccolo principe','Antoine de Saint-Exupéry','Fiaba filosofica',1943,'Un pilota incontra un giovane principe proveniente da un altro pianeta.'),(19,'La fattoria degli animali','George Orwell','Satira politica',1945,'Una rivolta degli animali in una fattoria si trasforma in una dittatura.'),(20,'Il giovane Holden','J.D. Salinger','Romanzo di formazione',1951,'Le avventure e i pensieri di un adolescente ribelle a New York.'),(21,'Il signore degli anelli','J.R.R. Tolkien','Fantasy',1954,'La lotta per distruggere un anello malvagio che minaccia il mondo.'),(22,'Il dottor Živago','Boris Pasternak','Romanzo',1957,'Una storia d’amore durante la rivoluzione russa.'),(23,'Il gattopardo','Giuseppe Tomasi di Lampedusa','Romanzo storico',1958,'Il declino della nobiltà siciliana durante il Risorgimento.'),(24,'Il maestro e Margherita','Michail Bulgakov','Romanzo fantastico',1967,'Il diavolo visita Mosca portando scompiglio tra la popolazione.'),(25,'Cent\'anni di solitudine','Gabriel García Márquez','Realismo magico',1967,'La saga della famiglia Buendía nel villaggio di Macondo.'),(26,'Il nome della rosa','Umberto Eco','Giallo storico',1980,'Un frate indaga su misteriosi omicidi in un monastero medievale.'),(27,'Il pendolo di Foucault','Umberto Eco','Thriller esoterico',1988,'Tre editori si immergono in una caccia al tesoro occultista.'),(28,'Amatissima','Toni Morrison','Romanzo',1987,'Una ex schiava è perseguitata dal fantasma della figlia morta.'),(29,'Il dio delle piccole cose','Arundhati Roy','Romanzo',1997,'La storia di due gemelli in India e delle tragedie familiari che li colpiscono.'),(30,'La strada','Cormac McCarthy','Romanzo post-apocalittico',2006,'Un padre e un figlio viaggiano attraverso un’America desolata.'),(31,'Orgoglio e pregiudizio','Jane Austen','Romanzo',1813,'La storia d’amore tra Elizabeth Bennet e Mr. Darcy.'),(32,'Cime tempestose','Emily Brontë','Romanzo',1847,'Una storia di passioni intense e vendette ambientata nelle brughiere inglesi.'),(33,'Moby Dick','Herman Melville','Romanzo d’avventura',1851,'La caccia ossessiva del capitano Ahab alla balena bianca.'),(34,'I miserabili','Victor Hugo','Romanzo',1862,'La redenzione di un ex galeotto nella Francia del XIX secolo.'),(35,'Le avventure di Huckleberry Finn','Mark Twain','Romanzo',1884,'Un ragazzo fugge lungo il Mississippi con uno schiavo in fuga.'),(36,'Cuore di tenebra','Joseph Conrad','Romanzo',1899,'Un viaggio nel cuore dell’Africa e nell’oscurità dell’animo umano.'),(37,'La metamorfosi','Franz Kafka','Racconto',1915,'Un uomo si sveglia trasformato in un insetto gigante.'),(38,'Il lupo della steppa','Hermann Hesse','Romanzo',1927,'La crisi esistenziale di un uomo diviso tra natura umana e animale.'),(39,'Il mondo nuovo','Aldous Huxley','Distopia',1932,'Una società futuristica dove la felicità è ottenuta attraverso il controllo totale.'),(40,'Il piccolo amico','Donna Tartt','Romanzo',2002,'Una ragazza cerca di scoprire l’assassino del fratello in una cittadina del Mississippi.'),(41,'La casa degli spiriti','Isabel Allende','Realismo magico',1982,'La saga di una famiglia cilena attraverso generazioni.'),(42,'Il barone rampante','Italo Calvino','Romanzo',1957,'Un ragazzo decide di vivere sugli alberi senza mai scendere a terra.'),(43,'Il deserto dei Tartari','Dino Buzzati','Romanzo',1940,'Un ufficiale attende invano l’arrivo del nemico in una fortezza isolata.'),(44,'Il giorno della civetta','Leonardo Sciascia','Giallo',1961,'Un’indagine sulla mafia in Sicilia.'),(45,'Se questo è un uomo','Primo Levi','Memorie',1947,'Il racconto della prigionia in un campo di concentramento nazista.'),(46,'Il fu Mattia Pascal','Luigi Pirandello','Romanzo',1904,'Un uomo finge la propria morte per iniziare una nuova vita.'),(47,'Il piacere','Gabriele D’Annunzio','Romanzo',1889,'La vita edonistica di un giovane aristocratico romano.'),(48,'I promessi sposi','Alessandro Manzoni','Romanzo storico',1827,'La storia di Renzo e Lucia nell’Italia del XVII secolo.'),(49,'La coscienza di Zeno','Italo Svevo','Romanzo',1923,'Le memorie di un uomo che cerca di smettere di fumare.'),(50,'Le confessioni di un italiano','Ippolito Nievo','Romanzo',1867,'Le memorie di un uomo attraverso l’Italia del Risorgimento.'),(51,'Pinocchio','Carlo Collodi','Fiaba',1883,'Le avventure di un burattino che desidera diventare un bambino vero.');
/*!40000 ALTER TABLE `books` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-22  9:22:42
