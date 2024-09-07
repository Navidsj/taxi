package taxi.taxi.service;


import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class GetVehicles {

    private static HashMap<String,Vehicle> vehicles = new HashMap<>();

    GetVehicles() {
        creatMap();
    }

    private void creatMap(){
        vehicles.put("car",new Car());
        vehicles.put("mashin",new Car());
        vehicles.put("motor",new Motor());
    }

    public static double getVehiclePrice(String vehicle){
        if(vehicles.containsKey(vehicle)){
            return vehicles.get(vehicle).pricePerMeter();
        }else{
            return -1;
        }
    }


}
