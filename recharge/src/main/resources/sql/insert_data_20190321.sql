-- 拿新的一批手机号去处理
INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile, 1000 FROM mobile_original_import_excel WHERE id>=1 AND id<=2;
INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile, 100 FROM mobile_original_import_excel WHERE id>=1 AND id<=2;
INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile, 100 FROM mobile_original_import_excel WHERE id>=1 AND id<=2;
INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile, 100 FROM mobile_original_import_excel WHERE id>=1 AND id<=2;
INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile, 100 FROM mobile_original_import_excel WHERE id>=1 AND id<=2;
	
-- 拿新的一批手机号去处理
INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile, 1000 FROM mobile_original_import_excel WHERE id>=3 AND id<=12;
INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile, 100 FROM mobile_original_import_excel WHERE id>=3 AND id<=12;
INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile, 100 FROM mobile_original_import_excel WHERE id>=3 AND id<=12;
INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile, 100 FROM mobile_original_import_excel WHERE id>=3 AND id<=12;
INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile, 100 FROM mobile_original_import_excel WHERE id>=3 AND id<=12;
	
INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile, 1000 FROM mobile_original_import_excel WHERE id>=13 AND id<=22;
INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile, 100 FROM mobile_original_import_excel WHERE id>=13 AND id<=22;
INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile, 100 FROM mobile_original_import_excel WHERE id>=13 AND id<=22;
INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile, 100 FROM mobile_original_import_excel WHERE id>=13 AND id<=22;
INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile, 100 FROM mobile_original_import_excel WHERE id>=13 AND id<=22;

INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile, 1000 FROM mobile_original_import_excel WHERE id>=23 AND id<=42;
INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile, 100 FROM mobile_original_import_excel WHERE id>=23 AND id<=42;
INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile, 100 FROM mobile_original_import_excel WHERE id>=23 AND id<=42;
INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile, 100 FROM mobile_original_import_excel WHERE id>=23 AND id<=42;
INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile, 100 FROM mobile_original_import_excel WHERE id>=23 AND id<=42;

INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile, 1000 FROM mobile_original_import_excel WHERE id>=43 AND id<=142;
INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile, 100 FROM mobile_original_import_excel WHERE id>=43 AND id<=142;
INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile, 100 FROM mobile_original_import_excel WHERE id>=43 AND id<=142;
INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile, 100 FROM mobile_original_import_excel WHERE id>=43 AND id<=142;
INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile, 100 FROM mobile_original_import_excel WHERE id>=43 AND id<=142;
	
update mobile set kc_query_id=NULL,kc_query_status=NULL,is_finished=0,process_status=2,query_retry_count=0,last_query_time=null mobile WHERE process_status IN(2,5,12,13)

INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile, 1000 FROM mobile_original_import_excel WHERE id>=143 AND id<=242;
INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile, 100 FROM mobile_original_import_excel WHERE id>=143 AND id<=242;
INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile, 100 FROM mobile_original_import_excel WHERE id>=143 AND id<=242;
INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile, 100 FROM mobile_original_import_excel WHERE id>=143 AND id<=242;
INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile, 100 FROM mobile_original_import_excel WHERE id>=143 AND id<=242;

INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile, 1000 FROM mobile_original_import_excel WHERE id>=243;
INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile, 100 FROM mobile_original_import_excel WHERE id>=243;
INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile, 100 FROM mobile_original_import_excel WHERE id>=243;
INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile, 100 FROM mobile_original_import_excel WHERE id>=243;
INSERT INTO mobile (uid,activity_id,mobile, money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile, 100 FROM mobile_original_import_excel WHERE id>=243;


