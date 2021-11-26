package com.pasquatch;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserSyncDataService extends AbstractUserMappingService {

    @Inject
    DynamoDbClient dynamoDB;

    public List<User> findAll() {
        return dynamoDB.scanPaginator(scanRequest()).items().stream()
                .map(User::from)
                .collect(Collectors.toList());
    }

    public List<User> add(User user) {
        dynamoDB.putItem(putRequest(user));
        return findAll();
    }

    public User get(String name) {
        return User.from(dynamoDB.getItem(getRequest(name)).item());
    }
    
    public void createUser(String userName) {
        User user = new User();
        user.setDescription("my new user");
        user.setName(userName);
        dynamoDB.putItem(putRequest(user));
    }
}
