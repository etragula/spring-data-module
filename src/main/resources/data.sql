-- Insert authors
INSERT INTO author (name) VALUES ('Robert C. Martin');
INSERT INTO author (name) VALUES ('Martin Kleppmann');
INSERT INTO author (name) VALUES ('Chris Richardson');
INSERT INTO author (name) VALUES ('Iuliana Cosmina');
INSERT INTO author (name) VALUES ('Alexander Pushkin');
INSERT INTO author (name) VALUES ('Vlad Khononov');
INSERT INTO author (name) VALUES ('Douglas Adams');
INSERT INTO author (name) VALUES ('Stanisław Lem');
INSERT INTO author (name) VALUES ('Stephen King');
INSERT INTO author (name) VALUES ('Stephen Chbosky');
INSERT INTO author (name) VALUES ('Henry Sharier');
INSERT INTO author (name) VALUES ('Leo Tolstoy');
INSERT INTO author (name) VALUES ('Ingrid Carlberg');
INSERT INTO author (name) VALUES ('J.R.R. Tolkien');
INSERT INTO author (name) VALUES ('Andrzej Sapkowski');
INSERT INTO author (name) VALUES ('George Martin');
INSERT INTO author (name) VALUES ('Agatha Christie');
INSERT INTO author (name) VALUES ('Fyodor Dostoevsky');
INSERT INTO author (name) VALUES ('Mikhail Bulgakov');
INSERT INTO author (name) VALUES ('Jack London');
INSERT INTO author (name) VALUES ('Gabriel García Márquez');
INSERT INTO author (name) VALUES ('Anthony Burgess');

-- Insert books
INSERT INTO books (name, language, category, author_id) VALUES ('Eugene Onegin', 'RUSSIAN', 'CLASSIC', (SELECT id FROM author a where a.name = 'Alexander Pushkin'));
INSERT INTO books (name, language, category, author_id) VALUES ('One Hundred Years of Solitude', 'SPANISH', 'CLASSIC', (SELECT id FROM author a where a.name = 'Gabriel García Márquez'));
INSERT INTO books (name, language, category, author_id) VALUES ('Crime and Punishment', 'RUSSIAN', 'CLASSIC', (SELECT id FROM author a where a.name = 'Fyodor Dostoevsky'));
INSERT INTO books (name, language, category, author_id) VALUES ('The Master and Margarita', 'RUSSIAN', 'CLASSIC', (SELECT id FROM author a where a.name = 'Mikhail Bulgakov'));
INSERT INTO books (name, language, category, author_id) VALUES ('Martin Eden', 'ENGLISH', 'CLASSIC', (SELECT id FROM author a where a.name = 'Jack London'));

INSERT INTO books (name, language, category, author_id) VALUES ('Murder on the Orient Express', 'ENGLISH', 'DETECTIVE', (SELECT id FROM author a where a.name = 'Agatha Christie'));
INSERT INTO books (name, language, category, author_id) VALUES ('And Then There Were None', 'ENGLISH', 'DETECTIVE', (SELECT id FROM author a where a.name = 'Agatha Christie'));

INSERT INTO books (name, language, category, author_id) VALUES ('The Lord of the Rings', 'ENGLISH', 'FANTASY', (SELECT id FROM author a where a.name = 'J.R.R. Tolkien'));
INSERT INTO books (name, language, category, author_id) VALUES ('The Witcher', 'POLISH', 'FANTASY', (SELECT id FROM author a where a.name = 'Andrzej Sapkowski'));
INSERT INTO books (name, language, category, author_id) VALUES ('A Game of Thrones', 'ENGLISH', 'FANTASY', (SELECT id FROM author a where a.name = 'George Martin'));

INSERT INTO books (name, language, category, author_id) VALUES ('Papillon', 'FRENCH', 'BIOGRAPHY', (SELECT id FROM author a where a.name = 'Henry Sharier'));
INSERT INTO books (name, language, category, author_id) VALUES ('Confession', 'RUSSIAN', 'BIOGRAPHY', (SELECT id FROM author a where a.name = 'Leo Tolstoy'));
INSERT INTO books (name, language, category, author_id) VALUES ('Nobel', 'GERMAN', 'BIOGRAPHY', (SELECT id FROM author a where a.name = 'Ingrid Carlberg'));

INSERT INTO books (name, language, category, author_id) VALUES ('The Stand', 'ENGLISH', 'HORROR', (SELECT id FROM author a where a.name = 'Stephen King'));
INSERT INTO books (name, language, category, author_id) VALUES ('Imaginary Friend', 'ENGLISH', 'HORROR', (SELECT id FROM author a where a.name = 'Stephen Chbosky'));
INSERT INTO books (name, language, category, author_id) VALUES ('The Dead Zone', 'ENGLISH', 'HORROR', (SELECT id FROM author a where a.name = 'Stephen King'));

INSERT INTO books (name, language, category, author_id) VALUES ('The Hitchhiker''s Guide to the Galaxy', 'ENGLISH', 'SCIENCE_FICTION', (SELECT id FROM author a where a.name = 'Douglas Adams'));
INSERT INTO books (name, language, category, author_id) VALUES ('Solaris', 'POLISH', 'SCIENCE_FICTION', (SELECT id FROM author a where a.name = 'Stanisław Lem'));
INSERT INTO books (name, language, category, author_id) VALUES ('A Clockwork Orange', 'ENGLISH', 'SCIENCE_FICTION', (SELECT id FROM author a where a.name = 'Anthony Burgess'));

INSERT INTO books (name, language, category, author_id) VALUES ('Clean Architecture', 'ENGLISH', 'IT', (SELECT id FROM author a where a.name = 'Robert C. Martin'));
INSERT INTO books (name, language, category, author_id) VALUES ('Designing Data-Intensive Applications', 'ENGLISH', 'IT', (SELECT id FROM author a where a.name = 'Martin Kleppmann'));
INSERT INTO books (name, language, category, author_id) VALUES ('Microservices Patterns', 'ENGLISH', 'IT', (SELECT id FROM author a where a.name = 'Chris Richardson'));
INSERT INTO books (name, language, category, author_id) VALUES ('Pro Spring 5', 'ENGLISH', 'IT', (SELECT id FROM author a where a.name = 'Iuliana Cosmina'));
INSERT INTO books (name, language, category, author_id) VALUES ('Learning Domain-Driven Design', 'ENGLISH', 'IT', (SELECT id FROM author a where a.name = 'Vlad Khononov'));
