package com.frontEnd;

import com.Global;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ModalWindow {

    private final Stage stage;
    private final Scene scene;
    private final BorderPane mainWorkSpace;

    public ModalWindow(String caption){
        this.stage = new Stage();
        this.mainWorkSpace = new BorderPane();
        this.scene = new Scene(new Pane());
        this.stage.initModality(Modality.WINDOW_MODAL);
        this.stage.getIcons().add(Global.primaryStage.getIcons().get(0));
        this.stage.initOwner(Global.primaryStage);
        this.stage.setTitle(caption);
        this.stage.setMinHeight(150);
        this.stage.setMinWidth(250);
        this.stage.setResizable(false);
    }

    public void show(){
        this.stage.showAndWait();
    }

    public void show(boolean withoutTop){
        this.stage.initStyle(StageStyle.UNDECORATED);
        this.stage.showAndWait();
    }

    public void close(){ this.stage.close(); }

    public void setMainWorkSpace(Pane node) {
        Button closeBtn = new Button("Cancel");
        closeBtn.setOnAction(event -> this.stage.close());

        this.mainWorkSpace.setBottom(closeBtn);
        this.mainWorkSpace.setCenter(node);
        this.mainWorkSpace.setPadding(new Insets(10));
        BorderPane.setAlignment(closeBtn, Pos.CENTER);
        this.scene.setRoot(this.mainWorkSpace);
        this.stage.setScene(this.scene);
    }

    public Stage getStage() {
        return stage;
    }
}
