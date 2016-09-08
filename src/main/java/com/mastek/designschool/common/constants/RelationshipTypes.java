package com.mastek.designschool.common.constants;

import org.neo4j.graphdb.RelationshipType;

public enum RelationshipTypes implements RelationshipType {
    IS_FRIEND_OF,
    HAS_ACCOUNT,
    HAS_PHONE_NO,
    HAS_EMAIL_ADDR,
    WORKS_FOR,
    STAYS_AT,
    HAS_SEEN, HAS_POSTCODE, HAS_ACCOUNT_IN, HAS_PIN, HAS_ACCOUNTNO;
}