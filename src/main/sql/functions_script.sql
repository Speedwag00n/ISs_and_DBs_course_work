CREATE OR REPLACE FUNCTION GET_USER_OBJECTS(AUTHOR INTEGER)
    RETURNS TABLE
            (
                ID           INTEGER,
                NAME         VARCHAR(32),
                DESCRIPTION  VARCHAR(256),
                IMAGE        BYTEA,
                OBJECT_STATE "OBJECT_STATE",
                USER_ID      INTEGER
            )
AS
$$
BEGIN
    RETURN QUERY SELECT *
                 FROM object
                 WHERE user_id = AUTHOR;
END;
$$ LANGUAGE PLPGSQL;

CREATE OR REPLACE FUNCTION INSERT_OBJECT_SUGGESTION(NAME_SUGGESTION VARCHAR(32),
                                                    DESCRIPTION_SUGGESTION VARCHAR(256),
                                                    OBJECT_ID INTEGER,
                                                    AUTHOR_ID INTEGER)
    RETURNS INTEGER AS
$$
DECLARE
    SUGGESTION_ID INTEGER;
BEGIN
    INSERT INTO SUGGESTION (NAME, DESCRIPTION, CREATION_DATE, AUTHOR)
    VALUES (NAME_SUGGESTION, DESCRIPTION_SUGGESTION, CURRENT_TIMESTAMP, AUTHOR_ID)
    RETURNING id INTO SUGGESTION_ID;
    INSERT INTO OBJECT_SUGGESTION (ID, OBJECT)
    VALUES (SUGGESTION_ID, OBJECT_ID);
    RETURN 1;
END;
$$ LANGUAGE PLPGSQL;

CREATE OR REPLACE FUNCTION INSERT_SERVICE_SUGGESTION(NAME_SUGGESTION VARCHAR(32),
                                                     DESCRIPTION_SUGGESTION VARCHAR(256),
                                                     SERVICE_ID INTEGER,
                                                     AUTHOR_ID INTEGER)
    RETURNS INTEGER AS
$$
DECLARE
    SUGGESTION_ID INTEGER;
BEGIN
    INSERT INTO SUGGESTION (NAME, DESCRIPTION, CREATION_DATE, AUTHOR)
    VALUES (NAME_SUGGESTION, DESCRIPTION_SUGGESTION, CURRENT_TIMESTAMP, AUTHOR_ID)
    RETURNING id INTO SUGGESTION_ID;
    INSERT INTO SERVICE_SUGGESTION (ID, SERVICE)
    VALUES (SUGGESTION_ID, SERVICE_ID);
    RETURN 1;
END;
$$ LANGUAGE PLPGSQL;

CREATE OR REPLACE FUNCTION INSERT_SUGGESTION_REQUEST(NAME VARCHAR(32),
                                                     CONTENT VARCHAR(256),
                                                     AUTHOR INTEGER,
                                                     SUGGESTION INTEGER)
    RETURNS INTEGER AS
$$
DECLARE
    REQUEST_ID INTEGER;
BEGIN
    INSERT INTO REQUEST (NAME, CONTENT, AUTHOR)
    VALUES (NAME, CONTENT, AUTHOR)
    RETURNING id INTO REQUEST_ID;
    INSERT INTO SUGGESTION_REQUEST (SUGGESTION, REQUEST)
    VALUES (SUGGESTION, REQUEST_ID);
    RETURN 1;
END;
$$ LANGUAGE PLPGSQL;

CREATE OR REPLACE FUNCTION INSERT_OBJECT_OFFER_REQUEST(NAME VARCHAR(32),
                                                       CONTENT VARCHAR(256),
                                                       AUTHOR INTEGER,
                                                       OFFER INTEGER,
                                                       OBJECT INTEGER)
    RETURNS INTEGER AS
$$
DECLARE
    REQUEST_ID INTEGER;
BEGIN
    INSERT INTO REQUEST (NAME, CONTENT, AUTHOR)
    VALUES (NAME, CONTENT, AUTHOR)
    RETURNING id INTO REQUEST_ID;
    INSERT INTO OBJECT_OFFER_REQUEST (REQUEST, OFFER, OBJECT)
    VALUES (REQUEST_ID, OFFER, OBJECT);
    RETURN 1;
END;
$$ LANGUAGE PLPGSQL;

CREATE OR REPLACE FUNCTION INSERT_SERVICE_OFFER_REQUEST(NAME VARCHAR(32),
                                                        CONTENT VARCHAR(256),
                                                        AUTHOR INTEGER,
                                                        OFFER INTEGER,
                                                        SERVICE INTEGER)
    RETURNS INTEGER AS
$$
DECLARE
    REQUEST_ID INTEGER;
