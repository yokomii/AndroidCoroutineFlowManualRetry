package jp.co.example.yokomii.coroutine_flow_manual_retry

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import jp.co.example.yokomii.coroutine_flow_manual_retry.databinding.ActivityManualRetryBinding

class ManualRetryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityManualRetryBinding

    private val viewModel: ManualRetryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManualRetryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.retry.setOnClickListener { viewModel.retry() }

        viewModel.retryEvent.observe(this) {
            when (it) {
                is RetryEvent.Retrying -> showProgress()
                is RetryEvent.Retried -> hideProgress()
            }
        }
        viewModel.uiState.observe(this) {
            when (it) {
                is UiState.Success -> showMessage(it.message)
                is UiState.Error -> showError()
            }
        }
    }

    private fun showMessage(message: String) {
        binding.errorMessage.visibility = View.GONE
        binding.successContainer.visibility = View.VISIBLE
        binding.message.text = message
    }

    private fun showError() {
        binding.errorMessage.visibility = View.VISIBLE
        binding.successContainer.visibility = View.GONE
    }

    private fun showProgress() {
        binding.progress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        binding.progress.visibility = View.GONE
    }
}
