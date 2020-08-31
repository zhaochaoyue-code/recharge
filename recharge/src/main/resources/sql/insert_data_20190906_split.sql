-- 拆单后发送 
INSERT INTO mobile (uid,activity_id,mobile, money) 
	SELECT REPLACE(UUID(), '-', ''),'tx_0805',mobile, 1000 FROM mobile_0906_tx WHERE process_status=12;
INSERT INTO mobile (uid,activity_id,mobile, money) 
	SELECT REPLACE(UUID(), '-', ''),'tx_0805',mobile, 1000 FROM mobile_0906_tx WHERE process_status=12;
	
INSERT INTO mobile (uid,activity_id,mobile, money) 
	SELECT REPLACE(UUID(), '-', ''),'yk_0805',mobile, 1000 FROM mobile_0906_yk WHERE process_status=12;
INSERT INTO mobile (uid,activity_id,mobile, money) 
	SELECT REPLACE(UUID(), '-', ''),'yk_0805',mobile, 1000 FROM mobile_0906_yk WHERE process_status=12;
	
INSERT INTO mobile (uid,activity_id,mobile, money) 
	SELECT REPLACE(UUID(), '-', ''),'iqi_0805',mobile, 1000 FROM mobile_0906_iqi WHERE process_status=12;
INSERT INTO mobile (uid,activity_id,mobile, money) 
	SELECT REPLACE(UUID(), '-', ''),'iqi_0805',mobile, 1000 FROM mobile_0906_iqi WHERE process_status=12;

