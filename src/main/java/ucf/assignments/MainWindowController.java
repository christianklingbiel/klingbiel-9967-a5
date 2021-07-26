package ucf.assignments;

/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Christian Klingbiel
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.math.BigDecimal;

public class MainWindowController {

    //create javafx controls in class to correspond with java program
    public ObservableList<Item> observableList = FXCollections.observableArrayList();

    @FXML
    public TableView<Item> itemsTableView;

    @FXML
    public TableColumn<Item, String> itemSerialNumberColumn;

    @FXML
    public TableColumn<Item, String> itemNameColumn;

    @FXML
    public TableColumn<Item, BigDecimal> itemValueColumn;

    @FXML
    public TextField itemNameTextField;

    @FXML
    public TextField itemValueTextField;

    @FXML
    public TextField itemSerialNumberTextField;

    @FXML
    public TextField newPropertyTextField;

    @FXML
    public TextField outputTextField;

    @FXML
    public TextField searchTextField;

    @FXML
    public void addItemButtonClicked(){
        //get TextField data and assign it to a Item
        String sn = itemSerialNumberTextField.getText();
        String name = itemNameTextField.getText();
        BigDecimal value = BigDecimal.valueOf(Double.parseDouble(itemValueTextField.getText()));
        Item item = new Item(sn, name, value);

        //add item to observable list
        String SN = itemSerialNumberTextField.getText();
        if (validateName(name) && validateSN(SN, observableList) && validateValue(value)){
            addItem(item, observableList);
            outputTextField.setText("Added item");
        }
        else outputTextField.setText("Check inputs");

        //display the list
        displayList(observableList, itemSerialNumberColumn, itemNameColumn, itemValueColumn,  itemsTableView);
    }

    @FXML
    public void removeItemButtonClicked(){
        //get TextField data and assign it to a Item
        String sn = itemSerialNumberTextField.getText();
        String name = itemNameTextField.getText();
        BigDecimal value = BigDecimal.valueOf(Double.parseDouble(itemValueTextField.getText()));
        Item item = new Item(sn, name, value);

        //check if the item is in the list
        for (Item element : observableList) {
            if (element.getSn().equals(item.getSn())) {
                outputTextField.setText("Removed item");
            } else outputTextField.setText("Check input");
        }

        //remove item from list
        removeItem(observableList, item);

        //display the list
        displayList(observableList, itemSerialNumberColumn, itemNameColumn, itemValueColumn,  itemsTableView);
    }

    @FXML
    public void changeSNButtonClicked(){
        //get the TextField data and assign it to an item
        String sn = itemSerialNumberTextField.getText();
        String name = itemNameTextField.getText();
        BigDecimal value = BigDecimal.valueOf(Double.parseDouble(itemValueTextField.getText()));
        Item item = new Item(sn, name, value);

        //get the new serial number from the TextField
        String newSN = newPropertyTextField.getText();

        //find item in list and change the serial number to newSN
        for (Item element : observableList) {
            if (sn.equalsIgnoreCase(element.getSn())) {
                if (validateSN(sn,observableList)) {
                    changeSN(newSN, item, observableList);
                    outputTextField.setText("Changed Item SN");
                } else outputTextField.setText("Check input");
            } else outputTextField.setText("Check input");
        }

        //display the list
        displayList(observableList, itemSerialNumberColumn, itemNameColumn, itemValueColumn,  itemsTableView);
    }

    @FXML
    public void changeNameButtonClicked(){
        //get data from TextFields
        String sn = itemSerialNumberTextField.getText();
        String name = itemNameTextField.getText();
        BigDecimal value = BigDecimal.valueOf(Double.parseDouble(itemValueTextField.getText()));
        Item item = new Item(sn, name, value);

        //get new name from TextField
        String newName = newPropertyTextField.getText();

        //find item's serial number in list and replace it's name with newName
        for (Item element : observableList) {
            if (sn.equalsIgnoreCase(element.getSn())) {
                if (validateName(newName)) {
                    changeName(newName, item, observableList);
                    outputTextField.setText("Changed item name");
                } else outputTextField.setText("Check input");
            } else outputTextField.setText("Check input");
        }

        //display the list
        displayList(observableList, itemSerialNumberColumn, itemNameColumn, itemValueColumn,  itemsTableView);
    }

