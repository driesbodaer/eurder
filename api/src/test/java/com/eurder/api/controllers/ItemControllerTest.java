package com.eurder.api.controllers;

import com.eurder.domain.classes.Item;
import com.eurder.domain.classes.Price;
import com.eurder.domain.dto.ItemDto;
import com.eurder.domain.mapper.ItemMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.Base64Utils;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ItemControllerTest {
    ItemController itemController;
    ItemMapper itemMapper;
    WebTestClient testClient;

    @Autowired
    public ItemControllerTest(ItemController itemController, WebTestClient webTestClient, ItemMapper itemMapper) {
        this.itemController = itemController;
        this.itemMapper = itemMapper;
        this.testClient = webTestClient;
    }

    @Test
    void addItem() {
        Item item = getItem();
        ItemDto itemDto = itemMapper.toItemDto(item);

        Assertions.assertThat(itemDto).isEqualTo(itemController.addItem(itemDto));
    }

    @Test
    void addItem_addedToList() {
        Item item = getItem();
        ItemDto itemDto = itemMapper.toItemDto(item);
        itemController.addItem(itemDto);
        Assertions.assertThat(itemController.getItemService().getItemRepository().getItemList().get(2)).isEqualTo(item);
    }

    private Item getItem() {
        return new Item("cheese", "burgut", new Price(5.4, "eur"), 15);
    }

    @Test
    void addItem_fieldsAreEmpty_throws() {
        Item item = new Item("", "burgut", new Price(5.4, "eur"), 15);
        ItemDto itemDto = itemMapper.toItemDto(item);

        Assertions.assertThatThrownBy(() -> itemController.addItem(itemDto));
    }

    @Test
    void webtestclient_test() {
        Item item = getItem();
        ItemDto itemDto = itemMapper.toItemDto(item);

        String url = "items";

        testClient.post()
                .uri(url)
                .header("Authorization", "Basic " + Base64Utils
                        .encodeToString(("admin" + ":" + "admin").getBytes(StandardCharsets.UTF_8)))
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(itemDto), ItemDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ItemDto.class)
                .isEqualTo(itemDto);

    }

}