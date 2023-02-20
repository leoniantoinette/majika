package com.example.majika.fragment

import android.Manifest
import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import com.example.majika.R
import com.google.common.util.concurrent.ListenableFuture
import com.example.majika.permission.Permission
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

private const val var1 = "arg1"
private const val var2 = "arg2"
class Twibbon : Fragment() {

    private lateinit var content: Context
    private lateinit var prosesCamera: ListenableFuture<ProcessCameraProvider>
    private lateinit var textPopUp: TextView
    private lateinit var buttonTakePhoto: Button
    private lateinit var previewView: PreviewView
    private lateinit var previewImage: ImageView
    private lateinit var artis: ImageView
    private var arg1: String? = null
    private var arg2: String? = null
    private var imagePreview: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            arg1 = it.getString(var1)
            arg2 = it.getString(var2)
        }
        prosesCamera = ProcessCameraProvider.getInstance(content)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        // membuat layout
        viewInflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

    // membuat view inflater
        val view = viewInflater.inflate(R.layout.fragment_twibbon, container, false)

        previewView = view.findViewById(R.id.previewView)
        previewView.implementationMode = PreviewView.ImplementationMode.PERFORMANCE
        previewView.scaleType = PreviewView.ScaleType.FILL_CENTER
        previewView.visibility = View.VISIBLE
        previewImage = view.findViewById(R.id.imagePreview)
        artis = view.findViewById(R.id.twibbon)
        artis.visibility = View.VISIBLE

        // text pop up peringatan kameta
        textPopUp = view.findViewById(R.id.popUpTwibbon)
        textPopUp.visibility = View.GONE
        buttonTakePhoto = view.findViewById(R.id.buttoncapture)
        buttonTakePhoto.visibility = View.VISIBLE

        // Permission untuk mengakses kamera
        if (!Permission.isPermissionGranted(requireActivity() as AppCompatActivity,Manifest.permission.CAMERA)) {
            textPopUp.visibility = View.VISIBLE
            textPopUp.text = resources.getString(R.string.twibbon_warning_no_permissions)
            buttonTakePhoto.visibility = View.GONE
            previewView.visibility = View.GONE
            artis.visibility = View.GONE

            this.requestPermissions(
                arrayOf(Manifest.permission.CAMERA),
                Permission.getRequestCode()
            )
            return view
        }

        // jika gambar tidak ditampilkan maka lakukan hal berikut
        if (!imagePreview) {
            // melakukan capture gambar
            previewView.visibility = View.VISIBLE
            previewImage.visibility = View.GONE
            buttonTakePhoto.text = "Capture"
        } else {
            // melakukan preview image dan tampilkan button take again
            previewView.visibility = View.GONE
            previewImage.visibility = View.VISIBLE
            buttonTakePhoto.text = "Take Again"
        }

        // Yang dilakukan ketika menekan button take photo
        buttonTakePhoto.setOnClickListener {
            // jika image tidak sedang ditampilkan maka akan melakukan take photo
            // tombol belum diklik
            if (!imagePreview) {
                previewView.visibility = View.GONE
                previewImage.visibility = View.VISIBLE
                buttonTakePhoto.text = "Take Again"
                imagePreview = true
                val bitmap = previewView.bitmap
                previewImage.setImageBitmap(bitmap)
                Toast.makeText(content, "Gambar telah di capture", Toast.LENGTH_SHORT).show()


            } else {
                // jika gambar sudah diambil (tombol telah diklik)
                previewView.visibility = View.VISIBLE
                previewImage.visibility = View.GONE
                buttonTakePhoto.text = "Capture"
                imagePreview = false
            }
        }
        return view
    }

    private fun bindPreview(cameraProvider: ProcessCameraProvider) {
        val cameraSelector: CameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
            .build()


        val preview: Preview = Preview.Builder()
            .build()

        preview.setSurfaceProvider(previewView.surfaceProvider)

        cameraProvider.bindToLifecycle(content as LifecycleOwner, cameraSelector, preview)
    }

    override fun onStart() {
        super.onStart()
        prosesCamera.addListener({
            val cameraProvider = prosesCamera.get()
            bindPreview(cameraProvider)
        }, ContextCompat.getMainExecutor(content))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        content = context
    }

    override fun onDestroy() {
        super.onDestroy()
        prosesCamera.get().unbindAll()
    }


}
