CREATE INDEX USER_INDEX_DORMITORY ON "USER" USING HASH(DORMITORY);
CREATE INDEX REVIEW_INDEX_TARGET ON REVIEW USING HASH(TARGET);
CREATE INDEX OBJECT_INDEX_USER_ID ON OBJECT USING HASH(USER_ID);
CREATE INDEX SERVICE_INDEX_USER_ID ON SERVICE USING HASH(USER_ID);
CREATE INDEX OFFER_INDEX_NAME ON OFFER USING BTREE(NAME);
CREATE INDEX SUGGESTION_INDEX_NAME ON SUGGESTION USING BTREE(NAME);
CREATE INDEX OFFER_INDEX_CREATION_DATE ON OFFER USING BTREE(CREATION_DATE);
CREATE INDEX SUGGESTION_INDEX_CREATION_DATE ON SUGGESTION USING BTREE(CREATION_DATE);
CREATE INDEX WHEN_CAN_DO_INDEX_SERVICE_SUGGESTION ON WHEN_CAN_DO USING BTREE(SUGGESTION,SERVICE);
CREATE INDEX OBJECT_OFFER_REQUEST_INDEX_OFFER ON OBJECT_OFFER_REQUEST USING HASH(OFFER);
CREATE INDEX SERVICE_OFFER_REQUEST_INDEX_OFFER ON SERVICE_OFFER_REQUEST USING HASH(OFFER);
CREATE INDEX SUGGESTION_REQUEST_INDEX_SUGGESTION ON SUGGESTION_REQUEST USING HASH(SUGGESTION);