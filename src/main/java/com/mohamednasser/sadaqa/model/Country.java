package com.mohamednasser.sadaqa.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "countries")
public class Country {

    @Id
    private int id;

    private String iso;

    private String name;

    @Column(name = "nice_name")
    private String niceName;

    private String iso3;

    @Column(name = "num_code")
    private int numCode;

    @Column(name = "phone_code")
    private int phoneCode;

}
