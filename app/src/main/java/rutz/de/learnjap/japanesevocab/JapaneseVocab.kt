package rutz.de.learnjap.japanesevocab

import android.app.Application
import rutz.de.learnjap.ENUMS.VocabInformation

class JapaneseVocab private constructor():Application(){

    private object Holder { val INSTANCE=JapaneseVocab()}

    companion object {
        fun getInstance(): JapaneseVocab {
            val instance: JapaneseVocab by lazy { Holder.INSTANCE }
            return instance
        }
    }


    var kanjis: List<Kanji>? = null
    var vocabularies: List<Vocabulary>? = null

    fun kanjiCount(level:Int,information: VocabInformation):Int{
        var counter:Int=0
        kanjis!!.forEach {
           if(information== VocabInformation.JLTPLEVEL && it.jlptLevel==level){
               counter++
           }
       }
        return counter
    }

    fun getKanjis(level: Int, information: VocabInformation):ArrayList<Kanji>{
        var kanjiList:ArrayList<Kanji> = ArrayList()
        kanjis!!.forEach {
            if (information== VocabInformation.JLTPLEVEL && it.jlptLevel == level) {
                kanjiList.add(it)
            }
        }
        return kanjiList
    }

    fun getMeanings(level:Int, information: VocabInformation):ArrayList<String>{
        var answerList:ArrayList<String> = ArrayList()
        kanjis!!.forEach{
            if(information== VocabInformation.JLTPLEVEL && it.jlptLevel == level) {
                    answerList.add(it.meaning!![0])
            }
        }
        return answerList
    }

}