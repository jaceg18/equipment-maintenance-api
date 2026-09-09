package io.github.jace.equipment_maintenance_api.repository;

import io.github.jace.equipment_maintenance_api.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<Asset, Integer> {}