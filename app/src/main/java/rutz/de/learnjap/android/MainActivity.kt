package rutz.de.learnjap.android

import android.nfc.Tag
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.util.Log
import org.xmlpull.v1.XmlPullParser
import rutz.de.learnjap.R
import rutz.de.learnjap.japanesevocab.JapaneseVocab
import rutz.de.learnjap.japanesevocab.Kanji

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private var mViewPager: ViewPager? = null

    private var japaneseVocab: JapaneseVocab = JapaneseVocab.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mViewPager = findViewById(R.id.container) as ViewPager
        setupViewPager(mViewPager!!)

        var tabLayout: TabLayout = findViewById(R.id.tabs) as TabLayout
        tabLayout.setupWithViewPager(mViewPager)

        readXml()

    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = SectionsPageAdapter(supportFragmentManager)
        adapter.addFragment(N5TabFragment(), "N5")
        adapter.addFragment(N4TabFragment(), "N4")
        adapter.addFragment(N3TabFragment(), "N3")
        adapter.addFragment(N2TabFragment(), "N2")
        adapter.addFragment(N1TabFragment(), "N1")
        adapter.addFragment(SearchFragment(),"Search")
        viewPager.adapter = adapter
    }

    private fun readXml() {
        val japXml: XmlPullParser = resources.getXml(R.xml.japanesevocab)
        var eventType: Int = japXml.eventType
        var kanjis: ArrayList<Kanji> = ArrayList()
        var newKanji: Kanji? = null
        var character: String? = null
        var meanings: ArrayList<String>? = null
        var onyomis: ArrayList<String>? = null
        var kunyomis: ArrayList<String>? = null
        var jlptLevel: Int = 0

        var xmlTag:String?=null
        while (eventType != XmlPullParser.END_DOCUMENT) {
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    xmlTag= japXml.name
                    if (xmlTag.equals("kanji")) {
                        newKanji = Kanji()
                    }  else if (xmlTag.equals("meanings")) {
                        meanings = ArrayList()
                    }  else if (xmlTag.equals("onyomis")) {
                        onyomis = ArrayList()
                    } else if (xmlTag.equals("kunyomis")) {
                        kunyomis = ArrayList()
                    }
                }
                XmlPullParser.TEXT ->  {
                    if (xmlTag.equals("character")) {
                        character = japXml.text
                    }  else if (xmlTag.equals("meaning")) {
                        meanings!!.add(japXml.text)
                    } else if (xmlTag.equals("onyomi")) {
                        onyomis!!.add(japXml.text)
                    }  else if (xmlTag.equals("kunyomi")) {
                        kunyomis!!.add(japXml.text)
                    } else if (xmlTag.equals("jlptLevel")) {
                        jlptLevel = japXml.text.toInt()
                    }
                }
                XmlPullParser.END_TAG -> {
                    var japName: String = japXml.name
                    if (japName.equals("kanji")) {
                        newKanji!!.character=character
                        newKanji.meaning = meanings
                        newKanji.onyomi = onyomis
                        newKanji.kunyomi = kunyomis
                        newKanji.jlptLevel = jlptLevel

                        kanjis.add(newKanji)
                    }
                }
            }
            eventType = japXml.next()
        }

        Log.i(TAG,"Kanjis loadet")
        japaneseVocab.kanjis=kanjis

    }

}
