-- 重新设计goods表结构，使其更适合通用商品而非仅限书本
-- 这个脚本会添加新字段并重新组织现有字段

-- 1. 添加新的通用商品字段
ALTER TABLE `goods` 
ADD COLUMN `original_price` decimal(10, 2) NULL COMMENT '商品原价' AFTER `price`,
ADD COLUMN `condition_status` varchar(50) NULL COMMENT '商品成色：全新/轻微使用痕迹/明显使用痕迹' AFTER `description`,
ADD COLUMN `function_status` varchar(100) NULL COMMENT '功能状态：功能完好/有小问题但不影响使用/维修过可正常使用/无法正常使用' AFTER `condition_status`,
ADD COLUMN `purchase_time` date NULL COMMENT '购买时间' AFTER `function_status`,
ADD COLUMN `trade_method` varchar(100) NULL COMMENT '交易方式：自提柜/当面交易/送至宿舍楼下' AFTER `purchase_time`,
ADD COLUMN `brand` varchar(100) NULL COMMENT '品牌' AFTER `trade_method`,
ADD COLUMN `model` varchar(100) NULL COMMENT '型号' AFTER `brand`,
ADD COLUMN `specifications` text NULL COMMENT '规格参数（JSON格式存储）' AFTER `model`,
ADD COLUMN `tags` varchar(500) NULL COMMENT '商品标签，用逗号分隔' AFTER `specifications`,
ADD COLUMN `is_negotiable` tinyint(1) DEFAULT 0 COMMENT '是否可议价：0-不可议价，1-可议价' AFTER `tags`,
ADD COLUMN `created_at` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' AFTER `time`,
ADD COLUMN `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间' AFTER `created_at`;

-- 2. 修改现有字段的注释，使其更通用
ALTER TABLE `goods` 
MODIFY COLUMN `goodname` varchar(255) NOT NULL COMMENT '商品名称',
MODIFY COLUMN `description` varchar(1000) NOT NULL COMMENT '商品描述',
MODIFY COLUMN `image` varchar(1000) NOT NULL COMMENT '商品图片，多个图片用分号分隔',
MODIFY COLUMN `isbn` varchar(20) NULL COMMENT 'ISBN号（仅图书类商品）',
MODIFY COLUMN `chubanshe` varchar(100) NULL COMMENT '出版社（仅图书类商品）',
MODIFY COLUMN `author` varchar(100) NULL COMMENT '作者（仅图书类商品）',
MODIFY COLUMN `chubantime` varchar(20) NULL COMMENT '出版时间（仅图书类商品）';

-- 3. 创建商品属性扩展表（用于存储不同类型商品的特殊属性）
CREATE TABLE IF NOT EXISTS `good_attributes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `good_id` int(11) NOT NULL COMMENT '商品ID',
  `attribute_name` varchar(100) NOT NULL COMMENT '属性名称',
  `attribute_value` varchar(500) NOT NULL COMMENT '属性值',
  `created_at` timestamp DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_good_id` (`good_id`),
  KEY `idx_attribute_name` (`attribute_name`),
  FOREIGN KEY (`good_id`) REFERENCES `goods` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品属性扩展表';

-- 4. 创建商品标签表
CREATE TABLE IF NOT EXISTS `good_tags` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(50) NOT NULL COMMENT '标签名称',
  `tag_color` varchar(20) DEFAULT '#1890ff' COMMENT '标签颜色',
  `usage_count` int(11) DEFAULT 0 COMMENT '使用次数',
  `created_at` timestamp DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tag_name` (`tag_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品标签表';

-- 5. 插入一些预设的标签
INSERT INTO `good_tags` (`tag_name`, `tag_color`) VALUES
('急售', '#ff4d4f'),
('包邮', '#52c41a'),
('可议价', '#faad14'),
('九成新', '#1890ff'),
('全新', '#722ed1'),
('限时优惠', '#eb2f96'),
('学习用品', '#13c2c2'),
('生活用品', '#fa8c16'),
('电子产品', '#2f54eb'),
('服装配饰', '#f759ab');

-- 6. 创建商品浏览历史表（如果不存在）
CREATE TABLE IF NOT EXISTS `good_views` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `good_id` int(11) NOT NULL COMMENT '商品ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `view_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '浏览时间',
  `view_duration` int(11) DEFAULT 0 COMMENT '浏览时长（秒）',
  PRIMARY KEY (`id`),
  KEY `idx_good_id` (`good_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_view_time` (`view_time`),
  FOREIGN KEY (`good_id`) REFERENCES `goods` (`id`) ON DELETE CASCADE,
  FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品浏览历史表';

-- 7. 更新现有数据，将一些字段的数据迁移到新字段
-- 注意：这里需要根据实际数据情况进行调整

-- 将chubanshe字段中的成色信息迁移到condition_status字段
UPDATE `goods` SET 
  `condition_status` = CASE 
    WHEN `chubanshe` IN ('全新', '轻微使用痕迹', '明显使用痕迹') THEN `chubanshe`
    ELSE NULL
  END
WHERE `chubanshe` IN ('全新', '轻微使用痕迹', '明显使用痕迹');

-- 将author字段中的功能状态信息迁移到function_status字段
UPDATE `goods` SET 
  `function_status` = CASE 
    WHEN `author` IN ('功能完好', '有小问题但不影响使用', '维修过可正常使用', '无法正常使用') THEN `author`
    ELSE NULL
  END
WHERE `author` IN ('功能完好', '有小问题但不影响使用', '维修过可正常使用', '无法正常使用');

-- 8. 创建索引以提高查询性能
CREATE INDEX `idx_goods_condition` ON `goods` (`condition_status`);
CREATE INDEX `idx_goods_function` ON `goods` (`function_status`);
CREATE INDEX `idx_goods_brand` ON `goods` (`brand`);
CREATE INDEX `idx_goods_trade_method` ON `goods` (`trade_method`);
CREATE INDEX `idx_goods_created_at` ON `goods` (`created_at`);
CREATE INDEX `idx_goods_price_range` ON `goods` (`price`, `original_price`);
