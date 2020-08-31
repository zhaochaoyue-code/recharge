-- 查询状态，拆单前的。
SELECT STATUS '充值状态', COUNT(1) '数目' FROM (
    SELECT IF(CODE='0', STATUS, 0) STATUS FROM `tl_mail_fee_log` WHERE sid='talkweb' AND activity_id='tx_0805' AND insert_time > '2019-08-06' AND money=2000
    UNION ALL
    SELECT STATUS FROM `tl_mail_fee_his_log` WHERE sid='talkweb' AND activity_id='tx_0805' AND insert_time > '2019-08-06' AND money=2000
) a
GROUP BY STATUS ORDER BY status

-- 把查询数据重置为0，让范爷的接口重新去上游查询状态。
UPDATE tl_mail_fee_log t
SET times=0 
-- select count(*) from tl_mail_fee_log t
WHERE sid = 'talkweb'
    AND activity_id = 'tx_0805'
    AND insert_time > '2019-09-06'
    AND money = 2000
    AND t.status=1
    AND CODE ='0'

-- 查询状态，拆单后的。
SELECT STATUS '充值状态', COUNT(1) '数目' FROM (
    SELECT IF(CODE='0', STATUS, 0) STATUS FROM `tl_mail_fee_log` WHERE sid='talkweb' AND activity_id='youku_activity' AND insert_time > '2019-04-09 16:28' and money in(1000,500)
    UNION ALL
    SELECT STATUS FROM `tl_mail_fee_his_log` WHERE sid='talkweb' AND activity_id='youku_activity' AND insert_time > '2019-04-09 16:28' and money in(1000,500)
) a
GROUP BY STATUS ORDER BY STATUS

-- 按状态查询记录列表
SELECT mobile, IF(CODE='0', STATUS, 0) STATUS FROM `tl_mail_fee_log` WHERE sid='talkweb' AND activity_id='youku_activity' AND insert_time > '2019-04-08' AND money=1500 AND IF(CODE='0', STATUS, 0)=1
UNION ALL
SELECT  mobile,STATUS FROM `tl_mail_fee_his_log` WHERE sid='talkweb' AND activity_id='youku_activity' AND insert_time > '2019-04-08' AND money=1500 AND STATUS=1

-- 清缓存
UPDATE tb_mail_fee_cache a, (
    SELECT a.sid, a.activity_id, a.mobile, COUNT(b.id) fee_count, IFNULL(SUM(b.money), 0) money FROM (
        SELECT DISTINCT sid, activity_id, mobile FROM tl_mail_fee_log WHERE CODE <> '30000' AND fee_id IS NULL
        AND sid = 'talkweb' AND activity_id='youku_activity' AND insert_time > '2019-06-21'
    ) a LEFT JOIN  tl_mail_fee_his_log b ON a.sid = b.sid AND a.activity_id = b.activity_id AND a.mobile = b.mobile AND b.status=2 
    AND b.insert_time > '2019-06-21'
    GROUP BY a.sid, a.activity_id, a.mobile
) c 
SET a.fee_count = c.fee_count, a.money = c.money
WHERE a.sid = c.sid AND a.activity_id = c.activity_id AND a.mobile = c.mobile
