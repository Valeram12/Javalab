package dto;

import java.util.ArrayList;
import java.util.List;

public class ManufacturerDTO {
    private Long manufacturerId;
    private String name;
    private List<BrandDTO> brands = new ArrayList<>();

    public ManufacturerDTO() { }

    public ManufacturerDTO(Long manufacturerId, String name) {
        this.manufacturerId = manufacturerId;
        this.name = name;
    }

    public Long getId() {
        return manufacturerId;
    }

    public void setId(Long authorId) {
        this.manufacturerId = authorId;
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
