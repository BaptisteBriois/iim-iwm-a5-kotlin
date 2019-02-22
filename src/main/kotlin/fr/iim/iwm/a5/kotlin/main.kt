package fr.iim.iwm.a5.kotlin

import freemarker.cache.ClassTemplateLoader
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.freemarker.FreeMarker
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

class App

fun main() {

    val model = MysqlModel("jdbc:mysql://localhost:3306/cms", "root", "root")

    val articleListController = ArticleListController(model)
    val articleController = ArticleController(model)

    embeddedServer(Netty, 8080) {
        install(FreeMarker) {
            templateLoader = ClassTemplateLoader(App::class.java.classLoader, "templates")
        }

        routing {
            get("/") {
                val content = articleListController.startFM()
                call.respond(content)
            }

            get("/articles/{id}") {
                val id = call.parameters["id"] !! . toInt()

                val content = articleController.startFM(id)
                call.respond(content)
            }
        }
    }.start(true)
}
