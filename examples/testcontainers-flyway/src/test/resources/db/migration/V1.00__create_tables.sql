CREATE TABLE films (
    title       varchar(40) NOT NULL,
    date_prod   date,
    kind        varchar(10),
    len         integer
);

CREATE TABLE distributors (
     name   varchar(40) NOT NULL CHECK (name <> '')
);
