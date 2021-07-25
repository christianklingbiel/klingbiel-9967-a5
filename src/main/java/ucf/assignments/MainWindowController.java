package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.math.BigDecimal;

public class MainWindowController {

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
        if (validateName(itemNameTextField) && validateSN(itemSerialNumberTextField) && validateValue(itemValueTextField)){
            addItem(item);
            outputTextField.setText("Added item");
        }
        else outputTextField.setText("Check inputs");

        displayList(observableList);
    }

    @FXML
    public void removeItemButtonClicked(){
        //get TextField data and assign it to a Item
        String sn = itemSerialNumberTextField.getText();
        String name = itemNameTextField.getText();
        BigDecimal value = BigDecimal.valueOf(Double.parseDouble(itemValueTextField.getText()));
        Item item = new Item(sn, name, value);

        for (Item element : observableList) {
            if (element.getSn().equals(item.getSn())) {
                outputTextField.setText("Removed item");
            } else outputTextField.setText("Check input");
        }

        removeItem(observableList, item);

        displayList(observableList);
    }

    @FXML
    public void changeSNButtonClicked(){
        String sn = itemSerialNumberTextField.getText();
        String name = itemNameTextField.getText();
        BigDecimal value = BigDecimal.valueOf(Double.parseDouble(itemValueTextField.getText()));
        Item item = new Item(sn, name, value);

        String newSN = newPropertyTextField.getText();

        for (Item element : observableList) {
            if (sn.equalsIgnoreCase(element.getSn())) {
                if (validateSN(newPropertyTextField)) {
                    changeSN(newSN, item);
                    outputTextField.setText("Changed Item SN");
                } else outputTextField.setText("Check input");
            } else outputTextField.setText("Check input");
        }

        displayList(observableList);
    }

    @FXML
    public void changeNameButtonClicked(){
        String sn = itemSerialNumberTextField.getText();
        String name = itemNameTextField.getText();
        BigDecimal value = BigDecimal.valueOf(Double.parseDouble(itemValueTextField.getText()));
        Item item = new Item(sn, name, value);

        String newName = newPropertyTextField.getText();

        for (Item element : observableList) {
            if (sn.equalsIgnoreCase(element.getSn())) {
                if (validateName(newPropertyTextField)) {
                    changeName(newName, item);
                    outputTextField.setText("Changed item name");
                } else outputTextField.setText("Check input");
            } else outputTextField.setText("Check input");
        }

        displayList(observableList);
    }

    @FXML
    public void changeValueButtonClicked(){

        String sn = itemSerialNumberTextField.getText();
        String name = itemNameTextField.getText();
        BigDecimal value = BigDecimal.valueOf(Double.parseDouble(itemValueTextField.getText()));
        Item item = new Item(sn, name, value);

        BigDecimal newValue = BigDecimal.valueOf(Double.parseDouble(newPropertyTextField.getText()));

        for (Item element : observableList) {
            if (sn.equalsIgnoreCase(element.getSn())) {
                if (validateValue(newPropertyTextField)) {
                    changeValue(newValue, item);
                    outputTextField.setText("Changed item value");
                } else outputTextField.setText("Check input");
            } else outputTextField.setText("Check input");
        }

        displayList(observableList);
    }

    @FXML
    public void sortByValueButtonClicked(){
        ObservableList<Item> sortedObservableList = FXCollections.observableArrayList();
        int observableListSize = observableList.size();
        for(int i = 0;i < observableListSize;i++){
            sortedObservableList.add(getBiggestValueItem());
        }

        observableList = sortedObservableList;

        displayList(observableList);

        outputTextField.setText("Sorted by value");

    }

    @FXML
    public void sortBySNButtonClicked(){
        ObservableList<Item> sortedObservableList = FXCollections.observableArrayList();
        int observableListSize = observableList.size();
        for(int i = 0;i < observableListSize;i++){
            sortedObservableList.add(getSNItem());
        }

        observableList = sortedObservableList;

        displayList(observableList);

        outputTextField.setText("Sorted by SN");

    }

    @FXML
    public void sortByNameButtonClicked(){
        ObservableList<Item> sortedObservableList = FXCollections.observableArrayList();
        int observableListSize = observableList.size();
        for(int i = 0;i < observableListSize;i++){
            sortedObservableList.add(getNameItem());
        }

        observableList = sortedObservableList;

        displayList(observableList);

        outputTextField.setText("Sorted by name");

    }

    @FXML
    public void searchBySerialNumberButtonClicked(){
        ObservableList<Item> newSearchList = FXCollections.observableArrayList();
        for (int i = 0;i < observableList.size();i++){
            if (observableList.get(i).getSn().contains(searchTextField.getText())){
                newSearchList.add(observableList.get(i));
            }
        }
        displayList(newSearchList);
    }

    @FXML
    public void searchByNameButtonClicked(){
        ObservableList<Item> newSearchList = FXCollections.observableArrayList();
        for (int i = 0;i < observableList.size();i++){
            if (observableList.get(i).getName().contains(searchTextField.getText())){
                newSearchList.add(observableList.get(i));
            }
        }
        displayList(newSearchList);
    }

    @FXML
    public void saveAsMenuItemAction(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a folder to place the new save");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TSV, HTML, or JSON","*.txt","*.html","*.json"));


        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null){
            fileChooser.showSaveDialog(null);
        }else{
            System.out.println("file is not valid");
        }
    }

    public Item getBiggestValueItem(){
        BigDecimal max = BigDecimal.valueOf(0);
        Item maxValueItem = new Item("","",max);
        for (int i = 0;i < observableList.size();i++){
            if (observableList.get(i).value.compareTo(max) > 0){
                max = observableList.get(i).value;
            }
        }
        for (int i = 0;i < observableList.size();i++){
            if (observableList.get(i).value.compareTo(max) == 0){
                maxValueItem = observableList.get(i);
                removeItem(observableList, observableList.get(i));
                i--;
            }
        }
        return maxValueItem;
    }

    public Item getSNItem(){
        String priority = "";
        Item priorityItem = new Item(priority,"",BigDecimal.valueOf(0));
        for (int i = 0;i < observableList.size();i++){
            if (observableList.get(i).sn.compareToIgnoreCase(priority) > 0){
                priority = observableList.get(i).sn;
            }
        }
        for (int i = 0;i < observableList.size();i++){
            if (observableList.get(i).sn.compareToIgnoreCase(priority) == 0){
                priorityItem = observableList.get(i);
                removeItem(observableList, observableList.get(i));
                i--;
            }
        }
        return priorityItem;
    }

    public Item getNameItem(){
        String priority = "";
        Item priorityItem = new Item("",priority,BigDecimal.valueOf(0));
        for (int i = 0;i < observableList.size();i++){
            if (observableList.get(i).name.compareToIgnoreCase(priority) > 0){
                priority = observableList.get(i).name;
            }
        }
        for (int i = 0;i < observableList.size();i++){
            if (observableList.get(i).name.compareToIgnoreCase(priority) == 0){
                priorityItem = observableList.get(i);
                removeItem(observableList, observableList.get(i));
                i--;
            }
        }
        return priorityItem;
    }

    public void addItem(Item item){
        observableList.add(item);
    }

    public void removeItem(ObservableList<Item> list, Item item){
        for (int i = 0;i < list.size();i++) {
            if (list.get(i).getSn().equals(item.getSn())) {
                list.remove(i);
                i--;
            }
        }
    }

    public void displayList(ObservableList<Item> list){
        itemSerialNumberColumn.setCellValueFactory(new PropertyValueFactory<>("sn"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        itemValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        itemsTableView.setItems(list);
    }

    public boolean validateSN(TextField t){
        return t.getText().length() == 10
                && validateLetterOrDigit(t)
                && validateListDoesNotContainSN(t);
    }
    public boolean validateListDoesNotContainSN(TextField t){
        for (Item item : observableList) {
            if (item.sn.equalsIgnoreCase(t.getText()))
                return false;
        }
        return true;
    }
    public boolean validateName(TextField t){
        String name = t.getText();
        return name.length() >= 2 && name.length() <= 256;
    }
    public boolean validateLetterOrDigit(TextField t){
        for (int i = 0;i < observableList.size();i++) {
            if ((!Character.isLetterOrDigit(t.getText().charAt(i))))
                return false;
        }
        return true;
    }
    public boolean validateValue(TextField t){
        return BigDecimal.valueOf(Double.parseDouble(t.getText())).compareTo(BigDecimal.valueOf(0.00)) > 0;
    }
    public void changeSN(String newSN, Item item){
        removeItem(observableList, item);
        Item itemTemp = new Item(newSN, item.getName(), item.getValue());
        addItem(itemTemp);
    }

    public void changeName(String newName, Item item){
        removeItem(observableList, item);
        Item itemTemp = new Item(item.getSn(), newName, item.getValue());
        addItem(itemTemp);
    }

    public void changeValue(BigDecimal newValue, Item item){
        removeItem(observableList, item);
        Item itemTemp = new Item(item.getSn(), item.getName(), newValue);
        addItem(itemTemp);
    }


    void saveAsButtonClicked(){
        //SimpleStringProperty filename = FileChooser.getName();
        //SimpleStringProperty filetype = FileChooser.getType();

        //if(filetype.equals("CSV")){
        //saveAsCSV(filename + filetype);
        //}
    }

    public void saveAsCSV(String filename){
        /*
        open up filename
        for each item in the item model
            write the item to file as sn,name,price
        close file
         */
    }
}

