/**
 * 
 */

function checkNotification(notification){
	if (!notification || 0 === notification.length || notification == "null"){
		document.getElementById("alert").hidden = true;
	} else {
		document.getElementById("alert").hidden = false;
	}
}

function checkNotificationLogin(notification, notificationRegistration){
	if (!notification || 0 === notification.length || notification == "null"){
		document.getElementById("alert").hidden = true;
	} else {
		document.getElementById("alert").hidden = false;
	}
	
	if (!notificationRegistration || 0 === notificationRegistration.length || notificationRegistration == "null"){
		document.getElementById("alert-registration").hidden = true;
	} else {
		document.getElementById("alert-registration").hidden = false;
	}
}