package main.service;

import main.domain.City;
import main.domain.Route;
import main.domain.Subscription;
import main.persistence.CityRepository;
import main.persistence.RouteRepository;
import main.persistence.SubscriptionRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

@Service
public class SubscriptionService {
    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    RouteRepository routeRepository;

    @Autowired
    ForecastService forecastService;

    Properties prop;

    @Autowired
    public SubscriptionService() throws IOException {
        prop = new Properties();
        String propFileName = "app.properties";

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

        if (inputStream != null) {
            prop.load(inputStream);
        } else {
            throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
        }
    }

    public Subscription addSubscription(Subscription newSubscription, String language) throws ServiceException {
        Subscription subscription = subscriptionRepository.findByEmailAndStartingCityAndDestinationCity(newSubscription.getEmail(), newSubscription.getStartingCity(), newSubscription.getDestinationCity());
        if(subscription!=null){
            switch (language) {
                case "english" -> throw new ServiceException("You are already subscribed to this route!");
                case "romanian" -> throw new ServiceException("Ești deja abonat la această rută!");
            }
        }
        List<Route> routes = routeRepository.findAllByStartingCityAndDestinationCity(newSubscription.getStartingCity(), newSubscription.getDestinationCity());
        if (routes.isEmpty()) {
            routeRepository.saveAll(newSubscription.getRoutes());
        } else {
            newSubscription.setRoutes(routes);
        }
        newSubscription.setToken(RandomStringUtils.randomAscii(30));
        newSubscription.setLastNotification(LocalDate.now());
        return subscriptionRepository.save(newSubscription);
    }

    @Transactional
    public int deleteSubscription(String email, String startingCityName, String destinationCityName, String hash) {
        City startingCity = cityRepository.findByName(startingCityName);
        City destinationCity = cityRepository.findByName(destinationCityName);
        Subscription subscription = subscriptionRepository.findByEmailAndStartingCityAndDestinationCity(email, startingCity, destinationCity);

        int deletedSubscription = 0;
        if (subscription != null) {
            int newHash = generateUnsubscribeHash(subscription);
            if (hash.equals(String.valueOf(newHash))) {
                deletedSubscription = subscriptionRepository.deleteSubscriptionById(subscription.getId());
                System.out.println(deletedSubscription);
            }
        }
        return deletedSubscription;
    }

    private int generateUnsubscribeHash(Subscription subscription) {
        String string = subscription.getEmail() + subscription.getStartingCity().getName() + subscription.getDestinationCity().getName() + subscription.getToken();
        return string.hashCode();
    }

    private void setupMailServer(Properties properties) {
        properties.put("mail.smtp.host", prop.getProperty("MAIL_HOST"));
        properties.put("mail.smtp.port", prop.getProperty("MAIL_PORT"));
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
    }

    private String generateUnsubscribeURL(Subscription subscription) {
        int hash = generateUnsubscribeHash(subscription);
        String unsubscribeURL = String.format("%s/unsubscribe?email=%s&startingCity=%s&destinationCity=%s&hash=%s",
                prop.getProperty("WEBSITE_URL"),
                URLEncoder.encode(subscription.getEmail(), StandardCharsets.UTF_8),
                URLEncoder.encode(subscription.getStartingCity().getName(), StandardCharsets.UTF_8),
                URLEncoder.encode(subscription.getDestinationCity().getName(), StandardCharsets.UTF_8),
                URLEncoder.encode(String.valueOf(hash), StandardCharsets.UTF_8));
        System.out.println("UNSUBSCRIBEEEE " + unsubscribeURL);
        return String.format("<a style=\"font-size: 12px; color: white\" href=\"%s\">Unsubscribe</a>", unsubscribeURL);
    }

