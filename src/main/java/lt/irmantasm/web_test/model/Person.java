package lt.irmantasm.web_test.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static lt.irmantasm.web_test.model.Person.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "person", schema = "test")
@NamedQuery(name = COUNT_ALL, query = "select count(p.id) from Person p")
@NamedQuery(name = FIND_PAGE,query = "select p from Person p order by id desc")
public class Person {
    public static final String FIND_PAGE = "Person.findPage";
    public static final String COUNT_ALL = "Person.count";
    public static final String DELETE_BY_ID = "Person.delete";
    @Id
    @SequenceGenerator(name="person_id_seq", schema = "test", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "person_id_seq")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private Long age;

    public Person(String name, Long age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        return id != null ? id.equals(person.id) : person.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
