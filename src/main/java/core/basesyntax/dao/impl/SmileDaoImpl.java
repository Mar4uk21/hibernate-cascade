package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class SmileDaoImpl extends AbstractDao implements SmileDao {
    public SmileDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Smile create(Smile smile) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(smile);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create smile " + smile + " to DB", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return smile;
    }

    @Override
    public Smile get(Long id) {
        try (Session session = factory.openSession()) {
            Smile smile = session.get(Smile.class,id);
            return smile;
        } catch (Exception e) {
            throw new RuntimeException("Can't get smile by id" + id, e);
        }
    }

    @Override
    public List<Smile> getAll() {
        try (Session session = factory.openSession()) {
            Query<Smile> getAllSmileQuery = session.createQuery(
                    "from Smile", Smile.class);
            return getAllSmileQuery.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all smiles from DB",e);
        }
    }
}
