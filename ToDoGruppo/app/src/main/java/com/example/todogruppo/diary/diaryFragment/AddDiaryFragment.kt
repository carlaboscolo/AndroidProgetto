package com.example.todogruppo.diary.diaryFragment

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.todogruppo.R
import com.example.todogruppo.databinding.FragmentAddDiaryBinding
import com.example.todogruppo.diary.viewModel.Diary
import com.example.todogruppo.diary.viewModel.DiaryModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.OnProgressListener
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class AddDiaryFragment : Fragment() {

    //binding
    private lateinit var binding: FragmentAddDiaryBinding

    //view model
    val diaryModel: DiaryModel by viewModels()

    //diary null -> nuovo diario, altrimenti serve per la modifica
    private var diary: Diary? = null

    //dichiarazione variabili
    private lateinit var CalendarBtn: Button
    private lateinit var selectedDate: TextView
    private lateinit var titletext: EditText
    private lateinit var inputText: EditText
    private lateinit var addBtn: Button
    private lateinit var loadingView: ProgressBar
    private lateinit var errorSave: TextView
    private lateinit var errorSave2: TextView
    private lateinit var closeBtn: Button


    //carica immagine
    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    lateinit var imagePreview: ImageView
    lateinit var btn_choose_image: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_add_diary, container, false)
        binding = FragmentAddDiaryBinding.inflate(inflater, container, false)
        return binding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //se passo dei parametri la variabile diario sar?? valorizzata, altrimenti sar?? nullo
        diary = arguments?.getSerializable("diary") as Diary?

        //carica immagine
        btn_choose_image = binding.selectImageBtn
        imagePreview = binding.firebaseImage
        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference


        //inizializza variabili
        addBtn = binding.newDiaryButton

        titletext = binding.titleDiary
        inputText = binding.newDiaryText
        loadingView = binding.loadingView
        errorSave = binding.errorSave
        errorSave2 = binding.errorSave2


        //aggiungere una data alla pagina di diario
        CalendarBtn = binding.addDataBtn
        selectedDate = view.findViewById(R.id.selected_data)


        //prendere la data di oggi
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val day = calendar[Calendar.DAY_OF_MONTH]
        val month = calendar[Calendar.MONTH] + 1

        val dayString = if (day < 10) "0$day" else "$day"
        val monthString = if (month < 10) "0$month" else "$month"

       //formato di stampa a schermo della data
        val data_string = "$dayString-$monthString-$year"
        Log.d("data", data_string)

       //inserisci data selezionata come default
        selectedDate.setText(data_string)

        //MaterialDatePicker
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .build()

        CalendarBtn.setOnClickListener {
            //far comparire il calendario
            datePicker.show(parentFragmentManager, "tag");

            datePicker.addOnPositiveButtonClickListener {
                val utc = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                utc.timeInMillis = it
                val format = SimpleDateFormat("dd-MM-yyyy")
                selectedDate.setText(format.format(utc.time))
            }
        }


        //inserire l'immagine dal dispositivo
        btn_choose_image.setOnClickListener {
            launchGallery()
        }


        diaryModel.duplicateDate.observe(viewLifecycleOwner) {
            if (it) {
                Log.d("okey", "data errata")
                //Avviso se si ?? inserito correttamente la pagina di diario
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Conferma")
                    .setMessage("La pagina che vuoi inserire gi?? esiste oppure ?? una data del futuro, cosa vuoi fare?")
                    .setNegativeButton("Annulla l'inserimento") { dialog, which ->
                        parentFragmentManager.popBackStack()
                    }
                    .setPositiveButton("Scegli una nuova data") { dialog, which ->
                    }
                    .show()
            } else {
                Log.d("okey", "data accettata")
                parentFragmentManager.popBackStack()
            }
        }

        //salva il diario inserita
        addBtn.setOnClickListener {

            val idImg = uploadImage()

            loadingView.visibility = View.VISIBLE

            if (inputText.text.toString().isEmpty() && titletext.text.toString().isEmpty()) {
                errorSave.visibility = View.VISIBLE
                errorSave2.visibility = View.VISIBLE
                Log.d("error", "campo vuoto")
            } else if (inputText.text.toString().isEmpty()) {
                errorSave.visibility = View.GONE
                errorSave2.visibility = View.VISIBLE
                Log.d("error", "campo vuoto")
            } else if (titletext.text.toString().isEmpty()) {
                errorSave.visibility = View.VISIBLE
                errorSave2.visibility = View.GONE
                Log.d("error", "campo vuoto")
            } else {

                errorSave.visibility = View.GONE
                errorSave2.visibility = View.GONE

                if (diary == null) {

                    Log.d("bool", selectedDate.text.toString())
                    //salva la nuova pagina di diario
                    diaryModel.saveDiary(
                        titletext.text.toString(),
                        inputText.text.toString(),
                        selectedDate.text.toString(),
                        idImg
                    )

                } else {

                    //modifica la pagina di diario
                    diaryModel.changeDiary(
                        diary!!._id,
                        titletext.text.toString(),
                        inputText.text.toString(),
                        selectedDate.text.toString(),
                        idImg
                    )
                }

            }

            //serve per mostrare il pulsante "+" tornando a Diary Fragment
            DiaryFragment.istance?.showButton()
        }


        //torna indietro senza salvare
        closeBtn = binding.closeBtn

        closeBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
            //serve per mostrare il pulsante "+" tornando a Diary Fragment
            DiaryFragment.istance?.showButton()
        }


        //settare le variabili per modificarle
        diary?.let {
            titletext.setText(it._heading)
            inputText.setText(it._description)
            selectedDate.setText(it._data)
        }
    }


    //apri galleria telefono
    private fun launchGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) {
                return
            }
            filePath = data.data

            try {
                val bitmap =
                    MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, filePath)
                imagePreview.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

   //carica immagine
    private fun uploadImage(): String {
        if (filePath != null) {
            val progressDialog = ProgressDialog(requireContext())
            progressDialog.setTitle("Uploading...")
            progressDialog.show()

            val id = UUID.randomUUID()
            val ref = storageReference!!.child("images/" + id)
            ref.putFile(filePath!!)
                .addOnSuccessListener {
                    progressDialog.dismiss()
                }
                .addOnFailureListener { e ->
                    progressDialog.dismiss()

                }
                .addOnProgressListener { taskSnapshot ->
                    val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot
                        .totalByteCount
                    progressDialog.setMessage("Uploaded " + progress.toInt() + "%")

                }
        }

        return id.toString()
    }
}
