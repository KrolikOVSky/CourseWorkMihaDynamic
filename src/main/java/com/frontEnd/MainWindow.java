package com.frontEnd;

import com.Global;
import com.backEnd.Book;
import com.backEnd.Books;
import com.backEnd.Month;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

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

            mainTable.setOnMouseClicked(mouseEvent -> remove(mouseEvent));
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
                showAllBtn.setTooltip(new Tooltip("Show all the\nelements in the table"));
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
                sortBtn.setTooltip(new Tooltip("Sorting by\nascending in \"Book ID\"\nand by descending in\n\"Copies quantity\" "));
            }

            // Add button
            {
                addBtn.setMaxWidth(MAX_VALUE);
                addBtn.setOnAction(event -> {
                    if(Global.books != null){
                        ModalWindow modalWindow = new ModalWindow("Add element");
                        VBox addBox = new VBox(10);
                        TextField bookNumInput = new TextField();
                        TextField vendorCodeInput = new TextField();
                        TextField copyCountsInput = new TextField();
                        ComboBox<String> monthInput = new ComboBox<>();
                        Button commit = new Button("Add element");
                        // Add box
                        {
                            var width = 300;
                            // Book num input
                            {
                                bookNumInput.setPromptText("Enter book ID number");
                                bookNumInput.setPrefWidth(width);
                                bookNumInput.textProperty().addListener((observable, oldValue, newValue) -> {
                                    if (!newValue.matches("\\d*")) {
                                        bookNumInput.setText(newValue.replaceAll("[^\\d]", ""));
                                    }
                                });
                            }

                            // Vendor code input
                            {
                                vendorCodeInput.setPromptText("Enter vendor code");
                                vendorCodeInput.setPrefWidth(width);
                            }

                            // Copy counts input
                            {
                                copyCountsInput.setPromptText("Enter quantity of copies");
                                copyCountsInput.setPrefWidth(width);
                                copyCountsInput.textProperty().addListener((observable, oldValue, newValue) -> {
                                    if (!newValue.matches("\\d*")) {
                                        copyCountsInput.setText(newValue.replaceAll("[^\\d]", ""));
                                    }
                                });
                            }

                            // Month input
                            {
                                monthInput.setItems(Month.getMonths());
                                monthInput.setMaxWidth(MAX_VALUE);
                                monthInput.setPromptText("Choose the month");
                            }

                            // Commit button
                            {
                                commit.setOnAction(e -> {
                                    if(!bookNumInput.getText().equals("") && !vendorCodeInput.getText().equals("") && monthInput.getValue() != null && !copyCountsInput.getText().equals("")){
                                        Global.books.add(new Book(Integer.parseInt(bookNumInput.getText()), vendorCodeInput.getText(), monthInput.getValue(), Long.parseLong(copyCountsInput.getText())));
                                        setMainTable(Global.books);
                                        modalWindow.close();
                                    }
                                });
                            }

                            addBox.setAlignment(Pos.CENTER);
                            addBox.setPadding(new Insets(0, 0, 10, 0));
                            addBox.getChildren().add(bookNumInput);
                            addBox.getChildren().add(vendorCodeInput);
                            addBox.getChildren().add(monthInput);
                            addBox.getChildren().add(copyCountsInput);
                            addBox.getChildren().add(commit);
                        }
                        modalWindow.setMainWorkSpace(addBox);
                        modalWindow.showDialog();
                    }
                });
                addBtn.setTooltip(new Tooltip("Add new element\nin the table"));
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
                remByCondBtn.setTooltip(new Tooltip("Remove if quantity\nof copies less\nthen avengers"));
            }

            // Result 1 button
            {
                resultOneBtn.setMaxWidth(MAX_VALUE);
                resultOneBtn.setOnAction(event -> {
                    if (Global.books != null && !Global.books.getBooks().isEmpty()) {
                        VBox content = new VBox();
                        content.setAlignment(Pos.CENTER_LEFT);
                        content.setPadding(new Insets(0, 0, 10, 0));
                        content.setSpacing(10);

                        for (var el : Global.books.result1()) {
                            content.getChildren().add(new Label(el));
                        }
                        ModalWindow modalWindow = new ModalWindow("Result 1");
                        modalWindow.setMainWorkSpace(content);
                        modalWindow.showDialog();
                    }
                });
                resultOneBtn.setTooltip(new Tooltip("Total number of copies\nfor each book"));
            }

            // Result 2 button
            {
                resultTwoBtn.setMaxWidth(MAX_VALUE);
                resultTwoBtn.setOnAction(event -> {
                    if(Global.books != null && !Global.books.getBooks().isEmpty()){
                        VBox content = new VBox();
                        content.setAlignment(Pos.CENTER_LEFT);
                        content.setPadding(new Insets(0,0,10,0));
                        content.setSpacing(10);
                        content.getChildren().add(new Label(String.format("Total number of vendors is %d", Global.books.getCountOfVendors())));
                        ModalWindow modalWindow = new ModalWindow("Result 2");
                        modalWindow.setMainWorkSpace(content);
                        modalWindow.showDialog();
                    }
                });
                resultTwoBtn.setTooltip(new Tooltip("Total number\nof vendors"));
            }

            // Average copy counts button
            {
                averageBtn.setMaxWidth(MAX_VALUE);
                averageBtn.setOnAction(event ->  {
                    if(Global.books != null && !Global.books.getBooks().isEmpty()){
                        VBox content = new VBox();
                        content.setAlignment(Pos.CENTER_LEFT);
                        content.setPadding(new Insets(0,0,10,0));
                        content.setSpacing(10);
                        content.getChildren().add(new Label(String.format("Average quantity of book copies is %f", Global.books.getAvg())));
                        ModalWindow modalWindow = new ModalWindow("Average copy counts");
                        modalWindow.setMainWorkSpace(content);
                        modalWindow.showDialog();
                    }
                });
                averageBtn.setTooltip(new Tooltip("Average quantity\n of book copies"));
            }

            // Filter box
            {
                TextField input = new TextField();
                Button confirm = new Button("Filter");

                // Input
                {
                    input.setPromptText("Enter filter");
                    input.setOnAction(event -> confirm.getOnAction().handle(new ActionEvent()));
                }

                // Confirm btn
                {
                    confirm.setOnAction(event -> {
                        if(Global.books != null && !Global.books.getBooks().isEmpty() && !input.getText().equals("")){
                            setMainTable(Global.books.filter(input.getText()));
                        }
                    });
                    confirm.setTooltip(new Tooltip("Apply filter in\nvendor code field"));
                }


                filterBox.setSpacing(10);
                filterBox.getChildren().add(input);
                filterBox.getChildren().add(confirm);
            }

            // Selection box
            {
                Label caption = new Label("Select by min and max copy's count");
                TextField minInput = new TextField();
                TextField maxInput = new TextField();
                Button selectBtn = new Button("Commit");

                // Caption
                {

                }

                // Min input
                {
                    minInput.setPromptText("Min");
                    minInput.textProperty().addListener((observable, oldValue, newValue) -> {
                        if (!newValue.matches("\\d*")) {
                            minInput.setText(newValue.replaceAll("[^\\d]", ""));
                        }
                    });
                }

                // Max Input
                {
                    maxInput.setPromptText("Max");
                    maxInput.textProperty().addListener((observable, oldValue, newValue) -> {
                        if (!newValue.matches("\\d*")) {
                            maxInput.setText(newValue.replaceAll("[^\\d]", ""));
                        }
                    });
                    maxInput.setOnAction(event -> selectBtn.getOnAction().handle(new ActionEvent()));
                }

                // Apply selection button
                {
                    selectBtn.setMaxWidth(MAX_VALUE);
                    selectBtn.setOnAction(event -> {
                        if(Global.books != null && !Global.books.getBooks().isEmpty() && !minInput.getText().equals("") && !maxInput.getText().equals("")){
                            setMainTable(Global.books.selectionByMinMaxCopyCounts(Integer.parseInt(minInput.getText()), Integer.parseInt(maxInput.getText())));
                            minInput.setText("");
                            maxInput.setText("");
                        }
                    });
                    selectBtn.setTooltip(new Tooltip("Apply selection\nelements with quantity\nbetween Min and Max"));
                }

                VBox vSelBox = new VBox(10, caption, minInput, maxInput, selectBtn);
                vSelBox.setAlignment(Pos.CENTER);
                selectionBox.setCenter(vSelBox);
            }

            // Control panel options
            {
                Label caption = new Label("Control\nPanel");
                caption.setStyle("-fx-font-size: 30");
                caption.setTextAlignment(TextAlignment.CENTER);


                controlPanel.setPadding(new Insets(10));
                controlPanel.setSpacing(10);
                controlPanel.setPrefWidth(230);
//                controlPanel.setMaxWidth(200);
//                controlPanel.setStyle("-fx-background-color: red");
                controlPanel.setAlignment(Pos.CENTER);

                controlPanel.getChildren().add(caption);
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

    public void remove(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.SECONDARY) {
            Book currentRoute = mainTable.getSelectionModel().getSelectedItem();
            if (currentRoute != null) {
                ContextMenu contextMenu = new ContextMenu();
                MenuItem removeItem = new MenuItem(String.format("Remove %s", currentRoute.getBookNum()));
                MenuItem editItem = new MenuItem(String.format("Edit %s", currentRoute.getBookNum()));

                removeItem.setOnAction(event -> {
                    Global.books.remove(currentRoute.getId());
                    setMainTable(Global.books);
                });

                editItem.setOnAction(event -> {
                    if(Global.books != null){
                        ModalWindow modalWindow = new ModalWindow("Edit element");
                        VBox editBox = new VBox(10);
                        TextField bookNumInput = new TextField();
                        TextField vendorCodeInput = new TextField();
                        TextField copyCountsInput = new TextField();
                        ComboBox<String> monthInput = new ComboBox<>();
                        Button commit = new Button("Update element");
                        // Edit box
                        {
                            var width = 300;
                            // Book num input
                            {
                                bookNumInput.setPromptText("Enter book ID number");
                                bookNumInput.setPrefWidth(width);
                                bookNumInput.textProperty().addListener((observable, oldValue, newValue) -> {
                                    if (!newValue.matches("\\d*")) {
                                        bookNumInput.setText(newValue.replaceAll("[^\\d]", ""));
                                    }
                                });
                                bookNumInput.setText(String.valueOf(currentRoute.getBookNum()));
                            }

                            // Vendor code input
                            {
                                vendorCodeInput.setPromptText("Enter vendor code");
                                vendorCodeInput.setPrefWidth(width);
                                vendorCodeInput.setText(currentRoute.getVendorCode());
                            }

                            // Copy counts input
                            {
                                copyCountsInput.setPromptText("Enter quantity of copies");
                                copyCountsInput.setPrefWidth(width);
                                copyCountsInput.textProperty().addListener((observable, oldValue, newValue) -> {
                                    if (!newValue.matches("\\d*")) {
                                        copyCountsInput.setText(newValue.replaceAll("[^\\d]", ""));
                                    }
                                });
                                copyCountsInput.setText(String.valueOf(currentRoute.getCopyCount()));
                            }

                            // Month input
                            {
                                monthInput.setItems(Month.getMonths());
                                monthInput.setMaxWidth(MAX_VALUE);
                                monthInput.setPromptText("Choose the month");
                                monthInput.setValue(currentRoute.getMonth());
                            }

                            // Commit button
                            {
                                commit.setOnAction(e -> {
                                    if(!bookNumInput.getText().equals("") && !vendorCodeInput.getText().equals("") && !monthInput.getValue().equals("") && !copyCountsInput.getText().equals("")){
                                        Global.books.getById(currentRoute.getId()).setBookNum(Integer.parseInt(bookNumInput.getText()));
                                        Global.books.getById(currentRoute.getId()).setVendorCode(vendorCodeInput.getText());
                                        Global.books.getById(currentRoute.getId()).setMonth(monthInput.getValue());
                                        Global.books.getById(currentRoute.getId()).setCopyCount(Integer.parseInt(copyCountsInput.getText()));
                                        setMainTable(Global.books);
                                        modalWindow.close();
                                    }
                                });
                            }

                            editBox.setAlignment(Pos.CENTER);
                            editBox.setPadding(new Insets(0, 0, 10, 0));
                            editBox.getChildren().add(bookNumInput);
                            editBox.getChildren().add(vendorCodeInput);
                            editBox.getChildren().add(monthInput);
                            editBox.getChildren().add(copyCountsInput);
                            editBox.getChildren().add(commit);
                        }
                        modalWindow.setMainWorkSpace(editBox);
                        modalWindow.showDialog();
                    }
                });

                contextMenu.getItems().add(removeItem);
                contextMenu.getItems().add(editItem);
                mainTable.setContextMenu(contextMenu);
            }
        }

    }
}
