package io.github.jace.equipment_maintenance_api.model;

import io.github.jace.equipment_maintenance_api.dto.TicketRequest;
import jakarta.persistence.*;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Asset asset;

    private String description;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Ticket() {}

    public void setId(int id){this.id = id;}
    public void setDescription(String description){this.description = description;}
    public void setPriority(Priority priority){this.priority = priority;}
    public Ticket setStatus(Status status){this.status = status; return this;}
    public void setAsset(Asset asset){this.asset = asset;}

    public int getId(){return id;}
    public String getDescription(){return description;}
    public Priority getPriority(){return priority;}
    public Status getStatus(){return status;}
    public Asset getAsset(){return asset;}

    public Ticket build(TicketRequest request, Asset asset){
        this.description = request.getDescription();
        this.priority = request.getPriority();
        this.status = Status.OPEN;
        this.asset = asset;
        return this;
    }
}
