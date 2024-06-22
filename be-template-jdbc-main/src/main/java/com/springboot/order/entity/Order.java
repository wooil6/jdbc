package com.springboot.order.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Table("ORDERS")
public class Order {
    @Id
    private long orderId;
    private long memberId;
    private long coffeeId;
    //order와 coffee 클래스를 매핑하기 위한 코드
    @MappedCollection(idColumn = "ORDER_ID", keyColumn = "ORDER_COFFEE_ID")
    private Set<OrderCoffee> orderCoffees = new LinkedHashSet<>();

    //주문 상태를 나타내는 멤버 변수로 enum타입 order_request가 기본값
    private OrderStatus orderStatus = OrderStatus.ORDER_REQUEST;

    //주문이 등록되는 시간 정보를 나타내는 멤버 변수
    private LocalDateTime createAt = LocalDateTime.now();

    //주문 상태를 나타내는 ENUM
    public enum OrderStatus {
        ORDER_REQUEST(1, "주문 요청"),
        ORDER_CONFIRM(2, "주문 확정"),
        ORDER_COMPLETE(3, "주문 완료"),
        ORDER_CANCEL(4, "주문 취소");

        @Getter
        private int stepNumber;
        @Getter
        private String stepDescription;

        OrderStatus(int stepNumber, String stepDescription) {
            this.stepNumber = stepNumber;
            this.stepDescription = stepDescription;
        }
    }

}
