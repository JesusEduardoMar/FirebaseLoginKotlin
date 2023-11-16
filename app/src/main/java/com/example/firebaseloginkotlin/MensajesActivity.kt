package com.example.firebaseloginkotlin

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MensajesActivity : AppCompatActivity() {
    private lateinit var messageListView: ListView
    private lateinit var messageEditText: EditText
    private lateinit var sendButton: Button
    private lateinit var messageAdapter: ArrayAdapter<String>

    private val messages: ArrayList<String> = ArrayList()

    private lateinit var database: DatabaseReference
    private lateinit var currentUserUid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mensajes)

        messageListView = findViewById(R.id.messageListView)
        messageEditText = findViewById(R.id.messageEditText)
        sendButton = findViewById(R.id.sendButton)

        messageAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, messages)
        messageListView.adapter = messageAdapter

        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUserUid = currentUser?.uid ?: ""

        database = FirebaseDatabase.getInstance().reference

        sendButton.setOnClickListener {
            val message = messageEditText.text.toString().trim()
            if (message.isNotEmpty()) {
                sendMessage(currentUserUid, message)
                messageEditText.text.clear()
            }
        }


        // Escuchar mensajes nuevos
        listenForMessages()
    }


    private fun sendMessage(senderUid: String, message: String) {
        val messageData = Message(senderUid, message)

        val messageKey = database.child("messages").push().key
        if (messageKey != null) {
            val messageValues = messageData.toMap()

            val childUpdates = hashMapOf<String, Any>(
                "/messages/$messageKey" to messageValues,
                "/user-messages/$senderUid/$messageKey" to messageValues
            )

            database.updateChildren(childUpdates)
                .addOnSuccessListener {
                    // Éxito al enviar el mensaje
                }
                .addOnFailureListener { e ->
                    // Error al enviar el mensaje
                    // Maneja el error según tus necesidades
                }
        }
    }


    private fun listenForMessages() {
        val userMessagesRef = database.child("user-messages").child(currentUserUid)

        userMessagesRef.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                val messageKey = dataSnapshot.key
                messageKey?.let { key ->
                    val messageRef = database.child("messages").child(key)

                    messageRef.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            val message = dataSnapshot.getValue(Message::class.java)
                            message?.let {
                                receiveMessage(it)
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            // Error al recuperar el mensaje
                            // Maneja el error según tus necesidades
                        }
                    })
                }
            }

            // Implementa los demás métodos de ChildEventListener según tus necesidades
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                // Empty implementation
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                // Empty implementation
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                // Empty implementation
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Empty implementation
            }
        })
    }

    private fun receiveMessage(message: Message) {
        val senderUid = message.senderUid
        val messageText = message.message

        val senderEmail = getSenderEmail(senderUid)// Obtén el correo electrónico del remitente si lo necesitas

        val formattedMessage = if (senderUid == currentUserUid) {
            "Yo: $messageText"
        } else {
            "Otro Usuario ($senderEmail): $messageText"
        }

        messages.add(formattedMessage)
        messageAdapter.notifyDataSetChanged()
    }

    private fun getSenderEmail(senderUid: String): String {
        var senderEmail = ""

        val userRef = FirebaseAuth.getInstance().currentUser?.uid?.let { uid ->
            FirebaseDatabase.getInstance().reference.child("users").child(uid)
        }

        userRef?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                senderEmail = dataSnapshot.child("email").value as? String ?: ""
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Error al recuperar el correo electrónico del remitente
                // Maneja el error según tus necesidades
            }
        })

        return senderEmail
    }

    private data class Message(
        val senderUid: String = "",
        val message: String = ""
    ) {
        fun toMap(): Map<String, Any?> {
            return mapOf(
                "senderUid" to senderUid,
                "message" to message
            )
        }
    }
}