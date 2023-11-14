/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package grocerystore;

import java.io.IOException;
import java.sql.Connection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Wayt Turks
 */
public class FXMLDocumentController implements Initializable {

    
    
    //close button
    public void close(){
    System.exit(0);
    }
    
    //database
    @FXML
    private Button close;
    
    @FXML
    private Button log_LoginBtn;

    @FXML
    private Button log_RegAccountBtn;

    @FXML
    private PasswordField log_password;

    @FXML
    private TextField log_user;

    @FXML
    private BorderPane login_form;

    @FXML
    private Button reg_LoginAccount;

    @FXML
    private Button reg_RegisterBtn;

    @FXML
    private PasswordField reg_password;

    @FXML
    private TextField reg_user;

    @FXML
    private BorderPane register_form;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    private double x = 0;
    private double y = 0;
    
    /**
     *
     * @throws java.io.IOException
     */
    
    
    
    public void loginAccount() throws IOException {
        String sql = "SELECT username, password FROM admin WHERE username = ? and password = ?";
        connect = database.connect();

        try {
            Alert alert;
            if (log_user.getText().isEmpty() || log_password.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, log_user.getText());
                prepare.setString(2, log_password.getText());

                result = prepare.executeQuery();

                if (result.next()) {
                    
                    data.username = log_user.getText();
                    //if correct username and pass
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login");
                    alert.showAndWait();
                    
                    //hide login page
                    log_LoginBtn.getScene().getWindow().hide();
                    
                    Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));

                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    root.setOnMousePressed((MouseEvent event) ->{
                    x = event.getSceneX();
                    y = event.getSceneY();
                    });

                    root.setOnMouseDragged((MouseEvent event) ->{
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);
                    });
                    
                    stage.initStyle(StageStyle.TRANSPARENT);
                    
                    stage.setScene(scene);
                    stage.show();
                } else {
                    //if incorrect username and passoword
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect Username/Password");
                    alert.showAndWait();
                }
            }
        } catch (SQLException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    public void registerAccount() {
        String sql = "INSERT INTO admin (username, password) VALUES(?,?)";
        connect = database.connect();
        try {
            Alert alert;
            if (reg_user.getText().isEmpty() || reg_password.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                // check if admin name already taken
                String checkData = "SELECT username FROM admin WHERE username = '" + reg_user.getText() + "'";

                prepare = connect.prepareStatement(checkData);
                result = prepare.executeQuery();

                if (result.next()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Username is already taken");
                    alert.showAndWait();
                } else {
                    if (reg_password.getText().length() < 8) {
                        alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Password must be atleast 8 characters");
                        alert.showAndWait();
                    } else {
                        prepare = connect.prepareStatement(sql);
                        prepare.setString(1, reg_user.getText());
                        prepare.setString(2, reg_password.getText());

                        prepare.executeUpdate();

                        alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successfuly Registered");
                        alert.showAndWait();
                        
                        login_form.setVisible(true);
                        register_form.setVisible(false);

                        reg_user.setText("");
                        reg_password.setText("");
                        log_user.setText("");
                        log_password.setText("");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //database
    public void switchForm(ActionEvent event) {
        if (event.getSource() == reg_LoginAccount) {
            login_form.setVisible(true);
            register_form.setVisible(false);
        } else if (event.getSource() == log_RegAccountBtn) {
            login_form.setVisible(false);
            register_form.setVisible(true);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
