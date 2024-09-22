package com.comp2120.a3.inventory;

/**
 * An inventory that unlocks a nearby chest
 *
 * @author Jason Xu
 */
public class Key extends InventoryBase{
    @Override
    public String getName() {
        return "Key";
    }

    @Override
    public void onUse() {
        //TODO unlock nearby chest - require map system to be done
    }
}
