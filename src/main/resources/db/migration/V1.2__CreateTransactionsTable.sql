CREATE TABLE TRANSACTIONS
(
    ID BIGINT GENERATED BY DEFAULT AS IDENTITY,
    TRANSACTION_TYPE VARCHAR(255) NOT NULL,
    DATE_OF_TRANSACTION DATE NOT NULL,
    CURRENT_BALANCE NUMERIC(6,2) NOT NULL,
    ACCOUNT_NUMBER VARCHAR(255) REFERENCES ACCOUNTS (ACCOUNT_NUMBER),
    PRIMARY KEY (ID)
)