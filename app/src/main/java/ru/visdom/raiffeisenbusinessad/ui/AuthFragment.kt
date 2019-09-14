package ru.visdom.raiffeisenbusinessad.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import ru.visdom.raiffeisenbusinessad.R
import ru.visdom.raiffeisenbusinessad.databinding.FragmentAuthBinding
import ru.visdom.raiffeisenbusinessad.viewmodels.AuthViewModel

class AuthFragment : Fragment() {
    private lateinit var binding: FragmentAuthBinding

    private val viewModel: AuthViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, AuthViewModel.Factory(activity.application))
            .get(AuthViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_auth, container, false)
        init()
        return binding.root
    }

    private fun init() {
        binding.signInButton.setOnClickListener { viewModel.authUser(
            binding.phoneNumberEditText.text.toString(),
            binding.passwordEditText.text.toString()
        ) }
    }

    private fun checkInputData(): Boolean {
        var isOk = true

        if (TextUtils.isEmpty(binding.phoneNumberEditText.text)) {
            binding.phoneNumberTextInputLayout.error = getString(R.string.empty_phone_number_error)
            isOk = false
        } else {
            binding.phoneNumberTextInputLayout.error = ""
        }

        if (binding.phoneNumberEditText.text?.length != 10) {
            binding.phoneNumberTextInputLayout.error =
                getString(R.string.length_phone_number_not_equal_eleven_error)
            isOk = false
        } else {
            binding.phoneNumberTextInputLayout.error = ""
        }

        if (TextUtils.isEmpty(binding.passwordEditText.text)) {
            binding.passwordTextInputLayout.error = getString(R.string.empty_password_error)
            isOk = false
        } else {
            binding.passwordTextInputLayout.error = ""
        }

        return isOk
    }
}