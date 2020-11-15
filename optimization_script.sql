-- <--- INDEXES --->

CREATE INDEX REVIEW_INDEX_TARGET ON REVIEW USING HASH(TARGET);
CREATE INDEX OBJECT_INDEX_USER_ID ON OBJECT USING HASH(USER_ID);
CREATE INDEX SERVICE_INDEX_USER_ID ON SERVICE USING HASH(USER_ID);
CREATE INDEX OFFER_INDEX_NAME ON OFFER USING BTREE(NAME);
CREATE INDEX SUGGESTION_INDEX_NAME ON SUGGESTION USING BTREE(NAME);
CREATE INDEX OFFER_INDEX_CREATION_DATE ON OFFER USING BTREE(CREATION_DATE);
CREATE INDEX SUGGESTION_INDEX_CREATION_DATE ON SUGGESTION USING BTREE(CREATION_DATE);
CREATE INDEX OFFER_COMMENT_INDEX ON OFFER_COMMENT USING HASH(OFFER);
CREATE INDEX SUGGESTION_COMMENT_INDEX ON SUGGESTION_COMMENT USING HASH(SUGGESTION);
CREATE INDEX WHEN_CAN_DO_INDEX_SERVICE_SUGGESTION ON WHEN_CAN_DO USING HASH(SERVICE_SUGGESTION);
CREATE INDEX OBJECT_OFFER_REQUEST_INDEX_OFFER ON OBJECT_OFFER_REQUEST USING HASH(OFFER);
CREATE INDEX SERVICE_OFFER_REQUEST_INDEX_OFFER ON SERVICE_OFFER_REQUEST USING HASH(OFFER);
CREATE INDEX SUGGESTION_REQUEST_INDEX_SUGGESTION ON SUGGESTION_REQUEST USING HASH(SUGGESTION);