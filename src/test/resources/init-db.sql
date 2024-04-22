-- Table Definition ----------------------------------------------

CREATE TABLE IF NOT EXISTS books (
    id BIGSERIAL PRIMARY KEY,
    name character varying(1023) NOT NULL,
    language character varying(1023) NOT NULL,
    category character varying(1023) NOT NULL,
    author character varying(1023) NOT NULL
);

-- Indices -------------------------------------------------------

CREATE UNIQUE INDEX IF NOT EXISTS books_pkey ON books(id);
CREATE UNIQUE INDEX IF NOT EXISTS book_author_uniqueness ON books(name, author);
