package nel.marco.parser.adder

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator
import nel.marco.parser.UpdateResourceRequest
import org.springframework.stereotype.Component
import org.yaml.snakeyaml.Yaml

@Component
class BigQueryAdder {

    val yaml = Yaml()

    private fun parse(yamlContent: String): List<MutableMap<String, Any>> {
        return yaml.loadAll(yamlContent).map { it as MutableMap<String, Any> }
    }

    fun addAccess(updateRequest: UpdateResourceRequest): List<MutableMap<String, Any>> {
        val fullMap = parse(updateRequest.yamlContent)

        val connections = fullMap
            .first { it["name"] == updateRequest.serviceName }
            .let { it["spec"] as MutableMap<*, *> }
            .let { it["permissions"] as MutableMap<String, Any> }
            .let {
                it[updateRequest.toBeAdded.bigQueryAccountName] = updateRequest.toBeAdded.bigQueryAccountName
                it
            }


        connections[updateRequest.toBeAdded.bigQueryAccountName] =
            mutableMapOf("roles" to listOf(updateRequest.toBeAdded.textToAdd))

        return fullMap
    }



}