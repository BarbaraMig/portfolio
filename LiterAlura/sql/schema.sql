CREATE TABLE autor (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    ano_nascimento INT,
    ano_falecimento INT,
    idiomas text
);

CREATE TABLE livro (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT,
    idiomas TEXT[] -- campo de array de strings
);

CREATE TABLE livro_autor (
    livro_id INT NOT NULL REFERENCES livro(id) ON DELETE CASCADE,
    autor_id INT NOT NULL REFERENCES autor(id) ON DELETE CASCADE,
    PRIMARY KEY (livro_id, autor_id)
);
