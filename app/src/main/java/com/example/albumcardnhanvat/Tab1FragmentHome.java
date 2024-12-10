package com.example.albumcardnhanvat;

public class Tab1FragmentHome {
    private int resourceImage;
    private String name;
    private boolean isSelected;


    public Tab1FragmentHome(int resourceImage, String name) {
        this.resourceImage = resourceImage;
        this.name = name;
        this.isSelected = false;
    }

    public int getResourceImage() {
        return resourceImage;
    }

    public void setResourceImage(int resourceImage) {
        this.resourceImage = resourceImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

}
