package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;

public class MainWindowController {

    public final ObservableList<Item> observableList = FXCollections.observableArrayList();

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
    public TextField outputTextField;

    @FXML
    public void addItemButtonClicked(){
        //get TextField data and assign it to a Item
        String sn = itemSerialNumberTextField.getText();
        String name = itemNameTextField.getText();
        BigDecimal value = BigDecimal.valueOf(Double.parseDouble(itemValueTextField.getText()));
        Item item = new Item(sn, name, value);

        //add item to observable list
        if (validateName() && validateSN() && validateValue()){
            addNewItem(item);
            outputTextField.setText("Item added successfully.");
        }
        else outputTextField.setText("Check the credentials.");

        displayList();
    }

    public void addNewItem(Item item){
        observableList.add(item);
    }

    public void displayList(){
        itemSerialNumberColumn.setCellValueFactory(new PropertyValueFactory<>("sn"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        itemValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        itemsTableView.setItems(observableList);
    }

    public boolean validateSN(){
        if (itemSerialNumberTextField.getText().length() == 10 && validateLetterOrDigit() && validateListDoesNotContainSN())
            return true;
        else return false;
    }
    public boolean validateListDoesNotContainSN(){
        for (int i = 0;i < observableList.size();i++){
            if (observableList.get(i).sn.equalsIgnoreCase(itemSerialNumberTextField.getText()) == true)
                return false;
        }
        return true;
    }
    public boolean validateName(){
        String name = itemNameTextField.getText();
        if (name.length() >= 2 && name.length() <= 256)
            return true;
        else return false;
    }
    public boolean validateLetterOrDigit(){
        for (int i = 0;i < observableList.size();i++) {
            if ((Character.isLetterOrDigit(itemSerialNumberTextField.getText().charAt(i)) == false))
                return false;
        }
        return true;
    }
    public boolean validateValue(){
        if (BigDecimal.valueOf(Double.parseDouble(itemValueTextField.getText())).compareTo(BigDecimal.valueOf(0.00)) == 1){
            return true;
        }
        else return false;
    }


    void saveAsButtonClicked(ActionEvent event){
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

