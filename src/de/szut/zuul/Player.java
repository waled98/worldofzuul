package de.szut.zuul;

import java.util.LinkedList;

public class Player {
    private Room currentRoom;

    public double loadCapacity;

    private double currentCapacity;

    public LinkedList<Item> inventory;

    public Player() {
        inventory = new LinkedList<Item>();
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public Boolean takeitem(Item item){
        if(IsTakePossible(item)){
            inventory.add(item);
            return true;
        }
        return false;
    }

    public Item dropItem(String name) {
        Item item = GetItem(name);
        if(item != null) {
            inventory.remove(item);
        }
        return item;
    }

    public String showStatus() {
        String itemString = "Current capacity: " + currentCapacity + "\n";
        if (!inventory.isEmpty()){
            for (Item item : inventory) {
                itemString += "- " + item.toString() + "\n";
            }
        } else {
            itemString += "None";
        }

        return itemString;
    }

    public Item GetItem(String name){
        Item item = null;
        for (var itm: inventory) {
            if(itm.getName().equals(name)){
                item = itm;
            }
        }
        return item;
    }

    private double calculateWeight(Item item){
        return currentCapacity+item.getWeight();
   }

   private Boolean IsTakePossible(Item item){
        if(calculateWeight(item) != loadCapacity) {
            return true;
        }
       return false;
   }
}
