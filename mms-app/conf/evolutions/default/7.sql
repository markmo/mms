# --- !Ups

insert into mms.datasource(datasource_id, datasource_name) values(1, 'Oracle Data Warehouse');
insert into mms.datasource(datasource_id, datasource_name) values(2, 'SQL Server 2012 Finance Data Mart');

insert into mms.domain(domain_id, domain_name) values(1, 'Finance');
insert into mms.domain(domain_id, domain_name) values(2, 'Customer Experience');
insert into mms.domain(domain_id, domain_name, parent_id) values(3, 'Direct', 2);
insert into mms.domain(domain_id, domain_name, parent_id) values(4, 'Online', 2);
insert into mms.domain(domain_id, domain_name) values(5, 'Marketing');
insert into mms.domain(domain_id, domain_name) values(6, 'Operations');

insert into mms.business_term(business_term_id, business_term_name, definition, domain_id, datasource_of_record_id, security_class_id) values(1, 'ABN', 'abbrev. Australian Business Number.', 1, 2, 5);
insert into mms.business_term(business_term_id, business_term_name, definition, domain_id, datasource_of_record_id, security_class_id) values(2, 'Account Executive', 'A sales representative responsible for a major customer account or group of major accounts;; also referred to as an account executive.', 3, 1, 4);
insert into mms.business_term(business_term_id, business_term_name, definition, domain_id, datasource_of_record_id, security_class_id) values(3, 'Asset', 'Something valuable that an entity owns, benefits from, or has use of, in generating income.', 1, 2, 4);
insert into mms.business_term(business_term_id, business_term_name, definition, domain_id, datasource_of_record_id, security_class_id) values(4, 'Consumer Goods', 'Items purchased by consumers for personal and household use;; consumer goods are classified as durables and non-durables.', 5, NULL, 5);
insert into mms.business_term(business_term_id, business_term_name, definition, domain_id, datasource_of_record_id, security_class_id) values(5, 'FAS Pricing', 'A pricing approach in which the manufacturer pays the freight cost to the wharf;; costs associated with loading and shipping are borne by the purchaser.', 1, 2, 4);
insert into mms.business_term(business_term_id, business_term_name, definition, domain_id, datasource_of_record_id, security_class_id) values(6, 'Implied Warranty', 'The notion, upheld by courts in recent years in response to mounting consumer complaints, that a product is covered by warranty even if not expressly stated, and that manufacturers are liable for injury caused by a product even if there has been no negligence in manufacturing;; hence, caveat vendor or ''let the seller beware''.', 1, 2, 4);
insert into mms.business_term(business_term_id, business_term_name, definition, domain_id, datasource_of_record_id, security_class_id) values(7, 'KPI', 'KPI are metrics used to quantify objectives that reflect the strategic performance of your online marketing campaigns. They provide business and marketing intelligence to assess a measurable objective and the direction in which that objective is headed', NULL, 1, 4);
insert into mms.business_term(business_term_id, business_term_name, definition, domain_id, datasource_of_record_id, security_class_id) values(8, 'Labour Cost', 'The cost of wages paid to workers during an accounting period on daily, weekly, monthly, or job basis, plus payroll and related taxes and benefits (if any).', 1, 2, 4);
insert into mms.business_term(business_term_id, business_term_name, definition, domain_id, datasource_of_record_id, security_class_id) values(9, 'Labour Turnover', 'The ratio of the number of employees that leave a company through attrition, dismissal, or resignation during a period to the number of employees on payroll during the same period.', 6, 1, 3);
insert into mms.business_term(business_term_id, business_term_name, definition, domain_id, datasource_of_record_id, security_class_id) values(10, 'Life Stage Buying Power Segmentation', 'The division of a total heterogeneous market into relatively homogeneous groups on the basis of their ability to afford a product at their particular stage in the family life cycle.', 5, NULL, 4);

insert into mms.tag(tag_id, tag_name) values(1, 'suppliers');
insert into mms.tag(tag_id, tag_name) values(2, 'abbreviations');
insert into mms.tag(tag_id, tag_name) values(3, 'roles');
insert into mms.tag(tag_id, tag_name) values(4, 'balance sheet');
insert into mms.tag(tag_id, tag_name) values(5, 'pricing');
insert into mms.tag(tag_id, tag_name) values(6, 'contracts');
insert into mms.tag(tag_id, tag_name) values(7, 'metrics');
insert into mms.tag(tag_id, tag_name) values(8, 'cost');
insert into mms.tag(tag_id, tag_name) values(9, 'staff');

