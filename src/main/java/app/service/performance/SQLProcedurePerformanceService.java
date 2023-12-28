package app.service.performance;

import app.configuration.HibernateConfiguration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.StoredProcedureQuery;
import javax.transaction.Transactional;

public class SQLProcedurePerformanceService implements PerformanceService {
    @Override
    @Transactional
    public void applyLogicOnUsers() {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        StoredProcedureQuery query = session.createStoredProcedureQuery("UpdateUserEmailAndDoubleSongDuration");
        query.execute();
    }
}
