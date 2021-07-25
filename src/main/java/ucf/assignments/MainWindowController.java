package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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
    public void addItemButtonClicked(){
        //get TextField data and assign it to a Item
        String sn = itemSerialNumberTextField.getText();
        String name = itemNameTextField.getText();
        BigDecimal value = BigDecimal.valueOf(Double.parseDouble(itemValueTextField.getText()));
        Item item = new Item(sn, name, value);

        //add item to observable list
        if (validateName(itemNameTextField) && validateSN(itemSerialNumberTextField) && validateValue(itemValueTextField)){
            addItem(item);
            outputTextField.setText("Item added successfully.");
        }
        else outputTextField.setText("Check the credentials.");

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
                outputTextField.setText("Removed item successfully.");
            } else outputTextField.setText("Check your credentials.");
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
                    outputTextField.setText("Successfully changed item serial number.");
                } else outputTextField.setText("Make sure the new serial number meets credentials. ");
            } else outputTextField.setText("Check the credentials.");
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
                    outputTextField.setText("Successfully changed item name.");
                } else outputTextField.setText("Make sure the new name meets credentials.");
            } else outputTextField.setText("Check the credentials.");
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
                    outputTextField.setText("Successfully changed item value.");
                } else outputTextField.setText("Make sure the new value meets credentials. ");
            } else outputTextField.setText("Check the credentials.");
        }

        displayList(observableList);
    }
    @FXML
    public void sortByValueButtonClicked(){
        /*
        ObservableList<Item> tempObservableList = observableList;
        ObservableList<Item> sortedObservableList = FXCollections.observableArrayList();

        for (int i = 0; i < observableList.size();i++){
            sortedObservableList.add(getBiggestValue(tempObservableList));
        }

        displayList(sortedObservableList);

         */
        ObservableList<Item> sortedObservableList = FXCollections.observableArrayList();
        int observableListSize = observableList.size();
        for(int i = 0;i < observableListSize;i++){
            sortedObservableList.add(getBiggestValueItem());
        }

        observableList = sortedObservableList;

        displayList(observableList);

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

