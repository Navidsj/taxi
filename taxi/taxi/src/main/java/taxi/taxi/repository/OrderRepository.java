package taxi.taxi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import taxi.taxi.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
