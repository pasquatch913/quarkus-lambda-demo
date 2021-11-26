package com.pasquatch;

import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Slf4j
@Path("/hello")
public class UserController {
    
    @Inject
    UserSyncDataService userSyncDataService;
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        
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
}
