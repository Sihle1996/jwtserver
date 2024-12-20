package com.example.testing.controller;

import com.example.testing.entity.MenuItem;

import com.example.testing.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/vi/auth/admin/menu")
@RequiredArgsConstructor
public class AdminMenuController {
    private final MenuService menuService;

    @PostMapping
    public MenuItem createMenuItem(@RequestBody MenuItem menuItem) {
        return menuService.createMenuItem(menuItem);
    }

    @PutMapping("/{id}")
    public MenuItem updateMenuItem(@PathVariable Long id, @RequestBody MenuItem menuItem) {
        return menuService.updateMenuItem(id, menuItem);
    }

    @DeleteMapping("/{id}")
    public void deleteMenuItem(@PathVariable Long id) {
        menuService.deleteMenuItem(id);
    }

    @GetMapping
    public List<MenuItem> getAllMenuItems() {
        return menuService.getAllMenuItems();
    }

//    @PostMapping("/bulk")
//    public List<MenuItem> createMenuItems(@RequestBody List<MenuItem> menuItems) {
//        return menuService.createMenuItems(menuItems);
//    }

}
