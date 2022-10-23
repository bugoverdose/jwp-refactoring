package kitchenpos.application.dto.request;

import kitchenpos.domain.order.OrderStatus;

public class UpdateOrderStatusDto {

    private final Long orderId;
    private final OrderStatus orderStatus;

    public UpdateOrderStatusDto(Long orderId, OrderStatus orderStatus) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
    }

    public Long getOrderId() {
        return orderId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
}
