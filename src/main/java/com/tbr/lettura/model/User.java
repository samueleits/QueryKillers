package com.tbr.lettura.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
    private Long id;
    // altri campi, getter, setter
}