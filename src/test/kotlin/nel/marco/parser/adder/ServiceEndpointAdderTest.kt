package nel.marco.parser.adder

import nel.marco.parser.DataToBeAdded
import nel.marco.parser.ResourceType
import nel.marco.parser.UpdateResourceRequest
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class ServiceEndpointAdderTest {

    val service = ServiceEndpointAdder()

    @Test
    fun addAccess() {
        val request = UpdateResourceRequest(
            resourceType = ResourceType.SERVICE_ENDPOINT,
            serviceName = "service",
            toBeAdded = DataToBeAdded("application:custom", ""),
            yamlContent = inputR2D2Text()
        )
        val actual = service.addAccess(request)

        assertThat(actual).hasSize(2)

        val service = actual.find { it["name"] == request.serviceName }!!
        val spec = service["spec"] as MutableMap<*, *>
        val groups = spec["allow_connections_from"] as MutableMap<String, Any>
        assertThat(groups).hasSize(3)
        assertThat(groups).containsKeys("application:custom")
    }

    @Language("yaml")
    private fun inputR2D2Text() = """
                ---
                kind: Kubernetes/ServiceEndpoint
                name: service
                spec:
                  allow_connections_from:
                    "application:sdd-a": {}
                    "application:sdd-b": {}
                ---
                kind: Kubernetes/UserEndpoint
                name: user
                spec:
                  allowed_groups:
                    "AAD/SG_A_TEAM": {}
                    "AAD/SG_B_TEAM": {}
                """.trimIndent()

}