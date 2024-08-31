package taxi.taxi.dto;

import lombok.Data;

@Data
public class OrderDto {

    private long userId;
    private double startLat;
    private double startLong;
    private double endLat;
    private double endLong;
    private String vehicleType;

}
