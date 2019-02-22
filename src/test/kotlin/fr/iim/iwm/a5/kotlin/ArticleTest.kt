package fr.iim.iwm.a5.kotlin

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import fr.iim.iwm.a5.kotlin.Article
import fr.iim.iwm.a5.kotlin.ArticleController
import fr.iim.iwm.a5.kotlin.Model
import fr.iim.iwm.a5.kotlin.MysqlModel
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.html.HtmlContent
import io.ktor.http.HttpStatusCode
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

fun mrize(name: String) = "Mr $name"

fun calculateAnswer() = 42

class FakeModel : Model {
    override fun getArticlesList(): List<Article> = TODO() /*{
        return listOf(Article(42, "titre", "text"))
    }*/

    override fun getArticle(id: Int): Article? {
        return if (id == 42)
            Article(42, "titre", "text")
        else
            null
    }
}

class ArticleTest {
    /*@Test
    fun testFoo() {
        assertEquals("Mr Baptiste", mrize("Baptiste"))
    }

    @Test
    fun testAnswer() {
        assertEquals(42, calculateAnswer())
    }*/

    @Test
    fun testArticleFound() {
        val model = mock<Model> {
            on { getArticle(42) } doReturn Article(42, "titre", "text")
        }

        val controller = ArticleController(model)

        val result = controller.startFM(42)

        assertTrue(result is FreeMarkerContent)
    }

    @Test
    fun testArticleNotFound() {
        val controller = ArticleController(FakeModel())

        val result = controller.startFM(21)

        assertEquals(HttpStatusCode.NotFound, result)
    }
}