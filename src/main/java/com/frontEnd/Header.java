package com.frontEnd;

import com.Global;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;

import java.io.File;

public class Header {

    private final MenuBar mainMenuBar;
    private final Menu fileMenu = new Menu("File");
    private final Menu editMenu = new Menu("Edit");
    private final Menu helpMenu = new Menu("Help");
    private final MenuItem newItem = new MenuItem("New");
    private final MenuItem openItem = new MenuItem("Open");
    private final MenuItem saveAsItem = new MenuItem("Save as...");
    private final MenuItem saveItem = new MenuItem("Save");
    private final MenuItem closeItem = new MenuItem("Close");
    private final MenuItem addTableItem = new MenuItem("Add table");
    private final MenuItem delTableItem = new MenuItem("Delete table");
    private final MenuItem addRecordItem = new MenuItem("Add new record to table");
    private final MenuItem delRecordItem = new MenuItem("Delete record from table");
    private final MenuItem helpItem = new MenuItem("Help");
    private final MenuItem aboutItem = new MenuItem("About...");

    public Header() {
        // Menu
        mainMenuBar = new MenuBar();
        {
//          File
            {
//              New
                {
                    newItem.setOnAction(event -> Global.newSource());
                    newItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
                }

//              Open
                {
                    openItem.setOnAction(event -> {
//                        FileChooser fileChooser = new FileChooser();//Класс работы с диалогом выборки и сохранения
//                        fileChooser.setTitle("Open Database");//Заголовок диалога
//                        FileChooser.ExtensionFilter extFilter =
//                                new FileChooser.ExtensionFilter("My Database Files (*"+Global.extension+")", "*" + Global.extension);//Расширение
//                        fileChooser.getExtensionFilters().addAll(
//                                extFilter,
//                                new FileChooser.ExtensionFilter("All Images", "*.*")
//                        );
//
//                        File file = fileChooser.showOpenDialog(Global.primaryStage);//Указываем текущую сцену CodeNote.mainStage
                            File file = new File("C:\\Users\\Alex\\Desktop\\test_Miha.mydb");
                        if (file != null) {
                            Global.path = file.getPath();
                            Global.fromFileToList();
                        }

                    });
                    openItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
                }

//              Save As...
                {
                    saveAsItem.setOnAction(event -> {
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Save Database");
                        FileChooser.ExtensionFilter extFilter =
                                new FileChooser.ExtensionFilter("My Database Files (*"+Global.extension+")", "*" + Global.extension);
                        fileChooser.getExtensionFilters().add(extFilter);
                        File file = fileChooser.showSaveDialog(Global.primaryStage);
                        if (file != null) {
                            Global.path = file.getPath();
                            try {
                                Global.fromListToFile();
                            } catch (Exception exception) {
                                Global.errorReport(exception);
                            }
                        }
                    });
                    saveAsItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));
                }

//              Save
                {
                    saveItem.setOnAction(event -> {
                        if (!Global.path.equals("")) {
                            Global.fromListToFile();
                        } else {
                            saveAsItem.getOnAction().handle(event);
                        }
                    });
                    saveItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
                }

//              Close
                {
                    closeItem.setOnAction(event -> Global.primaryStage.close());
                }

                fileMenu.getItems().add(newItem);
                fileMenu.getItems().add(openItem);
                fileMenu.getItems().add(new SeparatorMenuItem());
                fileMenu.getItems().add(saveItem);
                fileMenu.getItems().add(saveAsItem);
                fileMenu.getItems().add(new SeparatorMenuItem());
                fileMenu.getItems().add(closeItem);
            }

//          Edit
            {
//              Add table
                {
                    addTableItem.setOnAction(event -> {

                    });
                }

//              Delete table
                {
                    delTableItem.setOnAction(event -> {

                    });
                }

//              Add new record to table
                {
                    addRecordItem.setOnAction(event -> {

                    });
                }

//              Delete record from table
                {
                    delRecordItem.setOnAction(event -> {

                    });
                }

                editMenu.getItems().add(addTableItem);
                editMenu.getItems().add(delTableItem);
                editMenu.getItems().add(new SeparatorMenuItem());
                editMenu.getItems().add(addRecordItem);
                editMenu.getItems().add(delRecordItem);
            }

//          Help
            {
//              Help
                {
                    helpItem.setOnAction(event -> {

                    });
                    helpItem.setAccelerator(new KeyCodeCombination(KeyCode.F1));
                }

//              About
                {
                    aboutItem.setOnAction(event -> {
                    });
                }

                helpMenu.getItems().addAll(helpItem, new SeparatorMenuItem(), aboutItem);
            }

            mainMenuBar.getMenus().add(fileMenu);
            mainMenuBar.getMenus().add(editMenu);
            mainMenuBar.getMenus().add(helpMenu);
        }
    }

    public MenuBar getMainMenuBar() {
//        openItem.getOnAction().handle(new ActionEvent());
        return mainMenuBar;
    }
}
