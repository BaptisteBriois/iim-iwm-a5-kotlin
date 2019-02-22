package fr.iim.iwm.a5.kotlin

class MysqlModel(url: String, user: String?, password: String?) : Model {
    val connectionPool = ConnectionPool(url, user, password)

    override fun getArticlesList(): List<Article> {
        val articles = ArrayList<Article>()

        connectionPool.use { connection ->
            connection.prepareStatement("SELECT * FROM articles").use { stmt ->
                val results = stmt.executeQuery()

                while(results.next()){
                    articles.add(Article(results.getInt("id"), results.getString("title")))
                }

                /* val str = buildString {
                    while (results.next()) {
                        val id = results.getInt("id")
                        val title = results.getString("title")
                        append("<p><a href='/article/$id'>$title</a></p>")
                    }
                }*/
            }
        }

        return articles
    }

    override fun getArticle(id: Int): Article? {
        connectionPool.use { connection ->

            connection.prepareStatement("SELECT * FROM articles WHERE id = ?").use { stmt ->
                stmt.setInt(1, id)
                val results = stmt.executeQuery()
                val found = results.next()

                if (found) {
                    return Article(
                        results.getInt("id"),
                        results.getString("title"),
                        results.getString("text")
                    )
                }
            }
        }

        return null
    }
}