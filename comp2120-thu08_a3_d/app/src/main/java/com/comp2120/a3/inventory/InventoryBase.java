package com.comp2120.a3.inventory;


public abstract class InventoryBase {
    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public abstract String getName();

    public abstract void onUse();

    public void get(int amount){
        quantity += amount;
    }

    public void use(int amount){
        for (int i = 0; i < amount; i++) {
            use();
        }
    }

    public void use() {
        if (quantity > 0) {
            quantity--;
            onUse();
        }
    }
}
