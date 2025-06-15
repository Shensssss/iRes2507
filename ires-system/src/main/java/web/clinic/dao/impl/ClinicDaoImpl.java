package web.clinic.dao.impl;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import web.clinic.dao.ClinicDAO;
import web.clinic.entity.Clinic;

@Repository
public class ClinicDaoImpl implements ClinicDAO {
    private final SessionFactory sessionFactory;

    public ClinicDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public int insert(Clinic pojo) {
        getSession().save(pojo);
        return 1;
    }

    @Override
    public int deleteById(String id) {
        Clinic clinic = getSession().get(Clinic.class, id);
        if (clinic != null) {
            getSession().delete(clinic);
            return 1;
        }
        return 0;
    }

    @Override
    public int update(Clinic pojo) {
        getSession().update(pojo);
        return 1;
    }

    @Override
    public Clinic selectById(String id) {
        return getSession().get(Clinic.class, id);
    }

    @Override
    public List<Clinic> selectAll() {
        return getSession().createQuery("FROM Clinic", Clinic.class).list();
    }

    @Override
    public int updatePsd(Clinic clinic) {
        StringBuilder hql = new StringBuilder("UPDATE Clinic SET ");
        boolean hasPassword = clinic.getPassword() != null && !clinic.getPassword().isEmpty();

        if (hasPassword) {
            hql.append("password = :password, ");
        }

        hql.append("clinicName = :clinicName, ")
                .append("updateId = :updateId, ")
                .append("updateTime = CURRENT_TIMESTAMP ")
                .append("WHERE account = :account");

        Query<?> query = getSession().createQuery(hql.toString());

        if (hasPassword) {
            query.setParameter("password", clinic.getPassword());
        }

        query.setParameter("clinicName", clinic.getClinicName())
                .setParameter("updateId", clinic.getUpdateId())
                .setParameter("account", clinic.getAccount());

        return query.executeUpdate();
    }
}
