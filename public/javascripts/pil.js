
var selectModule = angular.module("letsPlay", []);

selectModule.controller("selectCTRL", function($scope){
	$scope.decks = [
        "ICM Museum Deck 1",
        "ICM Museum Deck 2"];
        $scope.selectedDeck = $scope.decks[0];
        
        $scope.ages = [
        "0-3",
        "3+"];
        $scope.selectedAge = $scope.ages[0];

        var icmDeck = [
        { name: 'Train Room',
                logo: '@routes.Assets.at("images/icons/train.png")',
                youngOne:'@routes.Assets.at("images/cards/Cards-29-03.png")',
                youngTwo:'@routes.Assets.at("images/cards/Cards-30-03.png")',
                oldOne:'@routes.Assets.at("images/cards/Cards-31-39.png")',
                oldTwo:'@routes.Assets.at("images/cards/Cards-32-39.png")'}, 
        { name: 'Bodies In Motion', 
                logo:'@routes.Assets.at("images/icons/trail.png")',
                youngOne:'@routes.Assets.at("images/cards/Cards-21-03.png")',
                youngTwo:'@routes.Assets.at("images/cards/Cards-22-03.png")',
                oldOne:'@routes.Assets.at("images/cards/Cards-23-39.png")',
                oldTwo:'@routes.Assets.at("images/cards/Cards-24-39.png")'}, 
        { name: 'Wind Tunnel', 
                logo:'@routes.Assets.at("images/icons/windMill.png")',
                youngOne:'@routes.Assets.at("images/cards/Cards-59-03.png")',
                youngTwo:'@routes.Assets.at("images/cards/Cards-60-03.png")',
                oldOne:'@routes.Assets.at("images/cards/Cards-57-39.png")',
                oldTwo:'@routes.Assets.at("images/cards/Cards-58-39.png")'},
        { name: 'Post Office',
                logo:'@routes.Assets.at("images/icons/postOffice.png")',
                youngOne:'@routes.Assets.at("images/cards/Cards-53-03.png")',
                youngTwo:'@routes.Assets.at("images/cards/Cards-54-03.png")',
                oldOne:'@routes.Assets.at("images/cards/Cards-55-39.png")',
                oldTwo:'@routes.Assets.at("images/cards/Cards-56-39.png")'},
        { name: 'ABC Forest',
                logo:'@routes.Assets.at("images/icons/abcForest.png")',
                youngOne:'@routes.Assets.at("images/cards/Cards-05-03.png")',
                youngTwo:'@routes.Assets.at("images/cards/Cards-06-03.png")',
                oldOne:'@routes.Assets.at("images/cards/Cards-07-39.png")',
                oldTwo:'@routes.Assets.at("images/cards/Cards-08-39.png")'},
        { name: 'Art Room',
                logo:'@routes.Assets.at("images/icons/art.png")',
                youngOne:'@routes.Assets.at("images/cards/Cards-13-03.png")',
                youngTwo:'@routes.Assets.at("images/cards/Cards-14-03.png")',
                oldOne:'@routes.Assets.at("images/cards/Cards-15-39.png")',
                oldTwo:'@routes.Assets.at("images/cards/Cards-16-39.png")'},
        { name: 'Bank',
                logo:'@routes.Assets.at("images/icons/dollarCity.png")',
                youngOne:'@routes.Assets.at("images/cards/Cards-33-03.png")',
                youngTwo:'@routes.Assets.at("images/cards/Cards-34-03.png")',
                oldOne:'@routes.Assets.at("images/cards/Cards-35-39.png")',
                oldTwo:'@routes.Assets.at("images/cards/Cards-36-39.png")'},
        { name: 'Theatre',
                logo:'@routes.Assets.at("images/icons/theatre.png")',
                youngOne:'@routes.Assets.at("images/cards/Cards-49-03.png")',
                youngTwo:'@routes.Assets.at("images/cards/Cards-50-03.png")',
                oldOne:'@routes.Assets.at("images/cards/Cards-51-39.png")',
                oldTwo:'@routes.Assets.at("images/cards/Cards-52-39.png")'} 
    ];
    
    $scope.array = icmDeck;
    
    $scope.useDeck = function() { $scope.array = icmDeck; };
});

selectModule.filter('shuffle', function() {
    return function(k) {
        for(var j, x, i = k.length; i; j = parseInt(Math.random() * i), x = k[--i], k[i] = k[j], k[j] = x);
        
        return k;
    };
});
