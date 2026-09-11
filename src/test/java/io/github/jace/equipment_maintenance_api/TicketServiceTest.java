package io.github.jace.equipment_maintenance_api;

import io.github.jace.equipment_maintenance_api.dto.TicketRequest;
import io.github.jace.equipment_maintenance_api.exception.AssetNotFoundException;
import io.github.jace.equipment_maintenance_api.model.Asset;
import io.github.jace.equipment_maintenance_api.model.Priority;
import io.github.jace.equipment_maintenance_api.model.Status;
import io.github.jace.equipment_maintenance_api.model.Ticket;
import io.github.jace.equipment_maintenance_api.repository.AssetRepository;
import io.github.jace.equipment_maintenance_api.repository.TicketRepository;

import io.github.jace.equipment_maintenance_api.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @Mock private TicketRepository ticketRepository;

    @Mock private AssetRepository assetRepository;

    private TicketService ticketService;

    @BeforeEach
    void setUp() {
        ticketService = new TicketService(ticketRepository, assetRepository);
    }

    @Test
    void createTicket_WithExistingAsset_SavesTicket() {
        // Arrange
        Asset asset = new Asset();
        asset.setId(1);
        asset.setName("CNC Machine #4");
        asset.setLocation("Building A");

        TicketRequest request = new TicketRequest("Hydraulic leak", Priority.HIGH, 1);

        when(assetRepository.findById(1))
                .thenReturn(Optional.of(asset));

        when(ticketRepository.save(any(Ticket.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Ticket result = ticketService.createTicket(request);

        // Assert
        assertEquals("Hydraulic leak", result.getDescription());
        assertEquals(Priority.HIGH, result.getPriority());
        assertEquals(Status.OPEN, result.getStatus());
        assertEquals(asset, result.getAsset());

        verify(assetRepository).findById(1);
        verify(ticketRepository).save(any(Ticket.class));
    }

    @Test
    void createTicket_WithMissingAsset_ThrowsAssetNotFoundException() {
        // Arrange
        TicketRequest request = new TicketRequest("Something broke", Priority.HIGH, 999);

        when(assetRepository.findById(999))
                .thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(
                AssetNotFoundException.class,
                () -> ticketService.createTicket(request)
        );

        // A ticket should NOT be saved if its asset doesn't exist
        verify(ticketRepository, never()).save(any(Ticket.class));
    }

    @Test
    void getTicketById_WhenTicketExists_ReturnsTicket() {
        // Arrange
        Ticket ticket = new Ticket();
        ticket.setId(1);
        ticket.setDescription("Broken motor");

        when(ticketRepository.findById(1))
                .thenReturn(Optional.of(ticket));

        // Act
        Optional<Ticket> result = ticketService.getTicket(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Broken motor", result.get().getDescription());
    }

    @Test
    void getTicketById_WhenTicketDoesNotExist_ReturnsEmpty() {
        when(ticketRepository.findById(999))
                .thenReturn(Optional.empty());

        Optional<Ticket> result = ticketService.getTicket(999);

        assertTrue(result.isEmpty());
    }

    @Test
    void updateStatus_WhenTicketExists_ChangesAndSavesStatus() {
        // Arrange
        Ticket ticket = new Ticket();
        ticket.setId(1);
        ticket.setStatus(Status.OPEN);

        when(ticketRepository.findById(1))
                .thenReturn(Optional.of(ticket));

        // Act
        Optional<Ticket> result =
                ticketService.updateStatus(1, Status.IN_PROGRESS);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(Status.IN_PROGRESS, result.get().getStatus());

        verify(ticketRepository).save(ticket);
    }

    @Test
    void updateStatus_WhenTicketDoesNotExist_DoesNotSave() {
        when(ticketRepository.findById(999))
                .thenReturn(Optional.empty());

        Optional<Ticket> result =
                ticketService.updateStatus(999, Status.RESOLVED);

        assertTrue(result.isEmpty());

        verify(ticketRepository, never()).save(any(Ticket.class));
    }

    @Test
    void getTickets_WithNoFilters_ReturnsAllTickets() {
        List<Ticket> tickets = List.of(
                new Ticket(),
                new Ticket()
        );

        when(ticketRepository.findAll()).thenReturn(tickets);

        List<Ticket> result = ticketService.getTickets(null, null);

        assertEquals(2, result.size());

        verify(ticketRepository).findAll();
    }

    @Test
    void getTickets_WithStatusFilter_FiltersByStatus() {
        Ticket ticket = new Ticket();
        ticket.setStatus(Status.OPEN);

        when(ticketRepository.findByStatus(Status.OPEN))
                .thenReturn(List.of(ticket));

        List<Ticket> result =
                ticketService.getTickets(Status.OPEN, null);

        assertEquals(1, result.size());
        assertEquals(Status.OPEN, result.get(0).getStatus());

        verify(ticketRepository).findByStatus(Status.OPEN);
    }

    @Test
    void getTickets_WithPriorityFilter_FiltersByPriority() {
        Ticket ticket = new Ticket();
        ticket.setPriority(Priority.HIGH);

        when(ticketRepository.findByPriority(Priority.HIGH))
                .thenReturn(List.of(ticket));

        List<Ticket> result =
                ticketService.getTickets(null, Priority.HIGH);

        assertEquals(1, result.size());
        assertEquals(Priority.HIGH, result.get(0).getPriority());

        verify(ticketRepository).findByPriority(Priority.HIGH);
    }

    @Test
    void getTickets_WithStatusAndPriority_FiltersByBoth() {
        Ticket ticket = new Ticket();
        ticket.setStatus(Status.OPEN);
        ticket.setPriority(Priority.HIGH);

        when(ticketRepository.findByStatusAndPriority(
                Status.OPEN,
                Priority.HIGH
        )).thenReturn(List.of(ticket));

        List<Ticket> result =
                ticketService.getTickets(Status.OPEN, Priority.HIGH);

        assertEquals(1, result.size());

        verify(ticketRepository)
                .findByStatusAndPriority(Status.OPEN, Priority.HIGH);
    }

    @Test
    void shouldReturnTicketsForAsset() {
        Integer assetId = 1;

        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();

        List<Ticket> expectedTickets = List.of(ticket1, ticket2);

        when(assetRepository.existsById(assetId)).thenReturn(true);
        when(ticketRepository.findByAssetId(assetId)).thenReturn(expectedTickets);

        List<Ticket> result = ticketService.getTicketsByAsset(assetId);

        assertEquals(2, result.size());
        assertEquals(expectedTickets, result);

        verify(assetRepository).existsById(assetId);
        verify(ticketRepository).findByAssetId(assetId);
    }

    @Test
    void shouldThrowExceptionWhenAssetDoesNotExist() {
        Integer assetId = 999;

        when(assetRepository.existsById(assetId)).thenReturn(false);

        assertThrows(
                AssetNotFoundException.class,
                () -> ticketService.getTicketsByAsset(assetId)
        );

        verify(assetRepository).existsById(assetId);
        verify(ticketRepository, never()).findByAssetId(assetId);
    }
}
