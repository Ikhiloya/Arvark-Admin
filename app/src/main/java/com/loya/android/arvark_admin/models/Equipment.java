package com.loya.android.arvark_admin.models;

/**
 * Created by user on 12/10/2017.
 */

public class Equipment {
    private String name;
    private String category;
    private int image;
    private String originalPrice;
    private String discountPrice;
    private String size;
    private String desc;


    public Equipment(String name, String category, int image, String originalPrice, String discountPrice, String size, String desc) {
        this.name = name;
        this.category = category;
        this.image = image;
        this.originalPrice = originalPrice;
        this.discountPrice = discountPrice;
        this.size = size;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getImage() {
        return image;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public String getSize() {
        return size;
    }

    public String getDesc() {
        return desc;
    }
}
