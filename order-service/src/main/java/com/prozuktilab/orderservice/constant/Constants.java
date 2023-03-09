package com.prozuktilab.orderservice.constant;

public class Constants {
    public static final String INVENTORY_SERVICE = "inventory-service";
    public static final String INVENTORY_BASE_URL = "/api/inventory";
    public static final String INVENTORY_SERVICE_URL = new StringBuilder()
            .append("http://")
            .append(INVENTORY_SERVICE)
            .append(INVENTORY_BASE_URL)
            .toString();
}
