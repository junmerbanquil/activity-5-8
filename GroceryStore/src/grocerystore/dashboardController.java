/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package grocerystore;
import java.net.URL;
import java.sql.Connection;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert;
import java.sql.PreparedStatement; 
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import static java.awt.AWTEventMulticaster.add;
import java.io.IOException;
import java.net.URL;
import static java.time.Clock.system;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.transformation.SortedList;

/**
 *
 * @author Wayt Turks
 */
public class dashboardController implements Initializable {



    @FXML
    private BarChart<?, ?> dashboard_NOrderChart;

    @FXML
    private Button dashboard_btn;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private TextField forID;
    
    @FXML
    private BarChart<?, ?> dashboard_incomeChart;

    @FXML
    private AnchorPane dashboard_todaysIncome;

    @FXML
    private AnchorPane dashboard_totalIncome;

    @FXML
    private AnchorPane dashboard_totalProducts;

   

    @FXML
    private Button order_btn;

    @FXML
    private Button prod_addBtn;

    @FXML
    private Button prod_clearBtn;

    @FXML
    private TableColumn<products, String> prod_col_productCategory;

    @FXML
    private TableColumn<products, String> prod_col_productCode;

    @FXML
    private TableColumn<products, String> prod_col_productId;

    @FXML
    private TableColumn<products, String> prod_col_productName;

    @FXML
    private TableColumn<products, String> prod_col_productPrice;

    @FXML
    private TableColumn<products, String> prod_col_productStatus;

    @FXML
    private Button prod_deleteBtn;

    @FXML
    private ComboBox<?> prod_productCategory;

    @FXML
    private TextField prod_productCode;

    @FXML
    private TextField prod_productName;

    @FXML
    private TextField prod_productPrice;

    @FXML
    private ComboBox<?> prod_productStatus;

    @FXML
    private TextField prod_search;

    @FXML
    private TableView<products> allProd_tableView;
    
    @FXML
    private Button prod_updateBtn;

    @FXML
    private Button products_btn;

    @FXML
    private AnchorPane main_form;

    @FXML
    private AnchorPane products_form;

    @FXML
    private Label username;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    
    public void addProducts(){
    String sql = "INSERT INTO product (categoryID, product_name, product_code, product_price, product_status) " + "VALUES(?,?,?,?,?)";
    connect = (Connection) database.connect();

    try{
    prepare = connect.prepareStatement(sql);
    prepare.setString(1, (String) prod_productCategory.getSelectionModel().getSelectedItem());
    prepare.setString(2, prod_productName.getText());
    prepare.setString(3, prod_productCode.getText());
    prepare.setString(4, prod_productPrice.getText());
    prepare.setString(5, (String) prod_productStatus.getSelectionModel().getSelectedItem());

    Alert alert;

    if(prod_productCategory.getSelectionModel() == null || prod_productName.getText().isEmpty() || prod_productCode.getText().isEmpty() || prod_productPrice.getText().isEmpty() || prod_productStatus.getSelectionModel() == null){
    alert = new Alert(AlertType.ERROR);
    alert.setTitle("Error Message");
    alert.setHeaderText(null);
    alert.setContentText("Please fill all blank fields");
    alert.showAndWait();
    }else{

    String checkData = "SELECT product_code FROM product WHERE product_code = '" +prod_productCode.getText()+"'";

    connect = (Connection) database.connect();

    statement = connect.createStatement();
    result = statement.executeQuery(checkData);

    if(result.next()){
    alert = new Alert(AlertType.ERROR);
    alert.setTitle("Error Message");
    alert.setHeaderText(null);
    alert.setContentText("Product Code: " + prod_productCode.getText() + " is already exist!");
    alert.showAndWait();
    }else{
    prepare.executeUpdate();

    alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Information Message");
    alert.setHeaderText(null);
    alert.setContentText("Successfully Added!");
    alert.showAndWait();

    allProductShowData();
    allProductClear();
    }

    }

    }catch(SQLException e){}

    }
    
