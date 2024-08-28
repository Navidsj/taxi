package taxi.taxi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import taxi.taxi.model.Driver;


@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
}
