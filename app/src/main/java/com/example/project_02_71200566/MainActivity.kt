package com.example.project_02_71200566

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.*
import java.nio.file.Files.delete


class MainActivity : AppCompatActivity() {
    var firestore: FirebaseFirestore? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firestore = FirebaseFirestore.getInstance()

        val edtJudul = findViewById<EditText>(R.id.edtJudul)
        val edtTanggal = findViewById<EditText>(R.id.edtTanggal)
        val edtIsi = findViewById<EditText>(R.id.edtIsi)
        val btnSimpan =  findViewById<Button>(R.id.btnSimpan)
        val txvHasil = findViewById<TextView>(R.id.txvHasil)
        val edtJudulCari = findViewById<EditText>(R.id.edtJudulCari)
        val btnCari =  findViewById<Button>(R.id.btnCari)
        val txvCariJudul = findViewById<TextView>(R.id.txvCariJudul)
        val edtDokumen = findViewById<EditText>(R.id.edtDokumen)
        val btnHapus =  findViewById<Button>(R.id.btnHapus)
        val edtDokumenEdit = findViewById<EditText>(R.id.edtDokumenEdit)
        val edtJudulEdit = findViewById<EditText>(R.id.edtJudulEdit)
        val edtTanggalEdit = findViewById<EditText>(R.id.edtTanggalEdit)
        val edtIsiEdit = findViewById<EditText>(R.id.edtIsiEdit)
        val btnEdit = findViewById<Button>(R.id.btnEdit)
        val txvHasilEdit = findViewById<TextView>(R.id.txvHasilEdit)

        btnSimpan.setOnClickListener {
            val notes = Notes(edtJudul.text.toString(), edtTanggal.text.toString(), edtIsi.text.toString())
            edtJudul.setText("")
            edtTanggal.setText("")
            edtIsi.setText("")
            firestore?.collection("Notes")?.add(notes)

            firestore?.collection("Notes")?.orderBy("tanggal", Query.Direction.DESCENDING)?.get()?.addOnSuccessListener { data ->
                var output = ""
                for (hasil in data){
                    output += "\n${hasil["tanggal"]} - ${hasil["judul"]} - ${hasil["isi"]} "
                }
                txvHasil.text= output
            }
        }

        btnCari.setOnClickListener {
            firestore?.collection("Notes")?.orderBy("tanggal", Query.Direction.DESCENDING)?.get()?.addOnSuccessListener { data ->
                var output = ""
                for (hasil in data){
                    if("${hasil["judul"]}" == edtJudulCari.text.toString() ) {
                        output += "\n${hasil["tanggal"]} - ${hasil["judul"]} - ${hasil["isi"]} "
                    }
                }
                txvCariJudul.text= output
            }
        }

        btnHapus.setOnClickListener {
            firestore?.collection("Notes")?.document(edtDokumen.text.toString())?.delete()
            //Cth Inputan: d6Y3qJxo0hXORY0Mr4th
        }

        btnEdit.setOnClickListener {
            firestore?.collection("Notes")?.document(edtDokumenEdit.text.toString())?.update("judul", edtJudulEdit.text.toString())
            firestore?.collection("Notes")?.document(edtDokumenEdit.text.toString())?.update("tanggal", edtTanggalEdit.text.toString())
            firestore?.collection("Notes")?.document(edtDokumenEdit.text.toString())?.update( "isi", edtIsiEdit.text.toString())
        }


    }
}