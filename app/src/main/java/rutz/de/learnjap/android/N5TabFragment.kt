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
import rutz.de.learnjap.japanesevocab.Kanji
import rutz.de.learnjap.ENUMS.VocabInformation

class N5TabFragment : Fragment() {

    private val TAG = "N5TabFragment"
    private val DIFFICULT=5

    private val japaneseVocab:JapaneseVocab= JapaneseVocab.getInstance()

    private var kanMeanLayout: LinearLayout? = null
    private var kanMeanCount: TextView?=null
    private var MeanKanLayout: LinearLayout? = null
    private var MeanKanCount: TextView?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.n5_fragment, container, false)
        kanMeanLayout = view.findViewById(R.id.KanMeanN5) as LinearLayout
        kanMeanCount= view.findViewById(R.id.KanMeanCountN5) as TextView
        MeanKanLayout = view.findViewById(R.id.MeanKanN5) as LinearLayout
        MeanKanCount = view.findViewById(R.id.MeanKanCountN5) as TextView

        kanMeanCount!!.text=japaneseVocab.kanjiCount(DIFFICULT, VocabInformation.JLTPLEVEL).toString()
        MeanKanCount!!.text=japaneseVocab.kanjiCount(DIFFICULT, VocabInformation.JLTPLEVEL).toString()
        kanMeanLayout!!.setOnClickListener(View.OnClickListener {
            val intentQuiz = Intent(activity, QuizSite::class.java)
            intentQuiz.putExtra("difficult",DIFFICULT)
            intentQuiz.putExtra("modus",Modus.KANJIMEANING)
            startActivity(intentQuiz)
        })
        MeanKanLayout!!.setOnClickListener(View.OnClickListener {
            val intentQuiz = Intent(activity, QuizSite::class.java)
            intentQuiz.putExtra("difficult",DIFFICULT)
            intentQuiz.putExtra("modus",Modus.MEANINGKANJI)
            startActivity(intentQuiz)
        })
        return view
    }
}
