package com.eurder.domain.dto;

import com.eurder.domain.classes.Price;
import java.util.ArrayList;
import java.util.List;


public class ReportDto {
    private List<OrderDto> orderReportList;
    private Price totalpriceOfOrders;

    public ReportDto() {
        this.orderReportList = new ArrayList<>();
        this.totalpriceOfOrders = calculateTotal();
    }

    public void addOrder(OrderDto order) {
        orderReportList.add(order);
        setTotalpriceOfOrders();
    }

    Price calculateTotal() {
        int prijs = 0;
        for (OrderDto order : orderReportList) {
            prijs += order.getTotalPrice().getPrice();
        }
        return new Price(prijs, "eur");
    }

    public void setTotalpriceOfOrders() {
        this.totalpriceOfOrders = calculateTotal();
    }

    @Override
    public String toString() {
        return "ReportDto{" +
                "orderReportList=" + orderReportList +
                ", totalpriceOfOrders=" + totalpriceOfOrders +
                '}';
    }

}
