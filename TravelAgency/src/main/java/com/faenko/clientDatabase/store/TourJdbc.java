package com.faenko.clientDatabase.store;


import com.faenko.clientDatabase.models.Route;
import com.faenko.clientDatabase.models.Tour;
import com.faenko.clientDatabase.service.Settings;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Реализация списка туров на основе SQL базы данных
 *
 * @author Artem Faenko
 */
public class TourJdbc implements StorageTour{
    /** Соединение с БД */
    private Connection connection;
    /** Список при запросе всех туров */
    private final List<Tour> tours = new ArrayList<>();
    /** Список при поиске тура */
    private final List<Tour> found = new ArrayList<>();
    /**
     * Запрос по 2 таблицам, связаны по client.passport_id = passport.uid
     * Обьединение 2 таблиц через команду INNER JOIN
     */
    private final String SQL_SELECT_ALL = "SELECT * FROM tour INNER JOIN route ON tour.route_id = route.uid";

    /**
     * Конструктор
     */
    public TourJdbc() {
        final Settings settings = Settings.getInstance();
        try {
            /** обращение к базе данных */
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
     * Поиск туров по заданным критериям в Базе данных
     * @return Список найденных туров
     */
    @Override
    public Collection<Tour> valuesFound() {
        return found;
    }

    /**
     * Получить список туров из Базы данных
     * @return Клиенты
     */
    @Override
    public Collection<Tour> values() {
        tours.clear();
        try (final Statement statement = this.connection.createStatement();
             final ResultSet rs = statement.executeQuery
                     (SQL_SELECT_ALL + " " + "ORDER BY tour.uid")) {
            while (rs.next()) {
                tours.add(new Tour(rs.getInt("uid"), rs.getString("nameTour"),rs.getString("dateBegin"),
                        rs.getString("dateEnd"),rs.getString("dayNumber"),rs.getString("personNumber"),
                        rs.getString("tourOperator"),rs.getString("hotel"), rs.getString("typeNumber"), rs.getString("food"), rs.getString("tourСost"),
                        new Route(rs.getInt("route_id"), rs.getString("transportName"), rs.getString("cityDeparture"),
                                rs.getString("cityCome"),rs.getString("countryCome"),rs.getString("dateDeparture"),rs.getString("dateCome"),rs.getString("dateReturn"))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tours;
    }

     /**
     * Добавление тура в БД
     *
     * @param tour Тур
     */
    public void addTour(Tour tour) {
        try (final PreparedStatement statement = this.connection.prepareStatement
                ("insert into tour (nameTour, dateBegin, dateEnd, dayNumber, personNumber, tourOperator, hotel, typeNumber, food, tourСost, route_id) values (?,?,?,?,?,?,?,?,?,?,?)")) {
            statement.setString(1, tour.getNameTour());
            statement.setString(2, tour.getDateBegin());
            statement.setString(3, tour.getDateEnd());
            statement.setString(4, tour.getDayNumber());
            statement.setString(5, tour.getPersonNumber());
            statement.setString(6, tour.getTourOperator());
            statement.setString(7, tour.getHotel());
            statement.setString(8, tour.getTypeNumber());
            statement.setString(9, tour.getFood());
            statement.setString(10, tour.getTourСost());
            statement.setInt(11, tour.getRoute().getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Добавление маршрута к текущему туру в базу данных
     * @param tour Тур
     */
    @Override
    public void add(Tour tour) {
        /** RETURN_GENERATED_KEYS - генерация ключа для новой записи в базе */
        try (final PreparedStatement statement = this.connection.prepareStatement
                ("insert into route (transportName, cityDeparture, cityCome, countryCome, dateDeparture, dateCome, dateReturn) values (?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, tour.getRoute().getTransportName());
            statement.setString(2, tour.getRoute().getCityDeparture());
            statement.setString(3, tour.getRoute().getCityCome());
            statement.setString(4, tour.getRoute().getCountryCome());
            statement.setString(5, tour.getRoute().getDateDeparture());
            statement.setString(6, tour.getRoute().getDateCome());
            statement.setString(7, tour.getRoute().getDateReturn());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    tour.getRoute().setId(generatedKeys.getInt(1));
                }
            }
            addTour(tour);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Редактирование тура и маршрута в базе данных
     * @param tour Клиент
     */
    @Override
    public void edit(Tour tour) {
        PreparedStatement editRoute = null;
        PreparedStatement editTour = null;
        String stringEditRoute = "UPDATE route SET transportName = (?), cityDeparture = (?), cityCome = (?), countryCome = (?), dateDeparture = (?), dateCome = (?), dateReturn = (?) WHERE uid = (?)";
        String stringEditTour = "UPDATE tour SET nameTour = (?), dateBegin = (?), dateEnd = (?), dayNumber = (?), personNumber = (?), tourOperator = (?), hotel = (?), typeNumber = (?), food = (?), tourСost = (?)  WHERE uid = (?)";
        try {
            this.connection.setAutoCommit(false);
            editRoute = this.connection.prepareStatement(stringEditRoute);
            editTour = this.connection.prepareStatement(stringEditTour);

            editRoute.setString(1, tour.getRoute().getTransportName());
            editRoute.setString(2, tour.getRoute().getCityDeparture());
            editRoute.setString(3, tour.getRoute().getCityCome());
            editRoute.setString(4, tour.getRoute().getCountryCome());
            editRoute.setString(5, tour.getRoute().getDateDeparture());
            editRoute.setString(6, tour.getRoute().getDateCome());
            editRoute.setString(7, tour.getRoute().getDateReturn());
            editRoute.setInt(8, tour.getRoute().getId());
            editRoute.executeUpdate();

            editTour.setString(1, tour.getNameTour());
            editTour.setString(2, tour.getDateBegin());
            editTour.setString(3, tour.getDateEnd());
            editTour.setString(4, tour.getDayNumber());
            editTour.setString(5, tour.getPersonNumber());
            editTour.setString(6, tour.getTourOperator());
            editTour.setString(7, tour.getHotel());
            editTour.setString(8, tour.getTypeNumber());
            editTour.setString(9, tour.getFood());
            editTour.setString(10, tour.getTourСost());
            editTour.setInt(11, tour.getRoute().getId());
            editTour.executeUpdate();

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
                if (editRoute != null) editRoute.close();
                if (editTour != null) editTour.close();
                this.connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Удаление тура
     * @param id
     */
    @Override
    public void delete(int id) {
        int sum = 0;

        try (Statement statement = this.connection.createStatement();
             final ResultSet rs = statement.executeQuery(SQL_SELECT_ALL)) {
            while (rs.next()) {
                if (rs.getInt("uid") == id) {
                    sum = rs.getInt("route_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (final PreparedStatement statement = this.connection.prepareStatement
                ("DELETE FROM tour WHERE uid = (?)")) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        /** Вызов удаления маршрута */
        deleteRoute(sum);
    }

    /**
     * Удаление маршрута
     * @param id
     */
    public void deleteRoute(int id) {
        try (final PreparedStatement statement = this.connection.prepareStatement
                ("DELETE FROM route WHERE uid = (?)")) {
            statement.setInt(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) throw new SQLException("Ошибка! Удаление тура не удалось!");
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
             final ResultSet rs = statement.executeQuery("select count(*) as count from tour")) {
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
            statement.addBatch("DELETE FROM route");
            statement.addBatch("DELETE FROM tour");
            statement.addBatch("ALTER SEQUENCE tour_uid_seq RESTART WITH 1");
            statement.addBatch("ALTER SEQUENCE route_uid_seq RESTART WITH 1");
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Tour get(int id) {
        try (final PreparedStatement statement = this.connection.prepareStatement
                (SQL_SELECT_ALL + " " + "where tour.uid = (?)")) {
            statement.setInt(1, id);
            try (final ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    return new Tour(rs.getInt("uid"), rs.getString("nameTour"),rs.getString("dateBegin"),
                            rs.getString("dateEnd"),rs.getString("dayNumber"),rs.getString("personNumber"),
                            rs.getString("tourOperator"),rs.getString("hotel"), rs.getString("typeNumber"), rs.getString("food"), rs.getString("tourСost"),
                            new Route(rs.getInt("route_id"), rs.getString("transportName"), rs.getString("cityDeparture"),
                                    rs.getString("cityCome"),rs.getString("countryCome"),rs.getString("dateDeparture"),rs.getString("dateCome"),rs.getString("dateReturn")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException(String.format("User %s does not exists", id));
    }

    @Override
    public void find(String idTour, String tourNameTour, String routeCityCome, String routeCountryCome) {
        this.found.clear();

        if (!idTour.equals(""))
            findIdTour(Integer.valueOf(idTour));
        else
        if (!findThreeParameters(tourNameTour, routeCityCome, routeCountryCome))
            if (!findTwoParameters(tourNameTour, routeCityCome, routeCountryCome))
                findOneParameters(tourNameTour, routeCityCome, routeCountryCome);
    }

    public void foundAdd(ResultSet rs) throws SQLException {
        found.add(new Tour(rs.getInt("uid"), rs.getString("nameTour"),rs.getString("dateBegin"),
                rs.getString("dateEnd"),rs.getString("dayNumber"),rs.getString("personNumber"),
                rs.getString("tourOperator"),rs.getString("hotel"), rs.getString("typeNumber"), rs.getString("food"), rs.getString("tourСost"),
                new Route(rs.getInt("route_id"), rs.getString("transportName"), rs.getString("cityDeparture"),
                        rs.getString("cityCome"),rs.getString("countryCome"),rs.getString("dateDeparture"),rs.getString("dateCome"),rs.getString("dateReturn") )));
    }

    /**
     * Поиск по id тура
     * @param idTour
     */
    public void findIdTour(int idTour) {
        try (final Statement statement = this.connection.createStatement();
             final ResultSet rs = statement.executeQuery
                     ("select * from tour right join route on tour.route_id = route.uid")) {
            while (rs.next()) {
                if (rs.getInt("uid") == idTour) {
                    foundAdd(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean findThreeParameters(String tourNameTour, String routeCityCome, String routeCountryCome) {
        boolean check = false;
        try (final Statement statement = this.connection.createStatement();
             final ResultSet rs = statement.executeQuery(SQL_SELECT_ALL)) {
            while (rs.next()) {
                if (findTourNameTour(rs.getInt("uid"), tourNameTour) && findRouteCityCome(rs.getInt("uid"), routeCityCome) &&
                        findRouteCountryCome(rs.getInt("uid"), routeCountryCome) && !Objects.equals(routeCountryCome, "")) {
                    foundAdd(rs);
                    check = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return check;
    }

    public boolean findTwoParameters(String tourNameTour, String routeCityCome, String routeCountryCome) {
        boolean check = false;
        try (final Statement statement = this.connection.createStatement();
             final ResultSet rs = statement.executeQuery(SQL_SELECT_ALL)) {
            while (rs.next()) {
                if (findTourNameTour(rs.getInt("uid"), tourNameTour) && findRouteCityCome(rs.getInt("uid"), routeCityCome)) {
                    foundAdd(rs);
                    check = true;
                } else
                if (findTourNameTour(rs.getInt("uid"), tourNameTour) && findRouteCountryCome(rs.getInt("uid"), routeCountryCome)
                        && !Objects.equals(routeCountryCome, "")) {
                    foundAdd(rs);
                    check = true;
                } else
                if (findRouteCityCome(rs.getInt("uid"), routeCityCome) && findRouteCountryCome(rs.getInt("uid"), routeCountryCome)
                        && !Objects.equals(routeCountryCome, "")) {
                    foundAdd(rs);
                    check = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return check;
    }

    public void findOneParameters(String tourNameTour, String routeCityCome, String routeCountryCome) {
        try (final Statement statement = this.connection.createStatement();
             final ResultSet rs = statement.executeQuery(SQL_SELECT_ALL)) {
            while (rs.next()) {
                if (findTourNameTour(rs.getInt("uid"), tourNameTour)) {
                    foundAdd(rs);
                } else
                if (findRouteCityCome(rs.getInt("uid"), routeCityCome)) {
                    foundAdd(rs);
                } else
                if (findRouteCountryCome(rs.getInt("uid"), routeCountryCome) && !routeCountryCome.equals("")) {
                    foundAdd(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Поиск по названию тура
     * @param id
     * @param tourNameTour
     * @return
     */
    public boolean findTourNameTour(int id, String tourNameTour) {
        try (final PreparedStatement statement = this.connection.prepareStatement
                (SQL_SELECT_ALL + " " + "where tour.uid = (?)")) {
            statement.setInt(1, id);
            try (final ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    if (Objects.equals(rs.getString("nameTour"), tourNameTour))
                        return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Поиск по городу прибытия
     * @param id
     * @param routeCityCome
     * @return
     */
    public boolean findRouteCityCome(int id, String routeCityCome) {
        try (final PreparedStatement statement = this.connection.prepareStatement
                (SQL_SELECT_ALL + " " + "where tour.uid = (?)")) {
            statement.setInt(1, id);
            try (final ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    if (Objects.equals(rs.getString("cityCome"), routeCityCome))
                        return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Поиск по стране прибытия
     * @param id
     * @param routeCountryCome
     * @return
     */
    public boolean findRouteCountryCome(int id, String routeCountryCome) {
        try (final PreparedStatement statement = this.connection.prepareStatement
                (SQL_SELECT_ALL + " " + "where tour.uid = (?)")) {
            statement.setInt(1, id);
            try (final ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    if (Objects.equals(rs.getString("countryCome"), routeCountryCome))
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
