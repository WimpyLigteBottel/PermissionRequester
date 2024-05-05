package nel.marco.parser.adder

import nel.marco.parser.UpdateResourceRequest
import org.springframework.stereotype.Component
import org.yaml.snakeyaml.Yaml

@Component
class ServiceEndpointAdder {
    private val yaml = Yaml()

    fun parse(yamlContent: String): List<MutableMap<String, Any>> {
        return yaml.loadAll(yamlContent).map { it as MutableMap<String, Any> }
    }

    fun addAccess(updateRequest: UpdateResourceRequest): List<MutableMap<String, Any>> {
        val fullMap = parse(updateRequest.yamlContent)

        val connections = fullMap
            .first { it["name"] == updateRequest.serviceName }
            .let { it["spec"] as MutableMap<*, *> }
            .let { it["allow_connections_from"] as MutableMap<String, Any> }

        connections[updateRequest.toBeAdded.groupName] = emptyMap<String, Any>()

        return fullMap
    }
}