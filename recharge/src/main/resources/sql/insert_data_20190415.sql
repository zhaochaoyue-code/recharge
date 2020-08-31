-- 拿新的一批手机号去处理
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'youku_activity','17623300895',1500;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'youku_activity','17607092680',1500;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'youku_activity','18623242542',1500;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'youku_activity','16603005020',1500;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'youku_activity','18688424073',1500;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'youku_activity','13107696073',1500;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'youku_activity','13013063785',1500;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'youku_activity','18631544004',1500;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'youku_activity','13125177321',1500;

INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'youku_activity',mobile,1500 FROM mobile_original_import_excel WHERE id>=1 AND id<=600;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'youku_activity',mobile,1500 FROM mobile_original_import_excel WHERE id>=601 AND id<=10600;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'youku_activity',mobile,1500 FROM mobile_original_import_excel WHERE id>=10601 AND id<=22525;

-- 拆单后
INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'youku_activity',mobile, 1000 FROM mobile_original_import_excel WHERE id>=1 AND id<=2;
INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'youku_activity',mobile, 500 FROM mobile_original_import_excel WHERE id>=1 AND id<=2;

INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'youku_activity',mobile, 1000 FROM mobile_original_import_excel WHERE id>=3 AND id<=401;
INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'youku_activity',mobile, 500 FROM mobile_original_import_excel WHERE id>=3 AND id<=401;