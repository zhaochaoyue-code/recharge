-- 拿新的一批手机号去处理
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'yk_0805',mobile,2000 FROM mobile_original_import_excel WHERE id>=1 AND id<=22;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'iqi_0805',mobile,2000 FROM mobile_original_import_excel WHERE id>=1 AND id<=14;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'tx_0805',mobile,2000 FROM mobile_original_import_excel WHERE id>=15 AND id<=15;



-- 拆单后
