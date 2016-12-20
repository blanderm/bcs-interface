{
    "ecobeePin": "yetz",
    "code": "q8XVNmT6IiRLyOGlEiDcO73m1S1AnPQ2",
    "scope": "smartWrite",
    "expires_in": 9,
    "interval": 30
}

var apiKey = $('#apiKey').val();
var url = "https://api.ecobee.com/authorize?response_type=ecobeePin&client_id=".concat(apiKey).concat("&scope=smartWrite");
$.getJSON(url,  function(data) {
    var response = JSON.stringify(data, null, 4);
    $('#authorizeResponse').html(response);
});

apiKey = $('#apiKey').val();
refreshToken = $('#refreshToken').val();

var url = "https://api.ecobee.com/token";
var data = "grant_type=refresh_token&code=".concat(refreshToken).concat("&client_id=").concat(apiKey);

$.post(url, data, function(resp) {
    var response = JSON.stringify(resp, null, 4);
    $('#refreshTokenResponse').html(response);
}, 'json');

{
    "access_token": "g3csVXLzNoWooDEWuMzlkXDNQVLLF5Kw",
    "token_type": "Bearer",
    "expires_in": 3599,
    "refresh_token": "k0eIlvfh1cXjBq9n3YpEjo65goosxfYN",
    "scope": "smartWrite"
}

{
    "access_token": "20l0G6FpFcRFRYJq0oLgAtnKVGcizGgr",
    "token_type": "Bearer",
    "expires_in": 3599,
    "refresh_token": "4yo2XRMymLGpzoRFCDM4oNyooZyl12oB",
    "scope": "smartWrite"
}