import hi,{HELLO} from './part'
import _ from 'lodash'

hi();
console.log(`constant:${HELLO}`);


export default function () {
    console.log('this is a function from index module...');
    var strings = ['a', 'b', 'c', 'd'];
    console.log(_.chunk(strings, 2));
}