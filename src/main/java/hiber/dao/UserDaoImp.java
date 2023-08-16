package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public UserDaoImp(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public void add(User user) {
        getSessionFactory().getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = getSessionFactory().getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public User getUserByCarParams(String model, int series) {
        String hql = """
                from Car where model =:model and series =:series
                """;
        Query<Car> query = getSessionFactory().getCurrentSession().createQuery(hql, Car.class);
        query.setParameter("model", model).setParameter("series", series);
        List<Car> car = query.list();
        //        Car car = query.uniqueResult();
        if (car.isEmpty()) {
            return new User();
        }
        return car.get(0).getUser();
    }
}
