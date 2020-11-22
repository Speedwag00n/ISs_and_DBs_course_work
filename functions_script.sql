CREATE OR REPLACE FUNCTION INSERT_OBJECT_SUGGESTION(NAME_SUGGESTION VARCHAR(32),
                                                    DESCRIPTION_SUGGESTION VARCHAR(256),
                                                    NAME_OBJECT VARCHAR(32),
                                                    DESCRIPTION_OBJECT VARCHAR(256),
                                                    AUTHOR INTEGER)
    RETURNS VOID AS
$$
DECLARE
    OBJECT_ID     INTEGER;
    SUGGESTION_ID INTEGER;
BEGIN
    INSERT INTO OBJECT (NAME, DESCRIPTION, USER_ID)
    VALUES (NAME_OBJECT, DESCRIPTION_OBJECT, AUTHOR)
    RETURNING id INTO OBJECT_ID;
    INSERT INTO SUGGESTION (NAME, DESCRIPTION, CREATION_DATE, AUTHOR)
    VALUES (NAME_SUGGESTION, DESCRIPTION_SUGGESTION, CURRENT_TIMESTAMP, AUTHOR)
    RETURNING id INTO SUGGESTION_ID;
    INSERT INTO OBJECT_SUGGESTION (SUGGESTION, OBJECT)
    VALUES (SUGGESTION_ID, OBJECT_ID);
END;
$$ LANGUAGE PLPGSQL;

CREATE OR REPLACE FUNCTION INSERT_SERVICE_SUGGESTION(NAME_SUGGESTION VARCHAR(32),
                                                     DESCRIPTION_SUGGESTION VARCHAR(256),
                                                     NAME_SERVICE VARCHAR(32),
                                                     DESCRIPTION_SERVICE VARCHAR(256),
                                                     AUTHOR INTEGER)
    RETURNS VOID AS
$$
DECLARE
    SERVICE_ID    INTEGER;
    SUGGESTION_ID INTEGER;
BEGIN
    INSERT INTO SERVICE (NAME, DESCRIPTION, USER_ID)
    VALUES (NAME_SERVICE, DESCRIPTION_SERVICE, AUTHOR)
    RETURNING id INTO SERVICE_ID;
    INSERT INTO SUGGESTION (NAME, DESCRIPTION, CREATION_DATE, AUTHOR)
    VALUES (NAME_SUGGESTION, DESCRIPTION_SUGGESTION, CURRENT_TIMESTAMP, AUTHOR)
    RETURNING id INTO SUGGESTION_ID;
    INSERT INTO SERVICE_SUGGESTION (SUGGESTION, SERVICE)
    VALUES (SUGGESTION_ID, SERVICE_ID);
END;
$$ LANGUAGE PLPGSQL;

CREATE OR REPLACE FUNCTION INSERT_SUGGESTION_REQUEST(NAME VARCHAR(32),
                                                     CONTENT VARCHAR(256),
                                                     AUTHOR INTEGER,
                                                     SUGGESTION INTEGER)
    RETURNS VOID AS
$$
DECLARE
    REQUEST_ID INTEGER;
BEGIN
    INSERT INTO REQUEST (NAME, CONTENT, AUTHOR)
    VALUES (NAME, CONTENT, AUTHOR)
    RETURNING id INTO REQUEST_ID;
    INSERT INTO SUGGESTION_REQUEST (SUGGESTION, REQUEST)
    VALUES (SUGGESTION, REQUEST_ID);
END;
$$ LANGUAGE PLPGSQL;

CREATE OR REPLACE FUNCTION INSERT_OBJECT_OFFER_REQUEST(NAME VARCHAR(32),
                                                       CONTENT VARCHAR(256),
                                                       AUTHOR INTEGER,
                                                       OFFER INTEGER,
                                                       OBJECT INTEGER)
    RETURNS VOID AS
$$
DECLARE
    REQUEST_ID INTEGER;
BEGIN
    INSERT INTO REQUEST (NAME, CONTENT, AUTHOR)
    VALUES (NAME, CONTENT, AUTHOR)
    RETURNING id INTO REQUEST_ID;
    INSERT INTO OBJECT_OFFER_REQUEST (REQUEST, OFFER, OBJECT)
    VALUES (REQUEST_ID, OFFER, OBJECT);
END;
$$ LANGUAGE PLPGSQL;

CREATE OR REPLACE FUNCTION INSERT_SERVICE_OFFER_REQUEST(NAME VARCHAR(32),
                                                        CONTENT VARCHAR(256),
                                                        AUTHOR INTEGER,
                                                        OFFER INTEGER,
                                                        SERVICE INTEGER)
    RETURNS VOID AS
$$
DECLARE
    REQUEST_ID INTEGER;
