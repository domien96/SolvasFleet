
TRUNCATE TABLE "fleet_subscriptions" CASCADE;
TRUNCATE TABLE "fleets" CASCADE;
TRUNCATE TABLE "vehicles" CASCADE;
TRUNCATE TABLE "roles" CASCADE;
TRUNCATE TABLE "companies" CASCADE;
TRUNCATE TABLE "users" CASCADE;


INSERT INTO "users" (user_id,first_name,last_name,email,password,updated_at,created_at) VALUES (1,'Sierra','Cooper','metus.vitae.velit@facilisismagnatellus.net','WBL90DHP2RU','2017-02-28 12:15:29','2018-01-27 01:41:55');
INSERT INTO "users" (user_id,first_name,last_name,email,password,updated_at,created_at) VALUES (2,'Lamar','Yang','eleifend.Cras@tellusimperdiet.edu','GTZ20SSS5HV','2017-01-25 22:22:47','2017-04-30 09:44:22');
INSERT INTO "users" (user_id,first_name,last_name,email,password,updated_at,created_at) VALUES (3,'Hanna','Sims','ac.mattis.semper@semegetmassa.org','HPQ85RQA5VS','2016-11-20 23:35:34','2018-01-01 07:59:24');

--INSERT INTO "vehicle_types" (vehicletype_id,name) VALUES (1,'Persoonswagen'),(2,'Trekker'),(3,'Aanhangwagen'),(4,'Camionet'),(5,'Moto');


INSERT INTO "companies" (company_id,name,vat,phone,address_city,address_country,address_house_number,address_postalCode,address_street,updated_at,created_at,company_type) VALUES (1,'Volutpat Company','Q57E87DW34DR','08 90 21 34 65','Lanco','Saudi Arabia',159,'573460','Ap #373-5592 Duis St.','2016-01-12 23:46:04','2016-06-09 07:57:09',1);
INSERT INTO "companies" (company_id,name,vat,phone,address_city,address_country,address_house_number,address_postalCode,address_street,updated_at,created_at,company_type) VALUES (2,'Faucibus Ut Industries','W64Y14BO63MG','07 40 61 67 21','King Township','Thailand',100,'1088','P.O. Box 123, 6958 Est, Road','2015-12-04 17:05:48','2016-04-29 13:50:10',1);
INSERT INTO "companies" (company_id,name,vat,phone,address_city,address_country,address_house_number,address_postalCode,address_street,updated_at,created_at,company_type) VALUES (3,'Vulputate Ullamcorper Industries','L98V91FE17LD','06 82 21 42 43','Juazeiro do Norte','Switzerland',156,'74554','718-5787 Turpis. Rd.','2016-01-27 05:29:40','2017-03-25 22:37:33',1);


--INSERT INTO "roles" (role_id,function,company_id,user_id,created_at,updated_at) VALUES (1,'BasicUSer',1,3,'2015-09-07 17:13:12','2018-01-18 10:46:24');
--INSERT INTO "roles" (role_id,function,company_id,user_id,created_at,updated_at) VALUES (2,'Supervisor',3,2,'2015-07-11 05:23:39','2016-06-16 08:06:19');
--INSERT INTO "roles" (role_id,function,company_id,user_id,created_at,updated_at) VALUES (3,'BasicUSer',2,1,'2016-01-28 00:00:40','2016-08-03 00:35:28');


INSERT INTO "vehicles" (vehicle_id,license_plate,chassis_number,model,kilometer_count,year,leasing_company_id,value,brand,vehicletype_id,updated_at,created_at) VALUES (1,'1-IKR-795','I85K66OH72VD','RLH28YTM7GU',278625,2004,1,51418,'Audi',1,'2015-12-13 05:09:40','2017-09-27 10:32:59');
INSERT INTO "vehicles" (vehicle_id,license_plate,chassis_number,model,kilometer_count,year,leasing_company_id,value,brand,vehicletype_id,updated_at,created_at) VALUES (2,'1-AOP-124','L96U37PM44XQ','TXV75WNS1ZV',219284,2005,2,55618,'Audi',2,'2016-01-16 04:39:19','2017-06-26 22:33:40');
INSERT INTO "vehicles" (vehicle_id,license_plate,chassis_number,model,kilometer_count,year,leasing_company_id,value,brand,vehicletype_id,updated_at,created_at) VALUES (3,'1-KZK-146','S72T19ZF92TW','ZPP76APY9TS',384999,2009,3,26004,'Skoda',3,'2015-10-30 00:23:20','2016-08-17 00:13:29');


