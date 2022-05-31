package com.ecom.store.controllers;


import com.ecom.store.dto.ItemsDto;
import com.ecom.store.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ItemController {

    @Autowired
    private ItemService itemService;
  /*  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/items")
    ResponseEntity getAllActiveItems() {
        return new ResponseEntity(itemService.getAllItems(), HttpStatus.OK);
    }*/

    //@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  /*  @PostMapping("/add-item")
    ResponseEntity saveItem(@RequestBody ItemsDto itemsDto) {

        itemService.saveitem(itemsDto);
        return new ResponseEntity(HttpStatus.OK);
    }
*/
    @PutMapping("/update-item")
    ResponseEntity updateItem(@RequestBody ItemsDto itemsDto) {

        itemService.updateItem(itemsDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/delete-item")
    ResponseEntity deleteItem(@RequestParam("id")Long id) {

        itemService.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
