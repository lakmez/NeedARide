/**
 * 
 */
  
    function verify_geolocation_capability()
    {
       if (navigator.geolocation)
       {
          document.getElementById('capability').value = "Enabled";
          retrieve_location();
       }
       else
       {
          document.getElementById('capability').value = "Not Enabled";
       }
    }

    function display_geolocation_properties(position)
    {
    	document.getElementById('capability').value = "W o r k i n g . . .";
    	document.getElementById('latitude').value = position.coords.latitude;
    	document.getElementById('longitude').value = position.coords.longitude;
    }

    function handle_error(error)
    {
       document.form1.capability.value = "ERROR: " + error.code;
    }

    function retrieve_location()
    {
       if (navigator.geolocation)
       {
    	   document.getElementById('capability').value = "Starting...";
          navigator.geolocation.getCurrentPosition(display_geolocation_properties, handle_error);
          document.getElementById('capability').value = "Finished";
       }
       else
       {
          alert("This browser does not support geolocation services.");
       }
    }
  