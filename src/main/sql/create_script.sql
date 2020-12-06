CREATE TYPE "OBJECT_STATE" AS ENUM ('IN_STOCK', 'SHARED');

CREATE TYPE "STATUS" AS ENUM ('OPEN', 'CANCELED', 'RESOLVED');

CREATE TABLE DORMITORY
(
    ID          SERIAL PRIMARY KEY,
    NAME        VARCHAR(32) NOT NULL,
    ADDRESS     VARCHAR(128),
    DESCRIPTION VARCHAR(256)
);

CREATE TABLE USERS
(
    ID            SERIAL PRIMARY KEY,
    USERNAME      VARCHAR(32) CHECK (CHAR_LENGTH(USERNAME) >= 5)  NOT NULL UNIQUE,
    NAME          VARCHAR(32)                                     NOT NULL,
    SURNAME       VARCHAR(32)                                     NOT NULL,
    EMAIL         VARCHAR(64)                                     NOT NULL,
    TELEPHONE     VARCHAR(12) CHECK (CHAR_LENGTH(TELEPHONE) = 12) NOT NULL,
    PASSWORD      VARCHAR(128)                                    NOT NULL,
    DORMITORY     INTEGER                                         REFERENCES DORMITORY (ID) ON UPDATE CASCADE ON DELETE SET NULL,
    RATING        REAL CHECK (RATING >= 0 AND RATING <= 5),
    IMAGE         BYTEA,
	ROLES         VARCHAR(128),
	PERMISSIONS   VARCHAR(128),
	ACTIVE        INTEGER
);

CREATE TABLE REVIEW
(
    ID            SERIAL PRIMARY KEY,
    CONTENT       VARCHAR(256),
    CREATION_DATE TIMESTAMP WITH TIME ZONE                                                               NOT NULL,
    AUTHOR        INTEGER REFERENCES USERS ON UPDATE CASCADE ON DELETE CASCADE CHECK (TARGET != AUTHOR) NOT NULL,
    TARGET        INTEGER REFERENCES USERS ON UPDATE CASCADE ON DELETE CASCADE CHECK (TARGET != AUTHOR) NOT NULL,
    RATING        REAL CHECK (RATING >= 0 AND RATING <= 5)                                               NOT NULL
);

CREATE TABLE OBJECT
(
    ID           SERIAL PRIMARY KEY,
    NAME         VARCHAR(32)                                                   NOT NULL,
    DESCRIPTION  VARCHAR(256)                                                  NOT NULL,
    IMAGE        BYTEA,
    OBJECT_STATE "OBJECT_STATE" DEFAULT 'IN_STOCK',
    USER_ID      INTEGER REFERENCES USERS ON UPDATE CASCADE ON DELETE CASCADE NOT NULL
);

CREATE TABLE OFFER
(
    ID            SERIAL PRIMARY KEY,
    NAME          VARCHAR(32)                                                   NOT NULL,
    DESCRIPTION   VARCHAR(256),
    STATUS        "STATUS" DEFAULT 'OPEN'                                       NOT NULL,
    CREATION_DATE TIMESTAMP WITH TIME ZONE                                      NOT NULL,
    AUTHOR        INTEGER REFERENCES USERS ON UPDATE CASCADE ON DELETE CASCADE NOT NULL
);

CREATE TABLE SUGGESTION
(
    ID            SERIAL PRIMARY KEY,
    NAME          VARCHAR(32) CHECK (CHAR_LENGTH(NAME) >= 6)                         NOT NULL,
    DESCRIPTION   VARCHAR(256),
    STATUS        "STATUS" DEFAULT 'OPEN'                                            NOT NULL,
    CREATION_DATE TIMESTAMP WITH TIME ZONE                                           NOT NULL,
    AUTHOR        INTEGER REFERENCES USERS (ID) ON UPDATE CASCADE ON DELETE CASCADE NOT NULL
);

CREATE TABLE SERVICE
(
    ID          SERIAL PRIMARY KEY,
    NAME        VARCHAR(256) CHECK (CHAR_LENGTH(NAME) >= 6)                        NOT NULL,
    IMAGE       BYTEA,
    DESCRIPTION VARCHAR(256),
    USER_ID     INTEGER REFERENCES USERS (ID) ON UPDATE CASCADE ON DELETE CASCADE NOT NULL
);

CREATE TABLE CATEGORY
(
    ID          SERIAL PRIMARY KEY,
    NAME        VARCHAR(32) NOT NULL,
    DESCRIPTION VARCHAR(256)
);

CREATE TABLE REQUEST
(
    ID          SERIAL PRIMARY KEY,
    NAME        VARCHAR(32) CHECK (CHAR_LENGTH(NAME) >= 6)                         NOT NULL,
    CONTENT     VARCHAR(256)                                                       NOT NULL,
    AUTHOR      INTEGER REFERENCES USERS (ID) ON UPDATE CASCADE ON DELETE CASCADE NOT NULL,
    AGREED_TIME TIMESTAMP WITH TIME ZONE                                           NOT NULL
);

