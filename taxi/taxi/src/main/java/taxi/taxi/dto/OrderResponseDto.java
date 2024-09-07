package taxi.taxi.dto;

import lombok.Data;

@Data
public class OrderResponseDto {

    long orderId;
    int price;
    String status;
    String driverName;
    String driverPhoneNumber;
    String driverLicensePlateNumber;
    String startPoint;
    String endPoint;



}