BEGIN
    INSERT INTO REQUEST (NAME, CONTENT, AUTHOR)
    VALUES (NAME, CONTENT, AUTHOR)
    RETURNING id INTO REQUEST_ID;
    INSERT INTO SERVICE_OFFER_REQUEST (REQUEST, OFFER, SERVICE)
    VALUES (REQUEST_ID, OFFER, SERVICE);
END;
$$ LANGUAGE PLPGSQL;

CREATE OR REPLACE FUNCTION INSERT_OFFER_COMMENT(CONTENT VARCHAR(256),
                                                AUTHOR INTEGER,
                                                PARENT_COMMENT INTEGER,
                                                OFFER INTEGER)
    RETURNS VOID AS
$$
DECLARE
    COMMENT_ID INTEGER;
BEGIN
    INSERT INTO COMMENT (CONTENT, CREATION_DATE, AUTHOR, PARENT_COMMENT)
    VALUES (CONTENT, CURRENT_TIMESTAMP, AUTHOR, PARENT_COMMENT)
    RETURNING id INTO COMMENT_ID;
    INSERT INTO OFFER_COMMENT (OFFER, COMMENT)
    VALUES (OFFER, COMMENT_ID);
END;
$$ LANGUAGE PLPGSQL;

CREATE OR REPLACE FUNCTION INSERT_SUGGESTION_COMMENT(CONTENT VARCHAR(256),
                                                     AUTHOR INTEGER,
                                                     PARENT_COMMENT INTEGER,
                                                     SUGGESTION INTEGER)
    RETURNS VOID AS
$$
DECLARE
    COMMENT_ID INTEGER;
BEGIN
    INSERT INTO COMMENT (CONTENT, CREATION_DATE, AUTHOR, PARENT_COMMENT)
    VALUES (CONTENT, CURRENT_TIMESTAMP, AUTHOR, PARENT_COMMENT)
    RETURNING id INTO COMMENT_ID;
    INSERT INTO SUGGESTION_COMMENT (SUGGESTION, COMMENT)
    VALUES (SUGGESTION, COMMENT_ID);
END;
$$ LANGUAGE PLPGSQL;



CREATE OR REPLACE FUNCTION GET_OFFERS_IN_DORMITORY(DORMITORY_ID INTEGER)
    RETURNS TABLE
            (
                ID            INTEGER,
                NAME          VARCHAR(32),
                DESCRIPTION   VARCHAR(256),
                STATUS        "STATUS",
                CREATION_DATE TIMESTAMP WITH TIME ZONE,
                AUTHOR        INTEGER
            )
AS
$$
BEGIN
    RETURN QUERY SELECT OFFER.ID,
                        OFFER.NAME,
                        OFFER.DESCRIPTION,
                        OFFER.STATUS,
                        OFFER.CREATION_DATE,
                        OFFER.AUTHOR
                 FROM "USER"
                          JOIN DORMITORY ON "USER".DORMITORY = DORMITORY.ID
                          JOIN OFFER ON OFFER.AUTHOR = "USER".ID
                 WHERE DORMITORY.ID = DORMITORY_ID;
END;
$$ LANGUAGE PLPGSQL;



CREATE OR REPLACE FUNCTION GET_SERVICE_SUGGESTIONS_IN_DORMITORY(DORMITORY_ID INTEGER)
    RETURNS TABLE
            (
                SUGGESTION_ID          INTEGER,
                SUGGESTION_NAME        VARCHAR(32),
                SUGGESTION_DESCRIPTION VARCHAR(256),
                STATUS                 "STATUS",
                CREATION_DATE          TIMESTAMP WITH TIME ZONE,
                AUTHOR                 INTEGER,
                SERVICE_ID             INTEGER,
                SERVICE_NAME           VARCHAR(32),
                SERVICE_DESCRIPTION    VARCHAR(256)
            )
AS
$$
BEGIN
    RETURN QUERY SELECT SUGGESTION.ID          as SUGGESTION_ID,
                        SUGGESTION.NAME        as SUGGESTION_NAME,
                        SUGGESTION.DESCRIPTION as SUGGESTION_DESCRIPTION,
                        SUGGESTION.STATUS,
                        SUGGESTION.CREATION_DATE,
                        SUGGESTION.AUTHOR,
                        SERVICE.ID             as SERVICE_ID,
                        SERVICE.NAME           as SERVICE_NAME,
                        SERVICE.DESCRIPTION    as SERVICE_DESCRIPTION
                 FROM "USER"
                          JOIN DORMITORY ON "USER".DORMITORY = DORMITORY.ID
                          JOIN SUGGESTION ON SUGGESTION.AUTHOR = "USER".ID
                          JOIN SERVICE_SUGGESTION ON SERVICE_SUGGESTION.SUGGESTION = SUGGESTION.ID
                          JOIN SERVICE ON SERVICE_SUGGESTION.SERVICE = SERVICE.ID
                 WHERE DORMITORY.ID = DORMITORY_ID;
END;
$$ LANGUAGE PLPGSQL;



