INSERT INTO groups (
                    group_name) VALUES
                                    ('FI-01'),
                                    ('AB-24'),
                                    ('MF-98');

INSERT INTO students (
                      first_name,
                      last_name,
                      group_id) VALUES
                                    ('John', 'Doe', 1),
                                    ('Alice', 'Smith',2 ),
                                    ('Bob', 'Johnson', 3),
                                    ('Eva', 'Williams', 1),
                                    ('Michael', 'Brown', 2);

INSERT INTO courses (course_name,
                     course_description) VALUES
                                               ('Math', 'Mathematics course'),
                                               ('Physics', 'Physics course'),
                                               ('History', 'History course'),
                                               ('Computer Science', 'Computer Science course');

INSERT INTO students_courses (student_id,
                              course_id) VALUES
                                               (1, 1),
                                               (1, 2),
                                               (2, 3),
                                               (3, 4);
