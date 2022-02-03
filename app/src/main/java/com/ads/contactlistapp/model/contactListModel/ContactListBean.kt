package com.ads.auronia.kotlinlearn1.model.contactListModel

//import com.google.firebase.firestore.Exclude
import java.io.Serializable

class ContactListBean(val contactId: String? = null,
                      val name: String? = null,
                      val nickName: String? = null,
                      val email: String? = null,
                      val phoneNo: String? = null,
                      val bloodGroup: String? = null): Serializable
{
//    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "contactId" to contactId,
            "name" to name,
            "nickName" to nickName,
            "email" to email,
            "phoneNo" to phoneNo,
            "bloodGroup" to bloodGroup
        )
    }
}