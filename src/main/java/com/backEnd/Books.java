package com.backEnd;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Books {

    private List<Book> books;

    private Long count = 1L;

    public Books() {
        this.books = new ArrayList<Book>();
    }

    public Books(List<Book> books) {
        this.books = books;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Book getById(Long id) {
        for (var el : books) {
            if (el.getId() == id) return el;
        }
        return null;
    }

    public boolean add(Book book) {
        book.setId(count++);
        if (getById(book.getId()) != null) return false;
        return books.add(book);
    }

    public boolean remove(long id) {
        if (books == null || getById(id) == null) return false;
        return books.remove(getById(id));
    }

    public int getCountOfVendors() {
        var vendors = new ArrayList<String>();
        for (var book : getBooks()) {
            boolean flag = false;
            for (var el : vendors) {
                if (book.getVendorCode().equals(el)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) vendors.add(book.getVendorCode());
        }
        return vendors.size();
    } // result 2

    public Books sortByBookNumAndCopyCounts() {
        Comparator<Book> comparator = Comparator.comparing(Book::getBookNum).reversed().thenComparing(Book::getCopyCount).reversed();
        Books books = new Books(this.books);
        books.books.sort(comparator);
        return books;
    }

    public double getAvg() {
        var value = 0.0;
        var i = 0;
        for (var el : this.getBooks()) {
            value += el.getCopyCount();
            i++;
        }
        return value / i;
    }

    public List<String> result1(){
        var result = new ArrayList<String>();

        var books = new ArrayList<Integer>();
        for (var book : getBooks()) {
            boolean flag = false;
            for (var el : books) {
                if (book.getBookNum() == el) {
                    flag = true;
                    break;
                }
            }
            if (!flag) books.add(book.getBookNum());
        }

        for(var el : books){
            var i = 0;
            for(var book : getBooks()){
                if(el == book.getBookNum()){
                    i += book.getCopyCount();
                }
            }
            result.add(String.format("Book: \"%s\" has %d pcs", el, i));
        }

        return result;
    } // result 1

    public void removeLessThenAvg() {
        var value = getAvg();
        this.getBooks().removeIf(el -> (el.getCopyCount() < value));
    }

    public Books filter(String type) {
        var books = new Books();
        for (var el : this.getBooks()) {
            if (el.getVendorCode().toLowerCase().startsWith(type.toLowerCase())) {
                books.add(el);
            }
        }
        return books;
    }

}
