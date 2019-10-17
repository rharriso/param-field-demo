package com.rharriso

import org.apache.log4j.PropertyConfigurator
import org.slf4j.LoggerFactory
import spark.Spark
import java.lang.Exception
import java.util.*

class GraphqlService {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
        private val allowedHeaders = listOf("Content-Type", "Authorization", "X-Guest-UUID", "X-Alias-UUID,Accept")

        // Launch with allow origin when using locally
        private val allowedOriginRegex = true

        @JvmStatic
        fun main(args: Array<String>) {
            initLogger()
            Spark.port(5000)
            defineRoutes()
        }

        private fun initLogger() {
            val propFileContent = GraphqlService::class.java.getResourceAsStream("/log4j.properties")
            val p = Properties()
            p.load(propFileContent)
            PropertyConfigurator.configure(p)
        }

        private fun defineRoutes() {
            Spark.initExceptionHandler { e ->
                log.error("Spark init failure", e)
                System.exit(100)
            }

            Spark.get("/hello") { _, _ -> "Hello, World" }
            Spark.get("/time") { _, _ -> Date().toString() }

            val graphQLHandler = GraphQLHandler()
            Spark.post("/graphql") { request, response ->
                try {
                    graphQLHandler.handle(request, response)
                } catch (e: Exception) {
                    log.error(e.message)
                    response.status(500)
                    response.body("Server Error")
                }
            }

            Spark.options("*") { _, _ -> "ok" }
            Spark.options("/graphql") { _, _ -> "ok" }

            Spark.before("*") { request, response ->
                val requestOrigin = request.headers("Origin")
                if (allowedOriginRegex) {
                    response.header("Access-Control-Allow-Origin", requestOrigin)
                    response.header("Access-Control-Allow-Methods", "GET,POST,OPTIONS")
                    response.header("Access-Control-Allow-Headers", allowedHeaders.joinToString(","))
                }
            }
        }
    }
}