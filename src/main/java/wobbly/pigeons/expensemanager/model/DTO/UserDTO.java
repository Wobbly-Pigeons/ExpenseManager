package wobbly.pigeons.expensemanager.model.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDTO {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate dob;
}
