-- 拿新的一批手机号去处理
INSERT INTO mobile (mobile, money) SELECT mobile, 1000 FROM mobile_original_import_excel WHERE id>=1 AND id<=2;
INSERT INTO mobile (mobile, money) SELECT mobile, 100 FROM mobile_original_import_excel WHERE id>=1 AND id<=2;
INSERT INTO mobile (mobile, money) SELECT mobile, 100 FROM mobile_original_import_excel WHERE id>=1 AND id<=2;
INSERT INTO mobile (mobile, money) SELECT mobile, 100 FROM mobile_original_import_excel WHERE id>=1 AND id<=2;
INSERT INTO mobile (mobile, money) SELECT mobile, 100 FROM mobile_original_import_excel WHERE id>=1 AND id<=2;

INSERT INTO mobile (mobile, money) SELECT mobile, 1000 FROM mobile_original_import_excel WHERE id>=3 AND id<=12;
INSERT INTO mobile (mobile, money) SELECT mobile, 100 FROM mobile_original_import_excel WHERE id>=3 AND id<=12;
INSERT INTO mobile (mobile, money) SELECT mobile, 100 FROM mobile_original_import_excel WHERE id>=3 AND id<=12;
INSERT INTO mobile (mobile, money) SELECT mobile, 100 FROM mobile_original_import_excel WHERE id>=3 AND id<=12;
INSERT INTO mobile (mobile, money) SELECT mobile, 100 FROM mobile_original_import_excel WHERE id>=3 AND id<=12;

INSERT INTO mobile (mobile, money) SELECT mobile, 1000 FROM mobile_original_import_excel WHERE id>=13 AND id<=62;
INSERT INTO mobile (mobile, money) SELECT mobile, 100 FROM mobile_original_import_excel WHERE id>=13 AND id<=62;
INSERT INTO mobile (mobile, money) SELECT mobile, 100 FROM mobile_original_import_excel WHERE id>=13 AND id<=62;
INSERT INTO mobile (mobile, money) SELECT mobile, 100 FROM mobile_original_import_excel WHERE id>=13 AND id<=62;
INSERT INTO mobile (mobile, money) SELECT mobile, 100 FROM mobile_original_import_excel WHERE id>=13 AND id<=62;

INSERT INTO mobile (mobile, money) SELECT mobile, 1000 FROM mobile_original_import_excel WHERE id>=63 AND id<=162;
INSERT INTO mobile (mobile, money) SELECT mobile, 100 FROM mobile_original_import_excel WHERE id>=63 AND id<=162;
INSERT INTO mobile (mobile, money) SELECT mobile, 100 FROM mobile_original_import_excel WHERE id>=63 AND id<=162;
INSERT INTO mobile (mobile, money) SELECT mobile, 100 FROM mobile_original_import_excel WHERE id>=63 AND id<=162;
INSERT INTO mobile (mobile, money) SELECT mobile, 100 FROM mobile_original_import_excel WHERE id>=63 AND id<=162;

INSERT INTO mobile (mobile, money) SELECT mobile, 1000 FROM mobile_original_import_excel WHERE id>=163 AND id<=262;
INSERT INTO mobile (mobile, money) SELECT mobile, 100 FROM mobile_original_import_excel WHERE id>=163 AND id<=262;
INSERT INTO mobile (mobile, money) SELECT mobile, 100 FROM mobile_original_import_excel WHERE id>=163 AND id<=262;
INSERT INTO mobile (mobile, money) SELECT mobile, 100 FROM mobile_original_import_excel WHERE id>=163 AND id<=262;
INSERT INTO mobile (mobile, money) SELECT mobile, 100 FROM mobile_original_import_excel WHERE id>=163 AND id<=262;

INSERT INTO mobile (mobile, money) SELECT mobile, 1000 FROM mobile_original_import_excel WHERE id>=263 AND id<=462;
INSERT INTO mobile (mobile, money) SELECT mobile, 100 FROM mobile_original_import_excel WHERE id>=263 AND id<=462;
INSERT INTO mobile (mobile, money) SELECT mobile, 100 FROM mobile_original_import_excel WHERE id>=263 AND id<=462;
INSERT INTO mobile (mobile, money) SELECT mobile, 100 FROM mobile_original_import_excel WHERE id>=263 AND id<=462;
INSERT INTO mobile (mobile, money) SELECT mobile, 100 FROM mobile_original_import_excel WHERE id>=263 AND id<=462;

INSERT INTO mobile (mobile, money) SELECT mobile, 1000 FROM mobile_original_import_excel WHERE id>=463 AND id<=562;
INSERT INTO mobile (mobile, money) SELECT mobile, 100 FROM mobile_original_import_excel WHERE id>=463 AND id<=562;
INSERT INTO mobile (mobile, money) SELECT mobile, 100 FROM mobile_original_import_excel WHERE id>=463 AND id<=562;
INSERT INTO mobile (mobile, money) SELECT mobile, 100 FROM mobile_original_import_excel WHERE id>=463 AND id<=562;
INSERT INTO mobile (mobile, money) SELECT mobile, 100 FROM mobile_original_import_excel WHERE id>=463 AND id<=562;

INSERT INTO mobile (mobile, money) SELECT mobile, 1000 FROM mobile_original_import_excel WHERE id>=563 AND id<=667;
INSERT INTO mobile (mobile, money) SELECT mobile, 100 FROM mobile_original_import_excel WHERE id>=563 AND id<=667;
INSERT INTO mobile (mobile, money) SELECT mobile, 100 FROM mobile_original_import_excel WHERE id>=563 AND id<=667;
INSERT INTO mobile (mobile, money) SELECT mobile, 100 FROM mobile_original_import_excel WHERE id>=563 AND id<=667;
INSERT INTO mobile (mobile, money) SELECT mobile, 100 FROM mobile_original_import_excel WHERE id>=563 AND id<=667;

INSERT INTO mobile (mobile, money) SELECT mobile, 1000 FROM mobile_original_import_excel WHERE id=668;
INSERT INTO mobile (mobile, money) SELECT mobile, 100 FROM mobile_original_import_excel WHERE id=668;
INSERT INTO mobile (mobile, money) SELECT mobile, 100 FROM mobile_original_import_excel WHERE id=668;
INSERT INTO mobile (mobile, money) SELECT mobile, 100 FROM mobile_original_import_excel WHERE id=668;
INSERT INTO mobile (mobile, money) SELECT mobile, 100 FROM mobile_original_import_excel WHERE id=668;

INSERT INTO mobile (mobile, money) SELECT mobile, 1000 FROM mobile_original_import_excel WHERE id=669;
INSERT INTO mobile (mobile, money) SELECT mobile, 100 FROM mobile_original_import_excel WHERE id=669;
INSERT INTO mobile (mobile, money) SELECT mobile, 100 FROM mobile_original_import_excel WHERE id=669;
INSERT INTO mobile (mobile, money) SELECT mobile, 100 FROM mobile_original_import_excel WHERE id=669;
INSERT INTO mobile (mobile, money) SELECT mobile, 100 FROM mobile_original_import_excel WHERE id=669;

UPDATE mobile SET process_status=0,retry_recharge_count=0 WHERE  recharge_exception_msg='cn.csatv.common.exception.ServiceException: 用户额度不足';
	