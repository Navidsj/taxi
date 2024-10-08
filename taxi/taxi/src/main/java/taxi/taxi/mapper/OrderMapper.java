package taxi.taxi.mapper;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import taxi.taxi.dto.order.OrderDto;
import taxi.taxi.dto.order.OrderResponseDto;
import taxi.taxi.model.drivers.Driver;
import taxi.taxi.model.order.Order;

import java.util.Date;

public class OrderMapper {

    public static Order orderDtoMapperToOrder(OrderDto orderDto) {
        Order order = new Order();
        GeometryFactory gef = new GeometryFactory();

        order.setTime(new Date(System.currentTimeMillis()));
        order.setStartLocation(gef.createPoint(new Coordinate(orderDto.getStartLat(),orderDto.getStartLong())));
        order.setVehicleType(orderDto.getVehicleType());
        order.setEndLocation(gef.createPoint(new Coordinate(orderDto.getEndLat(),orderDto.getEndLong())));
        return order;
    }

    public static OrderResponseDto orderMapperToOrderResponseDto(Order order,Driver driver) {


        if(driver == null) {
            OrderResponseDto orderResponseDto = new OrderResponseDto();

            orderResponseDto.setOrderId(order.getId());
            orderResponseDto.setStatus(order.getStatus());
            orderResponseDto.setPrice(order.getPrice());
            orderResponseDto.setStartPoint(order.getStartLocation().toString());
            orderResponseDto.setEndPoint(order.getEndLocation().toString());
            return orderResponseDto;
        }

        OrderResponseDto orderResponseDto = new OrderResponseDto();

        orderResponseDto.setOrderId(order.getId());
        orderResponseDto.setStatus(order.getStatus());
        orderResponseDto.setPrice(order.getPrice());
        orderResponseDto.setDriverName(driver.getName());
        orderResponseDto.setDriverPhoneNumber(driver.getPhoneNumber());
        orderResponseDto.setDriverLicensePlateNumber(driver.getLicensePlateNumber());
        orderResponseDto.setStartPoint(order.getStartLocation().toString());
        orderResponseDto.setEndPoint(order.getEndLocation().toString());


        return orderResponseDto;
    }


}
