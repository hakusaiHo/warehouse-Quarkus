-- INSERT INTO ktst.ktst_item_master
-- (allocation_uom, glcategory, item_id, pricing_uom, primary_uom, production_uom, purchase_uom, secondary_uom, shipping_uom
-- , stock_days, stocking_type, volume_uom, weight_uom, modified_date, modified_user, address_number_planner, buyer, item_number, description1, description2)
-- VALUES('', '', 548, '', '', 'KG', '', '', '', 0, '', '', '', '', 0, 0, 0, '', '', '');

INSERT INTO ktst_item_master
(item_id, sales_category_code, modified_date, modified_user, item_number, description1, description2, allergen_mark, muslim_mark)
VALUES(548, '140', NOW(), 42, '000494', '迪化148原味開心果5斤4包入', '5斤/包,4包/箱', true, false);
INSERT INTO ktst_item_master
(item_id, sales_category_code, modified_date, modified_user, item_number, description1, description2, allergen_mark, muslim_mark)
VALUES(570, '020', NOW(), 42, '000495', '萬歲原味開心果1000G10包', '每箱10包_每包1000G', true, false);
INSERT INTO ktst_item_master
(item_id, sales_category_code, modified_date, modified_user, item_number, description1, description2, allergen_mark, muslim_mark)
VALUES(572, '020', NOW(), 42, '000499', '萬歲原味開心果900G6罐', '每箱6罐_每罐900G', true, false);
INSERT INTO ktst_item_master
(item_id, sales_category_code, modified_date, modified_user, item_number, description1, description2, allergen_mark, muslim_mark)
VALUES(318, '031', NOW(), 42, '000483', '卡迪那牛排55G12包入', '每箱12包_每包55G', false, false);
INSERT INTO ktst_item_master
(item_id, sales_category_code, modified_date, modified_user, item_number, description1, description2, allergen_mark, muslim_mark)
VALUES(200, '030', NOW(), 42, 'A20100', '可樂果57G24包入', '每箱24包_每包57G', false, false);

INSERT INTO ktst_lot_master
(expired_date, item_id, supplier_number, modified_date, modified_user, branch, item_number, lot_number)
VALUES('2024-12-31', 548, 0, NOW(), 42, 'A011', '000494', '20230830');
INSERT INTO ktst_lot_master
(expired_date, item_id, supplier_number, modified_date, modified_user, branch, item_number, lot_number)
VALUES('2024-12-31', 572, 0, NOW(), 42, 'A011', '000499', '20230830');
INSERT INTO ktst_lot_master
(expired_date, item_id, supplier_number, modified_date, modified_user, branch, item_number, lot_number)
VALUES('2024-12-31', 570, 0, NOW(), 42, 'A011', '000495', '20230831');
INSERT INTO ktst_lot_master
(expired_date, item_id, supplier_number, modified_date, modified_user, branch, item_number, lot_number)
VALUES('2024-12-31', 200, 0, NOW(), 42, 'A011', 'A20100', '20230832');
INSERT INTO ktst_lot_master
(expired_date, item_id, supplier_number, modified_date, modified_user, branch, item_number, lot_number)
VALUES('2024-12-31', 200, 0, NOW(), 42, 'A010', 'A20100', '20230833');
INSERT INTO ktst_lot_master
(expired_date, item_id, supplier_number, modified_date, modified_user, branch, item_number, lot_number)
VALUES('2024-12-31', 318, 0, NOW(), 42, 'A010', '000483', '20230831');

INSERT INTO ktst_vehicle_master
(pause_code, modified_date, modified_user, vehicle_type_id, branch, store_location, vehicle_id)
VALUES('00', NOW(), 1, 'SF', 'A010', 'LW1002', 'SF000001');
INSERT INTO ktst_vehicle_master
(pause_code, modified_date, modified_user, vehicle_type_id, branch, store_location, vehicle_id)
VALUES('00', NOW(), 1, 'PG', 'A011', 'LW1002', 'PG000001');

INSERT INTO ktst_vehicle_detail
(item_id, store_quantity, store_uom, modified_date, modified_user, lot_number, vehicle_id)
VALUES(318, 20.0000, 'KG', NOW(), 1, '20230831', 'SF000001');
INSERT INTO ktst_vehicle_detail
(item_id, store_quantity, store_uom, modified_date, modified_user, lot_number, vehicle_id)
VALUES(200, 20.0000, 'KG', NOW(), 1, '20230832', 'SF000001');
INSERT INTO ktst_vehicle_detail
(item_id, store_quantity, store_uom, modified_date, modified_user, lot_number, vehicle_id)
VALUES(200, 20.0000, 'KG', NOW(), 1, '20230833', 'PG000001');
INSERT INTO ktst_vehicle_detail
(item_id, store_quantity, store_uom, modified_date, modified_user, lot_number, vehicle_id)
VALUES(570, 40.0000, 'KG', NOW(), 1, '20230831', 'PG000001');
INSERT INTO ktst_vehicle_detail
(item_id, store_quantity, store_uom, modified_date, modified_user, lot_number, vehicle_id)
VALUES(572, 30.0000, 'KG', NOW(), 1, '20230830', 'PG000001');