CREATE OR REPLACE FUNCTION GET_OBJECT_SUGGESTIONS_IN_DORMITORY(DORMITORY_ID INTEGER)
    RETURNS TABLE
            (
                SUGGESTION_ID          INTEGER,
                SUGGESTION_NAME        VARCHAR(32),
                SUGGESTION_DESCRIPTION VARCHAR(256),
                STATUS                 "STATUS",
                CREATION_DATE          TIMESTAMP WITH TIME ZONE,
                AUTHOR                 INTEGER,
                OBJECT_ID              INTEGER,
                OBJECT_NAME            VARCHAR(32),
                OBJECT_DESCRIPTION     VARCHAR(256),
                OBJECT_STATE           "OBJECT_STATE"
            )
AS
$$
BEGIN
    RETURN QUERY SELECT SUGGESTION.ID          as SUGGESTION_ID,
                        SUGGESTION.NAME        as SUGGESTION_NAME,
                        SUGGESTION.DESCRIPTION as SUGGESTION_DESCRIPTION,
                        SUGGESTION.STATUS,
                        SUGGESTION.CREATION_DATE,
                        SUGGESTION.AUTHOR,
                        OBJECT.ID              as OBJECT_ID,
                        OBJECT.NAME            as OBJECT_NAME,
                        OBJECT.DESCRIPTION     as OBJECT_DESCRIPTION,
                        OBJECT.OBJECT_STATE    as OBJECT_STATE
                 FROM "USER"
                          JOIN DORMITORY ON "USER".DORMITORY = DORMITORY.ID
                          JOIN SUGGESTION ON SUGGESTION.AUTHOR = "USER".ID
                          JOIN OBJECT_SUGGESTION ON OBJECT_SUGGESTION.SUGGESTION = SUGGESTION.ID
                          JOIN OBJECT ON OBJECT_SUGGESTION.OBJECT = OBJECT.ID
                 WHERE DORMITORY.ID = DORMITORY_ID;
END;
$$ LANGUAGE PLPGSQL;

CREATE OR REPLACE FUNCTION FIND_RELATED_SERVICE(USER_ID_1 INTEGER, USER_ID_2 INTEGER)
    RETURNS TABLE
            (
                PRODUCER_ID      INTEGER,
                NAME_PRODUCER    VARCHAR(32),
                SURNAME_PRODUCER VARCHAR(32),
                CONSUMER_ID      INTEGER,
                NAME_CONSUMER    VARCHAR(32),
                SURNAME_CONSUMER VARCHAR(32),
                SUGGESTION_NAME  VARCHAR(32),
                AGREED_TIME      TIMESTAMP WITH TIME ZONE,
                CROSSES          bool
            )
AS
$$
BEGIN
    RETURN QUERY SELECT suggestion.author AS PRODUCER_ID,
                        (SELECT NAME FROM "USER" WHERE ID = SUGGESTION.AUTHOR),
                        (SELECT surname FROM "USER" WHERE ID = SUGGESTION.AUTHOR),
                        REQUEST.author    AS CONSUMER_ID,
                        (SELECT NAME FROM "USER" WHERE ID = REQUEST.author),
                        (SELECT surname FROM "USER" WHERE ID = REQUEST.author),
                        SUGGESTION.NAME,
                        REQUEST.agreed_time,
                        (SELECT IS_TIME_CROSSES(REQUEST.agreed_time, suggestion.author, REQUEST.author))
                 FROM "USER"
                          JOIN request ON "USER".id = request.author
                          JOIN suggestion_request ON request.id = suggestion_request.request
                          JOIN suggestion ON suggestion_request.suggestion = suggestion.id
                 WHERE ("USER".ID = USER_ID_1 OR "USER".ID = USER_ID_2)
                   AND (REQUEST.author = USER_ID_1 OR REQUEST.author = USER_ID_2)
                   AND (SUGGESTION.author = USER_ID_1 OR SUGGESTION.author = USER_ID_2);
END;
$$ LANGUAGE PLPGSQL;

CREATE OR REPLACE FUNCTION IS_TIME_CROSSES(agreed_time TIMESTAMP WITH TIME ZONE, PRODUCER_ID INTEGER,
                                      CONSUMER_ID INTEGER) RETURNS BOOLEAN
AS
$$
BEGIN
    RETURN agreed_time IN (SELECT REQUEST.agreed_time
                           FROM "USER"
                                    JOIN request ON "USER".id = request.author
                                    JOIN suggestion_request ON request.id = suggestion_request.request
                                    JOIN suggestion ON suggestion_request.suggestion = suggestion.id
                           WHERE ("USER".ID = PRODUCER_ID OR "USER".ID = CONSUMER_ID)
                             AND (REQUEST.author = PRODUCER_ID)
                             AND (SUGGESTION.author = CONSUMER_ID));
END;
$$ LANGUAGE PLPGSQL;
