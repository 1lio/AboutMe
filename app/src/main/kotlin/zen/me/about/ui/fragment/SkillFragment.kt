package zen.me.about.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_skills.*
import zen.me.about.R
import zen.me.about.model.Skill

class SkillFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, group: ViewGroup?, state: Bundle?): View? {
        super.onCreateView(inflater, group, state)
        return inflater.inflate(R.layout.fragment_skills, group, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SkillListAdapter()
        adapter.list = arrayListOf(
            Skill("Java", 56),
            Skill("Kotlin", 70),
            Skill("RxJava", 64),
            Skill("Dagger2", 60),
            Skill("Design", 91)
        )
        recyclerSkills.adapter = adapter
        recyclerSkills.setHasFixedSize(true)
    }
}