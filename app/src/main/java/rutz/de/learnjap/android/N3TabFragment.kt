package rutz.de.learnjap.android

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import rutz.de.learnjap.ENUMS.Modus
import rutz.de.learnjap.R
import rutz.de.learnjap.japanesevocab.JapaneseVocab
import rutz.de.learnjap.ENUMS.VocabInformation
import rutz.de.learnjap.japanesevocab.Kanji

class N3TabFragment : Fragment() {

    private val TAG = "N3TabFragment"
    private val DIFFICULT=3

    private val japaneseVocab: JapaneseVocab = JapaneseVocab.getInstance()

    private var kanMeanLayout: LinearLayout? = null
    private var kanMeanCount: TextView?=null
    private var MeanKanLayout: LinearLayout? = null
    private var MeanKanCount: TextView?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.n3_fragment, container, false)
        kanMeanLayout = view.findViewById(R.id.KanMeanN3) as LinearLayout
        kanMeanCount= view.findViewById(R.id.KanMeanCountN3) as TextView
        MeanKanLayout = view.findViewById(R.id.MeanKanN3) as LinearLayout
        MeanKanCount = view.findViewById(R.id.MeanKanCountN3) as TextView

        kanMeanCount!!.text=japaneseVocab.kanjiCount(DIFFICULT, VocabInformation.JLTPLEVEL).toString()
        MeanKanCount!!.text=japaneseVocab.kanjiCount(DIFFICULT, VocabInformation.JLTPLEVEL).toString()
        kanMeanLayout!!.setOnClickListener(View.OnClickListener {
            val intentQuiz = Intent(activity, QuizSite::class.java)
            intentQuiz.putExtra("difficult",DIFFICULT)
            intentQuiz.putExtra("modus", Modus.KANJIMEANING)
            startActivity(intentQuiz)
        })
        MeanKanLayout!!.setOnClickListener(View.OnClickListener {
            val intentQuiz = Intent(activity, QuizSite::class.java)
            intentQuiz.putExtra("difficult",DIFFICULT)
            intentQuiz.putExtra("modus", Modus.MEANINGKANJI)
            startActivity(intentQuiz)
        })
        return view
    }


}
