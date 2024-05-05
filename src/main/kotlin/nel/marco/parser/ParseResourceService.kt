package nel.marco.parser

import java.net.URI

data class DataToBeAdded(
    val groupName: String = "",
    val serviceName: String = "",
    val bigQueryAccountName: String = "",
    val textToAdd: String = ""
)

data class UpdateResourceRequest(
    val resourceType: ResourceType,
    val serviceName: String,
    val toBeAdded: DataToBeAdded,
    val yamlContent: String,
)

enum class ResourceType {
    USER_ENDPOINT,
    SERVICE_ENDPOINT,
    BIGQUERY_TABLE
}

interface ParseResourceService {
    fun update(updateRequest: UpdateResourceRequest): String
}