const numberField = document.querySelector(".game #numberGuess");
const submitButton = document.querySelector(".game #submitGuess");
const prevGuessField = document.querySelector(".game #previousGuess");
const lowHighField = document.querySelector(".game #lowHighGuess");

const randomNumber = Math.round(Math.random()*100);
var lowGuess = 0;
var highGuess = 100;

numberField.addEventListener("input", (e) => {
    const currentGuess = Number(e.target.value);

    if ( !isNaN(currentGuess) && lowGuess <= currentGuess && currentGuess <= highGuess) {
        submitButton.disabled = false;
    } else {
        submitButton.disabled = true;
    }
});

submitButton.addEventListener("click", (e) => {
    const currentGuess = numberField.value;
    console.log(`value: ${currentGuess}`);
    prevGuessField.innerText += `${currentGuess}, `;
    if (currentGuess == randomNumber) {
        lowHighField.innerText = "Nice!";
    }

    if (currentGuess < randomNumber) {
        lowGuess = currentGuess;
        lowHighField.innerText = "Low";
    }

    if (currentGuess > randomNumber) {
        highGuess = currentGuess;
        lowHighField.innerText = "High";
    }
})