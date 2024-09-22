package com.comp2120.a3.system;

import com.comp2120.a3.config.InventorySystemConfig;
import com.comp2120.a3.inventory.InventoryBase;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Inventory system to manage inventories, i.e. Gold, Key, Sword, etc.
 */
public class InventorySystem extends SystemBase<InventorySystemConfig> {
    private Map<Class<? extends InventoryBase>, InventoryBase> inventories;

    public InventorySystem() {
        super(InventorySystemConfig.class);
    }

    private <T extends InventoryBase> T getInventory(Class<T> inventoryClass) {
        if (!inventories.containsKey(inventoryClass)) {
            try {
                // Instantiate the inventory
                InventoryBase inventory = inventoryClass.getDeclaredConstructor().newInstance();
                // Add the inventory to the map
                inventories.put(inventoryClass, inventory);
            } catch (Exception e) {
                throw new RuntimeException("Inventory instantiation failed: " + inventoryClass.getName());
            }
        }

        InventoryBase inventory = inventories.get(inventoryClass);
        // Safe cast
        if (inventoryClass.isInstance(inventory)) {
            return inventoryClass.cast(inventory);  // Safe cast
        } else {
            throw new IllegalArgumentException("Type mismatch: " + inventoryClass.getName());
        }
    }

    /**
     * Get the number of non-empty inventories
     *
     * @return the number of non-empty inventories
     */
    public int nonEmptyInventoryCount() {
        int sum = 0;
        for (InventoryBase inventory : inventories.values()) {
            if (inventory.getQuantity() > 0) {
                sum++;
            }
        }

        return sum;
    }

    /**
     * Get the list of non-empty inventory classes
     *
     * @return the list of non-empty inventory classes
     */
    public List<Class<? extends InventoryBase>> getNonEmptyInventories() {
        List<Class<? extends InventoryBase>> nonEmptyInventories = new ArrayList<>();
        for (Map.Entry<Class<? extends InventoryBase>, InventoryBase> entry : inventories.entrySet()) {
            if (entry.getValue().getQuantity() > 0) {
                nonEmptyInventories.add(entry.getKey());
            }
        }

        return nonEmptyInventories;
    }


    /**
     * Get the quantity of the inventory the player holds
     *
     * @param inventoryClass the class of the inventory
     * @param <T>            the type of the inventory
     * @return the quantity of the inventory
     * @author Jason Xu
     */
    public <T extends InventoryBase> int getInventoryQuantity(Class<T> inventoryClass) {
        T inventory = getInventory(inventoryClass);
        return inventory.getQuantity();
    }

    /**
     * Get the name of the inventory
     *
     * @param inventoryClass the class of the inventory
     * @param <T>            the type of the inventory
     * @return the name of the inventory
     * @author Jason Xu
     */
    public <T extends InventoryBase> String getInventoryName(Class<T> inventoryClass) {
        T inventory = getInventory(inventoryClass);
        return inventory.getName();
    }

    /**
     * Obtain the inventory
     *
     * @param inventoryClass the class of the inventory
     * @param amount         the amount of the inventory to obtain
     * @param <T>            the type of the inventory
     * @author Jason Xu
     */
    public <T extends InventoryBase> void obtainInventory(Class<T> inventoryClass, int amount) {
        T inventory = getInventory(inventoryClass);
        inventory.get(amount);
    }

    /**
     * Use the inventory
     *
     * @param inventoryClass the class of the inventory
     * @param amount         the amount of the inventory to use
     * @param <T>            the type of the inventory
     * @author Jason Xu
     */
    public <T extends InventoryBase> void useInventory(Class<T> inventoryClass, int amount) {
        T inventory = getInventory(inventoryClass);
        inventory.use(amount);
    }

    @Override
    public String configPath() {
        return "inventory_system.json";
    }

    @Override
    public void start() {
        // initialise the inventories map
        inventories = new LinkedHashMap<>();
    }

    @Override
    public void stop() {
        // Do nothing
    }
}
