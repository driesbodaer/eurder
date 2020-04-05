package com.eurder.domain.dto;

import com.eurder.domain.classes.Order;
import com.eurder.domain.classes.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


public class ReportDto {
    private List<OrderDto> orderReportList;
    private Price totalpriceOfOrders;

    public ReportDto() {
        this.orderReportList = new ArrayList<>();
        this.totalpriceOfOrders = calculateTotal();
    }

    public List<OrderDto> getOrderReportList() {
        return orderReportList;
    }

    public void setOrderReportList(List<OrderDto> orderReportList) {
        this.orderReportList = orderReportList;
    }

    public Price getTotalpriceOfOrders() {
        return totalpriceOfOrders;
    }

    public void setTotalpriceOfOrders(Price totalpriceOfOrders) {
        this.totalpriceOfOrders = totalpriceOfOrders;
    }

    public void addOrder(OrderDto order) {
        orderReportList.add(order);
        setTotalpriceOfOrders();
    }

    Price calculateTotal() {
        int prijs = 0;
        for (OrderDto order : orderReportList) {
//            prijs += order.getTotalPrice().getPrice();
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
