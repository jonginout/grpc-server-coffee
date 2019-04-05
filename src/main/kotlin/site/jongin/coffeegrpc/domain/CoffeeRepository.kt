package site.jongin.coffeegrpc.domain

import org.springframework.data.jpa.repository.JpaRepository

interface CoffeeRepository : JpaRepository<Coffee, Long>
