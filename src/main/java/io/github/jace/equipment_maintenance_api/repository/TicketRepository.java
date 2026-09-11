package io.github.jace.equipment_maintenance_api.repository;

import io.github.jace.equipment_maintenance_api.model.Priority;
import io.github.jace.equipment_maintenance_api.model.Status;
import io.github.jace.equipment_maintenance_api.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findByStatus(Status status);
    List<Ticket> findByPriority(Priority priority);
    List<Ticket> findByStatusAndPriority(Status status, Priority priority);
    List<Ticket> findByAssetId(Integer assetId);

}
