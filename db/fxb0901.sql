/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : localhost:3306
 Source Schema         : fxb

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 01/09/2021 12:28:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for acl_permission
-- ----------------------------
DROP TABLE IF EXISTS `acl_permission`;
CREATE TABLE `acl_permission`  (
  `id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '编号',
  `pid` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '所属上级',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '名称',
  `type` tinyint(3) NOT NULL DEFAULT 0 COMMENT '类型(1:菜单,2:按钮)',
  `permission_value` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '权限值',
  `path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '访问路径',
  `component` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '组件路径',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '图标',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态(0:禁止,1:正常)',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_pid`(`pid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '权限' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of acl_permission
-- ----------------------------
INSERT INTO `acl_permission` VALUES ('1', '0', '全部数据', 0, NULL, NULL, NULL, NULL, NULL, 0, '2019-11-15 17:13:06', '2019-11-15 17:13:06');
INSERT INTO `acl_permission` VALUES ('1195268474480156673', '1', '权限管理', 1, NULL, '/acl', 'Layout', NULL, NULL, 0, '2019-11-15 17:13:06', '2019-11-18 13:54:25');
INSERT INTO `acl_permission` VALUES ('1195268616021139457', '1195268474480156673', '用户管理', 1, NULL, 'user/list', '/acl/user/list', NULL, NULL, 0, '2019-11-15 17:13:40', '2019-11-18 13:53:12');
INSERT INTO `acl_permission` VALUES ('1195268788138598401', '1195268474480156673', '角色管理', 1, NULL, 'role/list', '/acl/role/list', NULL, NULL, 0, '2019-11-15 17:14:21', '2019-11-15 17:14:21');
INSERT INTO `acl_permission` VALUES ('1195268893830864898', '1195268474480156673', '菜单管理', 1, NULL, 'menu/list', '/acl/menu/list', NULL, NULL, 0, '2019-11-15 17:14:46', '2019-11-15 17:14:46');
INSERT INTO `acl_permission` VALUES ('1195269143060602882', '1195268616021139457', '查看', 2, 'user.list', '', '', NULL, NULL, 0, '2019-11-15 17:15:45', '2019-11-17 21:57:16');
INSERT INTO `acl_permission` VALUES ('1195269295926206466', '1195268616021139457', '添加', 2, 'user.add', 'user/add', '/acl/user/form', NULL, NULL, 0, '2019-11-15 17:16:22', '2019-11-15 17:16:22');
INSERT INTO `acl_permission` VALUES ('1195269473479483394', '1195268616021139457', '修改', 2, 'user.update', 'user/update/:id', '/acl/user/form', NULL, NULL, 0, '2019-11-15 17:17:04', '2019-11-15 17:17:04');
INSERT INTO `acl_permission` VALUES ('1195269547269873666', '1195268616021139457', '删除', 2, 'user.remove', '', '', NULL, NULL, 0, '2019-11-15 17:17:22', '2019-11-15 17:17:22');
INSERT INTO `acl_permission` VALUES ('1195269821262782465', '1195268788138598401', '修改', 2, 'role.update', 'role/update/:id', '/acl/role/form', NULL, NULL, 0, '2019-11-15 17:18:27', '2019-11-15 17:19:53');
INSERT INTO `acl_permission` VALUES ('1195269903542444034', '1195268788138598401', '查看', 2, 'role.list', '', '', NULL, NULL, 0, '2019-11-15 17:18:47', '2019-11-15 17:18:47');
INSERT INTO `acl_permission` VALUES ('1195270037005197313', '1195268788138598401', '添加', 2, 'role.add', 'role/add', '/acl/role/form', NULL, NULL, 0, '2019-11-15 17:19:19', '2019-11-18 11:05:42');
INSERT INTO `acl_permission` VALUES ('1195270442602782721', '1195268788138598401', '删除', 2, 'role.remove', '', '', NULL, NULL, 0, '2019-11-15 17:20:55', '2019-11-15 17:20:55');
INSERT INTO `acl_permission` VALUES ('1195270621548568578', '1195268788138598401', '角色权限', 2, 'role.acl', 'role/distribution/:id', '/acl/role/roleForm', NULL, NULL, 0, '2019-11-15 17:21:38', '2019-11-15 17:21:38');
INSERT INTO `acl_permission` VALUES ('1195270744097742849', '1195268893830864898', '查看', 2, 'permission.list', '', '', NULL, NULL, 0, '2019-11-15 17:22:07', '2019-11-15 17:22:07');
INSERT INTO `acl_permission` VALUES ('1195270810560684034', '1195268893830864898', '添加', 2, 'permission.add', '', '', NULL, NULL, 0, '2019-11-15 17:22:23', '2019-11-15 17:22:23');
INSERT INTO `acl_permission` VALUES ('1195270862100291586', '1195268893830864898', '修改', 2, 'permission.update', '', '', NULL, NULL, 0, '2019-11-15 17:22:35', '2019-11-15 17:22:35');
INSERT INTO `acl_permission` VALUES ('1195270887933009922', '1195268893830864898', '删除', 2, 'permission.remove', '', '', NULL, NULL, 0, '2019-11-15 17:22:41', '2019-11-15 17:22:41');
INSERT INTO `acl_permission` VALUES ('1196301740985311234', '1195268616021139457', '分配角色', 2, 'user.assgin', 'user/role/:id', '/acl/user/roleForm', NULL, NULL, 0, '2019-11-18 13:38:56', '2019-11-18 13:38:56');
INSERT INTO `acl_permission` VALUES ('1423567105018294274', '1', '系统管理', 1, NULL, '/system', 'Layout', NULL, NULL, 0, '2021-08-06 16:50:21', '2021-08-07 17:16:43');
INSERT INTO `acl_permission` VALUES ('1423567685056983041', '1423567105018294274', '字典管理', 1, NULL, 'dict/list', '/system/dict/list', NULL, NULL, 0, '2021-08-06 16:52:39', '2021-08-07 17:41:37');
INSERT INTO `acl_permission` VALUES ('1423936560441315330', '1423567685056983041', '列表', 2, 'system.list', 'dict/dictData/:id', '/system/dict/dictData', NULL, NULL, 0, '2021-08-07 17:18:26', '2021-08-07 17:39:56');
INSERT INTO `acl_permission` VALUES ('1423937855852105730', '1423567105018294274', '网站设置', 1, NULL, 'system/webConfig', '/system/webConfig', NULL, NULL, 0, '2021-08-07 17:23:35', '2021-08-31 00:15:57');
INSERT INTO `acl_permission` VALUES ('1424016500725567490', '1', '卷题管理', 1, NULL, '/edu', 'Layout', NULL, NULL, 0, '2021-08-07 22:36:05', '2021-08-07 22:36:14');
INSERT INTO `acl_permission` VALUES ('1424017105527427073', '1424016500725567490', '学科列表', 1, NULL, 'subject/list', '/edu/subject/list', NULL, NULL, 0, '2021-08-07 22:38:30', '2021-08-07 22:38:52');
INSERT INTO `acl_permission` VALUES ('1424017386940059650', '1424016500725567490', '题目列表', 1, NULL, 'topic/list', '/edu/topic/list', NULL, NULL, 0, '2021-08-07 22:39:37', '2021-08-11 14:55:49');
INSERT INTO `acl_permission` VALUES ('1424017433580720130', '1424016500725567490', '试卷列表', 1, NULL, 'paper/list', '/edu/paper/list', NULL, NULL, 0, '2021-08-07 22:39:48', '2021-08-26 01:47:26');
INSERT INTO `acl_permission` VALUES ('1425523414462529537', '1424017105527427073', '添加单选题', 2, 'topic.single-choice', 'topic/single-choice/:id', '/edu/topic/single-choice', NULL, NULL, 0, '2021-08-12 02:24:02', '2021-08-26 01:42:10');
INSERT INTO `acl_permission` VALUES ('1430586470406234113', '1424017105527427073', '添加多选题', 2, 'topic.multiple-choice', 'topic/multiple-choice/:id', '/edu/topic/multiple-choice', NULL, NULL, 0, '2021-08-26 01:42:48', '2021-08-26 01:43:18');
INSERT INTO `acl_permission` VALUES ('1430586865073463298', '1424017105527427073', '添加简答题', 2, 'topic.shortAnswer-question', 'topic/shortAnswer-question/:id', '/edu/topic/shortAnswer-question', NULL, NULL, 0, '2021-08-26 01:44:22', '2021-08-26 01:44:50');
INSERT INTO `acl_permission` VALUES ('1430587157680693250', '1424017105527427073', '添加判断题', 2, 'topic.trueFalse-choice', 'topic/trueFalse-choice/:id', '/edu/topic/trueFalse-choice', NULL, NULL, 0, '2021-08-26 01:45:32', '2021-08-26 01:45:58');
INSERT INTO `acl_permission` VALUES ('1430588087188791297', '1424017433580720130', '添加试卷', 2, 'paper.edit', 'paper/edit/:id', '/edu/paper/edit', NULL, NULL, 0, '2021-08-26 01:49:14', '2021-08-26 01:49:58');

-- ----------------------------
-- Table structure for acl_role
-- ----------------------------
DROP TABLE IF EXISTS `acl_role`;
CREATE TABLE `acl_role`  (
  `id` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '角色id',
  `role_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '角色名称',
  `role_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色编码',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of acl_role
-- ----------------------------
INSERT INTO `acl_role` VALUES ('1', '普通管理员', NULL, NULL, 0, '2019-11-11 13:09:32', '2019-11-18 10:27:18');
INSERT INTO `acl_role` VALUES ('1424006270549696513', '系统设置', NULL, NULL, 0, '2021-08-07 21:55:26', '2021-08-07 21:55:26');

-- ----------------------------
-- Table structure for acl_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `acl_role_permission`;
CREATE TABLE `acl_role_permission`  (
  `id` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `role_id` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `permission_id` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_role_id`(`role_id`) USING BTREE,
  INDEX `idx_permission_id`(`permission_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色权限' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of acl_role_permission
-- ----------------------------
INSERT INTO `acl_role_permission` VALUES ('1424006297263218690', '1424006270549696513', '1', 0, '2021-08-07 21:55:33', '2021-08-07 21:55:33');
INSERT INTO `acl_role_permission` VALUES ('1424006297359687682', '1424006270549696513', '1423567105018294274', 0, '2021-08-07 21:55:33', '2021-08-07 21:55:33');
INSERT INTO `acl_role_permission` VALUES ('1424006297359687683', '1424006270549696513', '1423567685056983041', 0, '2021-08-07 21:55:33', '2021-08-07 21:55:33');
INSERT INTO `acl_role_permission` VALUES ('1424006297359687684', '1424006270549696513', '1423936560441315330', 0, '2021-08-07 21:55:33', '2021-08-07 21:55:33');
INSERT INTO `acl_role_permission` VALUES ('1424006297359687685', '1424006270549696513', '1423937855852105730', 0, '2021-08-07 21:55:33', '2021-08-07 21:55:33');

-- ----------------------------
-- Table structure for acl_user
-- ----------------------------
DROP TABLE IF EXISTS `acl_user`;
CREATE TABLE `acl_user`  (
  `id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '会员id',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '微信openid',
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '密码',
  `nick_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '昵称',
  `salt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户头像',
  `token` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户签名',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_username`(`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of acl_user
-- ----------------------------
INSERT INTO `acl_user` VALUES ('1', 'admin', '96e79218965eb72c92a549dd5a330112', 'admin', '', NULL, 0, '2019-11-01 10:39:47', '2019-11-01 10:39:47');
INSERT INTO `acl_user` VALUES ('1423436004928933889', 'jzj', '96e79218965eb72c92a549dd5a330112', 'jzj', NULL, NULL, 0, '2021-08-06 08:09:24', '2021-08-06 08:09:24');

-- ----------------------------
-- Table structure for acl_user_role
-- ----------------------------
DROP TABLE IF EXISTS `acl_user_role`;
CREATE TABLE `acl_user_role`  (
  `id` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '主键id',
  `role_id` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '角色id',
  `user_id` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '用户id',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_role_id`(`role_id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of acl_user_role
-- ----------------------------
INSERT INTO `acl_user_role` VALUES ('1424006337805361154', '1424006270549696513', '1423436004928933889', 0, '2021-08-07 21:55:42', '2021-08-07 21:55:42');

-- ----------------------------
-- Table structure for dict
-- ----------------------------
DROP TABLE IF EXISTS `dict`;
CREATE TABLE `dict`  (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `parent_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '上级id',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '名称',
  `value` int(11) DEFAULT NULL COMMENT '值',
  `dict_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '编码',
  `gmt_create` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_parent_id_value`(`parent_id`, `value`) USING BTREE,
  INDEX `idx_parent_id`(`parent_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '数据字典' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dict
-- ----------------------------
INSERT INTO `dict` VALUES (100, 1, '题型', NULL, 'sys_question', '2021-08-10 14:22:23', '2021-08-10 14:22:23', 1, NULL);
INSERT INTO `dict` VALUES (101, 100, '单选题', 1, 'multiple-choice', '2021-08-10 14:23:42', '2021-08-10 14:25:22', 1, '');
INSERT INTO `dict` VALUES (102, 100, '多选题', 2, NULL, '2021-08-10 14:23:49', '2021-08-10 14:23:49', 1, NULL);
INSERT INTO `dict` VALUES (103, 100, '填空题', 3, NULL, '2021-08-10 14:23:58', '2021-08-10 14:23:58', 1, NULL);
INSERT INTO `dict` VALUES (104, 100, '简答题', 4, NULL, '2021-08-10 14:24:13', '2021-08-10 14:24:13', 1, NULL);
INSERT INTO `dict` VALUES (105, 100, '判断题', 5, NULL, '2021-08-10 14:24:25', '2021-08-12 14:50:41', 1, NULL);
INSERT INTO `dict` VALUES (200, 1, '年级', NULL, 'sys_nian', '2021-08-11 13:09:11', '2021-08-11 13:09:11', 1, NULL);
INSERT INTO `dict` VALUES (201, 200, '一年级', 1, NULL, '2021-08-11 13:09:24', '2021-08-11 13:09:24', 1, NULL);
INSERT INTO `dict` VALUES (202, 200, '二年级', 2, NULL, '2021-08-11 13:09:32', '2021-08-11 13:09:32', 1, NULL);
INSERT INTO `dict` VALUES (203, 200, '三年级', 3, NULL, '2021-08-11 13:09:37', '2021-08-11 13:09:37', 1, NULL);
INSERT INTO `dict` VALUES (204, 200, '四年级', 4, NULL, '2021-08-11 13:09:43', '2021-08-11 13:09:43', 1, NULL);

-- ----------------------------
-- Table structure for edu_paper
-- ----------------------------
DROP TABLE IF EXISTS `edu_paper`;
CREATE TABLE `edu_paper`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '试卷表id',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '修改时间',
  `status` int(11) NOT NULL COMMENT '状态',
  `score` int(11) DEFAULT NULL COMMENT '试卷总分',
  `subject_id` bigint(20) DEFAULT NULL COMMENT '学科',
  `question_count` int(11) DEFAULT NULL COMMENT '题目总数',
  `suggest_time` int(11) DEFAULT NULL COMMENT '考试时长',
  `paper_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '试卷名称',
  `paper_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '试卷标题',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for edu_paper_topic
-- ----------------------------
DROP TABLE IF EXISTS `edu_paper_topic`;
CREATE TABLE `edu_paper_topic`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '试卷关联题目表id',
  `paperId` bigint(20) DEFAULT NULL COMMENT '试卷表id',
  `topicId` bigint(20) DEFAULT NULL COMMENT '题目表id',
  `topicContentId` bigint(20) DEFAULT NULL COMMENT '题目详情表id',
  `gmtCreate` datetime(0) NOT NULL COMMENT '创建时间',
  `gmtModified` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for edu_subject
-- ----------------------------
DROP TABLE IF EXISTS `edu_subject`;
CREATE TABLE `edu_subject`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '科目类别id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '科目名称',
  `sort` int(11) DEFAULT 0 COMMENT '排序',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '更新时间',
  `status` int(11) NOT NULL COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of edu_subject
-- ----------------------------
INSERT INTO `edu_subject` VALUES (4, '语文', 0, '2021-08-10 13:09:41', '2021-08-10 14:25:08', 1);
INSERT INTO `edu_subject` VALUES (5, '数学', 0, '2021-08-10 13:09:57', '2021-08-10 13:09:57', 1);

-- ----------------------------
-- Table structure for edu_topic
-- ----------------------------
DROP TABLE IF EXISTS `edu_topic`;
CREATE TABLE `edu_topic`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '题目表id',
  `subject_id` bigint(20) DEFAULT NULL COMMENT '学科id',
  `score` int(11) DEFAULT NULL COMMENT '题目分数',
  `difficult` int(11) DEFAULT NULL COMMENT '题目难度',
  `correct` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '正确答案',
  `topic_details_id` bigint(20) DEFAULT NULL COMMENT '题干，选项详情表id',
  `status` int(11) NOT NULL COMMENT '状态',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '修改时间',
  `question_id` int(11) DEFAULT NULL COMMENT '题型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '题目' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of edu_topic
-- ----------------------------
INSERT INTO `edu_topic` VALUES (4, 4, 3, 3, 'B', 1, 1, '2021-08-26 01:46:29', '2021-08-26 01:46:29', 1);
INSERT INTO `edu_topic` VALUES (9, 4, 3, 2, 'B', 6, 1, '2021-08-26 16:48:25', '2021-08-26 16:48:25', 2);
INSERT INTO `edu_topic` VALUES (10, 4, 22, 3, 'B', 7, 1, '2021-08-26 16:58:40', '2021-08-26 16:58:40', 1);

-- ----------------------------
-- Table structure for edu_topic_content
-- ----------------------------
DROP TABLE IF EXISTS `edu_topic_content`;
CREATE TABLE `edu_topic_content`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '题目详情表id',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '题目内容',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '更新时间',
  `title_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '题目题干',
  `analyzes` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '题目解析',
  `correct` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '题目答案',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of edu_topic_content
-- ----------------------------
INSERT INTO `edu_topic_content` VALUES (1, '[{\"prefix\":\"A\",\"content\":\"<p>鬼地方鬼地方鬼地方</p>\"},{\"prefix\":\"B\",\"content\":\"<p>鬼地方高浮雕高浮雕g\'d\'fgdf</p>\"},{\"prefix\":\"C\",\"content\":\"<p>感地方官梵蒂冈的</p>\"},{\"prefix\":\"D\",\"content\":\"<p>高浮雕高浮雕鬼地方</p>\"}]', '2021-08-26 01:46:29', '2021-08-26 01:46:29', '<p>dfdsf手动释放速度</p>', '<p>的撒范德萨范德萨范德萨</p>', 'B');
INSERT INTO `edu_topic_content` VALUES (6, '[{\"prefix\":\"A\",\"content\":\"<p>大萨达撒</p>\"},{\"prefix\":\"B\",\"content\":\"<p>大萨达撒</p>\"},{\"prefix\":\"C\",\"content\":\"<p>奥术大师多</p>\"},{\"prefix\":\"D\",\"content\":\"<p>大萨达撒大所</p>\"}]', '2021-08-26 16:48:25', '2021-08-26 16:48:25', '<p>大萨达撒</p>', '<p>大萨达撒大所</p>', 'B');
INSERT INTO `edu_topic_content` VALUES (7, '[{\"prefix\":\"A\",\"content\":\"<p>大萨达撒多撒</p>\"},{\"prefix\":\"B\",\"content\":\"<p>大萨达撒大所</p>\"},{\"prefix\":\"C\",\"content\":\"<p>敖德萨大所多撒</p>\"},{\"prefix\":\"D\",\"content\":\"<p>大萨达撒多撒</p>\"}]', '2021-08-26 16:58:40', '2021-08-26 16:58:40', '<p>硕大的撒大萨达撒多</p>', '<p>大萨达撒多撒大</p>', 'B');

-- ----------------------------
-- Table structure for ucenter_member
-- ----------------------------
DROP TABLE IF EXISTS `ucenter_member`;
CREATE TABLE `ucenter_member`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '会员id',
  `openid` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '微信openid',
  `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '手机号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '密码',
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '昵称',
  `sex` tinyint(2) UNSIGNED DEFAULT NULL COMMENT '性别 1 女，2 男',
  `age` tinyint(3) UNSIGNED DEFAULT NULL COMMENT '年龄',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户头像',
  `sign` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户签名',
  `is_disabled` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否禁用 1（true）已禁用，  0（false）未禁用',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '会员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ucenter_member
-- ----------------------------
INSERT INTO `ucenter_member` VALUES (1, NULL, '18176702572', '96e79218965eb72c92a549dd5a330112', 'jzj', NULL, NULL, 'https://guli-jzj.oss-cn-guangzhou.aliyuncs.com/2021/07/28/16.jpeg', NULL, 0, 0, '2021-08-29 13:57:21', '2021-08-29 13:57:24');

-- ----------------------------
-- Table structure for web_config
-- ----------------------------
DROP TABLE IF EXISTS `web_config`;
CREATE TABLE `web_config`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '网站设置表id',
  `logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '网站logo',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '网站名称',
  `summary` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '网站介绍',
  `keyword` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '网站关键字',
  `author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '网站作者',
  `record_num` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '网站备案号',
  `gmt_create` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) DEFAULT NULL COMMENT '修改时间',
  `github` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'github地址',
  `gitee` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '码云地址',
  `qq_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'QQ号',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮箱',
  `we_chat` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '微信号',
  `slideshow_one` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '轮播图1',
  `slideshow_two` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '轮播图2',
  `slideshow_three` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '轮播图3',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '网站配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of web_config
-- ----------------------------
INSERT INTO `web_config` VALUES (1, 'https://fxb-jzj.oss-cn-guangzhou.aliyuncs.com/Logo/2021/08/31/7f3045db-2f94-479c-ab91-4f9bd6ae39ea.png', '复习宝', '为学生提供在线复习，在线考试的功能', '学习，复习，在线考试，单选题，多选题，判断题，简答题', '漫漫长路', '桂B-999999', '2021-08-31 15:59:41', '2021-08-31 17:46:51', NULL, NULL, '946232976', '946232976@qq.com', 'fr946232976', NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
