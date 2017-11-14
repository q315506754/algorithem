// export var firstName = 'Michael';
// export var lastName = 'Jackson';
// export var year = 1958;

// export的写法，除了像上面这样，还有另外一种。
//
// // profile.js
var firstName = 'Michael'
var lastName = 'Jackson'
var year = 1958

function multiply (x, y) {
  return x * y
};

export {firstName, lastName, year}

export {multiply}

export {
    multiply as multiplyV1,
    multiply as multiplyV2
}