BEGIN
    INSERT INTO REQUEST (NAME, CONTENT, AUTHOR)
    VALUES (NAME, CONTENT, AUTHOR)
    RETURNING id INTO REQUEST_ID;
    INSERT INTO SERVICE_OFFER_REQUEST (REQUEST, OFFER, SERVICE)
    VALUES (REQUEST_ID, OFFER, SERVICE);
    RETURN 1;
END;
$$ LANGUAGE PLPGSQL;

CREATE OR REPLACE FUNCTION INSERT_OFFER_COMMENT(CONTENT VARCHAR(256),
                                                AUTHOR INTEGER,
                                                PARENT_COMMENT INTEGER,
                                                OFFER INTEGER)
    RETURNS INTEGER AS
$$
DECLARE
    COMMENT_ID INTEGER;
BEGIN
    INSERT INTO COMMENT (CONTENT, CREATION_DATE, AUTHOR, PARENT_COMMENT)
    VALUES (CONTENT, CURRENT_TIMESTAMP, AUTHOR, PARENT_COMMENT)
    RETURNING id INTO COMMENT_ID;
    INSERT INTO OFFER_COMMENT (OFFER, COMMENT)
    VALUES (OFFER, COMMENT_ID);
    RETURN 1;
END;
$$ LANGUAGE PLPGSQL;

CREATE OR REPLACE FUNCTION INSERT_SUGGESTION_COMMENT(CONTENT VARCHAR(256),
                                                     AUTHOR INTEGER,
                                                     PARENT_COMMENT INTEGER,
                                                     SUGGESTION INTEGER)
    RETURNS INTEGER AS
$$
DECLARE
    COMMENT_ID INTEGER;
BEGIN
    INSERT INTO COMMENT (CONTENT, CREATION_DATE, AUTHOR, PARENT_COMMENT)
    VALUES (CONTENT, CURRENT_TIMESTAMP, AUTHOR, PARENT_COMMENT)
    RETURNING id INTO COMMENT_ID;
    INSERT INTO SUGGESTION_COMMENT (SUGGESTION, COMMENT)
    VALUES (SUGGESTION, COMMENT_ID);
    RETURN 1;
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
                 FROM USERS
                          JOIN DORMITORY ON USERS.DORMITORY = DORMITORY.ID
                          JOIN OFFER ON OFFER.AUTHOR = USERS.ID
                 WHERE DORMITORY.ID = DORMITORY_ID AND OFFER.STATUS = 'OPEN';
END;
$$ LANGUAGE PLPGSQL;



CREATE OR REPLACE FUNCTION GET_SERVICE_SUGGESTIONS_IN_DORMITORY(DORMITORY_ID INTEGER)
    RETURNS TABLE
            (
                ID          INTEGER,
                NAME        VARCHAR(32),
                DESCRIPTION VARCHAR(256),
                STATUS                 "STATUS",
                CREATION_DATE          TIMESTAMP WITH TIME ZONE,
                AUTHOR                 INTEGER,
                SERVICE                INTEGER
            )
AS
$$
BEGIN
    RETURN QUERY SELECT SUGGESTION.ID          as ID,
                        SUGGESTION.NAME        as SUGGESTION_NAME,
                        SUGGESTION.DESCRIPTION as SUGGESTION_DESCRIPTION,
                        SUGGESTION.STATUS,
                        SUGGESTION.CREATION_DATE,
                        SUGGESTION.AUTHOR,
                        SERVICE.ID             as SERVICE
                 FROM USERS
                          JOIN DORMITORY ON USERS.DORMITORY = DORMITORY.ID
                          JOIN SUGGESTION ON SUGGESTION.AUTHOR = USERS.ID
                          JOIN SERVICE_SUGGESTION ON SERVICE_SUGGESTION.ID = SUGGESTION.ID
                          JOIN SERVICE ON SERVICE_SUGGESTION.SERVICE = SERVICE.ID
                 WHERE DORMITORY.ID = DORMITORY_ID AND SUGGESTION.STATUS = 'OPEN';
END;
$$ LANGUAGE PLPGSQL;



CREATE OR REPLACE FUNCTION GET_OBJECT_SUGGESTIONS_IN_DORMITORY(DORMITORY_ID INTEGER)
    RETURNS TABLE
            (
                ID          INTEGER,
                NAME        VARCHAR(32),
                DESCRIPTION VARCHAR(256),
                STATUS                 "STATUS",
                CREATION_DATE          TIMESTAMP WITH TIME ZONE,
                AUTHOR                 INTEGER,
                OBJECT                 INTEGER
            )