INSERT INTO ktst_vehicle_type
(item_id, modified_date, modified_user, vehicle_type_id, vehicle_type_spec)
VALUES(200, NOW(), 1, 'SF', '田字型棧板');
INSERT INTO ktst_vehicle_type
(item_id, modified_date, modified_user, vehicle_type_id, vehicle_type_spec)
VALUES(548, NOW(), 1, 'PG', '長方形棧板');
INSERT INTO ktst_vehicle_type
(item_id, modified_date, modified_user, vehicle_type_id, vehicle_type_spec)
VALUES(200, NOW(), 1, 'TH', 'H形鐵架');

--3/14後更新 移儲作業
--料品分支廠主檔
INSERT INTO ktst_item_branch
(item_id, lot_control, modified_date, modified_user, branch, item_number)
VALUES(200, '', NOW(), 1, 'A010', 'A20100');
INSERT INTO ktst_item_branch
(item_id, lot_control, modified_date, modified_user, branch, item_number)
VALUES(318, '', NOW(), 1, 'A011', '000483');
INSERT INTO ktst_item_branch
(item_id, lot_control, modified_date, modified_user, branch, item_number)
VALUES(548, '', NOW(), 1, 'A010', '000494');
INSERT INTO ktst_item_branch
(item_id, lot_control, modified_date, modified_user, branch, item_number)
VALUES(570, '', NOW(), 1, 'A010', '000495');
INSERT INTO ktst_item_branch
(item_id, lot_control, modified_date, modified_user, branch, item_number)
VALUES(572, '', NOW(), 1, 'A011', '000499');

--4/26 儲位主檔
INSERT INTO ktst.ktst_location_master
(modified_date, modified_user, branch, user_reserved_reference, location_id)
VALUES(NOW(), 1, 'A010', '', 'V40U01');
INSERT INTO ktst.ktst_location_master
(modified_date, modified_user, branch, user_reserved_reference, location_id)
VALUES(NOW(), 1, 'A010', '', 'V40U02');
INSERT INTO ktst.ktst_location_master
(modified_date, modified_user, branch, user_reserved_reference, location_id)
VALUES(NOW(), 1, 'A011', '', 'V30S01');
INSERT INTO ktst.ktst_location_master
(modified_date, modified_user, branch, user_reserved_reference, location_id)
VALUES(NOW(), 1, 'A011', '', 'V30T01');
INSERT INTO ktst.ktst_location_master
(modified_date, modified_user, branch, user_reserved_reference, location_id)
VALUES(NOW(), 1, 'A011', '', 'V30E01');

--3/18 儲區設定檔
INSERT INTO ktst_area_info
(modified_date, modified_user, branch, area, area_name)
VALUES(NOW(), 1, 'A011', 'V30S', '萬歲一樓A棟東區');
INSERT INTO ktst_area_info
(modified_date, modified_user, branch, area, area_name)
VALUES(NOW(), 1, 'A011', 'V30T', '萬歲一樓A棟西區');
INSERT INTO ktst_area_info
(modified_date, modified_user, branch, area, area_name)
VALUES(NOW(), 1, 'A010', 'V40U', '聯華三樓U棟01區');
--3/18 儲區儲位對照檔
INSERT INTO ktst_area_location
(is_virtual, modified_date, modified_user, branch, area, location_desc, location_id)
VALUES(false, NOW(), 1, 'A011', 'V30S', '東區S1位', 'V30S01');
INSERT INTO ktst_area_location
(is_virtual, modified_date, modified_user, branch, area, location_desc, location_id)
VALUES(false, NOW(), 1, 'A011', 'V30T', '東區T1位', 'V30T01');
INSERT INTO ktst_area_location
(is_virtual, modified_date, modified_user, branch, area, location_desc, location_id)
VALUES(false, NOW(), 1, 'A010', 'V40U', '01區U1位', 'V40U01');
--3/18 料品偏好保存區
INSERT INTO ktst_item_area
(item_id, modified_date, modified_user, branch, prefer_save_area)
VALUES(570, NOW(), 1, 'A010', 'V20A');
INSERT INTO ktst_item_area
(item_id, modified_date, modified_user, branch, prefer_save_area)
VALUES(548, NOW(), 1, 'A010', 'V30S');