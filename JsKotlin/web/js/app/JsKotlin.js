if (typeof kotlin === 'undefined') {
  throw new Error("Error loading module 'JsKotlin'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'JsKotlin'.");
}
var JsKotlin = function (_, Kotlin) {
  'use strict';
  var println = Kotlin.kotlin.io.println_s8jyv4$;
  function main(args) {
    println('Hello ggggg world!!!!````');
  }
  _.main_kand9s$ = main;
  Kotlin.defineModule('JsKotlin', _);
  main([]);
  return _;
}(typeof JsKotlin === 'undefined' ? {} : JsKotlin, kotlin);