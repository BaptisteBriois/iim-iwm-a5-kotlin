package fr.iim.iwm.a5.kotlin

import io.ktor.freemarker.FreeMarkerContent
import io.ktor.http.HttpStatusCode

class ArticleController(private val model: Model) {
    fun startFM(id: Int): Any {
        val article = model.getArticle(id)

        // Response FMC
        if (article != null)
            return FreeMarkerContent("article.ftl",mapOf("article" to article))
        return HttpStatusCode.NotFound
    }
}