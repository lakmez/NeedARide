<!DOCTYPE html>
<html ng-app >
<head>
    <title>Hello World, AngularJS - ViralPatel.net</title>
    <script type="text/javascript"
        src="//ajax.googleapis.com/ajax/libs/angularjs/1.0.7/angular.min.js"></script>
        <script type="text/javascript" src="resources/assets/controllers/ridescontroller.js">
</script> 
</head>
<body>
     
    Write some text in textbox:
    <input type="text" ng-model="sometext" />
 
    <h1>Hello {{ sometext }}  {{ queryType }}</h1>
     
</body>
</html>