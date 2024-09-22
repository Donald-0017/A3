package com.comp2120.a3.inventory;

/**
 * Currency to trade with NPC
 *
 * @author Jason Xu
 */
public class Gold extends InventoryBase {
    @Override
    public String getName() {
        return "Gold";
    }

    @Override
    public void onUse() {
        // Do nothing
    }
}
