package model;

import java.io.Serializable;

public class Book implements Serializable{
    private String id;
    private String BookName;
    private Integer YearOfPublishing;
    private String PublishingHouse;
    private String author;
    private Integer NumberOfPages;
    private String type;
    private Integer Price;

    public Book(String id, String BookName, Integer YearOfPublishing, String PublishingHouse, String author, Integer NumberOfPages,
                String type, Integer Price) {
        this.id = id;
        this.BookName = BookName;
        this.YearOfPublishing = YearOfPublishing;
        this.PublishingHouse = PublishingHouse;
        this.author = author;
        this.NumberOfPages = NumberOfPages;
        this.type = type;
        this.Price = Price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookName() {
        return BookName;
    }

    public void setBookName(String bookName) {
        this.BookName = bookName;
    }

    public Integer getYearOfPublishing() {
        return YearOfPublishing;
    }

    public void setYearOfPublishing(Integer yearOfPublishing) {
        this.YearOfPublishing = yearOfPublishing;
    }

    public String getPublishingHouse() {
        return PublishingHouse;
    }

    public void setPublishingHouse(String publishingHouse) {
        this.PublishingHouse = publishingHouse;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getNumberOfPages() {
        return NumberOfPages;
    }

    public void setNumberOfPages(Integer numberOfPages) {
        this.NumberOfPages = numberOfPages;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPrice() {
        return Price;
    }

    public void setPrice(Integer price) {
        this.Price = price;
    }


    public String toString(){
        return ""+id+" "+ BookName +" "+ YearOfPublishing +" "+" "+ PublishingHouse +" "+ author +" "+ NumberOfPages +" "+type+" "+ Price;
    }
}