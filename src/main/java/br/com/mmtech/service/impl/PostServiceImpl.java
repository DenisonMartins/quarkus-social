package br.com.mmtech.service.impl;

import br.com.mmtech.domain.model.Post;
import br.com.mmtech.domain.model.User;
import br.com.mmtech.domain.repository.FollowerRepository;
import br.com.mmtech.domain.repository.PostRepository;
import br.com.mmtech.rest.dto.CreatePost;
import br.com.mmtech.rest.dto.PostDto;
import br.com.mmtech.service.PostService;
import br.com.mmtech.service.UserService;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ApplicationScoped
public class PostServiceImpl implements PostService {

    private final UserService userService;
    private final PostRepository postRepository;
    private final FollowerRepository followerRepository;

    @Inject
    public PostServiceImpl(UserService userService,
                           PostRepository postRepository,
                           FollowerRepository followerRepository) {
        this.userService = userService;
        this.postRepository = postRepository;
        this.followerRepository = followerRepository;
    }

    @Override
    @Transactional
    public PostDto savePost(Long userId, CreatePost createPost) {
        User user = getUser(userId);
        Post post = new Post();
        post.setText(createPost.getText());
        post.setUser(user);
        postRepository.persist(post);
        return new PostDto(post);
    }

    @Override
    public List<PostDto> getAll(Long userId, Long followerId) {
        User user = getUser(userId);

        if (Objects.isNull(followerId)) {
            throw new RuntimeException("Você esqueceu do header followerId");
        }

        User follower = getUser(followerId);
        boolean follows = followerRepository.follows(follower, user);
        if (!follows) {
            throw new RuntimeException("Você não consegue ver esses posts");
        }

        PanacheQuery<Post> query = postRepository.find("user", Sort.descending("dataCriacao"), user);
        return query.list().stream().map(PostDto::new).collect(Collectors.toList());
    }

    private User getUser(Long userId) {
        return userService.findById(userId);
    }
}
