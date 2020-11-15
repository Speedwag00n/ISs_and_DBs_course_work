CREATE TRIGGER CHECK_STATUS
    BEFORE INSERT
    ON OBJECT_OFFER_REQUEST
    FOR EACH ROW
EXECUTE PROCEDURE check_status();

CREATE OR REPLACE FUNCTION check_status()
    RETURNS TRIGGER AS
$$
BEGIN
    SELECT O
    NEW.object
    INSERT INTO AUDIT(EMP_ID, ENTRY_DATE)
    VALUES (NEW.ID, current_timestamp);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;