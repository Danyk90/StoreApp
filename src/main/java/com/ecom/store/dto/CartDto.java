package com.ecom.store.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartDto implements Serializable {

    private Long cartId;
    @JsonProperty("items")
    private List<ItemsDto> itemsInCart;
    @JsonProperty("user")
    private UserDto user;

}
