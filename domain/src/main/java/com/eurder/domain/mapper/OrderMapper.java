package com.eurder.domain.mapper;

import com.eurder.domain.classes.Customer;
import com.eurder.domain.classes.Item;
import com.eurder.domain.classes.ItemGroup;
import com.eurder.domain.classes.Order;
import com.eurder.domain.dto.ItemDto;
import com.eurder.domain.dto.ItemGroupDto;
import com.eurder.domain.dto.OrderDto;
import com.eurder.domain.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderMapper {

    ItemGroupMapper itemGroupMapper;
    ItemRepository itemRepository;


    @Autowired
    public OrderMapper(ItemGroupMapper itemGroupMapper, ItemRepository itemRepository) {
        this.itemGroupMapper = itemGroupMapper;
        this.itemRepository = itemRepository;

    }

    public Order toOrder(OrderDto orderDto, Customer customer) {
        List<ItemGroup> itemGroupList = new ArrayList<>();
        for (ItemGroupDto itemGroupDto : orderDto.getItemGroupDtoList()) {
            itemGroupList.add(itemGroupMapper.toItemGroup(itemGroupDto, checkIfItemGroupIsInStock(itemGroupDto)));
            removeItemFromRepo(itemGroupDto);
        }
        return new Order(itemGroupList, customer);
    }

    private void removeItemFromRepo(ItemGroupDto itemGroupDto) {
        if (checkIfItemGroupIsInStock(itemGroupDto)) {
            Item item = getItemFromList(itemGroupDto);
            item.setAmount(item.getAmount() - itemGroupDto.getAmount());
        }
    }

    public Boolean checkIfItemGroupIsInStock(ItemGroupDto itemGroupDto) {
        Item itemTocheck = getItemFromList(itemGroupDto);
        return itemTocheck != null && itemTocheck.getAmount() >= itemGroupDto.getAmount();
    }

    public OrderDto toOrderDto(Order order) {
        List<ItemGroupDto> itemGroupDtoList = new ArrayList<>();
        for (ItemGroup itemGroup : order.getItemGroupList()) {
            itemGroupDtoList.add(itemGroupMapper.toItemGroupDto(itemGroup));
        }
        return new OrderDto(itemGroupDtoList);
    }

    private Item getItemFromList(ItemGroupDto itemGroupDto) {
        return itemRepository.getItemList().stream()
                .filter(x -> x.getName().equals(itemGroupDto.getItem().getName()))
                .findFirst().orElse(null);
    }
}
