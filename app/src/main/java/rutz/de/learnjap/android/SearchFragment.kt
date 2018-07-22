package rutz.de.learnjap.android

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import rutz.de.learnjap.R
import rutz.de.learnjap.japanesevocab.JapaneseVocab
import java.util.ArrayList
import android.widget.AdapterView
import rutz.de.learnjap.ENUMS.Modus
import rutz.de.learnjap.japanesevocab.InfoObject


class SearchFragment : Fragment(),SearchView.OnQueryTextListener{

    val TAG="SearchFragment"
    private var searchList:ListView?=null
    private var searchView:SearchView?=null

    override fun onQueryTextSubmit(p0: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        var adapter: InfoObjectAdapter=searchList!!.adapter as InfoObjectAdapter
        var filter:Filter=adapter.filter
        filter.filter(newText)
        return true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.search, container, false)
        searchList= view.findViewById(R.id.searchList) as ListView
        searchView= view.findViewById(R.id.search) as SearchView
        var arrayAdapter= InfoObjectAdapter(activity,JapaneseVocab.getInstance().kanjis as ArrayList<InfoObject>)
        searchList!!.adapter=arrayAdapter
        searchList!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            var kanji=parent.getItemAtPosition(position) as InfoObject
            var intent= Intent(context,Dictionary::class.java)
            intent.putExtra("item",kanji)
            intent.putExtra("modus",Modus.KANJIMEANING)
            startActivity(intent)
        }
        setupSearchView()
        return view
    }

    private fun setupSearchView() {
        searchView!!.setIconifiedByDefault(false)
        searchView!!.setOnQueryTextListener(this)
        searchView!!.queryHint="Kanjisuche"
    }


}
