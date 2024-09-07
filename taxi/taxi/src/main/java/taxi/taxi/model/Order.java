package taxi.taxi.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import net.bytebuddy.implementation.bind.annotation.Default;
import org.checkerframework.checker.index.qual.Positive;
import org.locationtech.jts.geom.Point;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.Date;

@Entity
@Data
@Table(name = "safarha")
public class Order{

    @Id
    @SequenceGenerator(name = "OrderIdSeqGenerator", allocationSize = 1, sequenceName = "OrderIdSeq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OrderIdSeqGenerator")
    private Long id;

    @Column(nullable = false)
    String status = "not payed";

    @Column
    @Positive
    int price;

    @Column
    String vehicleType;

    @Column
    Date time;

    @Column
    Long driverId = 0L;

    @Column
    Long userId;

    @Column
    Point startLocation;

    @Column
    Point endLocation;

}


