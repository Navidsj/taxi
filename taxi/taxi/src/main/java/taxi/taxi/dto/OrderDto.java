package taxi.taxi.dto;

import lombok.Data;

@Data
public class OrderDto {

    private double startLat;
    private double startLong;
    private double endLat;
    private double endLong;
    private String vehicleType;

}
