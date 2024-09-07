package taxi.taxi.checker;

import org.springframework.http.ResponseEntity;

public class ValueCheck {


    public static ResponseEntity<String> checkUser(String name, String email, String password){
        if(name.length() < 3 && !name.isEmpty()){
            return ResponseEntity.badRequest().body("Name is too short");
        }
        if(email.length() < 3 && !email.isEmpty()){
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

    public static ResponseEntity<String> checkVehicle(){
        return null;
    }


}
