package com.faenko.clientDatabase.store;

import com.faenko.clientDatabase.models.Client;

import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientMemory implements StorageClient{

    private final AtomicInteger ids = new AtomicInteger();

    private final ConcurrentHashMap<Integer, Client> clients = new ConcurrentHashMap<>();

    private final AtomicInteger idFound = new AtomicInteger();

    private final ConcurrentHashMap<Integer, Client> found = new ConcurrentHashMap<>();

    @Override
    public Collection<Client> values() {
        return this.clients.values();
    }

    @Override
    public Collection<Client> valuesFound() {
        return this.found.values();
    }

    @Override
    public void add(Client client) {
        this.clients.put(client.getId(), client);
    }

    @Override
    public void edit(Client client) {
        this.clients.replace(client.getId(), client);
    }

    @Override
    public void delete(int id) {
        this.clients.remove(id);
    }

    @Override
    public void foldCounter() {
        clients.clear();
    }

    @Override
    public Client get(int id) {
        return this.clients.get(id);
    }

    @Override
    public void find(String idClient, String clientSurname, String clientName, String passportNumber) {
        this.found.clear();

        if (!Objects.equals(idClient, "")) {
            findIdClient(Integer.valueOf(idClient));
        } else {
            findThreeParameters(clientSurname, clientName, passportNumber);
            if (found.isEmpty()) {
                findTwoParameters(clientSurname, clientName, passportNumber);
                if (found.isEmpty())
                    findOneParameters(clientSurname, clientName, passportNumber);
            }
        }
    }

    public void findIdClient(int idClient) {
        for (final Client client : this.clients.values()) {
            if (client.getId() == idClient) {
                this.found.put(this.idFound.incrementAndGet(), client);
            }
        }
    }

    public void findThreeParameters(String clientSurname, String clientName, String passportNumber) {
        for (final Client client : this.clients.values()) {
            if (client.getSurname().equals(clientSurname) && client.getName().equals(clientName) &&
                    client.getPassport().getNumber().equals(passportNumber) && !Objects.equals(passportNumber, "")) {
                this.found.put(this.idFound.incrementAndGet(), client);
            }
        }
    }

    public void findTwoParameters(String clientSurname, String clientName, String passportNumber) {
        for (final Client client : this.clients.values()) {
            if (client.getSurname().equals(clientSurname) && client.getName().equals(clientName)) {
                this.found.put(this.idFound.incrementAndGet(), client);
            } else if (client.getSurname().equals(clientSurname) && client.getPassport().getNumber().equals(passportNumber)
                    && !Objects.equals(passportNumber, "")) {
                this.found.put(this.idFound.incrementAndGet(), client);
            } else if (client.getName().equals(clientName) && client.getPassport().getNumber().equals(passportNumber)
                    && !Objects.equals(passportNumber, "")) {
                this.found.put(this.idFound.incrementAndGet(), client);
            }
        }
    }

    public void findOneParameters(String clientSurname, String clientName, String passportNumber) {
        for (final Client client : this.clients.values()) {
            if (client.getSurname().equals(clientSurname)) {
                this.found.put(this.idFound.incrementAndGet(), client);
            } else if (client.getName().equals(clientName)) {
                this.found.put(this.idFound.incrementAndGet(), client);
            } else if (client.getPassport().getNumber().equals(passportNumber)
                    && !Objects.equals(passportNumber, "")) {
                this.found.put(this.idFound.incrementAndGet(), client);
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
