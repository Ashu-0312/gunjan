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

    @POST(Constants.Partial.addCommunity)
    fun addCommunity(
        @Body params:HashMap<String,String>,
        @HeaderMap headers:Map<String,String>
    ): Call<AddCommunityResponse>

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
}