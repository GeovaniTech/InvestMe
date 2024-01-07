const imgs = document.getElementById("container-banners");
const img = document.querySelectorAll("#container-banners img");

let idx = 0;

function carousel() {
	idx++;
	
	if(idx > img.length - 1) {
		idx = 0;
	}
	
	imgs.style.transform = `translateX(${-idx * 100}vw)`;
}

setInterval(carousel, 4000);