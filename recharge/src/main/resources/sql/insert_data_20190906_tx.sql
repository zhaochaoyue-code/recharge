-- 拿新的一批手机号去处理
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'tx_0805',mobile,2000 FROM mobile_original_import_excel_tx WHERE id>=1 AND id<=100;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'tx_0805',mobile,2000 FROM mobile_original_import_excel_tx WHERE id>=101 AND id<=1100;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'tx_0805',mobile,2000 FROM mobile_original_import_excel_tx WHERE id>=1101 AND id<=4101;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'tx_0805',mobile,2000 FROM mobile_original_import_excel_tx WHERE id>=4102 AND id<=9101;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'tx_0805',mobile,2000 FROM mobile_original_import_excel_tx WHERE id>=9102 AND id<=19101;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'tx_0805',mobile,2000 FROM mobile_original_import_excel_tx WHERE id>=19102 AND id<=29101;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'tx_0805',mobile,2000 FROM mobile_original_import_excel_tx WHERE id>=29102 AND id<=40000;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'tx_0805',mobile,2000 FROM mobile_original_import_excel_tx WHERE id>=40001 AND id<=54946;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'tx_0805',mobile,2000 FROM mobile_original_import_excel_tx WHERE id>=54947 AND id<=55148;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'tx_0805',mobile,2000 FROM mobile_original_import_excel_tx WHERE id>=55149 AND id<=55218;


-- 拆单后
