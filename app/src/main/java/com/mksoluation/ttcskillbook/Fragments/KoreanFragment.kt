package com.mksoluation.ttcskillbook.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mksoluation.ttcskillbook.JapaniMod1Activity
import com.mksoluation.ttcskillbook.KoreanModule1Activity
import com.mksoluation.ttcskillbook.LearnLanguageActivity
import com.mksoluation.ttcskillbook.R
import com.mksoluation.ttcskillbook.databinding.FragmentKoreanBinding


class KoreanFragment : Fragment() {
    private lateinit var binding: FragmentKoreanBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentKoreanBinding.inflate(layoutInflater)
        binding.cardModule1.setOnClickListener {
            startActivity(Intent(requireContext(),KoreanModule1Activity::class.java))
        }
        binding.cardModule2.setOnClickListener {
            val intent = Intent(requireContext(), LearnLanguageActivity::class.java)
            intent.putExtra("MODULE_NAME","Korean_module_two")
            startActivity(intent)
        }
        binding.cardModule3.setOnClickListener {
            val intent = Intent(requireContext(), LearnLanguageActivity::class.java)
            intent.putExtra("MODULE_NAME","Korean_module_three")
            startActivity(intent)
        }
        binding.cardModule4.setOnClickListener {
            val intent = Intent(requireContext(), LearnLanguageActivity::class.java)
            intent.putExtra("MODULE_NAME","Korean_module_four")
            startActivity(intent)
        }
        binding.cardModule5.setOnClickListener {
            val intent = Intent(requireContext(), LearnLanguageActivity::class.java)
            intent.putExtra("MODULE_NAME","Korean_module_five")
            startActivity(intent)
        }

        return binding.root
    }

}