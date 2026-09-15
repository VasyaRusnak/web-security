package com.example.demo.service;



/*
  @author   vasya
  @project  demo
  @class  ItemService
  @version  1.0.0
  @since 02/09/2026 - 21.10
*/

import com.example.demo.model.Item;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {
    private List<Item> items = new ArrayList<>();
    {
        items.add(new Item("1", "name1", "description1"));
        items.add(new Item("2", "name2", "description2"));
        items.add(new Item("3", "name3", "description3"));
    }

    public List<Item> getAllItems() {
        return items;
    }

    public Item createItem(Item item){
        items.add(item);
        System.out.println(items.size());
        return item;
    }

    public Item getItem(String id){
        return items.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst().orElse(null);
    }

    public Item updateItem(Item item){
        Item oldItem = getItem(item.getId());
        items.remove(oldItem);
        items.add(item);
        return item;
    }

    public void deleteItem(String id){
        Item item = getItem(id);
        items.remove(item);
    }
}