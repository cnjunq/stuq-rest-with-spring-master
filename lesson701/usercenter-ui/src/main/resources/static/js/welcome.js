angular.module('welcome', [ 'ngResource', 'ngRoute' ]).config(function($routeProvider, $httpProvider) {

    $routeProvider.when('/', {
        templateUrl : 'home.html',
        controller : 'home'
    }).when('/login', {
        templateUrl : 'login.html',
        controller : 'navigation'
    }).otherwise('/');
    
    $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

}).controller('navigation', function($rootScope, $scope, $http, $httpParamSerializer, $location, $route, $window) {
	$scope.tab = function(route) {
		return $route.current && route === $route.current.controller;
	};

	var authenticate = function(credentials, callback) {
		$scope.data = {grant_type:"password", username: credentials.username, password: credentials.password, client_id: "uc"};

		var req = {
				method: 'POST',
				url: "http://localhost:8082/oauth/token",
				headers: {
					"Authorization": "Basic " + btoa("uc" + ":" + "VXB0YWtlLUlyb24h"),
					"Content-type": "application/x-www-form-urlencoded; charset=utf-8"
						},
				data: $httpParamSerializer($scope.data)
		     }
		
		$http(req).then(function(data){
			if (data.data.access_token) {
				$rootScope.authenticated = true;
				$window.sessionStorage.accessToken = data.data.access_token;
			} else {
				$rootScope.authenticated = false;
				delete $window.sessionStorage.accessToken;
			}
		                
			callback && callback($rootScope.authenticated);
		  }, function(){
			  $rootScope.authenticated = false;
			  delete $window.sessionStorage.accessToken;
			  callback && callback(false);
		  	});
		}

	$scope.credentials = {};
	$scope.login = function() {
		authenticate($scope.credentials, function(authenticated) {
			if (authenticated) {
				console.log("登录成功")
				$location.path("/");
				$scope.error = false;
				$rootScope.authenticated = true;
			} else {
				console.log("登录失败")
				$location.path("/login");
				$scope.error = true;
				$rootScope.authenticated = false;
			}
		})
	};

}).controller('home', function($scope, $http, $window) {
    var headers = {
        "Accept" : "application/json", 
        authorization : "Bearer " + $window.sessionStorage.accessToken
    };
    
    $http.get("http://localhost:8082/api/roles/16", {
        headers : headers
    }).success(function(data) {
        $scope.role = data;
    })
});