package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String query = "CREATE TABLE IF NOT EXISTS users (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "table_name VARCHAR(50) NOT NULL, " +
                "table_lastName VARCHAR(50) NOT NULL, " +
                "table_age TINYINT NOT NULL)";
        Transaction tx = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.createSQLQuery(query).executeUpdate();
            tx.commit();
            System.out.println("Table created");
        } catch (Exception e) {
            if (tx != null){
                tx.rollback();
            }
            System.out.println("Table Creation Failed");
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction tx = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            tx.commit();
            System.out.println("Table Dropped");
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("Table Drop Failed");
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction tx = null;
        User user = new User();
        user.setName(name);
        user.setLastName(lastName);
        user.setAge(age);
        try (Session session = Util.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(user);
            tx.commit();
            System.out.println("User: " + user.getName() + " saved to db");
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("User save Failed");
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction tx = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            //User user = (User) session.get(User.class, id);
            session.delete(session.get(User.class, id));
            tx.commit();
            System.out.println("User removed");
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                System.out.println("User remove Failed");
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction tx = null;
        List<User> usersList = new ArrayList<>();
        try(Session session = Util.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            usersList = session.createNativeQuery("SELECT * FROM users", User.class).getResultList();
            tx.commit();
            return usersList;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("Users List Failed");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void cleanUsersTable() {
        Transaction tx = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.createSQLQuery("DELETE FROM users").executeUpdate();
            tx.commit();
            System.out.println("Table cleaned");
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("Table Clean Failed");
        }
    }
}
