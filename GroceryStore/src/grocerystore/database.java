/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package grocerystore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class database {
public static Connection connect(){
try{
Class.forName("com.mysql.jdbc.Driver");
Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/grocerystore", "root", "");
return connect;
}catch(Exception e) {
    e.printStackTrace();
}


return null;
}
}



