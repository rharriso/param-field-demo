package com.rharriso

import com.expediagroup.graphql.SchemaGeneratorConfig
import com.expediagroup.graphql.TopLevelObject
import com.expediagroup.graphql.toSchema
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import graphql.ExecutionInput
import graphql.ExecutionResult
import graphql.GraphQL
import spark.Request
import spark.Response
import java.io.IOException
import javax.xml.ws.http.HTTPException

class GraphQLHandler {
    companion object {
        private val config = SchemaGeneratorConfig(supportedPackages = listOf("nyc.rowan"))
        private val queries = listOf(
                TopLevelObject(HelloQueryService())
        )

        private val mutations: List<TopLevelObject> = listOf()

        private val graphQLSchema = toSchema(config, queries, mutations)
        val graphQL = GraphQL.newGraphQL(graphQLSchema).build()!!
    }

    private val mapper = jacksonObjectMapper()

    /**
     * Get payload from the request.
     */
    private fun getPayload(request: Request): Map<String, Any>? {
        return try {
            mapper.readValue<Map<String, Any>>(request.body())
        } catch (e: IOException) {
            // TODO: Should this throw here?
            throw HTTPException(500)
        }
    }

    /**
     * Get any variables passed in the request.
     */
    private fun getVariables(payload: Map<String, *>) =
            payload.getOrElse("variables") { emptyMap<String, Any>() } as Map<String, Any>


    /**
     * Find current user by authentication header, or assign null
     * to current user
     */
    private fun getContext(request: Request) = DummyContext()

    class DummyContext {}

    /**
     * Get any errors or data from [executionResult].
     */
    private fun getResult(executionResult: ExecutionResult): MutableMap<String, Any> {
        val result = mutableMapOf<String, Any>()
        when {
            executionResult.errors.isNotEmpty() ->
                result["errors"] = executionResult.errors
            else ->
                result["data"] = executionResult.getData()
        }
        return result
    }

    /**
     * Execute query against schema
     */
    fun handle(request: Request, response: Response): String? {
        val payload = getPayload(request)

        return payload?.let {
            // Execute query and get data or any errors
            val executionResult = graphQL.executeAsync(
                    ExecutionInput.Builder()
                            .query(payload["query"].toString())
                            .variables(getVariables(payload))
                            .context(getContext(request))
            ).get()
            val result = getResult(executionResult)

            response.type("application/json")
            return@let mapper.writeValueAsString(result)
        }
    }
}
