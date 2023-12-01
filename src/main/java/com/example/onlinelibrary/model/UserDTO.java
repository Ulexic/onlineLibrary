package com.example.onlinelibrary.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private String password;
    private String email;
    private Long id;
    private String username;

    public UserDTO(Long id) {
        this.id = id;
    }

    public UserDTO(User user) {
        email = user.getEmail();
        id = user.getId();
        username = user.getUsername();
    }
}
