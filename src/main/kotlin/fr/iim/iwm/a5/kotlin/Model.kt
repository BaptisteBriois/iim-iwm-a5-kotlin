package fr.iim.iwm.a5.kotlin

interface Model {
    fun getArticlesList(): List<Article>
    fun getArticle(id: Int): Article?
}