package main.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "driving_tips")
public class DrivingTip {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "text")
    private String text;

    @Column(name = "weather_type")
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = WeatherType.class, fetch = FetchType.EAGER)
    private Set<WeatherType> categories;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    public DrivingTip() {
    }

    public DrivingTip(Long id, String text, Set<WeatherType> categories, User user) {
        this.id = id;
        this.text = text;
        this.categories = categories;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Set<WeatherType> getCategories() {
        return categories;
    }

    public void setCategories(Set<WeatherType> categories) {
        this.categories = categories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "DrivingTip{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", categories=" + categories +
                ", user=" + user +
                '}';
    }
}