    public void allProductUpdate() {
    String sql = "UPDATE product SET product_name = '"
        +prod_productName.getText()+"', categoryID = '"
        +prod_productCategory.getSelectionModel().getSelectedItem() +"', product_price = '"
        +prod_productPrice.getText()+"', product_status = '"
        +prod_productStatus.getSelectionModel().getSelectedItem()
        +"' WHERE product_code = '"+prod_productCode.getText()+"'";

    connect = database.connect();
    try {
      
      Alert alert;
      
        if (prod_productCode.getText().isEmpty() ||
            prod_productName.getText().isEmpty() ||
            prod_productCategory.getSelectionModel().getSelectedItem() == null ||
            prod_productPrice.getText().isEmpty() ||
            prod_productStatus.getSelectionModel().getSelectedItem() == null){
                
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        }else{
            
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to Update Product Code:" + prod_productCode.getText() + "?");
            Optional<ButtonType> option = alert.showAndWait();
            
            if(option.get().equals(ButtonType.OK)){
                
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Updated!");
                alert.showAndWait();
                
                statement = connect.createStatement();
                statement.executeUpdate(sql);
                
                //show data on table
                allProductShowData();
                //clear text field
                allProductClear();
                
                
            }else{
            
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Cancelled");
                alert.showAndWait();
                
            }

            }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
    
    public void allProductDelete(){
    String sql = "DELETE FROM product WHERE product_code = '" +prod_productCode.getText()+"'";

    connect = database.connect();

    try{
    Alert alert;

    if (prod_productCode.getText().isEmpty() &&
                prod_productName.getText().isEmpty() &&
                prod_productCategory.getSelectionModel().getSelectedItem() == null &&
                prod_productPrice.getText().isEmpty() &&
                prod_productStatus.getSelectionModel().getSelectedItem() == null){

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else{

                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to Delete Product Code:" + prod_productCode.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if(option.get().equals(ButtonType.OK)){

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    //show data on table
                    allProductShowData();
                    //clear text field
                    allProductClear();


                }else{

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled");
                    alert.showAndWait();
                }
            }
        }catch(Exception e){e.printStackTrace();
    }}
    
    public void allProductClear(){
        prod_productCode.setText("");
        prod_productName.setText("");
        prod_productPrice.setText("");
        prod_productCategory.getSelectionModel().clearSelection();
        prod_productStatus.getSelectionModel().clearSelection();
        prod_productCode.setEditable(true);
    }
    
    
    
    private ObservableList<products> allProductListData(){
    ObservableList<products> listData = FXCollections.observableArrayList();

    String sql = "SELECT * FROM product";

    connect = (Connection) database.connect();

    try{

    prepare = connect.prepareStatement(sql);
    result = prepare.executeQuery();

    products cat;

    while(result.next()){
    cat = new products(result.getString("categoryID"), result.getString("product_name"), result.getString("product_code"), result.getDouble("product_price"), result.getString("product_status"));
    listData.add(cat);
    }

    }catch(Exception e){e.printStackTrace();
    }
    return listData;
    }
    
    public void allProductSearch() {
    FilteredList<products> filter = new FilteredList<>(allProductList, e -> true);
    prod_search.textProperty().addListener((observable, oldValue, newValue) -> {
        filter.setPredicate(predicatedCategories -> {
            if (newValue.isEmpty() || newValue == null) {
                return true;
            }

            String searchKey = newValue.toLowerCase();

            if (predicatedCategories.getCode().toLowerCase().contains(searchKey)) {
                return true;
            } else if (predicatedCategories.getName().toLowerCase().contains(searchKey)) {
                return true;
            } else if (predicatedCategories.getName().toLowerCase().contains(searchKey)) {
                return true;
            } else if (predicatedCategories.getPrice().toString().contains(searchKey)) {
                return true;
            } else if (predicatedCategories.getStatus().toLowerCase().contains(searchKey)) {
                return true;
            } else{
            return false;}
        });
    });

    SortedList<products> sorList = new SortedList<>(filter);

    sorList.comparatorProperty().bind(allProd_tableView.comparatorProperty());
    allProd_tableView.setItems(sorList);
}

    
    private ObservableList<products> allProductList;
    public void allProductShowData(){

    allProductList = allProductListData();
    
    prod_col_productCode.setCellValueFactory(new PropertyValueFactory<>("code"));
    prod_col_productName.setCellValueFactory(new PropertyValueFactory<>("name"));
    prod_col_productPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    prod_col_productCategory.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
    prod_col_productStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    
    allProd_tableView.setItems(allProductList);
    }

    public void allProductSelect(){
    
        products catData = allProd_tableView.getSelectionModel().getSelectedItem();
        int num = allProd_tableView.getSelectionModel().getSelectedIndex();
        
        if((num -1) < -1){
        return;
        }

        prod_productCode.setText(catData.getCode());
        prod_productCode.setStyle("-fx-opacity: 1; -fx-disable: true;");
        prod_productName.setText(catData.getName());	
        prod_productPrice.setText(String.valueOf(catData.getPrice()));	
    }
    
    private final String[] products = {"Bakery", "Canned Goods", "Frozen Foods"};
    public void allProductCategory(){
    List<String> listCat = new ArrayList<>();

    listCat.addAll(Arrays.asList(products));
    ObservableList listData = FXCollections.observableArrayList(listCat);
    prod_productCategory.setItems(listData);
    }
    
    private final String[] status = {"Available", "Not Available"};
    public void allProductStatus(){
        List<String> listStatus = new ArrayList<>();

        for(String data: status){
                listStatus.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listStatus);
        prod_productStatus.setItems(listData);
    }
    
    public void switchForm(ActionEvent event){
    if(event.getSource() == dashboard_btn){
        dashboard_form.setVisible(true);
        products_form.setVisible(false);
        
        dashboard_btn.setStyle("-fx-background-color: #006664; -fx-text-fill: #fff; -fx-border-width: 0px;");
        products_btn.setStyle("-fx-background-color: transparent; -fx-border-width: 1px; -fx-text-fill: #000;");

        
    }else if(event.getSource() == products_btn){
        dashboard_form.setVisible(false);
        products_form.setVisible(true);
        
        products_btn.setStyle("-fx-background-color: #006664; -fx-text-fill: #fff; -fx-border-width: 0px;");
        dashboard_btn.setStyle("-fx-background-color: transparent; -fx-border-width: 1px; -fx-text-fill: #000;");
        
        allProductShowData();
        allProductSearch();
    }
    }
    
    private double x = 0;
    private double y = 0;

    public void logout() {
        try {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.isPresent() && option.get().equals(ButtonType.OK)) {

                
                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.initStyle(StageStyle.TRANSPARENT);

                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);

                    stage.setOpacity(.8f);
                });

                root.setOnMouseReleased((MouseEvent event) -> {
                    stage.setOpacity(1);
                });

                stage.setScene(scene);
                stage.show();
            }

        } catch (IOException e) {
        }
    }
    public void close() {
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // load data, initialize UI components, etc
        allProductStatus();
        allProductCategory();
        allProductShowData();
        allProductSearch();
    }

}
