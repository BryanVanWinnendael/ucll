insert INTO patient (social_security_number, birth_date, gender) VALUES ('74329457893', '1994-07-25', 'FEMALE');
insert INTO patient (social_security_number, birth_date, gender) VALUES ('93051822361', '1993-05-18', 'MALE');
insert into patient(social_security_number, birth_date, gender) values('164325495683', '1998-12-18', 'MALE');
insert into patient(social_security_number, birth_date, gender) values('4326597653462', '2000-11-25', 'FEMALE');
insert into patient(social_security_number, birth_date, gender) values('3046592375', '1972-10-18', 'MALE');
insert into patient(social_security_number, birth_date, gender) values('2953761542', '1970-03-25', 'FEMALE');
insert into patient(social_security_number, birth_date, gender) values('562973564', '1935-07-18', 'MALE');
insert into patient(social_security_number, birth_date, gender) values('673285947', '1940-04-25', 'FEMALE');

insert INTO examination (length, weight, examination_date) VALUES (159, 56000, '2019-08-14');
insert INTO examination (length, weight, examination_date) VALUES (159, 57000, '2019-12-02');
insert INTO examination (length, weight, examination_date) VALUES (159, 56000, '2019-04-08');
insert INTO examination (length, weight, examination_date) VALUES (176, 82000, '2018-03-25');

insert into patient_examinations (patient_id, examinations_id) VALUES (1,1);
insert into patient_examinations (patient_id, examinations_id) VALUES (1,2);
insert into patient_examinations (patient_id, examinations_id) VALUES (1,3);
insert into patient_examinations (patient_id, examinations_id) VALUES (2,4);