var map;
var geoJsonLayer;
var items;

var accidentIcon = new L.Icon({
  iconUrl: 'http://localhost:8080/res/accident.svg',
  iconSize: [36, 36],
  iconAnchor: [18, 18],
  popupAnchor: [18, 18],
});

var burglaryIcon = new L.Icon({
  iconUrl: 'http://localhost:8080/res/burglary.svg',
  iconSize: [36, 36],
  iconAnchor: [18, 18],
  popupAnchor: [18, 18],
});


var damageIcon = new L.Icon({
  iconUrl: 'http://localhost:8080/res/damage.svg',
  iconSize: [36, 36],
  iconAnchor: [18, 18],
  popupAnchor: [18, 18],
});

var drugIcon = new L.Icon({
  iconUrl: 'http://localhost:8080/res/drug.svg',
  iconSize: [36, 36],
  iconAnchor: [18, 18],
  popupAnchor: [18, 18],
});

var drunkennessIcon = new L.Icon({
  iconUrl: 'http://localhost:8080/res/drunkenness.svg',
  iconSize: [36, 36],
  iconAnchor: [18, 18],
  popupAnchor: [18, 18],
});

var fireIcon = new L.Icon({
  iconUrl: 'http://localhost:8080/res/fire.svg',
  iconSize: [36, 36],
  iconAnchor: [18, 18],
  popupAnchor: [18, 18],
});

var propertyIcon = new L.Icon({
  iconUrl: 'http://localhost:8080/res/property.svg',
  iconSize: [36, 36],
  iconAnchor: [18, 18],
  popupAnchor: [18, 18],
});

var sexIcon = new L.Icon({
  iconUrl: 'http://localhost:8080/res/sex.svg',
  iconSize: [36, 36],
  iconAnchor: [18, 18],
  popupAnchor: [18, 18],
});

var violenceIcon = new L.Icon({
  iconUrl: 'http://localhost:8080/res/violence.svg',
  iconSize: [36, 36],
  iconAnchor: [18, 18],
  popupAnchor: [18, 18],
});


// Map callbacks
function showCoordinates(e) {
  alert(e.latlng);
}

// Specify popup options
var customOptions = {
  'className': 'custom-popup'
}

function markerOnClick(e) {
  var attributes = e.layer.properties;
}

// See https://leaflet-extras.github.io/leaflet-providers/preview/ for different providers
var streets = L.tileLayer('https://{s}.tile.openstreetmap.de/tiles/osmde/{z}/{x}/{y}.png', {
  attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
});

var esri_worldImagery = L.tileLayer('https://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}', {
  attribution: 'Tiles &copy; Esri &mdash; Source: Esri, i-cubed, USDA, USGS, AEX, GeoEye, Getmapping, Aerogrid, IGN, IGP, UPR-EGP, and the GIS User Community'
});

var baseLayers = {
  "Satellite": esri_worldImagery,
  "Streets": streets
};

function removeMap() {
  console.log("removeMap")
  if (map != undefined || map != null) {
    map.off();
    map.remove();
    map = null;

  }
}

function initMap(gdata) {
  console.log("Init map");
  removeMap();

  map = L.map('map', {
    zoom: 10,
    layers: [streets],
    contextmenu: true,
    contextmenuWidth: 140,
    contextmenuItems: [{
        text: 'Show coordinates', callback: showCoordinates
      }
    ]
  });

  // Add layer list to map
  L.control.layers(baseLayers).addTo(map);

  // Init information pane
  L.control.infoPane('infopane', {
    position: 'bottomright'
  }).addTo(map);

  // Set up marker cluster, which is linked to geoJson-layer
  var clusterLayer = L.markerClusterGroup();
  geoJsonLayer = L.geoJson(gdata, {
    onEachFeature: function(feature, layer) {
      
      htmlDesc = "<b>" + feature.properties.offense + "</b></br></br>" + feature.properties.description;

      layer.bindPopup(feature.properties.timestamp + "<br><br>" + htmlDesc, customOptions);
      layer._leaflet_id = feature.properties.id;

    },
    pointToLayer: function(feature, latlng) {

      switch (feature.properties.offense) {

        case "Accident":
          myIcon = accidentIcon;
          break;
        case "Burglary":
          myIcon = burglaryIcon;
          break;
        case "Property crime":
          myIcon = propertyIcon;
          break;
        case "Sex crime":
          myIcon = sexIcon;
          break;
        case "Drugs":
          myIcon = drugIcon;
          break;
        case "Fire":
          myIcon = fireIcon;
          break;
        case "Violent act":
          myIcon = violenceIcon;
          break;
        case "Drunkenness":
          myIcon = drunkennessIcon;
          break;
        case "Damage to property":
          myIcon = damageIcon;
          break;

      }

      return L.marker(latlng, { icon: myIcon });

    }
  });

  clusterLayer.addLayer(geoJsonLayer)
  map.addLayer(clusterLayer)
  
  // Define initial viewport
  map.fitBounds(clusterLayer.getBounds());
  // Set callback on click
  geoJsonLayer.on("click", markerOnClick)

  return map, geoJsonLayer;

}

// toString-Utility method
function stringifyObject(object) {
  if (!object) return;
  var replacer = function(key, value) {
    if (value && value.tagName) {
      return "DOM Element";
    } else {
      return value;
    }
  }
  return JSON.stringify(object, replacer)
}

// Utility method to log to web console
function logEvent(event, properties) {
  console.log(event, ": ", stringifyObject(properties))

}

fetch('http://localhost:8080/crimes')
  .then((response) => {
    return response.json();
  })
  .then((data) => {
    console.log(data);
    // Initialize leaflet map
    map, geoJsonLayer = initMap(data);
    // Initialize timeline
    // timeline, items = initTimeline(data);
  });

