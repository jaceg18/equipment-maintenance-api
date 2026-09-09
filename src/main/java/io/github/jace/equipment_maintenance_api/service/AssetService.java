package io.github.jace.equipment_maintenance_api.service;

import io.github.jace.equipment_maintenance_api.model.Asset;
import io.github.jace.equipment_maintenance_api.repository.AssetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssetService {

    private final AssetRepository assetRepository;

    public AssetService(AssetRepository assetRepository){this.assetRepository = assetRepository;}

    public List<Asset> getAssets(){return assetRepository.findAll();}

    public Asset createAsset(Asset asset){return assetRepository.save(asset);}

    public Optional<Asset> getAsset(int id){return assetRepository.findById(id);}

}
