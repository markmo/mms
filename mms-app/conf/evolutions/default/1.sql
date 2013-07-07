# --- !Ups

insert into mms.security_role(role_id, role_name) values(1, 'user');
insert into mms.security_role(role_id, role_name) values(2, 'admin');
insert into mms.security_role(role_id, role_name) values(3, 'superadmin');
insert into mms.organization(organization_id, organization_code, organization_name) values(1, 'TEST', 'Test Organization');
insert into mms.security_subject(subject_id, name, organization_id, root_id, level, lft, rgt) values(1, 'Admins', 1, 1, 0, 1, 4);
insert into mms.security_group(subject_id) values (1);
insert into mms.security_subject(subject_id, name, organization_id, root_id, level, lft, rgt) values(2, 'Super Admins', 1, 1, 0, 1, 4);
insert into mms.security_group(subject_id) values (2);
insert into mms.security_subject(subject_id, name, organization_id, parent_group_id, root_id, level, lft, rgt) values(3, 'Mark Moloney', 1, 2, 1, 1, 2, 3);
insert into mms.users(subject_id, first_name, last_name, title, email, email_validated, dept, biography, number_comments, number_votes, active) values (3, 'Mark', 'Moloney', 'Founder', 'markmo@metamorphichq.com', TRUE, 'Product Development', 'I believe that there are tremendous opportunities from instant recall of knowledge and machine augmented analysis and learning. These opportunities depend on the ability to consume and analyze vast quantities of data, which in turn, depends on the ability to discover and manage data about data (metadata).', 0, 0, TRUE);
insert into mms.subject_roles(subject_id, role_id) values(1, 2);
insert into mms.subject_roles(subject_id, role_id) values(2, 3);
insert into mms.subject_roles(subject_id, role_id) values(3, 2);
insert into mms.subject_roles(subject_id, role_id) values(3, 1);
insert into mms.linked_account(linked_account_id, provider_key, provider_user_id, user_id) values (1, 'password', '$2a$10$a62NymDY713dFnnK6Md8ReEH8BL7tKPE2EKoFEnF8.yVZnnde3try', 3);

# --- !Downs

truncate table mms.linked_account cascade;
truncate table mms.subject_roles cascade;
truncate table mms.users cascade;
truncate table mms.security_group cascade;
truncate table mms.security_subject cascade;
truncate table mms.organization cascade;
truncate table mms.security_role cascade;
