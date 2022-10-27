package models;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dealer {
    private Map<String, Manufacturers> manufacturers = new HashMap<>();
    private Map<String, String> manufacturersNames = new HashMap<>();
    private Map<String, Brands> brands = new HashMap<>();
    private Map<String, String> brandsNames = new HashMap<>();

    public Dealer() { }

    public void createId(Manufacturers manufacturers) {
        int id = this.manufacturers.size();
        String idToString = "author" + id;
        while(this.manufacturers.get(idToString) != null) {
            id++;
            idToString = "author" + id;
        }
        manufacturers.setManufacturersId(idToString);
    }

    public void createId(Brands brands) {
        int id = this.brands.size();
        String idToString = "id" + id;
        while(manufacturers.get(idToString) != null) {
            id++;
            idToString = "id" + id;
        }
        brands.setBrandsId(idToString);
    }

    public void addManufacturers(Manufacturers manufacturers) {
        this.manufacturers.put(manufacturers.getManufacturersId(), manufacturers);
        manufacturersNames.put(manufacturers.getName(), manufacturers.getManufacturersId());
    }

    public void addBrands(Brands brands, String authorId) {
        Manufacturers manufacturers = this.manufacturers.get(authorId);
        brands.setManufacturersId(authorId);
        this.brands.put(brands.getBrandsId(), brands);
        brandsNames.put(brands.getName(), brands.getBrandsId());
        manufacturers.getBrands().add(brands);
    }

    public void removeManufacturers(Manufacturers manufacturers) {
        this.manufacturers.remove(manufacturers.getManufacturersId());
        manufacturersNames.remove(manufacturers.getName());
        for(Brands brands : manufacturers.getBrands()) {
            this.brands.remove(brands.getBrandsId());
        }
    }

    public void removeBrands(Brands brands) {
        this.brands.remove(brands.getBrandsId());
        brandsNames.remove(brands.getName());
        manufacturers.get(brands.getManufacturersId()).getBrands().remove(brands);
    }

    public void changeBooksAuthor(Brands brands, Manufacturers manufacturers) {
        Manufacturers old = this.manufacturers.get(brands.getManufacturersId());
        if(old != null) {
            old.getBrands().remove(brands);
        }
        brands.setManufacturersId(manufacturers.getManufacturersId());
        manufacturers.getBrands().add(brands);
    }

    public void renameBrands(Brands brands, String newName) {
        brandsNames.remove(brands.getName());
        brands.setName(newName);
        brandsNames.put(brands.getName(), brands.getBrandsId());
    }

    public void renameManufacturers(Manufacturers manufacturers, String newName) {
        manufacturersNames.remove(manufacturers.getName());
        manufacturers.setName(newName);
        manufacturersNames.put(manufacturers.getName(), manufacturers.getManufacturersId());
    }

    public Brands getBook(String name) {
        String id = brandsNames.get(name);
        if(id != null) {
            return brands.get(id);
        }
        return null;
    }

    public Manufacturers getAuthor(String name) {
        String id = manufacturersNames.get(name);
        if(id != null) {
            return manufacturers.get(id);
        }
        return null;
    }

    public List<Brands> getAuthorsBooks(String name){
        return getAuthor(name).getBrands();
    }

    public Map<String, Manufacturers> getManufacturers() {
        return manufacturers;
    }

    public void outputAllContents(){
        for (String id : manufacturers.keySet()){
            Manufacturers manufacturers = this.manufacturers.get(id);
            manufacturers.outputmanufacturers();
            List<Brands> brandss = manufacturers.getBrands();
            for (Brands brands : brandss) brands.outputBrands();
        }
    }
}