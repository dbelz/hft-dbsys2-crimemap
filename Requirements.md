# Requirements

## Functional

 * There are several views:
   * map view
     * map
     * filter options
   * admin dashboard
     * admin user management
     * crime data management
 * The map UI is public and anyone can access it
 * The admin dashboard is password protected and only admin users have access
 * Several admin accounts can be created and managed through a separate user interface
 * An admin user has the following attributes:
   * real name (e.g. Daniel Belz)
   * user name (dbelz)
   * email address (dani.belz@gmail.com)
   * password (stored encrypted!)
 * It has to be made sure, that at all times at least one admin exists
 * An admin can create other admins
 * An admin can enter crime data
 * A crime has the following attributes
   * date and time
   * zip
   * city
   * address (street, house number)
   * offense
   * latitude
   * longitude
   * _optional (if available)_: description
   * _optional (if available)_: url to police press release
  * The admin can upload a csv file with several crimes
  * Incomplete crime data is rejected
  * Crime data can be edited and deleted by any admin
  * When a user opens the web application, the map view is shown
  * The map view shows a default view
    * __TODO:__ _region to be defined_
    * __TODO:__ _zoom level to be defined_
  * The user can use the mouse wheel or some dedicated buttons on the map to zoom in and out
  * The map can be dragged using the left mouse button (or two fingers on mobile devices)
  * Each crime in the database is represented by a needle marker icon (_or another icon?_) on the map
    * Depending on the crime, the marker can differ (icon on the marker?)
    * the marker is located on the lat/lon values of the according crime
    * when the user clicks on the marker (touch on mobile devices) a small info box is opened which shows some additional (see attributes of crime above)
    * When the user clicks on the url to the police press report, a new tab in the browser is opened
    * clicking/touching anywhere on the map will close the info box
  * The user has some filter options to influence the crime data that is displayed on the map
    * date and time range
    * zip code resp. district
    * offense
    * Example: show all burglaries in Stuttgart West from 2020-10-01, 00:00 to 2021-04-30, 23:59
  * Changing any filter has an immediate effect on the data displayed on the map (no additional click on an OK button is needed)
  * The map always tries to zoom and move the map so that all currently selected crime data is shown on the screen
  * Statistics for the current view are shown (number of crimes divided by district and period)


## Technical

 * The system is build as a micro services architecture
 * The front-end is implemented using HTML, CSS and JavaScript
   * Leaflet as is used as map library
 * Map material: Open Street Map, Mapbox and/or ArcGIS (__TODO:__ make a decision)
 * The backend service is implemented in Java using Spring Boot and the following dependencies:
   * __TODO:__ List dependencies
 * Data is stored in a MySQL (or PostgreSQL?) database (__TODO:__ make a decision)
 * The front-end, backend and database are delivered as Docker images

### RESTfull API

#### Provided REST endpoints
| end point | method | description |
|-----------|--------|-------------|
| /crimes    | GET    | Return all crime scenes in the database |
| /crimes    | POST   | Adds a new crime scene to the database |
| /crimes/{id} | { GET \| PUT \| DELETE }  | Returns, updates or deletes the crime scene with the given ID |
| /admins    | GET    | Return all admins in the database |
| /admins    | POST   | Adds a new admin to the database |
| /admins/{id} | { GET \| PUT \| DELETE }  | Returns, updates or deletes the admin with the given ID |

#### Query params
| param    | description |
|----------|-------------|
| zip      | Only return crimes in that given district |
| from     | Only return crimes committed after (and including) the given time (date and time formatted in epoch time) |
| to       | Only return crimes committed before (and including) the given time (date and time formatted in epoch time) |
| offense  | Only return crimes with the given offense |


#### JSON syntax for return values
Each call to the `/crime` endpoint returns a [GeoJSON](https://en.wikipedia.org/wiki/GeoJSON) format string.

#### Example query

```
http://<host>:<port>/v1/crime?zip=70437&from=1617235200&to=1619827200&offense=burglary
```

This will return all burglaries in april 2021 in the Stuttgart district _Sommerrain_.

## Notes

* We will not cover how we get to the data (scraping of police press releases or the like). The data are considered as given.
* https://cloud.google.com/apis/design


## Scratch

* zip(zip, district name, number of crimes in total)
* offense(id, name, number of crimes of this offense)
  * Both tables are not veeeery useful, but we can play around with these for the foreign key constraints, triggers, maybe stored functions, ...
  * Improvement idea: The number of crimes is given per year (how to model that? In an m:n relationship?)