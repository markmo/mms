# --- !Ups

insert into mms.stakeholder_role(stakeholder_role_id, stakeholder_role_name, organization_id) values(1, 'Data Steward', 1);
insert into mms.stakeholder_role(stakeholder_role_id, stakeholder_role_name, organization_id) values(2, 'Business Sponsor', 1);
insert into mms.stakeholder_role(stakeholder_role_id, stakeholder_role_name, organization_id) values(3, 'Application Owner', 1);
insert into mms.stakeholder_role(stakeholder_role_id, stakeholder_role_name, organization_id) values(4, 'Data Architect', 1);
insert into mms.stakeholder_role(stakeholder_role_id, stakeholder_role_name, organization_id) values(5, 'CTO', 1);

# --- !Downs

truncate table mms.stakeholder_role cascade;
