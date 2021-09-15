package com;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;

public class ProductController extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    TableView<ProductModel> table = new TableView<>();
    TextField nameInput;
    TextField priceInput;
    TextField cantitateInput;
    TextField detailsInput;
    final Desktop desktop = Desktop.getDesktop();

    @Override
    public void start(Stage primaryStage) {


        primaryStage.setTitle("Table View");
        table.setEditable(true);

        ////Defining the name column and edit that
        TableColumn<ProductModel, String> nameCol = new TableColumn<>("Name ");
        nameCol.setMinWidth(100);

        //Setting Data Properties to Columns
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        //Editable cell for String parameters
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setOnEditCommit(tab -> tab.getTableView().getItems().get(tab.getTablePosition().getRow()).setName(tab.getNewValue()));

        ////Defining the price column and edit that
        TableColumn<ProductModel, Double> priceCol = new TableColumn<>("Price ");
        priceCol.setMinWidth(100);

        //Setting Data Properties to Columns
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Editable cell for Double parameters
        priceCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        priceCol.setOnEditCommit(tab -> tab.getTableView().getItems().get(tab.getTablePosition().getRow()).setPrice(tab.getNewValue()));

        ////Defining the cantitate column and edit that
        TableColumn<ProductModel, Integer> cantitateCol = new TableColumn<>("Cantitate ");
        cantitateCol.setMinWidth(100);

        //Setting Data Properties to Columns
        cantitateCol.setCellValueFactory(new PropertyValueFactory<>("cantitate"));

        //Editable cell for Integer parameters
        cantitateCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        cantitateCol.setOnEditCommit(tab -> tab.getTableView().getItems().get(tab.getTablePosition().getRow()).setCantitate(tab.getNewValue()));


        //Defining the details column and edit that
        TableColumn<ProductModel, String> detailsCol = new TableColumn<>("Details ");
        detailsCol.setMinWidth(100);

        //Setting Data Properties to Columns
        detailsCol.setCellValueFactory(new PropertyValueFactory<>("details"));

        //Editable cell for String parameters
        detailsCol.setCellFactory(TextFieldTableCell.forTableColumn());
        detailsCol.setOnEditCommit(tab -> tab.getTableView().getItems().get(tab.getTablePosition().getRow()).setDetails(tab.getNewValue()));

        //name input
        nameInput = new TextField();
        nameInput.setPromptText("Name");
        nameInput.setMinWidth(100);

        //price input
        priceInput = new TextField();
        priceInput.setPromptText("Price");

        //cantitate input
        cantitateInput = new TextField();
        cantitateInput.setPromptText("Cantitate");

        //details input
        detailsInput = new TextField();
        detailsInput.setPromptText("Details");

        //created button
        Button addButton = new Button("Add");
        addButton.setOnAction(event -> {
            setUserAgentStylesheet(STYLESHEET_MODENA);
        });
        addButton.setOnAction(event -> addButtonClicked());

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(event -> {
            setUserAgentStylesheet(STYLESHEET_MODENA);
        });
        deleteButton.setOnAction(event -> {
            deleteButtonClicked();
        });

        Button saveButton = new Button("Save");
        //set an action to my save as button
        saveButton.setOnAction(event -> {
            setUserAgentStylesheet(STYLESHEET_MODENA);
        });
        saveButton.setOnAction(event -> {
            try {
                saveButtonClicked();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        final FileChooser fileChooser = new FileChooser();

        Button openButton = new Button("Open");
        openButton.setOnAction(event -> {
            setUserAgentStylesheet(STYLESHEET_MODENA);
        });
        openButton.setOnAction(event -> {
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                openFile(file);
            }
        });

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(event -> {
            setUserAgentStylesheet(STYLESHEET_MODENA);
        });
        exitButton.setOnAction(event -> {
            exitButtonClicked();
        });

        Button refreshButton = new Button("Reset");
        refreshButton.setOnAction(event -> {
            setUserAgentStylesheet(STYLESHEET_MODENA);
        });
        refreshButton.setOnAction(event -> {
            resetButtonClicked();
        });

        //Creating a Table and Adding Data to It
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10, 10, 10, 10));
        hBox.setSpacing(5);
        hBox.getChildren().addAll(nameInput,
                priceInput,
                cantitateInput,
                detailsInput,
                addButton,
                deleteButton,
                openButton,
                saveButton,
                refreshButton,
                exitButton);

        table.setItems(getProduct());
        //noinspection unchecked
        table.getColumns().addAll(nameCol, priceCol, cantitateCol, detailsCol);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(table, hBox);

        Scene scene = new Scene(vBox);
        scene.getStylesheets().add("style");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException exp) {
            Logger.getLogger(FileChooser.class.getName()).log(SEVERE, null, exp);
        }
    }


    private void exitButtonClicked() {
        Platform.exit();
        System.out.println("\n" +
                " Application closed successfully \n");
    }


    public void saveButtonClicked() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XLSX files (*.xlsx)", "*.xlsx"),
                new FileChooser.ExtensionFilter("XLS files (*.xls)", "*.xls"),
                new FileChooser.ExtensionFilter("ODS files (*.ods)", "*.ods"),
                new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"),
                new FileChooser.ExtensionFilter("HTML files (*.html)", "*.html"),
                new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt")
        );
        File saveFile = fileChooser.showSaveDialog(table.getScene().getWindow());

        PrintStream print = new PrintStream(saveFile);
        print.println(nameInput.getText());
        print.println(priceInput.getText());
        print.println(cantitateInput.getText());
        print.println(detailsInput.getText());
        print.flush();
        System.out.println("\n" +
                " Application saved successfully \n");

    }

    private void resetButtonClicked() {
        ObservableList<ProductModel> product, totalProdus;
        totalProdus = table.getItems();
        product = table.getSelectionModel().getSelectedItems();
        product.forEach(totalProdus::remove);
        if (product.isEmpty()) {
            ObservableList<ProductModel> data = FXCollections.observableArrayList();
            table.setItems(data);
            table.refresh();
            System.out.println("\n" +
                    " Application reseated successfully \n");
        }
    }


    private void deleteButtonClicked() {

        ObservableList<ProductModel> productSlected, allProdus;
        allProdus = table.getItems();
        productSlected = table.getSelectionModel().getSelectedItems();
        productSlected.forEach(allProdus::remove);
        System.out.println("\n" +
                " Application deleted successfully \n");

    }

    private void addButtonClicked() {

        ProductModel product = new ProductModel();

        product.setName(nameInput.getText());
        product.setPrice(Double.parseDouble(priceInput.getText()));
        product.setCantitate(Integer.parseInt(cantitateInput.getText()));
        product.setDetails(detailsInput.getText());
        table.getItems().add(product);
        nameInput.clear();
        priceInput.clear();
        cantitateInput.clear();
        detailsInput.clear();
        System.out.println("\n" +
                " Application add with successfully \n");


    }

    //get all the products, defining table data in an Observable List
    //created an ObservableList array and define as many data rows as you would like to show in your table
    //Creating a Table and Adding Data to It
    public ObservableList<ProductModel> getProduct() {

        ObservableList<ProductModel> products = FXCollections.observableArrayList();
        products.add(new ProductModel("Laptop", 2323, 100, "-"));
        products.add(new ProductModel("DVD", 1522, 200, "-"));
        products.add(new ProductModel("Phone", 434, 100, "details"));
        products.add(new ProductModel("PC", 1450, 500, "-"));
        products.add(new ProductModel("Tastatura", 70, 100, "-"));

        return products;
    }


}

