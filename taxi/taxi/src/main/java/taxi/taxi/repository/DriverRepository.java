package taxi.taxi.repository;

import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import taxi.taxi.model.Driver;

import java.util.ArrayList;


@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {



    @Query(value = "SELECT * FROM driver WHERE ST_DWithin(location,location,1000)", nativeQuery = true)
    ArrayList<Driver> findByDistinc(Point point);

}
