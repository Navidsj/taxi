package taxi.taxi.dto;

import lombok.Data;

@Data
public class RegisterUserDto {

    private String name;
    private String email;
    private String phoneNumber;
    private String password;

}