    private String generateTableRowContent(City city, int index, boolean isLast, LocalDateTime dateTime){
        String imgTemplate="<img style=\"box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);background-color: #87CEEB; border-radius: 10%;\" src=\"url\" alt=\"WeatherIcon\" height=\"30\" width=\"30\">";
        String tdTemplate="<td style=\"padding: 12px 15px;font-size: 13px;font-weight: bold;\">{CONTINUT}</td>";
        String oddTrTemplate = "<tr style=\"border-bottom: 1px solid #dddddd;background-color: #fff;\">{CONTINUT}</tr>";
        String evenTrTemplate = "<tr style=\"background-color: #f3f3f3;\">{CONTINUT}</tr>";

        JSONObject forecast = forecastService.getForecast(city, dateTime);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String time = dateTime.format(formatter);
        String temperature = forecast.getJSONObject("main").getDouble("temp") + "°C";

        JSONObject weatherData = forecast.getJSONArray("weather").getJSONObject(0);
        String iconId = weatherData.getString("icon");
        String icon = imgTemplate.replace("url", prop.getProperty("WEATHER_ICON_URL").replace("{id}", iconId));

        String weather = weatherData.getString("description");
        String clouds = forecast.getJSONObject("clouds").getInt("all")+"%";
        String wind = forecast.getJSONObject("wind").getDouble("speed")+ " km/h";
        String precip = Math.round(forecast.getDouble("pop") * 100) + "%";

        String nameCell = tdTemplate.replace("{CONTINUT}", city.getName());
        String timeCell = tdTemplate.replace("{CONTINUT}", time);
        String tempCell = tdTemplate.replace("{CONTINUT}", temperature);
        String iconCell = tdTemplate.replace("{CONTINUT}", icon);
        String weatherCell = tdTemplate.replace("{CONTINUT}", weather);
        String cloudsCell = tdTemplate.replace("{CONTINUT}", clouds);
        String windCell = tdTemplate.replace("{CONTINUT}", wind);
        String precipCell = tdTemplate.replace("{CONTINUT}", precip);

        String row = "";
        String rowContent = String.format("%s%s%s%s%s%s%s%s", nameCell, timeCell, tempCell, iconCell, weatherCell, cloudsCell, windCell, precipCell);
        if(index % 2 == 0){
            row = evenTrTemplate.replace("{CONTINUT}", rowContent);
        } else {
            row = oddTrTemplate.replace("{CONTINUT}", rowContent);
        }
        if(isLast){
            row = row.replace("style=\"", "style=\"border-bottom: 5px solid #F3CA40;");
        }
        return row;
    }

    private String generateTableContent(Route route, LocalTime startTime) throws IOException {
        String tableContent = readFile(prop.getProperty("TABLE_DATA_CONTENT_PATH"));
        StringBuilder tableRows = new StringBuilder();

        Map<Long, City> cities = route.getCities();
        List<Long> keys = new ArrayList<>(cities.keySet()).stream().sorted().collect(Collectors.toList());
        LocalDateTime startDateTime = LocalDateTime.now().with(startTime);
        if(startDateTime.toEpochSecond(ZoneOffset.UTC) < LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)){
            startDateTime = startDateTime.plusDays(1);
        }
        for(int i=1; i<=cities.size(); i++){
            City city = cities.get(keys.get(i-1));
            LocalDateTime dateTime = startDateTime.plusSeconds(keys.get(i-1));
            tableRows.append(generateTableRowContent(city, i, i==cities.size(), dateTime));
        }
        tableContent = tableContent.replace("{CONTENT}", tableRows);
        return tableContent;
    }

    private String generateRouteContent(Route route, boolean firstRoute, LocalTime time) throws IOException {
        String routeContent = readFile(prop.getProperty("ROUTE_DATA_CONTENT_PATH"));
        StringBuilder routeData = new StringBuilder();
        String startingCityName = route.getStartingCity().getName();
        String destinationCity = route.getStartingCity().getName();
        if (firstRoute) {
            routeData.append(String.format("Starting city: %s <br>Destination city: %s <br>", startingCityName, destinationCity));
        }
        routeData.append(String.format("Description: %s", route.getDescription()));
        routeContent = routeContent.replace("{CONTENT}", routeData);
        routeContent = routeContent + generateTableContent(route, time);
        return routeContent;
    }

    private String generateEmailContent(Subscription subscription) throws IOException {
        String content = readFile(prop.getProperty("EMAIL_CONTENT_STARTER_PATH"));

        List<Route> routes = subscription.getRoutes();
        StringBuilder routesContent = new StringBuilder(generateRouteContent(routes.get(0), true, subscription.getNotificationTime()));
        for (int i = 1; i < routes.size(); i++) {
            String routeContent = generateRouteContent(routes.get(i), false, subscription.getNotificationTime());
            routesContent.append(routeContent);
        }
        routesContent.append(generateUnsubscribeURL(subscription));
        content = content.replace("{CONTENT}", routesContent.toString());
        return content;
    }

    private MimeMessage setupMessage(Session session, String from, String to, Subscription subscription) throws MessagingException, IOException {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject("This is the Subject Line!");
        message.setContent(generateEmailContent(subscription), "text/html; charset=utf-8");
        return message;
    }

    private String readFile(String path) throws IOException {
        return Files.readString(Paths.get(path));
    }

    private void sendEmail(Subscription subscription) {
        String to = subscription.getEmail();
        String from = prop.getProperty("SERVER_EMAIL");
        Properties properties = System.getProperties();

        setupMailServer(properties);

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(prop.getProperty("SERVER_EMAIL"), prop.getProperty("SERVER_PASSWORD"));
            }
        });
        session.setDebug(true);
        try {
            MimeMessage message = setupMessage(session, from, to, subscription);
            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException | IOException mex) {
            mex.printStackTrace();
        }
    }

    public void sendEmails() {
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        LocalDate now = LocalDate.now();
        subscriptions.forEach(subscription -> {
            if(Duration.between(subscription.getLastNotification(), now).toDays()==subscription.getNotificationFrequency().getValue()){
                sendEmail(subscription);
                subscription.setLastNotification(now);
            }
        });
    }
}
