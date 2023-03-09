package inventoryservice.service;

import inventoryservice.dto.InventoryResponse;
import inventoryservice.model.Inventory;

import java.util.List;

public interface InventoryService {
    public List<InventoryResponse> isInStock(List<String> skuCodes);

    public boolean isInStock(String skuCode);
}
