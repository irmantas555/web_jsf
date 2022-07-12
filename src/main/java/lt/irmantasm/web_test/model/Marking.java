package lt.irmantasm.web_test.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Marking {
    private Integer number;
    private String mark;

    @Override
    public String toString() {
        return  mark;
    }
}
