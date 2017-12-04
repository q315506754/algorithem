// Load the full build.
var _ = require('lodash');
// Load the core build.
// var _ = require('lodash/core');
// // Load the FP build for immutable auto-curried iteratee-first data-last methods.
// var fp = require('lodash/fp');
//
// // Load method categories.
// var array = require('lodash/array');
// var object = require('lodash/fp/object');
//
// // Cherry-pick methods for smaller browserify/rollup/webpack bundles.
// var at = require('lodash/at');
// var curryN = require('lodash/fp/curryN');

//https://lodash.com/docs/4.17.4
console.log('“Array” Methods');

//_.chunk(array, [size=1])
var strings = ['a', 'b', 'c', 'd'];
console.log(_.chunk(strings, 2));
console.log(strings);//['a', 'b', 'c', 'd'] 并不影响原数组