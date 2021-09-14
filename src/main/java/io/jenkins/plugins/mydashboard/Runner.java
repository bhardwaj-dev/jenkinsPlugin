package io.jenkins.plugins.mydashboard;

import net.sf.json.JSONObject;

import javax.persistence.*;
import java.util.List;

public class Runner {
    private   EntityManagerFactory ENTITY_MANAGER_FACTORY ;

    Runner (){
        this.ENTITY_MANAGER_FACTORY =  Persistence
                .createEntityManagerFactory("mydashboard");
    }

    public  void addCustomer(int id, String fname, String lname) {
        // The EntityManager class allows operations such as create, read, update, delete
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        // Used to issue transactions on the EntityManager
        EntityTransaction et = null;

        try {
            // Get transaction and start
            et = em.getTransaction();
            et.begin();

            // Create and set values for new customer
            Customer cust = new Customer();
            cust.setID(id);
            cust.setFName(fname);
            cust.setLName(lname);

            // Save the customer object
            em.persist(cust);
            et.commit();
        } catch (Exception ex) {
            // If there is an exception rollback changes
            if (et != null) {
                et.rollback();
            }
            ex.printStackTrace();
        } finally {
            // Close EntityManager
            em.close();
        }
    }

    public  void getCustomer(int id) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        // the lowercase c refers to the object
        // :custID is a parameterized query thats value is set below
        String query = "SELECT c FROM Customer c WHERE c.id = :custID";

        // Issue the query and get a matching Customer
        TypedQuery<Customer> tq = em.createQuery(query, Customer.class);
        tq.setParameter("custID", id);

        Customer cust = null;
        try {
            // Get matching customer object and output
            cust = tq.getSingleResult();
            System.out.println(cust.getFName() + " " + cust.getLName());
        }
        catch(NoResultException ex) {
            ex.printStackTrace();
        }
        finally {
            em.close();
        }
    }

    public  JSONObject getCustomers() {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        // the lowercase c refers to the object
        // :custID is a parameterized query thats value is set below
        String strQuery = "SELECT c FROM Customer c WHERE c.id IS NOT NULL";

        // Issue the query and get a matching Customer
        TypedQuery<Customer> tq = em.createQuery(strQuery, Customer.class);
        List<Customer> custs;
        JSONObject json  = new JSONObject();
        try {
            // Get matching customer object and output
            custs = tq.getResultList();
//            custs.forEach(cust->System.out.println(cust.getFName() + " " + cust.getLName()));
            custs.forEach(cust->json.put("data", cust.getFName()));
        }
        catch(NoResultException ex) {
            ex.printStackTrace();
        }
        finally {
            em.close();
        }
        return json;
    }

    public  void changeFName(int id, String fname) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;

        Customer cust = null;

        try {
            // Get transaction and start
            et = em.getTransaction();
            et.begin();

            // Find customer and make changes
            cust = em.find(Customer.class, id);
            cust.setFName(fname);

            // Save the customer object
            em.persist(cust);
            et.commit();
        } catch (Exception ex) {
            // If there is an exception rollback changes
            if (et != null) {
                et.rollback();
            }
            ex.printStackTrace();
        } finally {
            // Close EntityManager
            em.close();
        }
    }

    public  void deleteCustomer(int id) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        Customer cust = null;

        try {
            et = em.getTransaction();
            et.begin();
            cust = em.find(Customer.class, id);
            em.remove(cust);
            et.commit();
        } catch (Exception ex) {
            // If there is an exception rollback changes
            if (et != null) {
                et.rollback();
            }
            ex.printStackTrace();
        } finally {
            // Close EntityManager
            em.close();
        }
    }
}

