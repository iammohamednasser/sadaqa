package com.mohamednasser.sadaqa.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "countries")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(mappedBy = "country",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<City> cities;
}
