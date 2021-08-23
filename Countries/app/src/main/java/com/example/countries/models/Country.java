package com.example.countries.models;

public class Country {
    private String nativeName;
    private String englishName;
    private String area;

    public Country(String nativeName, String englishName, String area) {
        this.nativeName = nativeName;
        this.englishName = englishName;
        this.area = area;
    }

    public String getNativeName() {
        return nativeName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public String getArea() {
        return area;
    }

    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
