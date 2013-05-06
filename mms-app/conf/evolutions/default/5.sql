# --- !Ups

insert into mms.settings(id) values(1);

# --- !Downs

truncate table mms.settings cascade;
