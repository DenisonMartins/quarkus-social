package br.com.mmtech.rest;

import br.com.mmtech.rest.dto.CreatePost;
import br.com.mmtech.rest.dto.ResponseError;
import br.com.mmtech.service.PostService;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Set;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/users/{userId}/posts")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class PostResource {

    public static final int UNPROCESSABLE_ENTITY_STATUS = 422;
    private final PostService postService;
    private final Validator validator;

    @Inject
    public PostResource(PostService postService,
                        Validator validator) {
        this.postService = postService;
        this.validator = validator;
    }

    @POST
    public Response savePost(@PathParam("userId") Long userId, CreatePost createPost) {
        Set<ConstraintViolation<CreatePost>> violations = validator.validate(createPost);

        if (!violations.isEmpty()) {
            return ResponseError.createFromValidator(violations).withStatusCode(UNPROCESSABLE_ENTITY_STATUS);
        }

        return Response.status(Response.Status.CREATED.getStatusCode())
                .entity(postService.savePost(userId, createPost))
                .build();
    }

    @GET
    public Response getPosts(@PathParam("userId") Long userId, @HeaderParam("followerId") Long followerId) {
        return Response.ok(postService.getAll(userId, followerId)).build();
    }
}
