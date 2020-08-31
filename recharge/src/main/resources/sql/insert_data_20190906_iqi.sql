-- 拿新的一批手机号去处理
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'iqi_0805',mobile,2000 FROM mobile_original_import_excel_iqi WHERE id>=1 AND id<=10000;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'iqi_0805',mobile,2000 FROM mobile_original_import_excel_iqi WHERE id>=10001 AND id<=24339;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'iqi_0805',mobile,2000 FROM mobile_original_import_excel_iqi WHERE id>=24340 AND id<=24446;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'iqi_0805',mobile,2000 FROM mobile_original_import_excel_iqi WHERE id>=24447 AND id<=24500;


-- 拆单后
