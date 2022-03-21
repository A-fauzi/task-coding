package com.afauzi.task_coding

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import com.afauzi.task_coding.databinding.FragmentAccountBinding
import com.afauzi.task_coding.databinding.FragmentMainBinding
import com.afauzi.task_coding.integerasiApi.auth.authpage.LoginActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.R

class AccountFragment : Fragment() {

    private lateinit var binding: FragmentAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.BtnMoreOption.setOnClickListener {
            popupMenu()
        }

    }

    @SuppressLint("RtlHardcoded")
    private fun popupMenu() {

        val popupMenu = PopupMenu(activity, binding.BtnMoreOption, Gravity.RIGHT)
        popupMenu.menuInflater.inflate(com.afauzi.task_coding.R.menu.menu_account, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
//            dialogShowNotPage(it.title)
            when (it.itemId) {
                com.afauzi.task_coding.R.id.logout -> {
                    val auth: FirebaseAuth = FirebaseAuth.getInstance()
                    auth.signOut().let {
                        startActivity(Intent(activity, LoginActivity::class.java))
                        activity?.finish()
                    }

                }
            }

            true
        }
        popupMenu.show()
    }

    private fun dialogShowNotPage(title: CharSequence) {
        val dialog = MaterialAlertDialogBuilder(requireActivity(), com.afauzi.task_coding.R.style.MaterialAlertDialog_rounded)
//        dialog.setView(R.layout.no_page_introduction)
        dialog.setTitle(title)
        dialog.show()
    }


}
