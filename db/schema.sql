CREATE TABLE USERS(
  USER_ID NUMBER CONSTRAINT USERS_PK PRIMARY KEY,
  LOGIN VARCHAR2(100) UNIQUE,
  CURRENT_MASK NUMBER,
  LAST_LOGIN DATE,
  FAILED_LOGINS NUMBER DEFAULT 0 NOT NULL,
  FAILED_LOGIN_DATE DATE,
  MAX_LOGIN_ATT NUMBER DEFAULT 3 NOT NULL,
  CONSTRAINT USR_MAX_RANGE CHECK(MAX_LOGIN_ATT BETWEEN 3 AND 10)
);

CREATE TABLE PASSWORDS(
    PASS_ID NUMBER CONSTRAINT PASS_PK PRIMARY KEY,
    USER_ID NUMBER CONSTRAINT USERS_P__PK REFERENCES USERS(USER_ID),
    MASK VARCHAR2(16) NOT NULL,
    PASSWORD_HASH VARCHAR2(100)
);

ALTER TABLE USERS ADD CONSTRAINT PASSWORDS_FK FOREIGN KEY (CURRENT_MASK) REFERENCES PASSWORDS(PASS_ID);
CREATE TABLE BAD_USERS(
  USER_ID NUMBER CONSTRAINT BUSERS_PK PRIMARY KEY,
  LOGIN VARCHAR2(100) UNIQUE,
  FAILED_LOGINS NUMBER DEFAULT 0 NOT NULL,
  FAILED_LOGIN_DATE DATE,
  MAX_LOGIN_ATT NUMBER DEFAULT 3 NOT NULL,
  PASSWORD_MASK VARCHAR2(16) NOT NULL,
  CONSTRAINT BUSR_MAX_RANGE CHECK(MAX_LOGIN_ATT BETWEEN 3 AND 10)
);

CREATE TABLE MESSAGE(
  MESSAGE_ID NUMBER CONSTRAINT MESSAGE_PK PRIMARY KEY ,
  TEXT VARCHAR2(200),
  MOD NUMBER CONSTRAINT USERS_FK REFERENCES USERS(USER_ID)
);

CREATE TABLE ALLOWED_MESSAGES (
  MESSAGE_ID NUMBER CONSTRAINT MSG_ID_FK REFERENCES MESSAGE(MESSAGE_ID),
  USER_ID NUMBER CONSTRAINT USR_ID_FK REFERENCES USERS(USER_ID),
  CONSTRAINT ALL_MSG_PK PRIMARY KEY (MESSAGE_ID, USER_ID)
);

CREATE SEQUENCE users_seq
  MINVALUE 1
  MAXVALUE 9999999999
  START WITH 1
  INCREMENT BY 1;
  
  CREATE SEQUENCE pass_seq
  MINVALUE 1
  MAXVALUE 9999999999
  START WITH 1
  INCREMENT BY 1;
  
  CREATE SEQUENCE bad_users_seq
  MINVALUE 1
  MAXVALUE 9999999999
  START WITH 1
  INCREMENT BY 1;

CREATE SEQUENCE message_seq
  MINVALUE 1
  MAXVALUE 9999999999
  START WITH 1
  INCREMENT BY 1;