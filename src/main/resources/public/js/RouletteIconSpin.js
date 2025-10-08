const wheel = document.getElementById("wheel");
const spinBtn = document.getElementById("spinBtn");

spinBtn.addEventListener("click", () => {

    wheel.classList.remove("animate");
    void wheel.offsetWidth;
    wheel.classList.add("animate");
});