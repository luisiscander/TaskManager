package com.taskmanager

import com.taskmanager.di.appModules
import com.taskmanager.presentation.routes.taskRoutes
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.callloging.CallLogging
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.request.path
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import kotlinx.serialization.json.Json
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import org.slf4j.event.Level

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureKoin()
        configurePlugins()
        configureRouting()
    }.start(wait = true)
}

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(appModules)
    }
}

fun Application.configurePlugins() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }

    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }

    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respond(
                HttpStatusCode.InternalServerError,
                mapOf("error" to "Error interno del servidor: ${cause.message}")
            )
        }
    }
}

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respond(
                HttpStatusCode.OK,
                mapOf(
                    "status" to "running",
                    "service" to "Task Manager API",
                    "version" to "1.0.0",
                    "di" to "Koin"
                )
            )
        }

        taskRoutes()
    }
}
