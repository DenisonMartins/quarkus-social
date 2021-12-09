package br.com.mmtech.service.impl;

import br.com.mmtech.domain.model.Follower;
import br.com.mmtech.domain.model.User;
import br.com.mmtech.domain.repository.FollowerRepository;
import br.com.mmtech.rest.dto.FollowerRequest;
import br.com.mmtech.rest.dto.FollowersDto;
import br.com.mmtech.rest.dto.FollowersPerUser;
import br.com.mmtech.service.FollowerService;
import br.com.mmtech.service.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class FollowerServiceImpl implements FollowerService {

    private final UserService userService;
    private final FollowerRepository followerRepository;

    @Inject
    public FollowerServiceImpl(UserService userService,
                               FollowerRepository followerRepository) {

        this.userService = userService;
        this.followerRepository = followerRepository;
    }

    @Override
    public void followUser(Long userId, FollowerRequest followerRequest) {
        User user = userService.findById(userId);
        User follower = userService.findById(followerRequest.getFollowerId());

        if (!isFollows(user, follower)) {
            Follower follow = new Follower();
            follow.setUser(user);
            follow.setFollower(follower);
            followerRepository.persist(follow);
        }

    }

    @Override
    public FollowersPerUser getAll(Long userId) {
        verificaSeUsuarioExiste(userId);

        List<FollowersDto> content = followerRepository.findAllByUser(userId)
                .stream().map(FollowersDto::new)
                .collect(Collectors.toList());
        return new FollowersPerUser(content);
    }

    private void verificaSeUsuarioExiste(Long userId) {
        userService.findById(userId);
    }

    private boolean isFollows(User user, User follower) {
        return followerRepository.follows(follower, user);
    }
}
