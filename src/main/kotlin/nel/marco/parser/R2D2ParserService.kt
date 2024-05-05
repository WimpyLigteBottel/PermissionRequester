package nel.marco.parser

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator
import nel.marco.parser.adder.BigQueryAdder
import nel.marco.parser.adder.ServiceEndpointAdder
import nel.marco.parser.adder.UserEndpointAdder
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import java.net.URI

@Service
class R2D2ParserService(
    private val bigQueryAdder: BigQueryAdder,
    private val serviceEndpointAdder: ServiceEndpointAdder,
    private val userEndpointAdder: UserEndpointAdder,
) : ParseResourceService {

    private val log = LoggerFactory.getLogger(this::class.java)

    val restClient = RestClient.create()


    fun retrieve(uri: URI): String {
        val resource = restClient.get()
            .uri(uri)
            .retrieve()
            .toEntity(String::class.java)

        log.info("retrieved [uri=$uri]")

        return resource.body ?: ""
    }


    override fun update(updateRequest: UpdateResourceRequest): String {
        val fullMap = when (updateRequest.resourceType) {
            ResourceType.BIGQUERY_TABLE -> bigQueryAdder.addAccess(updateRequest)
            ResourceType.SERVICE_ENDPOINT -> serviceEndpointAdder.addAccess(updateRequest)
            ResourceType.USER_ENDPOINT -> userEndpointAdder.addAccess(updateRequest)
        }

        val yamlR2D2 = ObjectMapper(
            YAMLFactory()
                .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)
                .disable(YAMLGenerator.Feature.SPLIT_LINES)
                .enable(YAMLGenerator.Feature.MINIMIZE_QUOTES)
        )

        return fullMap
            .map { yamlR2D2.writeValueAsString(it) }
            .distinct()
            .fold("%YAML 1.1") { acc, s -> acc + "\n---\n" + s }
    }

}