package io.github.jace.equipment_maintenance_api.dto;

import io.github.jace.equipment_maintenance_api.model.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TicketRequest(
        @NotBlank String description,
        @NotNull Priority priority,
        @NotNull Integer assetId
) {}
