package com.szklarnia.model;

public class Greenhouse {

    private Integer greenhouseId;
    private Float area; //powierzchnia szklarni
    private String buildingMaterial; //poliwęglan (polycarbonate) lub szkło (glass)
    private Float rentPrice;
    private String harvestType; //manualnie (manually), sprzęt (equipment) lub roboty (robots)
    private String farmingTechnique; //w ziemi (ground farming), na poletkach (plot farming), hydroponika (hydroponic farming)

    public Greenhouse(Integer greenhouseId, Float area, String buildingMaterial, Float rentPrice, String harvestType, String farmingTechnique) {
        this.greenhouseId = greenhouseId;
        this.area = area;
        this.buildingMaterial = buildingMaterial;
        this.rentPrice = rentPrice;
        this.harvestType = harvestType;
        this.farmingTechnique = farmingTechnique;
    }

    public Greenhouse() {

    }

    public Integer getGreenhouseId() {
        return greenhouseId;
    }

    public void setGreenhouseId(Integer greenhouseId) {
        this.greenhouseId = greenhouseId;
    }

    public Float getArea() {
        return area;
    }

    public void setArea(Float area) {
        this.area = area;
    }

    public String getBuildingMaterial() {
        return buildingMaterial;
    }

    public void setBuildingMaterial(String buildingMaterial) {
        this.buildingMaterial = buildingMaterial;
    }

    public Float getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(Float rentPrice) {
        this.rentPrice = rentPrice;
    }

    public String getHarvestType() {
        return harvestType;
    }

    public void setHarvestType(String harvestType) {
        this.harvestType = harvestType;
    }

    public String getFarmingTechnique() {
        return farmingTechnique;
    }

    public void setFarmingTechnique(String farmingTechnique) {
        this.farmingTechnique = farmingTechnique;
    }
}
