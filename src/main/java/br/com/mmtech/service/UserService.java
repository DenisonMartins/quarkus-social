package br.com.mmtech.service;

import br.com.mmtech.domain.model.User;
import br.com.mmtech.rest.dto.CreateUserRequest;
import br.com.mmtech.rest.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto salvar(CreateUserRequest createUserRequest);

    List<UserDto> getAll();

    void delete(Long id);

    UserDto update(Long id, CreateUserRequest createUserRequest);

    User findById(Long userId);
}
