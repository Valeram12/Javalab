package dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class manufacturerDTO implements Serializable {
    private Long manufacturerId;
    private String name;
    private List<BrandDTO> brands = new ArrayList<>();

    public manufacturerDTO() { }

    public manufacturerDTO(Long manufacturerId, String name) {
        this.manufacturerId = manufacturerId;
        this.name = name;
    }

    public Long getId() {
        return manufacturerId;
    }

    public void setId(Long manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BrandDTO> getBrands() {
        return brands;
    }

    public void setBrands(List<BrandDTO> brands) {
        this.brands = brands;
    }
}
