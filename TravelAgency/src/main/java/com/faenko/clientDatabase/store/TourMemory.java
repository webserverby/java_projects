package com.faenko.clientDatabase.store;

import com.faenko.clientDatabase.models.Tour;

import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TourMemory implements StorageTour{

    private final AtomicInteger ids = new AtomicInteger();

    private final ConcurrentHashMap<Integer, Tour> tours = new ConcurrentHashMap<>();

    private final AtomicInteger idFound = new AtomicInteger();

    private final ConcurrentHashMap<Integer, Tour> found = new ConcurrentHashMap<>();

    @Override
    public Collection<Tour> values() {
        return this.tours.values();
    }

    @Override
    public Collection<Tour> valuesFound() {
        return this.found.values();
    }

    @Override
    public void add(Tour tour) {
        this.tours.put(tour.getId(), tour);
    }

    @Override
    public void edit(Tour tour) {
        this.tours.replace(tour.getId(), tour);
    }

    @Override
    public void delete(int id) {
        this.tours.remove(id);
    }

    @Override
    public void foldCounter() {
        tours.clear();
    }

    @Override
    public Tour get(int id) {
        return this.tours.get(id);
    }

    @Override
    public void find(String idTour, String tourNameTour, String routeCityCome, String routeCountryCome) {
        this.found.clear();

        if (!Objects.equals(idTour, "")) {
            findIdTour(Integer.valueOf(idTour));
        } else {
            findThreeParameters(tourNameTour, routeCityCome, routeCountryCome);
            if (found.isEmpty()) {
                findTwoParameters(tourNameTour, routeCityCome, routeCountryCome);
                if (found.isEmpty())
                    findOneParameters(tourNameTour, routeCityCome, routeCountryCome);
            }
        }
    }

    public void findIdTour(int idTour) {
        for (final Tour tour : this.tours.values()) {
            if (tour.getId() == idTour) {
                this.found.put(this.idFound.incrementAndGet(), tour);
            }
        }
    }

    public void findThreeParameters(String tourNameTour, String routeCityCome, String routeCountryCome) {
        for (final Tour tour : this.tours.values()) {
            if (tour.getNameTour().equals(tourNameTour) && tour.getRoute().getCityCome().equals(routeCityCome) &&
                    tour.getRoute().getCountryCome().equals(routeCountryCome) && !Objects.equals(routeCountryCome, "")) {
                this.found.put(this.idFound.incrementAndGet(), tour);
            }
        }
    }

    public void findTwoParameters(String tourNameTour, String routeCityCome, String routeCountryCome) {
        for (final Tour tour : this.tours.values()) {
            if (tour.getNameTour().equals(tourNameTour) && tour.getRoute().getCityCome().equals(routeCityCome)) {
                this.found.put(this.idFound.incrementAndGet(), tour);
            } else if (tour.getNameTour().equals(tourNameTour) && tour.getRoute().getCountryCome().equals(routeCountryCome)
                    && !Objects.equals(routeCountryCome, "")) {
                this.found.put(this.idFound.incrementAndGet(), tour);
            } else if (tour.getRoute().getCityCome().equals(routeCityCome) && tour.getRoute().getCountryCome().equals(routeCountryCome)
                    && !Objects.equals(routeCountryCome, "")) {
                this.found.put(this.idFound.incrementAndGet(), tour);
            }
        }
    }

    public void findOneParameters(String tourNameTour, String routeCityCome, String routeCountryCome) {
        for (final Tour tour : this.tours.values()) {
            if (tour.getNameTour().equals(tourNameTour)) {
                this.found.put(this.idFound.incrementAndGet(), tour);
            } else if (tour.getRoute().getCityCome().equals(routeCityCome)) {
                this.found.put(this.idFound.incrementAndGet(), tour);
            } else if (tour.getRoute().getCountryCome().equals(routeCountryCome)
                    && !Objects.equals(routeCountryCome, "")) {
                this.found.put(this.idFound.incrementAndGet(), tour);
            }
        }
    }

    @Override
    public int generateId() {
        return this.ids.incrementAndGet();
    }

    @Override
    public void close() {
    }

}
