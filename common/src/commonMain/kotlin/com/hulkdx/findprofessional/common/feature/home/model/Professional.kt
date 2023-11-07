package com.hulkdx.findprofessional.common.feature.home.model

import com.hulkdx.findprofessional.common.utils.CommonParcelable
import com.hulkdx.findprofessional.common.utils.CommonParcelize
import kotlinx.serialization.Serializable

@CommonParcelize
@Serializable
data class Professional(
    val id: Int,
    val email: String,
    val firstName: String? = null,
    val lastName: String? = null,
    val coachType: String? = null,
    val priceNumber: Int? = null,
    val priceCurrency: String? = null,
    val profileImageUrl: String? = null,
    val rating: String? = null,
    val description: String? = null,
    val availability: List<ProfessionalAvailability>,
    // TODO: add to backend:
    val reviews: ProfessionalReview? = null,
) : CommonParcelable {
    val fullName: String
        get() = "$firstName $lastName"
    val price: String
        get() = "$priceCurrency $priceNumber"
}
