function openMenu() {  
    if(document.getElementById("header").style.width == "0px") {
        document.getElementById("header").style.left = "0px";
        document.getElementById("header").style.width = "250px";
        document.getElementById("body-main").style.marginLeft = "260px"
        document.getElementById("top-header").style.paddingLeft = "250px"
    } else {
        document.getElementById("header").style.left = "-300px";
        document.getElementById("header").style.width = "0px";
        document.getElementById("body-main").style.marginLeft = "10px"
        document.getElementById("top-header").style.paddingLeft = "0px"
    }
}