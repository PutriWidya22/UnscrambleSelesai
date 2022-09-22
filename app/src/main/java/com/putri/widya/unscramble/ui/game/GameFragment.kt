package com.putri.widya.unscramble.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.putri.widya.unscramble.R
import com.putri.widya.unscramble.databinding.GameFragmentBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

// membuat class GameFragment
class GameFragment : Fragment() {

    // binding yang nantinya akan mengatur pada tampilan game_fragment.xml
    private lateinit var binding: GameFragmentBinding

    // membuat viewModel
    // Jika fragmen dibuat ulang, maka akan menerima instance GameViewModel yang sama
    // yang dibuat oleh fragmen pertama.
    private val viewModel: GameViewModel by viewModels()

    // membuat viewModel dengan onCreateView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Mengembangkan file XML dan mengembalikan instance objek yang ada.
        binding = DataBindingUtil.inflate(inflater, R.layout.game_fragment, container, false)
        return binding.root
    }

    // untuk menyimpan instance ke fragmen
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // menerapkan viewModel untuk peningkatan data menggunakan binding
        binding.gameViewModel = viewModel
        binding.maxNoOfWords = MAX_NO_OF_WORDS
        binding.lifecycleOwner = viewLifecycleOwner

        // membuat tombol submit dan skip menggunakan setOnClickListener
        binding.submit.setOnClickListener { onSubmitWord() }
        binding.skip.setOnClickListener { onSkipWord() }
    }

    // membuat fungsi submitWord yang nantinya akan dipanggil saat mengklik tombol submit.
    // Memeriksa kata dan memperbarui skors.
    // Menampilkan kata acak berikutnya.
    // menampilkan skors akhir.
    private fun onSubmitWord() {
        val playerWord = binding.textInputEditText.text.toString()

        if (viewModel.isUserWordCorrect(playerWord)) {
            setErrorTextField(false)
            if (!viewModel.nextWord()) {
                showFinalScoreDialog()
            }
        } else {
            setErrorTextField(true)
        }
    }

    // membuat fungsi onSkipWord
    // currentWordCount untuk menambah kata
    // menampilkan skors akhir.
    private fun onSkipWord() {
        if (viewModel.nextWord()) {
            setErrorTextField(false)
        } else {
            showFinalScoreDialog()
        }
    }

    // Membuat dan menampilkan AlertDialog dengan skor akhir.
    private fun showFinalScoreDialog() {
        MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.congratulations))
                .setMessage(getString(R.string.you_scored, viewModel.score.value))
                .setCancelable(false)
                .setNegativeButton(getString(R.string.exit)) { _, _ ->
                    exitGame()
                }
                .setPositiveButton(getString(R.string.play_again)) { _, _ ->
                    restartGame()
                }
                .show()
    }

    // Inisialisasi ulang data di ViewModel dan memperbarui tampilan dengan data baru,
    // untuk mulai ulang permainan.
    private fun restartGame() {
        viewModel.reinitializeData()
        setErrorTextField(false)
    }

    // keluar dari permainan
    private fun exitGame() {
        activity?.finish()
    }

    // Mengatur ulang kesalahan teks.
    private fun setErrorTextField(error: Boolean) {
        if (error) {
            binding.textField.isErrorEnabled = true
            binding.textField.error = getString(R.string.try_again)
        } else {
            binding.textField.isErrorEnabled = false
            binding.textInputEditText.text = null
        }
    }
}
