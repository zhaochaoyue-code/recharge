-- 拆单后发送 
INSERT INTO mobile (uid,activity_id,mobile, money) 
	SELECT REPLACE(UUID(), '-', ''),'tx_0805',mobile, 1000 FROM mobile_0805_tx WHERE process_status=12;
INSERT INTO mobile (uid,activity_id,mobile, money) 
	SELECT REPLACE(UUID(), '-', ''),'tx_0805',mobile, 1000 FROM mobile_0805_tx WHERE process_status=12;
	
INSERT INTO mobile (uid,activity_id,mobile, money) 
	SELECT REPLACE(UUID(), '-', ''),'yk_0805',mobile, 1000 FROM mobile_0805_yk WHERE process_status=12;
INSERT INTO mobile (uid,activity_id,mobile, money) 
	SELECT REPLACE(UUID(), '-', ''),'yk_0805',mobile, 1000 FROM mobile_0805_yk WHERE process_status=12;
	
INSERT INTO mobile (uid,activity_id,mobile, money) 
	SELECT REPLACE(UUID(), '-', ''),'iqi_0805',mobile, 1000 FROM mobile_0805_iqi WHERE process_status=12;
INSERT INTO mobile (uid,activity_id,mobile, money) 
	SELECT REPLACE(UUID(), '-', ''),'iqi_0805',mobile, 1000 FROM mobile_0805_iqi WHERE process_status=12;

	
INSERT INTO mobile (uid,activity_id,mobile, money) 
	SELECT REPLACE(UUID(), '-', ''),'pptv_activity',mobile, 500 FROM mobile_bak_20190808_269 WHERE process_status=12;
INSERT INTO mobile (uid,activity_id,mobile, money) 
	SELECT REPLACE(UUID(), '-', ''),'pptv_activity',mobile, 500 FROM mobile_bak_20190808_269 WHERE process_status=12;
INSERT INTO mobile (uid,activity_id,mobile, money) 
	SELECT REPLACE(UUID(), '-', ''),'pptv_activity',mobile, 100 FROM mobile_bak_20190808_269 WHERE process_status=12;
