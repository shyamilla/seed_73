package com.htc.trainingmanagement.dto.request;

import java.util.Set;

import com.htc.trainingmanagement.annotation.ValidEmail;
import com.htc.trainingmanagement.enums.RoleName;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ValidEmail
public class UserRequestDto {

    @NotBlank(message = "User name must not be blank")
    @Size(min = 3, max = 25, message = "User name must be between 3 and 25 characters")
    private String userName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$", message = "Password must contain atleast 8 characters 1 upper case 1 lower case 1 digit and 1 special character")
    @NotBlank(message = "Password must not be empty")
    private String password;

    @NotEmpty(message = "At least one role is required")
    private Set<RoleName> roles;
}
