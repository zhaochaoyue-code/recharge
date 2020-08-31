-- 拿新的一批手机号去处理
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'pptv_activity',mobile,1100 FROM mobile_original_import_excel WHERE id>=1 AND id<=167;

-- 拆单后
INSERT INTO mobile (uid,activity_id,mobile, money) 
	SELECT REPLACE(UUID(), '-', ''),'pptv_activity',mobile, 500 FROM mobile_original_import_excel;
INSERT INTO mobile (uid,activity_id,mobile, money) 
	SELECT REPLACE(UUID(), '-', ''),'pptv_activity',mobile, 500 FROM mobile_original_import_excel;
INSERT INTO mobile (uid,activity_id,mobile, money) 
	SELECT REPLACE(UUID(), '-', ''),'pptv_activity',mobile, 100 FROM mobile_original_import_excel;

-- 备份拆单后的数据
