function rndNum(min,max) {
    return Math.floor(Math.random()*(max-min+1)+min);
}

function rndOne(varargs) {
    return arguments[rndNum(0,arguments.length-1)];
}
