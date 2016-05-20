
var serverIp = "127.0.0.1";
var serverPort = "1234";

function splitId(route){
	var details, data;
 	var idx = route.search("Ruta");
 	data = route.substring(0, idx);
 	details = route.substring(idx, route.length); 
	data = data.split(" - ");
	if (data[1]==null) data[1] = "Desconocido";
	return [data[0], data[1], details];
}

function showRoutes(routes){
	
    var table = $('#routes-table').dataTable({
    	"iDisplayLength": 8,
	    'fnCreatedRow': function (nRow, aData, iDataIndex) {
	        $(nRow).attr('id', routes[iDataIndex]); // or whatever you choose to set as the id
	    },
        columns: [
            { title: "Origen" },
            { title: "Destino" },
            { title: "Detalles" }
        ],
        "language": {
            "lengthMenu": "Mostrando _MENU_ registros por página",
            "zeroRecords": "No se encontraron resultados",
            "info": "Mostrando página _PAGE_ de _PAGES_",
            "infoEmpty": "No hay registros disponibles",
            "infoFiltered": "(filtrados de _MAX_ registros totales)",
		    "emptyTable":     "No hay información disponible en la tabla",
		    "loadingRecords": "Cargando...",
		    "processing":     "Procesando...",
		    "search":         "Buscar:",
		    "paginate": {
		        "next":       "Siguiente",
		        "previous":   "Anterior"
		    }
        }
    });

    // Add data to datatable.
	for (var i = 0; i < routes.length; i++) {
    	var route = routes[i];
		var arr = splitId(route);
		table.dataTable().fnAddData(arr);
	}
	
	// To change search box styles
    $("#routes-table_filter input").addClass("form-control");
    $("#routes-table_filter input").attr("placeholder","Buscar");
    $("#routes-table_filter input").unwrap();
    $('#routes-table_filter').contents().filter(function(){
    	return this.nodeType === 3;
    }).remove();

}


var ready = function() {

	// Initially request routes info for table.
	$.ajax({
	    method: "GET",
	    url: 'http://'+serverIp+':'+serverPort+'/routes',
	}).success(function (routes) {
		showRoutes(routes);
	}).fail(function (routes) {
		alert("Algo no anduvo bien.\nAsegúrese que el servicio web este disponible.");
	});	


    $('body').on('click', '#routes-table tbody tr', function () { // Click on route
    	var id = $(this).attr("id");
    	var routeInfo = splitId(id);
		$.ajax({
		    method: "GET",
		    url: 'http://'+serverIp+':'+serverPort+'/routes',
		    data: {id: id}
		}).success(function (route) {
			localStorage.setItem('start', routeInfo[0]);
			localStorage.setItem('end', routeInfo[1]);
			localStorage.setItem('details', routeInfo[2]);
			localStorage.setItem('routeJson', JSON.stringify(route));
			window.location = "map.html";
		}).fail(function (route) {
			alert("Algo no anduvo bien.\nAsegúrese que el servicio web este disponible.");
		});
    	
    	
    });

};

$(document).on('page:load', ready);
$(document).ready(ready);