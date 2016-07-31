package com.faenko.mavenjavafxcosts.object;

import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Модель класса Record
 *
 * @author Artem Faenko
 */
public class Record implements Serializable {
    // абстрактный класс SimpleStringProperty, чтобы при изменении данных из полей сразу отображались в таблице
    private SimpleStringProperty payment = new SimpleStringProperty("");
    private  SimpleStringProperty date = new SimpleStringProperty("");
    private SimpleStringProperty number = new SimpleStringProperty("");
    private SimpleStringProperty sum = new SimpleStringProperty("");

    /**
     * Конструктор по умолчанию
     */
    public Record(){}

    /**
     * Конструктор с начальными данными
     * @param payment
     * @param date
     * @param number
     * @param sum
     */
    public Record(String payment, String date, String number, String sum) {
        this.payment = new SimpleStringProperty(payment);
        this.date = new SimpleStringProperty(date);
        this.number = new SimpleStringProperty(number);
        this.sum = new SimpleStringProperty(sum);
    }

    public String getPayment() {
        return payment.get();
    }

    public void setPayment(Object payment) {
        this.payment.set(String.valueOf(payment));
    }

    public String getDate() {
        return date.get();
    }

    /**
     * Вносит дату формата 01.01.2016
     * @param date
     */
    public void setDate(LocalDate date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d.MM.YYYY");
        this.date.set(String.valueOf(dateTimeFormatter.format(date)));
    }

    public String getNumber() {
        return number.get();
    }

    public void setNumber(String number) {
        this.number.set(number);
    }

    public String getSum() {
        return sum.get();
    }

    public void setSum(String sum) {
        this.sum.set(sum);
    }

    public SimpleStringProperty paymentProperty() {
        return payment;
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public SimpleStringProperty numberProperty() {
        return number;
    }

    public SimpleStringProperty sumProperty() {
        return sum;
    }

    @Override
    public String toString() {
        return "Record{" +
                "payment='" + payment + '\'' +
                ", date='" + date + '\'' +
                ", number='" + number + '\'' +
                ", sum='" + sum + '\'' +
                '}';
    }

}
