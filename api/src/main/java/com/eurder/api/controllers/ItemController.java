package com.eurder.api.controllers;

import com.eurder.domain.classes.Item;
import com.eurder.domain.dto.ItemDto;
import com.eurder.service.ItemService;
import com.eurder.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "items")
public class ItemController {
    public static final String JSON = "application/json";
    private final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping (produces = JSON , consumes = JSON)
    @ResponseStatus(HttpStatus.OK)
    public Item addItem(@RequestBody ItemDto itemDto) {
       return itemService.addItem(itemDto);
    }
}
