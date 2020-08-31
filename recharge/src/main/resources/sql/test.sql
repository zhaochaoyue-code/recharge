-- 查询状态
USE `recharge`;
SELECT 
	CASE process_status
	WHEN 0 THEN '0待充值'
	WHEN 1 THEN '1发送充值处理中'
	WHEN 2 THEN '2已发送充值待查询状态'
	WHEN 3 THEN '3发送状态查询处理中'
	WHEN 4 THEN '4已充值成功待发送短信'
	WHEN 5 THEN '5获取充值结果异常'
	WHEN 6 THEN '6获取查询状态异常'
	WHEN 7 THEN '7提交充值异常'
	WHEN 8 THEN '8提交查询状态异常'
	WHEN 9 THEN '9短信发送处理中'
	WHEN 10 THEN '10短信发送成功'
	WHEN 11 THEN '11发送短信异常'
	WHEN 12 THEN '12充值失败(调接口返回)'
	WHEN 13 THEN '13充值超时(调接口返回)'
	END AS 状态
	,COUNT(*) AS 记录数 
FROM mobile GROUP BY process_status;

--------------------------------------------------------------------------
-- 拆单前数据备份数据
-- 备份导入原始表
CREATE TABLE mobile_original_bak_20190724_247 LIKE mobile_original_import_excel;
INSERT INTO mobile_original_bak_20190724_247 SELECT * FROM mobile_original_import_excel;
TRUNCATE TABLE mobile_original_import_excel;
-- 备份发送队列表
INSERT INTO mobile_history SELECT * FROM mobile;

-- 把发送失败拿去拆单
INSERT INTO mobile_original_import_excel(mobile)
SELECT mobile FROM mobile
WHERE process_status=12;

-- 清空发送队列
delete from mobile;
--------------------------------------------------------------------------

--开始拆单
INSERT INTO mobile_original_import_excel(mobile)
SELECT mobile FROM mobile_bak_20190724_247
WHERE process_status=12 

--------------------------------------------------------------------------
-- 拆单数据备份数据
CREATE TABLE mobile_original_bak_20190731_17_5_5 LIKE mobile_original_import_excel;
INSERT INTO mobile_original_bak_20190731_17_5_5 SELECT * FROM mobile_original_import_excel;
TRUNCATE TABLE mobile_original_import_excel;

CREATE TABLE mobile_bak_20190731_34_5_5 LIKE mobile;
INSERT INTO mobile_bak_20190731_34_5_5 SELECT * FROM mobile;
DELETE FROM mobile;

CREATE TABLE mobile_sms_bak_20190731_13 LIKE mobile_sms;
INSERT INTO mobile_sms_bak_20190731_13 SELECT * FROM mobile_sms;
TRUNCATE TABLE mobile_sms;
--------------------------------------------------------------------------

-- 从mobile_original_import_excel表检查有没有导入重复的手机号。
SELECT m.mobile, mh.activity_id,mh.insert_time
FROM mobile_original_import_excel m LEFT JOIN mobile_history mh ON m.mobile=mh.mobile
WHERE mh.activity_id ='youku_activity';

SELECT m.mobile, mh.activity_id,mh.insert_time
FROM mobile_original_import_excel m LEFT JOIN mobile_history mh ON m.mobile=mh.mobile
WHERE mh.activity_id ='mangguo_activity';

SELECT m.mobile, mh.activity_id,mh.insert_time
FROM mobile_original_import_excel m LEFT JOIN mobile_history mh ON m.mobile=mh.mobile
WHERE mh.activity_id ='pptv_activity';

-- 检查表本身有没有重复。
SELECT mobile,count(*)
from recharge.mobile_original_import_excel
GROUP by mobile 
having count(*)>1;

SELECT mobile
from recharge.mobile_original_import_excel
where LENGTH(TRIM(mobile))<> LENGTH(mobile);

SELECT mobile
from recharge.mobile_original_import_excel
where trim(mobile)='';
			
-- 把数据改为待查询
UPDATE mobile 
SET is_finished=0,process_status=2,
	kc_query_id=NULL,kc_query_status=NULL,
	query_retry_count=0,last_query_time=NULL,query_exception_msg=NULL
WHERE process_status=2;
-- 把数据改为待充值
UPDATE mobile 
set uid=REPLACE(UUID(), '-', ''),is_finished=0,process_status=0,
	kc_recharge_id=NULL,kc_recharge_status=NULL,
	kc_query_id=NULL,kc_query_status=NULL,
	recharge_retry_count=0,query_retry_count=0,sms_retry_count=0,
	last_recharg_time=NULL,last_query_time=NULL,last_sms_time=NULL,
	recharge_exception_msg=NULL,query_exception_msg=NULL,sms_exception_msg=NULL;
	
UPDATE mobile 
SET uid=REPLACE(UUID(), '-', ''),is_finished=0,process_status=0,
	kc_recharge_id=NULL,kc_recharge_status=NULL,
	kc_query_id=NULL,kc_query_status=NULL,
	recharge_retry_count=0,query_retry_count=0,sms_retry_count=0,
	last_recharg_time=NULL,last_query_time=NULL,last_sms_time=NULL,
	recharge_exception_msg=NULL,query_exception_msg=NULL,sms_exception_msg=NULL
WHERE process_status = 5
    AND recharge_exception_msg = 'cn.csatv.common.exception.ServiceException: 用户额度不足'
	
-- 查询异常种类
SELECT recharge_exception_msg,COUNT(*)
FROM mobile
WHERE process_status=5 
GROUP BY recharge_exception_msg

-- 查询全部成功的报表
SELECT mobile,SUM(money) AS '成功金额'
FROM mobile 
WHERE process_status=4
GROUP BY mobile
HAVING COUNT(*)=2

-- 部分成功
SELECT mobile,SUM(money) AS '成功金额'
FROM mobile 
WHERE process_status=4
GROUP BY mobile
HAVING COUNT(*)=1

-- 全部失败
SELECT mobile
FROM mobile 
WHERE process_status=12
GROUP BY mobile
HAVING COUNT(*)=2

-- 把5条都成功的的放到短信表去发短信
INSERT INTO mobile_sms(activity_id,mobile) 
SELECT 'pptv_activity',mobile
FROM mobile 
WHERE process_status=4
GROUP BY mobile
HAVING COUNT(*)=2



-- 统计失败的记录广东的占多少
SELECT mo.province,COUNT(*)
FROM mobile m LEFT JOIN mobile_original_import_excel mo ON m.mobile=mo.mobile
WHERE m.process_status=12
GROUP BY mo.province

-- 查询出失败中的非广东的号重新查询状态，如果是广东则要拆单为10+1+1+1+1来发送了。
SELECT m.mobile,mo.province
FROM mobile m LEFT JOIN mobile_original_import_excel mo ON m.mobile=mo.mobile
WHERE m.process_status=12 AND mo.province<>'广东'

-- 临时充值
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'youku_activity','13030579281',1500;
INSERT INTO mobile(uid,activity_id,mobile,money) SELECT REPLACE(UUID(), '-', ''),'youku_activity','18682886645',1500;
-- 临时发送短信
INSERT INTO mobile_sms(activity_id,mobile) VALUE('h5游戏','15626003690');
