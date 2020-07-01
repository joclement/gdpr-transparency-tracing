CREATE TABLE highscore (
                           user_id UUID NOT NULL,
                           highscore numeric NOT NULL,

                           PRIMARY KEY (user_id)
);

INSERT INTO highscore
VALUES ('a70e80c4-0b95-4c6b-9c25-6bec186f75bc','23');