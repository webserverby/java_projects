package com.faenko.mavenjavafxcosts.implement;

import com.faenko.mavenjavafxcosts.interfaces.BuyManager;
import com.faenko.mavenjavafxcosts.object.Record;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Вспомогательный класс, чтобы создать коллекцию для хранения данных из Record
 *
 * @author Artem Faenko
 */
public class CollectionBuyManager implements BuyManager {

    ObservableList<Record> recordList = FXCollections.observableArrayList();

    /**
     * Заносит данные по объекту в .txt файл и коллекцию
     * @param record
     * @param name
     */
    @Override
    public void addCollection(Record record, String name) {
        List<String> lines = new ArrayList();
        String way = wayFile(name);

        Record das = record;
        String payment = das.getPayment();
        String date = das.getDate();
        String number = new String(das.getNumber());
        String sum =  new String (das.getSum());
        lines.add(payment + " " + date + " " + number + " " + sum);

        writeFile(lines, way);
        // добавляет новый обьект в таблицу
        recordList.add(record);
    }


    // для коллекции не используется, но пригодится для случая, когда данные хранятся в БД и пр.
    // т.к. коллекция и является хранилищем - то ничего обновлять не нужно
    // если бы данные хранились в БД или файле - в этом методе нужно было бы обновить соотв. запись
    @Override
    public void updateCollection(Record record, String name) {

    }

    /**
     * Удаляет данные из файла и таблицы
     * открывает .txt файл и заносит в коллецию
     * цикл while получает выбранную строку и удаляет ее из коллекции
     * записываеться новый .txt файл с обновленной коллекцией
     * @param record
     * @param name
     */
    @Override
    public void deleteCollection(Record record, String name) {

        List<String> lines =  openFile(name);

        Iterator<String> iter = lines.iterator();
        while (iter.hasNext()) {
            String s = iter.next();

            if (s.equals(record.getPayment()+" "+ record.getDate()+" "+record.getNumber()+" "+record.getSum())) {
                iter.remove();
            }
        }

        String way = wayFile(name);
        File file = new File(way);
        file.delete();
        /*
        if(file.delete()){
            System.out.println("Файл " + url + " удален");
        }else System.out.println("Файл " + url + " не обнаружен");
        */

        writeFile(lines, way);
        recordList.remove(record);
    }

    public ObservableList<Record> getRecordList() {
        return recordList;
    }

    /**
     * Выводит данные коллекции в таблицу интерфейса
     * Открывает .txt файл и заносит данные в коллекцию вместе с принимаемым значение от ComboBox
     * выводит данные в таблицу интерфейса
     * @param name
     */
    public void selectCollection(String name)  {

        List<String> lines =  openFile(name);

        for (String x : lines) {

            StringTokenizer stw2 = new StringTokenizer(x);
            String payment = stw2.nextToken();
            String date = stw2.nextToken();
            String number = stw2.nextToken();
            String sum = stw2.nextToken();

            recordList.add(new Record(payment, date, number, sum));
        }

    }

    /**
     * Запись коллекции в .txt файл
     * @param lines
     * @param url
     */
    private void writeFile(List<String> lines, String url) {

        try{
            FileWriter writer = new FileWriter(url, true);
            for(String line : lines)
            {
                writer.write(line);
                writer.write(System.getProperty("line.separator"));
            }
            writer.close();
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Открывает .txt файл и возвращает коллекцию
     * @param name
     * @return
     */
    private List<String> openFile(String name) {
        List<String> lines = null;
        if (name == "Газоснабжение") {
            try {
                lines = Files.readAllLines(Paths.get("gas.txt"), Charset.defaultCharset());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (name == "Водоснабжение"){
            try {
                lines = Files.readAllLines(Paths.get("water.txt"), Charset.defaultCharset());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (name == "Электроэнергия"){
            try {
                lines = Files.readAllLines(Paths.get("electric.txt"), Charset.defaultCharset());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (name == "Покупки") {
            try {
                lines = Files.readAllLines(Paths.get("buy.txt"), Charset.defaultCharset());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return lines;
    }

    /**
     * Возвращает ссылку на .txt файл
     * @param name
     * @return
     */
    private String wayFile(String name) {
        String way = null;
        if (name == "Газоснабжение") {
            way = "gas.txt";
        } else if (name == "Водоснабжение"){
            way = "water.txt";
        }else if (name == "Электроэнергия"){
            way = "electric.txt";
        } else if (name == "Покупки") {
            way = "buy.txt";
        }
        return way;
    }

    /**
     * Очищает коллекцию при изменениях ComboBox
     */
    public void clear(){
        recordList.clear();
    }

}
