package taxi.taxi.mapper;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import taxi.taxi.dto.OrderDto;
import taxi.taxi.model.Order;

import java.util.Date;

public class OrderMapper {

    public static Order mapper(OrderDto orderDto) {
        Order order = new Order();
        GeometryFactory gef = new GeometryFactory();

        order.setUserId(orderDto.getUserId());
        order.setTime(new Date(System.currentTimeMillis()));
        order.setStartLocation(gef.createPoint(new Coordinate(orderDto.getStartLat(),orderDto.getStartLong())));
        order.setVehicleType(orderDto.getVehicleType());
        order.setEndLocation(gef.createPoint(new Coordinate(orderDto.getEndLat(),orderDto.getEndLong())));
        return order;
    }


}