CREATE TABLE CATEGORY_OF_OBJECT
(
    OBJECT_ID   INTEGER REFERENCES OBJECT ON UPDATE CASCADE ON DELETE CASCADE   NOT NULL,
    CATEGORY_ID INTEGER REFERENCES CATEGORY ON UPDATE CASCADE ON DELETE CASCADE NOT NULL,
    PRIMARY KEY (OBJECT_ID, CATEGORY_ID)
);

CREATE TABLE CATEGORY_OF_SERVICE
(
    SERVICE_ID  INTEGER REFERENCES SERVICE ON UPDATE CASCADE ON DELETE CASCADE  NOT NULL,
    CATEGORY_ID INTEGER REFERENCES CATEGORY ON UPDATE CASCADE ON DELETE CASCADE NOT NULL,
    PRIMARY KEY (SERVICE_ID, CATEGORY_ID)
);

CREATE TABLE OBJECT_SUGGESTION
(
    SUGGESTION INTEGER REFERENCES SUGGESTION ON UPDATE CASCADE ON DELETE CASCADE NOT NULL,
    OBJECT     INTEGER REFERENCES OBJECT ON UPDATE CASCADE ON DELETE CASCADE     NOT NULL,
    PRIMARY KEY (SUGGESTION, OBJECT)
);

CREATE TABLE OBJECT_OFFER_REQUEST
(
    REQUEST INTEGER REFERENCES REQUEST (ID) ON UPDATE CASCADE ON DELETE CASCADE NOT NULL,
    OFFER   INTEGER REFERENCES OFFER (ID) ON UPDATE CASCADE ON DELETE CASCADE   NOT NULL,
    OBJECT  INTEGER REFERENCES OBJECT (ID) ON UPDATE CASCADE ON DELETE CASCADE  NOT NULL,
    PRIMARY KEY (REQUEST, OFFER)
);

CREATE TABLE SERVICE_OFFER_REQUEST
(
    REQUEST INTEGER REFERENCES REQUEST (ID) ON UPDATE CASCADE ON DELETE CASCADE NOT NULL,
    OFFER   INTEGER REFERENCES OFFER (ID) ON UPDATE CASCADE ON DELETE CASCADE   NOT NULL,
    SERVICE INTEGER REFERENCES SERVICE (ID) ON UPDATE CASCADE ON DELETE CASCADE NOT NULL,
    PRIMARY KEY (REQUEST, OFFER)
);

CREATE TABLE SERVICE_SUGGESTION
(
    SUGGESTION INTEGER REFERENCES SUGGESTION (ID) ON UPDATE CASCADE ON DELETE CASCADE NOT NULL,
    SERVICE    INTEGER REFERENCES SERVICE (ID) ON UPDATE CASCADE ON DELETE CASCADE    NOT NULL,
    PRIMARY KEY (SUGGESTION, SERVICE)
);

CREATE TABLE COMMENT
(
    ID             SERIAL PRIMARY KEY,
    CONTENT        VARCHAR(256),
    CREATION_DATE  TIMESTAMP WITH TIME ZONE NOT NULL,
    AUTHOR         INTEGER                  REFERENCES USERS (ID) ON UPDATE CASCADE ON DELETE SET NULL,
    PARENT_COMMENT INTEGER REFERENCES COMMENT (ID) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE OFFER_COMMENT
(
    COMMENT INTEGER REFERENCES COMMENT (ID) ON UPDATE CASCADE ON DELETE CASCADE NOT NULL,
    OFFER   INTEGER REFERENCES OFFER (ID) ON UPDATE CASCADE ON DELETE CASCADE   NOT NULL,
    PRIMARY KEY (COMMENT, OFFER)
);

CREATE TABLE SUGGESTION_COMMENT
(
    COMMENT    INTEGER REFERENCES COMMENT (ID) ON UPDATE CASCADE ON DELETE CASCADE    NOT NULL,
    SUGGESTION INTEGER REFERENCES SUGGESTION (ID) ON UPDATE CASCADE ON DELETE CASCADE NOT NULL,
    PRIMARY KEY (COMMENT, SUGGESTION)
);

CREATE TABLE SUGGESTION_REQUEST
(
    REQUEST    INTEGER REFERENCES REQUEST (ID) ON UPDATE CASCADE ON DELETE CASCADE    NOT NULL,
    SUGGESTION INTEGER REFERENCES SUGGESTION (ID) ON UPDATE CASCADE ON DELETE CASCADE NOT NULL,
    PRIMARY KEY (REQUEST, SUGGESTION)
);

CREATE TABLE WHEN_CAN_DO
(
    ID             SERIAL PRIMARY KEY,
    DATETIME_START TIMESTAMP WITH TIME ZONE NOT NULL,
    DATETIME_END   TIMESTAMP WITH TIME ZONE NOT NULL,
    SUGGESTION     INTEGER                  NOT NULL,
    SERVICE        INTEGER                  NOT NULL,
    FOREIGN KEY (SUGGESTION, SERVICE) REFERENCES SERVICE_SUGGESTION (SUGGESTION, SERVICE) ON UPDATE CASCADE ON DELETE CASCADE
);