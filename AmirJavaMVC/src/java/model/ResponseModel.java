// A411_collaboration - Response Model Table
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Amir
 */
public class ResponseModel {
    
    private Object data;
    private String message;
   
    public Object getData() {
        return data;
    }
    
    public void setData(Object data) {
        this.data = data;
    }
    
    public String getMessage() {
       return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
}
