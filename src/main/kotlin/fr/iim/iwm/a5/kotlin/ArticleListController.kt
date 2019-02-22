package fr.iim.iwm.a5.kotlin

import io.ktor.freemarker.FreeMarkerContent

class ArticleListController(private val model: Model) {
    fun startFM(): FreeMarkerContent {
        val articles = model.getArticlesList()

        // Response FMC
        return FreeMarkerContent("index.ftl", IndexData(articles))
    }
}