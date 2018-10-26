console.log('background.js loaded');

//manual action
chrome.browserAction.onClicked.addListener(cloneTab)

//shortcut action
chrome.commands.onCommand.addListener(function(command) {
    console.log('Command:', command);

    if (command == 'clone-fire') {
        console.log('clone-fire action');
        cloneTab();
    }
});


chrome.contextMenus.create({id:"open-tab",title:"open in new tab"
,contexts:['all']
}, function () {

});

chrome.contextMenus.onClicked.addListener(function (info,tab) {
    if (info.menuItemId == "open-tab") {
        console.log("open-tab...");
        console.log(info);

        if (info.linkUrl) {
            openTab(info.linkUrl)
        } else if(info.srcUrl) {
            openTab(info.srcUrl)
        }else if(info.selectionText) {
            openTab(httpurlformat(info.selectionText))
        }
    }
});

function cloneTab() {
    chrome.tabs.query({active: true, currentWindow: true}, function(tabs) {
        // chrome.tabs.executeScript(
        //     tabs[0].id,
        //     {code: 'document.body.style.backgroundColor = "' + color + '";'});
        // console.log('Command:'+'aaa');

        if(tabs && tabs[0] && tabs[0].url) {

            openTab(tabs[0].url);

        }

    });
}

function httpurlformat(str) {
    str = str.trim()


    if (!str.startsWith("http")) {
        str = "http://"+str
    }

    if (str.indexOf(".")<0) {
        str = "http://www."+str.substring(str.indexOf("://")+3)+".com"
    }

    return str
}

function openTab(urlp) {
    chrome.storage.sync.get({
        autoFocus: true
    }, function(items) {
        // document.getElementById('autoFocus').checked = items.autoFocus;

        chrome.tabs.create({active:items.autoFocus,url:urlp},function (tb) {

        });
    });
}

chrome.runtime.onInstalled.addListener(function() {
    chrome.storage.sync.set({color: '#3aa757'}, function() {
        console.log("The color is green.");
    });

    //满足条件激活 manifest page_action项
    // chrome.declarativeContent.onPageChanged.removeRules(undefined, function() {
    //     chrome.declarativeContent.onPageChanged.addRules([{
    //         conditions: [new chrome.declarativeContent.PageStateMatcher({
    //             pageUrl: {hostEquals: 'developer.chrome.com'},
    //         })
    //         ],
    //         actions: [function () {
    //             console.log('action performed');
    //         }]
    //     }]);
    // });
});