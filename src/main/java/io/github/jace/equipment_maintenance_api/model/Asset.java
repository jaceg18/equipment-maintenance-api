package io.github.jace.equipment_maintenance_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name, location;

    public Asset(){}

    public void setId(int id){this.id = id;}
    public void setName(String name){this.name = name;}
    public void setLocation(String location){this.location = location;}

    public int getId(){return id;}
    public String getName(){return name;}
    public String getLocation(){return location;}
}
