package br.com.mmtech.rest;

import br.com.mmtech.rest.dto.CreateUserRequest;
import br.com.mmtech.rest.dto.ResponseError;
import br.com.mmtech.service.UserService;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Set;

import static br.com.mmtech.rest.dto.ResponseError.UNPROCESSABLE_ENTITY_STATUS;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("users")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class UserResource {

    private final UserService userService;
    private final Validator validator;

    @Inject
    public UserResource(UserService userService,
                        Validator validator) {
        this.userService = userService;
        this.validator = validator;
    }

    @POST
    public Response createUser(CreateUserRequest createUserRequest) {
        Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(createUserRequest);

        if (!violations.isEmpty()) {
            return ResponseError.createFromValidator(violations).withStatusCode(UNPROCESSABLE_ENTITY_STATUS);
        }

        return Response.status(Response.Status.CREATED.getStatusCode())
                .entity(userService.salvar(createUserRequest))
                .build();
    }

    @GET
    public Response getAllUsers() {
        return Response.ok(userService.getAll()).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteUser(@PathParam("id") Long id) {
        userService.delete(id);
        return Response.noContent().build();
    }

    @PUT
    @Path("{id}")
    public Response updateUser(@PathParam("id") Long id, CreateUserRequest createUserRequest) {
        return Response.ok(userService.update(id, createUserRequest)).build();
    }
}
