package taxi.taxi.checker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import taxi.taxi.service.driver.GetVehicles;

public class ValueCheck {


    public static ResponseEntity<String> checkUser(String name, String email,String phoneNumber, String password){

        if(checkPhoneNumber(phoneNumber) != null){
            return ResponseEntity.badRequest().body("Phone number is incorrect");
        }
        if(name.length() < 3){
            return ResponseEntity.badRequest().body("Name is too short");
        }
        if(email.length() < 3){
            return ResponseEntity.badRequest().body("Email is too short");
        }
        if(password.length() < 7 && !password.isEmpty()){
            return ResponseEntity.badRequest().body("Password is too short");
        }
        if(password.length() > 50){
            return ResponseEntity.badRequest().body("Password is too long");
        }
        if(name.length() > 20){
            return ResponseEntity.badRequest().body("Name is too long");
        }
        if(email.length() > 30){
            return ResponseEntity.badRequest().body("Email is too long");
        }
        return null;
    }

    public static ResponseEntity<String> checkPhoneNumber(String phoneNumber){
        if(phoneNumber.length() != 11){
            return ResponseEntity.badRequest().body("Phone number is incorrect");
        }
        if(phoneNumber.matches("[0-9]{10}")){
            return ResponseEntity.badRequest().body("Phone number is incorrect");
        }
        return null;
    }

    public static ResponseEntity<Object> checkDriver(String name, String phoneNumber, String licensePlateNumber,String vehicle){
        if(name.length() < 3){
            return ResponseEntity.badRequest().body("Name is too short");
        }
        if(name.length() > 20){
            return ResponseEntity.badRequest().body("Name is too long");
        }
        if(checkPhoneNumber(phoneNumber) != null){
            return ResponseEntity.badRequest().body("Phone number is incorrect");
        }
        if(licensePlateNumber.length() < 7 || licensePlateNumber.length() > 10){
            return ResponseEntity.badRequest().body("License plate number is incorrect");
        }
        if(GetVehicles.getVehiclePrice(vehicle) == -1){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("vehicle not correct!");
        }
        return null;

    }


}
