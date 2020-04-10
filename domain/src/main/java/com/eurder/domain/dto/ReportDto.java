package com.eurder.domain.dto;

import com.eurder.domain.classes.Price;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportDto reportDto = (ReportDto) o;
        return Objects.equals(orderReportList, reportDto.orderReportList) &&
                Objects.equals(totalpriceOfOrders, reportDto.totalpriceOfOrders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderReportList, totalpriceOfOrders);
    }

    @Override
    public String toString() {
        return "ReportDto{" +
                "orderReportList=" + orderReportList +
                ", totalpriceOfOrders=" + totalpriceOfOrders +
                '}';
    }

}
