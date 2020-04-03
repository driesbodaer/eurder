package com.eurder.api.controllers;

import com.eurder.domain.classes.Item;
import com.eurder.domain.dto.ItemDto;
import com.eurder.service.ItemService;
import com.eurder.service.NotEverythingFilledInExeption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.eurder.api.controllers.CustomerController.JSON;

@RestController
@RequestMapping(path = "items")
public class ItemController {
    private final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }


    @PostMapping(produces = JSON, consumes = JSON)
//    @PreAuthorize("hasAuthority('MAKE_ITEM')")
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto addItem(@RequestBody ItemDto itemDto) {
        return itemService.addItem(itemDto);
    }

    public ItemService getItemService() {
        return itemService;
    }

    @ExceptionHandler(NotEverythingFilledInExeption.class)
    protected void noteverythingfilledinexeption(NotEverythingFilledInExeption e, HttpServletResponse response) throws IOException {
        LOGGER.error(e.getMessage());
        response.sendError(403, "some fields are empty");
    }
}
