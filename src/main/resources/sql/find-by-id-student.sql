SELECT s.id AS student_id, name, surname, location_id, country, city FROM student s JOIN location l ON (s.location_id = l.id) WHERE s.id = ?