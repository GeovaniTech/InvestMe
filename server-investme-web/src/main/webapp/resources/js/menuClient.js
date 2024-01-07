function openMenu() {  
    if(document.getElementById("header").style.width == "0px") {
        document.getElementById("header").style.left = "0px";
        document.getElementById("header").style.width = "250px";
    } else {
        document.getElementById("header").style.left = "-300px";
        document.getElementById("header").style.width = "0px";
    }
}