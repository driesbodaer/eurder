package com.eurder.domain.dto;

import com.eurder.domain.classes.Order;
import com.eurder.domain.classes.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReportDto {
    private List<Order> orderReportList;
    private Price totalpriceOfOrders;

    @Autowired
    public ReportDto() {
        this.orderReportList = new ArrayList<>();
        this.totalpriceOfOrders = new Price(0, "eur");
    }

    public void addOrder(Order order) {
        orderReportList.add(order);
        setTotalpriceOfOrders();
    }

    public void setTotalpriceOfOrders() {
        int prijs = 0;
        for (Order order : orderReportList) {
            prijs += order.getTotalPrice().getPrice();
        }
        this.totalpriceOfOrders = new Price(prijs, "eur");
    }

    @Override
    public String toString() {
        return "ReportDto{" +
                "orderReportList=" + orderReportList +
                ", totalpriceOfOrders=" + totalpriceOfOrders +
                '}';
    }

}
