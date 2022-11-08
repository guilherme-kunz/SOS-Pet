package guilhermekunz.com.br.sospet.ui.authentication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import guilhermekunz.com.br.sospet.databinding.ActivityAuthenticationBinding

class AuthenticationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthenticationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}