-- 拿新的一批手机号去处理
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'tx_0805',mobile,2000 FROM mobile_original_import_excel_tx_49731 WHERE id>=1 AND id<=100;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'tx_0805',mobile,2000 FROM mobile_original_import_excel_tx_49731 WHERE id>=101 AND id<=1100;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'tx_0805',mobile,2000 FROM mobile_original_import_excel_tx_49731 WHERE id>=1101 AND id<=11100;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'tx_0805',mobile,2000 FROM mobile_original_import_excel_tx_49731 WHERE id>=11101 AND id<=31100;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'tx_0805',mobile,2000 FROM mobile_original_import_excel_tx_49731 WHERE id>=31101 AND id<=49640;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'tx_0805',mobile,2000 FROM mobile_original_import_excel_tx_49731 WHERE id>=49641 AND id<=49731;
INSERT INTO mobile(id,uid,activity_id,mobile,money) SELECT 1008034,REPLACE(UUID(), '-', ''),'tx_0805','17638923383',2000 -- 后面又加了一个。

-- 拆单后
