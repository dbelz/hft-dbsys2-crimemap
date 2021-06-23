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

## How to build and run

### Stage 1: Locally, without Docker, MySQL locally installed

#### Setup database

 - Create a database "crimemap" on your server (and optionally a dedicated user with read and write access to this database)
 - Login to the database and either
    - import the database init script `resources/init.sql` or
    - create the tables with the script `resources/create_database_sql` and then import the data from `resource/district.sql`, `resource/offense.sql` and `resource/crimes_2021.sql`.

Example:
```sh
cd /path/to/hft-dbsys2-crimemap
mysql -u hft -p crimemap < resources/init.sql
```

#### Build and run

```sh
cd /path/to/hft-dbsys2-crimemap
mvn spring-boot:run
```

#### Test

Open your browser and go to http://localhost:8080.

If you want to use the REST API, you can use the endpoint at http://localhost:8080/crimes.
Query parameter can be given as follows: http://localhost:8080/map.html?o=&d=&ds=&de=, where
 - o = offense id
 - d = district id
 - ds = start date
 - de = end date

On the command line, you can use the `http` command:

```sh
http localhost:8080/crimes
```

To add a new crime via REST API, use a POST command:

```sh
http -f POST :8080/crimes dateOfCrime="2021-03-02" district=14 lat=48.7839723 lon=9.1686235 offense=1 description="Einbruch in Architekturbüro"

http -f POST :8080/crimes dateOfCrime="2021-03-03" district=14 lat=48.782791 lon=9.192681 offense=2 description="Party in der Villa eskaliert"

http -f POST :8080/crimes dateOfCrime="2021-06-10" district=14 lat=48.781971 lon=9.181539 offense=3 description="Brezelkörble umgeworfen"

```

## Press portal with police reports (in German)

https://www.presseportal.de/blaulicht/nr/110977
