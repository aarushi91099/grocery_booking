package com.assesment.grocerybooking.controller;

import com.assesment.grocerybooking.model.GroceryItem;
import com.assesment.grocerybooking.model.OrderItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/items")
public class UserController {

    private final AdminController adminController;

    public UserController(AdminController adminController) {
        this.adminController = adminController;
    }

    @GetMapping
    public ResponseEntity<List<GroceryItem>> viewItems() {
        return adminController.viewItems();
    }

    @PostMapping("/book")
    public ResponseEntity<String> bookItems(@RequestBody List<OrderItem> orderItems) {
        for (OrderItem order : orderItems) {
            GroceryItem item = adminController.groceryItems.get(order.getItemId());
            if (item == null || item.getInventory() < order.getQuantity()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient inventory or item not found for ID: " + order.getItemId());
            }
            item.setInventory(item.getInventory() - order.getQuantity());
        }
        return ResponseEntity.ok("Order placed successfully.");
    }
}
