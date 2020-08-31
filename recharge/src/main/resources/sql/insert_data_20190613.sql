-- 拿新的一批手机号去处理
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'youku_activity',mobile,1500 FROM mobile_original_import_excel WHERE id>=1 AND id<=17303;


-- 拆单后
INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'youku_activity',mobile, 1000 FROM mobile_original_import_excel WHERE id>=1 AND id<=300;
INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'youku_activity',mobile, 500 FROM mobile_original_import_excel WHERE id>=1 AND id<=300;


INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'youku_activity',mobile, 1000 FROM mobile_original_import_excel WHERE id>=301 AND id<=301;
INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'youku_activity',mobile, 500 FROM mobile_original_import_excel WHERE id>=301 AND id<=301;


-- 由于失败的太多，重新拆单充值。
INSERT INTO mobile SELECT * FROM mobile_bak_20190617_602_10_5 WHERE process_status=4;
-- 失败的重发。
INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'youku_activity',mobile,500 FROM mobile_bak_20190617_602_10_5 WHERE process_status=12 AND money=500;
-- 失败的重发。
INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'youku_activity',mobile,500 FROM mobile_bak_20190617_602_10_5 WHERE process_status=12 AND money=1000;
INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'youku_activity',mobile,500 FROM mobile_bak_20190617_602_10_5 WHERE process_status=12 AND money=1000;


