package com.springboot.coffee.service;

import com.springboot.coffee.entity.Coffee;
import com.springboot.coffee.repository.CoffeeRepository;
import com.springboot.exception.BusinessLogicException;
import com.springboot.exception.ExceptionCode;

import com.springboot.order.entity.Order;
import com.springboot.order.entity.OrderCoffee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CoffeeService {
    private CoffeeRepository coffeeRepository;

    public CoffeeService(CoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
    }

    public Coffee createCoffee(Coffee coffee) {
        // 커피 코드를 대문자로 변경
        String coffeeCode = coffee.getCoffeeCode().toUpperCase();
        // 이미 존재하는 커피코드인지 확인
        verifyExistCoffee(coffee.getCoffeeCode());
        return coffeeRepository.save(coffee);
    }

    public Coffee updateCoffee(Coffee coffee) {
        // 존재하는 커피인지 확인
        Coffee findCoffee = findeVerifiedCoffee(coffee.getCoffeeId());

        // 커피 정보 수정
        Optional.ofNullable(coffee.getKorName())
                .ifPresent(korName -> findCoffee.setKorName(korName));
        Optional.ofNullable(coffee.getEngName())
                .ifPresent(engName -> coffee.setEngName(engName));
        Optional.ofNullable(coffee.getPrice())
                .ifPresent(price -> coffee.setPrice(price));

        return coffeeRepository.save(findCoffee);
    }

    public Coffee findCoffee(long coffeeId) {
        return findeVerifiedCoffee(coffeeId);
    }

    public List<Coffee> findCoffees() {
        return (List<Coffee>) coffeeRepository.findAll();
    }

    public void deleteCoffee(long coffeeId) {
        Coffee coffee = findeVerifiedCoffee(coffeeId);
        coffeeRepository.delete(coffee);


    }

    // 주문에 해당하는 커피 정보 조회
    public List<Coffee> findOrderedCoffees(Order order) {
        List<Coffee> orderedCoffees = new ArrayList<>();
            for (OrderCoffee ordercoffee : order.getOrderCoffees()) {
                Coffee coffee = findCoffee(ordercoffee.getCoffeeId());  
                orderedCoffees.add(coffee);
            }
            return orderedCoffees;
    }

    // 존재하는 커피인지 커피 코드로 확인
    public void verifyExistCoffee(String coffeeCode) {
        Optional<Coffee> coffee = coffeeRepository.findByCoffeeCode(coffeeCode);
        if (coffee.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.COFFEE_CODE_EXISTS);
        }
    }

    // 존재하는 커피인지 확인
    public Coffee findeVerifiedCoffee(long coffeeId) {
        Optional<Coffee> optionalCoffee = coffeeRepository.findById(coffeeId);
        //optianl클래스를 변경 null일때 상황을 벗겨내야함
        Coffee findeCoffee = optionalCoffee.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.COFFEE_NOT_FOUND)
        );

        return findeCoffee;
    }
}
