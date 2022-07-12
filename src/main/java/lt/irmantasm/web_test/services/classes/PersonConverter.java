package lt.irmantasm.web_test.services.classes;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lt.irmantasm.web_test.model.Person;
import lt.irmantasm.web_test.services.PersonService;

@Named
public class PersonConverter implements Converter<Person> {
    @Inject
    private PersonService personService;

    @Override
    public Person getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }

        try {
            return personService.findById(Long.valueOf(s));
        } catch (NumberFormatException e) {
            throw new ConverterException(new FacesMessage(s + " is not a valid Warehouse ID"), e);
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Person o) {
        if (o == null) {
            return ""; // Never return null here!
        }

        if (o instanceof Person) {
            return String.valueOf(((Person) o).getId());
        } else {
            throw new ConverterException(new FacesMessage(o + " is not a valid Person"));
        }
    }

}
