package com.example.covid_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.covid19.Boletim
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val boletim = intent.extras?.getParcelable<Boletim>("boletim")

        if (boletim != null) {
            var date = boletim.data + " " + boletim.hora + "h"
            title = "${getString(R.string.report)}: $date"
            txtDConfirmed.text = boletim.confirmados.toString()
            txtDCured.text = boletim.curados.toString()
            txtDDescarted.text = boletim.descartados.toString()
            txtDSuspect.text = boletim.suspeitos.toString()
            txtDDocimile.text = boletim.sDomiciliar.toString()
            txtDHospital.text = boletim.sHospitalar.toString()
            txtDDead.text = boletim.mortes.toString()
            txtDDate.text = date
        } else {
            Toast.makeText(this, getString(R.string.details_error), Toast.LENGTH_LONG).show()
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }

    }
}