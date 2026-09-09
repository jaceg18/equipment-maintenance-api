package io.github.jace.equipment_maintenance_api.dto;

import io.github.jace.equipment_maintenance_api.model.Status;

public class StatusRequest {
    private Status status;

    public Status getStatus(){return status;}

    public void setStatus(Status status){this.status = status;}
}
