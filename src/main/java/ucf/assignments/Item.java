package ucf.assignments;

/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Christian Klingbiel
 */

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
        this.sn = sn;
        this.name = name;
        this.value = value;
    }
}
