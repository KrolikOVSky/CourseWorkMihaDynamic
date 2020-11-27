package com.backEnd;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class Book {
    private SimpleLongProperty id = new SimpleLongProperty();
    private SimpleIntegerProperty bookNum = new SimpleIntegerProperty();
    private SimpleStringProperty vendorCode = new SimpleStringProperty();
    private SimpleStringProperty month = new SimpleStringProperty();
    private SimpleLongProperty copyCount = new SimpleLongProperty();

    public Book() {
        this.id.set(0L);
        this.bookNum.set(0);
        this.vendorCode.set("");
        this.month.set("");
        this.copyCount.set(0L);
    }

    public Book(long id, int bookNum, String vendorCode, String month, long copyCount) {
        this.id.set(id);
        this.bookNum.set(bookNum);
        this.vendorCode.set(vendorCode);
        this.month.set(month);
        this.copyCount.set(copyCount);
    }

    public long getId() {
        return id.get();
    }

    public SimpleLongProperty idProperty() {
        return id;
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public int getBookNum() {
        return bookNum.get();
    }

    public SimpleIntegerProperty bookNumProperty() {
        return bookNum;
    }

    public void setBookNum(int bookNum) {
        this.bookNum.set(bookNum);
    }

    public String getVendorCode() {
        return vendorCode.get();
    }

    public SimpleStringProperty vendorCodeProperty() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode.set(vendorCode);
    }

    public String getMonth() {
        return month.get();
    }

    public SimpleStringProperty monthProperty() {
        return month;
    }

    public void setMonth(String month) {
        this.month.set(month);
    }

    public long getCopyCount() {
        return copyCount.get();
    }

    public SimpleLongProperty copyCountProperty() {
        return copyCount;
    }

    public void setCopyCount(long copyCount) {
        this.copyCount.set(copyCount);
    }
}
