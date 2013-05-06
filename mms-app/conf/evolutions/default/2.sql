# --- !Ups

insert into mms.datatype(datatype_id, datatype_name) values(1, 'Numeric');
insert into mms.datatype(datatype_id, datatype_name) values(2, 'String');
insert into mms.datatype(datatype_id, datatype_name) values(3, 'Formula');
insert into mms.datatype(datatype_id, datatype_name) values(4, 'Blank');
insert into mms.datatype(datatype_id, datatype_name) values(5, 'Boolean');
insert into mms.datatype(datatype_id, datatype_name) values(6, 'Error');
insert into mms.datatype(datatype_id, datatype_name) values(7, 'Date');


# --- !Downs

truncate table mms.datatype;
