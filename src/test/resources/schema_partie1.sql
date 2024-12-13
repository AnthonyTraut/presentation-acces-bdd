CREATE TABLE IF NOT EXISTS forum(
    id bigint PRIMARY KEY,
    titre varchar
);
CREATE SEQUENCE forum_sequence START 1;

CREATE TABLE IF NOT EXISTS post_details(
    id bigint PRIMARY KEY,
    auteur varchar,
    date_creation date,
    date_modification date
);
CREATE SEQUENCE post_details_sequence START 1;

CREATE TABLE IF NOT EXISTS tag(
    id bigint PRIMARY KEY,
    nom varchar
);
CREATE SEQUENCE tag_sequence START 1;

CREATE TABLE IF NOT EXISTS post(
    id bigint PRIMARY KEY,
    titre varchar,
    contenu varchar,
    post_details_id bigint REFERENCES post_details(id),
    forum_id bigint REFERENCES forum(id)
);
CREATE SEQUENCE post_sequence START 1;

CREATE TABLE IF NOT EXISTS commentaire(
    id bigint PRIMARY KEY,
    texte varchar,
    post_id bigint REFERENCES post(id)
);
CREATE SEQUENCE commentaire_sequence START 1;

CREATE TABLE IF NOT EXISTS post_tag(
    post_id bigint REFERENCES post(id),
    tag_id bigint REFERENCES tag(id),
    PRIMARY KEY (post_id, tag_id)
);