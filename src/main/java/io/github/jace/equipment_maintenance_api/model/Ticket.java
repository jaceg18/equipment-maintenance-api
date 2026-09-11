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

    // TicketRequest contains the asset ID, but the service resolves the Asset
    // before constructing the Ticket so entity lookup stays out of this class.
    public static Ticket from(TicketRequest request, Asset asset){
        Ticket ticket = new Ticket();
        ticket.description = request.description();
        ticket.priority = request.priority();
        ticket.status = Status.OPEN;
        ticket.asset = asset;
        return ticket;
    }
}
