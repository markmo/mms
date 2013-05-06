# --- !Ups

insert into mms.security_classification(security_class_id, security_class_name) values(1, 'Unclassified');
insert into mms.security_classification(security_class_id, security_class_name) values(2, 'Confidential');
insert into mms.security_classification(security_class_id, security_class_name) values(3, 'Protected');
insert into mms.security_classification(security_class_id, security_class_name) values(4, 'Restricted');
insert into mms.security_classification(security_class_id, security_class_name) values(5, 'Public');

# --- !Downs

truncate table mms.security_classification cascade;
