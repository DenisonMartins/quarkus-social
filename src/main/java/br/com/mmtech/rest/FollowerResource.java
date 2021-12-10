package br.com.mmtech.rest;

import br.com.mmtech.rest.dto.FollowerRequest;
import br.com.mmtech.rest.dto.ResponseError;
import br.com.mmtech.service.FollowerService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Set;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/users/{userId}/followers")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class FollowerResource {

    public static final int UNPROCESSABLE_ENTITY_STATUS = 422;
    private final FollowerService followerService;
    private final Validator validator;

    @Inject
    public FollowerResource(FollowerService followerService,
                            Validator validator) {
        this.followerService = followerService;
        this.validator = validator;
    }

    @PUT
    @Transactional
    public Response followUser(@PathParam("userId") Long userId, FollowerRequest followerRequest) {
        if (userId.equals(followerRequest.getFollowerId())) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Você não pode seguir a si mesmo.")
                    .build();
        }

        Set<ConstraintViolation<FollowerRequest>> violations = validator.validate(followerRequest);

        if (!violations.isEmpty()) {
            return ResponseError.createFromValidator(violations).withStatusCode(UNPROCESSABLE_ENTITY_STATUS);
        }

        followerService.followUser(userId, followerRequest);

        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    public Response getPosts(@PathParam("userId") Long userId) {
        return Response.ok(followerService.getAll(userId)).build();
    }

    @DELETE
    public Response unfollowUser(@PathParam("userId") Long userId, @QueryParam("followerId") Long followerId) {
        followerService.unfollower(userId, followerId);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
