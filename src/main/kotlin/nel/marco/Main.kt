package nel.marco

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
open class Main

fun main(args: Array<String>) {
    runApplication<Main>(*args)
}


data class AccessRequest(
    val accessType: AccessType,
    val targetUri: String,
    val credentialToAdd: String,
    val reason: String
)

enum class AccessType {
    READ,
    WRITE,
    ADMIN
}

interface RequestPermission {
    fun generateRequest(accessRequest: AccessRequest)
    fun sendRequest(accessRequest: AccessRequest)
}

class RequestAccessUserEndpoint : RequestPermission {
    override fun generateRequest(accessRequest: AccessRequest) {
        TODO("Not yet implemented")
    }

    override fun sendRequest(accessRequest: AccessRequest) {
        TODO("Not yet implemented")
    }
}