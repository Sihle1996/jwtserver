package com.example.testing.controller;

import com.example.testing.entity.MenuItem;
import com.example.testing.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuItemController {

    private final MenuItemService menuItemService;

    // Public: Anyone can view the menu
    @GetMapping
    public List<MenuItem> getAllMenuItems() {
        return menuItemService.getAllMenuItems();
    }

    // Public: Anyone can view a specific menu item
    @GetMapping("/{id}")
    public MenuItem getMenuItemById(@PathVariable Long id) {
        return menuItemService.getMenuItemById(id);
    }

    // Restricted: Only ADMIN can create menu items
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public MenuItem createMenuItem(@RequestBody MenuItem item) {
        return menuItemService.createMenuItem(item);
    }

    // Restricted: Only ADMIN can update menu items
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public MenuItem updateMenuItem(@PathVariable Long id, @RequestBody MenuItem item) {
        return menuItemService.updateMenuItem(id, item);
    }

    // Restricted: Only ADMIN can delete menu items
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteMenuItem(@PathVariable Long id) {
        menuItemService.deleteMenuItem(id);
    }
}