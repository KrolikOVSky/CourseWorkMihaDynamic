package com;

import com.backEnd.Book;
import com.backEnd.Books;
import com.frontEnd.Header;
import com.frontEnd.MainWindow;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Global {
    public static final String extension = ".mydb";
    public static String path = "";
    public static Books books;
    public static Stage primaryStage = new Stage();
    public static Scene mainScene = new Scene(new Pane());
    public static VBox workSpace = new VBox();


    public static void setScene(Node node){
        HBox mainWorkSpace = new HBox();
        workSpace.getChildren().addAll(new Header().getMainMenuBar(), node);
        mainWorkSpace.getChildren().add(workSpace);
        mainScene.setRoot(mainWorkSpace);
    }

    public static void errorReport(Exception e) {
        e.printStackTrace();
        TextArea output = new TextArea();
        {
            StringBuilder string = new StringBuilder();
            var i = 1;
            for (var el : e.getStackTrace()) {
                string.append(i).append(") ").append(el).append("\n\n");
                i++;
            }
            output.setText(string.toString());
        }
        output.setWrapText(true);

        BorderPane pane = new BorderPane();
        pane.setTop(new Label("Errors report"));
        pane.setCenter(output);
        pane.setBottom(new Label("Short description of error: " + e.getMessage()));
        Scene scene = new Scene(pane);
        Stage stage = new Stage();
        stage.setWidth(400);
        stage.setHeight(200);
//        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void fromListToFile() {
        StringBuilder content = new StringBuilder();
        for (var el : books.getBooks()) {
            content.append(String.format("id=%d;\n", el.getId()));
            content.append(String.format("bookNum=%d;\n", el.getBookNum()));
            content.append(String.format("vendorCode=%s;\n", el.getVendorCode()));
            content.append(String.format("month=%s;\n", el.getMonth()));
            content.append(String.format("copyCount=%d;\n\n", el.getCopyCount()));
        }
        try (FileWriter writer = new FileWriter(path)) {
            writer.append(content);
            writer.flush();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static void fromFileToList() {
        String[] content = new String[0];
        try {
            content = Files
                    .readString(Paths.get(path))
                    .replaceAll("\n", "")
                    .replaceAll("\r", "")
                    .split(";");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            errorReport(e);
        }

        books = new Books();
        var j = 0;
        for (var i = 0; i < content.length / 5; i++) {
            Book book = new Book();
            book.setId(Long.parseLong(content[j].substring((content[j].indexOf("=")) + 1)));
            j++;
            book.setBookNum(Integer.parseInt(content[j].substring((content[j].indexOf("=") + 1))));
            j++;
            book.setVendorCode(content[j].substring((content[j].indexOf("=") + 1)));
            j++;
            book.setMonth(content[j].substring((content[j].indexOf("=")) + 1));
            j++;
            book.setCopyCount(Long.parseLong(content[j].substring((content[j].indexOf("=")) + 1)));
            j++;
            books.add(book);
        }
        MainWindow.setMainTable(books);
    }

    public static void newSource() {
        books = new Books();
        path = "";
        MainWindow.setMainTable(books);
    }

}
