document.addEventListener('DOMContentLoaded', restore_options);
document.getElementById('save').addEventListener('click',
    save_options);
// Saves options to chrome.storage
function save_options() {
    var autoFocus = document.getElementById('autoFocus').checked;
    chrome.storage.sync.set({
        autoFocus: autoFocus
    }, function() {
        // Update status to let user know options were saved.
        var status = document.getElementById('status');
        status.textContent = 'Options saved.';
        setTimeout(function() {
            status.textContent = '';
        }, 750);
    });
}

function restore_options() {
    // Use default value color = 'red' and likesColor = true.
    chrome.storage.sync.get({
        autoFocus: true
    }, function(items) {
        document.getElementById('autoFocus').checked = items.autoFocus;
    });
}


// let page = document.getElementById('buttonDiv');
// const kButtonColors = ['#3aa757', '#e8453c', '#f9bb2d', '#4688f1'];
//
// function constructOptions(kButtonColors) {
//     for (let item of kButtonColors) {
//         let button = document.createElement('button');
//         button.style.backgroundColor = item;
//         button.addEventListener('click', function() {
//             chrome.storage.sync.set({color: item}, function() {
//                 console.log('color is ' + item);
//             })
//         });
//         page.appendChild(button);
//     }
// }
//
// constructOptions(kButtonColors);