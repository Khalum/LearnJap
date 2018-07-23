package rutz.de.learnjap.android

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import rutz.de.learnjap.ENUMS.Modus
import rutz.de.learnjap.ENUMS.VocabInformation
import rutz.de.learnjap.R
import rutz.de.learnjap.japanesevocab.InfoObject
import rutz.de.learnjap.japanesevocab.JapaneseVocab
import rutz.de.learnjap.japanesevocab.Kanji
import java.util.*
import kotlin.collections.ArrayList

class QuizSite : AppCompatActivity() {

    private val TAG = "Quizsite"
    private val CARDS=15
    private val textViews: ArrayList<TextView> = ArrayList()

    private var kanjis: ArrayList<Kanji>?=null
    private var fullKanjis: ArrayList<Kanji>?= null
    private var modus:Modus?=null
    private var correctAnswer:String?=null
    private var topic: TextView?=null
    private var currentTopicObject: InfoObject?=null
    private var correctCount:Int=0
    private var wrongCount:Int=0

    private var sizeSet=false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quizsite)

        var diffcult:Int= intent.extras.get("difficult") as Int
        kanjis=JapaneseVocab.getInstance().getKanjis(diffcult, VocabInformation.JLTPLEVEL)
        fullKanjis=ArrayList(kanjis)

        modus=intent.extras.get("modus") as Modus
        topic = findViewById(R.id.topic) as TextView
        textViews.add(findViewById(R.id.R1C1) as TextView)
        textViews.add(findViewById(R.id.R1C2) as TextView)
        textViews.add(findViewById(R.id.R1C3) as TextView)
        textViews.add(findViewById(R.id.R1C4) as TextView)
        textViews.add(findViewById(R.id.R2C1) as TextView)
        textViews.add(findViewById(R.id.R2C2) as TextView)
        textViews.add(findViewById(R.id.R2C3) as TextView)
        textViews.add(findViewById(R.id.R2C4) as TextView)
        textViews.add(findViewById(R.id.R3C1) as TextView)
        textViews.add(findViewById(R.id.R3C2) as TextView)
        textViews.add(findViewById(R.id.R3C3) as TextView)
        textViews.add(findViewById(R.id.R3C4) as TextView)
        textViews.add(findViewById(R.id.R4C1) as TextView)
        textViews.add(findViewById(R.id.R4C2) as TextView)
        textViews.add(findViewById(R.id.R4C3) as TextView)
        textViews.add(findViewById(R.id.R4C4) as TextView)

        nextRound()

        textViews.forEach {textView->
            textView.setOnClickListener(View.OnClickListener {
                if(textView.text.toString().equals(correctAnswer)){
                    correctCount++
                    (findViewById(R.id.correct) as TextView).text=correctCount.toString()
                }else{
                    wrongCount++
                    (findViewById(R.id.wrong) as TextView).text=wrongCount.toString()
                    Toast.makeText(applicationContext,"Richtige Antwort: ${(topic!!.text)} = $correctAnswer",Toast.LENGTH_SHORT).show()
                }
                nextRound()
            })
        }
    }

    private fun nextRound() {
       when(modus) {
           Modus.KANJIMEANING -> startKanjiMeaning()
           Modus.MEANINGKANJI -> startMeaningKanji()
       }
    }

    private fun startKanjiMeaning(){
        if (kanjis!!.size > 0) {
            var answerList:ArrayList<String> = ArrayList()
            var answerCount:Int=0
            var rnd = Random().nextInt(kanjis!!.size)
            var textViewRnd = Random().nextInt(textViews.size)

            topic?.text = kanjis!![rnd].character
            correctAnswer=kanjis!![rnd].meaning!![0]
            textViews[textViewRnd].text = correctAnswer
            currentTopicObject=kanjis!!.get(rnd)
            kanjis!!.removeAt(rnd)

            while(answerCount<=CARDS){
                rnd = Random().nextInt(fullKanjis!!.size)
                var answer=fullKanjis!![rnd].meaning!![0]
                if(!answerList.contains(answer) && !answer.equals(correctAnswer)){
                    answerList.add(answer)
                    answerCount++
                }
            }

            for (i in textViews.indices) {
                if(textViewRnd!=i) {
                    textViews[i].text = answerList[0]
                    answerList.removeAt(0)
                }
            }

        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun startMeaningKanji(){
        if(sizeSet==false){
            textViews.forEach {textView->
                textView.textSize=40f
            }
        }
        if (kanjis!!.size > 0) {
            var answerList:ArrayList<String> = ArrayList()
            var answerCount:Int=0
            var rnd = Random().nextInt(kanjis!!.size)
            var textViewRnd = Random().nextInt(textViews.size)

            topic?.text = kanjis!![rnd].meaning!![0]
            correctAnswer=  kanjis!![rnd].character

            textViews[textViewRnd].text = correctAnswer
            currentTopicObject=kanjis!!.get(rnd)
            kanjis!!.removeAt(rnd)

            while(answerCount<=CARDS){
                rnd = Random().nextInt(fullKanjis!!.size)
                var answer=fullKanjis!![rnd].character!!
                if(!answerList.contains(answer)&& !answer.equals(correctAnswer)){
                    answerList.add(answer)
                    answerCount++
                }
            }

            for (i in textViews.indices) {
                if(textViewRnd!=i) {
                    textViews[i].text = answerList[0]
                    answerList.removeAt(0)
                }
            }

        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun openInfo(v:View){
        var intent=Intent(applicationContext,Dictionary::class.java)
        intent.putExtra("item",currentTopicObject)
        intent.putExtra("modus",modus)
        startActivity(intent)
    }
}