package taxi.taxi.repository;

import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import taxi.taxi.model.Driver;

import java.util.ArrayList;


@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {



    @Query(value = "SELECT * FROM driver WHERE ST_DWithin(ST_Transform(ST_SetSRID(location, 4326), 3857), ST_Transform(ST_SetSRID(:point, 4326), 3857), 1000) AND status = false AND vehicle = :vehicle", nativeQuery = true)
    ArrayList<Driver> findByDistinc(Point point,String vehicle);

}
