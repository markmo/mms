# --- !Ups

insert into mms.analysis_type(analysis_type_id, analysis_type_name) values(1, 'String analyzer');
insert into mms.analysis_type(analysis_type_id, analysis_type_name) values(2, 'Number analyzer');
insert into mms.analysis_type(analysis_type_id, analysis_type_name) values(3, 'Date/time analyzer');
insert into mms.analysis_type(analysis_type_id, analysis_type_name) values(4, 'Boolean analyzer');
insert into mms.analysis_type(analysis_type_id, analysis_type_name) values(5, 'Pattern finder');

insert into mms.metric(metric_id, metric_name, description) values (1, 'Row count', 'The number of rows.');
insert into mms.metric(metric_id, metric_name, description) values (2, 'Null count', 'The number of rows with Null values.');
insert into mms.metric(metric_id, metric_name, description) values (3, 'Highest value', 'The highest value by natural ordering.');
insert into mms.metric(metric_id, metric_name, description) values (4, 'Lowest value', 'The lowest value by natural ordering.');
insert into mms.metric(metric_id, metric_name, description) values (5, 'Sum', 'The sum of all values in this column.');
insert into mms.metric(metric_id, metric_name, description) values (6, 'Mean', 'The arithmetic mean, or average, of values in this column.');
insert into mms.metric(metric_id, metric_name, description) values (7, 'Geometric mean', 'The central tendency or typical value of values in this column. It is calculated as the nth root (where n is the row count) of the product of the numbers.');
insert into mms.metric(metric_id, metric_name, description) values (8, 'Standard deviation', 'A measure of the amount of variation or dispersion from the mean (expected value). It is calculated as the square root of the variance.');
insert into mms.metric(metric_id, metric_name, description) values (9, 'Variance', 'A measure of how far the set of numbers is spread out.');
insert into mms.metric(metric_id, metric_name, description) values (10, 'Second moment', 'In mathematics, a moment is, loosely speaking, a quantitative measure of the shape of a set of points. The second moment measures the ""width"" (in a particular sense) os a set of points in one dimension, or in higher dimensions measures the shape of a cloud of points as it could be fit by an ellipsoid.');
insert into mms.metric(metric_id, metric_name, description) values (11, 'Sum of squares', 'A measure of dispersion (also called variability). It is defined as being the sum, over all observations, of the squared differences of each observation from the overall mean.');
insert into mms.metric(metric_id, metric_name, description) values (12, 'Median', 'A numerical value separating the higher half of the set of numbers from the lower half.');
insert into mms.metric(metric_id, metric_name, description) values (13, '25th percentile', 'The value below which a quarter of the values fall. Also known as the first quartile.');
insert into mms.metric(metric_id, metric_name, description) values (14, '75th percentile', 'The value below which three quarters of the values fall. Also known as the third quartile.');
insert into mms.metric(metric_id, metric_name, description) values (15, 'Skewness', 'A measure of the extent to which the set of numbers ""leans"" to one side of the mean. The skewness value can be positive, negative or neutral.');
insert into mms.metric(metric_id, metric_name, description) values (16, 'Kurtosis', 'A measure of the ""peakedness"" of the set of numbers. A high kurtosis distribution has a sharper peak and longer, fatter tails, while a low kurtosis distribution has a more rounded peak and shorter, thinner tails.');

insert into mms.metric(metric_id, metric_name, description) values (17, 'Blank count', 'The number of rows with empty values.');
insert into mms.metric(metric_id, metric_name, description) values (18, 'Entirely uppercase count', 'The number of rows with entirely uppercase values.');
insert into mms.metric(metric_id, metric_name, description) values (19, 'Entirely lowercase count', 'The number of rows with entirely lowercase values.');
insert into mms.metric(metric_id, metric_name, description) values (20, 'Total char count', 'The total number of characters across all rows in the column.');
insert into mms.metric(metric_id, metric_name, description) values (21, 'Max chars', 'The maximum number of characters in any one row.');
insert into mms.metric(metric_id, metric_name, description) values (22, 'Min chars', 'The minimum number of characters in any one row.');
insert into mms.metric(metric_id, metric_name, description) values (23, 'Avg chars', 'The average number of characters per row.');
insert into mms.metric(metric_id, metric_name, description) values (24, 'Max white spaces', 'The maximum number of white spaces in any one row.');
insert into mms.metric(metric_id, metric_name, description) values (25, 'Min white spaces', 'The minumum number of white spaces in any one row.');
insert into mms.metric(metric_id, metric_name, description) values (26, 'Avg white spaces', 'The average number of white spaces per row.');
insert into mms.metric(metric_id, metric_name, description) values (27, 'Uppercase chars', 'The number of uppercase characters across all rows in the column.');
insert into mms.metric(metric_id, metric_name, description) values (28, 'Uppercase chars (excl. first letters)', 'The number of uppercase characters, except for first letters, across all rows in the column.');
insert into mms.metric(metric_id, metric_name, description) values (29, 'Lowercase chars', 'The number of lowercase characters across all rows in the column.');
insert into mms.metric(metric_id, metric_name, description) values (30, 'Digit chars', 'The number of characters that are numbers 0-9 across all rows in the column.');
insert into mms.metric(metric_id, metric_name, description) values (31, 'Diacritic chars', 'The number of diacritic characters across all rows in the column. The main use of diacritical marks in the Latin-derived alphabet is to change the sound value of the letter to which they are added.');
insert into mms.metric(metric_id, metric_name, description) values (32, 'Non-letter chars', 'The number of characters that are not in the alphabet a-z or A-Z across all rows in the column.');
insert into mms.metric(metric_id, metric_name, description) values (33, 'Word count', 'The total word count across all rows in the column.');
insert into mms.metric(metric_id, metric_name, description) values (34, 'Max words', 'The maximum number of words in any one row.');
insert into mms.metric(metric_id, metric_name, description) values (35, 'Min words', 'The minimum number of rows in any one row.');

insert into mms.metric(metric_id, metric_name, description) values (36, 'Highest date', 'The most recent date in the column.');
insert into mms.metric(metric_id, metric_name, description) values (37, 'Lowest date', 'The oldest date in the column.');
insert into mms.metric(metric_id, metric_name, description) values (38, 'Highest time', 'The most recent time in the column.');
insert into mms.metric(metric_id, metric_name, description) values (39, 'Lowest time', 'The oldest time in the column.');

insert into mms.metric(metric_id, metric_name, description) values (40, 'Least frequent', 'The least frequent occurring value.');
insert into mms.metric(metric_id, metric_name, description) values (41, 'Most frequent', 'The most frequent occurring value.');
insert into mms.metric(metric_id, metric_name, description) values (42, 'False count', 'The number of rows with false values.');
insert into mms.metric(metric_id, metric_name, description) values (43, 'True count', 'The number of rows with true values.');


# --- !Downs

truncate table mms.analysis_type;

truncate table mms.metric;
