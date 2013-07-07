# --- !Ups

insert into mms.client_tech(client_tech_id, client_tech_name) values(1, 'Web');
insert into mms.client_tech(client_tech_id, client_tech_name) values(2, 'Windows Desktop');
insert into mms.client_tech(client_tech_id, client_tech_name) values(3, 'Java Desktop');
insert into mms.client_tech(client_tech_id, client_tech_name) values(4, 'Other Cross-platform Desktop');
insert into mms.client_tech(client_tech_id, client_tech_name) values(5, 'iPhone');
insert into mms.client_tech(client_tech_id, client_tech_name) values(6, 'iPad');
insert into mms.client_tech(client_tech_id, client_tech_name) values(7, 'Android Phone');
insert into mms.client_tech(client_tech_id, client_tech_name) values(8, 'Android Tablet');
insert into mms.client_tech(client_tech_id, client_tech_name) values(9, 'Windows Phone');

insert into mms.operating_system(os_id, os_name) values(1, 'Windows XP');
insert into mms.operating_system(os_id, os_name) values(2, 'Windows 7');
insert into mms.operating_system(os_id, os_name) values(3, 'Windows 8');
insert into mms.operating_system(os_id, os_name) values(4, 'OS X');
insert into mms.operating_system(os_id, os_name) values(5, 'Linux');
insert into mms.operating_system(os_id, os_name) values(6, 'Other Unix');
insert into mms.operating_system(os_id, os_name) values(7, 'Mainframe');

insert into mms.hardware_platform(hardware_platform_id, hardware_platform_name) values(1, 'Intel Server');
insert into mms.hardware_platform(hardware_platform_id, hardware_platform_name) values(2, 'Other Server');
insert into mms.hardware_platform(hardware_platform_id, hardware_platform_name) values(3, 'Mainframe');
insert into mms.hardware_platform(hardware_platform_id, hardware_platform_name) values(4, 'Cloud');

insert into mms.language(language_id, language_name) values(1, 'Java');
insert into mms.language(language_id, language_name) values(2, 'C#');
insert into mms.language(language_id, language_name) values(3, 'Ruby');
insert into mms.language(language_id, language_name) values(4, 'Python');
insert into mms.language(language_id, language_name) values(5, 'JavaScript');
insert into mms.language(language_id, language_name) values(6, 'Scala');
insert into mms.language(language_id, language_name) values(7, 'Clojure');
insert into mms.language(language_id, language_name) values(8, 'COBOL');
insert into mms.language(language_id, language_name) values(9, 'C');
insert into mms.language(language_id, language_name) values(10, 'C++');
insert into mms.language(language_id, language_name) values(11, 'Objective-C');
insert into mms.language(language_id, language_name) values(12, 'Erlang');
insert into mms.language(language_id, language_name) values(13, 'F#');
insert into mms.language(language_id, language_name) values(14, 'R');
insert into mms.language(language_id, language_name) values(15, 'SAS');
insert into mms.language(language_id, language_name) values(16, 'VB.NET');
insert into mms.language(language_id, language_name) values(17, 'VB');
insert into mms.language(language_id, language_name) values(18, 'PL/SQL');
insert into mms.language(language_id, language_name) values(19, 'Other');


# --- !Downs

truncate table mms.client_tech;
truncate table mms.operating_system;
truncate table mms.hardware_platform;
truncate table mms.language;
