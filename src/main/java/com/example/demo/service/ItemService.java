package com.example.demo.service;

import com.example.demo.model.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ItemService {
    private static final Logger log = LoggerFactory.getLogger(ItemService.class);

    private final List<Item> items = new ArrayList<>();
    {
        items.add(new Item("1", "name1", "description1"));
        items.add(new Item("2", "name2", "description2"));
        items.add(new Item("3", "name3", "description3"));
    }

    /** Login of the current user, taken from the JWT-based Authentication (not hard-coded). */
    private String currentAuditor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth == null || !auth.isAuthenticated()) ? "anonymous" : auth.getName();
    }

    public List<Item> getAllItems() {
        return items;
    }

    public Item createItem(Item item) {
        if (item.getId() == null) item.setId(UUID.randomUUID().toString());
        Instant now = Instant.now();
        item.setCreatedBy(currentAuditor());
        item.setCreatedDate(now);
        item.setLastModifiedBy(currentAuditor());
        item.setLastModifiedDate(now);
        items.add(item);
        return item;
    }

    public Item getItem(String id) {
        return items.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst().orElse(null);
    }

    public Item updateItem(Item item) {
        Item old = getItem(item.getId());
        if (old == null) return null;
        item.setCreatedBy(old.getCreatedBy());
        item.setCreatedDate(old.getCreatedDate());
        item.setLastModifiedBy(currentAuditor());
        item.setLastModifiedDate(Instant.now());
        items.remove(old);
        items.add(item);
        return item;
    }

    // Захист на рівні сервісу
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteItem(String id) {
        Item item = getItem(id);
        items.remove(item);
        log.info("AUDIT: item {} deleted by {} at {}", id, currentAuditor(), Instant.now());
    }
}
