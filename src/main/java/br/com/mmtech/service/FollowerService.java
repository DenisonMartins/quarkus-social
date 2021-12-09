package br.com.mmtech.service;

import br.com.mmtech.rest.dto.FollowerRequest;
import br.com.mmtech.rest.dto.FollowersPerUser;

public interface FollowerService {
    void followUser(Long userId, FollowerRequest followerRequest);

    FollowersPerUser getAll(Long userId);
}
