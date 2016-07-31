package com.faenko.clientDatabase.store;

import com.faenko.clientDatabase.models.Client;
import com.faenko.clientDatabase.models.Tour;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TourHibernate implements StorageTour{

    private final SessionFactory factory;

    private List<Tour> found = new ArrayList<>();
    private List<Tour> checkFound = new ArrayList<>();

    private final String HQL_SELECT_ALL = "FROM Tour AS tour INNER JOIN FETCH tour.route AS route";

    public TourHibernate() {
        this.factory = new Configuration().configure().buildSessionFactory();
    }

    public interface Command<T> {
        T process(Session session);
    }

    private <T> T transaction(final Command<T> command) {
        final Session session = this.factory.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            return command.process(session);
        } finally {
            tx.commit();
            session.close();
        }
    }

    @Override
    public Collection<Tour> valuesFound() {
        return this.found;
    }

    /**
     * Лямбда - (имя входного параметра -> {действия с этим параметром});
     * @return
     */
    @Override
    public Collection<Tour> values() {
        return transaction(session -> session.createQuery(HQL_SELECT_ALL + " " + "ORDER BY tour.id").list());
    }

    @Override
    public void add(Tour tour) {
        transaction(session -> session.save(tour));
    }

    @Override
    public void edit(Tour tour) {
        transaction(session -> {
            session.update(tour);
            return null;
        });
    }

    @Override
    public void delete(int id) {
        transaction(session -> {
            session.delete(get(id));
            return null;
        });
    }

    @Override
    public void foldCounter() {
    }

    @Override
    public Tour get(int id) {
        return transaction(session -> (Tour) session.get(Tour.class, id));
    }


    @Override
    public void find(String idTour, String tourNameTour, String routeCityCome, String routeCountryCome) {
        this.found.clear();

        if (!idTour.equals(""))
            findIdClient(Integer.valueOf(idTour));
        else
        if (!findThreeParameters(tourNameTour, routeCityCome, routeCountryCome))
            if (!findTwoParameters(tourNameTour, routeCityCome, routeCountryCome))
                findOneParameters(tourNameTour, routeCityCome, routeCountryCome);
    }

    public void findIdClient(int idTour) {
        transaction(session -> {
            final Query query = session.createQuery(HQL_SELECT_ALL + " " + "WHERE tour.id=:id");
            query.setInteger("id", idTour);
            this.checkFound = query.list();
            for (Tour tour : this.checkFound) {
                if (tour.getId() == idTour)
                    this.found.add(tour);
            }
            return null;
        });
    }

    public boolean findThreeParameters(String tourNameTour, String routeCityCome, String routeCountryCome) {
        return transaction(session -> {
            final Query query = session.createQuery(HQL_SELECT_ALL + " " +
                    "WHERE tour.nameTour=:tourNameTour AND route.cityCome=:routeCityCome AND route.countryCome=:routeCountryCome");
            query.setString("tourNameTour", tourNameTour);
            query.setString("routeCityCome", routeCityCome);
            query.setString("routeCountryCome", routeCountryCome);
            this.checkFound = query.list();
            if (!this.checkFound.isEmpty()) {
                this.found.addAll(this.checkFound);
                return true;
            }
            return false;
        });
    }

    public boolean findTwoParameters(String tourNameTour, String routeCityCome, String routeCountryCome) {
        return transaction(session -> {
            boolean check = false;
            final Query query = session.createQuery(HQL_SELECT_ALL);
            this.checkFound = query.list();
            for (Tour tour : this.checkFound) {
                if (tour.getNameTour().equals(tourNameTour) && tour.getRoute().getCityCome().equals(routeCityCome)) {
                    this.found.add(tour);
                    check = true;
                } else
                if (tour.getNameTour().equals(tourNameTour) && tour.getRoute().getCountryCome().equals(routeCountryCome)
                        && !routeCountryCome.equals("")) {
                    this.found.add(tour);
                    check = true;
                } else
                if (tour.getRoute().getCityCome().equals(routeCityCome) && tour.getRoute().getCountryCome().equals(routeCountryCome)
                        && !routeCountryCome.equals("")) {
                    this.found.add(tour);
                    check = true;
                }
            }
            return check;
        });
    }

    public void findOneParameters(String tourNameTour, String routeCityCome, String routeCountryCome) {
        transaction(session -> {
            final Query query = session.createQuery(HQL_SELECT_ALL);
            this.checkFound = query.list();
            for (Tour tour : this.checkFound) {
                if (tour.getNameTour().equals(tourNameTour)) this.found.add(tour);
                else if (tour.getRoute().getCityCome().equals(routeCityCome)) this.found.add(tour);
                else if (tour.getRoute().getCountryCome().equals(routeCountryCome) && !routeCountryCome.equals("")) this.found.add(tour);
            }
            return null;
        });
    }

    @Override
    public int generateId() {
        return 0;
    }

    @Override
    public void close() {
        this.factory.close();
    }
}
