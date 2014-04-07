var app = angular.module('letsplay', []);

app.controller('letsplayCTRL', function($scope)) {
	$scope.deckAgeSelected = function(){
        var deck = document.getElementById('deck').value;
        document.getElementById('selectedDeck').innerHTML = deck;
        var age = document.getElementById('age').value;
        document.getElementById('selectedAge').innerHTML = age;
     }

});


    
  