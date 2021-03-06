package hibernate;


import java.util.HashMap;

/* Source code:
 * https://o7planning.org/en/10201/java-hibernate-tutorial-for-beginners#a77482
 */
import java.util.HashMap;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.BootstrapServiceRegistry;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import entities.film;

 
public class HibernateUtils {
    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory = buildSessionFactory();
 
    // Hibernate 5:
    private static SessionFactory buildSessionFactory() {
        try {
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()//
                    .configure("hibernate.cfg.xml").build();
 
            Metadata metadata = new MetadataSources(serviceRegistry).getMetadataBuilder().build();
 
            return metadata.getSessionFactoryBuilder().build();
        } catch (Throwable ex) {
         
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
 
    public static SessionFactory getSessionFactory() {
    	if (sessionFactory == null) {
            try {
               BootstrapServiceRegistry bootstrapRegistry =
                     new BootstrapServiceRegistryBuilder()
                     .applyIntegrator(new HibernateEventListenerIntegrator())
                     .build();
               
               StandardServiceRegistryBuilder registryBuilder = 
                     new StandardServiceRegistryBuilder(bootstrapRegistry);

               Map<String, Object> settings = new HashMap<>();
               settings.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
               settings.put(Environment.URL, "jdbc:mysql://localhost:3306/film");
               settings.put(Environment.USER, "root");
               settings.put(Environment.PASS, "fkmci8Bj@");
               
               registryBuilder.applySettings(settings);

               registry = registryBuilder.build();

               MetadataSources sources = new MetadataSources(registry)
                     .addAnnotatedClass(film.class);

               Metadata metadata = sources.getMetadataBuilder().build();

               sessionFactory = metadata.getSessionFactoryBuilder().build();
            } catch (Exception e) {
               if (registry != null) {
                  StandardServiceRegistryBuilder.destroy(registry);
               }
               e.printStackTrace();
            }
         }
        return sessionFactory;
    }
 
    public static void shutdown() {
        getSessionFactory().close();
    }
 
}