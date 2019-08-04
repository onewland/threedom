package com.oliverco

import com.google.gson.Gson
import io.ktor.application.*
import io.ktor.features.CORS
import io.ktor.features.CallLogging
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.util.KtorExperimentalAPI
import org.slf4j.event.Level

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@KtorExperimentalAPI
@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    val threeGramDao = ThreeGramDao(environment.config.property("ktor.database.url").getString(),
        environment.config.property("ktor.database.user").getString(),
        environment.config.property("ktor.database.password").getString())
    var position = Integer.parseInt(environment.config.propertyOrNull("ktor.database.position")?.getString())
    val gson = Gson()

    install(CORS) {
        anyHost()
    }
    install(CallLogging) {
        level = Level.INFO
    }

    routing {
        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }

        get("/ngram") {
            val threeGram = threeGramDao.getThreeGram(position)
            position += 1
            call.respondText(gson.toJson(threeGram), contentType = ContentType.Application.Json)
        }

        // Static feature. Try to access `/static/ktor_logo.svg`
        static("/static") {
            resources("static")
        }
    }
}

