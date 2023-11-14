/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package grocerystore;

/**
 *
 * @author Wayt Turks
 */
public class products {
    
    private String categoryId;
    private String name;
    private String code;
    private Double price;
    private String status;

public products(String categoryId, String name, String code, Double price, String status){
this.categoryId = categoryId;
this.name = name;
this.code = code;
this.price = price;
this.status = status;
}

public String getCategoryId(){
return categoryId;
}
public String getName(){
return name;
}
public String getCode(){
return code;
}
public Double getPrice(){
return price;
}
public String getStatus(){
return status;
}
    
}
