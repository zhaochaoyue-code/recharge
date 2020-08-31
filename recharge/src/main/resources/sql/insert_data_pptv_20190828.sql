-- 拿新的一批手机号去处理
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'pptv_activity',mobile,1100 FROM mobile_original_import_excel WHERE id>=1 AND id<=300;

-- 拆单后
INSERT INTO mobile (uid,activity_id,mobile, money) 
	SELECT REPLACE(UUID(), '-', ''),'pptv_activity',mobile, 500 FROM mobile_original_import_excel;
INSERT INTO mobile (uid,activity_id,mobile, money) 
	SELECT REPLACE(UUID(), '-', ''),'pptv_activity',mobile, 500 FROM mobile_original_import_excel;
INSERT INTO mobile (uid,activity_id,mobile, money) 
	SELECT REPLACE(UUID(), '-', ''),'pptv_activity',mobile, 100 FROM mobile_original_import_excel;

-- 备份拆单后的数据
CREATE TABLE mobile_original_bak_20190828_22_5_5_1 LIKE mobile_original_import_excel;
INSERT INTO mobile_original_bak_20190828_22_5_5_1 SELECT * FROM mobile_original_import_excel;
TRUNCATE TABLE mobile_original_import_excel;

CREATE TABLE mobile_bak_20190828_66_5_5_1 LIKE mobile;
INSERT INTO mobile_bak_20190828_66_5_5_1 SELECT * FROM mobile;
DELETE FROM mobile;

CREATE TABLE mobile_sms_bak_20190828_18 LIKE mobile_sms;
INSERT INTO mobile_sms_bak_20190828_18 SELECT * FROM mobile_sms;
TRUNCATE TABLE mobile_sms;