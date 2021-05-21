# Database Systems II - Pre-Exam _Crime Map_

Pre-exam project for the _Database Systems II_ lecture at the HFT Stuttgart (summer semester 2021) by Prof. Dorothee Koch.

## Team members

1. Jonathan Vijayakumar (02vijo1mst@hft-stuttgart.de)
2. Daniel Belz (92beda1mst@hft-stuttgart.de)

## Project description

Visualization of crimes from the police reports in an area using an interactive map (OSM, ArcGIS, ...) with an admin dashboard to import and manage committed crimes.

## System architecture overview

![SysArch](https://user-images.githubusercontent.com/56379426/115469804-944d6a80-a235-11eb-8e32-25ed8812ec1c.png)

On the frontend side we use Leaflet with map parts from OSM or ArgGIS. The backend will be implemented as Spring Boot application with a RESTful API serving JSON. Crime data is stored in a PostgreSQL or MySQL/MariaDN database system. A clean docker based system architecture is planned.

## Inspiration

This idea was implemented several times already for different cities (mainly in the U.S.).
A German example can be found on the web page of Stuttgarter Zeitung: [Stuttgarter Zeitung CrimeMap](https://www.stuttgarter-zeitung.de/crimemap).

## Ressources

https://docs.docker.com/get-started/08_using_compose/
https://docs.docker.com/compose/install/
https://docs.docker.com/compose/completion/

https://docs.docker.com/get-started/08_using_compose/

Use Google JIB?