    @FXML
    public void changeValueButtonClicked(){
        //get data from TextFields and assign it to a new item
        String sn = itemSerialNumberTextField.getText();
        String name = itemNameTextField.getText();
        BigDecimal value = BigDecimal.valueOf(Double.parseDouble(itemValueTextField.getText()));
        Item item = new Item(sn, name, value);

        //get newValue from TextField
        BigDecimal newValue = BigDecimal.valueOf(Double.parseDouble(newPropertyTextField.getText()));

        //find item in list and replace the value
        for (Item element : observableList) {
            if (sn.equalsIgnoreCase(element.getSn())) {
                if (validateValue(value)) {
                    changeValue(newValue, item, observableList);
                    outputTextField.setText("Changed item value");
                } else outputTextField.setText("Check input");
            } else outputTextField.setText("Check input");
        }

        //display the list
        displayList(observableList, itemSerialNumberColumn, itemNameColumn, itemValueColumn,  itemsTableView);
    }

    @FXML
    public void sortByValueButtonClicked(){
        //initialize new list and use getBiggestValueItem() to return the proper item from the list and add to new list
        ObservableList<Item> sortedObservableList = FXCollections.observableArrayList();
        int observableListSize = observableList.size();
        for(int i = 0;i < observableListSize;i++){
            sortedObservableList.add(getBiggestValueItem(observableList));
        }

        //make old list equal to new list
        observableList = sortedObservableList;

        //display list
        displayList(observableList, itemSerialNumberColumn, itemNameColumn, itemValueColumn,  itemsTableView);

        //set an output text
        outputTextField.setText("Sorted by value");

    }

    @FXML
    public void sortBySNButtonClicked(){
        //make new list and add items alphabetically to new list
        ObservableList<Item> sortedObservableList = FXCollections.observableArrayList();
        int observableListSize = observableList.size();
        for(int i = 0;i < observableListSize;i++){
            sortedObservableList.add(getSNItem(observableList));
        }

        //make old list equal to new list
        observableList = sortedObservableList;

        //display list
        displayList(observableList, itemSerialNumberColumn, itemNameColumn, itemValueColumn,  itemsTableView);

        //set output text
        outputTextField.setText("Sorted by SN");

    }

    @FXML
    public void sortByNameButtonClicked(){
        //create new list and add the names and corresponding items from the old list into the new list
        ObservableList<Item> sortedObservableList = FXCollections.observableArrayList();
        int observableListSize = observableList.size();
        for(int i = 0;i < observableListSize;i++){
            sortedObservableList.add(getNameItem(observableList));
        }

        //make old list equal to new list
        observableList = sortedObservableList;

        //display list
        displayList(observableList, itemSerialNumberColumn, itemNameColumn, itemValueColumn,  itemsTableView);

        //set text output
        outputTextField.setText("Sorted by name");

    }

    @FXML
    public void searchBySerialNumberButtonClicked(){
        //create new list and add item to list if list.contains(string)
        ObservableList<Item> newSearchList = FXCollections.observableArrayList();
        for (Item item : observableList) {
            if (item.getSn().contains(searchTextField.getText())) {
                newSearchList.add(item);
            }
        }

        //display list
        displayList(newSearchList, itemSerialNumberColumn, itemNameColumn, itemValueColumn,  itemsTableView);
    }

    @FXML
    public void searchByNameButtonClicked(){
        //create new list and ass item to list if list.contains(string)
        ObservableList<Item> newSearchList = FXCollections.observableArrayList();
        for (Item item : observableList) {
            if (item.getName().contains(searchTextField.getText())) {
                newSearchList.add(item);
            }
        }

        //display list
        displayList(newSearchList, itemSerialNumberColumn, itemNameColumn, itemValueColumn,  itemsTableView);
    }

