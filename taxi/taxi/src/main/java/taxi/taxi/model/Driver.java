package taxi.taxi.model;

import jakarta.persistence.*;
import lombok.Data;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;

@Entity
@Data
@Table(name = "driver")
public class Driver{

    @Id
    @SequenceGenerator(name = "DriverIdSeqGenerator", allocationSize = 1, sequenceName = "DriverIdSeq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DriverIdSeqGenerator")
    private Long id;

    @Column
    String name;

    @Column
    String phoneNumber;

    @Column
    String licensePlateNumber;

    @Column(columnDefinition = "GEOMETRY")
    Point location;

    @Column
    boolean status;

    @Column
    String vehicle;

    @Transient
    double lat;
    @Transient
    double lng;

}
