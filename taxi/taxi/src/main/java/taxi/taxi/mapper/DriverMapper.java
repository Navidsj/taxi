package taxi.taxi.mapper;

import taxi.taxi.dto.driver.DriverResponseDto;
import taxi.taxi.model.drivers.Driver;

public class DriverMapper {

    public static DriverResponseDto driverToDriverResponseDto(Driver driver) {
        DriverResponseDto driverResponseDto = new DriverResponseDto();

        driverResponseDto.setName(driver.getName());
        driverResponseDto.setLocation(driver.getLocation().toString());
        driverResponseDto.setPhoneNumber(driver.getPhoneNumber());
        driverResponseDto.setVehicle(driver.getVehicle());
        driverResponseDto.setLicensePlateNumber(driver.getLicensePlateNumber());
        return driverResponseDto;
    }

}
