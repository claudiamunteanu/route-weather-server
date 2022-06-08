package main.persistence;

import main.domain.City;
import main.domain.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Subscription findByEmailAndStartingCityAndDestinationCity(String email, City startingCity, City destinationCity);

    int deleteSubscriptionById(Long id);
}
