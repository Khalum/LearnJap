package rutz.de.learnjap.android

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import rutz.de.learnjap.R
import rutz.de.learnjap.japanesevocab.InfoObject
import rutz.de.learnjap.japanesevocab.Kanji
import java.util.*
import kotlin.collections.ArrayList

class InfoObjectAdapter(private val mContext: Context, list: ArrayList<InfoObject>) : ArrayAdapter<InfoObject>(mContext, 0, list) {

    private val TAG = "InfoObjectAdapter"

    private var adapterList: ArrayList<InfoObject>? = null
    private var orig: ArrayList<InfoObject>? = null

    init {
        adapterList = list
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var listItem: View? = convertView
        when (getItem(position)) {
            is Kanji -> listItem = setKanjiItem(position, convertView, parent)
        }

        return listItem!!
    }

    private fun setKanjiItem(position: Int, convertView: View?, parent: ViewGroup?): View {
        var listItem: View? = convertView
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.kanjilistitem, parent, false)
        }
        var currentKanji: Kanji = getItem(position) as Kanji
        var kanjiView = listItem!!.findViewById(R.id.kanjiListItem) as TextView
        kanjiView.text = currentKanji.character

        var meaningView = listItem.findViewById(R.id.meaningListItem) as TextView
        var meaning: String = ""
        currentKanji.meaning!!.forEach {
            meaning += "$it, "
        }
        meaning=meaning.dropLast(2)
        meaningView.text = meaning

        var onyomiView = listItem.findViewById(R.id.onyomiListItem) as TextView
        var onyomi: String = ""
        currentKanji.onyomi!!.forEach {
            onyomi += "$it, "
        }
        onyomi=onyomi.dropLast(2)
        onyomiView.text = onyomi

        var kunyomiView = listItem.findViewById(R.id.kunyomiListItem) as TextView
        var kunyomi: String = ""
        currentKanji.kunyomi!!.forEach {
            kunyomi += "$it, "
        }
        kunyomi=kunyomi.dropLast(2)
        kunyomiView.text = kunyomi

        return listItem
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val oReturn = FilterResults()
                val results: ArrayList<InfoObject> = ArrayList()
                if(orig==null)
                    orig= ArrayList(adapterList)
                if (!constraint.isNullOrEmpty()) {
                    if (orig != null && orig!!.size > 0) {
                        orig!!.forEach { infoObject ->
                            when (infoObject) {
                                is Kanji -> {
                                    var kanji = infoObject
                                    if (controlKanji(kanji,constraint))
                                        results.add(kanji)
                                }
                            }
                        }

                    }
                    oReturn.values = results
                    oReturn.count = results.size
                } else {
                    results.addAll(orig!!)
                    oReturn.values=results
                    oReturn.count=results.size
                }
                return oReturn
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                clear()
                addAll(results!!.values as ArrayList<InfoObject>)
                notifyDataSetChanged()
            }

        }
    }

    private fun controlKanji(kanji: Kanji, constraint: CharSequence?): Boolean {
        var foundMatch = false
        if (kanji.character!!.toLowerCase().contains(constraint.toString())) {
            foundMatch = true
            return foundMatch
        }

        kanji.meaning!!.forEach {
            if (it.toLowerCase().contains(constraint.toString())) {
                foundMatch=true
                return foundMatch
            }
        }

        kanji.onyomi!!.forEach {
            if (it.toLowerCase().contains(constraint.toString())) {
                foundMatch=true
                return foundMatch
            }
        }

        kanji.kunyomi!!.forEach {
            if (it.toLowerCase().contains(constraint.toString())) {
                foundMatch=true
                return foundMatch
            }
        }

        if(constraint.toString().equals("#jlpt"+kanji.jlptLevel.toString())){
            foundMatch = true
            return foundMatch
        }

        return foundMatch
    }
}