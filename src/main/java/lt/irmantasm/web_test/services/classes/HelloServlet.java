package lt.irmantasm.web_test.services.classes;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lt.irmantasm.web_test.model.Person;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "HelloServlet", value = "/HelloServlet")
public class HelloServlet extends HttpServlet {
    @PersistenceContext
    EntityManager em;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();
        response.setContentType("text/HTML");
        writer.println("<strong> Encoded text</strong><br/>");
        if (em != null) {
            Person person = em.find(Person.class, 1L);
            writer.write("<strong> Hi there annotation " + person.getName() + "</strong>");
        } else {
            writer.write("Entitiy manager is null");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
