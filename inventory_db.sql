CREATE DATABASE `inventory_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE TABLE `console` (
  `id_Console` int NOT NULL AUTO_INCREMENT,
  `id_Product` int DEFAULT NULL,
  `storageCapacity` varchar(255) DEFAULT NULL,
  `includesGames` tinyint(1) DEFAULT NULL,
  `isSpecialEdition` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id_Console`),
  KEY `id_Product` (`id_Product`),
  CONSTRAINT `console_ibfk_1` FOREIGN KEY (`id_Product`) REFERENCES `product` (`id_Product`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `cpu` (
  `id_CPU` int NOT NULL AUTO_INCREMENT,
  `id_Product` int DEFAULT NULL,
  `clockSpeed` varchar(255) DEFAULT NULL,
  `coreCount` int DEFAULT NULL,
  `threadCount` int DEFAULT NULL,
  `socketType` varchar(255) DEFAULT NULL,
  `isInstalled` tinyint(1) DEFAULT '0',
  `installationTime` float DEFAULT '0',
  `installationGuideURL` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_CPU`),
  KEY `id_Product` (`id_Product`),
  CONSTRAINT `cpu_ibfk_1` FOREIGN KEY (`id_Product`) REFERENCES `product` (`id_Product`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `gpu` (
  `id_GPU` int NOT NULL AUTO_INCREMENT,
  `id_Product` int DEFAULT NULL,
  `memorySize` varchar(255) DEFAULT NULL,
  `memoryType` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `coreClock` varchar(255) DEFAULT NULL,
  `isInstalled` tinyint(1) DEFAULT '0',
  `installationTime` float DEFAULT '0',
  `installationGuideURL` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_GPU`),
  KEY `id_Product` (`id_Product`),
  CONSTRAINT `gpu_ibfk_1` FOREIGN KEY (`id_Product`) REFERENCES `product` (`id_Product`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `headphones` (
  `id_Headphones` int NOT NULL AUTO_INCREMENT,
  `id_Peripheral` int DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `weight` double DEFAULT NULL,
  `microphone` tinyint(1) DEFAULT NULL,
  `inputImpedance` int DEFAULT NULL,
  `sensitivity` int DEFAULT NULL,
  `frequencyResponse` int DEFAULT NULL,
  `micSensitivity` tinyint(1) DEFAULT NULL,
  `micFrequencyResponse` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id_Headphones`),
  KEY `id_Peripheral` (`id_Peripheral`),
  CONSTRAINT `headphones_ibfk_1` FOREIGN KEY (`id_Peripheral`) REFERENCES `peripheral` (`id_Peripheral`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `inventory` (
  `id_Product` int NOT NULL,
  `stock` int NOT NULL,
  PRIMARY KEY (`id_Product`),
  CONSTRAINT `inventory_ibfk_1` FOREIGN KEY (`id_Product`) REFERENCES `product` (`id_Product`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `keyboard` (
  `id_Keyboard` int NOT NULL AUTO_INCREMENT,
  `id_Peripheral` int DEFAULT NULL,
  `switchType` varchar(255) DEFAULT NULL,
  `isMechanical` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id_Keyboard`),
  KEY `id_Peripheral` (`id_Peripheral`),
  CONSTRAINT `keyboard_ibfk_1` FOREIGN KEY (`id_Peripheral`) REFERENCES `peripheral` (`id_Peripheral`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `motherboard` (
  `id_Motherboard` int NOT NULL AUTO_INCREMENT,
  `id_Product` int DEFAULT NULL,
  `formFactor` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `chipset` varchar(255) DEFAULT NULL,
  `socketType` varchar(255) DEFAULT NULL,
  `memoryType` varchar(255) DEFAULT NULL,
  `maxMemory` int DEFAULT NULL,
  `isInstalled` tinyint(1) DEFAULT '0',
  `installationTime` float DEFAULT '0',
  `installationGuideURL` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_Motherboard`),
  KEY `id_Product` (`id_Product`),
  CONSTRAINT `motherboard_ibfk_1` FOREIGN KEY (`id_Product`) REFERENCES `product` (`id_Product`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `mouse` (
  `id_Mouse` int NOT NULL AUTO_INCREMENT,
  `id_Peripheral` int DEFAULT NULL,
  `dpi` int DEFAULT NULL,
  `isGamer` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id_Mouse`),
  KEY `id_Peripheral` (`id_Peripheral`),
  CONSTRAINT `mouse_ibfk_1` FOREIGN KEY (`id_Peripheral`) REFERENCES `peripheral` (`id_Peripheral`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `peripheral` (
  `id_Peripheral` int NOT NULL AUTO_INCREMENT,
  `id_Product` int DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `connectionType` varchar(255) DEFAULT NULL,
  `isWireless` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id_Peripheral`),
  KEY `id_Product` (`id_Product`),
  CONSTRAINT `peripheral_ibfk_1` FOREIGN KEY (`id_Product`) REFERENCES `product` (`id_Product`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `product` (
  `id_Product` int NOT NULL AUTO_INCREMENT,
  `SKU` int NOT NULL,
  `brand` varchar(255) NOT NULL,
  `model` varchar(255) NOT NULL,
  `description` text,
  `price` double NOT NULL,
  PRIMARY KEY (`id_Product`),
  UNIQUE KEY `SKU` (`SKU`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `ram` (
  `id_RAM` int NOT NULL AUTO_INCREMENT,
  `id_Product` int DEFAULT NULL,
  `capacity` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `speed` varchar(255) DEFAULT NULL,
  `formFactor` varchar(255) DEFAULT NULL,
  `isInstalled` tinyint(1) DEFAULT '0',
  `installationTime` float DEFAULT '0',
  `installationGuideURL` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_RAM`),
  KEY `id_Product` (`id_Product`),
  CONSTRAINT `ram_ibfk_1` FOREIGN KEY (`id_Product`) REFERENCES `product` (`id_Product`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;