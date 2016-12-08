angular.module('ridesshare')
.controller('ridescontroller',[ '$scope', '$location', '$http', '$rootScope', '$log',
				function($scope, $location, $http, $rootScope, $log) {
					$scope.environments = "";
					$scope.queryType = "ssn";
					$scope.selEnvironment = "PROD";
					$scope.error = false;
					$scope.page ="hitch";
					$scope.search = function() {
						
						$scope.getResults();
					};
					
					
					$scope.pageChange = function(value) {
				debugger		$scope.page = value;
						$scope.getResults();
					};
				} 
		]);