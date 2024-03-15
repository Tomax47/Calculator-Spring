let rawEquation = document.getElementById("equation");
let equation = "";
let buttons = document.querySelectorAll(".calculator-keys button");

function handleClick(button) {
    console.log("Clicked");
    if (button.getAttribute("value") == "erase") {
        equation = "";
        rawEquation.setAttribute("value", equation);
    } else {
        equation += `${button.getAttribute("value")} `;
        rawEquation.setAttribute("value", equation);
    }
}

buttons.forEach(button => {
    button.addEventListener('click', () => {
        handleClick(button);
    });
});
