package taxi.taxi.dto.register;

import lombok.Data;

@Data
public class RegisterUserDto {

    private String name;
    private String email;
    private String phoneNumber;
    private String password;

}
