package com.faenko.mavenjavafxcosts.controller;

import com.faenko.mavenjavafxcosts.object.Record;
import com.faenko.mavenjavafxcosts.utils.DialogManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Контроллер окна добавления данных
 *
 * @author Artem Faenko
 */
public class AddController  {

    @FXML
    private ComboBox boxPayment;

    @FXML
    private TextField textNumber;

    @FXML
    private TextField textSum;

    @FXML
    private DatePicker pickerDate;

    @FXML
    private Button btnOK;

    @FXML
    private Button btnCancel;

    private Record record;
    private ResourceBundle resourceBundle;

    /**
     * Добавление данных из окнав в объект
     * @param record
     */
    public void setRecord(Record record) {

        if (record == null){
            return;
        }

        this.record = record;
        boxPayment.getItems().setAll("Газоснабжение", "Водоснабжение", "Электроэнергия","Покупки" );
        boxPayment.setValue("Газоснабжение");
        pickerDate.setUserData(record.getDate());
        textNumber.setText(record.getNumber());
        textSum.setText(record.getSum());
    }

    public Record getRecord() {
        return record;
    }

    /**
     * Вызываеться при нажатии кнопки Отмена и метода actionSave при сохранении записи
     * @param actionEvent
     */
    public void actionClose(ActionEvent actionEvent) {
        // закрываем текущее окно без знания кто его открыл
        Node source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    /**
     * Вызываеться при нажатии кнопки ОК
     * @param actionEvent
     */
    public void actionSave(ActionEvent actionEvent) {
        if (!checkValues()){
            return;
        }
        record.setPayment(boxPayment.getValue());
        LocalDate date = pickerDate.getValue();
        // ReadOnlyObjectProperty df = boxPayment.getSelectionModel().selectedItemProperty();
        //  System.out.println(df);
        record.setDate(date);
        record.setNumber(textNumber.getText());
        record.setSum(textSum.getText());
        actionClose(actionEvent);
    }

    /**
     * Вызывает диалогое окно при сохранении пустых строк "Показания" и "Стоимость"
     * @return
     */
    private boolean checkValues() {
        if (textNumber.getText().trim().length()==0 || textSum.getText().trim().length()==0){
            DialogManager.showInfoDialog(resourceBundle.getString("error"), resourceBundle.getString("fill_field"));
            return false;
        }
        return true;
    }

}
