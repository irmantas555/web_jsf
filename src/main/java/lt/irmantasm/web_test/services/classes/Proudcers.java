package lt.irmantasm.web_test.services.classes;

import jakarta.enterprise.inject.Produces;
import lt.irmantasm.web_test.anotation.LoremProducer;

public class Proudcers {

    @Produces
    @LoremProducer
    public String lorem10Prod(){
        return "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Amet animi cum earum esse in incidunt, laudantium minima neque non veritatis?";
    }
}
