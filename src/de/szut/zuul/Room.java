package de.szut.zuul;

import java.util.HashMap;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
public class Room 
{
    private String description;

    private HashMap<String, Room> Exits;

    public HashMap<String, Item> items;

    public Room getExit(String direction){
        return Exits.get(direction);
    }


    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        Exits = new HashMap<String, Room>();
        items = new HashMap<String, Item>();
    }


    public void setExits(String key, Room neighbor)
    {
        Exits.put(key, neighbor);
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }
    public void setItem(Item newItem) {
        this.items.put(newItem.getName(), newItem);
    }
    public Item getItem(String itemName) {
        return items.get(itemName);
    }
    public String getItemString() {
        String itemString = "Items in this room:\n";
        if (!items.values().isEmpty()){
            for (Item item : items.values()) {
                itemString += "- " + item.toString() + "\n";
            }
        } else {
            itemString += "None";
        }

        return itemString;
    }
    public String exitsToString() {
        String returnvalue = "";

        for (String key: Exits.keySet()) {
            returnvalue = returnvalue + key + " ";
        }
        return  returnvalue;
    }

    public String getLongDescription() {
        String returnvalue = "";

        returnvalue = "\n You are " + getDescription() + "\n Exits: ";
        returnvalue = returnvalue + exitsToString() + "\n" +  getItemString();

        return  returnvalue;
    }
    public Item removeItem(String name) {
        Item item = null;
        for (var itm: items.values()) {
            if(itm.getName().equals(name)) {
                item = itm;
            }
        }
        items.remove(item.getName());
        return item;
    }
}
