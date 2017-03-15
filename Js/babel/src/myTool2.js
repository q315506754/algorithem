class Animal2 {
    constructor(){
        this.type = 'animal'
    }
    says(say){
        setTimeout( () => {
            console.log("Animal2:"+this.type + ' says ' + say)
    }, 1000)
    }
}
var animal = new Animal2()