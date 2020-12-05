package home;

import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.xdevapi.Result;
import com.mysql.cj.xdevapi.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import utils.ConnectionUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;

import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Controller implements Initializable {

    @FXML
    private VBox pnItems = null;
    @FXML
    private Button btnOverview;

    @FXML
    private Button btnCheckIn;

    @FXML
    private Button btnCheckOut;

    @FXML
    private Button btnMenus;

    @FXML
    private Button btnPackages;

    @FXML
    private Button btnSettings;

    @FXML
    private Button btnSignout;

    @FXML
    private Pane pnlCheckOut;

    @FXML
    private Pane pnlCheckIn;

    @FXML
    private Pane pnlOverview;

    @FXML
    private Pane pnlMenus;
    @FXML
    private AnchorPane ancpan;
    @FXML
    private AnchorPane ancpan2;
    @FXML
    private TextField ownername;

    @FXML
    private TextField contactno;

    @FXML
    private TextField vehicleid;
    @FXML
    private TextField tokentextfield;
    @FXML
    private TextField placeid;

    @FXML
    private DatePicker datepicker;

    @FXML
    private ComboBox<String> vehicletype;

    @FXML
    private TextField time;
    @FXML
    private TextField slotno;
    @FXML
    private  Label labeladdvehicle;
    @FXML
    private Button btngentoken;
    @FXML
    private Button btnok;
    @FXML
    private Button tokensearch;
    @FXML
    private Button btnupdate;
    @FXML
    private Button btnrefresh;
    @FXML
    private Label labeltoken;
    @FXML
    private Label labelcheckout;
    @FXML
    private AnchorPane checkoutpane;
    @FXML
    TableView tblData;
    @FXML
    TableView tblData2;

    @FXML
    private DatePicker checkoutdatepicker;

    @FXML
    private TextField checkouttime;

    @FXML
    private Button btncheckout;
    @FXML
            private Label labeladdplace;


    @FXML
    private AnchorPane panee;

    @FXML
    private TextField txtplaceid;

    @FXML
    private TextField txtplacename;

    @FXML
    private TextField totalslots;

    @FXML
    private Button btnaddplace;

    @FXML
    private Label labtotalslot;

    @FXML
    private Label laboccupied;

    @FXML
    private Label labvacant;

    @FXML
    private Button btuppd;
    @FXML
            private Label user_name;


Boolean k=true;
    private  int token;
    private ObservableList<ObservableList> data;
    private ObservableList<ObservableList> data2;
    private ObservableList<ObservableList> data3;
    PreparedStatement preparedStatement;
    Connection connection;
    String owner_n;
    String vec_id;
   /* private int tok= Integer.parseInt(tokentextfield.getText());
    Integer in=Integer.valueOf(tok);*/
    String SQL = "SELECT * from checkin";
    String SQLite = "SELECT * from checkin where token_no='Integer.valueOf(res)'";

    public Controller() {
        connection = (Connection) ConnectionUtil.conDB();
    }

    private void fetColumnList() {

        try {
            ResultSet rs = connection.createStatement().executeQuery(SQL);

            //SQL FOR SELECTING ALL OF CUSTOMER
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1).toUpperCase());
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                tblData.getColumns().removeAll(col);
                tblData.getColumns().addAll(col);

                System.out.println("Column [" + i + "] ");


            }

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());

        }
    }

    private void fetColumnList2() {

        try {
           // ResultSet rs3 = connection.createStatement().executeQuery(SQLite);
            PreparedStatement stmt2 = connection.prepareStatement(
                    "SELECT * "+
                            "FROM checkin "+
                            "WHERE token_no = ?");
            stmt2.setInt(1, Integer.parseInt(tokentextfield.getText()));

            ResultSet rs3 = stmt2.executeQuery();

            //SQL FOR SELECTING ALL OF CUSTOMER
            for (int i = 0; i < rs3.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                 int k = i;
                TableColumn col2 = new TableColumn(rs3.getMetaData().getColumnName(i + 1).toUpperCase());
                col2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param2) {
                        return new SimpleStringProperty(param2.getValue().get(k).toString());
                    }
                });

                tblData2.getColumns().removeAll(col2);

                tblData2.getColumns().addAll(col2);

                System.out.println("Column [" + i + "] ");


            }

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());

        }
    }


    public void buildData(){

        data3 = FXCollections.observableArrayList();
        try{
            PreparedStatement stmt2 = connection.prepareStatement(
                    "SELECT * "+
                            "FROM checkin "+
                            "WHERE token_no = ?");
            stmt2.setInt(1, Integer.parseInt(tokentextfield.getText()));

            ResultSet rs3 = stmt2.executeQuery();
            /**********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             **********************************/
            for(int i=0 ; i<rs3.getMetaData().getColumnCount(); i++){
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs3.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                tblData2.getColumns().addAll(col);
                System.out.println("Column ["+i+"] ");
            }

            /********************************
             * Data added to ObservableList *
             ********************************/
            while(rs3.next()){
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs3.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs3.getString(i));
                }
                System.out.println("Row [1] added "+row );
                data3.add(row);

            }

            //FINALLY ADDED TO TableView
            tblData2.setItems(data3);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    //fetches rows and data from the list
    private void fetRowList() {
        data = FXCollections.observableArrayList();

        ResultSet rs;
        try {

            rs = connection.createStatement().executeQuery(SQL);

            while (rs.next()) {
                //Iterate Row
                ObservableList row = FXCollections.observableArrayList();

                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added " + row);
                data.add(row);

            }

            tblData.setItems(data);

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    String  chckoutd;
    String checkoutt;
    private void fetRowList2() {
        data2= FXCollections.observableArrayList();

        try(java.sql.Statement smt = (java.sql.Statement) connection.createStatement()) {



          /*  ResultSet rs2   = smt.executeQuery(SQLite);*/
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * "+
                            "FROM checkin "+
                            "WHERE token_no = ?");
            stmt.setInt(1, Integer.parseInt(tokentextfield.getText()));

            ResultSet rs3 = stmt.executeQuery();

          while (rs3.next()) {
                //Iterate Row
                ObservableList row2 = FXCollections.observableArrayList();

                for (int i = 1; i <= rs3.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row2.add(rs3.getString(i));
                }
                System.out.println("Row [1] added " + row2);
                data2.add(row2);
                owner_n=rs3.getString(1);
                vec_id=rs3.getString(3);

            }

            tblData2.setItems(data2);

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vehicletype.getItems().addAll("Two Wheeler", "Four Wheeler", "Eight Wheller");
        vehicletype.getSelectionModel().select("Two Wheeler");
        /*fetColumnList();
        fetRowList();*/
   /*     Node[] nodes = new Node[10];
        for (int i = 0; i < nodes.length; i++) {
            try {

                final int j = i;
                nodes[i] = FXMLLoader.load(getClass().getResource("Item.fxml"));

                //give the items some effect

                nodes[i].setOnMouseEntered(event -> {
                    nodes[j].setStyle("-fx-background-color : #0A0E3F");
                });
                nodes[i].setOnMouseExited(event -> {
                    nodes[j].setStyle("-fx-background-color : #02030A");
                });
                pnItems.getChildren().add(nodes[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

    }

public Boolean r=true;
    public void handleClicks(ActionEvent actionEvent) throws IOException {
        if(actionEvent.getSource()==btncheckout){

            /*String checkoutdate=checkoutdatepicker.getValue().toString();
            String checkouttm=checkouttime.getText();*/

savedata2();

           /* double calcprice=calculateprice(checkoutdate,checkouttm);
            System.out.println(calcprice);*/
        }
        if (actionEvent.getSource() == btnCheckOut) {
            labelcheckout.setVisible(true);
            checkoutpane.setVisible(true);
            pnlCheckOut.setStyle("-fx-background-color : #F9A825");
            System.out.println("Checkout");
            pnlCheckOut.toFront();

        }
        if(actionEvent.getSource()==tokensearch){
            String res=tokentextfield.getText();


           if(k==true) {

                fetColumnList2();

                k=false;
            }



                fetRowList2();


        }
        if(actionEvent.getSource()==btuppd){
            retrievedata();
        }
        if(actionEvent.getSource()==btnaddplace){
            savedata3();
        }
        if (actionEvent.getSource() == btnMenus) {
            pnlMenus.setStyle("-fx-background-color : #00695C");
            pnlMenus.toFront();
            labeladdplace.setVisible(true);
            panee.setVisible(true);


        }
        if (actionEvent.getSource() == btnOverview) {
            pnlOverview.setStyle("-fx-background-color :  #F50057");
            pnlOverview.toFront();
        }
        if(actionEvent.getSource()==btnCheckIn)
        {

            pnlCheckIn.setStyle("-fx-background-color : #464F67");
            pnlCheckIn.toFront();
            System.out.println("checkkIn");
            ancpan.setVisible(true);
            labeladdvehicle.setVisible(true);

        }
        if(actionEvent.getSource()==btnupdate){
            if(r==true) {
                fetColumnList();

                r = false;
            }

                fetRowList();

            btnupdate.setVisible(false);

        }
        if(actionEvent.getSource()==btnrefresh){


                tblData.getItems().clear();
                tblData.getColumns().removeAll();
                btnupdate.setVisible(true);


        }
        if(actionEvent.getSource()==btnSignout){
           Node node1 = (Node) actionEvent.getSource();
            Stage stage1 = (Stage) node1.getScene().getWindow();
            //stage.setMaximized(true);
            stage1.close();
            Scene scene1 = new Scene(FXMLLoader.load(getClass().getResource("/fxml/Login.fxml")));
            stage1.setScene(scene1);
            stage1.show();
          /*  System.out.println("jasbjhi");*/
        }
        if(actionEvent.getSource()==btnok){

            ancpan2.setVisible(false);

        }
        if(actionEvent.getSource()==btngentoken){
                      ancpan2.setVisible(true);
            if (ownername.getText().isEmpty() || contactno.getText().isEmpty() || vehicleid.getText().isEmpty() || datepicker.getValue().equals(null)||placeid.getText().isEmpty()||time.getText().isEmpty()||slotno.getText().isEmpty()) {
                /*lblStatus.setTextFill(Color.TOMATO);
                lblStatus.setText("Enter all details");*/
            } else {
                Random r=new Random();
                 token=r.nextInt(99999-10000)+10000;
                labeltoken.setText(String.valueOf(token));
                saveData();
            }


        }
    }

    private void retrievedata() {
int sum=0;
int theCount=0;
        try {
            // ResultSet rs3 = connection.createStatement().executeQuery(SQLite);
            PreparedStatement stmt2 = connection.prepareStatement(
                    "SELECT sum(totalno_slots) "+
                            "FROM place "
                            );


           ResultSet rs3 =  stmt2.executeQuery();
            while (rs3.next()) {
                int c = rs3.getInt(1);
                sum = sum + c;
            }

            labtotalslot.setText(String.valueOf(sum));
            PreparedStatement stmt3 = connection.prepareStatement(
                    "SELECT count(slotno) "+
                            "FROM checkin "
            );
            ResultSet rs4 =  stmt3.executeQuery();
            if (rs4.next()) {
                 theCount = rs4.getInt(1);
                laboccupied.setText(String.valueOf(theCount));

            }
            int lo=sum-theCount;
            labvacant.setText(String.valueOf(lo));

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());

        }

    }

    private String savedata3() {
        try {
            String st = "INSERT INTO place ( place_id, place_name, totalno_slots) VALUES (?,?,?)";
            preparedStatement = (PreparedStatement) connection.prepareStatement(st);
            preparedStatement.setString(1, txtplaceid.getText());
            preparedStatement.setString(2, txtplacename.getText());
            preparedStatement.setInt(3, Integer.parseInt(totalslots.getText()));

            preparedStatement.executeUpdate();
            clearFields3();
           /* lblStatus.setTextFill(Color.GREEN);
            lblStatus.setText("Added Successfully");*/

            //   fetRowList();
            //clear fields
            /*clearFields();*/
            return "Success";

        } catch (SQLException ex) {
            System.out.println("SQLError");/*
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText(ex.getMessage());*/
            return "Exception";
        }
    }

    private void clearFields3() {
        txtplaceid.clear();
        txtplacename.clear();
        totalslots.clear();
    }

    private String savedata2() {
        try {
            String st = "INSERT INTO checkout ( owner_name, token_no, vehicle_id, checkout_date, checkout_time) VALUES (?,?,?,?,?)";
            preparedStatement = (PreparedStatement) connection.prepareStatement(st);
            preparedStatement.setString(1, owner_n);
            preparedStatement.setString(2, tokentextfield.getText());
            preparedStatement.setString(3, vec_id);
            preparedStatement.setString(4, checkoutdatepicker.getValue().toString());
            preparedStatement.setString(5, checkouttime.getText());
            preparedStatement.executeUpdate();
            clearFields2();
           /* lblStatus.setTextFill(Color.GREEN);
            lblStatus.setText("Added Successfully");*/

            //   fetRowList();
            //clear fields
            /*clearFields();*/
            return "Success";

        } catch (SQLException ex) {
            System.out.println("SQLError");/*
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText(ex.getMessage());*/
            return "Exception";
        }
    }

    private void clearFields2() {
        tokentextfield.clear();
        checkouttime.clear();
        tblData2.getItems().clear();
        tblData2.getColumns().removeAll();
    }

    private double calculateprice(String checkoutdate, String checkouttm) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" );
        Date d1 = null;
        Date d2 = null;
        double cost = 0;
        try {
            d1 = format.parse(chckoutd+" "+checkoutt);
            d2 = format.parse(checkoutdate+" "+checkouttm);
            double diff = d2.getTime() - d1.getTime();

            double diffSeconds = diff / 1000 % 60;
            double diffMinutes = diff / (60 * 1000) % 60;
            double diffHours = diff / (60 * 60 * 1000) % 24;
            double diffDays = diff / (24 * 60 * 60 * 1000);
            cost = ((diffMinutes)* 60 * 2) + (diffHours * 60 * 60*2 ) + (diffDays * 24 * 60 * 60 * 2)+(diffSeconds*2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cost;
    }

    private String saveData() {

        try {
            String st = "INSERT INTO checkin ( owner_name, contact_no, vehicle_id, date, place_id,vehicle_type,time,slotno,token_no) VALUES (?,?,?,?,?,?,?,?,?)";
            preparedStatement = (PreparedStatement) connection.prepareStatement(st);
            preparedStatement.setString(1, ownername.getText());
            preparedStatement.setString(2, contactno.getText());
            preparedStatement.setString(3, vehicleid.getText());
            preparedStatement.setString(4, datepicker.getValue().toString());
            preparedStatement.setInt(5, Integer.parseInt(placeid.getText()));
            preparedStatement.setString(6,vehicletype.getValue().toString());
            preparedStatement.setString(7, time.getText());
            preparedStatement.setInt(8, Integer.parseInt(slotno.getText()));
            preparedStatement.setInt(9,token);
            preparedStatement.executeUpdate();
            clearFields();
           /* lblStatus.setTextFill(Color.GREEN);
            lblStatus.setText("Added Successfully");*/

         //   fetRowList();
            //clear fields
            /*clearFields();*/
            return "Success";

        } catch (SQLException ex) {
            System.out.println("SQLError");/*
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText(ex.getMessage());*/
            return "Exception";
        }
    }
    private void clearFields() {
        ownername.clear();
        contactno.clear();
        vehicleid.clear();
        placeid.clear();
        time.clear();
        slotno.clear();
    }



    //only fetch columns


}
