package lt.irmantasm.web_test.services;

import jakarta.ejb.Stateful;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lt.irmantasm.web_test.model.Person;

import java.io.Serializable;
import java.util.List;

@Stateful
@Transactional(Transactional.TxType.REQUIRES_NEW)
public class PersonService implements Serializable {

    @PersistenceContext
    private EntityManager em;


    public Person findById(Long id) {
        return em.find(Person.class, id);
    }

    public Person save(Person person) {
        if (em.contains(person)) {
            em.persist(person);
        } else {
            em.merge(person);
        }
        return person;
    }

    public List<Person> fidAll() {
        return em.createQuery("select p  FROM Person p ", Person.class)
                .getResultList();
    }
}
