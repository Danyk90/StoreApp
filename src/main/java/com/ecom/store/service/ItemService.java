package com.ecom.store.service;


import com.ecom.store.dto.ItemsDto;
import com.ecom.store.exceptions.ItemAlreadyExistException;
import com.ecom.store.exceptions.ItemNotFoundException;
import com.ecom.store.models.Items;
import com.ecom.store.repository.ItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {


    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ModelMapper modelMapper;

    public List<ItemsDto> getAllItems() {

        List<Items> items = itemRepository.findItemsByActiveIsTrue();
        return items.stream().map(Items::toDto).collect(Collectors.toList());

    }

    public void saveitem(ItemsDto itemsDto) {
        //todo validate item request
        Items items = modelMapper.map(itemsDto, Items.class);
        if (items.getItemId() == null)
            itemRepository.save(items);
        else
            throw new ItemAlreadyExistException("Item Already Exists");
    }

    public void updateItem(ItemsDto itemsDto) {
        //todo validate item request
        Items items = modelMapper.map(itemsDto, Items.class);
        if (items.getItemId() == null)
            throw new ItemNotFoundException("Item id not present ");
        else
            itemRepository.save(items);

    }

    public ItemsDto getItemById(Long id) {

        Items items = itemRepository.findById(id).orElseThrow(ItemNotFoundException::new);
        return modelMapper.map(items, ItemsDto.class);
    }

    public void deleteById(Long id) {
        itemRepository.deleteById(id);
    }

}
