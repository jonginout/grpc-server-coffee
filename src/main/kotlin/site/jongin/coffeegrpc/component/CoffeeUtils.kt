package site.jongin.coffeegrpc.component

import org.springframework.stereotype.Component
import site.jongin.coffeegrpc.domain.Coffee
import site.jongin.coffeegrpc.domain.CoffeeRepository
import site.jongin.coffeegrpc.domain.CoffeeRequestBody
import site.jongin.coffeegrpc.proto.CoffeeRequest
import site.jongin.coffeegrpc.proto.CoffeeResponse
import site.jongin.coffeegrpc.proto.CoffeeStatus
import site.jongin.coffeegrpc.proto.CoffeeUpdateRequest

@Component
class CoffeeUtils(
    private val coffeeRepository: CoffeeRepository
) {

    fun getCoffeeAll(): MutableList<CoffeeResponse> {
        var coffees = this.coffeeRepository.findAll()

        var coffeeResponseList: MutableList<CoffeeResponse> = mutableListOf()
        coffees.forEach{ coffee ->
            coffeeResponseList.add(
                CoffeeResponse.newBuilder()
                    .setId(coffee.id!!)
                    .setMenu(coffee.menu)
                    .setPrice(coffee.price)
                    .setCoffeeStatus(coffee.coffeeStatus)
                    .build()
            )
        }

        return coffeeResponseList
    }

    fun getCoffee(id: Long): CoffeeResponse {
        val coffee: Coffee = this.coffeeRepository.getOne(id)
        return CoffeeResponse.newBuilder()
            .setId(id)
            .setCoffeeStatus(coffee.coffeeStatus)
            .setPrice(coffee.price)
            .setMenu(coffee.menu)
            .build()
    }

    fun addCoffee(coffeeRequest: CoffeeRequest): Long? {
        return this.coffeeRepository.save(
            Coffee(
                price = coffeeRequest.price,
                menu = coffeeRequest.menu,
                coffeeStatus = CoffeeStatus.ACTIVE
            )
        ).id
    }

    fun updateCoffee(coffeeUpdateRequest: CoffeeUpdateRequest): Long? {
        val coffee: Coffee = this.coffeeRepository.getOne(coffeeUpdateRequest.id)
        coffee.update(
            CoffeeRequestBody(
                status = coffeeUpdateRequest.coffeeStatus.name,
                price = coffeeUpdateRequest.price,
                menu = coffeeUpdateRequest.menu
            )
        )
        return this.coffeeRepository.save(coffee).id
    }
}