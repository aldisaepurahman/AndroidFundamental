package com.app.myflexiblefragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.DialogFragment

class OptionDialogFragment : DialogFragment() {
    private lateinit var btnChoose: Button
    private lateinit var btnClose: Button
    private lateinit var rgOptions: RadioGroup
    private lateinit var rbOle: RadioButton
    private lateinit var rbKoeman: RadioButton
    private lateinit var rbRagnick: RadioButton
    private lateinit var rbSetien: RadioButton
    private var optionDialogListener: OnOptionDialogListener? = null

    /*pembuatan internal interface untuk pembuatan abstrak ketika dialog ditampilkan*/
    interface OnOptionDialogListener {
        fun onOptionChosen(text: String?)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_option_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnChoose = view.findViewById(R.id.btn_choose)
        btnClose = view.findViewById(R.id.btn_close)
        rgOptions = view.findViewById(R.id.rg_options)
        rbOle = view.findViewById(R.id.rb_ole)
        rbKoeman = view.findViewById(R.id.rb_koeman)
        rbRagnick = view.findViewById(R.id.rb_ragnick)
        rbSetien = view.findViewById(R.id.rb_setien)
        /*bermain dengan dialog*/
        btnChoose.setOnClickListener {
            val checkedRadioButtonId = rgOptions.checkedRadioButtonId
            if (checkedRadioButtonId != -1){
                var coach: String? = when(checkedRadioButtonId){
                    R.id.rb_ole -> rbOle.text.toString().trim()
                    R.id.rb_koeman -> rbKoeman.text.toString().trim()
                    R.id.rb_ragnick -> rbRagnick.text.toString().trim()
                    R.id.rb_setien -> rbSetien.text.toString().trim()
                    else -> null
                }
                /*ketika dipilih, teks masuk dan dialog dihilangkan*/
                optionDialogListener?.onOptionChosen(coach)
                dialog?.dismiss()
            }
        }
        /*ketika ditutup, dialog langsung di cancel*/
        btnClose.setOnClickListener{
            dialog?.cancel()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val fragment = parentFragment

        if (fragment is DetailCategoryFragment){
            this.optionDialogListener = fragment.optionDialogListener
        }
    }

    override fun onDetach() {
        super.onDetach()
        this.optionDialogListener = null
    }

}