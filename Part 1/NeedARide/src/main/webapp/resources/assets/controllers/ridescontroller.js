function ridescontroller($scope, $http) {
	//$scope.contacts = [ "viralpatel.net@gmail.com", "hello@email.com" ];
	$scope.page = "login";
	$scope.user = null;
	$scope.selRequest=null;
	$scope.myRides = null;
	$scope.rideOffers =null;
	$scope.inp = {};
	$scope.inp.showOffers=false;
	$scope.inp.rowdetail = false;
	$scope.slatlong = null;
	$scope.dlatlong = null;

	$scope.add = function() {
		$http({
			method : 'GET',
			url : 'register'
		}).success(function(data, status, headers, config) {
			debugger;
			$scope.viewReturnStatusData = data;
			$scope.viewReturnStatus = true;
			$scope.viewReturnStatusText = "Collapse Status";
		}).error(function(data, status, headers, config) {
			debugger;
			console.log(data);
		});
	}

	$scope.login = function() {
		debugger;
		$scope.error = "";
		$http(
				{
					method : 'GET',
					url : 'login?userName=' + $scope.inp.userName
							+ '&password=' + $scope.inp.password
				}).success(function(data, status, headers, config) {
			debugger;
			if (data != "") {
				$scope.user = data;
				$scope.page = "hitch";
			} else {
				$scope.page = "login";
				$scope.error = "Login Failed"
				$scope.user = null;
			}
		}).error(function(data, status, headers, config) {
			debugger;
			console.log(data);
		});
	}

	$scope.logout = function() {
		debugger;
		$scope.error = "";
		$scope.user = null;
		$scope.pageChange("login");
	};

	$scope.pageChange = function(value) {
		debugger;
		$scope.error = "";
		$scope.page = value;
		if (value == "profile") {
			$scope.profile();
		}
	};

	$scope.findLocation = function() {
		$scope.error = "";
		if (navigator.geolocation) {
			navigator.geolocation.getCurrentPosition($scope.showPosition,
					$scope.showError);

		} else {
			$scope.error = "Geolocation is not supported by this browser.";
			alert("Geolocation is not supported by this browser");
		}
	}

	$scope.showError = function(error) {
		$scope.error = "";
		switch (error.code) {
		case error.PERMISSION_DENIED:
			$scope.error = "User denied the request for Geolocation."
				alert("User denied the request for Geolocation.");
			break;
		case error.POSITION_UNAVAILABLE:
			$scope.error = "Location information is unavailable."
				alert("Location information is unavailable.");
			break;
		case error.TIMEOUT:
			$scope.error = "The request to get user location timed out."
				alert("The request to get user location timed out.");
			break;
		case error.UNKNOWN_ERROR:
			$scope.error = "An unknown error occurred."
				alert("An unknown error occurred.");
			break;
		}
		$scope.$apply();
	}

	$scope.showPosition = function(position) {
		$scope.inp.source = "Your Location";
		
		$scope.error = "";
		$scope.lat = position.coords.latitude;
		$scope.lng = position.coords.longitude;
		$scope.slatlong = $scope.lat + "," + $scope.lng;
		$scope.accuracy = position.coords.accuracy;		
		$scope.$apply();
		$scope.getAddressOfYourLocation($scope.lat ,$scope.lng);
	}

	//
	$scope.getAddressOfYourLocation = function(lat, lng) {
		debugger;
		$scope.error = "";
		//if ($scope.address != "Your Location" && $scope.address != "") {
			$scope.position = "";
			delete $http.defaults.headers.common['X-Requested-With'];
			$http(
					{
						method : 'GET',
						//https://maps.googleapis.com/maps/api/geocode/json?latlng=40.714224,-73.961452&key=YOUR_API_KEY
						url : 'https://maps.googleapis.com/maps/api/geocode/json?latlng='
								+lat+','+lng								
								+ '&key=AIzaSyAAqEuv_SHtc0ByecPXSQiKH5f2p2t5oP4',
						headers : {
							'Content-Type' : undefined
						}
					}).success(
					function(data, status, headers, config) {
						debugger;
						$scope.inp.source=data.results[0].formatted_address;
						
					}).error(function(data, status, headers, config) {
				debugger;
				alert("Error Occured Resolving your Address. Please try again");
				console.log(data);
			});
		
	}

	
	//
	
	
	
	$scope.getLocationFromAddress = function(address, val) {
		debugger;
		$scope.error = "";
		if ($scope.address != "Your Location" && $scope.address != "") {
			$scope.position = "";
			delete $http.defaults.headers.common['X-Requested-With'];
			$http(
					{
						method : 'GET',
						url : 'https://maps.googleapis.com/maps/api/geocode/json?address='
								+ address
								+ '&key=AIzaSyAAqEuv_SHtc0ByecPXSQiKH5f2p2t5oP4',
						headers : {
							'Content-Type' : undefined
						}
					}).success(
					function(data, status, headers, config) {
						debugger;
						$scope.position = data.results[0].geometry.location.lat
								+ "," + data.results[0].geometry.location.lng;// should
																				// return
																				// put
																				// values
																				// in
																				// slatlong
																				// dlatlong
						if (val == "src") {
							debugger;
							$scope.slatlong = $scope.position;
						} else {
							debugger;
							$scope.dlatlong = $scope.position;
							
						}
						// $scope.viewReturnStatusText = "Collapse Status";
					}).error(function(data, status, headers, config) {
				debugger;
				alert("Error Occured Resolving your Address. Please try again");
				console.log(data);
			});
		} else {
			$scope.findLocation();
		}
	}

	$scope.swap = function() {
		debugger;
		$scope.error = "";
		$scope.temp = $scope.inp.source;
		$scope.inp.source = $scope.inp.destination;
		$scope.inp.destination = $scope.temp;
		$scope.temp = $scope.slatlong;
		$scope.slatlong = $scope.dlatlong;
		$scope.dlatlong = $scope.temp;
	}
	$scope.addOffer = function() {
		debugger;
		$scope.error = "";

		$http(
				{
					method : 'GET',
					url : 'offer?userName=' + $scope.user.userName + '&source='
							+ $scope.slatlong + '&sAddress='
							+ $scope.inp.source + '&destination='
							+ $scope.dlatlong + '&dAddress='
							+ $scope.inp.destination + '&datetime='
							+ $scope.inp.date + " " + $scope.inp.time
							+ '&seats=' + $scope.inp.seats

				}).success(function(data, status, headers, config) {
			debugger;
			alert("Offer Added....");
		}).error(function(data, status, headers, config) {
			debugger;
			console.log(data);
		});
	}

	$scope.clear = function() {
		$scope.error = "";
		$scope.error = "";
		$scope.temp = null;
		$scope.inp.source = null;
		$scope.inp.destination = null;
		$scope.slatlong = null;
		$scope.inp.dlatlong = null;
		$scope.inp.date = null;
		$scope.inp.time = null;
		$scope.rideOffers =null;
		$scope.inp = {};
		$scope.inp.showOffers=false;
		$scope.slatlong = null;
		$scope.dlatlong = null;
	}

	$scope.addRequest = function() {
		debugger;
		$scope.error = "";

		$http(
				{
					method : 'GET',
					url : 'request?userName=' + $scope.user.userName
							+ '&source=' + $scope.slatlong + '&sAddress='
							+ $scope.inp.source + '&destination='
							+ $scope.dlatlong + '&dAddress='
							+ $scope.inp.destination + '&datetime='
							+ $scope.inp.date + " " + $scope.inp.time

				}).success(function(data, status, headers, config) {
			debugger;
			alert("Request Added");
		}).error(function(data, status, headers, config) {
			debugger;
			console.log(data);
		});
	}

	$scope.profile = function() {
		debugger;
		$scope.error = "";

		$http({
			method : 'GET',
			url : 'myProfile?userName=' + $scope.user.userName

		}).success(function(data, status, headers, config) {
			debugger;
			$scope.myRides = data;

		}).error(function(data, status, headers, config) {
			debugger;
			console.log(data);
		});
	}
	$scope.searchRides = function() {
		debugger;
		$scope.error = "";

		$http(
				{
					method : 'GET',
					url : 'search?userName=' + $scope.user.userName
							+ '&source=' + $scope.slatlong + '&sAddress='
							+ $scope.inp.source + '&destination='
							+ $scope.dlatlong + '&dAddress='
							+ $scope.inp.destination + '&datetime='
							+ $scope.inp.date + " " + $scope.inp.time

				}).success(function(data, status, headers, config) {
			debugger;
			$scope.inp.showOffers=true;
			$scope.rideOffers = data;

		}).error(function(data, status, headers, config) {
			debugger;
			console.log(data);
		});
	}

	$scope.deleteOffers = function(value) {
		debugger;
		$scope.error = "";

		$http({
			method : 'GET',
			url : 'delete?id=' + value + '&type=offer'

		}).success(function(data, status, headers, config) {
			debugger;
			// $scope.myRides = data;
			$scope.profile();
			alert("Deleted Offer");
		}).error(function(data, status, headers, config) {
			debugger;
			console.log(data);
		});
	}
	$scope.deleteRequests = function(value) {
		debugger;
		$scope.error = "";

		$http({
			method : 'GET',
			url : 'delete?id=' + value + '&type=request'

		}).success(function(data, status, headers, config) {
			debugger;
			// $scope.myRides = data;			
			$scope.profile();
			alert("Deleted Request");
		}).error(function(data, status, headers, config) {
			debugger;
			console.log(data);
		});
	}
	$scope.selectOffer = function(value) {
		debugger;
		$scope.error = "";

		$http({
			method : 'GET',
			url : 'select?id=' + value + '&userName='+$scope.user.userName

		}).success(function(data, status, headers, config) {
			debugger;
			$scope.inp.showOffers=false;
			alert("Offer Accepted");
			
		}).error(function(data, status, headers, config) {
			debugger;
			console.log(data);
		});
	}
	
	$scope.acceptMatchedRide = function(value){
	   $scope.selRequest = value;
       $scope.inp.rowdetail = true;
       $http({
			method : 'GET',
			url : 'select?id=' + $scope.selRequest.matchedOfferId + '&userName='+$scope.user.userName+'&reqId='+$scope.selRequest.requestId

		}).success(function(data, status, headers, config) {
			debugger;
			//$scope.inp.showOffers=false;
			if(null!=data)
				alert("Offer Accepted");
			else
				alert("No Seats available");
			
		}).error(function(data, status, headers, config) {
			debugger;
			console.log(data);
		});
    }
	
	
}

