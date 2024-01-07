function orderFlow(step) {    
    var menu = null;
    var body = document.body;
    switch(step) {
		case 'cart':
		    menu = document.getElementById("cart");
		    break;
		case 'address':
			menu = document.getElementById("address");
			break;
		case 'payment':
			menu = document.getElementById("payment");
			break;
		case 'confirmOrder':
			menu = document.getElementById("confirmOrder");
			break;
		case 'doLogin':
			menu = document.getElementById("doLogin");
			break;
		case 'productDetails':
			menu = document.getElementById("productDetails");
			break;
		case 'finishedOrder':
			menu = document.getElementById("finishedOrder");
			break;
	}
	
	if (menu.style.width === "0px") {
	    menu.style.right = "0px";
	    menu.style.width = "100vw";
	    body.classList.add("no-scroll");
	} else {
	    menu.style.right = "-100vw";
	    menu.style.width = "0px";
	    body.classList.remove("no-scroll");
	}
}