
"use strict"
$.support.cors = true;

//IE9 fix
if(!window.console) {
	window.console = {log: function() {}};
}

/*	127.0.0.1 www.paprika.com	*/

var sUrl = window.location;
document.getElementById('fb').setAttribute('data-href', sUrl);

function statusChangeCallback(response) {
	console.log('statusChangeCallback');
	console.log(response);
	// The response object is returned with a status field that lets the
	// app know the current login status of the person.
	// Full docs on the response object can be found in the documentation
	// for FB.getLoginStatus().
	if (response.status === 'connected') {
		// Logged into your app and Facebook.
		var accessToken = response.authResponse.accessToken;
		alert("accessToken" + accessToken);
		testAPI();

	} else if (response.status === 'not_authorized') {
		// The person is logged into Facebook, but not your app.
		document.getElementById('status').innerHTML = 'Please log ' + 'into this app.';
	} else {
		// The person is not logged into Facebook, so we're not sure if
		// they are logged into this app or not.
		document.getElementById('status').innerHTML = 'Please log ' + 'into Facebook.';
	}
}

// This function is called when someone finishes with the Login
// Button.  See the onlogin handler attached to it in the sample
// code below.
function checkLoginState() {
	FB.getLoginStatus(function(response) {
		statusChangeCallback(response);
	});
}

window.fbAsyncInit = function() {
	FB.init({
		appId		: '1017667354989615',
		cookie	: true,	// enable cookies to allow the server to access 
		status	: true,	// the session
		xfbml		: true,	// parse social plugins on this page
		version	: 'v2.6'	// use version 2.6
	});

	// Now that we've initialized the JavaScript SDK, we call 
	// FB.getLoginStatus().  This function gets the state of the
	// person visiting this page and can return one of three states to
	// the callback you provide.  They can be:
	//
	// 1. Logged into your app ('connected')
	// 2. Logged into Facebook, but not your app ('not_authorized')
	// 3. Not logged into Facebook and can't tell if they are logged into
	//    your app or not.
	//
	// These three cases are handled in the callback function.

	FB.getLoginStatus(function(response) {
		statusChangeCallback(response);
	});

};

// Load the SDK asynchronously
(function(d, s, id) {
	var js, fjs = d.getElementsByTagName(s)[0];
	if (d.getElementById(id)) return;
	js = d.createElement(s); js.id = id;
	js.src = "//connect.facebook.net/en_US/sdk.js";
	fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));

// Here we run a very simple test of the Graph API after login is
// successful.  See statusChangeCallback() for when this call is made.
function testAPI() {
	console.log('Welcome!  Fetching your information.... ');
	FB.api('/me', function(response) {
		console.log('Successful login for: ' + response.name);
		document.getElementById('status').innerHTML =
			'Thanks for logging in, ' + response.name + '!';
	});
}






/*

//페이스북 SDK 초기화   
window.fbAsyncInit = function() {
	FB.init({
		appId : '1017667354989615',
		status : true, 	// check login status
		cookie : true,	// enable cookies to allow the server to access the session
		xfbml : true,		// parse XFBML
		version : 'v2.6'
	});
};

(function(d, s, id){
	var js, fjs = d.getElementsByTagName(s)[0];
	if (d.getElementById(id)) {return;}
	js = d.createElement(s); js.id = id;
	js.src = "//connect.facebook.net/en_US/sdk.js";
	fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));

FB.login(function(response) {
	if (response.session) {
		// user successfully logged in
	} else {
		// user cancelled login
	}
});

FB.getLoginStatus(function(response) {
	if (response.session) {
		// logged in and connected user, someone you know
	} else {
		// no user session available, someone you dont know
	}
});

FB.getLoginStatus(handleSessionResponse);
function handleSessionResponse(response) { 
	if (!response.session) { 
		// Open login dialog box
		FB.login(handleSessionResponse);
		return; 
	} else{
		// already logged in
	}
}

function getUser(){
	FB.getLoginStatus(handleSessionResponse);
	function handleSessionResponse(response) { 
		if (!response.session) {
			//
		} else{
			FB.api( 
					{
						method: 'fql.query', 
						query: 'select uid,name,email,pic_square from user where uid  = "' + FB.getSession().uid + '"'
					}, 
					function(response) {
						for(var i=0; i < response.length; i++){
							response[i].uid; //유저아이디
							response[i].pic_square; // 사진
							response[i].name; // 이름
							response[i].email; // 이메일주소
						}
					} 
			);
		}
	}	 
}

FB.api('/me', function(response) { console.log(response.name); });



 */
