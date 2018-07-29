function showNotice(title,messageText)
{
	showNotification(title,messageText,'notice');
}
function showWarning(title,messageText)
{
	showNotification(title,messageText,'warning');
}
function showError(title,messageText)
{
	showNotification(title,messageText,'error');
}

function showNotification(title,messageText,type)
{
	var IconClass;
	switch(type)
	{
	case 'notice':
		IconClass = '<span class="icon-calendar"></span>';
	break;
	case 'warning':
		IconClass = '<span class="icon icon-calendar"></span>';
	break;
	case 'error':
		IconClass = '<span class="icon icon-calendar"></span>';
	break;
	default:
		IconClass = '<span class="icon icon-calendar"></span>';	
	}
	var HTMLData = IconClass+'<div>'+title+'</div><div>'+ messageText + '</div>'

	// create the notification
	var notification = new NotificationFx({
		message : HTMLData,
		// layout type: growl|attached|bar|other
		layout : 'attached',
		// effects for the specified layout:
		// for growl layout: scale|slide|genie|jelly
		// for attached layout: flip|bouncyflip
		// for bar layout: slidetop|exploader
		// for other layout: boxspinner|cornerexpand|loadingcircle|thumbslider
		// ...
		effect : 'bouncyflip',
		type : type, // notice, warning or error
		ttl : 8000,
		onClose : function() 
		{
			//do nothing
		}
	});

	// show the notification
	notification.show();
}