AS
$$
BEGIN
    RETURN QUERY SELECT SUGGESTION.ID          as ID,
                        SUGGESTION.NAME        as SUGGESTION_NAME,
                        SUGGESTION.DESCRIPTION as SUGGESTION_DESCRIPTION,
                        SUGGESTION.STATUS,
                        SUGGESTION.CREATION_DATE,
                        SUGGESTION.AUTHOR,
                        OBJECT.ID              as OBJECT
                 FROM USERS
                          JOIN DORMITORY ON USERS.DORMITORY = DORMITORY.ID
                          JOIN SUGGESTION ON SUGGESTION.AUTHOR = USERS.ID
                          JOIN OBJECT_SUGGESTION ON OBJECT_SUGGESTION.ID = SUGGESTION.ID
                          JOIN OBJECT ON OBJECT_SUGGESTION.OBJECT = OBJECT.ID
                 WHERE DORMITORY.ID = DORMITORY_ID AND SUGGESTION.STATUS = 'OPEN';
END;
$$ LANGUAGE PLPGSQL;

--ADDITIONAL TASK

CREATE OR REPLACE FUNCTION IS_TIME_CROSSES(agreed_time TIMESTAMP WITH TIME ZONE, PRODUCER_ID INTEGER,
                                           CONSUMER_ID INTEGER) RETURNS BOOLEAN
AS
$$
BEGIN
    RETURN agreed_time IN (SELECT REQUEST.agreed_time
                           FROM USERS
                                    JOIN request ON USERS.id = request.author
                                    JOIN suggestion_request ON request.id = suggestion_request.request
                                    JOIN suggestion ON suggestion_request.suggestion = suggestion.id
                           WHERE (USERS.ID = PRODUCER_ID OR USERS.ID = CONSUMER_ID)
                             AND (REQUEST.author = PRODUCER_ID)
                             AND (SUGGESTION.author = CONSUMER_ID));
END;
$$ LANGUAGE PLPGSQL;

CREATE OR REPLACE FUNCTION FIND_RELATED_SERVICE_FOR_TWO_USERS(USER_ID_1 INTEGER, USER_ID_2 INTEGER)
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
    RETURN QUERY SELECT suggestion.author,
                        (SELECT NAME FROM USERS WHERE ID = SUGGESTION.AUTHOR),
                        (SELECT surname FROM USERS WHERE ID = SUGGESTION.AUTHOR),
                        REQUEST.author,
                        (SELECT NAME FROM USERS WHERE ID = REQUEST.author),
                        (SELECT surname FROM USERS WHERE ID = REQUEST.author),
                        SUGGESTION.NAME,
                        REQUEST.agreed_time,
                        (SELECT IS_TIME_CROSSES(REQUEST.agreed_time, suggestion.author, REQUEST.author))
                 FROM USERS
                          JOIN request ON USERS.id = request.author
                          JOIN suggestion_request ON request.id = suggestion_request.request
                          JOIN suggestion ON suggestion_request.suggestion = suggestion.id
                 WHERE (USERS.ID = USER_ID_1 OR USERS.ID = USER_ID_2)
                   AND (REQUEST.author = USER_ID_1 OR REQUEST.author = USER_ID_2)
                   AND (SUGGESTION.author = USER_ID_1 OR SUGGESTION.author = USER_ID_2);
END;
$$ LANGUAGE PLPGSQL;

CREATE OR REPLACE FUNCTION FIND_RELATED_SERVICE_FOR_THREE_USERS(USER_ID_1 INTEGER, USER_ID_2 INTEGER, USER_ID_3 INTEGER)
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
    RETURN QUERY SELECT suggestion.author,
                        (SELECT NAME FROM USERS WHERE ID = SUGGESTION.AUTHOR),
                        (SELECT surname FROM USERS WHERE ID = SUGGESTION.AUTHOR),
                        REQUEST.author,
                        (SELECT NAME FROM USERS WHERE ID = REQUEST.author),
                        (SELECT surname FROM USERS WHERE ID = REQUEST.author),
                        SUGGESTION.NAME,
                        REQUEST.agreed_time,
                        (SELECT IS_TIME_CROSSES(REQUEST.agreed_time, suggestion.author, REQUEST.author))
                 FROM USERS
                          JOIN request ON USERS.id = request.author
                          JOIN suggestion_request ON request.id = suggestion_request.request
                          JOIN suggestion ON suggestion_request.suggestion = suggestion.id
                 WHERE (USERS.ID = USER_ID_1 OR USERS.ID = USER_ID_2 OR USERS.ID = USER_ID_3)
                   AND (REQUEST.author = USER_ID_1 OR REQUEST.author = USER_ID_2 OR REQUEST.author = USER_ID_3)
                   AND (SUGGESTION.author = USER_ID_1 OR SUGGESTION.author = USER_ID_2 OR
                        SUGGESTION.author = USER_ID_3);
END;
$$ LANGUAGE PLPGSQL;
