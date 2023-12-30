package com.example.mediclient.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mediclient.R
import com.example.mediclient.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = requireNotNull(_binding) { "바인딩 객체가 생성되지 않았습니다." }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val homeContentAdapter = HomeContentAdapter(requireContext())
        binding.rvHome.adapter = homeContentAdapter
        homeContentAdapter.setHomeContentList(mockHomeContentList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val mockHomeContentList = listOf<HomeContent>(
        HomeContent(
            profileImage = R.drawable.medi_dummy,
            title = "정각원의 소리",
            writer = "동국스님",
        ),
        HomeContent(
            profileImage = R.drawable.medi_dummy,
            title = "보신각의 소리",
            writer = "서울스님",
        ),
        HomeContent(
            profileImage = R.drawable.medi_dummy,
            title = "봉은사의 소리",
            writer = "강남스님",
        ),
        HomeContent(
            profileImage = R.drawable.medi_dummy,
            title = "소림사의 소리",
            writer = "중국스님",
        ),
        HomeContent(
            profileImage = R.drawable.medi_dummy,
            title = "조계사 소리",
            writer = "종로스님",
        ),
    )
}
