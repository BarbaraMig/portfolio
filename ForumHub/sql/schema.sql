-- forumhub.curso definição

CREATE TABLE `curso` (
  `id` int NOT NULL AUTO_INCREMENT,
  `categoria` varchar(100) NOT NULL,
  `nome` varchar(150) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- forumhub.perfil definição

CREATE TABLE `perfil` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- forumhub.usuario definição

CREATE TABLE `usuario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(150) NOT NULL,
  `nome` varchar(150) NOT NULL,
  `senha` varchar(255) NOT NULL,
  `perfil_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK5171l57faosmj8myawaucatdw` (`email`),
  UNIQUE KEY `UK3gsqha3puu1ccw81jefi4j71t` (`perfil_id`),
  CONSTRAINT `FK9po12ytp6krwvwht1kmd0qgxf` FOREIGN KEY (`perfil_id`) REFERENCES `perfil` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- forumhub.resposta definição

CREATE TABLE `resposta` (
  `id` int NOT NULL AUTO_INCREMENT,
  `data_criacao` datetime(6) DEFAULT NULL,
  `mensagem` text NOT NULL,
  `solucao` bit(1) NOT NULL,
  `autor_id` int NOT NULL,
  `topico_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9999kvnmdq63ah7imctrl06r7` (`autor_id`),
  KEY `FKltuv9rkfjtlmn8b0rb3wdbjsv` (`topico_id`),
  CONSTRAINT `FK9999kvnmdq63ah7imctrl06r7` FOREIGN KEY (`autor_id`) REFERENCES `usuario` (`id`),
  CONSTRAINT `FKltuv9rkfjtlmn8b0rb3wdbjsv` FOREIGN KEY (`topico_id`) REFERENCES `topico` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- forumhub.topico definição

CREATE TABLE `topico` (
  `id` int NOT NULL AUTO_INCREMENT,
  `data_criacao` datetime(6) DEFAULT NULL,
  `mensagem` text NOT NULL,
  `status` varchar(50) NOT NULL,
  `titulo` varchar(255) NOT NULL,
  `autor_id` int NOT NULL,
  `curso_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsk04hscorwqdymnafg8882v64` (`autor_id`),
  KEY `FKcaaogjo0ynd54updie6kdpxd1` (`curso_id`),
  CONSTRAINT `FKcaaogjo0ynd54updie6kdpxd1` FOREIGN KEY (`curso_id`) REFERENCES `curso` (`id`),
  CONSTRAINT `FKsk04hscorwqdymnafg8882v64` FOREIGN KEY (`autor_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