INSERT INTO "fleets" (fleet_id,company_id,name,updated_at,created_at,facturation_period,payment_period) VALUES (1,1,' Leasingwagens ','2015-10-11 05:48:39','2017-11-01 01:22:30',1,1);
INSERT INTO "fleets" (fleet_id,company_id,name,updated_at,created_at,facturation_period,payment_period) VALUES (2,1,' Leasingwagens2','2015-09-27 01:31:01','2016-06-17 08:10:44',1,1);
INSERT INTO "fleets" (fleet_id,company_id,name,updated_at,created_at,facturation_period,payment_period) VALUES (3,2,'Vracht ','2015-12-17 04:32:24','2016-07-11 20:26:16',1,1);


INSERT INTO "fleet_subscriptions" (fleet_subscription_id,start_date,end_date,updated_at,created_at,vehicle_id,fleet_id) VALUES (1,'2018-03-07','2016-06-03','2015-09-20 07:18:04','2017-08-22 17:31:22',1,1);
INSERT INTO "fleet_subscriptions" (fleet_subscription_id,start_date,end_date,updated_at,created_at,vehicle_id,fleet_id) VALUES (2,'2016-08-22','2018-03-06','2015-09-30 14:49:46','2016-09-07 07:55:42',2,2);
INSERT INTO "fleet_subscriptions" (fleet_subscription_id,start_date,end_date,updated_at,created_at,vehicle_id,fleet_id) VALUES (3,'2018-02-21','2017-11-16','2015-11-16 02:19:13','2016-05-31 19:12:09',3,3);
INSERT INTO "fleet_subscriptions" (fleet_subscription_id,start_date,end_date,updated_at,created_at,vehicle_id,fleet_id) VALUES (4,'2016-02-21','2015-11-16','2015-11-16 02:19:13','2016-05-31 19:12:09',3,2);


INSERT INTO "revisions" (revision_id,entity_type,user_id,entity_id,logDate,method) VALUES (1,'VEHICLE',1,1,'2016-05-31 19:12:09',1);
INSERT INTO "revisions" (revision_id,entity_type,user_id,entity_id,logDate,method) VALUES (2,'USER',1,1,'2016-05-31 19:12:09',1);






SELECT setval('users_user_id_seq', (SELECT MAX(user_id) FROM users));
SELECT setval('companies_company_id_seq', (SELECT MAX(company_id) FROM companies));
SELECT setval('vehicles_vehicle_id_seq', (SELECT MAX(vehicle_id) FROM vehicles));
SELECT setval('fleets_fleet_id_seq', (SELECT MAX(fleet_id) FROM fleets));
SELECT setval('fleet_subscriptions_fleet_subscription_id_seq', (SELECT MAX(fleet_subscription_id) FROM fleet_subscriptions));
SELECT setval('revisions_revision_id_seq', (SELECT MAX(revision_id) FROM revisions));

INSERT INTO roles (role_id, function) VALUES (1, 'administrator'), (2,'test');
INSERT INTO role_permissions (role_id, permission_id) SELECT role_id, permission_id FROM roles FULL JOIN permissions ON 1=1 WHERE function = 'administrator';

INSERT INTO functions (user_id, role_id, start_date) VALUES (1,1,now());

INSERT INTO contracts (fleet_subscription_id, startDate,endDate,franchise,premium,company_id,insurance_type_id) VALUES (1,'2014-02-21','2015-11-16',100,100,1,1);

INSERT INTO invoices (paid,fleet_id,start_date,end_date,type) VALUES (FALSE,1,'2014-02-21','2015-11-16',1);
 -- amount 3112.321,
