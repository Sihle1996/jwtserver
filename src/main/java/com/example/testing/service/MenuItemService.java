package com.example.testing.service;


import com.example.testing.entity.MenuItem;
import com.example.testing.repository.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor // Automatically generates a constructor for final fields
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;

    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    public MenuItem getMenuItemById(Long id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found with ID: " + id));
    }

    public MenuItem createMenuItem(MenuItem item) {
        return menuItemRepository.save(item);
    }

    public MenuItem updateMenuItem(Long id, MenuItem updatedItem) {
        MenuItem existingItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found with ID: " + id));

        existingItem.setName(updatedItem.getName());
        existingItem.setDescription(updatedItem.getDescription());
        existingItem.setPrice(updatedItem.getPrice());
        existingItem.setImageUrl(updatedItem.getImageUrl());
        return menuItemRepository.save(existingItem);
    }

    public void deleteMenuItem(Long id) {
        if (!menuItemRepository.existsById(id)) {
            throw new RuntimeException("Menu item not found with ID: " + id);
        }
        menuItemRepository.deleteById(id);
    }

    public List<MenuItem> createMenuItems(List<MenuItem> menuItems) {
        return menuItemRepository.saveAll(menuItems);
    }

}
