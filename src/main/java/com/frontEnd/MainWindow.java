package com.frontEnd;

import com.Global;
import com.backEnd.Book;
import com.backEnd.Books;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import static java.lang.Double.MAX_VALUE;

public class MainWindow {
    private static TableView<Book> mainTable;
    private final TableColumn<Book, Long> bookNumColumn = new TableColumn<Book, Long>("Book ID number");
    private final TableColumn<Book, String> vendorCodeColumn = new TableColumn<Book, String>("Vendor code");
    private final TableColumn<Book, String> monthColumn = new TableColumn<Book, String>("Month");
    private final TableColumn<Book, Long> copyCountColumn = new TableColumn<Book, Long>("Quantity of copies");
    private final VBox controlPanel = new VBox();
    private final Button controlPanelBtn = new Button(">");
    private final BorderPane mainWorkSpace = new BorderPane();
    private final Button showAllBtn = new Button("Show all");
    private final Button sortBtn = new Button("Sort");
    private final Button addBtn = new Button("Add");
    private final Button remByCondBtn = new Button("Remove by condition");
    private final Button resultOneBtn = new Button("Result 1");
    private final Button resultTwoBtn = new Button("Result 2");
    private final Button averageBtn = new Button("Average quantity of copies");
    private final HBox filterBox = new HBox();
    private final BorderPane selectionBox = new BorderPane();


    public MainWindow() {
        mainTable = new TableView<Book>();

        // Create Table
        {
            var width = 185;
            bookNumColumn.setCellValueFactory(new PropertyValueFactory<Book, Long>("bookNum"));
            bookNumColumn.setPrefWidth(width);

            vendorCodeColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("vendorCode"));
            vendorCodeColumn.setPrefWidth(width);

            monthColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("month"));
            monthColumn.setPrefWidth(width);

            copyCountColumn.setCellValueFactory(new PropertyValueFactory<Book, Long>("copyCount"));
            copyCountColumn.setPrefWidth(width);

            mainTable.getColumns().add(bookNumColumn);
            mainTable.getColumns().add(vendorCodeColumn);
            mainTable.getColumns().add(monthColumn);
            mainTable.getColumns().add(copyCountColumn);
        }

        // Ctrl panel
        {
            // Show all button
            {
                showAllBtn.setMaxWidth(MAX_VALUE);
                showAllBtn.setOnAction(event -> {
                    if (Global.books != null && !Global.books.getBooks().isEmpty()) {
                        setMainTable(Global.books);
                    }
                });
            }

            // Sort button
            {
                sortBtn.setMaxWidth(MAX_VALUE);
                sortBtn.setOnAction(event -> {
                    if( Global.books != null && !Global.books.getBooks().isEmpty()){
                        Global.books = Global.books.sortByBookNumAndCopyCounts();
                        setMainTable(Global.books);
                    }
                });
            }

            // Add button
            {
                addBtn.setMaxWidth(MAX_VALUE);
                addBtn.setOnAction(event -> {

                });
            }

            // Remove by condition button
            {
                remByCondBtn.setMaxWidth(MAX_VALUE);
                remByCondBtn.setOnAction(event -> {
                    if (Global.books != null && !Global.books.getBooks().isEmpty()) {
                        Global.books.removeLessThenAvg();
                        setMainTable(Global.books);
                    }
                });
            }

            // Result 1 button
            {
                resultOneBtn.setMaxWidth(MAX_VALUE);
                resultOneBtn.setOnAction(event -> {

                });
            }

            // Result 2 button
            {
                resultTwoBtn.setMaxWidth(MAX_VALUE);
                resultTwoBtn.setOnAction(event -> {

                });
            }

            // Average copy counts button
            {
                averageBtn.setMaxWidth(MAX_VALUE);
                averageBtn.setOnAction(event -> {

                });
            }

            // Filter box
            {
                TextField input = new TextField();
                Button confirm = new Button("Filter");

                filterBox.getChildren().add(input);
                filterBox.getChildren().add(confirm);
            }

            // Selection box
            {
                Label caption = new Label("Select by min and max copy\'s count");
                TextField minInput = new TextField();
                TextField maxInput = new TextField();
                Button selectBtn = new Button("Commit");

                // Caption
                {

                }

                // Min input
                {
                    minInput.setPromptText("Min");
                }

                // Max Input
                {
                    maxInput.setPromptText("Max");
                }

                // Select button
                {
                    selectBtn.setMaxWidth(MAX_VALUE);
                    selectBtn.setOnAction(event -> {

                    });
                }
                VBox vSelBox = new VBox(10, caption, minInput, maxInput, selectBtn);
                vSelBox.setAlignment(Pos.CENTER);
                selectionBox.setCenter(vSelBox);
            }

            // Control panel options
            {
                controlPanel.setPadding(new Insets(10));
                controlPanel.setSpacing(10);
                controlPanel.setPrefWidth(300);
                controlPanel.setMaxWidth(300);
//                controlPanel.setStyle("-fx-background-color: red");
                controlPanel.setAlignment(Pos.CENTER);

                controlPanel.getChildren().add(showAllBtn);
                controlPanel.getChildren().add(addBtn);
                controlPanel.getChildren().add(sortBtn);
                controlPanel.getChildren().add(remByCondBtn);
                controlPanel.getChildren().add(resultOneBtn);
                controlPanel.getChildren().add(resultTwoBtn);
                controlPanel.getChildren().add(averageBtn);
                controlPanel.getChildren().add(filterBox);
                controlPanel.getChildren().add(selectionBox);
            }
        }

        // Ctrl panel btn
        {
            controlPanelBtn.setMaxHeight(Global.primaryStage.getHeight());
            controlPanelBtn.setMaxWidth(25);
            controlPanelBtn.setOnAction(event -> {
                if (((HBox) Global.primaryStage.getScene().getRoot()).getChildren().contains(controlPanel)) {
                    controlPanelBtn.setText(">");
                    ((HBox) Global.primaryStage.getScene().getRoot()).getChildren().remove(controlPanel);
                    Global.primaryStage.setWidth(Global.primaryStage.getWidth() - controlPanel.getPrefWidth());
                } else {
                    controlPanelBtn.setText("<");
                    ((HBox) Global.primaryStage.getScene().getRoot()).getChildren().add(controlPanel);
                    Global.primaryStage.setWidth(Global.primaryStage.getWidth() + controlPanel.getPrefWidth());
                }
            });
        }

        // Main work space
        {
            mainWorkSpace.setRight(controlPanelBtn);
            mainWorkSpace.setCenter(mainTable);
            mainWorkSpace.setPrefSize(Global.primaryStage.getWidth(), Global.primaryStage.getHeight());
        }
    }

    public static void setMainTable(Books books) {
        mainTable.setItems(FXCollections.observableArrayList(books.getBooks()));
    }

    public Node getMainBoxOfElements() {
        return mainWorkSpace;
    }
}
