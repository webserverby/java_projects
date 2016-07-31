package com.faenko.mavenjavafxcosts.controller;

import com.faenko.mavenjavafxcosts.implement.CollectionBuyManager;
import com.faenko.mavenjavafxcosts.object.Record;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Контроллер главного окна
 *
 * @author Artem Faenko
 */
public class MainController  {

    private CollectionBuyManager buyManagerImpl = new CollectionBuyManager();

    private Stage mainStage;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private ComboBox boxSelect;

    @FXML
    private Label labelSum;

    @FXML
    private TableView tableBuy;

    @FXML
    private TableColumn<Record, String> columnPayment;

    @FXML
    private TableColumn<Record, String> columnDate;

    @FXML
    private TableColumn<Record, String> columnNumber;

    @FXML
    private TableColumn<Record, String> columnSum;

    // объект при создании диалогового окна
    private Parent fxmlEdit;
    // загрузчик
    private FXMLLoader fxmlLoader = new FXMLLoader();
    //  контролер в котором будем изменять значения перед отправкой
    private AddController addController;
    private Stage editDialogStage;


    /**
     * Инициализирует класс контроллера
     * Метод автоматически вызывается после того, как был загружен файл FXML
     */
    @FXML
    private void initialize()  {
        boxSelect.getItems().setAll("Газоснабжение", "Водоснабжение", "Электроэнергия","Покупки" );
        boxSelect.setValue("Газоснабжение");
        buyManagerImpl.selectCollection((String) boxSelect.getValue());
        columnPayment.setCellValueFactory(new PropertyValueFactory<Record, String>("payment"));
        columnDate.setCellValueFactory(new PropertyValueFactory<Record, String>("date"));
        columnNumber.setCellValueFactory(new PropertyValueFactory<Record, String>("number"));
        columnSum.setCellValueFactory(new PropertyValueFactory<Record, String>("sum"));

        initListeners();
        fillData();
        initLoader();
    }

    /**
     * Возвращает таблице коллекцию recordList
     */
    private void fillData()  {
        tableBuy.setItems(buyManagerImpl.getRecordList());
    }

    private void initListeners() {
        buyManagerImpl.getRecordList().addListener(new ListChangeListener<Record>() {
            @Override
            public void onChanged(Change<? extends Record> c) {
                updateCountLabel();
            }
        });

    /*  // Открывает запись во втором окне при срабатывании 2-ого клика мыши
      tableBuy.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    addController.setRecord((Record)tableBuy.getSelectionModel().getSelectedItem());
                    addDialog();
                }
            }
        });
    */

    }

    /**
     * Загружает параметры окна добавления данных
     */
    private void initLoader() {
        try {
            fxmlLoader.setLocation(getClass().getResource("/fxml/add.fxml"));
            fxmlEdit = fxmlLoader.load();
            addController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Строка Label выводит количество записей из таблицы
     */
    private void updateCountLabel() {
        labelSum.setText("Количество записей: " + buyManagerImpl.getRecordList().size());
    }

    /**
     * Для обработки всех кнопок onAction
     * @param actionEvent
     */
    public void actionButtonPressed(ActionEvent actionEvent) {
        // объект источник вызывающий метод
        Object source = actionEvent.getSource();

        // если нажата не кнопка - выходим из метода
        // проверяем если объект не являеться кнопкой то выходим
        if (!(source instanceof Button)) {
            return;
        }
        // нисходящее приведение в объект button
        Button clickedButton = (Button) source;

        // получаем id кнопки
        switch (clickedButton.getId()) {
            // Кнопка "Добавить"
            case "btnAdd":
                // создает объект и передает в ячейки добавления данных
                addController.setRecord(new Record());
                // вызов окна добавления данных
                addDialog();
                // добавляет в колекцию объект, записывает в файл и выводит в таблицу
                buyManagerImpl.addCollection(addController.getRecord(), (String) boxSelect.getValue());
                break;
            // Кнопка "Удалить"
            case "btnDelete":
                buyManagerImpl.deleteCollection((Record) tableBuy.getSelectionModel().getSelectedItem(), (String) boxSelect.getValue());
                break;

             /*   // Кнопка "Изменить"
            case "btnEdit":

               /*
                // получаем одну выбранную запись. И делаем нисходящее приведение в объект Record

                addController.setRecord((Record) tableBuy.getSelectionModel().getSelectedItem());

                // вызов второго окна
                addDialog();
                break;
                */
        }
    }

    /**
     * Вызов второго окна для добавления данных
     */
    private void addDialog() {
        if (editDialogStage==null) {
            editDialogStage = new Stage();
            editDialogStage.setTitle("Редактирование записи");
            editDialogStage.setMinHeight(200);
            editDialogStage.setMinWidth(300);
            editDialogStage.setResizable(false);
            editDialogStage.setScene(new Scene(fxmlEdit));
            editDialogStage.getScene().getStylesheets().add(0, "/styles/style.css");
            editDialogStage.initModality(Modality.WINDOW_MODAL);
            editDialogStage.initOwner(mainStage);
        }
        // для ожидания закрытия окна
        editDialogStage.showAndWait();
    }

    /**
     * При изменениях в ComboBox очищает таблицу
     * и выводит коллекцию в таблицу из текущего .txt файла
     * @param actionEvent
     */
    public void actionComboBox(ActionEvent actionEvent){
        buyManagerImpl.clear();
        buyManagerImpl.selectCollection((String) boxSelect.getValue());
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

}

