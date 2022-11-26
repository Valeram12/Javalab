package dto;

public class BrandDTO {
    private Long brandId;
    private Long manufacturerId;
    private String name;
    private Integer releaseYear;

    public BrandDTO(Long brandId, String name, Integer releaseYear, Long manufacturerId) {
        this.manufacturerId = manufacturerId;
        this.brandId = brandId;
        this.name = name;
        this.releaseYear = releaseYear;
    }

    public BrandDTO() { }

    public Long getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Long manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
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
}

