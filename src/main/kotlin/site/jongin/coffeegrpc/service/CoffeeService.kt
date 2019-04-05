package site.jongin.coffeegrpc.service


import com.google.protobuf.Empty
import io.grpc.Status
import io.grpc.stub.StreamObserver
import org.lognet.springboot.grpc.GRpcService
import site.jongin.coffeegrpc.component.CoffeeUtils
import site.jongin.coffeegrpc.proto.CoffeeGrpc
import site.jongin.coffeegrpc.proto.CoffeeRequest
import site.jongin.coffeegrpc.proto.CoffeeResponse
import site.jongin.coffeegrpc.proto.CoffeeResultResponse
import site.jongin.coffeegrpc.proto.CoffeeUpdateRequest
import site.jongin.coffeegrpc.proto.CoffeeeSearchRequest
import site.jongin.coffeegrpc.proto.CoffeesListResponse

@GRpcService
class CoffeeService(
    private val coffeeUtils: CoffeeUtils
) : CoffeeGrpc.CoffeeImplBase() {

    override fun getCoffeeAll(request: Empty, responseObserver: StreamObserver<CoffeesListResponse>) {
        val coffees = this.coffeeUtils.getCoffeeAll()

        val response = CoffeesListResponse.newBuilder()
            .addAllCoffeeResponse(coffees)
            .build()

        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }

    override fun getCoffee(request: CoffeeeSearchRequest, responseObserver: StreamObserver<CoffeeResponse>) {
        if (request.id < 1) {
            responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("요청 오류").asException())
            return
        }
        val coffee = this.coffeeUtils.getCoffee(request.id)

        responseObserver.onNext(coffee)
        responseObserver.onCompleted()
    }

    override fun addCoffee(request: CoffeeRequest, responseObserver: StreamObserver<CoffeeResultResponse>) {
        if(!this.validateRequest(request)) {
            responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("요청 오류").asException())
            return
        }

        val id = this.coffeeUtils.addCoffee(request)
        if (id == null) {
            responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("저장 실패").asException())
            return
        }

        val response = CoffeeResultResponse.newBuilder()
            .setIsSuccess(true)
            .setId(id)
            .build()

        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }

    override fun updateCoffee(request: CoffeeUpdateRequest, responseObserver: StreamObserver<CoffeeResultResponse>) {
        val id = this.coffeeUtils.updateCoffee(request)
        if (id == null) {
            responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("수정 실패").asException())
            return
        }

        val response = CoffeeResultResponse.newBuilder()
            .setIsSuccess(true)
            .setId(id)
            .build()

        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }

    private fun validateRequest(request: CoffeeRequest): Boolean {
        return !request.menu.isNullOrBlank() && request.price > 0
    }
}
