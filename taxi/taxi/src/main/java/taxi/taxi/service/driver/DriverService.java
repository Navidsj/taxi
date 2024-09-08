package taxi.taxi.service.driver;


import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import taxi.taxi.checker.ValueCheck;
import taxi.taxi.dto.register.ReportDto;
import taxi.taxi.mapper.DriverMapper;
import taxi.taxi.model.drivers.Driver;
import taxi.taxi.repository.DriverRepository;
import taxi.taxi.service.rabbit.RabbitMQProducer;

@Service
public class DriverService {

    private final DriverRepository driverRepository;
    private final RabbitMQProducer rabbitMQProducer;

    public DriverService(DriverRepository driverRepository, RabbitMQProducer rabbitMQProducer) {
        this.driverRepository = driverRepository;
        this.rabbitMQProducer = rabbitMQProducer;
    }

    public ResponseEntity<Object> addDriver(Driver driver){

        GeometryFactory gf = new GeometryFactory();

        ResponseEntity<Object> res = ValueCheck.checkDriver(driver.getName(),driver.getPhoneNumber(),driver.getLicensePlateNumber(),driver.getVehicle());

        if(res != null){
            return res;
        }

        if(driverRepository.findByDriverName(driver.getName()) != null){
            return ResponseEntity.badRequest().body("Driver with this name already exists");
        }

        if(driverRepository.findByLicensePlateNumber(driver.getLicensePlateNumber()) != null){
            return ResponseEntity.badRequest().body("License plate number already exists");
        }


        driver.setLocation(gf.createPoint(new Coordinate(driver.getLat(),driver.getLng())));
        driverRepository.save(driver);


        rabbitMQProducer.sendReport(new ReportDto("driver added","driverId="+driver.getId()).toString());

        return ResponseEntity.ok(DriverMapper.driverToDriverResponseDto(driver));
    }


}
