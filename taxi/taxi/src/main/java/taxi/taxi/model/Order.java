package taxi.taxi.model;


import jakarta.persistence.*;
import lombok.Data;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;

import java.util.Date;

@Entity
@Table(name = "order")
@Data
public class Order{


    @Id
    @SequenceGenerator(name = "OrderIdSeqGenerator", allocationSize = 1, sequenceName = "OrderIdSeq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OrderIdSeqGenerator")
    private Long id;

    @Column
    String serial;

    @Column
    String status;

    @Column
    Date time;

    @Column
    int price;

    @Column
    String vehicleType;

    @Column
    int driverId;

    @Column
    int userId;

    @Column
    Geometry startLocation;

    @Column
    Geometry endLocation;

}
