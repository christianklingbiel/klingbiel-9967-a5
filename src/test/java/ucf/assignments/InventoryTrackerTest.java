package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

    /*
    @Test
    void displayListTest(){
        Item item = new Item("XXXXXXXXXX","inventoryItem", BigDecimal.valueOf(10));
        ObservableList<Item> list1 = FXCollections.observableArrayList();
        list1.add(item);
        TableColumn<Item,String> col1 = new TableColumn<>();
        TableColumn<Item,String> col2 = new TableColumn<>();
        TableColumn<Item,BigDecimal> col3 = new TableColumn<>();
        TableView<Item> tableView = new TableView<>();
        tableView.getColumns().addAll(col1,col2,col3);
        displayList(list1, col1, col2, col3, tableView);
        ObservableList<Item> list2;
        list2 = tableView.getItems();
        assertEquals(list1,list2);
    }
     */

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
        Item expectedItem = new Item("OOOOOOOOOO","item",BigDecimal.valueOf(10));
        changeSN(newSN,item,list);

    }
}
