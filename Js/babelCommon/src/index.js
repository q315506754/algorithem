import hi,{HELLO} from './part'

hi();
console.log(`constant:${HELLO}`);


export default function () {
    console.log('this is a function from index module...');
}