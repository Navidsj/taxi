package taxi.taxi.controller.authentication;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import taxi.taxi.model.drivers.Driver;
import taxi.taxi.repository.DriverRepository;
import taxi.taxi.service.driver.DriverService;

@RestController
public class DriverRegisterController {


    private final DriverRepository driverRepository;
    private final DriverService driverService;

    public DriverRegisterController(DriverRepository driverRepository, DriverService driverService) {
        this.driverRepository = driverRepository;
        this.driverService = driverService;
    }

    @PostMapping("/new/driver")
    public ResponseEntity<Object> newDriver(@RequestBody Driver driver) throws JsonProcessingException {

        return driverService.addDriver(driver);
    }



}
