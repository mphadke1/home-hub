import java.io.IOException;
import java.io.*;

public class HeatMapData implements Serializable
{
    private String name;
    private String street;
    private String city;
    private String state;
    private int zipcode;
    private int weight;

    public HeatMapData(
        String name,
        String street,
        String city,
        String state,
        int zipcode,
        int weight
    ) {
        this.name = name;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.weight = weight;
    }
    
    public HeatMapData (Store store, int weight) {
        this.name = store.getName();
        this.street = store.getStreet();
        this.city = store.getCity();
        this.state = store.getState();
        this.zipcode = store.getZipcode();
        this.weight = weight;
    }
}