package com.project.library.dto;

import com.project.library.model.City;
import com.project.library.model.Order;
import com.project.library.model.ShoppingCart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    @Size(min = 3, max = 10, message = "First name must be between 3 - 10 characters")
    private String firstName;

    @Size(min = 3, max = 10, message = "Last name must be between 3 - 10 characters")
    private String lastName;
    private String username;
    @Size(min = 3, max = 15, message = "Password must be more than 8 characters")
    private String password;

    @Size(min = 10, max = 15, message = "Invalid phone number. Phone number must have more than 8 digits")
    private String phoneNumber;

    private String address;
    private String confirmPassword;
    private City city;
    private String image;
    private String country;

}
