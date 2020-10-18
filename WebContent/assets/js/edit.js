/*************COUNTRY**********************/
var countryCode;
var countryName;
var region;
var allCountries=[];
var allRegions=[];
var allCities=[];
var countryAPI = "https://restcountries.eu/rest/v2/region/europe";
var APIkey="key=dc7692df595b81491eca7693afe2bcad";
var regionAPI = "http://battuta.medunes.net/api/region/";
var regionAPIkey="/all/?"+APIkey;
var cityAPI = "http://battuta.medunes.net/api/city/";
var regionSearchParameter = "/search/?region=";
/*************WEATHER**********************/
var weatherAPI="http://api.openweathermap.org/data/2.5/weather?q="
var weatherAPIkey="&appid=6d4ab9fe99683ad065a318d2e70e4a20&units=metric";
var allWeatherData=[];
var allWeatherCities=[];

function checkNotification(notification){
	if(!notification || 0===notification.length || notification=="null"){
		document.getElementById("alert").hidden=true;
	} else{
		document.getElementById("alert").hidden=false;
	}
}
/*************PHOTO**UPLOAD********************/
$(document).ready(function() {
$('.custom-file-input').on('change',function(){
	  var fileName = document.getElementById("customFile").files[0].name;
	  $(this).next('.custom-file-label').addClass("selected").html(fileName);
	});
});

function init(){
	$("#region-selection-container").hide("fast");
	$("#city-selection-container").hide("fast");
	loadCountry();
}
/************COUNTRY****API*****************/

function loadCountry(){
	var request=new XMLHttpRequest();
	request.onreadystatechange=function(){
		if((request.readyState==4)&&(request.status==200)){
			allCountries=JSON.parse(request.responseText);
			loadSelectCountry();
		}
	};
	request.open("GET",countryAPI, true);
	request.send(null);
}
function loadSelectCountry(){
	var element=document.getElementById("country-selection");
	allCountries.forEach(country=>{
		var option=document.createElement("option");
		option.text=country.name;
		option.value=country.name;
		element.add(option);
	});
	document.getElementById("country-selection-container").parentElement.classList.add("is-focused");
	document.getElementById("country-selection-container").parentElement.classList.add("is-upgraded");
}

function clearSelect(select){
	var length=select.options.length;
	for(i=length-1;i>0;i--){
		select.options[i]=null;
	}
	select.selectedIndex=0;
}
function loadSelectRegion(){
	var select=document.getElementById("region-selection");
	clearSelect(select);
	var selectCity=document.getElementById("city-selection");
	clearSelect(selectCity);
	allRegions.forEach(region=>{
		var option=document.createElement("option");
		option.text=region.region;
		option.value=region.region;
		select.add(option);
	});
	select.hidden=false;
	$("#region-selection-container").show("fast");
	document.getElementById("region-selection-container").parentElement.classList.add('is-focused');
	document.getElementById("region-selection-container").parentElement.classList.add('is-upgraded');
}
function JSONHttpRequestRegion(url, callback){
	
	var element=document.createElement('script');
	element.src=url;
	document.body.appendChild(element);
	window[callback]=(data)=>{
		allRegions=data;
		loadSelectRegion();
	}
}
function loadRegion(countryCode){
	JSONHttpRequestRegion(regionAPI+countryCode+regionAPIkey+"&callback=cb","cb");
}
function setFlagAndCountryCode(){
	if(countryName!=null){
		allCountries.forEach(country=>{
			if(countryName==country.name){
				countryCode=country.alpha2Code;
				document.getElementById("country-code").value=countryCode;
				document.getElementById("flag").value=country.flag;
			}
		});
	}
}
function onSelectCountry(countryCodeElement){
	countryName=countryCodeElement.value;
	setFlagAndCountryCode();
	loadRegion(countryCode);
}
function loadSelectCity(){
	var select=document.getElementById("city-selection");
	clearSelect(select);
	allCities.forEach(city=>{
		var option=document.createElement("option");
		option.text=city.city;
		option.value=city.city;
		select.add(option);
	});
	select.hidden=false;
	$("#city-selection-container").show("fast");
	document.getElementById("city-selection-container").parentElement.classList.add('is-focused');
	document.getElementById("city-selection-container").parentElement.classList.add('is-upgraded');
}
function JSONHttpRequestCity(url,callback){
	var element=document.createElement('script');
	element.src=url;
	document.body.appendChild(element);
	window[callback]=(data)=>{
		allCities=data;
		loadSelectCity();
		}
}

function loadCity(){
	JSONHttpRequestCity(cityAPI+countryCode+regionSearchParameter+region+"&"+APIkey+"&callback=cb","cb");
}
function onSelectRegion(regionElement){
	region=regionElement.value;
	loadCity();
}
/*************WEATHER******API****************/

function JSONHttpRequestWeatherCities(url, callback) {
    var e = document.createElement('script');
    e.src = url;
    document.body.appendChild(e);
    window[callback] = (data) => {
    	allWeatherCities = data;
        loadWeatherForRandomCities();
    }
}

function loadWeatherForRandomCities(){
		var i=0;
		for(i=0;i<3;i++){
		var randomIndex=Math.floor(Math.random()*allWeatherCities.length);
		var randomCity=allWeatherCities[randomIndex].city;
		loadWeather(randomCity);
		allWeatherCities.splice(randomIndex,1);

	}
}
function displayWeather(wcountryCode,wregion){
	JSONHttpRequestWeatherCities(cityAPI+wcountryCode+regionSearchParameter+wregion+"&"+APIkey+"&callback=cb","cb");
}

function loadWeather(city){
	var request=new XMLHttpRequest();
	request.onreadystatechange=function(){
		if((request.readyState==4)&&(request.status==200)){
			var result=JSON.parse(request.responseText);
			if(result!=null){
				addWeatherForcast(city,result.weather[0].icon,result.main.temp);
		
			}
		} 
	};
	request.open("GET",weatherAPI+city+weatherAPIkey,true);
	request.send(null);
}
function addWeatherForcast(city, image, temp){
	
	var weatherCard=document.getElementById("weather-card");
	var cardElement=document.createElement("div");
	cardElement.id="weather-in-"+city;
	var content="";
	content+='<div class="d-flex justify-content-between mb-2 pb-2 border-bottom">'+
	'<div class="d-flex align-items-center hover-pointer">'+
		'<img class="img-xs rounded-circle"'+
			'src="http://openweathermap.org/img/w/'+image+'.png" alt="">'+
		'<div class="ml-2">'+
			'<p>'+city+'</p>'+
			'<p class="tx-14 text-muted">'+temp+'&#8451</p>'+
		'</div>'+
	'</div>';
	cardElement.innerHTML=content;
	weatherCard.appendChild(cardElement);
}