package com.eurder.service;

import com.eurder.domain.classes.Item;
import com.eurder.domain.dto.CustomerDto;
import com.eurder.domain.dto.ItemDto;
import com.eurder.domain.dto.ItemDto;
import com.eurder.domain.mapper.ItemMapper;
import com.eurder.domain.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    private final ItemMapper ItemMapper;
    private final ItemRepository ItemRepository;

    @Autowired
    public ItemService(ItemMapper ItemMapper, ItemRepository ItemRepository) {
        this.ItemMapper = ItemMapper;
        this.ItemRepository = ItemRepository;
    }

    public Item addItem(ItemDto itemDto) {
        if (hasAnyEmptyFields(itemDto)) {
            throw new NotEverythingFilledInExeption("fill in everything");
        }
        Item Item = ItemMapper.toItem(itemDto);
        ItemRepository.addItem(Item);
        return Item;
    }

    public boolean hasAnyEmptyFields(ItemDto itemDto) {
        return itemDto.getDescription().isEmpty() || itemDto.getName().isEmpty() || itemDto.getAmount() == 0 || itemDto.getPrice() == 0.0;
    }

    public com.eurder.domain.repository.ItemRepository getItemRepository() {
        return ItemRepository;
    }
}
