var hello = require('../src/part')['default'];
var expect = require("chai").expect;//npm install chai
//Babel默认不会对Iterator、Generator、Promise、Map、Set等全局对象，
// 以及一些全局对象的方法（比如Object.assign）转码。如果你想要对这些对象转码，就要安装babel-polyfill。
//npm install --global  mocha
//npm install babel-polyfill --save
//npm install babel-core babel-preset-es2015 --save-dev
// mocha --compilers js:babel-core/register
// console.log(hello);
// mocha --reporter tap


describe("part.js测试",function () {
    it("默认方法",function () {
        expect(hello('aaaa')).to.be.equal('hi~ aaaa');

        var Foo = function () {
        };
        var foo = new Foo();
        foo.bar = 'baz';

        // 相等或不相等
        expect(4 + 5).to.be.equal(9);
        expect(4 + 5).to.be.not.equal(10);
        expect(foo).to.be.deep.equal({ bar: 'baz' });

// 布尔值为true
        expect('everthing').to.be.ok;
        expect(false).to.not.be.ok;

// typeof
        expect('test').to.be.a('string');
        expect({ foo: 'bar' }).to.be.an('object');
        expect(foo).to.be.an.instanceof(Foo);

// include
        expect([1,2,3]).to.include(2);
        expect('foobar').to.contain('foo');
        expect({ foo: 'bar', hello: 'universe' }).to.include.keys('foo');

// empty
        expect([]).to.be.empty;
        expect('').to.be.empty;
        expect({}).to.be.empty;

// match
        expect('foobar').to.match(/^foo/);
    });


});
