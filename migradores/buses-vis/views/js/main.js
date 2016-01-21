
var serverIp = "10.10.0.100";
var serverPort = "1234";

function splitId(route){
	var details, dates;
 	var idx = route.search("Ruta");
 	dates = route.substring(0, idx);
 	details = route.substring(idx, route.length); 
	dates = dates.split(" - ");
	if (dates[1]==null) dates[1] = "Unknown";	
	return [dates[0], dates[1], details];
}

function showRoutes(routes){
	
    var table = $('#routes-table').dataTable({
    	"iDisplayLength": 8,
	    'fnCreatedRow': function (nRow, aData, iDataIndex) {
	        $(nRow).attr('id', routes[iDataIndex]); // or whatever you choose to set as the id
	    },
        columns: [
            { title: "Start" },
            { title: "End" },
            { title: "Details" }
        ]
    });

	for (var i = 0; i < routes.length; i++) {
    	var route = routes[i];
		var arr = splitId(route);
		table.dataTable().fnAddData(arr);
	}
	

    $("#routes-table_filter input").addClass("form-control");
    $("#routes-table_filter input").attr("placeholder","Search");
    $("#routes-table_filter input").unwrap();
    $('#routes-table_filter').contents().filter(function(){
    	return this.nodeType === 3;
    }).remove();

}

var ready = function() {

	$.ajax({
	    method: "get",
	    url: 'http://'+serverIp+':'+serverPort+'/routes',
	}).success(function (response) {
		showRoutes(response);
	}).fail(function (response) {
		alert("Something went bad");
	});	


    $('body').on('click', '#routes-table tbody tr', function () {
    	var ide = $(this).attr("id");
    	var arr = splitId(ide);
		$.ajax({
		    method: "get",
		    url: 'http://'+serverIp+':'+serverPort+'/routes',
		    data: {id: ide}
		}).success(function (response) {
			localStorage.setItem('start', arr[0]);
			localStorage.setItem('end', arr[1]);
			localStorage.setItem('details', arr[2]);
			localStorage.setItem('routeInfo', JSON.stringify(response));
			window.location = "map.html";
		}).fail(function (response) {
			alert("Something went bad");
		});
    	
    	
    });

};

$(document).on('page:load', ready);
$(document).ready(ready);