insert into mms.business_term_tags(business_term_id, tag_id) values(1, 1);
insert into mms.business_term_tags(business_term_id, tag_id) values(1, 2);
insert into mms.business_term_tags(business_term_id, tag_id) values(2, 3);
insert into mms.business_term_tags(business_term_id, tag_id) values(3, 3);
insert into mms.business_term_tags(business_term_id, tag_id) values(5, 5);
insert into mms.business_term_tags(business_term_id, tag_id) values(6, 6);
insert into mms.business_term_tags(business_term_id, tag_id) values(7, 7);
insert into mms.business_term_tags(business_term_id, tag_id) values(8, 8);
insert into mms.business_term_tags(business_term_id, tag_id) values(9, 9);

insert into mms.application(application_id, application_name) values(1, 'JD Edwards Financial');

insert into mms.user_group(user_group_id, user_group_name) values(1, 'Finance Team');
insert into mms.user_group(user_group_id, user_group_name) values(2, 'Marketing');
insert into mms.user_group(user_group_id, user_group_name) values(3, 'Customer Service');
insert into mms.user_group(user_group_id, user_group_name) values(4, 'Branch teams');

insert into mms.vendor(vendor_id, vendor_name) values(1, 'Oracle');
insert into mms.vendor(vendor_id, vendor_name) values(2, 'Metamorphic');
insert into mms.vendor(vendor_id, vendor_name) values(3, 'EMC');
insert into mms.vendor(vendor_id, vendor_name) values(4, 'Adobe');
insert into mms.vendor(vendor_id, vendor_name) values(5, 'Google');
insert into mms.vendor(vendor_id, vendor_name) values(6, 'Shine Technologies');

insert into mms.person(person_id, first_name, last_name, title, email, phone) values(1, 'Mark', 'Moloney', 'Director', 'markmo@enterpriseone.com', '61395551535');
insert into mms.person(person_id, first_name, last_name, title, email, phone) values(2, 'Kendra', 'Vant', 'Director', 'kendrav@enterpriseone.com', '61395551535');
insert into mms.person(person_id, first_name, last_name, title, email, phone) values(3, 'Jed', 'Knight', 'CEO', 'jedk@enterpriseone.com', '61395551535');
insert into mms.person(person_id, first_name, last_name, title, email, phone) values(4, 'Alan', 'Fresco', 'Sales Director', 'alanf@enterpriseone.com', '61395551535');
insert into mms.person(person_id, first_name, last_name, title, email, phone) values(5, 'Cosmo', 'Knott', 'CMO', 'cosmok@enterpriseone.com', '61395551535');
insert into mms.person(person_id, first_name, last_name, title, email, phone) values(6, 'Gene', 'Poole', 'Service Delivery Manager', 'genep@enterpriseone.com', '61395551535');
insert into mms.person(person_id, first_name, last_name, title, email, phone) values(7, 'Jim', 'Shue', 'Operations Manager', 'jims@enterpriseone.com', '61395551535');
insert into mms.person(person_id, first_name, last_name, title, email, phone) values(8, 'Lina', 'Credit', 'CFO', 'jims@enterpriseone.com', '61395551535');

-- insert into mms.os(os_id, os_name) values(1, 'Red Hat Linux');
-- insert into mms.os(os_id, os_name) values(2, 'Windows Server 2012');
-- insert into mms.os(os_id, os_name) values(3, 'OS/400');
-- insert into mms.os(os_id, os_name) values(4, 'OS/360');

insert into mms.business_term_stakeholder_person(business_term_id, stakeholder_role_id, person_id) values(1, 1, 8);
insert into mms.business_term_stakeholder_person(business_term_id, stakeholder_role_id, person_id) values(2, 1, 4);


# --- !Downs

truncate table mms.datasource cascade;
truncate table mms.domain cascade;
truncate table mms.business_term cascade;
truncate table mms.tag cascade;
truncate table mms.business_term_tags cascade;
truncate table mms.application cascade;
truncate table mms.user_group cascade;
truncate table mms.vendors cascade;
truncate table mms.person cascade;
-- truncate table mms.os cascade;
truncate table mms.business_term_stakeholder_person cascade;
