package com.example.mediclient.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mediclient.databinding.ItemHomeContentBinding

class HomeContentAdapter(context: Context) : RecyclerView.Adapter<HomeContentViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }
    private var homeContentList: List<HomeContent> = emptyList() // 임시 빈 리스트

    // 화면에 띄우기, 뷰 생성 (어댑터 필수요소)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeContentViewHolder {
        val binding = ItemHomeContentBinding.inflate(inflater, parent, false)
        return HomeContentViewHolder(binding)
    }

    // ViewHolder와 데이터 매칭 (어댑터 필수요소)
    override fun onBindViewHolder(holder: HomeContentViewHolder, position: Int) {
        holder.onBind(homeContentList[position])
    }

    // 데이터 리스트의 아이템 개수 (어댑터 필수요소)
    override fun getItemCount() = homeContentList.size

    // 임시 빈 리스트에 따로 만든 가짜리스트 연결
    fun setHomeContentList(homeContentList: List<HomeContent>) {
        this.homeContentList = homeContentList.toList()
        notifyDataSetChanged()
    }
}
