package io.github.jace.equipment_maintenance_api.controller;

import io.github.jace.equipment_maintenance_api.dto.StatusRequest;
import io.github.jace.equipment_maintenance_api.dto.TicketRequest;
import io.github.jace.equipment_maintenance_api.model.Asset;
import io.github.jace.equipment_maintenance_api.model.Priority;
import io.github.jace.equipment_maintenance_api.model.Status;
import io.github.jace.equipment_maintenance_api.model.Ticket;
import io.github.jace.equipment_maintenance_api.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService){this.ticketService = ticketService; }

    @GetMapping
    public List<Ticket> getTickets(@RequestParam(required = false) Status status,
                                   @RequestParam(required = false) Priority priority) {
        return ticketService.getTickets(status, priority);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicket(@PathVariable int id) {
        return ticketService.getTicket(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Ticket> updateStatus(@PathVariable int id, @RequestBody StatusRequest request){
        return ticketService.updateStatus(id, request.getStatus()).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping public Ticket createTicket(@Valid @RequestBody TicketRequest request){return ticketService.createTicket(request);}
}
