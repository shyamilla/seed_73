package com.htc.trainingmanagement.dto.request;

import java.util.Set;

import com.htc.trainingmanagement.annotation.ValidEmail;
import com.htc.trainingmanagement.enums.RoleName;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    @NotBlank(message = "User Name must not be blank")
    @Size(min = 3, max = 25, message = "User Name must be between 3 and 25 characters")
    private String userName;
    @NotBlank(message = "email is required")
    @Email(message = "Email must be valid")
    @ValidEmail
    private String email;
    @NotBlank(message = "password must not  be empty")
    // @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$",
    // message = "Password must contain at least 8 characters, one uppercase letter,
    // one lowercase letter, one digit and one special character")
    private String password;
    private Set<RoleName> roles;
}
