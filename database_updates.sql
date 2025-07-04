-- 添加collect_count字段到goods表
-- 这个字段用于记录商品的收藏次数，避免每次都实时查询collect表

-- 1. 添加collect_count字段
ALTER TABLE `goods` ADD COLUMN `collect_count` int(11) NOT NULL DEFAULT 0 COMMENT '收藏次数' AFTER `inquiry_count`;

-- 2. 初始化现有商品的collect_count值
-- 根据collect表中的实际数据来设置每个商品的收藏次数
UPDATE `goods` g
SET `collect_count` = (
    SELECT COUNT(*)
    FROM `collect` c
    WHERE c.goodId = g.id
)
WHERE EXISTS (  -- 只更新有收藏记录的商品
    SELECT 1
    FROM `collect` c
    WHERE c.goodId = g.id
);

-- 3. 验证更新结果的查询语句（可选执行）
SELECT id, goodname, collect_count,
       (SELECT COUNT(*) FROM collect WHERE goodId = goods.id) as actual_collect_count
FROM goods
WHERE collect_count > 0
ORDER BY collect_count DESC
LIMIT 10;

-- 4. 创建索引以提高查询性能（可选）
CREATE INDEX idx_goods_collect_count ON goods(collect_count);

-- 5. 验证数据一致性的查询
-- 检查collect_count与实际collect表数据是否一致
SELECT
    g.id,
    g.goodname,
    g.collect_count as stored_count,
    COUNT(c.userId) as actual_count,
    (g.collect_count - COUNT(c.userId)) as difference
FROM goods g
         LEFT JOIN collect c ON g.id = c.goodId
GROUP BY g.id, g.goodname, g.collect_count
HAVING difference != 0
ORDER BY ABS(difference) DESC;

-- 6. 修复不一致的数据（如果上面的查询发现了问题）
-- UPDATE goods g
-- SET collect_count = (
--     SELECT COUNT(*)
--     FROM collect c
--     WHERE c.goodId = g.id
-- );

-- 7. 创建触发器确保数据一致性（可选，但推荐）
-- 当collect表有变化时自动更新goods表的collect_count

DELIMITER $$

CREATE TRIGGER tr_collect_insert
    AFTER INSERT ON collect
    FOR EACH ROW
BEGIN
    UPDATE goods
    SET collect_count = collect_count + 1
    WHERE id = NEW.goodId;
END$$

CREATE TRIGGER tr_collect_delete
    AFTER DELETE ON collect
    FOR EACH ROW
BEGIN
    UPDATE goods
    SET collect_count = collect_count - 1
    WHERE id = OLD.goodId;
END$$

DELIMITER ;
