# --- !Ups

insert into mms.security_classification(security_class_id, security_class_name, organization_id) values(1, 'Unclassified', 1);
insert into mms.security_classification(security_class_id, security_class_name, organization_id) values(2, 'Confidential', 1);
insert into mms.security_classification(security_class_id, security_class_name, organization_id) values(3, 'Protected', 1);
insert into mms.security_classification(security_class_id, security_class_name, organization_id) values(4, 'Restricted', 1);
insert into mms.security_classification(security_class_id, security_class_name, organization_id) values(5, 'Public', 1);

# --- !Downs

truncate table mms.security_classification cascade;
