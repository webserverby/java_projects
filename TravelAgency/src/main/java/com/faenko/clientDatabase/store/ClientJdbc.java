package com.faenko.clientDatabase.store;

import com.faenko.clientDatabase.models.Client;
import com.faenko.clientDatabase.models.Passport;
import com.faenko.clientDatabase.service.Settings;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Реализация списка клиентов на основе SQL базы данных
 *
 * @author Artem Faenko
 */
public class ClientJdbc implements StorageClient{
    /** Соединение с БД */
    private Connection connection;
    /** Список при запросе всех клиентов */
    private final List<Client> clients = new ArrayList<>();
    /** Список при поиске клиентов */
    private final List<Client> found = new ArrayList<>();
    /**
     * Запрос по 2 таблицам, связаны по client.passport_id = passport.uid
     * Обьединение 2 таблиц через команду INNER JOIN
     */
    private final String SQL_SELECT_ALL = "SELECT * FROM client INNER JOIN passport ON client.passport_id = passport.uid";

    /**
     * Конструктор
     */
    public ClientJdbc() {
        final Settings settings = Settings.getInstance();
        try {
            /** Подключение к базе данных */
            Class.forName(settings.value("jdbc.driver_class"));
            this.connection = DriverManager.getConnection(
                    settings.value("jdbc.url"),
                    settings.value("jdbc.username"),
                    settings.value("jdbc.password")
            );

            if (!connection.isClosed())
                System.out.println("Соединение с БД установлено!");
            if (connection.isClosed())
                System.out.println("Соединение с БД закрыто!");
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Поиск клиентов по заданным критериям в Базе данных
     * @return Список найденных клиентов
     */
    @Override
    public Collection<Client> valuesFound() {
        return found;
    }

    /**
     * Получить список клиентов из Базы данных
     * @return Клиенты
     */
    @Override
    public Collection<Client> values() {
        clients.clear();
        try (final Statement statement = this.connection.createStatement();
             final ResultSet rs = statement.executeQuery
                     (SQL_SELECT_ALL + " " + "ORDER BY client.uid")) {
            /** считывание каждой строки и передача в коллекцию clients */
            while (rs.next()) {
                clients.add(new Client(rs.getInt("uid"), rs.getString("surname"),rs.getString("name"),
                        rs.getString("patronymic"),rs.getString("phoneMobile"),rs.getString("phoneHome"),
                        rs.getString("address"),rs.getString("birthDate"),
                        new Passport(rs.getInt("passport_id"), rs.getString("series"), rs.getString("number"),
                                rs.getString("received"),rs.getString("issueDate"),rs.getString("expiryDate"))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

     /**
     * Добавление клиента в БД
     *
     * @param client Клиент
     */
    public void addClient(Client client) {
        try (final PreparedStatement statement = this.connection.prepareStatement
                ("insert into client (surname, name, patronymic, phoneMobile, phoneHome, address, birthDate, passport_id) values (?,?,?,?,?,?,?,?)")) {
            statement.setString(1, client.getSurname());
            statement.setString(2, client.getName());
            statement.setString(3, client.getPatronymic());
            statement.setString(4, client.getPhoneMobile());
            statement.setString(5, client.getPhoneHome());
            statement.setString(6, client.getAddress());
            statement.setString(7, client.getBirthDate());
            statement.setInt(8, client.getPassport().getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Добавление паспорта текущему клиенту в базу данных
     * @param client Клиент
     */
    @Override
    public void add(Client client) {
        /** RETURN_GENERATED_KEYS - генерация ключа для новой записи в базе */
        try (final PreparedStatement statement = this.connection.prepareStatement
                ("insert into passport (series, number, received, issueDate, expiryDate) values (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, client.getPassport().getSeries());
            statement.setString(2, client.getPassport().getNumber());
            statement.setString(3, client.getPassport().getReceived());
            statement.setString(4, client.getPassport().getIssueDate());
            statement.setString(5, client.getPassport().getExpiryDate());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    client.getPassport().setId(generatedKeys.getInt(1));
                }
            }
            addClient(client);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Редактирование клиента и паспорта в базе данных
     * @param client Клиент
     */
    @Override
    public void edit(Client client) {
        PreparedStatement editPassport = null;
        PreparedStatement editClient = null;
        String stringEditPassport = "UPDATE passport SET series = (?), number = (?), received = (?), issueDate = (?), expiryDate = (?) WHERE uid = (?)";
        String stringEditClient = "UPDATE client SET surname = (?), name = (?), patronymic = (?), phoneMobile = (?), phoneHome = (?), address = (?), birthDate = (?)  WHERE uid = (?)";
        try {
            this.connection.setAutoCommit(false);

            editPassport = this.connection.prepareStatement(stringEditPassport);
            editClient = this.connection.prepareStatement(stringEditClient);

            editPassport.setString(1, client.getPassport().getSeries());
            editPassport.setString(2, client.getPassport().getNumber());
            editPassport.setString(3, client.getPassport().getReceived());
            editPassport.setString(4, client.getPassport().getIssueDate());
            editPassport.setString(5, client.getPassport().getExpiryDate());
            editPassport.setInt(6, client.getPassport().getId());
            editPassport.executeUpdate();

            editClient.setString(1, client.getSurname());
            editClient.setString(2, client.getName());
            editClient.setString(3, client.getPatronymic());
            editClient.setString(4, client.getPhoneMobile());
            editClient.setString(5, client.getPhoneHome());
            editClient.setString(6, client.getAddress());
            editClient.setString(7, client.getBirthDate());
            editClient.setInt(8, client.getPassport().getId());
            editClient.executeUpdate();

            this.connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (this.connection != null)
                    this.connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                if (editPassport != null) editPassport.close();
                if (editClient != null) editClient.close();
                this.connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Удаление клиента
     * @param id
     */
    @Override
    public void delete(int id) {
        int sum = 0;

        try (Statement statement = this.connection.createStatement();
             final ResultSet rs = statement.executeQuery(SQL_SELECT_ALL)) {
            while (rs.next()) {
                if (rs.getInt("uid") == id) {
                    sum = rs.getInt("passport_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (final PreparedStatement statement = this.connection.prepareStatement
                ("DELETE FROM client WHERE uid = (?)")) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        /** Вызов удаления паспорта */
        deletePassport(sum);
    }

    /**
     * Удаление паспорта
     * @param id
     */
    public void deletePassport(int id) {
        try (final PreparedStatement statement = this.connection.prepareStatement
                ("DELETE FROM passport WHERE uid = (?)")) {
            statement.setInt(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) throw new SQLException("Ошибка! Удаление клиента не удалось!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        checkEmptyTable();
    }

    /**
     * Если таблица пустая скидываем счетчик первичных ключей
     */
    public void checkEmptyTable() {
        try (final Statement statement = this.connection.createStatement();
             final ResultSet rs = statement.executeQuery("select count(*) as count from client")) {
            rs.next();
            int count = rs.getInt("count");
            if (count == 0) foldCounter();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *  Скидываем счётчик
     */
    @Override
    public void foldCounter() {
        try (final Statement statement = this.connection.createStatement()) {
            statement.addBatch("DELETE FROM passport");
            statement.addBatch("DELETE FROM client");
            statement.addBatch("ALTER SEQUENCE client_uid_seq RESTART WITH 1");
            statement.addBatch("ALTER SEQUENCE passport_uid_seq RESTART WITH 1");
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Client get(int id) {
        try (final PreparedStatement statement = this.connection.prepareStatement
                (SQL_SELECT_ALL + " " + "where client.uid = (?)")) {
            statement.setInt(1, id);
            try (final ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    return new Client(rs.getInt("uid"), rs.getString("surname"),rs.getString("name"),
                            rs.getString("patronymic"),rs.getString("phoneMobile"),rs.getString("phoneHome"),
                            rs.getString("address"),rs.getString("birthDate"),
                            new Passport(rs.getInt("passport_id"), rs.getString("series"), rs.getString("number"),
                                    rs.getString("received"),rs.getString("issueDate"),rs.getString("expiryDate")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException(String.format("User %s does not exists", id));
    }

    @Override
    public void find(String idClient, String clientSurname, String clientName, String passportNumber) {
        this.found.clear();

        if (!idClient.equals(""))
            findIdClient(Integer.valueOf(idClient));
        else
        if (!findThreeParameters(clientSurname, clientName, passportNumber))
            if (!findTwoParameters(clientSurname, clientName, passportNumber))
                findOneParameters(clientSurname, clientName, passportNumber);
    }

    public void foundAdd(ResultSet rs) throws SQLException {
        found.add(new Client(rs.getInt("uid"), rs.getString("surname"),rs.getString("name"),
                rs.getString("patronymic"),rs.getString("phoneMobile"),rs.getString("phoneHome"),
                rs.getString("address"),rs.getString("birthDate"),
                new Passport(rs.getInt("passport_id"), rs.getString("series"), rs.getString("number"),
                        rs.getString("received"),rs.getString("issueDate"),rs.getString("expiryDate") )));
    }

    /**
     * Поиск по id клиента
     * @param idClient
     */
    public void findIdClient(int idClient) {
        try (final Statement statement = this.connection.createStatement();
             final ResultSet rs = statement.executeQuery
                     ("select * from client right join passport on client.passport_id = passport.uid")) {
            while (rs.next()) {
                if (rs.getInt("uid") == idClient) {
                    foundAdd(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean findThreeParameters(String clientSurname, String clientName, String passportNumber) {
        boolean check = false;
        try (final Statement statement = this.connection.createStatement();
             final ResultSet rs = statement.executeQuery(SQL_SELECT_ALL)) {
            while (rs.next()) {
                if (findClientSurname(rs.getInt("uid"), clientSurname) && findClientName(rs.getInt("uid"), clientName) &&
                        findPassportNumber(rs.getInt("uid"), passportNumber) && !Objects.equals(passportNumber, "")) {
                    foundAdd(rs);
                    check = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return check;
    }

    public boolean findTwoParameters(String clientSurname, String clientName, String passportNumber) {
        boolean check = false;
        try (final Statement statement = this.connection.createStatement();
             final ResultSet rs = statement.executeQuery(SQL_SELECT_ALL)) {
            while (rs.next()) {
                if (findClientSurname(rs.getInt("uid"), clientSurname) && findClientName(rs.getInt("uid"), clientName)) {
                    foundAdd(rs);
                    check = true;
                } else
                if (findClientSurname(rs.getInt("uid"), clientSurname) && findPassportNumber(rs.getInt("uid"), passportNumber)
                        && !Objects.equals(passportNumber, "")) {
                    foundAdd(rs);
                    check = true;
                } else
                if (findClientName(rs.getInt("uid"), clientName) && findPassportNumber(rs.getInt("uid"), passportNumber)
                        && !Objects.equals(passportNumber, "")) {
                    foundAdd(rs);
                    check = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return check;
    }

    public void findOneParameters(String clientSurname, String clientName, String passportNumber) {
        try (final Statement statement = this.connection.createStatement();
             final ResultSet rs = statement.executeQuery(SQL_SELECT_ALL)) {
            while (rs.next()) {
                if (findClientSurname(rs.getInt("uid"), clientSurname)) {
                    foundAdd(rs);
                } else
                if (findClientName(rs.getInt("uid"), clientName)) {
                    foundAdd(rs);
                } else
                if (findPassportNumber(rs.getInt("uid"), passportNumber) && !passportNumber.equals("")) {
                    foundAdd(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Поиск по фамилии клиента
     * @param id
     * @param clientSurname
     * @return
     */
    public boolean findClientSurname(int id, String clientSurname) {
        try (final PreparedStatement statement = this.connection.prepareStatement
                (SQL_SELECT_ALL + " " + "where client.uid = (?)")) {
            statement.setInt(1, id);
            try (final ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    if (Objects.equals(rs.getString("surname"), clientSurname))
                        return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Поиск по имени клиента
     * @param id
     * @param clientName
     * @return
     */
    public boolean findClientName(int id, String clientName) {
        try (final PreparedStatement statement = this.connection.prepareStatement
                (SQL_SELECT_ALL + " " + "where client.uid = (?)")) {
            statement.setInt(1, id);
            try (final ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    if (Objects.equals(rs.getString("name"), clientName))
                        return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Поиск по номеру паспорта
     * @param id
     * @param passportNumber
     * @return
     */
    public boolean findPassportNumber(int id, String passportNumber) {
        try (final PreparedStatement statement = this.connection.prepareStatement
                (SQL_SELECT_ALL + " " + "where client.uid = (?)")) {
            statement.setInt(1, id);
            try (final ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    if (Objects.equals(rs.getString("number"), passportNumber))
                        return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int generateId() {
        return 0;
    }

    /**
     * Закрытие соединения с БД
     */
    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    /**
//     * Интерфейс Команда, чтобы вынести повторяющийся код:
//     *
//     * try (final PreparedStatement statement = this.connection.prepareStatement(SqlQuery)) {
//     *      ----Отличающийся код----
//     * } catch (SQLException e) {
//     *     e.printStackTrace();
//     * }
//     *
//     */
//    interface Command {
//        void execute(PreparedStatement statement) throws SQLException;
//    }
//
//    /**
//     * Выполнить PreparedStatement.
//     * Использует интерфейс Команда для разделения повторяющейся и отличающейся части кода
//     * @param SqlQuery SQL-запрос
//     * @param command Реализация интерфейса Команда
//     */
//    private void executePreparedStatement(String SqlQuery, Command command) {
//        try (final PreparedStatement statement = this.connection.prepareStatement(SqlQuery)) {
//            command.execute(statement);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

}
