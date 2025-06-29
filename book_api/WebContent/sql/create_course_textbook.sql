-- 创建课程教材对应关系表
CREATE TABLE IF NOT EXISTS `course_textbook` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `course_name` VARCHAR(255) NOT NULL COMMENT '课程名称',
  `textbook_name` VARCHAR(255) NOT NULL COMMENT '教材名称',
  `isbn` VARCHAR(50) DEFAULT NULL COMMENT 'ISBN号',
  `publisher` VARCHAR(255) DEFAULT NULL COMMENT '出版社',
  `author` VARCHAR(255) DEFAULT NULL COMMENT '作者',
  `semester` INT(11) NOT NULL DEFAULT 0 COMMENT '学期 (0-第一学期，1-第二学期)',
  `major` VARCHAR(255) DEFAULT NULL COMMENT '专业',
  `recommend_level` INT(11) NOT NULL DEFAULT 3 COMMENT '推荐等级 (1-5，5为最高)',
  `is_required` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '是否必修教材 (0-否，1-是)',
  PRIMARY KEY (`id`),
  INDEX `idx_course_name` (`course_name`),
  INDEX `idx_textbook_name` (`textbook_name`),
  INDEX `idx_semester` (`semester`),
  INDEX `idx_major` (`major`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程与教材对应关系表';

-- 插入一些初始数据
INSERT INTO `course_textbook` (`course_name`, `textbook_name`, `isbn`, `publisher`, `author`, `semester`, `major`, `recommend_level`, `is_required`) VALUES
('高等数学(上)', '高等数学(第七版)', '9787040429664', '高等教育出版社', '同济大学数学系', 0, '理工类', 5, 1),
('高等数学(下)', '高等数学(第七版)', '9787040429671', '高等教育出版社', '同济大学数学系', 1, '理工类', 5, 1),
('大学物理(上)', '大学物理学(第三版)', '9787302516675', '清华大学出版社', '赵近芳', 0, '理工类', 5, 1),
('大学物理(下)', '大学物理学(第三版)', '9787302516682', '清华大学出版社', '赵近芳', 1, '理工类', 5, 1),
('大学英语(1)', '新视野大学英语(第三版)', '9787513556354', '外语教学与研究出版社', '郑树棠', 0, '通用', 4, 1),
('大学英语(2)', '新视野大学英语(第三版)', '9787513556361', '外语教学与研究出版社', '郑树棠', 1, '通用', 4, 1),
('程序设计基础', 'C程序设计(第四版)', '9787302481447', '清华大学出版社', '谭浩强', 0, '计算机', 5, 1),
('数据结构', '数据结构(C语言版)', '9787302330646', '清华大学出版社', '严蔚敏', 1, '计算机', 5, 1),
('操作系统', '计算机操作系统(第四版)', '9787040408881', '高等教育出版社', '汤小丹', 0, '计算机', 4, 1),
('计算机网络', '计算机网络(第七版)', '9787121201677', '机械工业出版社', '谢希仁', 1, '计算机', 4, 1),
('微观经济学', '西方经济学(微观部分)', '9787301268902', '北京大学出版社', '高鸿业', 0, '经济管理', 5, 1),
('宏观经济学', '西方经济学(宏观部分)', '9787301268919', '北京大学出版社', '高鸿业', 1, '经济管理', 5, 1),
('会计学原理', '会计学原理(第三版)', '9787302413332', '清华大学出版社', '陈国辉', 0, '经济管理', 4, 1),
('市场营销学', '市场营销学(第五版)', '9787301273456', '北京大学出版社', '吴健安', 1, '经济管理', 4, 1),
('有机化学', '有机化学(第四版)', '9787122299444', '化学工业出版社', '徐寿昌', 0, '化学', 5, 1),
('无机化学', '无机化学(第四版)', '9787040307368', '高等教育出版社', '武汉大学', 1, '化学', 5, 1),
('分析化学', '分析化学(第六版)', '9787040283945', '高等教育出版社', '华东理工大学', 0, '化学', 4, 1),
('物理化学', '物理化学(第五版)', '9787040168358', '高等教育出版社', '天津大学', 1, '化学', 4, 1);


INSERT INTO `course_textbook` (`course_name`, `textbook_name`, `isbn`, `publisher`, `author`, `semester`, `major`, `recommend_level`, `is_required`) VALUES
('人工智能', '人工智能(柴玉美、张坤丽主编)', '9787111384014', '机械工业出版社', '郑州大学信息工程学院', 1, '理工类', 5, 1)