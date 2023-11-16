package com.example.firebaseloginkotlin

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.example.firebaseloginkotlin.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

import androidx.cardview.widget.CardView

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private val fileResult = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        updateUI()

        binding.updateProfileAppCompatButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()

            updateProfile(name)
        }

        binding.profileImageView.setOnClickListener {
            fileManager()
        }

        binding.deleteAccountTextView.setOnClickListener {
            val intent = Intent(this, DeleteAccountActivity::class.java)
            this.startActivity(intent)
        }

        binding.updatePasswordTextView.setOnClickListener {
            val intent = Intent(this, UpdatePasswordActivity::class.java)
            this.startActivity(intent)
        }

        binding.signOutImageView.setOnClickListener {
            signOut()
        }

    }

    private  fun updateProfile (name : String) {

        val user = auth.currentUser

        val profileUpdates = userProfileChangeRequest {
            displayName = name
        }

        user!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Se realizaron los cambios correctamente.",
                        Toast.LENGTH_SHORT).show()
                    updateUI()
                }
            }
    }

    private fun fileManager() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, fileResult)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == fileResult) {
            if (resultCode == RESULT_OK && data != null) {
                val uri = data.data

                uri?.let { imageUpload(it) }

            }
        }
    }

    private fun imageUpload(mUri: Uri) {

        val user = auth.currentUser
        val folder: StorageReference = FirebaseStorage.getInstance().reference.child("Users")
        val fileName: StorageReference = folder.child("img"+user!!.uid)

        fileName.putFile(mUri).addOnSuccessListener {
            fileName.downloadUrl.addOnSuccessListener { uri ->

                val profileUpdates = userProfileChangeRequest {
                    photoUri = Uri.parse(uri.toString())
                }

                user.updateProfile(profileUpdates)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Se realizaron los cambios correctamente.",
                                Toast.LENGTH_SHORT).show()
                            updateUI()
                        }
                    }
            }
        }.addOnFailureListener {
            Log.i("TAG", "file upload error")
        }
    }



    private  fun updateUI () {
        //Obtiene el usuario actualmente autenticado a través de auth.currentUser.
        val user = auth.currentUser
        //Si el usuario está autenticado (user != null), se establece el correo electrónico del
        // usuario en un TextView con la ID emailTextView.
        // Luego, verifica si el usuario tiene un nombre establecido (user.displayName != null).
        if (user != null){
            binding.emailTextView.text = user.email

            if(user.displayName != null){
                binding.nameTextView.text = user.displayName
                binding.nameEditText.setText(user.displayName)

                //Si el usuario tiene un nombre establecido, se establece ese nombre en otro TextView con la ID nameTextView,
                // y se coloca el mismo nombre en un EditText con la ID nameEditText.
                // Esto permite que el usuario vea su nombre actual y, si lo desea, lo edite.
            }

            Glide
                .with(this)
                .load(user.photoUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_profile)
                .into(binding.profileImageView)
            Glide
                .with(this)
                .load(user.photoUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_profile)
                .into(binding.bgProfileImageView)
        }

    }

    private  fun signOut(){
        auth.signOut()
        val intent = Intent(this, MainMenuActivity::class.java)
        this.startActivity(intent)
    }

}