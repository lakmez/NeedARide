/*function showResult(result) {
    document.getElementById('latitude').value = result.geometry.location.lat();
    document.getElementById('longitude').value = result.geometry.location.lng();
}

function getLatitudeLongitude(callback, address) {
    // If adress is not supplied, use default value 'Ferrol, Galicia, Spain'
    address = address || 'Ferrol, Galicia, Spain';
    // Initialize the Geocoder
    geocoder = new google.maps.Geocoder();
    if (geocoder) {
        geocoder.geocode({
            'address': address
        }, function (results, status) {
            if (status == google.maps.GeocoderStatus.OK) {
                callback(results[0]);
            }
        });
    }
}

var button = document.getElementById('btn');

button.addEventListener("click", function () {
    var address = document.getElementById('address').value;
    getLatitudeLongitude(showResult, address)
});*/

function getLatLong(){
	var geocoder = new google.maps.Geocoder();	
	
	 if (geocoder) {
	        geocoder.geocode({
	            'address': address
	        }, function (results, status) {
	            if (status == google.maps.GeocoderStatus.OK && results.length > 0) {
	            	debugger; 
			        var location = results[0].geometry.location,
			            lat      = location.lat(),
			            lng      = location.lng();
			      alert("Latitude: " + lat);
			      alert("Longitude: " + lng);
			      document.getElementById('latitude').value = lat;
			      document.getElementById('longitude').value = lng;
	            }
	        });
	    }
}