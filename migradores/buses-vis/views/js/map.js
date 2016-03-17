
var ready = function() {

	// Function to draw points on map.
	function drawRoute(route, color){ 
		var coords = route.path;
		var stops = route.stops;
		// Draw path
		for(var i = 0; i<coords.length-1; i++){
			var c1 = coords[i];
			var c2 = coords[i+1];
			var pointA = new L.LatLng(c1.latitude, c1.longitude);
			var pointB = new L.LatLng(c2.latitude, c2.longitude);

			var pointList = [pointA, pointB];

			var firstpolyline = new L.Polyline(pointList, {
				color: color,
				weight: 3,
				opacity: 0.5,
				smoothFactor: 1
			});
			firstpolyline.addTo(map);
		}
		// Draw stops
		for(var i = 0; i < stops.length; i++){
			var stop = stops[i];
			L.marker([stop.location.latitude, stop.location.longitude]).addTo(map)
				.bindPopup(stop.name);
		}

	};	

	var map = L.map('map').setView([9.9187, -84.073457], 8);
	L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
	            attribution: '© Contribuidores de OpenStreetMap '
	        }).addTo(map);

	var route = localStorage.getItem('routeJson');
	var routeJson = JSON.parse(route);
	drawRoute(routeJson, 'red');

	var start = localStorage.getItem('start');
	var end = localStorage.getItem('end');
	var details = localStorage.getItem('details');
	$("#route-name").html(start+" - "+end);
	$("#route-desc").html(details);
	
};

$(document).on('page:load', ready);
$(document).ready(ready);

