package com.springboot.order.service;

import com.springboot.coffee.entity.Coffee;
import com.springboot.coffee.service.CoffeeService;
import com.springboot.exception.BusinessLogicException;
import com.springboot.exception.ExceptionCode;
import com.springboot.member.entity.Member;
import com.springboot.member.service.MemberService;
import com.springboot.order.entity.Order;
import com.springboot.order.entity.OrderCoffee;
import com.springboot.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private CoffeeService coffeeService;
    private MemberService memberService;

    public OrderService(CoffeeService coffeeService, MemberService memberService) {
        this.coffeeService = coffeeService;
        this.memberService = memberService;
    }

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(Order order) {
        // TODO should business logic
        // 회원이 존재하는지 확인
        memberService.findVerifiedMember(order.getMemberId());

        // 존재하는 커피인지 확인
        for (OrderCoffee orderCoffee : order.getOrderCoffees()) {
            coffeeService.findeVerifiedCoffee(orderCoffee.getCoffeeId());
        }

        return orderRepository.save(order);


    }

    public Order findOrder(long orderId) {
        // TODO should business logic

        // TODO order 객체는 나중에 DB에서 조회 하는 것으로 변경 필요.

    }

    // 주문 수정 메서드는 사용하지 않습니다.

    public List<Order> findOrders() {
        // TODO should business logic

        // TODO order 객체는 나중에 DB에서 조회하는 것으로 변경 필요.

    }

    public void cancelOrder() {
        // TODO should business logic
    }

    public Order findVerifiedOrder(long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        Order findOrder = optionalOrder.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.ORDER_NOT_FOUND)
        );
        return findOrder;
    }

}
