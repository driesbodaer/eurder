package com.eurder.domain.dto;

import com.eurder.domain.classes.Order;

import java.util.List;

public class ReportDto {
    private List<Order> orderReportList;
    private double totalpriceOfOrders;

    public ReportDto(List<Order> orderList) {
        this.orderReportList = orderList;
        this.totalpriceOfOrders = 0; //use calculatemethod here
    }



}
