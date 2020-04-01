package com.eurder.domain.mapper;

import com.eurder.domain.classes.Item;
import com.eurder.domain.dto.ItemDto;

public class ItemMapper {

    public Item toItem(ItemDto itemDto) {
        return new Item(itemDto.getName(), itemDto.getDescription(), itemDto.getPrice(), itemDto.getAmount());
    }
    public ItemDto toItemDto(Item item) {
        return new ItemDto(item.getName(), item.getDescription(), item.getPrice(), item.getAmount());
    }
}
