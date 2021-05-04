/*package no.kristiania.android_innlevering.fragments.buy

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_buy_currency.*
import kotlinx.android.synthetic.main.fragment_list.view.*
import no.kristiania.android_innlevering.R
import no.kristiania.android_innlevering.data.User
import no.kristiania.android_innlevering.data.UserViewModel

class BuyCurrencyFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_buy_currency, container, false)

       mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.buy_btn.setOnClickListener{
            insertDataToDatabase()

        }

        return view

    }
    private fun insertDataToDatabase() {
        val usDollarAmount = addUSCurrency_et.text;


        if(inputCheck(usDollarAmount)){
            val user = User(0, Integer.parseInt(usDollarAmount.toString()))

            mUserViewModel.addUser(user)
            Toast.makeText(requireContext(), "Success", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(requireContext(), "Oops, didn't work.", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(usDollarAmount: Editable?): Boolean{
        return !(TextUtils.isEmpty(usDollarAmount))
    }
}*/