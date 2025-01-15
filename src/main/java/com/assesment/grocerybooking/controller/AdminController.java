package com.assesment.grocerybooking.controller;

import com.assesment.grocerybooking.model.GroceryItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/items")
public class AdminController {

    public final Map<Integer, GroceryItem> groceryItems = new HashMap<>();
    private int currentId = 1;

    @PostMapping
    public ResponseEntity<String> addItem(@RequestBody GroceryItem newItem) {
        newItem.setId(currentId++);
        groceryItems.put(newItem.getId(), newItem);
        return ResponseEntity.status(HttpStatus.CREATED).body("Item added successfully with ID: " + newItem.getId());
    }

    @GetMapping
    public ResponseEntity<List<GroceryItem>> viewItems() {
        return ResponseEntity.ok(new ArrayList<>(groceryItems.values()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateItem(@PathVariable int id, @RequestBody GroceryItem updatedItem) {
        if (!groceryItems.containsKey(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found.");
        }
        updatedItem.setId(id);
        groceryItems.put(id, updatedItem);
        return ResponseEntity.ok("Item updated successfully.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable int id) {
        if (!groceryItems.containsKey(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found.");
        }
        groceryItems.remove(id);
        return ResponseEntity.ok("Item removed successfully.");
    }

    @PutMapping("/{id}/inventory")
    public ResponseEntity<String> updateInventory(@PathVariable int id, @RequestParam int quantity) {
        if (!groceryItems.containsKey(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found.");
        }
        GroceryItem item = groceryItems.get(id);
        item.setInventory(quantity);
        return ResponseEntity.ok("Inventory updated successfully.");
    }
}
