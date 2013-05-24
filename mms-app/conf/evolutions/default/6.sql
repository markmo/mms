# --- !Ups

insert into mms.stakeholder_role(stakeholder_role_id, stakeholder_role_name) values(1, 'Data Steward');
insert into mms.stakeholder_role(stakeholder_role_id, stakeholder_role_name) values(2, 'Business Sponsor');
insert into mms.stakeholder_role(stakeholder_role_id, stakeholder_role_name) values(3, 'Application Owner');
insert into mms.stakeholder_role(stakeholder_role_id, stakeholder_role_name) values(4, 'Data Architect');
insert into mms.stakeholder_role(stakeholder_role_id, stakeholder_role_name) values(5, 'CTO');

# --- !Downs

truncate table mms.stakeholder_role cascade;
