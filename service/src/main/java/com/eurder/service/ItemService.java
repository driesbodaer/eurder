package com.eurder.service;

import com.eurder.domain.classes.Item;
import com.eurder.domain.classes.Urgency;
import com.eurder.domain.dto.ItemDto;
import com.eurder.domain.mapper.ItemMapper;
import com.eurder.domain.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private final ItemMapper itemMapper;
    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemMapper itemMapper, ItemRepository itemRepository) {
        this.itemMapper = itemMapper;
        this.itemRepository = itemRepository;
    }

    public ItemDto addItem(ItemDto itemDto) {
        if (hasAnyEmptyFields(itemDto)) {
            throw new NotEverythingFilledInExeption("fill in everything");
        }
        Item Item = itemMapper.toItem(itemDto);
        itemRepository.addItem(Item);
        return itemDto;
    }

    public List<ItemDto> getSortedList() {
        List<ItemDto> returnlist = new ArrayList<>();
        for (Item item : itemRepository.sortedList()) {
            returnlist.add(itemMapper.toItemDto(item));
        }
        return returnlist;
    }

    public Urgency calculateUrgency(String urgencyString) {
        if (urgencyString.equals("Urgency.STOCK_LOW")) {
            return Urgency.STOCK_LOW;
        }
        if (urgencyString.equals("Urgency.STOCK_MEDIUM")) {
            return Urgency.STOCK_MEDIUM;
        }
        return Urgency.STOCK_HIGH;
    }

    public ItemDto updateItem(ItemDto itemDto, String name) {
        int index = itemRepository.getItemList().indexOf(itemRepository.getItemList().stream().filter(x -> x.getName().equals(name)).findFirst().orElse(null));
        itemRepository.getItemList().set(index, itemMapper.toItem(itemDto));
        return itemDto;
    }

    public List<ItemDto> filterList(Urgency urgency) {
        return getSortedList().stream().filter(x -> x.getUrgency() == urgency).collect(Collectors.toList());
    }

    public boolean hasAnyEmptyFields(ItemDto itemDto) {
        return itemDto.getDescription().isEmpty() || itemDto.getName().isEmpty() || itemDto.getAmount() == 0 || itemDto.getPrice().getPrice() == 0.0;
    }

    public ItemRepository getItemRepository() {
        return itemRepository;
    }
}
