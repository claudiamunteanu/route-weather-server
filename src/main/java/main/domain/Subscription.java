package main.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "subscriptions",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"email", "starting_city_id", "destination_city_id"}),
                @UniqueConstraint(columnNames = {"token"})})
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "email")
    private String email;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "starting_city_id", nullable = false)
    private City startingCity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "destination_city_id", nullable = false)
    private City destinationCity;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "subscriptions_routes",
            joinColumns = @JoinColumn(name = "subscription_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "route_id", referencedColumnName = "id"))
    private List<Route> routes;

    @Column(name = "token")
    private String token;

    @Column(name = "last_notification")
    private LocalDate lastNotification;

    @Column(name = "notification_time")
    private LocalTime notificationTime;

    @Column(name = "notification_frequency_days")
    @Convert(converter = NotificationFrequencyConverter.class)
    private NotificationFrequency notificationFrequency;

    public Subscription() {

    }

    public Subscription(Long id, String email, City startingCity, City destinationCity, List<Route> routes, String token, LocalDate lastNotification, LocalTime notificationTime, NotificationFrequency notificationFrequency) {
        this.id = id;
        this.email = email;
        this.startingCity = startingCity;
        this.destinationCity = destinationCity;
        this.routes = routes;
        this.token = token;
        this.lastNotification = lastNotification;
        this.notificationTime = notificationTime;
        this.notificationFrequency = notificationFrequency;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDate getLastNotification() {
        return lastNotification;
    }

    public void setLastNotification(LocalDate lastNotification) {
        this.lastNotification = lastNotification;
    }

    public LocalTime getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(LocalTime notificationTime) {
        this.notificationTime = notificationTime;
    }

    public NotificationFrequency getNotificationFrequency() {
        return notificationFrequency;
    }

    public void setNotificationFrequency(NotificationFrequency notificationFrequency) {
        this.notificationFrequency = notificationFrequency;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", startingCity=" + startingCity +
                ", destinationCity=" + destinationCity +
                ", routes=" + routes +
                ", token='" + token + '\'' +
                ", lastNotification=" + lastNotification +
                ", notificationTime=" + notificationTime +
                ", notificationFrequency=" + notificationFrequency +
                '}';
    }
}