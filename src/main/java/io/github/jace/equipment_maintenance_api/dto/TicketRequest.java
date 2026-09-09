package io.github.jace.equipment_maintenance_api.dto;

import io.github.jace.equipment_maintenance_api.model.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TicketRequest {

    @NotBlank private String description;
    @NotNull private Priority priority;
    @NotNull private Integer assetId;

    public void setDescription(String description){this.description = description;}
    public void setPriority(Priority priority){this.priority = priority;}
    public void setAssetId(int assetId){this.assetId = assetId;}

    public String getDescription(){return description;}
    public Priority getPriority(){return priority;}
    public int getAssetId(){return assetId;}

}
