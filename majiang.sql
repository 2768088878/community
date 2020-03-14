/*
 Navicat MySQL Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50562
 Source Host           : localhost:3306
 Source Schema         : majiang

 Target Server Type    : MySQL
 Target Server Version : 50562
 File Encoding         : 65001

 Date: 14/03/2020 10:56:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `parent_id` int(20) NOT NULL COMMENT '评论的问题/回复的评论',
  `type` int(2) NOT NULL COMMENT '类型（1评论  ；2回复）',
  `commentator` int(10) NOT NULL COMMENT '评论人id',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `like_count` int(255) NOT NULL DEFAULT 0 COMMENT '点赞数',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评论内容',
  `comment_count` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '回复数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (1, 1, 1, 1, '2020-02-04 18:26:48', '2020-02-04 18:26:48', 0, '22222', '0');
INSERT INTO `comment` VALUES (2, 1, 1, 1, '2020-02-04 18:29:39', '2020-02-04 18:29:39', 0, '2323233', '0');
INSERT INTO `comment` VALUES (3, 1, 1, 1, '2020-02-04 18:33:41', '2020-02-04 18:33:41', 0, '44444444', '0');
INSERT INTO `comment` VALUES (4, 5, 1, 1, '2020-02-04 20:02:42', '2020-02-04 20:02:42', 0, 'SADSD', '0');
INSERT INTO `comment` VALUES (5, 4, 1, 1, '2020-02-04 20:02:47', '2020-02-04 20:02:47', 0, 'XAWX', '0');
INSERT INTO `comment` VALUES (6, 4, 1, 1, '2020-02-04 20:02:49', '2020-02-04 20:02:49', 0, 'RASDADA', '0');
INSERT INTO `comment` VALUES (7, 3, 1, 1, '2020-02-04 20:02:57', '2020-02-04 20:02:57', 0, 'DDDDDA', '0');
INSERT INTO `comment` VALUES (8, 2, 1, 2, '2020-03-03 15:58:20', '2020-03-03 15:58:09', 0, '答案是打算', '1');
INSERT INTO `comment` VALUES (9, 8, 2, 2, '2020-03-03 15:58:20', '2020-03-03 15:58:20', 0, '回复评论', '0');
INSERT INTO `comment` VALUES (10, 8, 1, 1, '2020-03-10 21:49:37', '2020-03-10 21:45:31', 0, '21点45分', '2');
INSERT INTO `comment` VALUES (11, 10, 2, 1, '2020-03-10 21:45:39', '2020-03-10 21:45:39', 0, '测试回复', '0');
INSERT INTO `comment` VALUES (12, 10, 2, 1, '2020-03-10 21:49:37', '2020-03-10 21:49:37', 0, '哈哈', '0');
INSERT INTO `comment` VALUES (13, 10, 1, 3, '2020-03-13 23:43:40', '2020-03-13 23:43:07', 0, '你好啊', '1');
INSERT INTO `comment` VALUES (14, 13, 2, 4, '2020-03-13 23:43:40', '2020-03-13 23:43:40', 0, '你好你好', '0');
INSERT INTO `comment` VALUES (15, 11, 1, 4, '2020-03-13 23:53:06', '2020-03-13 23:52:35', 0, '嗯嗯嗯', '1');
INSERT INTO `comment` VALUES (16, 15, 2, 3, '2020-03-13 23:53:06', '2020-03-13 23:53:06', 0, '嘻嘻嘻', '0');

-- ----------------------------
-- Table structure for notification
-- ----------------------------
DROP TABLE IF EXISTS `notification`;
CREATE TABLE `notification`  (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `notifier` int(50) NOT NULL COMMENT '通知人',
  `receiver` int(255) NOT NULL COMMENT '接收人',
  `question_id` int(255) NOT NULL DEFAULT 0 COMMENT '问题id',
  `comment_id` int(255) NOT NULL DEFAULT 0 COMMENT '评论id',
  `type` int(1) NOT NULL COMMENT '1：评论通知；2：回复通知',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '时间',
  `status` int(1) NOT NULL DEFAULT 0 COMMENT '0:未读 ,1：已读',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of notification
-- ----------------------------
INSERT INTO `notification` VALUES (10, 1, 1, 8, 10, 2, '2020-03-10 21:49:49', 1, '哈哈');
INSERT INTO `notification` VALUES (11, 3, 4, 10, 0, 1, '2020-03-13 23:43:31', 1, '你好啊');
INSERT INTO `notification` VALUES (12, 4, 3, 10, 13, 2, '2020-03-13 23:47:59', 1, '你好你好');
INSERT INTO `notification` VALUES (13, 4, 3, 11, 0, 1, '2020-03-13 23:52:57', 1, '嗯嗯嗯');
INSERT INTO `notification` VALUES (14, 3, 4, 11, 15, 2, '2020-03-13 23:53:24', 1, '嘻嘻嘻');

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `creator` int(50) NOT NULL,
  `comment_count` int(10) NULL DEFAULT 0,
  `view_count` int(10) NULL DEFAULT 0,
  `like_count` int(10) NULL DEFAULT 0,
  `tag` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `temp` int(10) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of question
-- ----------------------------
INSERT INTO `question` VALUES (8, '2020年3月10日21:45:07', '现在时间是2020年3月10日21:44:55', '2020-03-10 21:45:13', '2020-03-10 21:45:13', 1, 1, 8, 0, 'python', 0);
INSERT INTO `question` VALUES (9, '定时器', '测试测试', '2020-03-10 21:47:57', '2020-03-10 21:47:57', 1, 0, 1, 0, 'python', 0);
INSERT INTO `question` VALUES (10, '2020/3/13', '哈哈哈	', '2020-03-13 23:42:37', '2020-03-13 23:42:37', 4, 1, 7, 0, 'js,php', 0);
INSERT INTO `question` VALUES (11, '好好好', '好好好', '2020-03-13 23:52:19', '2020-03-13 23:52:19', 3, 1, 7, 0, 'python', 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `pwd` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `avatar_url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (3, '雪玲', '123456', '2768088878@qq.com', '2020-03-13 22:36:34', NULL);
INSERT INTO `user` VALUES (4, 'chuangchuang', '123456', 'jwf2768088878@163.com', '2020-03-13 23:42:03', NULL);

SET FOREIGN_KEY_CHECKS = 1;
