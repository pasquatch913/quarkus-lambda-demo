package com.pasquatch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.amazon.lambda.http.model.AwsProxyRequest;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@Path("/hello")
public class UserController {

    private final UserSyncDataService userSyncDataService;
    private final ObjectMapper objectMapper;
    
    UserController(ObjectMapperConfig objectMapperConfig, UserSyncDataService dataService) {
        this.objectMapper = objectMapperConfig.getObjectMapper();
        this.userSyncDataService = dataService;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        log.info("Responding to simple web request!");
        return "hello jaxrs";
    }

    @POST
    @Path("/user/{username}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createUser(@PathParam("username") String username) {
        log.info("creating user with username {}", username);
        userSyncDataService.createUser(username);
        return Response.ok().build();
    }

    @GET
    @Path("/user/{username}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getuser(@PathParam("username") String username) {
        log.info("getting user with username {}", username);
        return Response.ok(userSyncDataService.get(username)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/test")
    public Response test(@Context AwsProxyRequest request) throws JsonProcessingException {
//        NOTE: request body is base64 encoded json

        byte[] unencodedBytes = Base64.getDecoder().decode(request.getBody());
        String requestString = new String(unencodedBytes, StandardCharsets.UTF_8);
        SimpleRequest simpleRequest = this.objectMapper.readValue(requestString, SimpleRequest.class);
        log.info("handling request: {} with header {}", simpleRequest, request.getMultiValueHeaders());
        return Response.ok(simpleRequest).build();
    }
}
