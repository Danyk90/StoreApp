package com.ecom.store.models;

import com.ecom.store.dto.ItemsDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "item", schema = "store")
public class Items implements Serializable {


    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long itemId;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "active", nullable = false)
    private boolean active;

    @ManyToOne
    /* @Column(name="cart_id",nullable = false)*/
    private Carts carts;


    public ItemsDto toDto() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, ItemsDto.class);
    }
}
