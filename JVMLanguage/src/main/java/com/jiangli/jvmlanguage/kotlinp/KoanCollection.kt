package com.jiangli.jvmlanguage.kotlinp

/**
 *
 *
 * @author Jiangli
 * @date 2017/5/23 13:16
 */
fun main(args: Array<String>) {
    println(listOf(1,42,33).max())
}

//////////////////////////////////////
//////////////////////////////////////

// Return the set of products that were ordered by every customer
fun Shop.getSetOfProductsOrderedByEveryCustomer(): Set<Product>
//    this.customers.flatMap { it.orders }.flatMap { it.products }.
//            filter {
//        eachProduct->
//        this.customers.all { it.orders.flatMap { it.products }.contains(eachProduct) }
//    }.toSet()
        {
            val allProducts=this.customers.flatMap { it.orders }.flatMap { it.products }

            val initial = setOf<Product>()

            allProducts.fold(initial,{
                sss, ppp ->
                if(this.customers.all { it.orders.flatMap { it.products }.contains(ppp) })
                    sss.plus(ppp)

                sss
            })

            return initial
        }

//        this.customers.flatMap { it.orders }.flatMap { it.products }.fold(setOf<Product>(),
//                {
//                    sss, ppp ->
//                    if(this.customers.all { it.orders.flatMap { it.products }.contains(ppp) })
//                        sss.plus(ppp)
//                    sss
//                }
//)

//////////////////////////////////////

// Return customers who have more undelivered orders than delivered
// (positive, negative)
fun Shop.getCustomersWithMoreUndeliveredOrdersThanDelivered(): Set<Customer> = this.customers.partition { it.orders.filter {!it.isDelivered}.size > it.orders.filter {it.isDelivered}.size  }.first.toSet()

//////////////////////////////////////

// Return a map of the customers living in each city
fun Shop.groupCustomersByCity(): Map<City, List<Customer>> = this.customers.groupBy {it.city }

//////////////////////////////////////

// Return the sum of prices of all products that a customer has ordered.
// Note: the customer may order the same product for several times.
fun Customer.getTotalOrderPrice(): Double = this.orders.flatMap { it.products }.sumByDouble { it.price }

//////////////////////////////////////

// Return a list of customers, sorted by the ascending number of orders they made
fun Shop.getCustomersSortedByNumberOfOrders(): List<Customer> = this.customers.sortedBy {it.orders.size}

//////////////////////////////////////

// Return a customer whose order count is the highest among all customers
fun Shop.getCustomerWithMaximumNumberOfOrders(): Customer? = this.customers.maxBy { it.orders.size }

// Return the most expensive product which has been ordered
fun Customer.getMostExpensiveOrderedProduct(): Product? = this.orders.flatMap { it.products }.maxBy { it.price }

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
