package com.comp2120.a3.inventory;

/**
 * A weapon to equip, and add player's attack
 *
 * @author Jason Xu
 */
public class Sword extends InventoryBase{
    @Override
    public String getName() {
        return "Sword";
    }

    @Override
    public void onUse() {
        //TODO wait til PlayerSystem is implemented, so we can add equipment data to it
    }
}
