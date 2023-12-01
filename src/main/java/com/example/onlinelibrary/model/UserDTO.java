package com.example.onlinelibrary.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.lang.Nullable;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    @Nullable
    private String password;
    @Nullable
    private String email;
    @Nullable
    private Long id;
    @Nullable
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
