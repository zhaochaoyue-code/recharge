-- 拿新的一批手机号去处理
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile,1400 FROM mobile_original_import_excel WHERE id<=5;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile,1400 FROM mobile_original_import_excel WHERE id>=6 and id<=10;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile,1400 FROM mobile_original_import_excel WHERE id>=11 and id<=50;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile,1400 FROM mobile_original_import_excel WHERE id>=51 and id<=150;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile,1400 FROM mobile_original_import_excel WHERE id>=151 and id<=1150;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile,1400 FROM mobile_original_import_excel WHERE id>=1151 and id<=2150;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile,1400 FROM mobile_original_import_excel WHERE id>=2151 and id<=7150;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile,1400 FROM mobile_original_import_excel WHERE id>=7151 and id<=12150;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile,1400 FROM mobile_original_import_excel WHERE id>=12151 and id<=17150;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile,1400 FROM mobile_original_import_excel WHERE id>=17151 and id<=27150;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile,1400 FROM mobile_original_import_excel WHERE id>=27151 and id<=28150;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile,1400 FROM mobile_original_import_excel WHERE id>=28151 and id<=42150;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile,1400 FROM mobile_original_import_excel WHERE id>=42151 and id<=50410;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile,1400 FROM mobile_original_import_excel WHERE id IN(50416,50415,50414,50413,50412,50411)
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile,1400 FROM mobile_original_import_excel WHERE id=50417;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile,1400 FROM mobile_original_import_excel WHERE id in(50418,50419);
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile,1400 FROM mobile_original_import_excel WHERE id in(50420,50421,50422,50423);
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile,1400 FROM mobile_original_import_excel WHERE id>=50424 AND id<=57331

-- 误操作后整理数据：
SELECT mb.uid ,m.uid AS mui
FROM mobile_5191 mb LEFT JOIN mobile m ON mb.id =m.id
WHERE m.id IS NOT NULL AND mb.uid=m.uid AND mb.process_status IN(2,5);


UPDATE mobile_5191 mb LEFT JOIN mobile m ON mb.id =m.id
set m.uid=mb.uid
WHERE m.id IS NOT NULL;

UPDATE mobile_5191 mb LEFT JOIN mobile m ON mb.id =m.id
-- set m.uid=mb.uid
SET     m.kc_recharge_id=mb.kc_recharge_id,
	m.kc_recharge_status=mb.kc_recharge_status,
	m.kc_query_id=mb.kc_query_id,
	m.kc_query_status=mb.kc_query_status,
	m.is_finished=mb.is_finished,
	m.process_status=mb.process_status,
	m.recharge_retry_count=mb.recharge_retry_count,
	m.query_retry_count=mb.query_retry_count,
	m.last_recharg_time=mb.last_recharg_time,
	m.last_query_time=mb.last_query_time,
	m.recharge_exception_msg=mb.recharge_exception_msg,
	m.query_exception_msg=mb.query_exception_msg
WHERE m.id IS NOT NULL AND mb.process_status IN(2,5);