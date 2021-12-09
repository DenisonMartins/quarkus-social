package br.com.mmtech.rest.dto;

import javax.validation.constraints.NotNull;

public class FollowerRequest {

    @NotNull(message = "Não pode ser nulo")
    private Long followerId;

    public Long getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Long followerId) {
        this.followerId = followerId;
    }
}
