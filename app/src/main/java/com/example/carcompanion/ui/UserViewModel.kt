import androidx.lifecycle.ViewModel
import com.example.carcompanion.database.models.UserObject
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class UserViewModel : ViewModel() {
    var firebaseReference = Firebase.firestore.collection(UserObject.COLLECTION_PATH)
        .document(Firebase.auth.currentUser?.uid!!)

    var user: UserObject? = null //Allows local user to be null

    fun hasCompletedSetup(): Boolean {
        return user?.hasCompletedSetup ?: false
    }

    //Send in a callback observer function
    fun getOrMakeUser(observer: () -> Unit) {
        firebaseReference = Firebase.firestore.collection(UserObject.COLLECTION_PATH)
            .document(Firebase.auth.currentUser?.uid!!)
        if (user != null) {
            //done for now, user exists, need to get it
            observer()
        } else {
            //User does not exist, need to make it
            firebaseReference.get().addOnSuccessListener { snapshot: DocumentSnapshot ->
                if (snapshot.exists()) {
                    //If the snapshot does indeed exist
                    user = snapshot.toObject(UserObject::class.java)
                } else {
                    //There is no corresponding user on firebase
                    with(Firebase.auth.currentUser) {
                    //TODO Fix this, Emily broke it by not using a standardized format that can be parsed
                    var firstNameInput = this?.displayName?.split(" ")?.get(0)
                    var lastNameInput = this?.displayName?.split(" ")?.get(1)
                    user = UserObject(
                        email = this?.email!!,
                        firstName = firstNameInput!!,
                        lastName = lastNameInput!!,
                    ) }
                    //Push the new thing back to firebase
                    firebaseReference.set(user!!)
                }
                observer()
            }

        }
    }

    fun updateUser(
        emailIn: String,
        firstNameIn: String,
        lastNameIn: String,
        newHasCompletedSetup: Boolean
    ) {
        if (user != null) {
            with(user!!) {
                email = emailIn
                firstName = firstNameIn
                lastName = lastNameIn
                hasCompletedSetup = newHasCompletedSetup

//push the data up to firebase
                firebaseReference.set(this)
            }
        }
    }
}