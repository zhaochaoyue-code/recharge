-- 拿新的一批手机号去处理
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'youku_activity',mobile,1500 FROM mobile_original_import_excel WHERE id>=1 AND id<=100;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'youku_activity',mobile,1500 FROM mobile_original_import_excel WHERE id>=101 AND id<=2488;
