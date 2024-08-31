package taxi.taxi.model;

import jakarta.persistence.*;
import lombok.Data;
import org.locationtech.jts.geom.Point;

import java.util.Date;

@Entity
@Data
@Table(name = "safarha")
public class Order{

    @Id
    @SequenceGenerator(name = "OrderIdSeqGenerator", allocationSize = 1, sequenceName = "OrderIdSeq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OrderIdSeqGenerator")
    private Long id;

    @Column
    String status;

    @Column
    int price;

    @Column
    String vehicleType;

    @Column
    Date time;

    @Column
    Long driverId;

    @Column
    Long userId;

    @Column
    Point startLocation;

    @Column
    Point endLocation;

}


