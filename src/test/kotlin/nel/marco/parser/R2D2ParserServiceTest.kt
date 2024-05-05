package nel.marco.parser

import nel.marco.parser.adder.BigQueryAdder
import nel.marco.parser.adder.ServiceEndpointAdder
import nel.marco.parser.adder.UserEndpointAdder
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class R2D2ParserServiceTest {

    val bigQueryAdder = mock<BigQueryAdder>()
    val serviceEndpointAdder = mock<ServiceEndpointAdder>()
    val userEndpointAdder = mock<UserEndpointAdder>()

    val service = R2D2ParserService(
        bigQueryAdder = bigQueryAdder,
        serviceEndpointAdder = serviceEndpointAdder,
        userEndpointAdder = userEndpointAdder
    )

    @Test
    fun `user added called`() {
        val request = UpdateResourceRequest(
            resourceType = ResourceType.USER_ENDPOINT,
            serviceName = "user",
            toBeAdded = DataToBeAdded("AAD/SG_CUSTOM_TEAM", ""),
            yamlContent = ""
        )

        service.update(request)

        verify(userEndpointAdder).addAccess(request)
    }

    @Test
    fun `bigquery added called`() {
        val request = UpdateResourceRequest(
            resourceType = ResourceType.BIGQUERY_TABLE,
            serviceName = "user",
            toBeAdded = DataToBeAdded("organizations/366839382793/roles/bigquery.dataReader", ""),
            yamlContent = ""
        )

        service.update(request)

        verify(bigQueryAdder).addAccess(request)
    }


    @Test
    fun `able to update service endpoint`() {
        val request = UpdateResourceRequest(
            resourceType = ResourceType.SERVICE_ENDPOINT,
            serviceName = "service",
            toBeAdded = DataToBeAdded("application:custom", ""),
            yamlContent = ""
        )

        service.update(request)

        verify(serviceEndpointAdder).addAccess(request)
    }


}