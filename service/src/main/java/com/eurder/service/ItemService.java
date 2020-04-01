package com.eurder.service;

import com.eurder.domain.classes.Item;
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
        Item Item = ItemMapper.toItem(itemDto);
        ItemRepository.addItem(Item);
        return Item;
    }
}
