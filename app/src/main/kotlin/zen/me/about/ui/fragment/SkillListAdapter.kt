package zen.me.about.ui.fragment

import android.content.Context
import android.view.View
import android.view.ViewGroup
import zen.me.about.R
import zen.me.about.base.BaseAdapter
import zen.me.about.base.BaseViewHolder
import zen.me.about.model.Skill
import zen.me.about.ui.view.skill.SkillView

class SkillListAdapter : BaseAdapter<Skill, SkillListAdapter.SkillViewHolder>() {

    private lateinit var ctx: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillViewHolder {
        ctx = parent.context

        return SkillViewHolder(SkillView(parent.context))
    }

    inner class SkillViewHolder(itemView: View) : BaseViewHolder<Skill>(itemView) {

        override fun bind(item: Skill) {
            try {
                itemView as SkillView


                itemView.setText(item.name)
                itemView.setProgress(item.progress)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}