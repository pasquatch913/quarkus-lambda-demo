package com.pasquatch;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;

import java.util.HashMap;
import java.util.Map;

public class AbstractUserMappingService {

    public final static String USER_NAME_COL = "userName";
    public final static String USER_DESC_COL = "userDescription";

    public String getTableName() {
        return "Users";
    }

    protected ScanRequest scanRequest() {
        return ScanRequest.builder().tableName(getTableName())
                .attributesToGet(USER_NAME_COL, USER_DESC_COL).build();
    }

    protected PutItemRequest putRequest(User user) {
        Map<String, AttributeValue> item = new HashMap<>();
        item.put(USER_NAME_COL, AttributeValue.builder().s(user.getName()).build());
        item.put(USER_DESC_COL, AttributeValue.builder().s(user.getDescription()).build());

        return PutItemRequest.builder()
                .tableName(getTableName())
                .item(item)
                .build();
    }

    protected GetItemRequest getRequest(String name) {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put(USER_NAME_COL, AttributeValue.builder().s(name).build());

        return GetItemRequest.builder()
                .tableName(getTableName())
                .key(key)
                .attributesToGet(USER_NAME_COL, USER_DESC_COL)
                .build();
    }
    
}
