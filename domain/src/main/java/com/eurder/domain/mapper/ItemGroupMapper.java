package com.eurder.domain.mapper;

import com.eurder.domain.classes.Customer;
import com.eurder.domain.classes.ItemGroup;
import com.eurder.domain.classes.ItemGroupWithadress;
import com.eurder.domain.dto.ItemGroupDto;
import org.springframework.stereotype.Component;

@Component
public class ItemGroupMapper {

    public ItemGroup toItemGroup(ItemGroupDto itemGroupDto, boolean inStock)  {
        return new ItemGroup(itemGroupDto.getItem(), itemGroupDto.getAmount(), inStock);
    }

    public ItemGroupWithadress toItemGroupWithadress(ItemGroup itemGroup, Customer customer) {
        return new ItemGroupWithadress(itemGroup, customer.getAddress());
    }

    public ItemGroupDto toItemGroupDto(ItemGroup itemGroup) {
        return new ItemGroupDto(itemGroup.getItem(), itemGroup.getAmount());
    }
}
