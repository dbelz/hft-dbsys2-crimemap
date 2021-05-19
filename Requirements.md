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
    * _region to be defined_
    * _zoom level to be defined_
  * The user can use the mouse wheel or some dedicated buttons on the map to zoom in and out
  * The map can be dragged using the left mouse button (or two fingers on mobile devices)
  * Each crime in the database is represented by a needle (_or other icon?_) on the map
    * Depending on the crime, the needle has a certain icon
    * the needle is located on the lat/lon values of the according crime
    * when the user clicks on the needle (touch on mobile devices) a small info box is opened which shows some additional (see attributes of crime above)
    * When the user clicks on the url to the police press report, a new tab in the browser is opened
    * clicking/touching anywhere on the map will close the info box
  * The user has some filter options to influence the crime data that is displayed on the map
    * date and time range
    * zip code resp. district
    * offense
    * Example: show all burglaries in Stuttgart West from 2020-10-01, 00:00 to 2021-04-30, 23:59
  * Changing any filter has an immediate effect on the data displayed on the map (no additional click on an OK button is needed)
  * The map always tries to zoom and move the map so that all currently selected crime data is shown on the screen


## Technical

 * The system is build as a microservices architecture
 * The backend has the following REST end points:
   | end point | method | description |
   |-----------|--------|-------------|
   | /         | GET    | Return all crime scenes in the database |
   | /admin    | GET    | Return all 


 * Query params:
    - __zip:__ Only return crimes in that given district
    - __from:__ Only return crimes committed after (and including) the given time (date and time formatted in epoch time)
    - __to:__ Only return crimes committed before (and including) the given time (date and time formatted in epoch time)
    - __offense:__ Only return crimes with the given offense
