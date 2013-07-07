# --- !Ups

insert into mms.algorithm(algorithm_id, algorithm_name) values(1, 'Association');
insert into mms.algorithm(algorithm_id, algorithm_name) values(2, 'Baseline');
insert into mms.algorithm(algorithm_id, algorithm_name) values(3, 'Clustering');
insert into mms.algorithm(algorithm_id, algorithm_name) values(4, 'General Regression');
insert into mms.algorithm(algorithm_id, algorithm_name) values(5, 'Mining');
insert into mms.algorithm(algorithm_id, algorithm_name) values(6, 'Naive Bayes');
insert into mms.algorithm(algorithm_id, algorithm_name) values(7, 'Nearest Neighbour');
insert into mms.algorithm(algorithm_id, algorithm_name) values(8, 'Neural Network');
insert into mms.algorithm(algorithm_id, algorithm_name) values(9, 'Regression');
insert into mms.algorithm(algorithm_id, algorithm_name) values(10, 'Rule Set');
insert into mms.algorithm(algorithm_id, algorithm_name) values(11, 'Sequence');
insert into mms.algorithm(algorithm_id, algorithm_name) values(12, 'Scorecard');
insert into mms.algorithm(algorithm_id, algorithm_name) values(13, 'Support Vector Machine');
insert into mms.algorithm(algorithm_id, algorithm_name) values(14, 'Text');
insert into mms.algorithm(algorithm_id, algorithm_name) values(15, 'Time Series');
insert into mms.algorithm(algorithm_id, algorithm_name) values(16, 'Tree');

# --- !Downs

truncate table mms.algorithm cascade;
