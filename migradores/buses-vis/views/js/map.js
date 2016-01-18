
var ready = function() {

	// Function to draw points on map.
	function drawRoute(coords,color){ 
		for(var i = 0; i<coords.length-1; i++){

			var c1 = coords[i]
			var c2 = coords[i+1];
			var pointA = new L.LatLng(c1['latitude'], c1['longitude']);
			var pointB = new L.LatLng(c2['latitude'], c2['longitude']);

			var pointList = [pointA, pointB];

			var firstpolyline = new L.Polyline(pointList, {
				color: color,
				weight: 3,
				opacity: 0.5,
				smoothFactor: 1
			});

			firstpolyline.addTo(map);

		}
	};	

	var map = L.map('map').setView([9.9187, -84.073457], 13);
	L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
	            attribution: '© OpenStreetMap contributors'
	        }).addTo(map);

	var route = localStorage.getItem('routeInfo');
	var routeJson = JSON.parse(route);
	drawRoute(routeJson.path,'red');
	$("#route-name").html("Ruta "+routeJson.id);
};

$(document).on('page:load', ready);
$(document).ready(ready);

