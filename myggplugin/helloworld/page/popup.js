let changeColor = document.getElementById('changeColor');

let previousColor = 1
chrome.storage.sync.get('color', function(data) {
    changeColor.style.backgroundColor = data.color;
    changeColor.setAttribute('value', data.color);

    document.getElementById("cs").innerText = previousColor++;
});

changeColor.onclick = function(element) {
    document.getElementById("cs").innerText = previousColor++;

    let color = element.target.value;
    chrome.tabs.query({active: true, currentWindow: true}, function(tabs) {
        chrome.tabs.executeScript(
            tabs[0].id,
            {code: 'document.body.style.backgroundColor = "' + color + '";'});
    });
};