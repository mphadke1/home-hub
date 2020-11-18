import java.util.*;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Store")

public class Store extends HttpServlet{
	private int id;
	private String name;
    private String street;
    private String city;
    private String state;
    private int zipcode;

    public Store(
        int id,
        String name,
        String street,
        String city,
        String state,
        int zipcode
    ) {
        this.id = id; 
        this.name = name; 
        this.street = street; 
        this.city = city; 
        this.state = state; 
        this.zipcode = zipcode; 
    }

    public Store(
        String name,
        String street,
        String city,
        String state,
        int zipcode
    ) {
        this.name = name; 
        this.street = street; 
        this.city = city; 
        this.state = state; 
        this.zipcode = zipcode; 
    }

    int getId() {
        return this.id;
    }

    String getName() {
        return this.name;
    }

    String getCity() {
        return this.city;
    }

    String getStreet() {
        return this.street;
    }

    String getState() {
        return this.state;
    }

    int getZipcode() {
        return this.zipcode;
    }

}