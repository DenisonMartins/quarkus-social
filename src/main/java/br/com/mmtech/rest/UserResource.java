package br.com.mmtech.rest;

import br.com.mmtech.domain.model.User;
import br.com.mmtech.rest.dto.CreateUserRequest;
import br.com.mmtech.service.UserService;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import java.util.Optional;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("users")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @POST
    public Response createUser(CreateUserRequest createUserRequest) {
        return Response.ok(userService.salvar(createUserRequest)).build();
    }

    @GET
    public Response getAllUsers() {
        return Response.ok(userService.getAll()).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteUser(@PathParam("id") Long id) {
        userService.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @PUT
    @Path("{id}")
    public Response updateUser(@PathParam("id") Long id, CreateUserRequest createUserRequest) {
        return Response.ok(userService.update(id, createUserRequest)).build();
    }
}
