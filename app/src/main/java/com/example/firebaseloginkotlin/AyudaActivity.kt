package com.example.firebaseloginkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AyudaActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var articleAdapter: ArticleAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ayuda)


        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.isNestedScrollingEnabled = false
        articleAdapter = ArticleAdapter(getSampleArticles()) // Obtén la lista de artículos

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = articleAdapter
    }

        private fun getSampleArticles(): List<Article> {
            // Aquí puedes crear y retornar una lista de objetos Article con información sobre los artículos
            val articles = mutableListOf<Article>()
            articles.add(Article("Practicar el autocuidado", "Dedicar tiempo regularmente a actividades que nos brinden placer y alivien el estrés es esencial. Esto puede incluir ejercicios físicos, meditación, hobbies, pasar tiempo con seres queridos o simplemente descansar.\n" +
                    "\n" +
                    "Establecer límites saludables: Aprender a decir \"no\" cuando nos sentimos abrumados y establecer límites en nuestra vida personal y laboral es vital para mantener un equilibrio saludable.", "Autor del artículo 1", "Fecha del artículo 1"))
            articles.add(Article("Desconectarse de la tecnología", "Si bien la tecnología tiene sus beneficios, también puede ser una fuente de estrés. Establecer períodos regulares de desconexión de dispositivos electrónicos puede ayudarte a reducir la ansiedad y mejorar tu salud mental.", "Autor del artículo 2","Fecha del artículo 2"))

            articles.add(Article("Promover el sueño de calidad", "El sueño adecuado es fundamental para nuestro bienestar físico y mental. Este artículo proporciona consejos y estrategias para mejorar la calidad del sueño, como mantener una rutina de sueño regular, crear un ambiente propicio para dormir y practicar técnicas de relajación antes de acostarse.", "Autor del artículo 2","Fecha del artículo 2"))
            articles.add(Article("Prácticas de mindfulness para reducir el estrés", "El mindfulness es una técnica efectiva para reducir el estrés y cultivar la atención plena en el presente. Este artículo explora diferentes prácticas de mindfulness, como la meditación y la atención plena en las actividades diarias, y cómo pueden beneficiar la salud mental..", "Autor del artículo 2","Fecha del artículo 2"))

            // Agrega más artículos según sea necesario
            // Agrega más artículos según sea necesario

            return articles
    }
}



/*codigo nuevo
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AyudaActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ayuda_activity)

        recyclerView = findViewById(R.id.recyclerView)
        articleAdapter = ArticleAdapter(getSampleArticles()) // Obtén la lista de artículos

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = articleAdapter
    }

    private fun getSampleArticles(): List<Article> {
        // Aquí puedes crear y retornar una lista de objetos Article con información sobre los artículos
        val articles = mutableListOf<Article>()
        articles.add(Article("Título del artículo 1", "Contenido del artículo 1"))
        articles.add(Article("Título del artículo 2", "Contenido del artículo 2"))
        // Agrega más artículos según sea necesario

        return articles
    }
}
*/



