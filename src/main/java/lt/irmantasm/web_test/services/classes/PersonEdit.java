package lt.irmantasm.web_test.services.classes;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.inject.spi.BeanManager;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import lt.irmantasm.web_test.model.Person;
import lt.irmantasm.web_test.services.PersonService;

import java.io.Serializable;

@SessionScoped
@Named
public class PersonEdit implements Serializable {

    @EJB
    PersonService personService;
    private Person person;

    @Inject
    BeanManager beanManager;

    @Inject
    PersonSelectBean personSelectBean;

    public void init(Long id){
        person = personService.findById(id);
    }

    @Transactional
    public void persist(){
        personService.save(person);
        personSelectBean.intitPaging();
        redirect("/web_test-1.0-SNAPSHOT/jsf/cards.xhtml");
    }

    public void attributeListener(ActionEvent event) {
        Long data = (Long) event.getComponent().getAttributes().get("p-id");
        init(data);
        redirect("/web_test-1.0-SNAPSHOT/jsf/personEdit.xhtml");
    }

    @SneakyThrows
    private void redirect(String route) {
        FacesContext.getCurrentInstance().getExternalContext().redirect(route);
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
