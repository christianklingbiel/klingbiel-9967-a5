package ucf;

import javafx.beans.property.SimpleStringProperty;

import java.math.BigDecimal;

public class Item {
    public SimpleStringProperty name;
    public SimpleStringProperty serialNumber;
    public BigDecimal value;

    public Item(String sn, String name, double value) {
    }
}
