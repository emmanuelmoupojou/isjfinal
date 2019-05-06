/*
 Navicat Premium Data Transfer

 Source Server         : test
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : localhost:3306
 Source Schema         : isj

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 06/05/2019 09:00:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for annee_academique
-- ----------------------------
DROP TABLE IF EXISTS `annee_academique`;
CREATE TABLE `annee_academique`  (
  `code` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_cloture` datetime(0) NULL DEFAULT NULL,
  `date_creation` datetime(0) NOT NULL,
  `date_debut` datetime(0) NULL DEFAULT NULL,
  `date_modification` datetime(0) NOT NULL,
  `description` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `libelle` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `signature` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `statut_vie` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `createur` bigint(20) NOT NULL,
  `modificateur` bigint(20) NOT NULL,
  PRIMARY KEY (`code`) USING BTREE,
  UNIQUE INDEX `signature`(`signature`) USING BTREE,
  INDEX `FK_annee_academique_createur`(`createur`) USING BTREE,
  INDEX `FK_annee_academique_modificateur`(`modificateur`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 4 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of annee_academique
-- ----------------------------
INSERT INTO `annee_academique` VALUES (1, '2020-05-08 00:00:00', '2019-04-08 13:32:34', '2019-09-02 00:00:00', '2019-04-08 13:32:34', NULL, NULL, 'ACTIVE', 'ACTIVE', 1, 1);
INSERT INTO `annee_academique` VALUES (2, '2021-05-03 00:00:00', '2019-04-08 13:36:43', '2020-09-01 00:00:00', '2019-04-08 13:36:43', NULL, NULL, NULL, 'ACTIVE', 1, 1);
INSERT INTO `annee_academique` VALUES (3, '2022-05-02 00:00:00', '2019-04-08 13:39:37', '2021-09-01 00:00:00', '2019-04-08 13:39:37', NULL, NULL, NULL, 'ACTIVE', 1, 1);

-- ----------------------------
-- Table structure for anonymat
-- ----------------------------
DROP TABLE IF EXISTS `anonymat`;
CREATE TABLE `anonymat`  (
  `code` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_creation` datetime(0) NOT NULL,
  `date_modification` datetime(0) NOT NULL,
  `description` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `libelle` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `numero_anonymat` int(11) NULL DEFAULT NULL,
  `signature` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `statut_vie` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `createur` bigint(20) NOT NULL,
  `evaluation` bigint(20) NULL DEFAULT NULL,
  `modificateur` bigint(20) NOT NULL,
  `note` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`code`) USING BTREE,
  UNIQUE INDEX `signature`(`signature`) USING BTREE,
  INDEX `FK_anonymat_createur`(`createur`) USING BTREE,
  INDEX `FK_anonymat_note`(`note`) USING BTREE,
  INDEX `FK_anonymat_modificateur`(`modificateur`) USING BTREE,
  INDEX `FK_anonymat_evaluation`(`evaluation`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 8 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of anonymat
-- ----------------------------
INSERT INTO `anonymat` VALUES (1, '2019-04-22 01:10:16', '2019-04-22 01:10:16', NULL, NULL, 1, NULL, 'ACTIVE', 1, 1, 1, 1);
INSERT INTO `anonymat` VALUES (2, '2019-04-22 01:13:41', '2019-04-22 01:13:41', NULL, NULL, 6, NULL, 'ACTIVE', 1, 1, 1, 2);
INSERT INTO `anonymat` VALUES (3, '2019-04-22 01:13:41', '2019-04-22 01:13:41', NULL, NULL, 7, NULL, 'ACTIVE', 1, 5, 1, 4);
INSERT INTO `anonymat` VALUES (4, '2019-04-22 01:13:41', '2019-04-22 01:13:41', NULL, NULL, 8, NULL, 'ACTIVE', 1, 6, 1, 5);
INSERT INTO `anonymat` VALUES (5, '2019-04-22 01:13:41', '2019-04-22 01:13:41', NULL, NULL, 9, NULL, 'ACTIVE', 1, 2, 1, 3);

-- ----------------------------
-- Table structure for candidat
-- ----------------------------
DROP TABLE IF EXISTS `candidat`;
CREATE TABLE `candidat`  (
  `code` bigint(20) NOT NULL AUTO_INCREMENT,
  `DTYPE` varchar(31) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `date_creation` datetime(0) NOT NULL,
  `date_modification` datetime(0) NOT NULL,
  `date_naissance` datetime(0) NOT NULL,
  `description` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `ecole_origine` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `libelle` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `nom` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `nom_de_la_mere` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `nom_du_pere` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `prenom` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `profession_de_la_mere` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `profession_du_pere` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `region_origine` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `sexe` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `signature` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `statut` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `statut_vie` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `telephone` int(11) NOT NULL,
  `telephone_de_la_mere` int(11) NULL DEFAULT NULL,
  `telephone_du_pere` int(11) NULL DEFAULT NULL,
  `classe` bigint(20) NULL DEFAULT NULL,
  `createur` bigint(20) NOT NULL,
  `modificateur` bigint(20) NOT NULL,
  PRIMARY KEY (`code`) USING BTREE,
  UNIQUE INDEX `email`(`email`) USING BTREE,
  UNIQUE INDEX `telephone`(`telephone`) USING BTREE,
  UNIQUE INDEX `signature`(`signature`) USING BTREE,
  INDEX `FK_candidat_classe`(`classe`) USING BTREE,
  INDEX `FK_candidat_modificateur`(`modificateur`) USING BTREE,
  INDEX `FK_candidat_createur`(`createur`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 7 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of candidat
-- ----------------------------
INSERT INTO `candidat` VALUES (1, 'Etudiant', '2019-04-08 13:13:03', '2019-04-08 13:13:03', '1996-04-01 00:00:00', NULL, 'Excellence', 'anthonyfouda@gmail.com', NULL, 'FOUDA', 'GOURIOU', 'FOUDA', 'ANTHONY', NULL, NULL, NULL, 'MASCULIN', NULL, 'ACTIVE', 'ACTIVE', 691063708, NULL, NULL, 3, 1, 1);
INSERT INTO `candidat` VALUES (2, 'Etudiant', '2019-04-08 13:19:18', '2019-04-08 13:19:18', '1995-04-17 00:00:00', NULL, 'NESCAS', 'nsola@gmail.com', NULL, 'nsola', NULL, 'OYANE', 'STAHNN', NULL, NULL, NULL, 'MASCULIN', NULL, 'ACTIVE', 'ACTIVE', 698745632, NULL, NULL, 4, 1, 1);
INSERT INTO `candidat` VALUES (3, 'Etudiant', '2019-04-08 13:23:46', '2019-04-08 13:23:46', '1997-08-16 00:00:00', NULL, 'vogt', 'cena@gmail.com', NULL, 'SONG', 'SONG', NULL, 'JUNIOR', NULL, NULL, NULL, 'MASCULIN', NULL, 'ACTIVE', 'ACTIVE', 658974121, NULL, NULL, 4, 1, 1);
INSERT INTO `candidat` VALUES (4, 'Etudiant', '2019-04-20 12:38:47', '2019-04-20 12:38:53', '1996-02-14 12:39:03', NULL, 'vogt', 'ntonga@gmail.com', NULL, 'ntonga', NULL, 'NTONGA', 'JEANNE', NULL, NULL, NULL, 'FEMININ', NULL, 'ACTIVE', 'ACTIVE', 698959632, NULL, NULL, 5, 1, 1);
INSERT INTO `candidat` VALUES (5, 'Etudiant', '2019-04-21 23:20:45', '2019-04-21 23:20:49', '1999-03-17 23:20:53', NULL, 'Saint benoit', 'pie@gmail.com', NULL, 'Mandeng', NULL, 'Mandeng', 'IRENE', NULL, NULL, NULL, 'FEMININ', NULL, 'ACTIVE', 'ACTIVE', 698784512, NULL, NULL, 6, 1, 1);
INSERT INTO `candidat` VALUES (6, 'Etudiant', '2019-04-21 23:23:02', '2019-04-21 23:23:06', '1997-10-07 23:23:10', NULL, 'Advantiste', 'anne@gmail.com', NULL, 'Mboa', 'NANDA', 'Mboa', 'ANNE', NULL, NULL, NULL, 'FEMININ', NULL, 'ACTIVE', 'ACTIVE', 632541258, NULL, NULL, 3, 1, 1);

-- ----------------------------
-- Table structure for classe
-- ----------------------------
DROP TABLE IF EXISTS `classe`;
CREATE TABLE `classe`  (
  `code` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_creation` datetime(0) NOT NULL,
  `date_modification` datetime(0) NOT NULL,
  `description` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `libelle` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `signature` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `statut_vie` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `createur` bigint(20) NOT NULL,
  `modificateur` bigint(20) NOT NULL,
  `niveau` bigint(20) NULL DEFAULT NULL,
  `specialite` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`code`) USING BTREE,
  UNIQUE INDEX `signature`(`signature`) USING BTREE,
  INDEX `FK_classe_createur`(`createur`) USING BTREE,
  INDEX `FK_classe_niveau`(`niveau`) USING BTREE,
  INDEX `FK_classe_modificateur`(`modificateur`) USING BTREE,
  INDEX `FK_classe_specialite`(`specialite`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 7 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of classe
-- ----------------------------
INSERT INTO `classe` VALUES (4, '2019-04-20 12:45:51', '2019-04-20 12:45:55', NULL, NULL, NULL, 'ACTIVE', 1, 1, 2, 2);
INSERT INTO `classe` VALUES (3, '2019-04-20 12:45:16', '2019-04-20 12:45:20', NULL, NULL, NULL, 'ACTIVE', 1, 1, 1, 1);
INSERT INTO `classe` VALUES (5, '2019-04-20 12:46:36', '2019-04-20 12:46:41', NULL, NULL, NULL, 'ACTIVE', 1, 1, 3, 2);
INSERT INTO `classe` VALUES (6, '2019-04-20 12:47:14', '2019-04-20 12:47:18', NULL, NULL, NULL, 'ACTIVE', 1, 1, 4, 1);

-- ----------------------------
-- Table structure for discipline
-- ----------------------------
DROP TABLE IF EXISTS `discipline`;
CREATE TABLE `discipline`  (
  `code` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_creation` datetime(0) NOT NULL,
  `date_modification` datetime(0) NOT NULL,
  `description` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `libelle` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `nb_heures_absences` int(11) NULL DEFAULT NULL,
  `nb_retards` int(11) NULL DEFAULT NULL,
  `signature` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `statut_vie` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `createur` bigint(20) NOT NULL,
  `etudiant` bigint(20) NULL DEFAULT NULL,
  `modificateur` bigint(20) NOT NULL,
  `semestre` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`code`) USING BTREE,
  UNIQUE INDEX `signature`(`signature`) USING BTREE,
  INDEX `FK_discipline_modificateur`(`modificateur`) USING BTREE,
  INDEX `FK_discipline_createur`(`createur`) USING BTREE,
  INDEX `FK_discipline_semestre`(`semestre`) USING BTREE,
  INDEX `FK_discipline_etudiant`(`etudiant`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 13 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of discipline
-- ----------------------------
INSERT INTO `discipline` VALUES (1, '2019-04-22 00:00:30', '2019-04-22 00:00:35', NULL, NULL, 10, 7, NULL, 'ACTIVE', 1, 1, 1, 1);
INSERT INTO `discipline` VALUES (2, '2019-04-22 00:02:21', '2019-04-22 00:02:21', NULL, NULL, 5, 18, NULL, 'ACTIVE', 1, 1, 1, 2);
INSERT INTO `discipline` VALUES (3, '2019-04-22 00:04:00', '2019-04-22 00:04:00', NULL, NULL, 0, 5, NULL, 'ACTIVE', 1, 2, 1, 1);
INSERT INTO `discipline` VALUES (4, '2019-04-22 00:07:30', '2019-04-22 00:07:30', NULL, NULL, 25, 8, NULL, 'ACTIVE', 1, 2, 1, 2);
INSERT INTO `discipline` VALUES (5, '2019-04-22 00:07:30', '2019-04-22 00:07:30', NULL, NULL, 2, 4, NULL, 'ACTIVE', 1, 3, 1, 1);
INSERT INTO `discipline` VALUES (6, '2019-04-22 00:07:30', '2019-04-22 00:07:30', NULL, NULL, 0, 0, NULL, 'ACTIVE', 1, 3, 1, 2);
INSERT INTO `discipline` VALUES (7, '2019-04-22 00:07:30', '2019-04-22 00:07:30', NULL, NULL, 35, 18, NULL, 'ACTIVE', 1, 4, 1, 1);
INSERT INTO `discipline` VALUES (8, '2019-04-22 00:07:30', '2019-04-22 00:07:30', NULL, NULL, 2, 0, NULL, 'ACTIVE', 1, 4, 1, 2);
INSERT INTO `discipline` VALUES (9, '2019-04-22 00:07:30', '2019-04-22 00:07:30', NULL, NULL, 3, 6, NULL, 'ACTIVE', 1, 5, 1, 1);
INSERT INTO `discipline` VALUES (10, '2019-04-22 00:07:30', '2019-04-22 00:07:30', NULL, NULL, 0, 0, NULL, 'ACTIVE', 1, 5, 1, 2);
INSERT INTO `discipline` VALUES (11, '2019-04-22 00:07:30', '2019-04-22 00:07:30', NULL, NULL, 1, 1, NULL, 'ACTIVE', 1, 6, 1, 1);
INSERT INTO `discipline` VALUES (12, '2019-04-22 00:07:30', '2019-04-22 00:07:30', NULL, NULL, 0, 21, NULL, 'ACTIVE', 1, 6, 1, 2);

-- ----------------------------
-- Table structure for droit
-- ----------------------------
DROP TABLE IF EXISTS `droit`;
CREATE TABLE `droit`  (
  `code` bigint(20) NOT NULL AUTO_INCREMENT,
  `categorie` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `date_creation` datetime(0) NOT NULL,
  `date_modification` datetime(0) NOT NULL,
  `description` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `ecriture` tinyint(1) NULL DEFAULT 0,
  `lecture` tinyint(1) NULL DEFAULT 0,
  `libelle` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `modification` tinyint(1) NULL DEFAULT 0,
  `signature` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `statut_vie` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `suppression` tinyint(1) NULL DEFAULT 0,
  `createur` bigint(20) NOT NULL,
  `modificateur` bigint(20) NOT NULL,
  `role` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`code`) USING BTREE,
  UNIQUE INDEX `signature`(`signature`) USING BTREE,
  INDEX `FK_droit_modificateur`(`modificateur`) USING BTREE,
  INDEX `FK_droit_role`(`role`) USING BTREE,
  INDEX `FK_droit_createur`(`createur`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 5 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of droit
-- ----------------------------
INSERT INTO `droit` VALUES (1, '', '2019-04-22 02:00:55', '2019-04-22 02:00:55', NULL, 1, 1, NULL, 1, NULL, 'ACTIVE', 1, 1, 1, 2);
INSERT INTO `droit` VALUES (2, '', '2019-04-22 02:01:59', '2019-04-22 02:01:59', NULL, 0, 1, NULL, 0, NULL, 'ACTIVE', 0, 1, 1, 3);
INSERT INTO `droit` VALUES (3, '', '2019-04-22 02:03:51', '2019-04-22 02:03:51', NULL, 1, 1, NULL, 0, NULL, 'ACTIVE', 0, 1, 1, 1);
INSERT INTO `droit` VALUES (4, '', '2019-04-22 02:04:37', '2019-04-22 02:04:37', NULL, 1, 1, NULL, 1, NULL, 'ACTIVE', 0, 1, 1, 4);

-- ----------------------------
-- Table structure for email
-- ----------------------------
DROP TABLE IF EXISTS `email`;
CREATE TABLE `email`  (
  `code` bigint(20) NOT NULL AUTO_INCREMENT,
  `objet` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`code`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 152 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of email
-- ----------------------------
INSERT INTO `email` VALUES (1, 'salutation');
INSERT INTO `email` VALUES (151, 'test');
INSERT INTO `email` VALUES (2, 'salutation 2');
INSERT INTO `email` VALUES (3, 'salution3');

-- ----------------------------
-- Table structure for enseignant
-- ----------------------------
DROP TABLE IF EXISTS `enseignant`;
CREATE TABLE `enseignant`  (
  `code` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_creation` datetime(0) NOT NULL,
  `date_modification` datetime(0) NOT NULL,
  `date_naissance` datetime(0) NOT NULL,
  `description` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `libelle` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `nom` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `prenom` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `qualification` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `sexe` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `signature` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `statut` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `statut_vie` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `telephone` int(11) NOT NULL,
  `createur` bigint(20) NOT NULL,
  `modificateur` bigint(20) NOT NULL,
  PRIMARY KEY (`code`) USING BTREE,
  UNIQUE INDEX `email`(`email`) USING BTREE,
  UNIQUE INDEX `telephone`(`telephone`) USING BTREE,
  UNIQUE INDEX `signature`(`signature`) USING BTREE,
  INDEX `FK_enseignant_createur`(`createur`) USING BTREE,
  INDEX `FK_enseignant_modificateur`(`modificateur`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 7 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of enseignant
-- ----------------------------
INSERT INTO `enseignant` VALUES (1, '2019-04-11 08:37:29', '2019-04-11 08:37:29', '1968-04-10 00:00:00', NULL, 'kiampi@gmail.com', NULL, 'Kiampi', NULL, 'Dr', 'MASCULIN', NULL, 'ACTIVE', 'ACTIVE', 699333333, 1, 1);
INSERT INTO `enseignant` VALUES (2, '2019-04-11 08:38:57', '2019-04-11 08:38:57', '1968-10-04 00:00:00', NULL, 'SAHA@gmail.com', NULL, 'Saha', NULL, 'MASTER', 'MASCULIN', NULL, 'ACTIVE', 'ACTIVE', 699989796, 1, 1);
INSERT INTO `enseignant` VALUES (3, '2019-04-11 08:40:49', '2019-04-11 08:40:49', '1975-04-09 00:00:00', NULL, 'Tchio@gmail.com', NULL, 'Tchio', 'Corneille', NULL, 'MASCULIN', NULL, 'ACTIVE', 'ACTIVE', 601063708, 1, 1);
INSERT INTO `enseignant` VALUES (4, '2019-04-11 08:42:14', '2019-04-11 08:42:14', '1964-03-09 00:00:00', NULL, 'Mballa@gmail.com', NULL, 'Mballa', 'Fabien', 'Pr', 'MASCULIN', NULL, 'ACTIVE', 'ACTIVE', 601043507, 1, 1);
INSERT INTO `enseignant` VALUES (5, '2019-04-11 08:46:09', '2019-04-11 08:46:09', '1963-08-21 00:00:00', NULL, 'kwamo@gmail.com', NULL, 'Kouamo', 'Georges', 'Dr', 'MASCULIN', NULL, 'ACTIVE', 'ACTIVE', 698451425, 1, 1);
INSERT INTO `enseignant` VALUES (6, '2019-04-11 08:47:57', '2019-04-11 08:47:57', '1963-08-21 00:00:00', NULL, 'Tchantcho@gmail.com', NULL, 'Tchantcho', 'Hugues', 'Dr', 'MASCULIN', NULL, 'ACTIVE', 'ACTIVE', 658471425, 1, 1);

-- ----------------------------
-- Table structure for enseignement
-- ----------------------------
DROP TABLE IF EXISTS `enseignement`;
CREATE TABLE `enseignement`  (
  `code` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_creation` datetime(0) NOT NULL,
  `date_modification` datetime(0) NOT NULL,
  `description` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `heures_de_cours` int(11) NULL DEFAULT NULL,
  `libelle` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `programme_de_cours` varchar(1020) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `signature` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `statut_vie` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `createur` bigint(20) NOT NULL,
  `modificateur` bigint(20) NOT NULL,
  `semestre` bigint(20) NULL DEFAULT NULL,
  `ue` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`code`) USING BTREE,
  UNIQUE INDEX `signature`(`signature`) USING BTREE,
  INDEX `FK_enseignement_ue`(`ue`) USING BTREE,
  INDEX `FK_enseignement_modificateur`(`modificateur`) USING BTREE,
  INDEX `FK_enseignement_createur`(`createur`) USING BTREE,
  INDEX `FK_enseignement_semestre`(`semestre`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 7 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of enseignement
-- ----------------------------
INSERT INTO `enseignement` VALUES (1, '2019-04-11 08:20:57', '2019-04-11 08:20:57', NULL, 35, 'ARHITECTURE', NULL, NULL, 'ACTIVE', 1, 1, 1, 2);
INSERT INTO `enseignement` VALUES (2, '2019-04-11 08:25:20', '2019-04-11 08:25:20', NULL, 35, 'MATH INFO', NULL, NULL, 'ACTIVE', 1, 1, 1, 3);
INSERT INTO `enseignement` VALUES (3, '2019-04-11 08:25:20', '2019-04-11 08:25:20', NULL, 35, 'JAVA I', NULL, NULL, 'ACTIVE', 1, 1, 2, 5);
INSERT INTO `enseignement` VALUES (4, '2019-04-11 08:25:20', '2019-04-11 08:25:20', NULL, 35, 'PROG C', NULL, NULL, 'ACTIVE', 1, 1, 2, 6);
INSERT INTO `enseignement` VALUES (5, '2019-04-11 08:25:20', '2019-04-11 08:25:20', NULL, 35, 'SYSTEME D INFORMATION', NULL, NULL, 'ACTIVE', 1, 1, 1, 1);
INSERT INTO `enseignement` VALUES (6, '2019-04-11 08:25:20', '2019-04-11 08:25:20', NULL, 35, 'MATH DISCRETES', NULL, NULL, 'ACTIVE', 1, 1, 1, 4);

-- ----------------------------
-- Table structure for enseignement_enseignant
-- ----------------------------
DROP TABLE IF EXISTS `enseignement_enseignant`;
CREATE TABLE `enseignement_enseignant`  (
  `code_enseignant` bigint(20) NOT NULL,
  `code_enseignement` bigint(20) NOT NULL,
  PRIMARY KEY (`code_enseignant`, `code_enseignement`) USING BTREE,
  INDEX `FK_enseignement_enseignant_code_enseignement`(`code_enseignement`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of enseignement_enseignant
-- ----------------------------
INSERT INTO `enseignement_enseignant` VALUES (1, 2);
INSERT INTO `enseignement_enseignant` VALUES (2, 1);
INSERT INTO `enseignement_enseignant` VALUES (3, 4);
INSERT INTO `enseignement_enseignant` VALUES (4, 5);
INSERT INTO `enseignement_enseignant` VALUES (5, 3);
INSERT INTO `enseignement_enseignant` VALUES (6, 6);

-- ----------------------------
-- Table structure for envoi_message
-- ----------------------------
DROP TABLE IF EXISTS `envoi_message`;
CREATE TABLE `envoi_message`  (
  `code` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_creation` datetime(0) NOT NULL,
  `date_envoi` datetime(0) NULL DEFAULT NULL,
  `date_modification` datetime(0) NOT NULL,
  `description` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `libelle` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `signature` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `statut` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `statut_vie` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `candidat` bigint(20) NULL DEFAULT NULL,
  `createur` bigint(20) NOT NULL,
  `modificateur` bigint(20) NOT NULL,
  `message` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`code`) USING BTREE,
  UNIQUE INDEX `signature`(`signature`) USING BTREE,
  INDEX `FK_envoi_message_createur`(`createur`) USING BTREE,
  INDEX `FK_envoi_message_candidat`(`candidat`) USING BTREE,
  INDEX `FK_envoi_message_modificateur`(`modificateur`) USING BTREE,
  INDEX `FK_envoi_message_message`(`message`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 402 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of envoi_message
-- ----------------------------
INSERT INTO `envoi_message` VALUES (1, '2019-04-22 01:16:49', '2019-04-22 01:16:49', '2019-04-22 01:16:49', NULL, NULL, NULL, 'SUCCES', 'ACTIVE', 1, 1, 1, 1);
INSERT INTO `envoi_message` VALUES (2, '2019-04-22 01:18:05', '2019-04-22 01:18:05', '2019-04-22 01:18:05', NULL, NULL, NULL, 'SUCCES', 'ACTIVE', 2, 1, 1, 2);
INSERT INTO `envoi_message` VALUES (3, '2019-04-22 01:18:05', '2019-04-22 01:18:05', '2019-04-22 01:18:05', NULL, NULL, NULL, 'SUCCES', 'ACTIVE', 3, 1, 1, 3);
INSERT INTO `envoi_message` VALUES (4, '2019-04-22 01:18:05', '2019-04-22 01:18:05', '2019-04-22 01:18:05', NULL, NULL, NULL, 'ECHEC', 'ACTIVE', 4, 1, 1, 4);
INSERT INTO `envoi_message` VALUES (5, '2019-04-22 01:18:05', '2019-04-22 01:18:05', '2019-04-22 01:18:05', NULL, NULL, NULL, 'ECHEC', 'ACTIVE', 5, 1, 1, 5);
INSERT INTO `envoi_message` VALUES (6, '2019-04-22 01:18:05', '2019-04-22 01:18:05', '2019-04-22 01:18:05', NULL, NULL, NULL, 'SUCCES', 'ACTIVE', 6, 1, 1, 6);
INSERT INTO `envoi_message` VALUES (401, '2019-04-26 12:58:26', '2019-04-26 12:58:26', '2019-04-26 12:58:26', 'test', 'test', '31', 'SUCCES', 'ACTIVE', 1, 1, 1, 151);

-- ----------------------------
-- Table structure for est_inscrit
-- ----------------------------
DROP TABLE IF EXISTS `est_inscrit`;
CREATE TABLE `est_inscrit`  (
  `code` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_creation` datetime(0) NOT NULL,
  `date_modification` datetime(0) NOT NULL,
  `description` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `libelle` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `signature` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `statut` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `statut_vie` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `candidat_inscrit` bigint(20) NULL DEFAULT NULL,
  `createur` bigint(20) NOT NULL,
  `enseignement` bigint(20) NULL DEFAULT NULL,
  `modificateur` bigint(20) NOT NULL,
  PRIMARY KEY (`code`) USING BTREE,
  UNIQUE INDEX `signature`(`signature`) USING BTREE,
  INDEX `FK_est_inscrit_candidat_inscrit`(`candidat_inscrit`) USING BTREE,
  INDEX `FK_est_inscrit_createur`(`createur`) USING BTREE,
  INDEX `FK_est_inscrit_modificateur`(`modificateur`) USING BTREE,
  INDEX `FK_est_inscrit_enseignement`(`enseignement`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 13 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of est_inscrit
-- ----------------------------
INSERT INTO `est_inscrit` VALUES (3, '2019-04-22 00:47:36', '2019-04-22 00:47:36', NULL, NULL, NULL, 'VALIDE', 'ACTIVE', 1, 1, 3, 1);
INSERT INTO `est_inscrit` VALUES (4, '2019-04-22 00:47:36', '2019-04-22 00:47:36', NULL, NULL, NULL, 'VALIDE', 'ACTIVE', 1, 1, 4, 1);
INSERT INTO `est_inscrit` VALUES (5, '2019-04-22 00:50:11', '2019-04-22 00:50:11', NULL, NULL, NULL, 'VALIDE', 'ACTIVE', 2, 1, 1, 1);
INSERT INTO `est_inscrit` VALUES (6, '2019-04-22 00:55:17', '2019-04-22 00:55:17', NULL, NULL, NULL, 'VALIDE', 'ACTIVE', 2, 1, 1, 1);
INSERT INTO `est_inscrit` VALUES (7, '2019-04-22 00:55:17', '2019-04-22 00:55:17', NULL, NULL, NULL, 'VALIDE', 'ACTIVE', 3, 1, 1, 1);
INSERT INTO `est_inscrit` VALUES (8, '2019-04-22 00:55:17', '2019-04-22 00:55:17', NULL, NULL, NULL, 'VALIDE', 'ACTIVE', 4, 1, 2, 1);
INSERT INTO `est_inscrit` VALUES (9, '2019-04-22 00:55:17', '2019-04-22 00:55:17', NULL, NULL, NULL, 'VALIDE', 'ACTIVE', 4, 1, 6, 1);
INSERT INTO `est_inscrit` VALUES (10, '2019-04-22 00:55:17', '2019-04-22 00:55:17', NULL, NULL, NULL, 'VALIDE', 'ACTIVE', 6, 1, 3, 1);
INSERT INTO `est_inscrit` VALUES (11, '2019-04-22 00:55:17', '2019-04-22 00:55:17', NULL, NULL, NULL, 'VALIDE', 'ACTIVE', 6, 1, 4, 1);
INSERT INTO `est_inscrit` VALUES (12, '2019-04-22 00:55:17', '2019-04-22 00:55:17', NULL, NULL, NULL, 'VALIDE', 'ACTIVE', 6, 1, 5, 1);

-- ----------------------------
-- Table structure for etudiant
-- ----------------------------
DROP TABLE IF EXISTS `etudiant`;
CREATE TABLE `etudiant`  (
  `code` bigint(20) NOT NULL AUTO_INCREMENT,
  `code_authentification` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `matricule` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`code`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 7 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of etudiant
-- ----------------------------
INSERT INTO `etudiant` VALUES (1, '1', 'ISJ0001');
INSERT INTO `etudiant` VALUES (2, '2', 'ISJ0002');
INSERT INTO `etudiant` VALUES (3, '3', 'ISJ0003');
INSERT INTO `etudiant` VALUES (4, '4', 'ISJ0004');
INSERT INTO `etudiant` VALUES (5, '5', 'ISJ0005');
INSERT INTO `etudiant` VALUES (6, '6', 'ISJ0006');

-- ----------------------------
-- Table structure for evaluation
-- ----------------------------
DROP TABLE IF EXISTS `evaluation`;
CREATE TABLE `evaluation`  (
  `code` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_creation` datetime(0) NOT NULL,
  `date_evaluation` datetime(0) NULL DEFAULT NULL,
  `date_modification` datetime(0) NOT NULL,
  `description` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `libelle` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `signature` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `statut` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `statut_vie` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `createur` bigint(20) NOT NULL,
  `modificateur` bigint(20) NOT NULL,
  `type_evaluation` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`code`) USING BTREE,
  UNIQUE INDEX `signature`(`signature`) USING BTREE,
  INDEX `FK_evaluation_modificateur`(`modificateur`) USING BTREE,
  INDEX `FK_evaluation_type_evaluation`(`type_evaluation`) USING BTREE,
  INDEX `FK_evaluation_createur`(`createur`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 7 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of evaluation
-- ----------------------------
INSERT INTO `evaluation` VALUES (1, '2019-04-22 00:14:46', '2019-12-10 07:00:00', '2019-04-22 00:14:46', NULL, NULL, NULL, 'ACTIVE', 'ACTIVE', 1, 1, 1);
INSERT INTO `evaluation` VALUES (2, '2019-04-22 00:17:57', '2019-12-11 07:00:00', '2019-04-22 00:17:57', NULL, NULL, NULL, 'ACTIVE', 'ACTIVE', 1, 1, 4);
INSERT INTO `evaluation` VALUES (6, '2019-04-22 00:22:43', '2019-12-13 10:00:00', '2019-04-22 00:22:43', NULL, NULL, NULL, 'ACTIVE', 'ACTIVE', 1, 1, 19);
INSERT INTO `evaluation` VALUES (5, '2019-04-22 00:22:29', '2019-12-13 08:00:00', '2019-04-22 00:22:29', NULL, NULL, NULL, 'ACTIVE', 'ACTIVE', 1, 1, 16);

-- ----------------------------
-- Table structure for filiere
-- ----------------------------
DROP TABLE IF EXISTS `filiere`;
CREATE TABLE `filiere`  (
  `code` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_creation` datetime(0) NOT NULL,
  `date_modification` datetime(0) NOT NULL,
  `description` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `libelle` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `signature` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `statut_vie` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `createur` bigint(20) NOT NULL,
  `modificateur` bigint(20) NOT NULL,
  PRIMARY KEY (`code`) USING BTREE,
  UNIQUE INDEX `signature`(`signature`) USING BTREE,
  INDEX `FK_filiere_createur`(`createur`) USING BTREE,
  INDEX `FK_filiere_modificateur`(`modificateur`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 4 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of filiere
-- ----------------------------
INSERT INTO `filiere` VALUES (1, '2019-04-11 07:53:49', '2019-04-11 07:53:49', NULL, 'LICENCE', NULL, 'ACTIVE', 1, 1);
INSERT INTO `filiere` VALUES (3, '2019-04-11 07:54:19', '2019-04-11 07:54:19', NULL, 'INGENIEUR', NULL, 'ACTIVE', 1, 1);

-- ----------------------------
-- Table structure for historique_note
-- ----------------------------
DROP TABLE IF EXISTS `historique_note`;
CREATE TABLE `historique_note`  (
  `code` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_creation` datetime(0) NOT NULL,
  `date_modification` datetime(0) NOT NULL,
  `description` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `libelle` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `signature` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `statut_vie` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `valeur_note` double NOT NULL,
  `createur` bigint(20) NOT NULL,
  `modificateur` bigint(20) NOT NULL,
  `note` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`code`) USING BTREE,
  UNIQUE INDEX `signature`(`signature`) USING BTREE,
  INDEX `FK_historique_note_note`(`note`) USING BTREE,
  INDEX `FK_historique_note_modificateur`(`modificateur`) USING BTREE,
  INDEX `FK_historique_note_createur`(`createur`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 6 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of historique_note
-- ----------------------------
INSERT INTO `historique_note` VALUES (1, '2019-04-22 01:25:46', '2019-04-22 01:25:46', NULL, NULL, NULL, 'ACTIVE', 17, 1, 1, 1);
INSERT INTO `historique_note` VALUES (2, '2019-04-22 01:28:03', '2019-04-22 01:28:03', NULL, NULL, NULL, 'ACTIVE', 17, 1, 1, 2);
INSERT INTO `historique_note` VALUES (3, '2019-04-22 01:28:03', '2019-04-22 01:28:03', NULL, NULL, NULL, 'ACTIVE', 14, 1, 1, 3);
INSERT INTO `historique_note` VALUES (4, '2019-04-22 01:28:03', '2019-04-22 01:28:03', NULL, NULL, NULL, 'ACTIVE', 8, 1, 1, 4);
INSERT INTO `historique_note` VALUES (5, '2019-04-22 01:28:03', '2019-04-22 01:28:03', NULL, NULL, NULL, 'ACTIVE', 11, 1, 1, 5);

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `code` bigint(20) NOT NULL AUTO_INCREMENT,
  `DTYPE` varchar(31) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `contenu` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `date_creation` datetime(0) NOT NULL,
  `date_modification` datetime(0) NOT NULL,
  `description` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `destinataire` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `emetteur` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `libelle` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `signature` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `statut_vie` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `createur` bigint(20) NOT NULL,
  `modificateur` bigint(20) NOT NULL,
  PRIMARY KEY (`code`) USING BTREE,
  UNIQUE INDEX `signature`(`signature`) USING BTREE,
  INDEX `FK_message_createur`(`createur`) USING BTREE,
  INDEX `FK_message_modificateur`(`modificateur`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 152 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (1, 'Email', 'BONjour 1', '2019-04-22 01:20:09', '2019-04-22 01:20:09', NULL, NULL, NULL, NULL, NULL, 'ACTIVE', 1, 1);
INSERT INTO `message` VALUES (2, 'Email', 'BONjour 2', '2019-04-22 01:21:05', '2019-04-22 01:21:05', NULL, NULL, NULL, NULL, NULL, 'ACTIVE', 1, 1);
INSERT INTO `message` VALUES (3, 'Email', 'BONjour 3', '2019-04-22 01:21:05', '2019-04-22 01:21:05', NULL, NULL, NULL, NULL, NULL, 'ACTIVE', 1, 1);
INSERT INTO `message` VALUES (4, 'Sms', 'BONjour 4', '2019-04-22 01:21:05', '2019-04-22 01:21:05', NULL, NULL, NULL, NULL, NULL, 'ACTIVE', 1, 1);
INSERT INTO `message` VALUES (5, 'Sms', 'BONjour 5', '2019-04-22 01:21:05', '2019-04-22 01:21:05', NULL, NULL, NULL, NULL, NULL, 'ACTIVE', 1, 1);
INSERT INTO `message` VALUES (6, 'Sms', 'BONjour 6', '2019-04-22 01:21:05', '2019-04-22 01:21:05', NULL, NULL, NULL, NULL, NULL, 'ACTIVE', 1, 1);

-- ----------------------------
-- Table structure for module
-- ----------------------------
DROP TABLE IF EXISTS `module`;
CREATE TABLE `module`  (
  `code` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_creation` datetime(0) NOT NULL,
  `date_modification` datetime(0) NOT NULL,
  `description` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `libelle` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `signature` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `statut_vie` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `createur` bigint(20) NOT NULL,
  `modificateur` bigint(20) NOT NULL,
  PRIMARY KEY (`code`) USING BTREE,
  UNIQUE INDEX `signature`(`signature`) USING BTREE,
  INDEX `FK_Module_modificateur`(`modificateur`) USING BTREE,
  INDEX `FK_Module_createur`(`createur`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 4 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of module
-- ----------------------------
INSERT INTO `module` VALUES (1, '2019-04-11 07:44:06', '2019-04-11 07:44:06', NULL, 'SYSTEME D\'INFORMATION ET ARCHITECTURE', NULL, 'ACTIVE', 1, 1);
INSERT INTO `module` VALUES (2, '2019-04-11 07:44:54', '2019-04-11 07:44:54', NULL, 'MATH APPLIQUEES POUR INFO', NULL, 'ACTIVE', 1, 1);
INSERT INTO `module` VALUES (3, '2019-04-11 07:45:34', '2019-04-11 07:45:34', NULL, 'JAVA1 ET PROGRAMMATION EN C', NULL, 'ACTIVE', 1, 1);

-- ----------------------------
-- Table structure for niveau
-- ----------------------------
DROP TABLE IF EXISTS `niveau`;
CREATE TABLE `niveau`  (
  `code` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_creation` datetime(0) NOT NULL,
  `date_modification` datetime(0) NOT NULL,
  `description` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `libelle` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `numero` int(11) NULL DEFAULT NULL,
  `signature` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `statut_vie` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `createur` bigint(20) NOT NULL,
  `modificateur` bigint(20) NOT NULL,
  PRIMARY KEY (`code`) USING BTREE,
  UNIQUE INDEX `signature`(`signature`) USING BTREE,
  INDEX `FK_Niveau_modificateur`(`modificateur`) USING BTREE,
  INDEX `FK_Niveau_createur`(`createur`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 5 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of niveau
-- ----------------------------
INSERT INTO `niveau` VALUES (1, '2019-04-11 07:50:44', '2019-04-11 07:50:44', 'NIVEAU 1 LICENCE', '', 1, NULL, 'ACTIVE', 1, 1);
INSERT INTO `niveau` VALUES (2, '2019-04-11 07:52:25', '2019-04-11 07:52:25', 'NIVEAU 1 INGENIEUR', '', 1, NULL, 'ACTIVE', 1, 1);
INSERT INTO `niveau` VALUES (3, '2019-04-11 07:52:25', '2019-04-11 07:52:25', 'NIVEAU 2 INGENIEUR', '', 2, NULL, 'ACTIVE', 1, 1);
INSERT INTO `niveau` VALUES (4, '2019-04-11 07:52:25', '2019-04-11 07:52:25', 'NIVEAU 2 LICENCE', '', 2, NULL, 'ACTIVE', 1, 1);

-- ----------------------------
-- Table structure for note
-- ----------------------------
DROP TABLE IF EXISTS `note`;
CREATE TABLE `note`  (
  `code` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_creation` datetime(0) NOT NULL,
  `date_modification` datetime(0) NOT NULL,
  `description` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `libelle` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `numero_table` int(11) NULL DEFAULT NULL,
  `signature` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `statut_vie` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `valeur_note` double NOT NULL,
  `createur` bigint(20) NOT NULL,
  `evaluation` bigint(20) NULL DEFAULT NULL,
  `modificateur` bigint(20) NOT NULL,
  `est_inscrit` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`code`) USING BTREE,
  UNIQUE INDEX `signature`(`signature`) USING BTREE,
  INDEX `FK_note_est_inscrit`(`est_inscrit`) USING BTREE,
  INDEX `FK_note_createur`(`createur`) USING BTREE,
  INDEX `FK_note_modificateur`(`modificateur`) USING BTREE,
  INDEX `FK_note_evaluation`(`evaluation`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 6 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of note
-- ----------------------------
INSERT INTO `note` VALUES (1, '2019-04-22 01:25:46', '2019-04-22 01:25:46', NULL, NULL, 10, NULL, 'ACTIVE', 17, 1, 1, 1, 6);
INSERT INTO `note` VALUES (2, '2019-04-22 01:28:03', '2019-04-22 01:28:03', NULL, NULL, 10, NULL, 'ACTIVE', 17, 1, 1, 1, 7);
INSERT INTO `note` VALUES (3, '2019-04-22 01:28:03', '2019-04-22 01:28:03', NULL, NULL, 15, NULL, 'ACTIVE', 14, 1, 2, 1, 8);
INSERT INTO `note` VALUES (4, '2019-04-22 01:28:03', '2019-04-22 01:28:03', NULL, NULL, 20, NULL, 'ACTIVE', 8, 1, 5, 1, 12);
INSERT INTO `note` VALUES (5, '2019-04-22 01:28:03', '2019-04-22 01:28:03', NULL, NULL, 22, NULL, 'ACTIVE', 11, 1, 6, 1, 9);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `code` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_creation` datetime(0) NOT NULL,
  `date_modification` datetime(0) NOT NULL,
  `description` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `libelle` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `signature` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `statut_vie` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `createur` bigint(20) NOT NULL,
  `modificateur` bigint(20) NOT NULL,
  PRIMARY KEY (`code`) USING BTREE,
  UNIQUE INDEX `signature`(`signature`) USING BTREE,
  INDEX `FK_role_modificateur`(`modificateur`) USING BTREE,
  INDEX `FK_role_createur`(`createur`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 5 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '2019-04-21 23:41:21', '2019-04-21 23:41:21', NULL, NULL, NULL, 'ACTIVE', 1, 1);
INSERT INTO `role` VALUES (2, '2019-04-21 23:42:24', '2019-04-21 23:42:24', NULL, NULL, NULL, 'ACTIVE', 1, 1);
INSERT INTO `role` VALUES (3, '2019-04-21 23:42:24', '2019-04-21 23:42:24', NULL, NULL, NULL, 'ACTIVE', 1, 1);
INSERT INTO `role` VALUES (4, '2019-04-21 23:42:24', '2019-04-21 23:42:24', NULL, NULL, NULL, 'ACTIVE', 1, 1);

-- ----------------------------
-- Table structure for semestre
-- ----------------------------
DROP TABLE IF EXISTS `semestre`;
CREATE TABLE `semestre`  (
  `code` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_cloture` datetime(0) NULL DEFAULT NULL,
  `date_creation` datetime(0) NOT NULL,
  `date_debut` datetime(0) NULL DEFAULT NULL,
  `date_modification` datetime(0) NOT NULL,
  `description` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `libelle` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `signature` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `statut_vie` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `annee_academique` bigint(20) NULL DEFAULT NULL,
  `createur` bigint(20) NOT NULL,
  `modificateur` bigint(20) NOT NULL,
  PRIMARY KEY (`code`) USING BTREE,
  UNIQUE INDEX `signature`(`signature`) USING BTREE,
  INDEX `FK_semestre_modificateur`(`modificateur`) USING BTREE,
  INDEX `FK_semestre_createur`(`createur`) USING BTREE,
  INDEX `FK_semestre_annee_academique`(`annee_academique`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 5 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of semestre
-- ----------------------------
INSERT INTO `semestre` VALUES (1, '2020-01-30 00:00:00', '2019-04-08 13:44:32', '2019-09-02 00:00:00', '2019-04-08 13:44:32', NULL, NULL, NULL, 'ACTIVE', 1, 1, 1);
INSERT INTO `semestre` VALUES (2, '2020-05-18 00:00:00', '2019-04-08 13:54:54', '2020-01-03 00:00:00', '2019-04-08 13:54:54', NULL, NULL, NULL, 'ACTIVE', 1, 1, 1);
INSERT INTO `semestre` VALUES (3, '2021-01-30 00:00:00', '2019-04-21 23:46:20', '2020-09-02 00:00:00', '2019-04-08 13:44:32', NULL, NULL, NULL, 'ACTIVE', 2, 1, 1);
INSERT INTO `semestre` VALUES (4, '2021-05-30 00:00:00', '2019-04-21 23:46:20', '2021-01-02 00:00:00', '2019-04-08 13:44:32', NULL, NULL, NULL, 'ACTIVE', 2, 1, 1);

-- ----------------------------
-- Table structure for sequence
-- ----------------------------
DROP TABLE IF EXISTS `sequence`;
CREATE TABLE `sequence`  (
  `SEQ_NAME` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `SEQ_COUNT` decimal(38, 0) NULL DEFAULT NULL,
  PRIMARY KEY (`SEQ_NAME`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sequence
-- ----------------------------
INSERT INTO `sequence` VALUES ('SEQ_GEN', 500);

-- ----------------------------
-- Table structure for session
-- ----------------------------
DROP TABLE IF EXISTS `session`;
CREATE TABLE `session`  (
  `code` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_connection` datetime(0) NULL DEFAULT NULL,
  `date_creation` datetime(0) NOT NULL,
  `date_deconnection` datetime(0) NULL DEFAULT NULL,
  `date_modification` datetime(0) NOT NULL,
  `description` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `libelle` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `machine_cliente` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `signature` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `statut` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `statut_vie` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `createur` bigint(20) NOT NULL,
  `modificateur` bigint(20) NOT NULL,
  `utilisateur` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`code`) USING BTREE,
  UNIQUE INDEX `signature`(`signature`) USING BTREE,
  INDEX `FK_Session_modificateur`(`modificateur`) USING BTREE,
  INDEX `FK_Session_utilisateur`(`utilisateur`) USING BTREE,
  INDEX `FK_Session_createur`(`createur`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 5 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of session
-- ----------------------------
INSERT INTO `session` VALUES (1, '2019-04-30 15:33:01', '2019-04-30 15:33:20', '2019-04-30 15:45:24', '2019-04-30 15:33:36', NULL, NULL, 'MA1', NULL, 'ACTIVE', 'ACTIVE', 1, 1, 1);
INSERT INTO `session` VALUES (2, '2019-04-30 15:35:11', '2019-04-30 15:35:18', '2019-04-30 15:49:22', '2019-04-30 15:35:48', NULL, NULL, 'MA2', NULL, 'ACTIVE', 'ACTIVE', 1, 1, 2);
INSERT INTO `session` VALUES (3, '2019-04-30 15:36:27', '2019-04-30 15:36:36', '2019-04-30 16:36:42', '2019-04-30 15:36:51', NULL, NULL, 'MA3', NULL, 'ACTIVE', 'ACTIVE', 1, 1, 3);
INSERT INTO `session` VALUES (4, '2019-04-30 15:37:17', '2019-04-30 15:37:21', '2019-04-30 17:37:25', '2019-04-30 15:37:35', NULL, NULL, 'MA4', NULL, 'ACTIVE', 'ACTIVE', 1, 1, 4);

-- ----------------------------
-- Table structure for sms
-- ----------------------------
DROP TABLE IF EXISTS `sms`;
CREATE TABLE `sms`  (
  `code` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`code`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 7 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of sms
-- ----------------------------
INSERT INTO `sms` VALUES (4);
INSERT INTO `sms` VALUES (5);
INSERT INTO `sms` VALUES (6);

-- ----------------------------
-- Table structure for specialite
-- ----------------------------
DROP TABLE IF EXISTS `specialite`;
CREATE TABLE `specialite`  (
  `code` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_creation` datetime(0) NOT NULL,
  `date_modification` datetime(0) NOT NULL,
  `description` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `libelle` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `signature` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `statut_vie` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `createur` bigint(20) NOT NULL,
  `filiere` bigint(20) NULL DEFAULT NULL,
  `modificateur` bigint(20) NOT NULL,
  PRIMARY KEY (`code`) USING BTREE,
  UNIQUE INDEX `signature`(`signature`) USING BTREE,
  INDEX `FK_specialite_filiere`(`filiere`) USING BTREE,
  INDEX `FK_specialite_modificateur`(`modificateur`) USING BTREE,
  INDEX `FK_specialite_createur`(`createur`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 3 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of specialite
-- ----------------------------
INSERT INTO `specialite` VALUES (1, '2019-04-11 07:56:37', '2019-04-11 07:56:37', NULL, 'genie logiciel', NULL, 'ACTIVE', 1, 1, 1);
INSERT INTO `specialite` VALUES (2, '2019-04-11 07:56:54', '2019-04-11 07:56:54', NULL, 'genie logiciel', NULL, 'ACTIVE', 1, 3, 1);

-- ----------------------------
-- Table structure for type_evaluation
-- ----------------------------
DROP TABLE IF EXISTS `type_evaluation`;
CREATE TABLE `type_evaluation`  (
  `code` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_creation` datetime(0) NOT NULL,
  `date_modification` datetime(0) NOT NULL,
  `description` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `libelle` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `pourcentage` float NULL DEFAULT NULL,
  `signature` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `statut_vie` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `createur` bigint(20) NOT NULL,
  `enseignement` bigint(20) NULL DEFAULT NULL,
  `modificateur` bigint(20) NOT NULL,
  PRIMARY KEY (`code`) USING BTREE,
  UNIQUE INDEX `signature`(`signature`) USING BTREE,
  INDEX `FK_type_evaluation_enseignement`(`enseignement`) USING BTREE,
  INDEX `FK_type_evaluation_createur`(`createur`) USING BTREE,
  INDEX `FK_type_evaluation_modificateur`(`modificateur`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 22 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of type_evaluation
-- ----------------------------
INSERT INTO `type_evaluation` VALUES (1, '2019-04-21 23:27:49', '2019-04-21 23:27:52', NULL, 'Controle continu', 30, NULL, 'ACTIVE', 1, 1, 1);
INSERT INTO `type_evaluation` VALUES (2, '2019-04-21 23:28:35', '2019-04-21 23:28:37', NULL, 'Session normale', 70, NULL, 'ACTIVE', 1, 1, 1);
INSERT INTO `type_evaluation` VALUES (3, '2019-04-21 23:29:18', '2019-04-21 23:29:22', NULL, 'Rattrapage', 70, NULL, 'ACTIVE', 1, 1, 1);
INSERT INTO `type_evaluation` VALUES (4, '2019-04-21 23:32:32', '2019-04-21 23:32:32', NULL, 'Controle continu', 30, NULL, 'ACTIVE', 1, 2, 1);
INSERT INTO `type_evaluation` VALUES (6, '2019-04-21 23:38:31', '2019-04-21 23:38:31', NULL, 'Session normaale', 70, NULL, 'ACTIVE', 1, 2, 1);
INSERT INTO `type_evaluation` VALUES (7, '2019-04-21 23:38:31', '2019-04-21 23:38:31', NULL, 'Rattrapage', 70, NULL, 'ACTIVE', 1, 2, 1);
INSERT INTO `type_evaluation` VALUES (8, '2019-04-21 23:38:31', '2019-04-21 23:38:31', NULL, 'Controle continu', 20, NULL, 'ACTIVE', 1, 3, 1);
INSERT INTO `type_evaluation` VALUES (9, '2019-04-21 23:38:31', '2019-04-21 23:38:31', NULL, 'Session normale', 40, NULL, 'ACTIVE', 1, 3, 1);
INSERT INTO `type_evaluation` VALUES (10, '2019-04-21 23:38:31', '2019-04-21 23:38:31', NULL, 'Travaux pratiques', 40, NULL, 'ACTIVE', 1, 3, 1);
INSERT INTO `type_evaluation` VALUES (11, '2019-04-21 23:38:31', '2019-04-21 23:38:31', NULL, 'Rattrapages', 40, NULL, 'ACTIVE', 1, 3, 1);
INSERT INTO `type_evaluation` VALUES (12, '2019-04-21 23:38:31', '2019-04-21 23:38:31', NULL, 'Controle continu', 20, NULL, 'ACTIVE', 1, 4, 1);
INSERT INTO `type_evaluation` VALUES (13, '2019-04-21 23:38:31', '2019-04-21 23:38:31', NULL, 'Session normale', 40, NULL, 'ACTIVE', 1, 4, 1);
INSERT INTO `type_evaluation` VALUES (14, '2019-04-21 23:38:31', '2019-04-21 23:38:31', NULL, 'Travaux pratiques', 40, NULL, 'ACTIVE', 1, 4, 1);
INSERT INTO `type_evaluation` VALUES (15, '2019-04-21 23:38:31', '2019-04-21 23:38:31', NULL, 'Rattrapages', 40, NULL, 'ACTIVE', 1, 4, 1);
INSERT INTO `type_evaluation` VALUES (16, '2019-04-21 23:38:31', '2019-04-21 23:38:31', NULL, 'Controle continu', 30, NULL, 'ACTIVE', 1, 5, 1);
INSERT INTO `type_evaluation` VALUES (17, '2019-04-21 23:38:31', '2019-04-21 23:38:31', NULL, 'Session normale', 70, NULL, 'ACTIVE', 1, 5, 1);
INSERT INTO `type_evaluation` VALUES (18, '2019-04-21 23:38:31', '2019-04-21 23:38:31', NULL, 'Rattrapages', 70, NULL, 'ACTIVE', 1, 5, 1);
INSERT INTO `type_evaluation` VALUES (19, '2019-04-21 23:38:31', '2019-04-21 23:38:31', NULL, 'Controle continu', 30, NULL, 'ACTIVE', 1, 6, 1);
INSERT INTO `type_evaluation` VALUES (20, '2019-04-21 23:38:31', '2019-04-21 23:38:31', NULL, 'Session normale', 70, NULL, 'ACTIVE', 1, 6, 1);
INSERT INTO `type_evaluation` VALUES (21, '2019-04-21 23:38:31', '2019-04-21 23:38:31', NULL, 'Rattrapages', 70, NULL, 'ACTIVE', 1, 6, 1);

-- ----------------------------
-- Table structure for ue
-- ----------------------------
DROP TABLE IF EXISTS `ue`;
CREATE TABLE `ue`  (
  `code` bigint(20) NOT NULL AUTO_INCREMENT,
  `code_ue` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `date_creation` datetime(0) NOT NULL,
  `date_modification` datetime(0) NOT NULL,
  `description` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `libelle` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `signature` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `statut` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `statut_vie` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `createur` bigint(20) NOT NULL,
  `modificateur` bigint(20) NOT NULL,
  `module` bigint(20) NULL DEFAULT NULL,
  `niveau` bigint(20) NULL DEFAULT NULL,
  `specialite` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`code`) USING BTREE,
  UNIQUE INDEX `signature`(`signature`) USING BTREE,
  INDEX `FK_UE_niveau`(`niveau`) USING BTREE,
  INDEX `FK_UE_modificateur`(`modificateur`) USING BTREE,
  INDEX `FK_UE_specialite`(`specialite`) USING BTREE,
  INDEX `FK_UE_module`(`module`) USING BTREE,
  INDEX `FK_UE_createur`(`createur`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 7 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ue
-- ----------------------------
INSERT INTO `ue` VALUES (1, 'INF111', '2019-04-11 08:04:22', '2019-04-11 08:04:22', NULL, 'Systeme d indormation', NULL, 'ACTIVE', 'ACTIVE', 1, 1, 1, 1, 1);
INSERT INTO `ue` VALUES (2, 'INF113', '2019-04-11 08:05:41', '2019-04-11 08:05:41', NULL, 'Architecture des ordinateurs', NULL, 'ACTIVE', 'ACTIVE', 1, 1, 1, 2, 2);
INSERT INTO `ue` VALUES (3, 'MAT111', '2019-04-11 08:07:55', '2019-04-11 08:07:55', NULL, 'MATH POUR INFORMATIQUE', NULL, 'ACTIVE', 'ACTIVE', 1, 1, 2, 3, 2);
INSERT INTO `ue` VALUES (4, 'MAT112', '2019-04-11 08:09:21', '2019-04-11 08:09:21', NULL, 'MATH DISCRETES I', NULL, 'ACTIVE', 'ACTIVE', 1, 1, 2, 3, 2);
INSERT INTO `ue` VALUES (5, 'INF121', '2019-04-11 08:11:52', '2019-04-11 08:11:52', NULL, 'JAVA I', NULL, 'ACTIVE', 'ACTIVE', 1, 1, 3, 1, 1);
INSERT INTO `ue` VALUES (6, 'INF124', '2019-04-11 08:12:31', '2019-04-11 08:12:31', NULL, 'PROGRAMMATION C', NULL, 'ACTIVE', 'ACTIVE', 1, 1, 4, 1, 1);

-- ----------------------------
-- Table structure for utilisateur
-- ----------------------------
DROP TABLE IF EXISTS `utilisateur`;
CREATE TABLE `utilisateur`  (
  `code` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_creation` datetime(0) NOT NULL,
  `date_modification` datetime(0) NOT NULL,
  `date_naissance` datetime(0) NOT NULL,
  `description` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `libelle` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `login` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `mot_de_passe` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `nom` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `prenom` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `sexe` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `signature` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `statut` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `statut_vie` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `telephone` int(11) NOT NULL,
  `createur` bigint(20) NOT NULL,
  `modificateur` bigint(20) NOT NULL,
  PRIMARY KEY (`code`) USING BTREE,
  UNIQUE INDEX `email`(`email`) USING BTREE,
  UNIQUE INDEX `login`(`login`) USING BTREE,
  UNIQUE INDEX `telephone`(`telephone`) USING BTREE,
  UNIQUE INDEX `signature`(`signature`) USING BTREE,
  INDEX `FK_utilisateur_createur`(`createur`) USING BTREE,
  INDEX `FK_utilisateur_modificateur`(`modificateur`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 5 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of utilisateur
-- ----------------------------
INSERT INTO `utilisateur` VALUES (1, '2019-04-08 12:52:09', '2019-04-08 12:52:09', '1990-04-09 00:00:00', NULL, 'ongono@gmail.com', NULL, 'yannick', '123456', 'ONGONO', 'YANNICKK', 'MASCULIN', NULL, 'ACTIVE', 'ACTIVE', 691063708, 1, 1);
INSERT INTO `utilisateur` VALUES (2, '2019-04-08 12:55:52', '2019-04-08 12:55:52', '1987-02-10 00:00:00', NULL, 'tapigue@gmail.com', NULL, 'tapigue', 'azerty', 'TAPIGUE', 'DELPHIN', 'MASCULIN', NULL, 'ACTIVE', 'ACTIVE', 698785474, 1, 1);
INSERT INTO `utilisateur` VALUES (3, '2019-04-08 12:59:48', '2019-04-08 12:59:48', '1978-06-21 00:00:00', NULL, 'herve@gmail.com', NULL, 'jeanherve', 'isj123', 'JEAN', 'HERVE', 'MASCULIN', NULL, 'ACTIVE', 'ACTIVE', 690548932, 1, 1);
INSERT INTO `utilisateur` VALUES (4, '2019-04-08 13:03:59', '2019-04-08 13:03:59', '1984-04-17 00:00:00', NULL, 'emmamoupo@gmail.com', NULL, 'emmanuel', 'isj456', 'MOUPOJOU', 'EMMANUEL', 'MASCULIN', NULL, 'ACTIVE', 'ACTIVE', 698745621, 1, 1);

-- ----------------------------
-- Table structure for utilisateur_role
-- ----------------------------
DROP TABLE IF EXISTS `utilisateur_role`;
CREATE TABLE `utilisateur_role`  (
  `code_utilisateur` bigint(20) NOT NULL,
  `code_role` bigint(20) NOT NULL,
  PRIMARY KEY (`code_utilisateur`, `code_role`) USING BTREE,
  INDEX `FK_utilisateur_role_code_role`(`code_role`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of utilisateur_role
-- ----------------------------
INSERT INTO `utilisateur_role` VALUES (1, 1);
INSERT INTO `utilisateur_role` VALUES (2, 2);
INSERT INTO `utilisateur_role` VALUES (3, 3);
INSERT INTO `utilisateur_role` VALUES (4, 4);

SET FOREIGN_KEY_CHECKS = 1;
