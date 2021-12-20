package br.com.mmtech.rest;

import br.com.mmtech.domain.model.User;
import br.com.mmtech.domain.repository.UserRepository;
import br.com.mmtech.rest.dto.CreatePost;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

@QuarkusTest
@TestHTTPEndpoint(PostResource.class)
@Transactional
class PostResourceTest {

    @Inject
    UserRepository userRepository;
    private Long userId;

    @BeforeEach
    void setUp() {
        var user = new User();
        user.setAge(33);
        user.setName("Fulano");
        userRepository.persist(user);
        userId = user.getId();
    }

    @Test
    @DisplayName("should create a post for a user")
    void createPostTest() {
        CreatePost createPost = new CreatePost();
        createPost.setText("Some text");

        given()
                .contentType(JSON)
                .body(createPost)
                .pathParam("userId", userId)
                .when()
                .post().then().statusCode(201);
    }

    @Test
    @DisplayName("should return 404 when trying to make a post for an inexistent user")
    void createPostForAnInexistentUserTest() {
        CreatePost createPost = new CreatePost();
        createPost.setText("Some text");

        var inexistentUser = 999;
        given()
                .contentType(JSON)
                .body(createPost)
                .pathParam("userId", inexistentUser)
                .when()
                .post().then().statusCode(500);
    }
}