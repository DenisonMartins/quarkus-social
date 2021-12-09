package br.com.mmtech.rest.dto;

import java.util.List;

public class FollowersPerUser {

    private Integer followersCount;
    private List<FollowersDto> content;

    public FollowersPerUser() {
    }

    public FollowersPerUser(List<FollowersDto> content) {
        followersCount = content.size();
        this.content = content;
    }

    public Integer getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(Integer followersCount) {
        this.followersCount = followersCount;
    }

    public List<FollowersDto> getContent() {
        return content;
    }

    public void setContent(List<FollowersDto> content) {
        this.content = content;
    }
}
