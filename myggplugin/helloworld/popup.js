var c = 0;
//Wire up event event handlers
document.addEventListener("DOMContentLoaded", function(event) {
    var resultsButton = document.getElementById("getResults");
    getResults();

    resultsButton.onclick = getResults;
});

//function getResults(){ alert('Hello World') }

function getResults(){
    chrome.tabs.query({ active: true, currentWindow: true }, function (tabs) {
        chrome.tabs.sendMessage(tabs[0].id, { action: "checkForWord" }, function (response) {
            showResults(response);
        });
    });
}

function showResults(results) {
    var resultsElement = document.getElementById("results");
    resultsElement.innerText = results ? "This page uses jQuery" : "This page does NOT use jQuery";
    resultsElement.innerText = resultsElement.innerText + " c:"+(++c);
}