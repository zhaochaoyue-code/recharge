-- 拿新的一批手机号去处理
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'yk_0805',mobile,2000 FROM mobile_original_import_excel_yk WHERE id>=1 AND id<=10000;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'yk_0805',mobile,2000 FROM mobile_original_import_excel_yk WHERE id>=10001 AND id<=20000;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'yk_0805',mobile,2000 FROM mobile_original_import_excel_yk WHERE id>=20001 AND id<=28465;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'yk_0805',mobile,2000 FROM mobile_original_import_excel_yk WHERE id>=28466 AND id<=28537;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'yk_0805',mobile,2000 FROM mobile_original_import_excel_yk WHERE id>=28538 AND id<=28568;



-- 拆单后
