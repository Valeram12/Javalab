package models;


public class Brands {
    private String brandsId;
    private String manufacturersId;
    private String name;
    private Integer releaseYear;

    public Brands(String name, Integer releaseYear, Dealer dealer) {
        this.name = name;
        this.releaseYear = releaseYear;
        dealer.createId(this);
    }

    public Brands() {
    }

    public String getManufacturersId() {
        return manufacturersId;
    }

    public void setManufacturersId(String manufacturersId) {
        this.manufacturersId = manufacturersId;
    }

    public String getBrandsId() {
        return brandsId;
    }

    public void setBrandsId(String brandsId) {
        this.brandsId = brandsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void outputBrands(){
        System.out.println("\tid=" + brandsId + "\tname=" + name + "\treleaseYear=" + releaseYear);
    }
}