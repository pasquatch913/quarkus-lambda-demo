package com.pasquatch;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.Map;

@Data
@RegisterForReflection
public class User {

    private String name;
    private String description;

    public static User from(Map<String, AttributeValue> item) {
        User user = new User();
        if (item != null && !item.isEmpty()) {
            user.setName(item.get(AbstractUserMappingService.USER_NAME_COL).s());
            user.setDescription(item.get(AbstractUserMappingService.USER_DESC_COL).s());
        }
        return user;
    }
    
}
