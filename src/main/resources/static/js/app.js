var map;
var geoJsonLayer;
var timeline;
var items;

var redIcon = new L.Icon({
  iconUrl: 'https://cdn.rawgit.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-red.png',
  shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
  iconSize: [25, 41],
  iconAnchor: [12, 41],
  popupAnchor: [1, -34],
  shadowSize: [41, 41]
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
  timeline.setSelection(e.layer._leaflet_id, {
    focus: true
  });
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
        text: 'Show coordinates',
        callback: showCoordinates
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

      // var rawDesc = feature.properties.description;
      // if (rawDesc == null)
      //   rawDesc = "";

      // split = rawDesc.split(",");
      // var htmlDesc = "";
      // for (s in split) {
      //   sub_split = split[s].split(":", 2)
      //   htmlDesc = htmlDesc + "<b>" + sub_split[0] + ":</b>" + sub_split[1] + "<br>"
      // }
      layer.bindPopup(feature.properties.timestamp + "<br><br>" + htmlDesc, customOptions);
      layer._leaflet_id = feature.properties.id;

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

function reinitTimeline(timeline, items, data) {
  console.log("Reinit timeline")
  items.clear();
  var items2 = new vis.DataSet();

  var i = 0;
  for (i = 0; i < data.features.length; i++) {
    items2.add({
      id: data.features[i].properties.id,
      content: data.features[i].properties.timestamp.toString(),
      start: data.features[i].properties.timestamp
    });
  }
  console.log("after")
  timeline.setItems(items2);

  // Set default zoom level
  timeline.fit(true);
  timeline.redraw();

  return timeline, items2;
}


function initTimeline(data) {
  // Set timeline options
  var timelineOptions = {

    "width": "100%",
    "height": "120px",
    "style": "box",
    "axisOnTop": true,
    "showCustomTime": true,
    "editable": false,
    "verticalScroll": false,
    "stack": false,
    "limitSize": true,
    "selectable": true
  };

  // Create items, used by timeline
  var items = new vis.DataSet();
  var i = 0;
  for (i = 0; i < data.features.length; i++) {
    console.log("add" + i);
    items.add({
      id: data.features[i].properties.id,
      content: data.features[i].properties.timestamp.toString(),
      start: data.features[i].properties.timestamp
    });
  }

  // Setup timeline
  timeline = new vis.Timeline(document.getElementById('timeline'), items, timelineOptions);

  // Set default zoom level
  timeline.fit(true);

  timeline.on('select', function(properties) {
    logEvent('select', properties);
    console.log(properties.items[0])
    centerLeafletMapOnMarker(properties.items[0])
  });

  return timeline, items;
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
    timeline, items = initTimeline(data);
  });

