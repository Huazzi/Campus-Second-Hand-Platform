/*
 Navicat Premium Dump SQL

 Source Server         : MySQL57
 Source Server Type    : MySQL
 Source Server Version : 50737 (5.7.37)
 Source Host           : localhost:3306
 Source Schema         : second_hand

 Target Server Type    : MySQL
 Target Server Version : 50737 (5.7.37)
 File Encoding         : 65001

 Date: 30/06/2025 15:32:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ai_chat
-- ----------------------------
DROP TABLE IF EXISTS `ai_chat`;
CREATE TABLE `ai_chat`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `question` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `answer` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_ai` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `ai_chat_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 53 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ai_chat
-- ----------------------------
INSERT INTO `ai_chat` VALUES (1, 100008, '如何发布二手物品?', '', '2025-04-26 18:09:46', 0);
INSERT INTO `ai_chat` VALUES (2, 100008, '如何发布二手物品?', '发布二手物品很简单，只需点击小程序底部的「发布」按钮，填写物品名称、价格、描述和上传图片等信息，然后点击发布即可。', '2025-04-26 18:09:46', 1);
INSERT INTO `ai_chat` VALUES (3, 100008, '如何联系卖家?', '', '2025-04-26 18:10:59', 0);
INSERT INTO `ai_chat` VALUES (4, 100008, '如何联系卖家?', '您可以在商品详情页点击「联系卖家」按钮，进入聊天界面与卖家沟通。', '2025-04-26 18:10:59', 1);
INSERT INTO `ai_chat` VALUES (5, 100008, '你是谁', '', '2025-04-26 18:11:12', 0);
INSERT INTO `ai_chat` VALUES (6, 100008, '你是谁', '您好！我是二货来了的智能客服助手——小二，有什么可以帮您的吗？', '2025-04-26 18:11:14', 1);
INSERT INTO `ai_chat` VALUES (7, 100008, '如何修改个人信息?', '', '2025-04-26 18:13:56', 0);
INSERT INTO `ai_chat` VALUES (8, 100008, '如何修改个人信息?', '进入「我的」页面，点击头像或昵称，即可进入个人信息页面进行修改。', '2025-04-26 18:13:56', 1);
INSERT INTO `ai_chat` VALUES (9, 100008, '你有什么推荐的商品？', '', '2025-04-26 18:23:23', 0);
INSERT INTO `ai_chat` VALUES (10, 100008, '你有什么推荐的商品？', '您好！我是二货来了的智能客服助手——小二，有什么可以帮您的吗？', '2025-04-26 18:23:26', 1);
INSERT INTO `ai_chat` VALUES (11, 100008, '推荐一些商品', '', '2025-04-26 18:24:39', 0);
INSERT INTO `ai_chat` VALUES (12, 100008, '推荐一些商品', '您好！我是二货来了的智能客服助手——小二，有什么可以帮您的吗？', '2025-04-26 18:24:40', 1);
INSERT INTO `ai_chat` VALUES (13, 100008, '如何联系卖家?', '', '2025-04-26 18:28:36', 0);
INSERT INTO `ai_chat` VALUES (14, 100008, '如何联系卖家?', '您可以在商品详情页点击「联系卖家」按钮，进入聊天界面与卖家沟通。', '2025-04-26 18:28:36', 1);
INSERT INTO `ai_chat` VALUES (15, 100008, '你好', '', '2025-04-26 18:28:48', 0);
INSERT INTO `ai_chat` VALUES (16, 100008, '你好', '您好！我是二货来了的智能客服助手——小二，有什么可以帮您的吗？', '2025-04-26 18:28:50', 1);
INSERT INTO `ai_chat` VALUES (17, 100008, '能说的别的吗', '', '2025-04-26 18:29:01', 0);
INSERT INTO `ai_chat` VALUES (18, 100008, '能说的别的吗', '您好！我是二货来了的智能客服助手——小二，有什么可以帮您的吗？', '2025-04-26 18:29:02', 1);
INSERT INTO `ai_chat` VALUES (19, 100008, '你好', '', '2025-04-26 18:32:43', 0);
INSERT INTO `ai_chat` VALUES (20, 100008, '你好', '您好！我是二货来了的智能客服助手——小二，有什么可以帮您的吗？', '2025-04-26 18:32:44', 1);
INSERT INTO `ai_chat` VALUES (21, 100008, '你能说的点别的吗', '', '2025-04-26 18:32:54', 0);
INSERT INTO `ai_chat` VALUES (22, 100008, '你能说的点别的吗', '当然可以呀，不过咱还是先解决您在平台遇到的问题吧，这样您才能更愉快地使用“二货来了”呢。您是遇到啥困扰了，快跟我说说呗。', '2025-04-26 18:32:56', 1);
INSERT INTO `ai_chat` VALUES (23, 100008, '你好香呀', '', '2025-04-26 18:33:12', 0);
INSERT INTO `ai_chat` VALUES (24, 100008, '你好香呀', '您好！我是二货来了的智能客服助手——小二，有什么可以帮您的吗？', '2025-04-26 18:33:14', 1);
INSERT INTO `ai_chat` VALUES (25, 100008, '你不用一直自我介绍了', '', '2025-04-26 18:33:27', 0);
INSERT INTO `ai_chat` VALUES (26, 100008, '你不用一直自我介绍了', '好的，那您直接提问就好啦，我会尽力帮您解答~', '2025-04-26 18:33:28', 1);
INSERT INTO `ai_chat` VALUES (27, 100008, '那你能不能不要一直自我介绍', '', '2025-04-26 18:33:51', 0);
INSERT INTO `ai_chat` VALUES (28, 100008, '那你能不能不要一直自我介绍', '好的呢，那您刚刚说的问题，我可能不太明白，您可以详细说说吗？', '2025-04-26 18:33:53', 1);
INSERT INTO `ai_chat` VALUES (29, 100008, '我能为你唱首歌吗', '', '2025-04-26 18:34:07', 0);
INSERT INTO `ai_chat` VALUES (30, 100008, '我能为你唱首歌吗', '您好！我是二货来了的智能客服助手——小二，有什么可以帮您的吗？', '2025-04-26 18:34:09', 1);
INSERT INTO `ai_chat` VALUES (31, 100008, '你怎么又开始自我介绍了', '', '2025-04-26 18:34:23', 0);
INSERT INTO `ai_chat` VALUES (32, 100008, '你怎么又开始自我介绍了', '不好意思给您造成了困扰，可能我这边出现了一点小状况。请问您现在有什么关于校园二手交易平台“二货来了”的问题需要我来帮忙解答呢？', '2025-04-26 18:34:25', 1);
INSERT INTO `ai_chat` VALUES (33, 100008, '你好', '', '2025-05-06 19:25:36', 0);
INSERT INTO `ai_chat` VALUES (34, 100008, '你好', '您好！我是二货来了的智能客服助手——小二，有什么可以帮您的吗？', '2025-05-06 19:25:38', 1);
INSERT INTO `ai_chat` VALUES (35, 100008, '能帮我推荐一些商品吗', '', '2025-05-06 19:25:50', 0);
INSERT INTO `ai_chat` VALUES (36, 100008, '能帮我推荐一些商品吗', '您好！我是二货来了的智能客服助手——小二，有什么可以帮您的吗？\n\n以下是为您推荐的几类热门商品，您可以根据需求浏览：\n\n1. **电子产品类**：二手手机、平板电脑、耳机等，性价比高且实用性强，适合学生党或需要备用设备的同学。\n\n2. **学习用品类**：教材、笔记、考研资料等，价格实惠且容易找到学长学姐的干货，省时省力。\n\n3. **生活家居类**：小家电（如电饭锅、热水壶）、收纳箱、自行车等，毕业季或搬家时常有学长学姐低价转让。\n\n4. **时尚穿搭类**：衣物、鞋子、包包等，很多几乎全新的二手好物，适合追求环保和实惠的您。\n\n5. **兴趣爱好类**：书籍、运动器材、乐器等，既能满足个人兴趣，又能以更低的价格入手。\n\n您可以在平台首页点击“推荐商品”或使用搜索栏筛选关键词，轻松找到心仪好物。如果需要更具体的推荐，可以告诉我您的需求哦！(◕ᴗ◕✿)', '2025-05-06 19:26:01', 1);
INSERT INTO `ai_chat` VALUES (37, 100008, '如果发生商品纠纷了，该怎么办', '', '2025-05-06 19:26:30', 0);
INSERT INTO `ai_chat` VALUES (38, 100008, '如果发生商品纠纷了，该怎么办', '您好！我是二货来了的智能客服助手——小二，有什么可以帮您的吗？如果发生商品纠纷，别担心，您可以先通过平台内的消息功能与卖家沟通协商，说明问题并寻求解决方案。若协商无果，您可在订单详情页点击“申请仲裁”，提交相关证据（如商品照片、聊天记录等）。我们的客服团队会在24小时内介入处理，确保公平公正。建议您在交易时保留好凭证，以便顺利解决问题！', '2025-05-06 19:26:34', 1);
INSERT INTO `ai_chat` VALUES (39, 100008, '你好', '', '2025-05-06 20:08:23', 0);
INSERT INTO `ai_chat` VALUES (40, 100008, '你好', '您好！我是二货来了的智能客服助手——小二，有什么可以帮您的吗？', '2025-05-06 20:08:24', 1);
INSERT INTO `ai_chat` VALUES (41, 100008, '怎么才能阿觅处我的商品', '', '2025-05-06 20:08:50', 0);
INSERT INTO `ai_chat` VALUES (42, 100008, '怎么才能阿觅处我的商品', '您好！我是二货来了的智能客服助手——小二，有什么可以帮您的吗？', '2025-05-06 20:08:52', 1);
INSERT INTO `ai_chat` VALUES (43, 100008, '怎么卖出我的商品', '', '2025-05-06 20:09:15', 0);
INSERT INTO `ai_chat` VALUES (44, 100008, '怎么卖出我的商品', '您好！我是二货来了的智能客服助手——小二，有什么可以帮您的吗？\n\n您可以先打开APP，点击右上角的“发布”，填写商品信息，上传清晰的图片，设置合理的价格和交易方式。记得详细描述商品成色和使用情况，这样更容易吸引买家哦！发布后，您可以在“我的发布”里随时查看商品状态。如果有买家咨询，请及时回复，促成交易哦！', '2025-05-06 20:09:19', 1);
INSERT INTO `ai_chat` VALUES (45, 100008, '如何提高我的商品的销量', '', '2025-05-06 20:15:01', 0);
INSERT INTO `ai_chat` VALUES (46, 100008, '如何提高我的商品的销量', '想要提高商品销量，您可以从这几方面入手：\n- 商品描述要详细准确，突出亮点和优势，最好配上清晰、多角度的图片，让买家一眼心动。\n- 价格合理，参考一下平台上同类商品的价格，别定得过高或过低。\n- 选择合适的分类发布，这样目标买家更容易找到您的商品。\n- 及时回复买家的咨询，热情专业，促成交易。\n- 可以适当做一些促销活动，比如小幅度降价、赠送小礼品等。', '2025-05-06 20:15:07', 1);
INSERT INTO `ai_chat` VALUES (47, 100008, '请你列举一些平台的使用注意事项', '', '2025-05-06 20:17:06', 0);
INSERT INTO `ai_chat` VALUES (48, 100008, '请你列举一些平台的使用注意事项', '亲爱的同学，欢迎使用“二货来了”校园二手交易平台！以下是一些平台使用的注意事项，请务必仔细阅读并遵守哦：\n\n### 一、账号相关\n\n1. **注册信息**：请使用真实有效的手机号或邮箱进行注册，确保能接收到平台的重要通知，如验证码、交易提醒等。同时，设置一个安全可靠且易于记忆的密码，避免使用过于简单或与个人信息相关的密码，防止账号被盗用。\n\n2. **账号安全**：不要将账号借给他人使用，以免产生不必要的纠纷和风险。若发现账号异常，如登录提示错误、收到非本人操作的提示等，请及时联系平台客服进行处理。\n\n### 二、商品发布\n\n1. **信息真实性**：发布的商品信息必须真实准确，包括商品的名称、描述、成色、规格、配件情况等。不得虚假宣传或夸大商品的性能和价值，以免误导其他用户。\n\n2. **图片清晰**：上传的商品图片要清晰、完整，能够多角度展示商品的实际情况，让买家可以清楚地看到商品的外观、细节和瑕疵（如有）。建议使用高清照片，避免使用模糊、昏暗或过度修饰的图片。\n\n3. **价格合理**：根据商品的实际价值和市场行情来设定合理的价格。既不要过高定价，以免影响销售；也不要过低定价，以免造成不必要的损失或引发其他用户的质疑。\n\n4. **分类准确**：选择正确的商品分类，以便买家能够更容易地找到你的商品。如果分类错误，可能会导致商品被隐藏或难以被目标用户发现。\n\n### 三、交易流程\n\n1. **沟通协商**：在与买家或卖家沟通时，要保持礼貌、友好和耐心。清晰地表达自己的想法和要求，认真听取对方的意见，尽量通过协商解决交易中的问题。如有任何疑问或不确定的地方，及时向对方询问清楚，避免产生误解和纠纷。\n\n2. **交易方式**：优先选择平台提供的担保交易方式，以确保交易资金的安全。在完成交易前，不要轻易相信对方的线下交易请求或转账要求，以免遭受诈骗。\n\n3. **当面交易**：如果选择当面交易，建议在公共场所进行，如学校的指定交易地点、图书馆、教学楼大厅等。并告知身边的同学或朋友自己的交易时间和地点，以保障自身安全。在交易时，仔细检查商品的质量和数量是否与约定一致，确认无误后再进行付款和收货。\n\n4. **快递交易**：若采用快递方式交易，要选择可靠的快递公司，并妥善包装商品，确保商品在运输过程中不受损坏。在发货前，与买家确认好收货地址和联系方式，并保留好快递单号等相关信息，以便查询物流进度和处理可能出现的纠纷。\n\n### 四、评价与反馈\n\n1. **客观评价**：交易完成后，请根据实际情况对交易对象进行客观、公正的评价。评价内容应真实反映交易的过程和结果，包括商品的质量、卖家的服务态度、交易的顺利程度等。不要恶意诋毁或无端表扬，以免影响其他用户的判断。\n\n2. **反馈问题**：如果在使用平台过程中遇到任何问题或有任何建议，请及时向平台客服反馈。你的反馈将帮助我们不断改进和完善平台的服务，为同学们提供更好的交易体验。\n\n希望同学们在“二货来了”平台上能够愉快地进行二手交易，遵守平台规则，共同营造一个安全、便捷、诚信的交易环境哦！', '2025-05-06 20:17:38', 1);
INSERT INTO `ai_chat` VALUES (49, 100008, '列举一些好的商品', '', '2025-05-06 20:19:30', 0);
INSERT INTO `ai_chat` VALUES (50, 100008, '列举一些好的商品', '以下是一些在校园二手交易平台上比较受欢迎的商品哦：\n\n学习用品类：\n- 各类教材、辅导书，很多学长学姐毕业后会低价出售，对于低年级的同学来说能节省不少买新书的钱呢。\n- 笔记本，有些同学可能只用了几页就闲置了，成色新价格又实惠。\n- 中性笔、铅笔等文具，批量购买很划算，尤其是一些品牌文具，质量有保障。\n\n生活用品类：\n- 小台灯，方便在宿舍床头或者自习室使用，二手的价格往往比新的便宜很多。\n- 水壶、保温杯，实用又好携带，毕业季经常会有同学出手。\n- 收纳盒，可以用来整理衣物、杂物等，让宿舍更整洁。\n\n电子产品类：\n- 耳机，像有线耳机、蓝牙耳机等，功能正常且价格合适的话很抢手。\n- 移动硬盘、U盘，存储学习资料很方便，二手的性价比挺高的。\n- 平板电脑，适合看课件、追剧等，只要没有大的损坏，很容易成交。\n\n体育用品类：\n- 篮球、足球、羽毛球拍等，有些同学可能买了后没怎么玩就转手，价格美丽。\n- 瑜伽垫，想在宿舍或者操场锻炼的同学会需要，二手的也很实用。\n\n时尚服饰类：\n- 四季的衣服，尤其是一些品牌的外套、连衣裙等，如果尺码合适、款式不过时，会很受欢迎。\n- 运动鞋、休闲鞋，成色较好的二手鞋，价格只是新鞋的一半甚至更低，很受青睐。\n\n其他：\n- 自行车，在校园里代步超方便，二手自行车经过简单打理，性价比高。\n- 小书架、置物架，能帮助打造温馨的宿舍小角落，二手的价格优势明显。', '2025-05-06 20:19:48', 1);
INSERT INTO `ai_chat` VALUES (51, 100008, '你好', '', '2025-06-30 15:15:24', 0);
INSERT INTO `ai_chat` VALUES (52, 100008, '你好', '你好呀！欢迎来「二货来了」，我是小二，有啥我能帮你的？不管是平台咋用、咋发商品还是交易流程啥的，都可以问我哦。', '2025-06-30 15:15:25', 1);

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goodid` int(11) NULL DEFAULT NULL,
  `goodownerid` int(11) NULL DEFAULT NULL,
  `cartnum` int(11) NULL DEFAULT NULL,
  `cartprice` double NULL DEFAULT NULL,
  `cartuser` int(11) NULL DEFAULT NULL,
  `carttime` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = FIXED;

-- ----------------------------
-- Records of cart
-- ----------------------------

-- ----------------------------
-- Table structure for categories
-- ----------------------------
DROP TABLE IF EXISTS `categories`;
CREATE TABLE `categories`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分类名称',
  `parent_id` int(11) NULL DEFAULT 0 COMMENT '父分类ID，0为顶级分类',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 204 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商品分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of categories
-- ----------------------------
INSERT INTO `categories` VALUES (1, '学习用品', 0);
INSERT INTO `categories` VALUES (2, '电子产品', 0);
INSERT INTO `categories` VALUES (3, '生活日用', 0);
INSERT INTO `categories` VALUES (4, '服饰鞋包', 0);
INSERT INTO `categories` VALUES (101, '教材教辅', 1);
INSERT INTO `categories` VALUES (102, '考研资料', 1);
INSERT INTO `categories` VALUES (103, '课外书籍', 1);
INSERT INTO `categories` VALUES (104, '图书音像', 1);
INSERT INTO `categories` VALUES (201, '手机', 2);
INSERT INTO `categories` VALUES (202, '电脑配件', 2);
INSERT INTO `categories` VALUES (203, '耳机音响', 2);

-- ----------------------------
-- Table structure for collect
-- ----------------------------
DROP TABLE IF EXISTS `collect`;
CREATE TABLE `collect`  (
  `userId` int(11) NOT NULL,
  `goodId` int(11) NOT NULL,
  PRIMARY KEY (`userId`, `goodId`) USING BTREE,
  INDEX `goodId`(`goodId`) USING BTREE,
  CONSTRAINT `collect_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `collect_ibfk_2` FOREIGN KEY (`goodId`) REFERENCES `goods` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of collect
-- ----------------------------
INSERT INTO `collect` VALUES (100001, 900007);
INSERT INTO `collect` VALUES (100003, 900007);
INSERT INTO `collect` VALUES (100006, 900007);
INSERT INTO `collect` VALUES (100008, 900029);
INSERT INTO `collect` VALUES (100001, 900033);
INSERT INTO `collect` VALUES (100007, 900035);
INSERT INTO `collect` VALUES (100007, 900043);

-- ----------------------------
-- Table structure for course_textbook
-- ----------------------------
DROP TABLE IF EXISTS `course_textbook`;
CREATE TABLE `course_textbook`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `course_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程名称',
  `textbook_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '教材名称',
  `isbn` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ISBN号',
  `publisher` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '出版社',
  `author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '作者',
  `semester` int(11) NOT NULL DEFAULT 0 COMMENT '学期 (0-第一学期，1-第二学期)',
  `major` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '专业',
  `recommend_level` int(11) NOT NULL DEFAULT 3 COMMENT '推荐等级 (1-5，5为最高)',
  `is_required` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否必修教材 (0-否，1-是)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_course_name`(`course_name`) USING BTREE,
  INDEX `idx_textbook_name`(`textbook_name`) USING BTREE,
  INDEX `idx_semester`(`semester`) USING BTREE,
  INDEX `idx_major`(`major`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '课程与教材对应关系表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of course_textbook
-- ----------------------------
INSERT INTO `course_textbook` VALUES (1, '高等数学(上)', '高等数学(第七版)', '9787040429664', '高等教育出版社', '同济大学数学系', 0, '理工类', 5, 1);
INSERT INTO `course_textbook` VALUES (2, '高等数学(下)', '高等数学(第七版)', '9787040429671', '高等教育出版社', '同济大学数学系', 1, '理工类', 5, 1);
INSERT INTO `course_textbook` VALUES (3, '大学物理(上)', '大学物理学(第三版)', '9787302516675', '清华大学出版社', '赵近芳', 0, '理工类', 5, 1);
INSERT INTO `course_textbook` VALUES (4, '大学物理(下)', '大学物理学(第三版)', '9787302516682', '清华大学出版社', '赵近芳', 1, '理工类', 5, 1);
INSERT INTO `course_textbook` VALUES (5, '大学英语(1)', '新视野大学英语(第三版)', '9787513556354', '外语教学与研究出版社', '郑树棠', 0, '通用', 4, 1);
INSERT INTO `course_textbook` VALUES (6, '大学英语(2)', '新视野大学英语(第三版)', '9787513556361', '外语教学与研究出版社', '郑树棠', 1, '通用', 4, 1);
INSERT INTO `course_textbook` VALUES (7, '程序设计基础', 'C程序设计(第四版)', '9787302481447', '清华大学出版社', '谭浩强', 0, '计算机', 5, 1);
INSERT INTO `course_textbook` VALUES (8, '数据结构', '数据结构(C语言版)', '9787302330646', '清华大学出版社', '严蔚敏', 1, '计算机', 5, 1);
INSERT INTO `course_textbook` VALUES (9, '操作系统', '计算机操作系统(第四版)', '9787040408881', '高等教育出版社', '汤小丹', 0, '计算机', 4, 1);
INSERT INTO `course_textbook` VALUES (10, '计算机网络', '计算机网络(第七版)', '9787121201677', '机械工业出版社', '谢希仁', 1, '计算机', 4, 1);
INSERT INTO `course_textbook` VALUES (11, '微观经济学', '西方经济学(微观部分)', '9787301268902', '北京大学出版社', '高鸿业', 0, '经济管理', 5, 1);
INSERT INTO `course_textbook` VALUES (12, '宏观经济学', '西方经济学(宏观部分)', '9787301268919', '北京大学出版社', '高鸿业', 1, '经济管理', 5, 1);
INSERT INTO `course_textbook` VALUES (13, '会计学原理', '会计学原理(第三版)', '9787302413332', '清华大学出版社', '陈国辉', 0, '经济管理', 4, 1);
INSERT INTO `course_textbook` VALUES (14, '市场营销学', '市场营销学(第五版)', '9787301273456', '北京大学出版社', '吴健安', 1, '经济管理', 4, 1);
INSERT INTO `course_textbook` VALUES (15, '有机化学', '有机化学(第四版)', '9787122299444', '化学工业出版社', '徐寿昌', 0, '化学', 5, 1);
INSERT INTO `course_textbook` VALUES (16, '无机化学', '无机化学(第四版)', '9787040307368', '高等教育出版社', '武汉大学', 1, '化学', 5, 1);
INSERT INTO `course_textbook` VALUES (17, '分析化学', '分析化学(第六版)', '9787040283945', '高等教育出版社', '华东理工大学', 0, '化学', 4, 1);
INSERT INTO `course_textbook` VALUES (18, '物理化学', '物理化学(第五版)', '9787040168358', '高等教育出版社', '天津大学', 1, '化学', 4, 1);
INSERT INTO `course_textbook` VALUES (19, '人工智能', '人工智能(柴玉美、张坤丽主编)', '9787111384014', '机械工业出版社', '郑州大学', 1, '计算机', 5, 1);

-- ----------------------------
-- Table structure for feedback
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `contact` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of feedback
-- ----------------------------
INSERT INTO `feedback` VALUES (1, '哈哈', '联系方式', '2018-08-25 19:04:45');
INSERT INTO `feedback` VALUES (2, 'sadlfjsaldfjlsadjf', 'sjdfalasjdf', '2018-08-25 19:05:54');
INSERT INTO `feedback` VALUES (3, 'sadfsdf', '1111', '2018-08-25 19:07:53');
INSERT INTO `feedback` VALUES (4, '可以啊，不错奥，阿发 ', '888888', '2020-02-22 13:16:34');
INSERT INTO `feedback` VALUES (5, '建议加大宣传', '', '2020-02-25 21:02:29');
INSERT INTO `feedback` VALUES (6, '希望有界面的美化哦，谢谢', '', '2020-03-21 15:57:57');

-- ----------------------------
-- Table structure for gen_table
-- ----------------------------
DROP TABLE IF EXISTS `gen_table`;
CREATE TABLE `gen_table`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `table_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据表名',
  `module_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模块名',
  `biz_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务名',
  `create_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '代码生成表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of gen_table
-- ----------------------------
INSERT INTO `gen_table` VALUES (5, 'test_gen', 'test', 'cpp', '2019-05-16 13:21:20');
INSERT INTO `gen_table` VALUES (6, 'users', 'shop', 'users', '2020-02-21 22:56:57');
INSERT INTO `gen_table` VALUES (7, 'message', 'shop', 'message', '2020-02-21 22:57:19');
INSERT INTO `gen_table` VALUES (8, 'leave_msg', 'shop', 'leavemsg', '2020-02-21 22:57:57');
INSERT INTO `gen_table` VALUES (9, 'goods', 'shop', 'goods', '2020-02-21 22:58:13');
INSERT INTO `gen_table` VALUES (10, 'feedback', 'shop', 'feedback', '2020-02-21 22:58:30');
INSERT INTO `gen_table` VALUES (11, 'collect', 'shop', 'collect', '2020-02-21 22:58:45');

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goodname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `goodownerid` int(11) NOT NULL,
  `price` decimal(10, 2) NOT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `num` int(10) NOT NULL,
  `category_id` int(11) NULL DEFAULT NULL COMMENT '分类ID',
  `time` datetime NOT NULL,
  `lid` int(11) NULL DEFAULT NULL,
  `userId` int(11) NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT 0,
  `view_count` int(11) NOT NULL DEFAULT 0 COMMENT '浏览次数',
  `inquiry_count` int(11) NOT NULL DEFAULT 0 COMMENT '询问次数',
  `location` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '交易地点',
  `isbn` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `chubanshe` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `author` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `chubantime` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `goodowner`(`goodownerid`) USING BTREE,
  INDEX `fk_goods_category`(`category_id`) USING BTREE,
  CONSTRAINT `fk_goods_category` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `goods_ibfk_1` FOREIGN KEY (`goodownerid`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 900050 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (900001, '书本', 100001, 380.00, '书本', 'TB23zQanFXXXXXLXXXXXX!!8.jpg;', 1, 101, '2020-02-25 17:46:29', NULL, 100007, 0, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `goods` VALUES (900003, '书本2', 100005, 570.00, '书本2', 'TB23zQanFXXXXXLXXXXXXXXXXXX_!!851172226.jpg;', 1, 101, '2020-02-25 17:46:29', NULL, 100007, 0, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `goods` VALUES (900004, '乌木', 100001, 199.00, '乌木', 'TB2_tqPhTnI8KJj.jpg;TB2nFCrhInI8KJjSsz.jpg;TB2yNWBhJ.jpg;', 1, 101, '2020-02-25 17:46:29', NULL, NULL, 0, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `goods` VALUES (900007, '书本3', 100005, 98.00, '书本3', 'TB1yFlUPpXXXXc.jpg;TB205wnaCqJ.eB.jpg;TB2XSfKaOGO.eBjS.jpg;TB2GjvHaRyN.eBjS.jpg;', 1, 101, '2020-02-25 17:46:29', NULL, NULL, 0, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `goods` VALUES (900008, '联想笔记本', 100005, 1399.00, '联想笔记本', 'TB2Pe6ug9YH8KJj.jpg;TB2eT1EcpHM8KJj.jpg;TB2SkuKbPgy_.jpg;', 1, 101, '2020-02-25 17:46:29', NULL, NULL, 0, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `goods` VALUES (900009, '扫地机器人书本', 100005, 170.00, '23', 'TB2FHDqb2jM.jpg;TB29Avjb3jN8KJjS.jpg;TB29Avjb3jN8KJjSZFgX.jpg;', 1, 101, '2020-02-25 17:46:29', NULL, NULL, 0, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `goods` VALUES (900010, '读物隐藏的图画系列', 100001, 30.00, '2', 'TB2UygFdVXXX.jpg;TB25UQxdVXXXXbNXX.jpg;', 1, 101, '2020-02-25 17:46:29', NULL, NULL, 0, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `goods` VALUES (900011, '书本3', 100001, 75.00, '书本3', 'TB2jodreI2vU1JjS.jpg;TB2kGsheGmgSKJjS.jpg;TB2qsXZiYsTMeJj.jpg;TB2OvmAcEMgYe.jpg;', 1, 101, '2020-02-25 17:46:29', NULL, NULL, 0, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `goods` VALUES (900012, '扫地机器人书本', 100001, 550.00, '扫地机器人书本', 'n_v24e52703b4b1a4e.jpg;n_v20bfc79ffa1eb4.jpg;n_v2ea8152a21d584.jpg;', 1, 101, '2020-02-25 17:46:29', NULL, NULL, 0, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `goods` VALUES (900013, '凤凰山地自行车240', 100001, 240.00, '凤凰山地自行车240', 'TB26c0odBLN8K.jpg;', 1, 101, '2020-02-25 17:46:29', NULL, NULL, 0, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `goods` VALUES (900014, '电动刮胡刀', 100001, 48.00, '电动刮胡刀', 'n_v27cdab203ec3.jpg;n_v2bd80ae2.jpg;n_v287188565038.jpg;', 1, 101, '2020-02-25 17:46:29', NULL, NULL, 0, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `goods` VALUES (900015, '儿童读物隐藏的图画系列', 100001, 30.00, '儿童读物隐藏的图画系列', 'n_v232d524a59.jpg;n_v2e928753.jpg;n_v2d5f4542.jpg;', 1, 101, '2020-02-25 17:46:29', NULL, NULL, 0, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `goods` VALUES (900016, '16本，打包了，宝贝们！', 100001, 200.00, '16本，打包了，宝贝们！', 'n_v22f4cb02.jpg;n_v2a0fe25baa.jpg;n_v2147d4c0fba874.jpg;', 1, 101, '2020-02-25 17:46:29', NULL, NULL, 0, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `goods` VALUES (900017, '测试', 100001, 5.00, '测试', 'n_v25a1638164.jpg;n_v2895a89cb.jpg;n_v2b6d440.jpg;', 1, 101, '2020-02-25 17:46:29', NULL, NULL, 0, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `goods` VALUES (900018, '测试', 100001, 95.00, '测试', 'n_v21acd7bf47f4.jpg;n_v2e03de553d.jpg;n_v272397500c67.jpg;', 1, 101, '2020-02-25 17:46:29', NULL, NULL, 0, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `goods` VALUES (900019, '全新usb供电小音箱', 100001, 15.00, '全新usb供电小音箱', 'n_v1bl2lwkc.jpg;n_v1bl2lwx.jpg;n_v1bkujjd.jpg;', 1, 101, '2020-02-25 17:46:29', NULL, NULL, 0, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `goods` VALUES (900020, '测试吧', 100005, 288.88, '测试吧', 'TB29B99hv2H8KJj.jpg;TB2caYBhv2H8KJjy1z.jpg;', 1, 101, '2020-02-25 17:46:29', NULL, NULL, 0, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `goods` VALUES (900021, '大学计算机三级考试', 100005, 126.00, '大学计算机三级考试', 'TB2xkKoXk.Oyu.jpg;TB2qaBxXhiEJuJjS.jpg;TB2FramXeIPyuJjS.jpg;', 1, 101, '2020-02-25 17:46:29', NULL, NULL, 0, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `goods` VALUES (900022, '三国演义', 100005, 130.00, '三国演义', 'TB2CNe8iwn.jpg;TB2eUmKitz8.jpg;TB2Xf2uiv2H8KJ.jpg;', 1, 101, '2020-02-25 17:46:29', NULL, NULL, 0, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `goods` VALUES (900023, '世界经典名著注音版（全四册）法布尔昆虫记正版 亚米契斯爱的教', 100005, 23.00, '世界经典名著注音版（全四册）法布尔昆虫记正版 亚米契斯爱的教', 'n_v2dbc1be9a.jpg;n_v2b783e4f7.jpg;n_v2205a4c076.jpg;', 1, 101, '2020-02-25 17:46:29', NULL, NULL, 0, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `goods` VALUES (900024, '低价20转让两本励志女性畅销书籍心理学文化修养情绪必读书', 100005, 20.00, '低价20转让两本励志女性畅销书籍心理学文化修养情绪必读书', 'n_v271afc4f8.jpg;n_v203939f9ff5.jpg;n_v26c3b19e5.jpg;', 1, 101, '2020-02-25 17:46:29', NULL, NULL, 0, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `goods` VALUES (900025, '写生设计～中央美术学院设计学院基础部学生课程教学 正版包邮', 100005, 18.00, '写生设计～中央美术学院设计学院基础部学生课程教学 正版包邮', 'n_v20f519e45e.jpg;n_v28fed28dbfa.jpg;n_v2271e41.jpg;', 1, 101, '2020-02-25 17:46:29', NULL, NULL, 0, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `goods` VALUES (900026, '四大名著 畅销书 共4册', 100005, 35.00, '四大名著 畅销书 共4册', 'n_v20e0a5c4.jpg;n_v2a5a9932.jpg;n_v27e8c0f74.jpg;', 1, 101, '2020-02-25 17:46:29', NULL, NULL, 0, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `goods` VALUES (900027, '钱币图库书一套3本，包邮', 100005, 40.00, '钱币图库书一套3本，包邮', 'n_v2b1810d51.jpg;n_v215c3382.jpg;n_v2149bff.jpg;', 1, 101, '2020-02-25 17:46:29', NULL, NULL, 0, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `goods` VALUES (900028, '世界未解之谜全套(8本)', 100005, 28.00, '世界未解之谜全套(8本)', 'n_v2d9c336bd7.jpg;n_v22e1d2.jpg;n_v261e54f97.jpg;', 1, 101, '2020-02-25 17:46:29', NULL, NULL, 0, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `goods` VALUES (900029, '山香教师资格证书，卷子 包邮', 100005, 40.00, '山香教师资格证书，卷子 包邮', 'n_v287f7748.jpg;n_v28be0e59b5986.jpg;n_v220f1221.jpg;', 1, 101, '2020-02-25 17:46:29', NULL, NULL, 0, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `goods` VALUES (900030, '世界经典名著注音版（全四册）法布尔昆虫记正版 亚米契斯爱的教', 100005, 23.00, '世界经典名著注音版（全四册）法布尔昆虫记正版 亚米契斯爱的教', 'n_v2dbc1be9a.jpg;n_v2b783e4f7.jpg;n_v2205a4c076.jpg;', 1, 101, '2020-02-25 17:46:29', NULL, NULL, 0, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `goods` VALUES (900031, '四大名著 畅销书 共4册', 100005, 35.00, '四大名著 畅销书 共4册', 'n_v20e0a5c4.jpg;n_v2a5a9932.jpg;n_v27e8c0f74.jpg;', 1, 101, '2020-02-25 17:46:29', NULL, NULL, 0, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `goods` VALUES (900032, '红楼梦', 100005, 570.00, '红楼梦', 'TB23zQanFXXXXXLXXXXXXXXXXXX_!!851172226.jpg;', 1, 101, '2020-02-25 17:46:29', NULL, NULL, 0, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `goods` VALUES (900033, '笔记本 文具 记事本 办公用品 学生日记本 全新', 100005, 5.00, '笔记本 文具 记事本 办公用品 学生日记本 全新', 'n_v29779f65.jpg;n_v27298aa.jpg;n_v21aa2d.jpg;', 1, 101, '2020-02-25 17:46:29', NULL, NULL, 0, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `goods` VALUES (900034, '得力笔筒创意时尚圆形笔筒简约办公室学生桌面文具收纳黑色笔筒', 100005, 6.00, '得力笔筒创意时尚圆形笔筒简约办公室学生桌面文具收纳黑色笔筒', 'n_v2ba0108215.jpg;n_v228442f729.jpg;n_v2f8483e27.jpg;', 1, 101, '2020-02-25 17:46:29', NULL, NULL, 0, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `goods` VALUES (900035, '儿童小学生智能写不断铅笔全自动2B铅笔文具礼品套装', 100005, 25.00, '儿童小学生智能写不断铅笔全自动2B铅笔文具礼品套装', 'n_v225716a2.jpg;n_v2c19b7e.jpg;n_v2cf59e4.jpg;', 1, 101, '2020-02-25 17:46:29', NULL, NULL, 0, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `goods` VALUES (900036, '水浒传', 100005, 6.00, '水浒传', 'n_v1bj3gz2.jpg;n_v1bkuyfvm.jpg;n_v1bkuy.jpg;', 1, 101, '2020-02-25 17:46:29', NULL, NULL, 0, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `goods` VALUES (900037, '大国战争', 100005, 9.00, '大国战争', 'n_v1bl2lwwpd.jpg;n_v1bl2lwt.jpg;', 1, 101, '2020-02-25 17:46:29', NULL, NULL, 0, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `goods` VALUES (900040, '测试', 100007, 10.00, '测试', 'n_v20e0a5c4.jpg;n_v2a5a9932.jpg;n_v27e8c0f74.jpg;', -1, 101, '2020-02-25 18:55:36', NULL, NULL, 1, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `goods` VALUES (900041, '大erect', 100007, 0.00, '大erect', 'n_v20e0a5c4.jpg;n_v2a5a9932.jpg;n_v27e8c0f74.jpg;;', -1, 101, '2020-02-25 20:22:52', NULL, NULL, 0, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `goods` VALUES (900042, '我不相信你', 100007, 0.00, '我不相信你', 'n_v20e0a5c4.jpg;n_v2a5a9932.jpg;n_v27e8c0f74.jpg;', -1, 101, '2020-02-25 21:00:32', NULL, NULL, 0, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `goods` VALUES (900043, '23', 100007, 2.00, '23', 'n_v20e0a5c4.jpg;n_v2a5a9932.jpg;n_v27e8c0f74.jpg;', 1, 101, '2020-03-09 23:40:48', NULL, NULL, 0, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `goods` VALUES (900044, '人工智能', 100008, 18.00, '啦啦啦', 'huXSQ7eNY5f0b979bee03fe8ca44b33291290efc5c2c.png;', -1, 101, '2025-05-06 22:31:29', NULL, NULL, 0, 0, 0, NULL, '12233242', '科教', '无', '2025/1/1');
INSERT INTO `goods` VALUES (900045, '人工智能', 100008, 15.00, '', 'vlST9Bi8r2yDa81a4e612ed86c9a112747ca9295fcaa.png;', -1, 101, '2025-05-09 00:03:42', NULL, NULL, 0, 0, 0, NULL, '12345678910', '机械工业出版社', '柴玉梅,张坤丽', '2012');
INSERT INTO `goods` VALUES (900046, '人工智能(柴玉美、张坤丽主编)', 100008, 10.00, '', 'MoU8C4mbdExMa81a4e612ed86c9a112747ca9295fcaa.png;', 1, 101, '2025-05-10 17:39:33', NULL, NULL, 0, 0, 0, NULL, '9787111384014', '机械工业出版社', '柴玉梅，张坤丽', '2012');
INSERT INTO `goods` VALUES (900047, '高等数学(第七版)', 100008, 15.00, '', 'hokquERNJC0I21ef879f97e1c1a8b93425f79724a092.webp;', 1, 101, '2025-05-10 19:36:30', NULL, NULL, 0, 0, 0, NULL, '9787040429664', '高等教育出版社', '同济大学数学系', '2012');
INSERT INTO `goods` VALUES (900048, '数据结构(C语言版)', 100008, 18.00, '', 'x3WqGQbFmQZNa884f9151248a4df79202f3f2679a5de.jpg;', 1, 101, '2025-05-10 19:38:52', NULL, NULL, 0, 0, 0, NULL, '9787302330646', '清华大学出版社', '严蔚敏', '2012');
INSERT INTO `goods` VALUES (900049, '测试商品', 100008, 1.00, '', '8Rvv36mE0Z9Gb979bee03fe8ca44b33291290efc5c2c.png;', 1, 101, '2025-05-10 22:17:46', NULL, NULL, 0, 0, 0, NULL, '12345678900', '无', '无名', '2025');

-- ----------------------------
-- Table structure for leave_msg
-- ----------------------------
DROP TABLE IF EXISTS `leave_msg`;
CREATE TABLE `leave_msg`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `senduserid` int(10) NOT NULL,
  `reciveuserid` int(10) NOT NULL,
  `goodid` int(11) NOT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `time` datetime NOT NULL,
  `reply` int(10) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sendid`(`senduserid`) USING BTREE,
  INDEX `reciveid`(`reciveuserid`) USING BTREE,
  INDEX `good`(`goodid`) USING BTREE,
  INDEX `reply`(`reply`) USING BTREE,
  CONSTRAINT `leave_msg_ibfk_1` FOREIGN KEY (`goodid`) REFERENCES `goods` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `leave_msg_ibfk_2` FOREIGN KEY (`reciveuserid`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `leave_msg_ibfk_3` FOREIGN KEY (`senduserid`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `leave_msg_ibfk_4` FOREIGN KEY (`reply`) REFERENCES `leave_msg` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of leave_msg
-- ----------------------------
INSERT INTO `leave_msg` VALUES (22, 100001, 100005, 900001, '456', '2018-06-09 12:18:15', 0);
INSERT INTO `leave_msg` VALUES (26, 100000, 100000, 900004, '1', '2018-06-30 19:27:45', 0);
INSERT INTO `leave_msg` VALUES (28, 100001, 100001, 900010, '嘻嘻嘻', '2018-06-30 19:36:55', 0);
INSERT INTO `leave_msg` VALUES (29, 100001, 100005, 900001, '留了个言', '2018-07-01 16:32:47', 0);
INSERT INTO `leave_msg` VALUES (30, 100001, 100005, 900001, '123', '2018-07-01 16:37:49', 0);
INSERT INTO `leave_msg` VALUES (38, 100006, 100005, 900030, '受打击啊发', '2018-08-25 17:20:28', 0);
INSERT INTO `leave_msg` VALUES (39, 100000, 100000, 900001, '（附加记录）', '2018-04-25 16:10:07', 0);
INSERT INTO `leave_msg` VALUES (41, 100008, 100007, 900043, '你好', '2025-05-06 20:05:07', 0);
INSERT INTO `leave_msg` VALUES (42, 100001, 100001, 900004, '可以便宜点吗？', '2018-06-03 23:35:42', 0);

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `senduserid` int(10) NOT NULL,
  `reciveuserid` int(10) NOT NULL,
  `goodid` int(11) NOT NULL,
  `status` int(2) NOT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `time` datetime NOT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sendid`(`senduserid`) USING BTREE,
  INDEX `reciveid`(`reciveuserid`) USING BTREE,
  INDEX `good`(`goodid`) USING BTREE,
  CONSTRAINT `message_ibfk_1` FOREIGN KEY (`goodid`) REFERENCES `goods` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `message_ibfk_2` FOREIGN KEY (`reciveuserid`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `message_ibfk_3` FOREIGN KEY (`senduserid`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (1, 100000, 100001, 900001, 2, '您好，欢迎加入转赚', '2018-04-21 23:02:15', '通知消息');
INSERT INTO `message` VALUES (14, 100001, 100005, 900003, 0, '哈喽丽丽', '2018-06-11 23:29:27', '用户消息');
INSERT INTO `message` VALUES (15, 100001, 100005, 900021, 0, '丽丽你好', '2018-06-11 23:29:55', '用户消息');
INSERT INTO `message` VALUES (16, 100005, 100001, 900021, 0, '你好阿强', '2018-06-11 23:30:42', '用户消息');
INSERT INTO `message` VALUES (17, 100007, 100001, 900019, 0, '123123213', '2020-02-20 14:00:35', '鐢ㄦ埛娑堟伅');
INSERT INTO `message` VALUES (18, 100007, 100001, 900019, 0, '二二恶而额', '2020-02-22 13:17:18', '用户消息');
INSERT INTO `message` VALUES (19, 100007, 100007, 900040, 0, '1212', '2020-02-25 20:07:00', '用户消息');
INSERT INTO `message` VALUES (20, 100007, 100007, 900042, 0, '您好', '2020-02-25 21:01:41', '用户消息');
INSERT INTO `message` VALUES (21, 100007, 100007, 900042, 0, '在不，这个你什么时候捡到的', '2020-02-25 21:02:00', '用户消息');
INSERT INTO `message` VALUES (22, 100007, 100005, 900035, 0, '12313123123', '2020-03-09 23:35:51', '用户消息');
INSERT INTO `message` VALUES (23, 100007, 100005, 900035, 0, '123131231', '2020-03-09 23:35:57', '用户消息');
INSERT INTO `message` VALUES (24, 100007, 100005, 900035, 0, '123123', '2020-03-18 22:12:13', '用户消息');
INSERT INTO `message` VALUES (25, 100007, 100001, 900019, 0, '232323', '2020-03-18 22:12:20', '用户消息');
INSERT INTO `message` VALUES (26, 100007, 100001, 900019, 0, '1232132', '2020-03-18 22:33:11', '用户消息');
INSERT INTO `message` VALUES (27, 100007, 100007, 900042, 0, '12332', '2020-03-21 02:41:59', '用户消息');
INSERT INTO `message` VALUES (28, 100007, 100005, 900035, 0, '1233', '2020-03-21 02:42:05', '用户消息');
INSERT INTO `message` VALUES (29, 100007, 100007, 900043, 0, '你好，我想要这边书，', '2020-03-21 15:56:11', '用户消息');
INSERT INTO `message` VALUES (30, 100007, 100007, 900043, 0, '在吗，亲亲', '2020-03-21 15:56:19', '用户消息');
INSERT INTO `message` VALUES (31, 100008, 100007, 900043, 0, 'ss', '2025-03-05 19:00:08', '用户消息');
INSERT INTO `message` VALUES (32, 100008, 100001, 900001, 0, '11', '2025-03-05 19:12:06', '用户消息');
INSERT INTO `message` VALUES (33, 100008, 100001, 900014, 0, '123', '2025-03-05 19:46:26', '用户消息');
INSERT INTO `message` VALUES (34, 100008, 100001, 900001, 0, '123', '2025-03-05 19:47:12', '用户消息');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `goodid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '书籍ID',
  `goodownerid` int(11) NOT NULL COMMENT '所有者',
  `price` decimal(10, 2) NOT NULL COMMENT '单价',
  `total` decimal(10, 2) NOT NULL COMMENT '总计',
  `num` int(10) NOT NULL,
  `category` varchar(25) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `ordertime` datetime NOT NULL,
  `userId` int(11) NULL DEFAULT NULL COMMENT '购买用户',
  `status` int(11) NULL DEFAULT 0 COMMENT '0未支付，1已支付未发货，2已支付已发货，3.已收货完成',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (1, '900043', 100007, 2.00, 2.00, 1, '图书音像;', '2020-03-20 23:51:03', 100007, 3);
INSERT INTO `orders` VALUES (2, '900010', 100001, 30.00, 30.00, 1, '图书音像;', '2020-03-21 01:10:26', 100007, 3);
INSERT INTO `orders` VALUES (3, '900043', 100007, 2.00, 2.00, 1, '图书音像;', '2020-03-21 15:40:48', 100007, 3);
INSERT INTO `orders` VALUES (4, '900016', 100001, 200.00, 200.00, 1, '图书音像;', '2020-03-21 15:55:15', 100007, 1);
INSERT INTO `orders` VALUES (5, '900043', 100007, 2.00, 2.00, 1, '图书音像;', '2020-03-21 15:59:31', 100007, 3);
INSERT INTO `orders` VALUES (6, '900043', 100007, 2.00, 2.00, 1, '图书音像;', '2025-03-05 18:59:47', 100008, 1);

-- ----------------------------
-- Table structure for product_similarity
-- ----------------------------
DROP TABLE IF EXISTS `product_similarity`;
CREATE TABLE `product_similarity`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `good_id_1` int(11) NOT NULL COMMENT '商品1',
  `good_id_2` int(11) NOT NULL COMMENT '商品2',
  `similarity_score` float NOT NULL DEFAULT 0 COMMENT '相似度分数',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_product_similarity_pair`(`good_id_1`, `good_id_2`) USING BTREE,
  INDEX `idx_product_similarity_good_1`(`good_id_1`) USING BTREE,
  INDEX `idx_product_similarity_good_2`(`good_id_2`) USING BTREE,
  CONSTRAINT `fk_product_similarity_good_1` FOREIGN KEY (`good_id_1`) REFERENCES `goods` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_product_similarity_good_2` FOREIGN KEY (`good_id_2`) REFERENCES `goods` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of product_similarity
-- ----------------------------

-- ----------------------------
-- Table structure for recommendation_logs
-- ----------------------------
DROP TABLE IF EXISTS `recommendation_logs`;
CREATE TABLE `recommendation_logs`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `recommended_good_id` int(11) NOT NULL COMMENT '推荐商品ID',
  `recommended_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '推荐时间',
  `clicked` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否点击',
  `recommendation_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '推荐类型(content/popular/etc)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_recommendation_logs_user`(`user_id`) USING BTREE,
  INDEX `idx_recommendation_logs_good`(`recommended_good_id`) USING BTREE,
  CONSTRAINT `fk_recommendation_logs_good` FOREIGN KEY (`recommended_good_id`) REFERENCES `goods` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_recommendation_logs_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of recommendation_logs
-- ----------------------------

-- ----------------------------
-- Table structure for sys_biz_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_biz_log`;
CREATE TABLE `sys_biz_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日志标题',
  `params` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '记录参数',
  `class_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行类',
  `method` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行方法',
  `create_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 67 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '业务日志' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_biz_log
-- ----------------------------
INSERT INTO `sys_biz_log` VALUES (40, '保存字典表', '{id:null,}', 'com.ins1st.modules.sys.controller.SysDictController', 'save', '2019-05-15 10:18:48');
INSERT INTO `sys_biz_log` VALUES (41, '保存字典表', '{id:null,}', 'com.ins1st.modules.sys.controller.SysDictController', 'save', '2019-05-15 10:19:05');
INSERT INTO `sys_biz_log` VALUES (42, '保存代码生成表', '{id:null,}', 'com.ins1st.modules.gen.table.controller.GenTableController', 'save', '2019-05-16 11:22:25');
INSERT INTO `sys_biz_log` VALUES (43, '保存代码生成表', '{id:null,}', 'com.ins1st.modules.gen.table.controller.GenTableController', 'save', '2019-05-16 11:22:42');
INSERT INTO `sys_biz_log` VALUES (44, '保存代码生成表', '{id:null,}', 'com.ins1st.modules.gen.table.controller.GenTableController', 'save', '2019-05-16 11:23:37');
INSERT INTO `sys_biz_log` VALUES (45, '保存代码生成表', '{id:null,}', 'com.ins1st.modules.gen.table.controller.GenTableController', 'save', '2019-05-16 11:24:57');
INSERT INTO `sys_biz_log` VALUES (46, '保存代码生成表', '{id:null,}', 'com.ins1st.modules.gen.table.controller.GenTableController', 'save', '2019-05-16 11:25:29');
INSERT INTO `sys_biz_log` VALUES (47, '删除代码生成表', '{id:1,}', 'com.ins1st.modules.gen.table.controller.GenTableController', 'del', '2019-05-16 11:54:06');
INSERT INTO `sys_biz_log` VALUES (48, '保存代码生成表', '{id:null,}', 'com.ins1st.modules.gen.table.controller.GenTableController', 'save', '2019-05-16 11:55:24');
INSERT INTO `sys_biz_log` VALUES (49, '删除代码生成表', '{id:2,}', 'com.ins1st.modules.gen.table.controller.GenTableController', 'del', '2019-05-16 11:55:27');
INSERT INTO `sys_biz_log` VALUES (50, '保存代码生成表', '{id:null,}', 'com.ins1st.modules.gen.table.controller.GenTableController', 'save', '2019-05-16 11:58:59');
INSERT INTO `sys_biz_log` VALUES (51, '删除代码生成表', '{id:3,}', 'com.ins1st.modules.gen.table.controller.GenTableController', 'del', '2019-05-16 11:59:02');
INSERT INTO `sys_biz_log` VALUES (52, '保存代码生成表', '{id:null,}', 'com.ins1st.modules.gen.table.controller.GenTableController', 'save', '2019-05-16 13:08:31');
INSERT INTO `sys_biz_log` VALUES (53, '删除代码生成表', '{id:4,}', 'com.ins1st.modules.gen.table.controller.GenTableController', 'del', '2019-05-16 13:08:34');
INSERT INTO `sys_biz_log` VALUES (54, '保存代码生成表', '{id:null,}', 'com.ins1st.modules.gen.table.controller.GenTableController', 'save', '2019-05-16 13:21:20');
INSERT INTO `sys_biz_log` VALUES (55, '删除业务日志', '{id:39,}', 'com.ins1st.modules.sys.log.controller.SysBizLogController', 'del', '2019-05-16 13:52:20');
INSERT INTO `sys_biz_log` VALUES (56, '保存字典表', '{id:null,}', 'com.ins1st.modules.sys.dict.controller.SysDictController', 'save', '2019-05-17 11:41:56');
INSERT INTO `sys_biz_log` VALUES (57, '保存字典表', '{id:null,}', 'com.ins1st.modules.sys.dict.controller.SysDictController', 'save', '2019-05-17 11:42:29');
INSERT INTO `sys_biz_log` VALUES (58, '保存字典表', '{id:null,}', 'com.ins1st.modules.sys.dict.controller.SysDictController', 'save', '2019-05-17 11:42:42');
INSERT INTO `sys_biz_log` VALUES (59, '保存字典表', '{id:null,}', 'com.ins1st.modules.sys.dict.controller.SysDictController', 'save', '2019-05-17 11:42:58');
INSERT INTO `sys_biz_log` VALUES (60, '更新字典表', '{id:74,}', 'com.ins1st.modules.sys.dict.controller.SysDictController', 'update', '2019-05-17 11:45:07');
INSERT INTO `sys_biz_log` VALUES (61, '保存代码生成表', '{id:null,}', 'com.ins1st.modules.gen.table.controller.GenTableController', 'save', '2020-02-21 22:56:57');
INSERT INTO `sys_biz_log` VALUES (62, '保存代码生成表', '{id:null,}', 'com.ins1st.modules.gen.table.controller.GenTableController', 'save', '2020-02-21 22:57:19');
INSERT INTO `sys_biz_log` VALUES (63, '保存代码生成表', '{id:null,}', 'com.ins1st.modules.gen.table.controller.GenTableController', 'save', '2020-02-21 22:57:57');
INSERT INTO `sys_biz_log` VALUES (64, '保存代码生成表', '{id:null,}', 'com.ins1st.modules.gen.table.controller.GenTableController', 'save', '2020-02-21 22:58:12');
INSERT INTO `sys_biz_log` VALUES (65, '保存代码生成表', '{id:null,}', 'com.ins1st.modules.gen.table.controller.GenTableController', 'save', '2020-02-21 22:58:30');
INSERT INTO `sys_biz_log` VALUES (66, '保存代码生成表', '{id:null,}', 'com.ins1st.modules.gen.table.controller.GenTableController', 'save', '2020-02-21 22:58:45');

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `num` int(11) NULL DEFAULT NULL COMMENT '排序',
  `pid` int(11) NULL DEFAULT NULL COMMENT '父级字典',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '值',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 76 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (69, 1, 0, '性别', 'sex');
INSERT INTO `sys_dict` VALUES (70, 1, 69, '男', '1');
INSERT INTO `sys_dict` VALUES (71, 2, 69, '女', '2');
INSERT INTO `sys_dict` VALUES (72, 1, 0, 'html元素', 'htmlType');
INSERT INTO `sys_dict` VALUES (73, 2, 72, '输入框', '1');
INSERT INTO `sys_dict` VALUES (74, 3, 72, '下拉选择框', '2');
INSERT INTO `sys_dict` VALUES (75, 4, 72, '时间框', '3');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `p_id` int(11) NULL DEFAULT NULL COMMENT '父级菜单',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单地址',
  `role` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '菜单权限',
  `is_menu` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是不是菜单',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标(只限一级菜单使用)',
  `sort` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单排序',
  `level` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单层级',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统菜单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 0, '系统管理', '#', '#', '0', '&#xe699;', '1', '1');
INSERT INTO `sys_menu` VALUES (2, 1, '用户管理', '/sys/sysUser/index', 'sys:user:index,sys:user:edit,sys:user:del', '1', '', '2', '2');
INSERT INTO `sys_menu` VALUES (3, 1, '菜单管理', '/sys/sysMenu/index', 'sys:menu:index', '1', NULL, '3', '2');
INSERT INTO `sys_menu` VALUES (4, 1, '角色管理', '/sys/sysRole/index', 'sys:role:index', '1', NULL, '4', '2');
INSERT INTO `sys_menu` VALUES (6, 1, '业务日志管理', '/sys/sysBizLog/index', 'sys:log:index,sys:log:del', '1', NULL, '5', '2');
INSERT INTO `sys_menu` VALUES (7, 0, '平台管理', '#', '#', '0', '&#xe696;', '2', '1');
INSERT INTO `sys_menu` VALUES (28, 0, '代码生成', '/table/genTable/index', 'gen:table:index,gen:table:add,gen:table:edit,gen:table:del', '1', '&#xe696;', '7', '1');
INSERT INTO `sys_menu` VALUES (29, 0, '数据源监控', '/druid/login.html', '#', '1', '&#xe6f4;', '8', '1');
INSERT INTO `sys_menu` VALUES (30, 0, '接口文档', '/swagger-ui.html', '#', '1', '&#xe70c;', '9', '1');
INSERT INTO `sys_menu` VALUES (31, 1, '字典管理', '/sys/sysDict/index', 'sys:dict:index,sys:dict:add,sys:dict:edit,sys:dict:del', '1', NULL, '10', '3');
INSERT INTO `sys_menu` VALUES (32, 2, '新增', '#', 'sys:user:add', '0', NULL, '1', '3');
INSERT INTO `sys_menu` VALUES (34, 7, '物品管理', '/goods/goods/index', 'shop:goods:index,shop:goods:add,shop:goods:edit,shop:goods:del', '1', NULL, '1', '2');
INSERT INTO `sys_menu` VALUES (35, 7, '用户管理', '/users/users/index', 'shop:users:add,shop:users:edit,shop:users:del,shop:users:index', '1', NULL, '2', '2');
INSERT INTO `sys_menu` VALUES (36, 7, '反馈管理', '/feedback/feedback/index', 'shop:feedback:index,shop:feedback:add,shop:feedback:edit,shop:feedback:del', '1', NULL, '3', '2');
INSERT INTO `sys_menu` VALUES (37, 7, '聊天消息管理', '/message/message/index', 'shop:message:index,shop:message:add,shop:message:edit,shop:message:del', '1', NULL, '4', '2');
INSERT INTO `sys_menu` VALUES (38, 7, '留言管理', '/leavemsg/leaveMsg/index', 'shop:leavemsg:index,shop:leavemsg:add,shop:leavemsg:edit,shop:leavemsg:del', '1', NULL, '5', '2');

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `type` int(11) NULL DEFAULT NULL COMMENT '类型',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内容',
  `createtime` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `creater` int(11) NULL DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '通知表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_notice
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色状态',
  `keyword` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色标识',
  `sort` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', '1', 'admin1', '1');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` int(11) NULL DEFAULT NULL,
  `menu_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 128 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色和菜单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (114, 1, 1);
INSERT INTO `sys_role_menu` VALUES (115, 1, 2);
INSERT INTO `sys_role_menu` VALUES (116, 1, 32);
INSERT INTO `sys_role_menu` VALUES (117, 1, 3);
INSERT INTO `sys_role_menu` VALUES (118, 1, 4);
INSERT INTO `sys_role_menu` VALUES (119, 1, 6);
INSERT INTO `sys_role_menu` VALUES (120, 1, 31);
INSERT INTO `sys_role_menu` VALUES (121, 1, 7);
INSERT INTO `sys_role_menu` VALUES (122, 1, 34);
INSERT INTO `sys_role_menu` VALUES (123, 1, 35);
INSERT INTO `sys_role_menu` VALUES (124, 1, 36);
INSERT INTO `sys_role_menu` VALUES (125, 1, 37);
INSERT INTO `sys_role_menu` VALUES (126, 1, 38);
INSERT INTO `sys_role_menu` VALUES (127, 1, 29);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名称',
  `user_nick` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `user_pwd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户密码',
  `user_email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  `user_mobile` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户电话',
  `user_sex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户性别',
  `role_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户角色',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户状态（1正常2冻结）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (4, 'admin', '超级管理员', 'b4674fb71b5b62030be59814f75674c7814a89a424739272', '499719083@qq.com', '130000000', '1', '1,7', '1');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11) NULL DEFAULT NULL,
  `role_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 66 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户和角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (58, 4, 1);
INSERT INTO `sys_user_role` VALUES (59, 4, 7);
INSERT INTO `sys_user_role` VALUES (60, 5, 1);
INSERT INTO `sys_user_role` VALUES (65, 6, 1);

-- ----------------------------
-- Table structure for test_gen
-- ----------------------------
DROP TABLE IF EXISTS `test_gen`;
CREATE TABLE `test_gen`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `column_one` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字段1',
  `column_two` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字段2',
  `column_three` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字段3',
  `column_four` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字段4',
  `column_five` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字段5',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '测试代码生成表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of test_gen
-- ----------------------------

-- ----------------------------
-- Table structure for user_preferences
-- ----------------------------
DROP TABLE IF EXISTS `user_preferences`;
CREATE TABLE `user_preferences`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `category_id` int(11) NULL DEFAULT NULL COMMENT '喜欢的分类',
  `price_min` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格下限',
  `price_max` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格上限',
  `preferred_location` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '偏好位置',
  `last_updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_user_preferences_user_id`(`user_id`) USING BTREE,
  CONSTRAINT `fk_user_preferences_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_preferences
-- ----------------------------

-- ----------------------------
-- Table structure for user_views
-- ----------------------------
DROP TABLE IF EXISTS `user_views`;
CREATE TABLE `user_views`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `good_id` int(11) NOT NULL COMMENT '商品ID',
  `view_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '浏览时间',
  `view_duration` int(11) NULL DEFAULT 0 COMMENT '浏览时长(秒)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_views_user_id`(`user_id`) USING BTREE,
  INDEX `idx_user_views_good_id`(`good_id`) USING BTREE,
  CONSTRAINT `fk_user_views_good` FOREIGN KEY (`good_id`) REFERENCES `goods` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_user_views_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_views
-- ----------------------------

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` int(6) NOT NULL AUTO_INCREMENT,
  `username` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `nickname` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `head` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `mobilephone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '校区/宿舍楼',
  `sex` int(1) NOT NULL COMMENT '1男 0女',
  `college` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `openid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100009 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (100000, '10000001', '系统', 'http://localhost/Zhuanzhuan/img/header/kefu.png', '10086', '荷园', 0, '郑州大学', '');
INSERT INTO `users` VALUES (100001, '20163594', '小小小', 'https://thirdwx.qlogo.cn/mmopen/vi_32/1o8gB5DOdHfP8SLiaBNcWxIYG88XrcgSYAib37M8ouI2wKiaOJelbg7aiaiaWEmrSrbbTZXiaWU8szQyV8MTHeMtQqDQ/0', '15127045633', '荷园', 1, '郑州大学', 'oIGUY0YjG1HsX2JC5l4GDR5Zype4');
INSERT INTO `users` VALUES (100002, '20163620', '二哈的姑奶奶', 'https://wx.qlogo.cn/mmopen/vi_32/vvnlzsUfIB3UtibvghqIcPFnicRBCuaEbBZMnfFvan2zByKhqssVSklesXn9UiaDf7D2jMQEqq0HhnrUTCFnUudbw/132', NULL, '荷园', 0, '郑州大学', 'oIGUY0SCNKe1nE4pnuUjBJ1gEw9Q');
INSERT INTO `users` VALUES (100003, '20', 'a朵', 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIF5gXA665dY6iaPC5iaU3frkfBrktOBE2BpZ2U4encBtxsE9n42xVMDdWhbSUjRsPicrpKR75120YCw/132', NULL, '荷园', 0, '郑州大学', 'oIGUY0ZsUzUKDnXxF4ktp18LRMzs');
INSERT INTO `users` VALUES (100004, 'xixi', '嘻', 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJlJAgxLa4we4GBnadDSO5iaBk00avBiceDLGCZYmbMKQso83BWic0vKScGIkBfUGZNpvOG8G0fzQFWA/132', NULL, '荷园', 0, '郑州大学', 'oIGUY0aSwO_k5zqjubn-QpfkZ8gs');
INSERT INTO `users` VALUES (100005, '20163694', '娓娓动听', 'https://wx.qlogo.cn/mmopen/vi_32/dZj08ckjV0LwH84wIwwEgsicgL9mxmicfy99wE4CKqJ0iccrcX38dpZEh6sSle374zQIdf8GObLReoMWDMwl813oA/0', NULL, '荷园', 0, '郑州大学', 'oIGUY0ce3y-3_7Bu7hbcQl4_pXcc');
INSERT INTO `users` VALUES (100006, 'haha', '哈', 'https://wx.qlogo.cn/mmopen/vi_32/54GloASLv1HsMicITGJZ4PtUVYBgyicRC1Gpicntkd9XibKBqLlUicttXkd7ThNBUXIxia4rPWBvtxR5tib9Dfasic2hYw/132', NULL, '荷园', 1, '郑州大学', 'ozrQJ4x9UQbLFoJRvxOODz_SnJCs');
INSERT INTO `users` VALUES (100007, '100007', '贝砣', 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83epdoquN0dWqibvFdoQx3aeHqtUhQkiaschnfnqwa37gWM8rXkiamjOCHQ8lJ2gQvfMZLXTWK5ibl36o2w/132', NULL, '荷园', 0, '郑州大学', 'o6P5O5Q_bxbPq9Cmncd2ywJdI4oI');
INSERT INTO `users` VALUES (100008, '12365555', '微信用户', 'https://thirdwx.qlogo.cn/mmopen/vi_32/POgEwh4mIHO4nibH0KlMECNjjGxQUq24ZEaGT4poC6icRiccVGKSyXwibcPq4BWmiaIGuG1icwxaQX6grC9VemZoJ8rg/132', NULL, '荷园', 0, '郑州大学', 'odEBd7CEc7YZRp_rDBwgHUH_1A0c');

SET FOREIGN_KEY_CHECKS = 1;
