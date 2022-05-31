package com.ecom.store.dto;

import com.ecom.store.models.Items;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemsDto {

    private Long itemId;

    private String itemName;

    private double price;

    @JsonProperty("isActive")
    private boolean isActive;

    public Items toEntity() {

        return Items.builder()
                .active(true)
                .itemId(this.itemId)
                .itemName(this.itemName)
                .price(this.price)
                .build();
    }
}