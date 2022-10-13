<!-- Improved compatibility of back to top link: See: https://github.com/othneildrew/Best-README-Template/pull/73 -->
<a name="readme-top"></a>
<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Don't forget to give the project a star!
*** Thanks again! Now go create something AMAZING! :D
-->



<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->

<!--[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url] 
[![Issues][issues-shield]][issues-url] 
[![License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]
-->

<!--
 PROJECT LOGO

 
<br />
<div align="center">
  -->

<a href="https://github.com/claudiamunteanu/route-weather-server">
    <img src="logo.png" alt="Logo" height="70"/>
</a>
                                             
# Route Weather - Server
This is the server for the web application made for my bachelor thesis.
  <!--
  <p align="center">
    Simple CRUD mobile application for the management of a store's products, created for the "Mobile Application Programming" course at my university.
    <br />
    <a href="https://github.com/claudiamunteanu/product-management"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/claudiamunteanu/product-management">View Demo</a>
    ·
    <a href="https://github.com/claudiamunteanu/product-management/issues">Report Bug</a>
    ·
    <a href="https://github.com/claudiamunteanu/product-management/issues">Request Feature</a>
  </p>
</div>
-->


<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#setup-and-build">Setup and Build</a></li>
        <li><a href="#running">Running</a></li>
      </ul>
    </li>
    <!--
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    -->
    <li><a href="#contact">Contact</a></li>
   <li><a href="#acknowledgments">Acknowledgments</a></li>
    <!--<li><a href="#acknowledgments">Acknowledgments</a></li>-->
  </ol>
</details>

<!-- ABOUT THE PROJECT -->
## About The Project

This project was made to give a hand to those who will go on holidays in order to help them prepare accordingly. The users can view the weather prediction for their route, in multiple locations along the route, at the time when it is predicted that they will be in that place. The informations that they can consult contain details such as the temperature or the precipitations and they can be viewed in a table or on a map. The only routes available at the moment are those in Romania.

The user can add his own driving tips to get ready for his journey, which involves creating an account. Those driving tips will be shown along side the weather informations for the route. 

If the user frequents some routes, he can subscribe to them and receive notifications on his email address regarding the weather on those routes, in an interval choosen by the user. Those same routes can be exported to a PDF file, so that the user can access them quicker and easier throughout the journey.

Moreover, the user can consult statistics for a city in Romania for the last seven days with the informations, which the user chooses whether to see them or not, presented on graphs and tables.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Built With
* [![Postgres][Postgres.org]][Postgres-url]
* [![IntelliJ IDEA][Intellij.com]][Intellij-url]
* [![Java][Java.com]][Java-url]
* [![Spring][Spring.io]][Spring-url]
                                       
<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- GETTING STARTED -->
## Getting Started

To get a local copy up and running follow these simple steps.

### Prerequisites

* [IntelliJ][IntelliJ-url]
* [Postgres][Postgres-url]
* [pgAdmin](https://www.pgadmin.org/) to manage the database
* An email address for the server to send subscription emails (preferably GMail)
* An API key from [OpenWeather](https://openweathermap.org/) from a plan which supports hourly forecast

### Setup and build
1. Using pgAdmin, create a new Postgre server if you do not have one, then create a new database.
3. Clone the repo
   ```sh
   git clone https://github.com/claudiamunteanu/route-weather-server.git
   ```  
3. Inside `src/main/resources/app.properties` replace `YOUR_API_KEY` with your API key, `server_email@domain.com` with your server email and `server_password` with the email account's password. If you wish to use an email address that is not from GMail, change the `MAIL_HOST` and `MAIL_PORT` values according to your domain. If the client runs on another URL, change the `WEBSITE_URL` value.
4. Inside `src/main/resources/application.properties` replace the values of `spring.datasource.username` and  `spring.datasource.password` with the credentials for your postgres server. Also, replace the value of `spring.datasource.url` with your database's url.
   
### Running

To run the desktop version:
1. Deploy and run the project using the `StartRestServices` class.
2. Check that the tables are created in the database, and that the table `cities` is populated.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->
<!--## Usage

Use this space to show useful examples of how a project can be used. Additional screenshots, code examples and demos work well in this space. You may also link to more resources.

_For more examples, please refer to the [Documentation](https://example.com)_

<p align="right">(<a href="#readme-top">back to top</a>)</p>
-->



<!-- CONTRIBUTING -->
<!--
## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p align="right">(<a href="#readme-top">back to top</a>)</p>
-->


<!-- CONTACT -->
## Contact

Munteanu Claudia-Maria - Linkedin: [claudiamunteanu][linkedin-url]

Project Link: [https://github.com/claudiamunteanu/route-weather-server](https://github.com/claudiamunteanu/route-weather-server)

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- ACKNOWLEDGMENTS -->
## Acknowledgments

* [OpenWeather](https://openweathermap.org/)
* [![Gmail][Gmail.com]][Gmail-url]

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/claudiamunteanu/shows-ticket-store-java.svg?style=for-the-badge
[contributors-url]: https://github.com/claudiamunteanu/shows-ticket-store-javat/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/claudiamunteanu/shows-ticket-store-java.svg?style=for-the-badge
[forks-url]: https://github.com/claudiamunteanu/shows-ticket-store-java/network/members
[stars-shield]: https://img.shields.io/github/stars/claudiamunteanu/shows-ticket-store-java.svg?style=for-the-badge
[stars-url]: https://github.com/claudiamunteanu/shows-ticket-store-java/stargazers
[issues-shield]: https://img.shields.io/github/issues/claudiamunteanu/shows-ticket-store-java.svg?style=for-the-badge
[issues-url]: https://github.com/claudiamunteanu/shows-ticket-store-java/issues
[license-shield]: https://img.shields.io/github/license/claudiamunteanu/shows-ticket-store-java.svg?style=for-the-badge
[license-url]: https://github.com/claudiamunteanu/shows-ticket-store-java/blob/master/LICENSE
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/claudiamunteanu
[Postgres.org]: https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white
[Postgres-url]: https://www.postgresql.org/
[Spring.io]: https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white
[Spring-url]: https://spring.io/
[Intellij.com]: https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white
[Intellij-url]: https://www.jetbrains.com/idea/
[Java.com]: https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white
[Java-url]: https://www.java.com/en/
[Gmail.com]: https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white
[Gmail-url]: https://www.google.com/gmail/about/
