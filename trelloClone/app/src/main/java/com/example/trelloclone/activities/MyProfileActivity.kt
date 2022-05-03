package com.example.trelloclone.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.trelloclone.R
import com.example.trelloclone.databinding.ActivityMyProfileBinding
import com.example.trelloclone.firebase.FireStoreClass
import com.example.trelloclone.models.User
import com.example.trelloclone.utils.Constants
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class MyProfileActivity : BaseActivity() {
    companion object {
        private const val READ_STORAGE_PERMISSION_CODE = 1
        private const val PICK_IMAGE_REQUEST_CODE = 2
    }

    private lateinit var mUserDetails: User
    private var mSelectedUserImage: Uri? = null
    private var mProfileImageDownload: String = ""
    private var binding: ActivityMyProfileBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyProfileBinding.inflate(layoutInflater)

        setContentView(binding?.root)
        setupActionBar()

        FireStoreClass().loadUserData(this)

        binding?.ivUserImage?.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                showImageChooser()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    READ_STORAGE_PERMISSION_CODE
                )
            }
        }

        binding?.btnUpdate?.setOnClickListener {
            if (mSelectedUserImage != null) {
                uploadUserImage()
            }
            updateUserProfileData()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == READ_STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showImageChooser()
            } else {

            }
        }
    }

    private fun showImageChooser() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE_REQUEST_CODE && data!!.data != null) {
            mSelectedUserImage = data.data
        }

        Glide.with(this@MyProfileActivity).load(mSelectedUserImage).centerCrop()
            .placeholder(R.drawable.ic_user_place_holder).into(binding?.ivUserImage!!)
    }

    private fun setupActionBar() {
        setSupportActionBar(binding?.toolbarMyProfileActivity)

        val actionBar = supportActionBar

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
            actionBar.title = resources.getString(R.string.my_profile)
        }

        binding?.toolbarMyProfileActivity?.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun updateUserProfileData() {
        val userHashMap = HashMap<String, Any>()

        if (mProfileImageDownload.isNotEmpty() && mProfileImageDownload != mUserDetails.image) {
            userHashMap[Constants.IMAGE] = mProfileImageDownload
        }
        if (binding?.etMobile.toString() != mUserDetails.mobile) {
            userHashMap[Constants.MOBILE] = binding?.etMobile.toString()
        }
        if (binding?.etName.toString() != mUserDetails.name) {
            userHashMap[Constants.NAME] = binding?.etName.toString()
        }

        FireStoreClass().updateUserProfileData(this, userHashMap)
    }

    fun setupUI(user: User) {
        mUserDetails = user

        binding?.ivUserImage?.let {
            Glide.with(this@MyProfileActivity).load(user.image).centerCrop()
                .placeholder(R.drawable.ic_user_place_holder).into(it)
        }

        binding?.etName?.setText(user.name)
        binding?.etEmail?.setText(user.email)
        binding?.etMobile?.setText(user.mobile.toString())
    }

    private fun uploadUserImage() {
        showProgressDialog(resources.getString(R.string.please_wait))

        if (mSelectedUserImage != null) {
            val storageReference: StorageReference =
                FirebaseStorage.getInstance().reference.child(
                    "USER_IMAGE" + System.currentTimeMillis() + "." + getFileExtension(
                        mSelectedUserImage
                    )
                )

            storageReference.putFile(mSelectedUserImage!!).addOnSuccessListener { taskSnapshot ->
                taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener { uri ->

                    mProfileImageDownload = uri.toString()
                    hideProgressDialog()

                    updateUserProfileData()
                }
            }
        }
    }

    private fun getFileExtension(uri: Uri?): String? {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(contentResolver.getType(uri!!))
    }

    fun profileUpdateSuccess() {
        hideProgressDialog()
        setResult(Activity.RESULT_OK)
        finish()
    }
}