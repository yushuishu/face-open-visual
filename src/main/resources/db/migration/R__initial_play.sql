-- 生成3000-50000的随机数
UPDATE td_course SET initial_play = FLOOR ( random( ) * 47000+3000 ) WHERE initial_play IS NULL OR initial_play = 0;
-- 注册类型，默认填充 1：普通注册
UPDATE td_face_in SET register_type=1 WHERE register_type IS NULL;