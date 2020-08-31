-- 拿新的一批手机号去处理
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'youku_activity',mobile,1500 FROM mobile_original_import_excel WHERE id>=1 AND id<=2;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'youku_activity',mobile,1500 FROM mobile_original_import_excel WHERE id>=3 AND id<=102;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'youku_activity',mobile,1500 FROM mobile_original_import_excel WHERE id>=103 AND id<=5102;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'youku_activity',mobile,1500 FROM mobile_original_import_excel WHERE id>=5103 AND id<=10102;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'youku_activity',mobile,1500 FROM mobile_original_import_excel WHERE id>=10103 AND id<=20977;



-- 拆单后处理
INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'youku_activity',mobile, 1000 FROM mobile_original_import_excel WHERE id>=1 AND id<=2;
INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'youku_activity',mobile, 500 FROM mobile_original_import_excel WHERE id>=1 AND id<=2;

INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'youku_activity',mobile, 1000 FROM mobile_original_import_excel WHERE id>=3 AND id<=476;
INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'youku_activity',mobile, 500 FROM mobile_original_import_excel WHERE id>=3 AND id<=476;

INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'youku_activity',mobile, 1000 FROM mobile_original_import_excel WHERE id>=512 AND id<=519;
INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'youku_activity',mobile, 500 FROM mobile_original_import_excel WHERE id>=512 AND id<=519;

