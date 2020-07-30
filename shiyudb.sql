/*
 Navicat Premium Data Transfer

 Source Server         : HBCMIS
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : shiyudb

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 30/07/2020 19:32:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for shiyu_admin
-- ----------------------------
DROP TABLE IF EXISTS `shiyu_admin`;
CREATE TABLE `shiyu_admin`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of shiyu_admin
-- ----------------------------
INSERT INTO `shiyu_admin` VALUES (2, 'admin', 'GeJkeev0FKnUGraJaQDJRg==', 'admin');

-- ----------------------------
-- Table structure for shiyu_article
-- ----------------------------
DROP TABLE IF EXISTS `shiyu_article`;
CREATE TABLE `shiyu_article`  (
  `id` int(6) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL,
  `desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `dateTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of shiyu_article
-- ----------------------------
INSERT INTO `shiyu_article` VALUES (5, '新博客2', '<p>新博客2</p>', NULL, '2020-07-30 04:22:56');
INSERT INTO `shiyu_article` VALUES (6, '新博客3', '<p>新博客3</p><p><br></p><p><br></p><p>update</p><p><br></p><p>444</p>', NULL, '2020-07-30 18:50:19');

SET FOREIGN_KEY_CHECKS = 1;
