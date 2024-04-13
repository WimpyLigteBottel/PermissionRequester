package nel.marco.parser

import org.springframework.core.io.Resource
import java.net.URI


data class UpdateResourceRequest(
    val resourceType:ResourceType,
    val toBeAddedString: String
)

enum class ResourceType {
    USER_ENDPOINT,
    SERVICE_ENDPOINT,
    BIGQUERY_TABLE
}

interface ParseResourceService {
    fun retrieve(uri: URI): String
    fun parse(resource: Resource): Map<String, Any>
    fun update(
        updateRequest: UpdateResourceRequest,
    ): Map<String, Any>
}