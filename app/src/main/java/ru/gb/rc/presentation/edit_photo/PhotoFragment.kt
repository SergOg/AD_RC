package ru.gb.rc.presentation.edit_photo

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import ru.gb.rc.R
import ru.gb.rc.databinding.FragmentPhotoBinding
import java.util.concurrent.Executor

private const val FILENAME_FORMAT = "yyy-MM-dd-HH-mm-ss"

class PhotoFragment : Fragment() {

    companion object {
        fun newInstance() = PhotoFragment()
        private val REQUEST_PERMISSIONS: Array<String> = buildList {
            add(Manifest.permission.CAMERA)
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }.toTypedArray()
    }

    private var imageCapture: ImageCapture? = null
    private lateinit var executor: Executor
    private lateinit var binding: FragmentPhotoBinding

    private val viewModel: PhotoViewModel by viewModels()

    private val launcher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
            if (map.values.all { it }) {
                startCamera()
                Toast.makeText(context, "permission is Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "permission is not Granted", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermissions()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_photo, container, false)
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build()
            preview.surfaceProvider = binding.viewFinder.surfaceProvider
            imageCapture = ImageCapture.Builder().build()

            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                this,
                CameraSelector.DEFAULT_BACK_CAMERA,
                preview,
                imageCapture
            )
        }, executor)
    }

    private fun checkPermissions() {
        val isAllGranted = REQUEST_PERMISSIONS.all { permission ->
            ContextCompat.checkSelfPermission(
                requireContext(),
                permission
            ) == PackageManager.PERMISSION_GRANTED
        }
        if (isAllGranted) {
            startCamera()
        } else {
            launcher.launch(REQUEST_PERMISSIONS)
        }
    }
}