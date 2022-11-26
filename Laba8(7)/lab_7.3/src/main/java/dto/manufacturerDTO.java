package dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class manufacturerDTO implements Serializable {
    private Long manufacturerId;
    private String name;
    private List<brandDTO> brands = new ArrayList<>();

    public manufacturerDTO() { }

    public manufacturerDTO(Long manufacturerId, String name) {
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

    public List<brandDTO> getBrands() {
        return brands;
    }

    public void setBrands(List<brandDTO> brands) {
        this.brands = brands;
    }
}