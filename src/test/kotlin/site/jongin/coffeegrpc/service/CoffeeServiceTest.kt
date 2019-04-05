package site.jongin.coffeegrpc.service

import com.google.protobuf.Empty
import io.grpc.ManagedChannelBuilder
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import site.jongin.coffeegrpc.domain.Coffee
import site.jongin.coffeegrpc.proto.CoffeeGrpc
import site.jongin.coffeegrpc.proto.CoffeeRequest
import site.jongin.coffeegrpc.proto.CoffeeStatus
import site.jongin.coffeegrpc.proto.CoffeeUpdateRequest
import site.jongin.coffeegrpc.proto.CoffeeeSearchRequest

@RunWith(SpringRunner::class)
@SpringBootTest
class CoffeeServiceTest {
    private var grpcPort: Int = 5959
    private val channel = ManagedChannelBuilder
        .forAddress("localhost", this.grpcPort)
        .usePlaintext()
        .build()

    @Test
    fun test_get_coffee_list_OK() {
        val request = Empty.newBuilder().build()

        val stub = CoffeeGrpc.newBlockingStub(this.channel)

        val response = stub.getCoffeeAll(request)

        response.coffeeResponseList.forEach{
            println(it)
        }
    }

    @Test
    fun test_get_coffee_OK() {
        val request = CoffeeeSearchRequest.newBuilder()
            .setId(1)
            .build()

        val stub = CoffeeGrpc.newBlockingStub(this.channel)

        val response = stub.getCoffee(request)

        println(response)
    }

    @Test
    fun test_save_coffee_OK() {
        val request = CoffeeRequest.newBuilder()
            .setPrice(1000)
            .setMenu("TEST_COFFEE")
            .build()

        val stub = CoffeeGrpc.newBlockingStub(this.channel)

        val response = stub.addCoffee(request)

        assertThat(response.isSuccess).isTrue()
        assertThat(response.id).isNotNull()
    }

    @Test
    fun test_update_coffee_OK() {
        val request = CoffeeUpdateRequest.newBuilder()
            .setId(6)
            .setPrice(1000)
            .setMenu("TEST_UPDATE_COFFEE!")
            .setCoffeeStatus(CoffeeStatus.INACTIVE)
            .build()

        val stub = CoffeeGrpc.newBlockingStub(this.channel)

        val response = stub.updateCoffee(request)

        assertThat(response.isSuccess).isTrue()
        assertThat(response.id).isNotNull()
    }
}
