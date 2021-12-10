package br.com.mmtech.service;

import br.com.mmtech.rest.dto.CreatePost;
import br.com.mmtech.rest.dto.PostDto;

import java.util.List;

public interface PostService {
    PostDto savePost(Long userId, CreatePost createPost);

    List<PostDto> getAll(Long userId, Long followerId);
}
