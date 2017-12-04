export var firstName = 'Michael';
export var lastName = 'Jackson';
export var year = 1958;
export function multiply(x, y) {
    return x * y;
};

// 正确
export function f() {};

// 正确
function f2() { console.log('f2 invoked');}
export {f2};

export var foo = 'bar';
setTimeout(() => foo = 'baz', 500);