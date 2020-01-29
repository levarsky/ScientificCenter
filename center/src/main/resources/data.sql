INSERT INTO `naucna_c`.`magazine` (`id`, `issn`, `name`, `reader_pays`,`price`) VALUES (1, 'issn1', 'Casopis 1', b'1',20.0);
INSERT INTO `naucna_c`.`magazine` (`id`, `issn`, `name`, `reader_pays`,`price`) VALUES (2, 'issn2', 'Casopis 2', b'0',22.0);
INSERT INTO `naucna_c`.`magazine` (`id`, `issn`, `name`, `reader_pays`,`price`) VALUES (3, 'issn3', 'Casopis 3', b'1',37.0);
INSERT INTO `naucna_c`.`magazine` (`id`, `issn`, `name`, `reader_pays`,`price`) VALUES (4, 'issn4', 'Casopis 4', b'0',50.0);
INSERT INTO `naucna_c`.`magazine` (`id`, `issn`, `name`, `reader_pays`,`price`) VALUES (5, 'issn5', 'Casopis 5', b'1',11.0);
INSERT INTO `naucna_c`.`magazine` (`id`, `issn`, `name`, `reader_pays`,`price`) VALUES (6, 'issn6', 'Casopis 6', b'0',12.0);
INSERT INTO `naucna_c`.`magazine` (`id`, `issn`, `name`, `reader_pays`,`price`) VALUES (7, 'issn7', 'Casopis 7', b'1',15.5);
INSERT INTO `naucna_c`.`magazine` (`id`, `issn`, `name`, `reader_pays`,`price`) VALUES (8, 'issn8', 'Casopis 8', b'0',13.0);

INSERT INTO `naucna_c`.`scentific_area` (`id`, `name`) VALUES ('1', 'Area 1');
INSERT INTO `naucna_c`.`scentific_area` (`id`, `name`) VALUES ('2', 'Area 2');
INSERT INTO `naucna_c`.`scentific_area` (`id`, `name`) VALUES ('3', 'Area 3');
INSERT INTO `naucna_c`.`scentific_area` (`id`, `name`) VALUES ('4', 'Area 4');
INSERT INTO `naucna_c`.`scentific_area` (`id`, `name`) VALUES ('5', 'Area 5');
INSERT INTO `naucna_c`.`scentific_area` (`id`, `name`) VALUES ('6', 'Area 6');

INSERT INTO `naucna_c`.`magazine_scientific_area` (`magazine_list_id`, `scientific_area_id`) VALUES (1, 1);
INSERT INTO `naucna_c`.`magazine_scientific_area` (`magazine_list_id`, `scientific_area_id`) VALUES (1, 5);
INSERT INTO `naucna_c`.`magazine_scientific_area` (`magazine_list_id`, `scientific_area_id`) VALUES (2, 2);
INSERT INTO `naucna_c`.`magazine_scientific_area` (`magazine_list_id`, `scientific_area_id`) VALUES (3, 1);
INSERT INTO `naucna_c`.`magazine_scientific_area` (`magazine_list_id`, `scientific_area_id`) VALUES (3, 4);
INSERT INTO `naucna_c`.`magazine_scientific_area` (`magazine_list_id`, `scientific_area_id`) VALUES (4, 2);
INSERT INTO `naucna_c`.`magazine_scientific_area` (`magazine_list_id`, `scientific_area_id`) VALUES (4, 5);
INSERT INTO `naucna_c`.`magazine_scientific_area` (`magazine_list_id`, `scientific_area_id`) VALUES (5, 5);
INSERT INTO `naucna_c`.`magazine_scientific_area` (`magazine_list_id`, `scientific_area_id`) VALUES (6, 3);
INSERT INTO `naucna_c`.`magazine_scientific_area` (`magazine_list_id`, `scientific_area_id`) VALUES (6, 1);
INSERT INTO `naucna_c`.`magazine_scientific_area` (`magazine_list_id`, `scientific_area_id`) VALUES (7, 1);
INSERT INTO `naucna_c`.`magazine_scientific_area` (`magazine_list_id`, `scientific_area_id`) VALUES (8, 4);

