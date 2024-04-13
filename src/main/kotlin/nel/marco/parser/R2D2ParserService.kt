package nel.marco.parser

import org.slf4j.LoggerFactory
import org.springframework.core.io.Resource
import org.springframework.web.client.RestClient
import java.net.URI


class R2D2ParserService : ParseResourceService {

    private val log = LoggerFactory.getLogger(this::class.java)

    val restClient = RestClient.create()

    override fun retrieve(uri: URI): String {
        val resource = restClient.get()
            .uri(uri)
            .retrieve()
            .toEntity(String::class.java)

        log.info("retrieved [uri=$uri]")

        return resource.body
    }

    override fun parse(resource: Resource): Map<String, Any> {
        TODO("Not yet implemented")
    }

    override fun update(updateRequest: UpdateResourceRequest): Map<String, Any> {
        TODO("Not yet implemented")
    }

}