package ucf.assignments;

/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Christian Klingbiel
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryTrackerTest extends MainWindowController{

    @Test
    void addItemTest(){
        Item item = new Item("XXXXXXXXXX","inventoryItem", BigDecimal.valueOf(10));
        ObservableList<Item> list = FXCollections.observableArrayList();
        addItem(item,list);
        assert(list.contains(item));
    }

    @Test
    void removeItemTest(){
        Item item = new Item("XXXXXXXXXX","inventoryItem", BigDecimal.valueOf(10));
        ObservableList<Item> list = FXCollections.observableArrayList();
        list.add(item);
        removeItem(list,item);
        assert(!list.contains(item));
    }

    @Test
    void getBiggestValueItemTest(){
        Item item1 = new Item("XXXXXXXXX1","inventoryItem1", BigDecimal.valueOf(10));
        Item item2 = new Item("XXXXXXXXX2","inventoryItem2", BigDecimal.valueOf(500));
        Item item3 = new Item("XXXXXXXXX3","inventoryItem3", BigDecimal.valueOf(20));
        ObservableList<Item> list = FXCollections.observableArrayList();
        list.add(item1);
        list.add(item2);
        list.add(item3);
        assertEquals(getBiggestValueItem(list),item2);

    }

    @Test
    void getSNItemTest(){
        Item item1 = new Item("XXXXXXXXX1","inventoryItem1", BigDecimal.valueOf(10));
        Item item2 = new Item("XXXXXXXXX2","inventoryItem2", BigDecimal.valueOf(500));
        Item item3 = new Item("XXXXXXXXX3","inventoryItem3", BigDecimal.valueOf(20));
        ObservableList<Item> list = FXCollections.observableArrayList();
        list.add(item1);
        list.add(item2);
        list.add(item3);
        assertEquals(getSNItem(list),item3);

    }

    @Test
    void getNameItemTest(){
        Item item1 = new Item("XXXXXXXXX1","inventoryItem1", BigDecimal.valueOf(10));
        Item item2 = new Item("XXXXXXXXX2","inventoryItem2", BigDecimal.valueOf(500));
        Item item3 = new Item("XXXXXXXXX3","inventoryItem3", BigDecimal.valueOf(20));
        ObservableList<Item> list = FXCollections.observableArrayList();
        list.add(item1);
        list.add(item2);
        list.add(item3);
        assertEquals(getNameItem(list),item3);
    }

    @Test
    void validateSNTest(){
        String SN = "XXXXXXXXXX";
        ObservableList<Item> list = FXCollections.observableArrayList();
        assert(validateSN(SN,list));
    }

    @Test
    void validateListDoesNotContainSNTest(){
        String SN = "XXXXXXXXXX";
        ObservableList<Item> list = FXCollections.observableArrayList();
        assert(validateListDoesNotContainSN(SN,list));
    }

    @Test
    void validateNameTest(){
        String SN = "Between2And256CharsInclusive";
        assert(validateName(SN));
    }

    @Test
    void validateLetterOrDigitTest(){
        String lettersAndDigits = "TXB678FStsh129";
        ObservableList<Item> list = FXCollections.observableArrayList();
        assert(validateLetterOrDigit(lettersAndDigits,list));
    }

    @Test
    void validateValueTest(){
        BigDecimal bigDecimal = BigDecimal.valueOf(10.00);
        assert(validateValue(bigDecimal));
    }

    @Test
    void changeSNTest(){
        ObservableList<Item> list = FXCollections.observableArrayList();
        Item item = new Item("XXXXXXXXXX","item",BigDecimal.valueOf(10));
        list.add(item);
        String newSN = "OOOOOOOOOO";
        changeSN(newSN,item,list);
        assertEquals(list.get(0).getSn(),newSN);
    }

    @Test
    void changeNameTest(){
        ObservableList<Item> list = FXCollections.observableArrayList();
        Item item = new Item("XXXXXXXXXX","item",BigDecimal.valueOf(10));
        list.add(item);
        String newName = "itemX";
        changeName(newName,item,list);
        assertEquals(list.get(0).getName(),newName);
    }

    @Test
    void changeValueTest(){
        ObservableList<Item> list = FXCollections.observableArrayList();
        Item item = new Item("XXXXXXXXXX","item",BigDecimal.valueOf(10));
        list.add(item);
        BigDecimal newValue = BigDecimal.valueOf(20);
        changeValue(newValue,item,list);
        assertEquals(list.get(0).getValue(),newValue);
    }


}
