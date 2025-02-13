-- Désactiver les contraintes de clés étrangères temporairement
SET FOREIGN_KEY_CHECKS=0;

-- Supprimer les tables existantes
DROP TABLE IF EXISTS `commentaire`;
DROP TABLE IF EXISTS `partage_note`;
DROP TABLE IF EXISTS `note`;
DROP TABLE IF EXISTS `utilisateur`;

-- Structure et données de la table utilisateur
CREATE TABLE if not exists `utilisateur` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `mot_de_passe` varchar(255) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `prenom` varchar(255) NOT NULL,
  `image_path` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKrma38wvnqfaf66vvmi57c71lo` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `utilisateur` (`id`, `email`, `mot_de_passe`, `nom`, `prenom`, `image_path`) VALUES
(1, 'ibra@gmail.com', '123456', 'Diallo', 'ibrahima ', 'images/67a643a9c334a-97164893dc28f8c4_1000041638.jpg'),
(2, 'ninho@gmail.com', '123456', 'Ninho', 'Ni', 'images/04239dc1-859a-4400-9994-ea20b458b7be_1000046006.jpg'),
(3, 'elon@gmail.com', '123456', 'Elon', 'Musk', 'images/4cc9c6ca-caba-4a46-a7bc-a712215336b0_1000046001.jpg'),
(4, 'picky@gmail.com', '123456', 'Picky', 'Blinders ', 'images/442c4dd1-dbce-4ef1-a665-7bbfc1f2661d_1000046008.jpg'),
(5, 'tirion@gmail.com', '123456', 'Tiryon', 'Lannister', 'images/8080a9f4-abd7-4cfa-8edc-e7bd537a93e3_1000046007.jpg'),
(6, 'jonh@gmail.com', '123456', 'Jonh', 'Snow', 'images/e9a429ab-d899-41f7-8c39-c02b8d3e48ef_1000046012.jpg'),
(7, 'aria@gmail.com', '123456', 'Aria', 'stark ', 'images/557f44fa-b0cb-4faf-a293-c9c4e9d0aeb7_1000046010.jpg'),
(8, 'soule@gmail.com', '123456', 'Souleymane ', 'Diallo', 'images/e24d9ccc-f89a-43c6-8c6d-f6275de32199_1000046002.jpg'),
(9, 'conde@gmail.com', '123456', 'Mr Daouda', 'Conde', 'images/c8388b9f-9e3b-4985-92f9-b2e14e34afb8_1000046003.jpg'),
(10, 'test@gmail.com', '123456', 'test', 'test', 'images/eb5f7c24-4b99-4277-8da7-f5b50cebadb0_image_picker_176AEDA2-5920-4B1C-B87D-A980BC6D4B45-86117-0000032A5F58E66D.png'),
(11, 'in@gmail.com', '123456', 'tst', 'test', 'images/5b10501a-0da5-4117-a2ec-64115dd3d40b_image_picker_71F2615B-4E73-4529-BBD4-338D76BFE7E6-86117-0000032B16152B54.jpg'),
(12, 'tee@gmail.com', '123456', 'test', 'test', 'images/0c87fae1-e189-454d-87bc-b316dcd8e80e_image_picker_F0D6912F-E20B-4224-B21E-7BEF3FDAA139-86117-0000032D1173D772.jpg'),
(13, 'daou@gmail.com', '123456', 'Conde', 'Daouda', 'images/ce61c154-5f9f-470c-9da9-d7c26baddee6_1000046110.jpg');

-- Structure et données de la table note
CREATE TABLE if not exists `note` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `contenu` varchar(2000) NOT NULL,
  `cours` varchar(255) NOT NULL,
  `nombre_telechargements` int(11) NOT NULL,
  `titre` varchar(255) NOT NULL,
  `utilisateur_id` bigint(20) NOT NULL,
  `date_ajout` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfubnay5m216o6xvkudchbu8vd` (`utilisateur_id`),
  CONSTRAINT `FKfubnay5m216o6xvkudchbu8vd` FOREIGN KEY (`utilisateur_id`) REFERENCES `utilisateur` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `note` (`id`, `contenu`, `cours`, `nombre_telechargements`, `titre`, `utilisateur_id`, `date_ajout`) VALUES
