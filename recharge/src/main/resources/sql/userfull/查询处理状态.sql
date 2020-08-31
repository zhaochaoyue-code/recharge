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