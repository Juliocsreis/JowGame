package com.example.jow

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*


const val qtdeRiscos = 10
private var riscosFaltando: Int = qtdeRiscos
private var jogador: Int = 1
private lateinit var tvGanhador: TextView
private lateinit var jogador01: TextView
private lateinit var jogador02: TextView

//placar
private lateinit var tvPlacarJogador01: TextView
private var placarJogador01: Int = 0

private lateinit var tvPlacarJogador02: TextView
private var placarJogador02: Int = 0

private lateinit var preferences: SharedPreferences

//riscos
private lateinit var risco1: ImageView
private lateinit var risco2: ImageView
private lateinit var risco3: ImageView
private lateinit var risco4: ImageView
private lateinit var risco5: ImageView
private lateinit var risco6: ImageView
private lateinit var risco7: ImageView
private lateinit var risco8: ImageView
private lateinit var risco9: ImageView
private lateinit var risco10: ImageView

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        riscosFaltando = qtdeRiscos
        jogador = 1
        tvGanhador = findViewById(R.id.tvAnuciaGanhador)
        jogador01 = findViewById(R.id.tvJogador01)
        jogador02 = findViewById(R.id.tvJogador02)
        tvJogadorAtivo.setTextColor(getColor(R.color.jogador01))
        tvJogadorAtivo.text = getString(R.string.jogador_ativo, jogador.toString())


        tvPlacarJogador01 = findViewById(R.id.tvPlacar01)
        tvPlacarJogador02 = findViewById(R.id.tvPlacar02)

        risco1 = findViewById(R.id.ivRisco1)
        risco2 = findViewById(R.id.ivRisco2)
        risco3 = findViewById(R.id.ivRisco3)
        risco4 = findViewById(R.id.ivRisco4)
        risco5 = findViewById(R.id.ivRisco5)
        risco6 = findViewById(R.id.ivRisco6)
        risco7 = findViewById(R.id.ivRisco7)
        risco8 = findViewById(R.id.ivRisco8)
        risco9 = findViewById(R.id.ivRisco9)
        risco10 = findViewById(R.id.ivRisco10)


        preferences = this.getSharedPreferences("jow", Context.MODE_PRIVATE)
        placarJogador01 = preferences.getInt("placarJogador01", 0)
        placarJogador02 = preferences.getInt("placarJogador02", 0)
        tvPlacarJogador01.text = placarJogador01.toString()
        tvPlacarJogador02.text = placarJogador02.toString()

        jogador01.setOnClickListener {
            jogador = 1
            tvJogadorAtivo.text = getString(R.string.jogador_ativo, jogador.toString())
            tvJogadorAtivo.setTextColor(getColor(R.color.jogador01))
            Log.d("jogador01 clicado mudando valor jogador para", jogador.toString())
        }
        jogador02.setOnClickListener {
            jogador = 2
            tvJogadorAtivo.text = getString(R.string.jogador_ativo, jogador.toString())
            tvJogadorAtivo.setTextColor(getColor(R.color.jogador02))
            Log.d("jogador02 clicado mudando valor jogador para", jogador.toString())
        }


        risco1.setOnClickListener(this)
        risco2.setOnClickListener(this)
        risco3.setOnClickListener(this)
        risco4.setOnClickListener(this)
        risco5.setOnClickListener(this)
        risco6.setOnClickListener(this)
        risco7.setOnClickListener(this)
        risco8.setOnClickListener(this)
        risco9.setOnClickListener(this)
        risco10.setOnClickListener(this)

        tvNovoJogo.setOnClickListener { novoJogo() }

        tvZerarPlacar.setOnClickListener {
            zerarPlacar()
        }

    }

    override fun onClick(v: View?) {
        Log.d("onCLick", v.toString())
        when (v!!.id) {
            R.id.ivRisco1 -> riscarRisco(risco1)
            R.id.ivRisco2 -> riscarRisco(risco2)
            R.id.ivRisco3 -> riscarRisco(risco3)
            R.id.ivRisco4 -> riscarRisco(risco4)
            R.id.ivRisco5 -> riscarRisco(risco5)
            R.id.ivRisco6 -> riscarRisco(risco6)
            R.id.ivRisco7 -> riscarRisco(risco7)
            R.id.ivRisco8 -> riscarRisco(risco8)
            R.id.ivRisco9 -> riscarRisco(risco9)
            R.id.ivRisco10 -> riscarRisco(risco10)
        }
    }


    private fun verificarEAnunciarGanhador() {
        riscosFaltando -= 1
        Log.d("riscos faltando", riscosFaltando.toString())
        when (riscosFaltando) {
            1 -> {
                tvGanhador.text = "jogador $jogador ganhou"
                if (jogador == 1) {
                    tvGanhador.setTextColor(getColor(R.color.jogador01))
                }
                if (jogador == 2) {
                    tvGanhador.setTextColor(getColor(R.color.jogador02))
                }
                Log.d("ganhador", jogador.toString())
                aumentarPlacarGanhador()
            }
        }
    }

    private fun aumentarPlacarGanhador() {
        if (jogador == 1) {
            placarJogador01 += 1
            tvPlacarJogador01.text = placarJogador01.toString()
            preferences.edit().putInt("placarJogador01", placarJogador01).apply()
        }
        if (jogador == 2) {
            placarJogador02 += 1
            tvPlacarJogador02.text = placarJogador02.toString()
            preferences.edit().putInt("placarJogador02", placarJogador02).apply()

        }
    }


    private fun riscarRisco(risco: ImageView) {
        val tag = "riscarRisco"
        verificarEAnunciarGanhador()
        if (jogador == 1) {
            risco.setImageResource(R.drawable.riscado_jogador_01)
            Log.d(tag, "jogador 1 riscou $risco")
        }
        if (jogador == 2) {
            risco.setImageResource(R.drawable.riscado_jogador_02)
            Log.d(tag, "jogador 2 riscou $risco")
        }
    }

    private fun zerarPlacar() {
        preferences.edit().clear().apply()
        tvPlacarJogador01.text = placarJogador01.toString()
        tvPlacarJogador02.text = placarJogador02.toString()
        recreate()
    }

    private fun novoJogo() {
        recreate()
    }
}

