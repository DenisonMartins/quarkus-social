package br.com.mmtech.service.impl;

import br.com.mmtech.domain.model.User;
import br.com.mmtech.domain.repository.UserRepository;
import br.com.mmtech.rest.dto.CreateUserRequest;
import br.com.mmtech.rest.dto.UserDto;
import br.com.mmtech.service.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Inject
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDto salvar(CreateUserRequest createUserRequest) {
        User user = new User();
        user.setName(createUserRequest.getName());
        user.setAge(createUserRequest.getAge());
        userRepository.persist(user);
        return new UserDto(user);
    }

    @Override
    public List<UserDto> getAll() {
        List<User> users = userRepository.findAll().list();
        return users.stream().map(UserDto::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        getById(id).ifPresent(userRepository::delete);
    }

    @Override
    @Transactional
    public UserDto update(Long id, CreateUserRequest createUserRequest) {
        Optional<User> user = getById(id);
        if (user.isPresent()) {
            user.get().setName(createUserRequest.getName());
            user.get().setAge(createUserRequest.getAge());
            return new UserDto(user.get());
        }
        throw new RuntimeException("Usuário não existe com id " + id);
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findByIdOptional(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não existe com id " + userId));
    }

    private Optional<User> getById(Long id) {
        return userRepository.findByIdOptional(id);
    }
}