(1, 'Java est un langage de programmation orienté objet, largement utilisé pour le développement d''applications mobiles, web et de bureau. Il est basé sur le principe "Write Once, Run Anywhere", ce qui signifie que le code écrit peut s''exécuter sur n''importe quelle plateforme compatible avec la machine virtuelle Java (JVM).', 'Java', 0, 'Cours de Java', 1, '2025-02-07 18:09:11.000000'),
(2, 'Flutter est un framework développé par Google pour créer des applications mobiles multiplateformes avec un seul code source. Il utilise le langage Dart et repose sur un système de widgets pour concevoir des interfaces graphiques dynamiques et performantes.', 'Flutter', 1, 'Introduction A flutter', 1, '2025-02-07 18:15:02.000000'),
(3, 'La programmation orientée objet (POO) en Python repose sur la création de classes et d''objets. Une classe est un modèle qui définit des attributs et des méthodes, tandis qu''un objet est une instance de cette classe. Ce paradigme facilite la réutilisation et l''organisation du code.', 'Python', 0, 'Poo Python', 3, '2025-02-07 18:16:30.000000'),
(4, 'HTML (HyperText Markup Language) est un langage de balisage utilisé pour structurer les pages web. Il permet d''intégrer du texte, des images, des liens et d''autres éléments interactifs qui sont interprétés par les navigateurs pour afficher des pages web.', 'HTML', 0, 'Intro HTML ', 2, '2025-02-07 18:20:09.000000'),
(5, 'MySQL est un système de gestion de base de données relationnelle (SGBD) qui permet de stocker et de gérer des données de manière structurée. Il utilise le langage SQL (Structured Query Language) pour interagir avec les bases de données en effectuant des requêtes de type SELECT, INSERT, UPDATE et DELETE.', 'SQL', 1, 'bases de données avec MySQL', 5, '2025-02-07 18:22:11.000000'),
(6, 'JavaScript est un langage de programmation utilisé pour rendre les pages web interactives. Les fonctions en JavaScript permettent de regrouper un ensemble d''instructions sous un même nom et de les exécuter à la demande. Elles peuvent prendre des paramètres et retourner des valeurs.', 'JavaScript', 1, 'Les fonctions en JavaScript', 8, '2025-02-07 18:26:10.000000'),
(7, 'Laravel est un framework PHP permettant de créer des applications web robustes. Il facilite la création d''API grâce à son architecture MVC et son support natif des routes et des contrôleurs. Il offre également une gestion efficace des bases de données avec Eloquent ORM.', 'PHP', 1, ' API avec Laravel', 6, '2025-02-07 18:27:29.000000'),
(8, 'un test uniquement ', 'Python', 0, 'yeste ', 1, '2025-02-08 13:34:50.000000'),
(9, 'ceci est un test ', 'Flutter', 0, 'introduction à firebase', 13, '2025-02-08 14:33:07.000000');

-- Structure et données de la table commentaire
CREATE TABLE if not exists `commentaire` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `contenu` varchar(255) NOT NULL,
  `date_creation` datetime(6) NOT NULL,
  `note_id` bigint(20) NOT NULL,
  `utilisateur_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7xmsfrgnlagssdl5dpo59n0ya` (`note_id`),
  KEY `FKfkx1pegfdsd6e3cp2wblsc5jf` (`utilisateur_id`),
  CONSTRAINT `FK7xmsfrgnlagssdl5dpo59n0ya` FOREIGN KEY (`note_id`) REFERENCES `note` (`id`),
  CONSTRAINT `FKfkx1pegfdsd6e3cp2wblsc5jf` FOREIGN KEY (`utilisateur_id`) REFERENCES `utilisateur` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `commentaire` (`id`, `contenu`, `date_creation`, `note_id`, `utilisateur_id`) VALUES
(1, 'salut ici ', '2025-02-07 19:49:14.000000', 7, 1),
(2, 'c''est bien', '2025-02-08 11:21:46.000000', 6, 5),
(3, 'comment', '2025-02-08 11:22:08.000000', 6, 5);

-- Structure et données de la table partage_note
CREATE TABLE if not exists `partage_note` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_partage` datetime(6) NOT NULL,
  `utilisateur_destinataire_id` bigint(20) DEFAULT NULL,
  `note_id` bigint(20) NOT NULL,
  `utilisateur_partageur_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcsjr5h34d4vbebe8x6l8t7ocp` (`utilisateur_destinataire_id`),
  KEY `FKeh03i2o2eayy71gcuwshag3ia` (`note_id`),
  KEY `FKbgas34vj1aeus3skd9p14lydo` (`utilisateur_partageur_id`),
  CONSTRAINT `FKbgas34vj1aeus3skd9p14lydo` FOREIGN KEY (`utilisateur_partageur_id`) REFERENCES `utilisateur` (`id`),
  CONSTRAINT `FKcsjr5h34d4vbebe8x6l8t7ocp` FOREIGN KEY (`utilisateur_destinataire_id`) REFERENCES `utilisateur` (`id`),
  CONSTRAINT `FKeh03i2o2eayy71gcuwshag3ia` FOREIGN KEY (`note_id`) REFERENCES `note` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `partage_note` (`id`, `date_partage`, `utilisateur_destinataire_id`, `note_id`, `utilisateur_partageur_id`) VALUES
(1, '2025-02-07 19:14:21.000000', 3, 2, 1),
(2, '2025-02-08 13:35:15.000000', 6, 8, 1),
(3, '2025-02-08 14:21:36.000000', 1, 3, 8),
(4, '2025-02-08 14:34:47.000000', 1, 3, 13);

-- Réactiver les contraintes de clés étrangères
SET FOREIGN_KEY_CHECKS=1; 