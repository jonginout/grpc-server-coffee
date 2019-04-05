package site.jongin.coffeegrpc.domain

import site.jongin.coffeegrpc.proto.CoffeeStatus
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Table

@Entity
@Table(name = "coffees")
class Coffee(
    coffeeStatus: CoffeeStatus,
    price: Int,
    menu: String
) : BaseEntity() {

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    var coffeeStatus = coffeeStatus
        private set
    var price = price
        private set
    var menu = menu
        private set

    fun update(body: CoffeeRequestBody) {
        this.coffeeStatus = CoffeeStatus.valueOf(body.status)
        this.price = body.price
        this.menu = body.menu
    }
}
