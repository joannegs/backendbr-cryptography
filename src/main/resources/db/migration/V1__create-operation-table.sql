CREATE TABLE  operations
(
    id              SERIAL PRIMARY KEY UNIQUE NOT NULL,
    userDocument    TEXT                      NOT NULL,
    creditCardToken TEXT                      NOT NULL,
    amount          BIGINT                    NOT NULL
)