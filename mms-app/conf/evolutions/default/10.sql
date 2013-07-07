# --- !Ups

insert into mms.business_event(event_id, event_name, subject_type, target) values(1, 'Mortgage Visit', 'CUSTOMER', null);
insert into mms.business_event(event_id, event_name, subject_type, target) values(2, 'Investment Account Open', 'CUSTOMER', null);
insert into mms.business_event(event_id, event_name, subject_type, target) values(3, 'Investment Calculator Usage', 'CUSTOMER', null);
insert into mms.business_event(event_id, event_name, subject_type, target) values(4, 'Online Account Creation', 'CUSTOMER', null);
insert into mms.business_event(event_id, event_name, subject_type, target) values(5, 'Online Banking Login', 'CUSTOMER', null);
insert into mms.business_event(event_id, event_name, subject_type, target) values(6, 'Click on Ad', 'CUSTOMER', 'AD');
insert into mms.business_event(event_id, event_name, subject_type, target) values(7, 'Page View', 'CUSTOMER', 'PAGE');
insert into mms.business_event(event_id, event_name, subject_type, target) values(8, 'Product Activated', 'CUSTOMER', 'PRODUCT');

# --- !Downs

truncate table mms.business_event cascade;
