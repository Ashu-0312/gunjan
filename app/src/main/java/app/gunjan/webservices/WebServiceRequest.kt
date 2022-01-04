package app.gunjan.webservices

import android.content.Context
import app.gunjan.entity.*
import app.gunjan.utill.FCSharedPreferances
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback

class WebServiceRequest private constructor() {
    private val apiInterface =
        ApiClient.getClient(Constants.Partial.BASE_URL)!!.create(ApiInterface::class.java)

    companion object {
        private var webServiceRequest: WebServiceRequest? = null

        fun getInstance(): WebServiceRequest {
            if (webServiceRequest == null) {
                webServiceRequest = WebServiceRequest()
                return webServiceRequest as WebServiceRequest
            } else {
                return webServiceRequest as WebServiceRequest
            }
        }
    }

    fun toRequestBody(value: String): RequestBody {
        return RequestBody.create(MultipartBody.FORM, value)
    }

    fun login(
        language: String,
        mobile: String,
        countryCode: String,
        device_type: String,
        registrationResponseCallback: Callback<LoginResponse>
    ) {
        val params = HashMap<String, String>()
        params[Constants.Keys.language] = language
        params[Constants.Keys.mobile] = mobile
        params[Constants.Keys.countryCode] = countryCode
        params[Constants.Keys.device_type] = device_type
        val registrationResponseCall: Call<LoginResponse> =
            apiInterface.login(language, mobile, countryCode,device_type)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }

    fun signup(
        language: String,
        mobile: String,
        countryCode: String,
        device_type: String,
        registrationResponseCallback: Callback<SignupResponse>
    ) {
        val params = HashMap<String, String>()
        params[Constants.Keys.language] = language
        params[Constants.Keys.mobile] = mobile
        params[Constants.Keys.countryCode] = countryCode
        params[Constants.Keys.device_type] = device_type
        val registrationResponseCall: Call<SignupResponse> =
            apiInterface.signup(language, mobile, countryCode,device_type)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }

    fun verifyOtp(
        language: String,
        mobile: String,
        countryCode: String,
        device_type: String,
        code: String,
        registrationResponseCallback: Callback<VerifyOtpResponse>
    ) {
        val params = HashMap<String, String>()
        params[Constants.Keys.language] = language
        params[Constants.Keys.mobile] = mobile
        params[Constants.Keys.countryCode] = countryCode
        params[Constants.Keys.device_type] = device_type
        params[Constants.Keys.code] = code
        val registrationResponseCall: Call<VerifyOtpResponse> =
            apiInterface.verifyOtp(language, mobile,countryCode,device_type,code)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }

    fun resendOtp(
        language: String,
        mobile: String,
        countryCode: String,
        device_type: String,
        registrationResponseCallback: Callback<ResendOtpResponse>
    ) {
        val params = HashMap<String, String>()
        params[Constants.Keys.language] = language
        params[Constants.Keys.mobile] = mobile
        params[Constants.Keys.countryCode] = countryCode
        params[Constants.Keys.device_type] = device_type
        val registrationResponseCall: Call<ResendOtpResponse> =
            apiInterface.resendOtp(language, mobile, countryCode,device_type)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }

    fun privacyPolicy(
        language: String,
        registrationResponseCallback: Callback<PrivacyPolicyResponse>
    ) {
        val params = HashMap<String, String>()
        params[Constants.Keys.language] = language
        val registrationResponseCall: Call<PrivacyPolicyResponse> =
            apiInterface.privacyAndPolicy(language)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }

    fun termAndConditions(
        language: String,
        registrationResponseCallback: Callback<TermsResponse>
    ) {
        val params = HashMap<String, String>()
        params[Constants.Keys.language] = language
        val registrationResponseCall: Call<TermsResponse> =
            apiInterface.termAndConditions(language)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }

    fun completeProfile(
        context: Context,
        profile_name: String,
        first_name: String,
        last_name: String,
        device_type: String,
        language: String,
        image: String,
        pincode: String,
        registrationResponseCallback: Callback<CompleteProfileResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        val params = HashMap<String, String>()
        params[Constants.Keys.profile_name] = profile_name
        params[Constants.Keys.first_name] = first_name
        params[Constants.Keys.last_name] = last_name
        params[Constants.Keys.device_type] = device_type
        params[Constants.Keys.language] = language
        params[Constants.Keys.image] = image
        params[Constants.Keys.pincode] = pincode
        val registrationResponseCall: Call<CompleteProfileResponse> =
            apiInterface.completeProfile(params,headers)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }

    fun addCommunity(
        context: Context,
        title: String,
        about: String,
        category: String,
        image: String,
        registrationResponseCallback: Callback<AddCommunityResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        val params = HashMap<String, String>()
        params[Constants.Keys.title] = title
        params[Constants.Keys.about] = about
        params[Constants.Keys.category] = category
        params[Constants.Keys.image] = image
        val registrationResponseCall: Call<AddCommunityResponse> =
            apiInterface.addCommunity(params,headers)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }

    fun addAboutYourself(
        context: Context,
        about: String,
        registrationResponseCallback: Callback<AddAboutResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        val params = HashMap<String, String>()
        params[Constants.Keys.about] = about
        val registrationResponseCall: Call<AddAboutResponse> =
            apiInterface.addAboutYourself(params,headers)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }

    fun addIdenificationFile(
        context: Context,
        identification_file: String,
        registrationResponseCallback: Callback<AddIdentityResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        val params = HashMap<String, String>()
        params[Constants.Keys.identification_file] = identification_file
        val registrationResponseCall: Call<AddIdentityResponse> =
            apiInterface.addIdenificationFile(params,headers)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }

    fun categoryList(
        context: Context,
        registrationResponseCallback: Callback<CategoryListResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        val registrationResponseCall: Call<CategoryListResponse> =
            apiInterface.getAllCategoryList(headers)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }
}

