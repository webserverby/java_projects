package com.faenko.clientDatabase.store;

import com.faenko.clientDatabase.models.Client;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ClientHibernate implements StorageClient{

    private final SessionFactory factory;

    private List<Client> found = new ArrayList<>();
    private List<Client> checkFound = new ArrayList<>();

    private final String HQL_SELECT_ALL = "FROM Client AS client INNER JOIN FETCH client.passport AS passport";

    public ClientHibernate() {
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
    public Collection<Client> valuesFound() {
        return this.found;
    }

    /**
     * Лямбда - (имя входного параметра -> {действия с этим параметром});
     * @return
     */
    @Override
    public Collection<Client> values() {
        return transaction(session -> session.createQuery(HQL_SELECT_ALL + " " + "ORDER BY client.id").list());
    }

    @Override
    public void add(Client client) {
        transaction(session -> session.save(client));
    }

    @Override
    public void edit(Client client) {
        transaction(session -> {
            session.update(client);
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
    public Client get(int id) {
        return transaction(session -> (Client) session.get(Client.class, id));
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

    public void findIdClient(int idClient) {
        transaction(session -> {
            final Query query = session.createQuery(HQL_SELECT_ALL + " " + "WHERE client.id=:id");
            query.setInteger("id", idClient);
            this.checkFound = query.list();
            for (Client client : this.checkFound) {
                if (client.getId() == idClient)
                    this.found.add(client);
            }
            return null;
        });
    }

    public boolean findThreeParameters(String clientSurname, String clientName, String passportNumber) {
        return transaction(session -> {
            final Query query = session.createQuery(HQL_SELECT_ALL + " " +
                    "WHERE client.surname=:clientSurname AND client.name=:clientName AND passport.number=:passportNumber");
            query.setString("clientSurname", clientSurname);
            query.setString("clientName", clientName);
            query.setString("passportNumber", passportNumber);
            this.checkFound = query.list();
            if (!this.checkFound.isEmpty()) {
                this.found.addAll(this.checkFound);
                return true;
            }
            return false;
        });
    }

    public boolean findTwoParameters(String clientSurname, String clientName, String passportNumber) {
        return transaction(session -> {
            boolean check = false;
            final Query query = session.createQuery(HQL_SELECT_ALL);
            this.checkFound = query.list();
            for (Client client : this.checkFound) {
                if (client.getSurname().equals(clientSurname) && client.getName().equals(clientName)) {
                    this.found.add(client);
                    check = true;
                } else
                if (client.getSurname().equals(clientSurname) && client.getPassport().getNumber().equals(passportNumber)
                        && !passportNumber.equals("")) {
                    this.found.add(client);
                    check = true;
                } else
                if (client.getName().equals(clientName) && client.getPassport().getNumber().equals(passportNumber)
                        && !passportNumber.equals("")) {
                    this.found.add(client);
                    check = true;
                }
            }
            return check;
        });
    }

    public void findOneParameters(String clientSurname, String clientName, String passportNumber) {
        transaction(session -> {
            final Query query = session.createQuery(HQL_SELECT_ALL);
            this.checkFound = query.list();
            for (Client client : this.checkFound) {
                if (client.getSurname().equals(clientSurname)) this.found.add(client);
                else if (client.getName().equals(clientName)) this.found.add(client);
                else if (client.getPassport().getNumber().equals(passportNumber) && !passportNumber.equals("")) this.found.add(client);
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
