package com.appraulbana.hp_project.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.appraulbana.hp_project.R
import com.appraulbana.hp_project.models.Spell

class SpellItemAdapter(
    private val onClick: (Spell) -> Unit
) : ListAdapter<Spell, SpellItemAdapter.SpellViewHolder>(SpellDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpellViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_spell, parent, false)
        return SpellViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: SpellViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SpellViewHolder(
        itemView: View,
        private val onClick: (Spell) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.tv_spell_name)
        private var currentSpell: Spell? = null

        init {
            itemView.setOnClickListener {
                currentSpell?.let { onClick(it) }
            }
        }

        fun bind(spell: Spell) {
            currentSpell = spell
            tvName.text = spell.name
        }
    }

    class SpellDiffCallback : DiffUtil.ItemCallback<Spell>() {
        override fun areItemsTheSame(oldItem: Spell, newItem: Spell): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Spell, newItem: Spell): Boolean {
            return oldItem == newItem
        }
    }
}