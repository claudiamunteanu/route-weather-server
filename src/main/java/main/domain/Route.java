package main.domain;

import javax.persistence.*;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "routes")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "starting_city_id", nullable = false)
    private City startingCity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "destination_city_id", nullable = false)
    private City destinationCity;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "routes_cities",
            joinColumns = @JoinColumn(name = "route_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "city_id", referencedColumnName = "id"))
    @MapKeyColumn(name = "seconds_passed")
    @OrderBy
    private Map<Long, City> cities;

    @Column(name="description")
    private String description;

    public Route() {
    }

    public Route(Long id, City startingCity, City destinationCity, Map<Long, City> cities, String description) {
        this.id = id;
        this.startingCity = startingCity;
        this.destinationCity = destinationCity;
        this.cities = cities;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public City getStartingCity() {
        return startingCity;
    }

    public void setStartingCity(City startingCity) {
        this.startingCity = startingCity;
    }

    public City getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(City destinationCity) {
        this.destinationCity = destinationCity;
    }

    public Map<Long, City> getCities() {
        return cities;
    }

    public void setCities(Map<Long, City> cities) {
        this.cities = cities;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Route)) return false;
        Route route = (Route) o;
        return id.equals(route.id) && startingCity.equals(route.startingCity) && destinationCity.equals(route.destinationCity) && cities.equals(route.cities) && description.equals(route.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startingCity, destinationCity, cities, description);
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", startingCity=" + startingCity +
                ", destinationCity=" + destinationCity +
                ", cities=" + cities +
                ", description='" + description + '\'' +
                '}';
    }
}
