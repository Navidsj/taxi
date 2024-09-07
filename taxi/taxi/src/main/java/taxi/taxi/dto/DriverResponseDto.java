package taxi.taxi.dto;

import lombok.Data;

@Data
public class DriverResponseDto {

    String name;
    String phoneNumber;
    String vehicle;
    String licensePlateNumber;
    String location;

}
