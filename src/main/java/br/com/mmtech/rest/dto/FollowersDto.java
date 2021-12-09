package br.com.mmtech.rest.dto;

import br.com.mmtech.domain.model.Follower;

public class FollowersDto {

    private Long id;
    private String name;

    public FollowersDto(Follower follower) {
        id = follower.getId();
        name = follower.getFollower().getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
