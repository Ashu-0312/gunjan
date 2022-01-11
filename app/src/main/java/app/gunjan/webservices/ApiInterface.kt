package app.gunjan.webservices

import app.gunjan.entity.*
import retrofit2.Call
import retrofit2.http.*
import java.util.*
import kotlin.collections.HashMap


interface ApiInterface {

    @POST(Constants.Partial.completeProfile)
    fun completeProfile(
        @Body params:HashMap<String,String>,
        @HeaderMap headers:Map<String,String>
    ): Call<CompleteProfileResponse>

    @POST(Constants.Partial.editProfile)
    fun editProfile(
        @Body params:HashMap<String,String>,
        @HeaderMap headers:Map<String,String>
    ): Call<EditProfileResponse>

    @POST(Constants.Partial.addCommunity)
    fun addCommunity(
        @Body params:HashMap<String,String>,
        @HeaderMap headers:Map<String,String>
    ): Call<AddCommunityResponse>

    @POST(Constants.Partial.sendCommunityRequest)
    fun sendCommunityRequest(
        @Body params:HashMap<String,String>,
        @HeaderMap headers:Map<String,String>
    ): Call<SendCommunityRequestResponse>

    @PUT(Constants.Partial.addAboutYourself)
    fun addAboutYourself(
        @Body params:HashMap<String,String>,
        @HeaderMap headers:Map<String,String>
    ): Call<AddAboutResponse>

    @PUT(Constants.Partial.addIdenificationFile)
    fun addIdenificationFile(
        @Body params:HashMap<String,String>,
        @HeaderMap headers:Map<String,String>
    ): Call<AddIdentityResponse>

    @GET(Constants.Partial.login)
    fun login(
        @Query(Constants.Keys.language) language: String?,
        @Query(Constants.Keys.mobile) mobile: String?,
        @Query(Constants.Keys.countryCode) countryCode: String?,
        @Query(Constants.Keys.device_type) device_type: String?,
    ): Call<LoginResponse>

    @GET(Constants.Partial.signup)
    fun signup(
        @Query(Constants.Keys.language) language: String?,
        @Query(Constants.Keys.mobile) mobile: String?,
        @Query(Constants.Keys.countryCode) countryCode: String?,
        @Query(Constants.Keys.device_type) device_type: String?,
    ): Call<SignupResponse>

    @GET(Constants.Partial.verifyOtp)
    fun verifyOtp(
        @Query(Constants.Keys.language) language: String?,
        @Query(Constants.Keys.mobile) mobile: String?,
        @Query(Constants.Keys.countryCode) countryCode: String?,
        @Query(Constants.Keys.device_type) device_type: String?,
        @Query(Constants.Keys.code) code: String?,
        @Query(Constants.Keys.otp_for) otp_for: String?,
        @HeaderMap headers: Map<String, String>,
    ): Call<VerifyOtpResponse>

    @GET(Constants.Partial.resendOtp)
    fun resendOtp(
        @Query(Constants.Keys.language) language: String?,
        @Query(Constants.Keys.mobile) mobile: String?,
        @Query(Constants.Keys.countryCode) countryCode: String?,
        @Query(Constants.Keys.device_type) device_type: String?,
    ): Call<ResendOtpResponse>

    @GET(Constants.Partial.termAndConditions)
    fun termAndConditions(
        @Query(Constants.Keys.language) language: String?,
    ): Call<TermsResponse>

    @GET(Constants.Partial.privacyAndPolicy)
    fun privacyAndPolicy(
        @Query(Constants.Keys.language) language: String?,
    ): Call<PrivacyPolicyResponse>

    @GET(Constants.Partial.getAllCategoryList)
    fun getAllCategoryList(
        @HeaderMap headers: Map<String, String>,
    ): Call<CategoryListResponse>

    @GET(Constants.Partial.getUserDetails)
    fun getUserDetails(
        @HeaderMap headers: Map<String, String>,
    ): Call<UserDetailsResponse>

    @GET(Constants.Partial.getAllCommunityList)
    fun getAllCommunityList(
        @Query(Constants.Keys.page) page: String?,
        @Query(Constants.Keys.limit) limit: String?,
        @Query(Constants.Keys.search) search: String?,
        @Query(Constants.Keys.type) type: String?,
        @HeaderMap headers: Map<String, String>,
    ): Call<CommunityListResponse>

    @GET(Constants.Partial.getAllInterest)
    fun getAllInterest(
        @Query(Constants.Keys.page) page: String?,
        @Query(Constants.Keys.limit) limit: String?,
        @HeaderMap headers: Map<String, String>,
    ): Call<InterestListResponse>

    @GET(Constants.Partial.getCommunityDetails)
    fun getCommunityDetails(
        @Query(Constants.Keys.communityId) communityId: String?,
        @HeaderMap headers: Map<String, String>,
    ): Call<CommunityDetailsResponse>

    @GET(Constants.Partial.getAllCommunityRequest)
    fun getAllCommunityRequest(
        @Query(Constants.Keys.communityId) communityId: String?,
        @Query(Constants.Keys.page) page: String?,
        @Query(Constants.Keys.limit) limit: String?,
        @HeaderMap headers: Map<String, String>,
    ): Call<RequestListResponse>

    @GET(Constants.Partial.getNotificationList)
    fun getNotificationList(
        @Query(Constants.Keys.page) page: String?,
        @Query(Constants.Keys.limit) limit: String?,
        @HeaderMap headers: Map<String, String>,
    ): Call<NotificationListResponse>

    @PATCH(Constants.Partial.acceptRejectRequest)
    fun acceptRejectRequest(
        @Body params: java.util.HashMap<String, String>,
        @HeaderMap headers: Map<String, String>
    ): Call<AcceptRejectRequestResponse>
}