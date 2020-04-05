package com.eurder.api.controllers;

import com.eurder.domain.classes.Item;
import com.eurder.domain.classes.Price;
import com.eurder.domain.classes.Urgency;
import com.eurder.domain.dto.ItemDto;
import com.eurder.domain.mapper.ItemMapper;
import com.eurder.domain.repository.ItemRepository;
import com.eurder.service.ItemService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.Base64Utils;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
@WithMockUser(username="admin", authorities={"ADMIN_ONLY"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ItemControllerTest {
    ItemController itemController;
    ItemMapper itemMapper;
    WebTestClient testClient;
    ItemService itemService;
    ItemRepository itemRepository;

    @Autowired
    public ItemControllerTest(ItemController itemController, WebTestClient webTestClient, ItemMapper itemMapper, ItemService itemService, ItemRepository itemRepository) {
        this.itemController = itemController;
        this.itemMapper = itemMapper;
        this.testClient = webTestClient;
        this.itemService = itemService;
        this.itemRepository = itemRepository;

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
    void webtestclient_post() {
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

    @Test
    void webtestclient_getsorted() {
        Item item = getItem();
        ItemDto itemDto = itemMapper.toItemDto(item);
        itemService.getSortedList();
        String url = "items";

        testClient.get()
                .uri(url)
                .header("Authorization", "Basic " + Base64Utils
                        .encodeToString(("admin" + ":" + "admin").getBytes(StandardCharsets.UTF_8)))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ItemDto.class)
                .isEqualTo(itemService.getSortedList());

    }

    @Test
    void webtestclient_getfiltered() {
        Item item = getItem();
        ItemDto itemDto = itemMapper.toItemDto(item);

        String url = "items?urgency=Urgency.STOCK_HIGH";

        testClient.get()
                .uri(url)
                .header("Authorization", "Basic " + Base64Utils
                        .encodeToString(("admin" + ":" + "admin").getBytes(StandardCharsets.UTF_8)))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ItemDto.class)
                .isEqualTo(itemService.filterList(Urgency.STOCK_HIGH));

    }

    @Test
    void webtestclient_put() {
        Item item = getItem();
        ItemDto itemDto = itemMapper.toItemDto(item);

        String url = "items/kaas";

        testClient.put()
                .uri(url)
                .header("Authorization", "Basic " + Base64Utils
                        .encodeToString(("admin" + ":" + "admin").getBytes(StandardCharsets.UTF_8)))
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(itemDto), ItemDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ItemDto.class)
                .isEqualTo(itemDto);

        Assertions.assertThat(itemRepository.getItemList().get(0)).isEqualTo(item);

    }

    @Test
    void addItem2() {
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

}