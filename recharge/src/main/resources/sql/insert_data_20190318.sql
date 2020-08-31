INSERT INTO mobile_original_import_excel(mobile) VALUE('18678691191');
INSERT INTO mobile_original_import_excel(mobile) VALUE('18538101943');
INSERT INTO mobile_original_import_excel(mobile) VALUE('15632306006');
INSERT INTO mobile_original_import_excel(mobile) VALUE('13239505951');

INSERT INTO mobile_original_import_excel(mobile) VALUES('13246990322');
INSERT INTO mobile_original_import_excel(mobile) VALUES('13231001685');
INSERT INTO mobile_original_import_excel(mobile) VALUES('18683723352');
INSERT INTO mobile_original_import_excel(mobile) VALUES('13256772140');
INSERT INTO mobile_original_import_excel(mobile) VALUES('13287762074');
INSERT INTO mobile_original_import_excel(mobile) VALUES('15610188342');
INSERT INTO mobile_original_import_excel(mobile) VALUES('15689709441');

INSERT INTO mobile_original_import_excel(mobile) VALUES('13206171508');
INSERT INTO mobile_original_import_excel(mobile) VALUES('13096071901');
INSERT INTO mobile_original_import_excel(mobile) VALUES('17650503214');

INSERT INTO mobile_original_bak_20190318_39644(id,mobile) SELECT id,mobile FROM mobile_original_import_excel WHERE id in (39641,39642,39643,39644);


-- 拿新的一批手机号去处理
--已执行
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile,1400 FROM mobile_original_import_excel where id<=3;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile,1400 FROM mobile_original_import_excel where id>=4 and id<=13;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile,1400 FROM mobile_original_import_excel where id>=14 and id<=63;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile,1400 FROM mobile_original_import_excel where id>=64 and id<=163;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile,1400 FROM mobile_original_import_excel where id>=164 and id<=1163;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile,1400 FROM mobile_original_import_excel where id>=1164 and id<=2163;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile,1400 FROM mobile_original_import_excel WHERE id>=2164 AND id<=7163;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile,1400 FROM mobile_original_import_excel WHERE id>=7164 AND id<=17163;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile,1400 FROM mobile_original_import_excel WHERE id>=17164 AND id<=22163;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile,1400 FROM mobile_original_import_excel WHERE id>=22164;
-- 手动加了7个。
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'tencent_activity',mobile,1400 FROM mobile_original_import_excel WHERE id>=39645;

--未执行：

-- 查询用户额度不足的，然后修改id和状态重试提交充值。
SELECT recharge_exception_msg,COUNT(*)
FROM mobile 
WHERE process_status=5
GROUP BY recharge_exception_msg;

SELECT * FROM mobile WHERE recharge_exception_msg='cn.csatv.common.exception.ServiceException: 用户额度不足' LIMIT 10;

UPDATE mobile SET uid=REPLACE(UUID(), '-', ''),process_status=0,recharge_retry_count=0,last_recharg_time=NULL,recharge_exception_msg=NULL
WHERE recharge_exception_msg='cn.csatv.common.exception.ServiceException: 用户额度不足';


