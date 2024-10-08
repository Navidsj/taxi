package taxi.taxi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import taxi.taxi.model.order.Order;

import java.util.ArrayList;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT * FROM safarha Where user_id = :id ORDER BY time DESC LIMIT :startToEnd OFFSET :startIndex",nativeQuery = true)
    ArrayList<Order> findOrdersByStatus(Long id,int startIndex,int startToEnd);
}
