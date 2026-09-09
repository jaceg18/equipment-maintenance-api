package io.github.jace.equipment_maintenance_api.service;

import io.github.jace.equipment_maintenance_api.dto.TicketRequest;
import io.github.jace.equipment_maintenance_api.exception.AssetNotFoundException;
import io.github.jace.equipment_maintenance_api.model.Asset;
import io.github.jace.equipment_maintenance_api.model.Priority;
import io.github.jace.equipment_maintenance_api.model.Status;
import io.github.jace.equipment_maintenance_api.model.Ticket;
import io.github.jace.equipment_maintenance_api.repository.AssetRepository;
import io.github.jace.equipment_maintenance_api.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final AssetRepository assetRepository;

    public TicketService(TicketRepository ticketRepository, AssetRepository assetRepository){
        this.ticketRepository = ticketRepository;
        this.assetRepository = assetRepository;
    }

    public List<Ticket> getTickets(Status status, Priority priority) {
        if (status != null && priority != null) return ticketRepository.findByStatusAndPriority(status, priority);
        if (status != null) return ticketRepository.findByStatus(status);
        if (priority != null) return ticketRepository.findByPriority(priority);
        return ticketRepository.findAll();
    }

    public Ticket createTicket(TicketRequest request) {
        Asset asset = assetRepository.findById(request.getAssetId())
                .orElseThrow(() -> new AssetNotFoundException(request.getAssetId()));
        return ticketRepository.save(new Ticket().build(request, asset));
    }

    public Optional<Ticket> getTicket(int id){return ticketRepository.findById(id);}

    public Optional<Ticket> updateStatus(int id, Status newStatus) {
        Optional<Ticket> target = ticketRepository.findById(id);
        target.ifPresent(ticket -> ticketRepository.save(ticket.setStatus(newStatus)));
        return target;
    }
}
