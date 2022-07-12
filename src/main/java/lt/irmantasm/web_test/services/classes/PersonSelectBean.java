package lt.irmantasm.web_test.services.classes;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lt.irmantasm.web_test.anotation.LoremProducer;
import lt.irmantasm.web_test.model.Person;
import lt.irmantasm.web_test.services.PersonService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@SessionScoped
@Setter
@Getter
@Named
public class PersonSelectBean implements Serializable {

    @PersistenceContext
    EntityManager em;

    @Inject
    private PersonEdit personEdit;

    @Inject
    @LoremProducer
    String getLorem10;

    @Inject
    private PersonService personService;
    @Inject
    private PagingBean pagingBean;
    private List<Person> people = new ArrayList<>();
    private List<String> peopleNames = new ArrayList<>();
    private Person selectedPerson;
    private String selectedName;

    private Integer personId;

    @SneakyThrows
    public void doOnInit() {
        intitPaging();
        selectedPerson = people.isEmpty() ? null : people.get(0);
        peopleNames = people.stream().map(Person::getName).collect(Collectors.toList());
        selectedName = peopleNames.isEmpty() ? "" : peopleNames.get(0);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/web_test-1.0-SNAPSHOT/jsf/cards.xhtml");
    }

    public void intitPaging() {
        Integer size = em.createQuery("select count(p.id) from Person p", Long.class).getSingleResult().intValue();
        if (pagingBean.getSize() == null || pagingBean.getSize() != size) {
            pagingBean.initPage(size);
        }
        TypedQuery<Person> namedQuery = em.createNamedQuery(Person.FIND_PAGE, Person.class);
        namedQuery.setFirstResult(pagingBean.getPageSize() * (pagingBean.getCurrentPage() - 1));
        namedQuery.setMaxResults(pagingBean.getPageSize());
        people = namedQuery.getResultList();
    }

    @Transactional
    public void deleteCard (ActionEvent event){
        Long data = (Long) event.getComponent().getAttributes().get("p-id");
        if (data != null ) {
            Query query1 = em.createQuery("delete from Person p where id = ?1");
            query1.setParameter(1,data);
            query1.executeUpdate();
        }
        intitPaging();
    }
    public void personChanged() {
        System.out.println(selectedPerson);
    }

    public void throwError() {
        FacesContext currentInstance = FacesContext.getCurrentInstance();
        currentInstance.addMessage("ent-msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Summury", getLorem10));

    }

    public List<String> completeName(String query) {
        System.out.println(query);
        return peopleNames.stream().filter(n -> n.toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT))).collect(Collectors.toList());
    }
}
