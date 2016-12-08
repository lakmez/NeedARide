<!DOCTYPE html>

<html lang="en" ng-app>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="robots" content="noindex">

<title>NeedARide- Get there Together</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" type="text/css"
	href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css"
	href="resources/assets/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="resources/assets/css/mystyle.css">
<script type="text/javascript"
	src="resources/assets/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="resources/assets/controllers/ridescontroller.js"></script>
<script type="text/javascript"
	src="//ajax.googleapis.com/ajax/libs/angularjs/1.2.2/angular.min.js"></script>
<script
	src="http://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script>

<script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
<script type="text/javascript"
	src="resources/assets/js/LocationScript.js"></script>
</head>
<body>
	<div class="site-wrapper" ng-controller="ridescontroller"
		align="center">
		<div class="site-wrapper-inner" align="center">
			<div class="cover-container" align="center">
				<div class="masthead clearfix" align="center">
					<div class="inner" align="center">
						<h2 class="masthead-brand">Need A Ride</h2>
						<div ng-show='user!=null' align="center">
							<ul class="nav masthead-nav">
								<li><a href="" ng-click='pageChange("hitch")'>Hitch</a></li>
								<li><a href="" ng-click='pageChange("offer")'>Offer</a></li>
								<li><a href="" ng-click='pageChange("request")'>Request</a></li>
								<!-- <li><a href="" ng-click='pageChange("browse")'>Browse</a></li> -->
								<li><a href="" ng-click='pageChange("profile")'>My
										Rides</a></li>
								<li><a href="#" ng-click='logout("login")'>Logout</a></li>
							</ul>
						</div>
					</div>
					<!-- <input type="text" id="latitude" /> <input type="text"
						id="longitude" /> -->
				</div>

				<div id="Login" align="center" ng-show='page=="login"'
					ng-if="user==null">
					<div class="inner cover" align="center">
						<h2 class="cover-heading"></h2>

						<p class="lead">
							NeedARide makes ride-sharing easy, safe, and accessible for
							students in UCM. It connects drivers who already have a mid- to
							long-distance journey planned, with people who need a ride in the
							same direction. In just a few seconds, you can offer empty seats
							in your car, and passengers can book to ride along. Use the
							NeedARide App to share your rides on the GO...<span class=""
								title="Test" data-original-title=""></span>

						</p>
					</div>
					<div class="container" align="center" id=hitch
						background-color=rgba(245,245,245,0.4)>
						<div class="row" align="center">
							<div class="col-md-4 col-md-offset-2 panel panel-default"
								align="center">

								<form class="margin-base-vertical" method="post">
									<p class="input-group">
									<br/>
										<input type="text" class="form-control" name="name"
											placeholder="Name" ng-model="inp.userName" />

									</p>

									<p class="input-group">

										<input type="text" class="form-control" name="password"
											placeholder="Password" ng-model="inp.password" />
									</p>
									{{error}}
									<p class="text-center">

										<button class="btn btn-success" ng-click=login()>Login</button>
									</p>

								</form>

							</div>
						</div>
					</div>

				</div>


				<div id="Hitch" align="center" ng-show='page=="hitch"'
					ng-if="user!=null">
					<div class="inner cover" align="center">
						<h2 class="cover-heading">Hitch A Ride</h2>

						<p class="lead">
							Don't feel like driving?... Make it to classes with friends...
							Get there together... <span class="" title="Test"
								data-original-title=""></span>

						</p>
					</div>
					<div class="container" align="center" id=hitch
						background-color=rgba(245,245,245,0.4)>
						<div class="row" align="center">
							<div class="col-md-4 col-md-offset-2 panel panel-default"
								align="center">

								<form class="margin-base-vertical" method="post">
									<p>
									<div class="input-group">
										<input type="text" class="form-control" placeholder="Source"
											ng-model="inp.source" id="inputGroup"
											ng-blur='getLocationFromAddress(inp.source,"src")' /> <span
											class="input-group-addon" title="Your Location"
											data-original-title=""> <i class="fa fa-map-marker"
											ng-click=findLocation()></i>
										</span>
									</div>
									</p>

									<p>
									<div class="input-group">
										<input type="text" class="form-control"
											placeholder="Destination" ng-model="inp.destination"
											id="inputGroup"
											ng-blur='getLocationFromAddress(inp.destination,"dest")' />
										<span class="input-group-addon" title="Swap"
											data-original-title=""> <i
											class="fa fa-exchange fa-rotate-90" ng-click=swap()></i>
										</span>
									</div>
									</p>

									<p>
										<input type="date" class="form-control " name="date"
											ng-model="inp.date" />
									</p>
									<p>
										<input type="time" class="form-control " name="time"
											ng-model="inp.time" />
									</p>
									<!-- {{slatlong}} ||{{dlatlong}} -->
									<p class="text-center">
										{{error}}
										<button class="btn btn-success" ng-click='searchRides()'>Find
											Ride</button>
										<button class="btn btn-info" ng-click=clear()>Clear</button>
									</p>
								</form>
							</div>
						</div>
					</div>
					<div ng-show="inp.showOffers">
						<table style="width: 600px" st-table="rideOffers"
							st-safe-src="rowCollection" class="table table-bordered"
							align="center">
							<thead>
								<th>Source</th>
								<th>Destination</th>
								<th>Date</th>
								<th>Seats</th>
								<th>Provider</th>
								<th></th>
							</thead>
							<tbody>
								<tr ng-repeat="row in rideOffers">
									<td>{{row.content.source.address}}</td>
									<td>{{row.content.destination.address}}</td>
									<td>{{ row.content.datetime| date:"yyyy-MM-dd HH:mm" }}</td>
									<td>{{row.content.seats}}</td>
									<td>{{row.content.userName}}</td>
									<td>
										<button type="button" class="btn btn-xs btn-success"
											data-toggle="tooltip" tooltip="delete offer"
											tooltip-placement="right"
											ng-click="selectOffer(row.content.offerid)">Select</button>
									</td>
								</tr>
							</tbody>
						</table>
					</div>

				</div>
				<div id="Offer" align="center" ng-show='page=="offer"'
					ng-if="user!=null">
					<div class="inner cover">
						<h2 class="cover-heading">Offer A Ride</h2>

						<p class="lead">Offer free seats on your car and share rides
							among friends.. Save on your traveling costs... Make it to
							classes together... And save the Earth by leaving a smaller
							carbon footprint...</p>
					</div>

					<div class="container" align="center" id=hitch
						background-color=rgba(245,245,245,0.4)>
						<div class="row" align="center">
							<div class="col-md-4 col-md-offset-2 panel panel-default"
								align="center">

								<form class="margin-base-vertical" method="post">
									<p>
									<div class="input-group">
										<input type="text" class="form-control" placeholder="Source"
											ng-model="inp.source" id="inputGroup"
											ng-blur='getLocationFromAddress(inp.source,"src")' /> <span
											class="input-group-addon" title="Your Location"
											data-original-title=""> <i class="fa fa-map-marker"
											ng-click=findLocation()></i>
										</span>
									</div>
									</p>

									<p>
									<div class="input-group">
										<input type="text" class="form-control"
											placeholder="Destination" ng-model="inp.destination"
											id="inputGroup"
											ng-blur='getLocationFromAddress(inp.destination,"dest")' />
										<span class="input-group-addon" title="Swap"
											data-original-title=""> <i
											class="fa fa-exchange fa-rotate-90" ng-click=swap()></i>
										</span>
									</div>
									</p>
									<p>
										<input type="text" class="form-control" name="seats"
											ng-model="inp.seats" placeholder="No of seats" />
									</p>
									<p>
										<input type="date" class="form-control " name="date"
											ng-model="inp.date" />
									</p>
									<p>
										<input type="time" class="form-control " name="time"
											ng-model="inp.time" />
									</p>
									<!-- {{slatlong}} ||{{dlatlong}}||{{date}}||{{time}}||{{seats}} -->
									<p class="text-center">
										{{error}}
										<button class="btn btn-success" ng-click='addOffer()'>Register
											Offer</button>
										<button class="btn btn-info" ng-click=clear()>Clear</button>

									</p>
								</form>
							</div>
						</div>
					</div>

				</div>
				<div id="Request" align="center" ng-show='page=="request"'
					ng-if="user!=null">
					<div class="inner cover">
						<h2 class="cover-heading">Request A Ride</h2>

						<p class="lead">Cannot find the ride you need... Request
							one...Feeling lucky...</p>
					</div>

					<div class="container" align="center" id=hitch
						background-color=rgba(245,245,245,0.4)>
						<div class="row" align="center">
							<div class="col-md-4 col-md-offset-2 panel panel-default"
								align="center">



								<form class="margin-base-vertical" method="post">
									<p>
									<div class="input-group">
										<input type="text" class="form-control" placeholder="Source"
											ng-model="inp.source" id="inputGroup"
											ng-blur='getLocationFromAddress(inp.source,"src")' /> <span
											class="input-group-addon" title="Your Location"
											data-original-title=""> <i class="fa fa-map-marker"
											ng-click=findLocation()></i>
										</span>
									</div>
									</p>

									<p>
									<div class="input-group">
										<input type="text" class="form-control"
											placeholder="Destination" ng-model="inp.destination"
											id="inputGroup"
											ng-blur='getLocationFromAddress(inp.destination,"dest")' />
										<span class="input-group-addon" title="Swap"
											data-original-title=""> <i
											class="fa fa-exchange fa-rotate-90" ng-click=swap()></i>
										</span>
									</div>
									</p>
									<p>
										<input type="date" class="form-control " name="date"
											ng-model="inp.date" />
									</p>
									<p>
										<input type="time" class="form-control " name="time"
											ng-model="inp.time" />
									</p>
									<!-- {{slatlong}} ||{{dlatlong}}{{error}} -->
									<p class="text-center">
										{{error}}
										<button class="btn btn-success" ng-click='addRequest()'>Add
											Request</button>
										<button class="btn btn-info" ng-click=clear()>Clear</button>
									</p>
								</form>

							</div>
						</div>
					</div>

				</div>
				<!-- <div id="Browse" align="center" ng-show='page=="browse"'
					ng-if="user!=null">
					<div class="inner cover">
						<h2 class="cover-heading">Browse Requests</h2>

						<p class="lead">Got some free time...Want to do some good or
							earn a few bucks...Give a ride... Browse through ride requests</p>
					</div>

					<div class="container" align="center" id=hitch
						background-color=rgba(245,245,245,0.4)>
						<div class="row" align="center">
							<div class="col-md-4 col-md-offset-2 panel panel-default"
								align=center>

								<form class="margin-base-vertical" method="post">
									<p class="input-group">
										<input type="text" class="form-control" name="source"
											placeholder="Source" ng-model="inp.source" />

									</p>

									<p class="input-group">

										<input type="text" class="form-control" name="destination"
											placeholder="Destination" ng-model="inp.destination" />
									</p>

									<p class="text-center">
										<button class="btn btn-success btn-info" ng-click=findRides()>Find
											Rides</button>
										<button type="button" class="btn btn-s">
											<span class="glyphicon glyphicon-map-marker"
												title="Your Location" data-original-title=""> </span>
										</button>
										<button type="button" class="btn btn-s">
											<span class="glyphicon glyphicon-sort" title="Swap"
												data-original-title=""> </span>
										</button>
									</p>
									<p>{{contacts[0]}}</p>

								</form>

							</div>
						</div>
					</div>

				</div> -->


				<div id="MyRides" align="center" ng-show='page=="profile"'
					ng-if="user!=null">
					<div class="inner cover" align="center">
						<!-- <h2 class="cover-heading">Your Rides</h2> -->

						<!-- <p class="lead"><h2>Your Rides</h2></p> -->


						<div class="container" align="center" id=hitch
							background-color=rgba(245,245,245,0.4)>
							<div class="row" align="center"></div>
						</div>
					</div>
					<div class="" align="center">

						<form align="center" class="margin-base-vertical" method="post">
							<p class="input-group">
							<h3>My Offers</h3>
							<table style="width: 600px" st-table="myRides[0]"
								st-safe-src="rowCollection" class="table table-bordered"
								align="center">
								<thead>
									<th>Source</th>
									<th>Destination</th>
									<th>Date</th>

									<th>Seats</th>
									<th></th>
								</thead>
								<tbody>
									<tr ng-repeat="row in myRides[0]">
										<td>{{row.source.address}}</td>
										<td>{{row.destination.address}}</td>
										<td>{{ row.datetime| date:"yyyy-MM-dd HH:mm" }}</td>

										<td>{{row.seats}}</td>
										<td>
											<button type="button" class="btn btn-xs btn-danger"
												data-toggle="tooltip" tooltip="delete offer"
												tooltip-placement="right"
												ng-click="deleteOffers(row.offerid)">X</button>
										</td>
									</tr>
								</tbody>
							</table>
							<h3>My Requests</h3>
							<table style="width: 600px" st-table="myRides[1]"
								st-safe-src="rowCollection" class="table table-bordered"
								align="center">
								<thead>
									<th>Source</th>
									<th>Destination</th>
									<th>Date</th>
									<th>Active</th>
									<th></th>
								</thead>
								<tbody>
									<tr ng-repeat="row in myRides[1]">
										<td>{{row.source.address}}</td>
										<td>{{row.destination.address}}</td>
										<td>{{ row.datetime| date:"yyyy-MM-dd HH:mm" }}</td>
										<td>{{row.active}}</td>
										<td>
											<button type="button"
												class="btn btn-xs btn-danger
														data-toggle="
												tooltip" tooltip="delete ride" tooltip-placement="right"
												ng-click="findRMatchingOffers(row)">X</button>
										</td>
										<td>
										<span ng-show="row.matchedOfferId!=null && row.active">
											<button type="button"
												class="btn btn-xs btn-success
														data-toggle="
												tooltip" tooltip="Accept Matching offer" tooltip-placement="right"
												ng-click="acceptMatchedRide(row)">Matching Offer</button>
												</span>
										</td>
									</tr>
								</tbody>
							</table>
							</p>
					
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<script type='text/javascript'>
    baser= "http://@((Request.Url.Authority + Request.ApplicationPath).TrimEnd('/'))/";
    </script>
</body>
</html>