-- 发送测试短信。
INSERT INTO mobile  (uid, activity_id, mobile, money)
SELECT REPLACE(UUID(), '-', ''), 'yk_0805', '18601011294', 1

-- 拿新的一批手机号去处理
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'yk_0805',mobile,2000 FROM mobile_original_import_excel_yk_28067 WHERE id>=1 AND id<=5000;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'yk_0805',mobile,2000 FROM mobile_original_import_excel_yk_28067 WHERE id>=5001 AND id<=10000;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'yk_0805',mobile,2000 FROM mobile_original_import_excel_yk_28067 WHERE id>=10001 AND id<=15000;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'yk_0805',mobile,2000 FROM mobile_original_import_excel_yk_28067 WHERE id>=15001 AND id<=20000;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'yk_0805',mobile,2000 FROM mobile_original_import_excel_yk_28067 WHERE id>=20001 AND id<=28067;



-- 拆单后
