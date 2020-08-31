-- 拿新的一批手机号去处理
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'mangguo_activity',mobile,1000 FROM mobile_original_import_excel WHERE id>=1 AND id<=1310;


-- 拆单后
