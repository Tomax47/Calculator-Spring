package com.calculator.cal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserForm {
    @NotEmpty(message = "Email mustn't be empty!")
    @Email(message = "Invalid email format!")
    private String email;
    @NotEmpty(message = "Username mustn't be empty!")
    @Size(min = 4, max = 10, message = "Username should be 4-10 characters long")
    private String username;
    @NotEmpty(message = "Password mustn't be empty!")
    @Length(min = 6, message = "The password should be at least 6 characters long")
    private String password;
}
