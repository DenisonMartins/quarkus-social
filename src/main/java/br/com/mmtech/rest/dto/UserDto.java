package br.com.mmtech.rest.dto;

import br.com.mmtech.domain.model.User;

public class UserDto {

    private Long id;
    private String name;
    private Integer age;

    public UserDto(User user) {
        id = user.getId();
        name = user.getName();
        age = user.getAge();
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
