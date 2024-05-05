package nel.marco.parser.adder

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator
import nel.marco.parser.DataToBeAdded
import nel.marco.parser.ResourceType
import nel.marco.parser.UpdateResourceRequest
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test

class BigQueryAdderTest {
    val bigQueryAdder = BigQueryAdder()

    @Test
    fun addAccess() {
        val request = UpdateResourceRequest(
            resourceType = ResourceType.BIGQUERY_TABLE,
            serviceName = "bigquery",
            toBeAdded = DataToBeAdded(
                textToAdd = "organizations/366839382793/roles/bigquery.dataReader",
                bigQueryAccountName = "serviceAccount:custom-name"
            ),
            yamlContent = inputR2D2Text()
        )

        val actual = bigQueryAdder.addAccess(request)

        assertThat(actual).hasSize(3)

        val service = actual.find { it["name"] == request.serviceName }!!

        assertThat(writeAsYaml(service)).containsIgnoringWhitespaces("""
                kind: BigQuery/Dataset
                name: bigquery
                spec:
                  permissions:
                    serviceAccount:account-name:
                      roles:
                        - read
                        - write
                    serviceAccount:custom-name:
                      roles:
                        - organizations/366839382793/roles/bigquery.dataReader""".trimIndent())

    }

    private fun writeAsYaml(input: Any): String {
        return ObjectMapper(
            YAMLFactory()
                .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)
                .disable(YAMLGenerator.Feature.SPLIT_LINES)
                .enable(YAMLGenerator.Feature.MINIMIZE_QUOTES)
        ).writeValueAsString(input)
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
                kind: BigQuery/Dataset
                name: bigquery
                spec:
                  permissions:
                    "serviceAccount:account-name":
                      roles:
                        - read
                        - write
                ---
                kind: Kubernetes/UserEndpoint
                name: user
                spec:
                  allowed_groups:
                    "AAD/SG_A_TEAM": {}
                    "AAD/SG_B_TEAM": {}
                """.trimIndent()


}