INSERT INTO `naucna_c`.`user` (`id`, `city`, `country`, `email`, `first_name`, `last_name`, `title`) VALUES ('1', 'Novi Sad', 'Serbia', 'vmosorinski@gmail.com', 'Veljko', 'Mosorinski', 'Dipl. ing');
INSERT INTO `naucna_c`.`user` (`id`, `city`, `country`, `email`, `first_name`, `last_name`, `title`) VALUES ('2', 'Novi Sad', 'Serbia', 'mihajlol@gmail.com', 'Mihajlo', 'Levarski', 'Dipl. ing');
INSERT INTO `naucna_c`.`user` (`id`, `city`, `country`, `email`, `first_name`, `last_name`, `title`) VALUES ('3', 'Novi Sad', 'Serbia', 'milicamat@gmail.com', 'Milica', 'Matijevic', 'Dipl. ing');
INSERT INTO `naucna_c`.`user` (`id`, `city`, `country`, `email`, `first_name`, `last_name`, `title`) VALUES ('4', 'Belgrade', 'Serbia', 'djordjej@gmail.com', 'Djordje', 'Jovanovic', 'Dipl. ing');
INSERT INTO `naucna_c`.`user` (`id`, `city`, `country`, `email`, `first_name`, `last_name`, `title`) VALUES ('5', 'Nis', 'Serbia', 'peraperic@gmail.com', 'Petar', 'Peric', 'Dipl. ing');
INSERT INTO `naucna_c`.`user` (`id`, `city`, `country`, `email`, `first_name`, `last_name`, `title`) VALUES ('6', 'Kragujevac', 'Serbia', 'jmarkovic@gmail.com', 'Jovan', 'Markovic', 'Srednjoskolac');
INSERT INTO `naucna_c`.`user` (`id`, `city`, `country`, `email`, `first_name`, `last_name`, `title`) VALUES ('7', 'Jagodina', 'Serbia', 'lmartinovic@gmail.com', 'Lea', 'Martinovic', 'Hemicar');

INSERT INTO `naucna_c`.`user_magazine_redactor` (`redactors_id`, `magazine_redactor_id`) VALUES (1, 1);
INSERT INTO `naucna_c`.`user_magazine_redactor` (`redactors_id`, `magazine_redactor_id`) VALUES (2, 1);
INSERT INTO `naucna_c`.`user_magazine_redactor` (`redactors_id`, `magazine_redactor_id`) VALUES (3, 2);
INSERT INTO `naucna_c`.`user_magazine_redactor` (`redactors_id`, `magazine_redactor_id`) VALUES (5, 2);

INSERT INTO `naucna_c`.`user_magazine_reviewer` (`reviewer_id`, `magazine_reviewer_id`) VALUES (3, 1);
INSERT INTO `naucna_c`.`user_magazine_reviewer` (`reviewer_id`, `magazine_reviewer_id`) VALUES (4, 1);
INSERT INTO `naucna_c`.`user_magazine_reviewer` (`reviewer_id`, `magazine_reviewer_id`) VALUES (1, 2);
INSERT INTO `naucna_c`.`user_magazine_reviewer` (`reviewer_id`, `magazine_reviewer_id`) VALUES (2, 2);

INSERT INTO `naucna_c`.`publication` (`id`, `description`, `no`, `price`, `title`, `magazine_id`) VALUES ('1', 'Ovo je description od izdanja 1', 1, 5.0, 'Necete verovati sta se desilo!', 1);
INSERT INTO `naucna_c`.`publication` (`id`, `description`, `no`, `price`, `title`, `magazine_id`) VALUES ('2', 'Ovo je description od izdanja 2', 2, 6.0, 'Necete verovati sta se opet desilo!', 1);