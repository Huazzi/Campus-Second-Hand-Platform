-- 修复浏览次数显示问题的SQL脚本
-- 这个脚本将为商品添加一些测试浏览次数，以便验证推荐系统是否正常工作

-- 1. 检查当前view_count的情况
SELECT 
    COUNT(*) as total_goods,
    COUNT(CASE WHEN view_count > 0 THEN 1 END) as goods_with_views,
    MAX(view_count) as max_views,
    AVG(view_count) as avg_views
FROM goods 
WHERE status = 0 AND num > 0;

-- 2. 显示当前浏览次数最多的前10个商品
SELECT id, goodname, view_count, collect_count, status, num
FROM goods 
WHERE status = 0 AND num > 0 
ORDER BY view_count DESC 
LIMIT 10;

-- 3. 为测试目的，给一些商品添加随机的浏览次数
-- 注意：这只是为了测试，生产环境中浏览次数应该通过用户实际访问来增加

-- 给前20个有效商品添加随机浏览次数（10-100之间）
UPDATE goods 
SET view_count = FLOOR(10 + RAND() * 90)
WHERE id IN (
    SELECT * FROM (
        SELECT id 
        FROM goods 
        WHERE status = 0 AND num > 0 
        ORDER BY id 
        LIMIT 20
    ) as temp
);

-- 4. 给一些商品添加更高的浏览次数，模拟热门商品
UPDATE goods 
SET view_count = FLOOR(100 + RAND() * 400)
WHERE id IN (
    SELECT * FROM (
        SELECT id 
        FROM goods 
        WHERE status = 0 AND num > 0 
        ORDER BY RAND() 
        LIMIT 5
    ) as temp
);

-- 5. 验证更新结果
SELECT 
    COUNT(*) as total_goods,
    COUNT(CASE WHEN view_count > 0 THEN 1 END) as goods_with_views,
    MAX(view_count) as max_views,
    AVG(view_count) as avg_views
FROM goods 
WHERE status = 0 AND num > 0;

-- 6. 显示更新后浏览次数最多的前10个商品
SELECT id, goodname, view_count, collect_count, status, num
FROM goods 
WHERE status = 0 AND num > 0 
ORDER BY view_count DESC 
LIMIT 10;

-- 7. 如果需要重置所有浏览次数为0（谨慎使用）
-- UPDATE goods SET view_count = 0;
