package com.example.miranlee.lec09;

/**
 * Created by miran lee on 2016-06-02.
 */
public class Product {
    private int _id;
    private String _productname;
    private int _quantity;

    public Product() {

    }

    public Product(int id, String productname, int quantity) {
        this._id = id;
        this._productname = productname;
        this._quantity = quantity;
    }

    public Product(String productname, int quantity) {
        this._productname = productname;
        this._quantity = quantity;
    }

    public void setID(int id){
        this._id = id;
    }

    public int getID(){
        return _id;
    }

    public void setProductName(String productName){
        this._productname = productName;
    }

    public String getProductName(){
        return _productname;
    }

    public void setQuantity(int quantity){
        this._quantity = quantity;
    }

    public int getQuantity(){
        return _quantity;
    }
}
