<!DOCTYPE html>
<!-- saved from url=(0033)http://bootsnipp.com/iframe/g6GWQ -->
<html lang="en" ng-app>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="robots" content="noindex">

<title>NeedARide- Get there Together</title>
<meta name="viewport" content="width=device-width, initial-scale=1">


<link rel="stylesheet" type="text/css"
		href="resources/assets/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css"
		href="resources/assets/css/mystyle.css">
	<script type="text/javascript"
		src="resources/assets/js/bootstrap.min.js"></script>
	<!-- <script type="text/javascript" src="resources/assets/js/jquery-1.10.2.min.js"></script> -->
	<!-- <script type="text/javascript"
		src="resources/assets/lib/angular/angular.js"></script>
	<script type="text/javascript"
		src="resources/assets/lib/angular-route/angular-route.js"></script>
	<script type="text/javascript"
		src="resources/assets/lib/angular-resource/angular-resource.js"></script>
	<script type="text/javascript"
		src="resources/assets/lib/angular-bootstrap/ui-bootstrap.js"></script>
	<script type="text/javascript"
		src="resources/assets/lib/angular-bootstrap/ui-bootstrap-tpls.js"></script> -->
	<script type="text/javascript" src="resources/assets/controllers/ridescontroller.js"></script>
    <script type="text/javascript"
        src="//ajax.googleapis.com/ajax/libs/angularjs/1.0.7/angular.min.js"></script>
 
</head>
<body>
	<div class="site-wrapper" >
		<div class="site-wrapper-inner">
			<div class="cover-container">
				<div class="masthead clearfix">
					<div class="inner">
						<h2 class="masthead-brand">Need A Ride</h2>
						 		<ul class="nav masthead-nav">
							<li class="active"><a href="#" target="_blank">Hitch</a></li>
							<li><a href="#">Offer</a></li>

							<li><a href="#">Request</a></li>

							<li><a href="#">Edit</a></li>
							<li><a href="#">Browse</a></li>
						</ul>
					</div>
				</div>

				<div class="inner cover">
					<h2 class="cover-heading">Pool Your Rides</h2>

					<p class="lead">NeedARide makes ride-sharing easy, safe, and
						accessible for students in UCM. It connects drivers who already
						have a mid- to long-distance journey planned, with people who need
						a ride in the same direction. In just a few seconds, you can offer
						empty seats in your car, and passengers can book to ride along.
						Use the NeedARide App to share your rides on the GO - and can
						share travelling costs.</p>
				</div>
				<div class="container" align="center"
					background-color=rgba(245,245,245,0.4)>
					<div class="row" ng-app="rideshare" ng-controller="ridescontroller">
						<div class="col-md-4 col-md-offset-2 panel panel-default">

							<form class="margin-base-vertical" method="post">
								<p class="input-group">
									<span class="input-group-addon"><span
										class=""></span></span> <input type="text"
										class="form-control input-lg" name="userName2"
										placeholder="User name" ng-model="userName">
								</p>
								
								<p class="input-group">
									<span class="input-group-addon"><span
										class=""></span></span> <input type="password"
										class="form-control input-lg" name="password2"
										placeholder="Password" ng-model="password">
								</p>

								<p class="text-center">
									<button class="btn btn-success btn-info" ng-click=login()>Login</button>
									
								</p>
								<p class ="input-group">{{error}}</p>
								<!-- <p>
									New User?<a href="/rides/newUser" target="_blank">Register</a>
								</p> -->
							</form>

						</div>
						<div class="margin-base-vertical"></div>

					</div>
				</div>

			</div>
		</div>
	</div>
	<!-- <link rel="stylesheet" type="text/css"
		href="resources/assets/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css"
		href="resources/assets/css/mystyle.css">
	<script type="text/javascript"
		src="resources/assets/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="resources/assets/js/jquery-1.10.2.min.js"></script>
	<script type="text/javascript"
		src="resources/assets/lib/angular/angular.js"></script>
	<script type="text/javascript"
		src="resources/assets/lib/angular-route/angular-route.js"></script>
	<script type="text/javascript"
		src="resources/assets/lib/angular-resource/angular-resource.js"></script>
	<script type="text/javascript"
		src="resources/assets/lib/angular-bootstrap/ui-bootstrap.js"></script>
	<script type="text/javascript"
		src="resources/assets/lib/angular-bootstrap/ui-bootstrap-tpls.js"></script>
	<script type="text/javascript" src="resources/assets/controllers/ridescontroller.js"></script> -->
</body>
</html>