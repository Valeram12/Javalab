package models;

import java.util.ArrayList;
import java.util.List;

public class Manufacturers {
    private String manufacturersId;
    private String name;
    private List<Brands> brands = new ArrayList<>();

    public Manufacturers(){ }

    public Manufacturers(String name, Dealer dealer) {
        this.name = name;
        dealer.createId(this);
    }

    public String getManufacturersId() {
        return manufacturersId;
    }

    public void setManufacturersId(String manufacturersId) {
        this.manufacturersId = manufacturersId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Brands> getBrands() {
        return brands;
    }

    public void setBrands(List<Brands> brands) {
        this.brands = brands;
    }

    public void outputmanufacturers(){
        System.out.println("id=" + manufacturersId + "\tname=" + name);
    }
}