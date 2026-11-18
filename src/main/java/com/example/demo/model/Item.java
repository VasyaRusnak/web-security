package com.example.demo.model;



import lombok.*;

import java.time.Instant;

import java.time.Instant;

/*
  @author   george
  @project   web-security26
  @class  Item
  @version  1.0.0
  @since 02/09/2026 - 21.08
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Item {
    private String id;
    private String name;
    private String description;

    // --- audit fields (filled by ItemService from the JWT) ---
    private String createdBy;
    private Instant createdDate;
    private String lastModifiedBy;
    private Instant lastModifiedDate;

    public Item(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Item(String description, String name) {
        this.description = description;
        this.name = name;
    }
}