    @FXML
    public void saveAsMenuItemAction(){
        //create fileChooser and apply options to make it a TSV, HTML, or JSON
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a folder to place the new save");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TSV, HTML, or JSON ","*.txt","*.html","*.json"));

        //get text from fileChooser and create a corresponding file and add data to it
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null){
            fileChooser.showSaveDialog(null);
        }else{
            System.out.println("file is not valid");
        }
    }

    @FXML
    public void loadMenuItemAction(){
        //load a fileChooser

        //get selected

        //display on the TableView
    }

    public Item getBiggestValueItem(ObservableList<Item> list){
        //make a max variable and item variable and assign the greatest value item
        BigDecimal max = BigDecimal.valueOf(0);
        Item maxValueItem = new Item("","",max);
        for (Item item : list) {
            if (item.value.compareTo(max) > 0) {
                max = item.value;
            }
        }

        //remove max value item from old list
        for (int i = 0;i < list.size();i++){
            if (list.get(i).value.compareTo(max) == 0){
                maxValueItem = list.get(i);
                removeItem(list, list.get(i));
                i--;
            }
        }
        return maxValueItem;
    }

    public Item getSNItem(ObservableList<Item> list){
        //create string and assign it the highest alphabetical value
        String priority = "";
        Item priorityItem = new Item(priority,"",BigDecimal.valueOf(0));
        for (Item item : list) {
            if (item.sn.compareToIgnoreCase(priority) > 0) {
                priority = item.sn;
            }
        }
        //remove item from old list
        for (int i = 0;i < list.size();i++){
            if (list.get(i).sn.compareToIgnoreCase(priority) == 0){
                priorityItem = list.get(i);
                removeItem(list, list.get(i));
                i--;
            }
        }
        return priorityItem;
    }

    public Item getNameItem(ObservableList<Item> list){
        //create new item and set the  highest alphabetical value to it
        String priority = "";
        Item priorityItem = new Item("",priority,BigDecimal.valueOf(0));
        for (Item item : list) {
            if (item.name.compareToIgnoreCase(priority) > 0) {
                priority = item.name;
            }
        }
        //remove item from old list
        for (int i = 0;i < list.size();i++){
            if (list.get(i).name.compareToIgnoreCase(priority) == 0){
                priorityItem = list.get(i);
                removeItem(list, list.get(i));
                i--;
            }
        }
        return priorityItem;
    }

    public void addItem(Item item, ObservableList<Item> list){
        //add item to list
        list.add(item);
    }

    public void removeItem(ObservableList<Item> list, Item item){
        //search list for matching serial numbers and remove corresponding item
        for (int i = 0;i < list.size();i++) {
            if (list.get(i).getSn().equals(item.getSn())) {
                list.remove(i);
                i--;
            }
        }
    }

    public void displayList(ObservableList<Item> list, TableColumn col1, TableColumn col2, TableColumn col3, TableView<Item> tableView){
        //set the cell value for each column
        col1.setCellValueFactory(new PropertyValueFactory<>("sn"));
        col2.setCellValueFactory(new PropertyValueFactory<>("name"));
        col3.setCellValueFactory(new PropertyValueFactory<>("value"));

        //set the items to the tableView
        tableView.setItems(list);
    }

    public boolean validateSN(String t, ObservableList<Item> list){
        //validate whether the serial number is 10 characters, only contains letters and digits, and doesn't already exist in the list
        return t.length() == 10
                && validateLetterOrDigit(t, list)
                && validateListDoesNotContainSN(t, list);
    }

    public boolean validateListDoesNotContainSN(String t, ObservableList<Item> list){
        //returns false if list contains item of serial number t
        for (Item item : list) {
            if (item.sn.equalsIgnoreCase(t))
                return false;
        }
        return true;
    }

    public boolean validateName(String name){
        //returns true if 2 <= name.length() <= 256
        return name.length() >= 2 && name.length() <= 256;
    }

    public boolean validateLetterOrDigit(String t, ObservableList<Item> list){
        //returns false once it finds a character that isn't a letter or digit
        for (int i = 0;i < list.size();i++) {
            if ((!Character.isLetterOrDigit(t.charAt(i))))
                return false;
        }
        return true;
    }

    public boolean validateValue(BigDecimal bigDecimal){
        //returns true if value is greater than 0
        return bigDecimal.compareTo(BigDecimal.valueOf(0.00)) > 0;
    }

    public void changeSN(String newSN, Item item, ObservableList<Item> list){
        //remove item from list, create new item with same name and value but different serial number,and add item back to list
        removeItem(list, item);
        Item itemTemp = new Item(newSN, item.getName(), item.getValue());
        addItem(itemTemp, list);
    }

    public void changeName(String newName, Item item, ObservableList<Item> list){
        //remove item from list, create new item with same serial number and value but different name,and add item back to list
        removeItem(list, item);
        Item itemTemp = new Item(item.getSn(), newName, item.getValue());
        addItem(itemTemp, list);
    }

    public void changeValue(BigDecimal newValue, Item item, ObservableList<Item> list){
        //remove item from list, create new item with same name and serial number but different value,and add item back to list
        removeItem(list, item);
        Item itemTemp = new Item(item.getSn(), item.getName(), newValue);
        addItem(itemTemp, list);
    }


    void saveAsButtonClicked(){
        //SimpleStringProperty filename = FileChooser.getName();
        //SimpleStringProperty filetype = FileChooser.getType();

        //if(filetype.equals("CSV")){
        //saveAsCSV(filename + filetype);
        //}
    }

    public void saveAsTSV(String filename){
        /*
        open up filename
        for each item in the item model
            write the item to file as sn,name,price
        close file
         */
    }

    public void saveAsHTML(String filename){
        /*
        open up filename
        for each item in the item model
            write the item to file as sn,name,price
        close file
         */
    }

    public void saveAsJSON(String filename){
        /*
        open up filename
        for each item in the item model
            write the item to file as sn,name,price
        close file
         */
    }
}

