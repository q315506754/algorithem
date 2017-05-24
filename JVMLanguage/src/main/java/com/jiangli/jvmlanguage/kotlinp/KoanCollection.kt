package com.jiangli.jvmlanguage.kotlinp

/**
 *
 *
 * @author Jiangli
 * @date 2017/5/23 13:16
 */
fun main(args: Array<String>) {

}

//////////////////////////////////////

// Return all products this customer has ordered
val Customer.orderedProducts: Set<Product> get() {
    return this.orders.flatMap { it.products }.toSet()
}

// Return all products that were ordered by at least one customer
val Shop.allOrderedProducts: Set<Product> get() {
    return this.customers.flatMap { it.orderedProducts }.toSet()
}

//////////////////////////////////////

val cityCusEq: (Customer ,City)->Boolean ={customer, city ->  city==customer.city}

// Return true if all customers are from the given city
fun Shop.checkAllCustomersAreFrom(city: City): Boolean = this.customers.all{cityCusEq(it,city)}

// Return true if there is at least one customer from the given city
fun Shop.hasCustomerFrom(city: City): Boolean = this.customers.any{cityCusEq(it,city)}

// Return the number of customers from the given city
fun Shop.countCustomersFrom(city: City): Int = this.customers.count{cityCusEq(it,city)}

// Return a customer who lives in the given city, or null if there is none
fun Shop.findAnyCustomerFrom(city: City): Customer? = this.customers.find{cityCusEq(it,city)}

//////////////////////////////////////

// Return the set of cities the customers are from
fun Shop.getCitiesCustomersAreFrom(): Set<City> = this.customers.map { it.city }.toSet()

// Return a list of the customers who live in the given city
fun Shop.getCustomersFrom(city: City): List<Customer> = this.customers.filter { it.city==city }

//////////////////////////////////////

fun Shop.getSetOfCustomers(): Set<Customer> =  this.customers.toSet()

//////////////////////////////////////
data class Shop(val name: String, val customers: List<Customer>)

data class Customer(val name: String, val city: City, val orders: List<Order>) {
    override fun toString() = "$name from ${city.name}"
}

data class Order(val products: List<Product>, val isDelivered: Boolean)

data class Product(val name: String, val price: Double) {
    override fun toString() = "'$name' for $price"
}

data class City(val name: String) {
    override fun toString() = name
}
