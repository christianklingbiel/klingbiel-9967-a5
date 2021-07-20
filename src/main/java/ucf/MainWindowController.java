package ucf;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import javax.swing.text.TableView;

public class MainWindowController {
    @FXML
    private TableView itemsTableView;

    @FXML
    private TableColumn itemSerialNumberColumn;

    @FXML
    private TableColumn itemNameColumn;

    @FXML
    private TableColumn itemValueColumn;

    @FXML
    private TextField itemNameTextField;

    @FXML
    private TextField itemValueTextField;

    @FXML
    private TextField itemSerialNumberTextField;

    @FXML
    void addNewItemButtonClicked(ActionEvent event){
        String sn = itemSerialNumberTextField.getText();
        String name = itemNameTextField.getText();
        double value = Double.parseDouble(itemValueTextField.getText());

        Item item = addNewItem(sn, name, value);

        ItemModel.add(item);
    }

    public void addNewItem(String sn, String name, double value){
        return new Item(sn, name, value);
    }

    void saveAsButtonClicked(ActionEvent event){
        filename = FileChooser.getName();
        filetype = FileChooser.getType();

        if(filetype == 'CSV'){
            saveAsCSV(filename + filetype);
        }
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
