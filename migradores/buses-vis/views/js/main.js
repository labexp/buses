
function showRoutes(routes){
	var dataSet = [];
	for (var i = 0; i < routes.length; i++) {
    	var r = routes[i];
		dataSet.push([r]);
	}
	
    $('#routes-table').DataTable({
        data: dataSet,
        columns: [
            { title: "ID" }
        ]
    });

    $("#routes-table_filter input").addClass("form-control");
    //$("#routes-table_filter label").hide();
    $("#routes-table_filter input").attr("placeholder","Search");
    $("#routes-table_filter input").unwrap();
    $('#routes-table_filter').contents().filter(function(){
    	return this.nodeType === 3;
    }).remove();

}

var ready = function() {

	$.ajax({
	    method: "get",
	    url: 'http://127.0.0.1:1234/routes',
	}).success(function (response) {
		showRoutes(response);
	}).fail(function (response) {
		alert("Something went bad");
	});	


    $('body').on('click', '#routes-table tbody td', function () {
    	var ide = this.id;
		$.ajax({
		    method: "get",
		    url: 'http://127.0.0.1:1234/routes',
		    data: {id: ide}
		}).success(function (response) {
			localStorage.setItem('routeInfo', JSON.stringify(response));
			window.location = "map.html";
		}).fail(function (response) {
			alert("Something went bad");
		});
    	
    	
    });


    /*
	$('#search-txt').keyup(function() {

	    var val = '^(?=.*\\b' + $.trim($(this).val()).split(/\s+/).join('\\b)(?=.*\\b') + ').*$',
	        reg = RegExp(val, 'i'),
	        text;

        $('#routes-table').paging({limit:7});

	    $rows.show().filter(function() {
	        text = $(this).text().replace(/\s+/g, ' ');
	        return !reg.test(text);
	    }).hide();
	});
	*/




};

$(document).on('page:load', ready);
$(document).ready(ready);