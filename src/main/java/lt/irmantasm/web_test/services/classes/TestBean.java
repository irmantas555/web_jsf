package lt.irmantasm.web_test.services.classes;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lt.irmantasm.web_test.model.Person;
import lt.irmantasm.web_test.services.PersonService;

import java.io.Serializable;

@Named
@RequestScoped
@Getter
@Setter
public class TestBean implements Serializable {
    private String name;
    private Long age;
    private String helloToString;

    @Inject
    PersonService personService;

    public void fillString() {
        Person person =personService.save(new Person(name, age));
        helloToString = "Welcome " + this.toString();
        resetValues();
    }

    private void resetValues() {
        this.name = "";
        this.age = null;
    }

    @Override
    public String toString() {
        return " " + name + " " + age;
    }
}
