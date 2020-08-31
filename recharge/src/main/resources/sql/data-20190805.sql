SELECT mobile AS '手机号',ROUND(money/100,0) AS '充值金额',last_recharg_time AS '充值时间',
	(
	CASE process_status
	WHEN 10 THEN '成功'
	WHEN 12 THEN '失败'
	ELSE process_status
	END
	) AS '是否成功'
FROM mobile_0805_tx
-- limit 10
UNION ALL 
SELECT mobile AS '手机号',ROUND(money/100,0) AS '充值金额',last_recharg_time AS '充值时间',
	(
	CASE process_status
	WHEN 4 THEN '成功'
	WHEN 12 THEN '失败'
	ELSE process_status
	END
	) AS '是否成功'
FROM mobile WHERE activity_id='tx_0805'

SELECT mobile AS '手机号',ROUND(money/100,0) AS '充值金额',last_recharg_time AS '充值时间',
	(
	CASE process_status
	WHEN 10 THEN '成功'
	WHEN 12 THEN '失败'
	ELSE process_status
	END
	) AS '是否成功'
FROM mobile_0805_yk
-- limit 10
UNION ALL 
SELECT mobile AS '手机号',ROUND(money/100,0) AS '充值金额',last_recharg_time AS '充值时间',
	(
	CASE process_status
	WHEN 4 THEN '成功'
	WHEN 12 THEN '失败'
	ELSE process_status
	END
	) AS '是否成功'
FROM mobile WHERE activity_id='yk_0805'

SELECT mobile AS '手机号',ROUND(money/100,0) AS '充值金额',last_recharg_time AS '充值时间',
	(
	CASE process_status
	WHEN 10 THEN '成功'
	WHEN 12 THEN '失败'
	ELSE process_status
	END
	) AS '是否成功'
FROM mobile_0805_iqi
-- limit 10
UNION ALL 
SELECT mobile AS '手机号',ROUND(money/100,0) AS '充值金额',last_recharg_time AS '充值时间',
	(
	CASE process_status
	WHEN 4 THEN '成功'
	WHEN 12 THEN '失败'
	ELSE process_status
	END
	) AS '是否成功'
FROM mobile WHERE activity_id='iqi_0805'

SELECT mobile AS '手机号',ROUND(money/100,0) AS '充值金额',last_recharg_time AS '充值时间',
	(
	CASE process_status
	WHEN 10 THEN '成功'
	WHEN 12 THEN '失败'
	ELSE process_status
	END
	) AS '是否成功'
FROM mobile_bak_20190808_269
-- limit 10
UNION ALL 
SELECT mobile AS '手机号',ROUND(money/100,0) AS '充值金额',last_recharg_time AS '充值时间',
	(
	CASE process_status
	WHEN 4 THEN '成功'
	WHEN 12 THEN '失败'
	ELSE process_status
	END
	) AS '是否成功'
FROM mobile WHERE activity_id='pptv_activity'



TRUNCATE TABLE temp_id;
INSERT INTO temp_id(id) 
	SELECT id
	FROM recharge.mobile_original_import_excel_tx
	GROUP BY mobile 
	HAVING COUNT(*)=2;
DELETE FROM mobile_original_import_excel_tx WHERE id IN(
SELECT id
 FROM temp_id
)

TRUNCATE TABLE temp_id;
INSERT INTO temp_id(id) 
	SELECT id
	FROM recharge.mobile_original_import_excel_iqy
	GROUP BY mobile 
	HAVING COUNT(*)=2;
DELETE FROM mobile_original_import_excel_iqy WHERE id IN(
SELECT id
 FROM temp_id
)

TRUNCATE TABLE temp_id;
INSERT INTO temp_id(id) 
	SELECT id
	FROM recharge.mobile_original_import_excel_yk
	GROUP BY mobile 
	HAVING COUNT(*)=2;
DELETE FROM mobile_original_import_excel_yk WHERE id IN(
SELECT id
 FROM temp_id
)

-- 补发查重
SELECT t.mobile
FROM (
	SELECT mobile FROM recharge.mobile_original_import_excel_yk_27867
	UNION ALL
	SELECT mobile FROM recharge.mobile_original_import_excel_yk_200
     ) t
GROUP BY t.mobile 
HAVING COUNT(*)>1;