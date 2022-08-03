INSERT INTO vets VALUES (1, 'James', 'Carter');
INSERT INTO vets VALUES (2, 'Helen', 'Leary');
INSERT INTO vets VALUES (3, 'Linda', 'Douglas');
INSERT INTO vets VALUES (4, 'Rafael', 'Ortega');
INSERT INTO vets VALUES (5, 'Henry', 'Stevens');
INSERT INTO vets VALUES (6, 'Sharon', 'Jenkins');

INSERT INTO specialties VALUES (1, 'radiology');
INSERT INTO specialties VALUES (2, 'surgery');
INSERT INTO specialties VALUES (3, 'dentistry');

INSERT INTO vet_specialties VALUES (2, 1);
INSERT INTO vet_specialties VALUES (3, 2);
INSERT INTO vet_specialties VALUES (3, 3);
INSERT INTO vet_specialties VALUES (4, 2);
INSERT INTO vet_specialties VALUES (5, 1);

INSERT INTO types VALUES (1, 'Twin');
INSERT INTO types VALUES (2, 'double twin');
INSERT INTO types VALUES (3, 'Queen');
INSERT INTO types VALUES (4, 'Double Queen');
INSERT INTO types VALUES (5, 'King');
INSERT INTO types VALUES (6, 'Double King');

INSERT INTO occupants VALUES (1, 'Leo', 'Divinci', '110 W. James St.', 'New York', '2175551026');
INSERT INTO occupants VALUES (2, 'Mike', 'Angelo', '724 Tough Ave.', 'New York', '2175551735');
INSERT INTO occupants VALUES (3, 'Dawn', 'Tellow', '7856 Beats St.', 'New York', '2175558729');
INSERT INTO occupants VALUES (4, 'Ralph', 'Eale', '456 Wacker St.', 'Peoria', '2175553185');
INSERT INTO occupants VALUES (5, 'April', 'Reporter', '7423 S. Sewer Way', 'Springfield', '2175552293');
INSERT INTO occupants VALUES (6, 'T.', 'Raat', '141 N. Acorn St.', 'New York', '2175552789');
INSERT INTO occupants VALUES (7, 'Bae', 'Bop', '100 Lake Blvd.', 'New York', '2175555473');
INSERT INTO occupants VALUES (8, 'Rocky', 'Steady', '345 Maple St.', 'Madison', '2175557109');
INSERT INTO occupants VALUES (9, 'Master', 'Chedder', '2749 Blackhawk Trail', 'Madison', '2175551234');
INSERT INTO occupants VALUES (10, 'Jones', 'Case', '2335 Independence La.', 'Carthage', '2175551789');

INSERT INTO rooms VALUES (1, '101', '2010-09-07', 1, 1);
INSERT INTO rooms VALUES (2, '102', '2012-08-06', 6, 2);
INSERT INTO rooms VALUES (3, '103', '2011-04-17', 2, 3);
INSERT INTO rooms VALUES (4, '104', '2010-03-07', 2, 3);
INSERT INTO rooms VALUES (5, '105', '2010-11-30', 3, 4);
INSERT INTO rooms VALUES (6, '106', '2010-01-20', 4, 5);
INSERT INTO rooms VALUES (7, '107', '2012-09-04', 1, 6);
INSERT INTO rooms VALUES (8, '108', '2012-09-04', 1, 6);
INSERT INTO rooms VALUES (9, '109', '2011-08-06', 5, 7);
INSERT INTO rooms VALUES (10, '110', '2007-02-24', 2, 8);
INSERT INTO rooms VALUES (11, '111', '2010-03-09', 5, 9);
INSERT INTO rooms VALUES (12, '112', '2010-06-24', 2, 10);
INSERT INTO rooms VALUES (13, 'Suite1', '2012-06-08', 1, 10);

INSERT INTO visits VALUES (1, 7, '2013-01-01', 'business');
INSERT INTO visits VALUES (2, 8, '2013-01-02', 'business');
INSERT INTO visits VALUES (3, 8, '2013-01-03', 'business');
INSERT INTO visits VALUES (4, 7, '2013-01-04', 'vacation');
