package rutz.de.learnjap.android

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.TextView
import rutz.de.learnjap.ENUMS.Modus
import rutz.de.learnjap.R
import rutz.de.learnjap.japanesevocab.InfoObject
import rutz.de.learnjap.japanesevocab.Kanji

class Dictionary : AppCompatActivity() {

    private val TAG = "Dictionary"

    private var modus: Modus? = null
    private var infoObject: InfoObject? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setTitle("Dictionary")
        modus = intent.extras.get("modus") as Modus
        setInfo()
    }

    private fun setInfo() {
        when (modus) {
            Modus.KANJIMEANING -> setInfoAsKanji()
            Modus.MEANINGKANJI -> setInfoAsKanji()
        }
    }

    private fun setInfoAsKanji() {
        setContentView(R.layout.kanji_info)
        infoObject = intent.extras.get("item") as InfoObject
        var kanji: Kanji = infoObject as Kanji
        (findViewById(R.id.infoCharacter) as TextView).text = kanji.character
        var meanings = kanji.meaning
        var meaning: String = ""
        meanings!!.forEach { m -> meaning += "$m, " }
        meaning=meaning.dropLast(2)
        (findViewById(R.id.infoMeaning) as TextView).text = meaning
        var onyumis = kanji.onyomi
        var onyumi: String = ""
        onyumis!!.forEach { o -> onyumi += "$o, " }
        onyumi=onyumi.dropLast(2)
        (findViewById(R.id.infoOn) as TextView).text = onyumi
        var kunyomis = kanji.kunyomi
        var kunyumi: String = ""
        kunyomis!!.forEach { k -> kunyumi += "$k, " }
        kunyumi=kunyumi.dropLast(2)
        (findViewById(R.id.infoKun) as TextView).text = kunyumi
        (findViewById(R.id.infoJLPT) as TextView).text = kanji.jlptLevel.toString()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }


}
