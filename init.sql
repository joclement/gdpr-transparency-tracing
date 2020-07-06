
-- todo can remove user_id
CREATE TABLE highscore (
                           id UUID NOT NULL,
                           highscore numeric NOT NULL,

                           PRIMARY KEY (id)
);

INSERT INTO highscore
VALUES ('a70e80c3-0b95-4c6b-9c25-6bec186f75bc', '1'),
       ('a70e80c2-0b95-4c6b-9c25-6bec186f75bc', '2'),
       ('a70e80c1-0b95-4c6b-9c25-6bec186f75bc', '42');

CREATE TABLE score (
                           user_name varchar(256) NOT NULL,
                           score numeric NOT NULL,

                           PRIMARY KEY (user_name)
);
