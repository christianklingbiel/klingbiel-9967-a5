package ucf;

import java.math.BigDecimal;

public class Item {
    public String name;
    public String sn;
    public BigDecimal value;

    public String getName() {
        return name;
    }

    public String getSn() {
        return sn;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Item(String sn, String name, BigDecimal value) {
        //super();
        this.sn = sn;
        this.name = name;
        this.value = value;
    }
}
