package io.github.jace.equipment_maintenance_api.controller;

import io.github.jace.equipment_maintenance_api.model.Asset;
import io.github.jace.equipment_maintenance_api.model.Ticket;
import io.github.jace.equipment_maintenance_api.service.AssetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/assets")
public class AssetController {

    private final AssetService assetService;

    public AssetController(AssetService assetService){this.assetService = assetService;}

    @GetMapping public List<Asset> getAssets(){return assetService.getAssets();}

    @GetMapping("/{id}")
    public ResponseEntity<Asset> getAsset(@PathVariable int id) {
        return assetService.getAsset(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping public Asset createAsset(@RequestBody Asset asset){return assetService.createAsset(asset);}

}
