const buttons = document.querySelector(".genres .pill");
const wheel = document.getElementById("rouletteWheel");

buttons.forEach(button => {

   buttons.addEventListener("click", () => {

       wheel.classList.remove("slide-in");
       void wheel.offsetWidth;
       wheel.classList.add("slide in");
    });
});