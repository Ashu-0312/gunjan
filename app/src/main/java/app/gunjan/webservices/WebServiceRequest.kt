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
        context: Context,
        language: String,
        mobile: String,
        countryCode: String,
        device_type: String,
        code: String,
        otp_for: String,
        registrationResponseCallback: Callback<VerifyOtpResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        val params = HashMap<String, String>()
        params[Constants.Keys.language] = language
        params[Constants.Keys.mobile] = mobile
        params[Constants.Keys.countryCode] = countryCode
        params[Constants.Keys.device_type] = device_type
        params[Constants.Keys.code] = code
        params[Constants.Keys.otp_for] = otp_for
        val registrationResponseCall: Call<VerifyOtpResponse> =
            apiInterface.verifyOtp(language, mobile,countryCode,device_type,code,otp_for,headers)
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

    fun addQuery(
        context: Context,
        subject: String,
        query: String,
        registrationResponseCallback: Callback<AddQueryResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        val params = HashMap<String, String>()
        params[Constants.Keys.subject] = subject
        params[Constants.Keys.query] = query
        val registrationResponseCall: Call<AddQueryResponse> =
            apiInterface.addQuery(params,headers)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }

    fun editProfile(
        context: Context,
        profile_name: String,
        first_name: String,
        last_name: String,
        device_type: String,
        language: String,
        image: String,
        pincode: String,
        email: String,
        dob: String,
        interests: String,
        mobile: String,
        countryCode: String,
        gender: String,
        about: String,
        registrationResponseCallback: Callback<EditProfileResponse>
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
        params[Constants.Keys.email] = email
        params[Constants.Keys.dob] = dob
        params[Constants.Keys.interests] = interests
        params[Constants.Keys.mobile] = mobile
        params[Constants.Keys.countryCode] = countryCode
        params[Constants.Keys.gender] = gender
        params[Constants.Keys.about] = about
        val registrationResponseCall: Call<EditProfileResponse> =
            apiInterface.editProfile(params,headers)
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


    fun sendCommunityRequest(
        context: Context,
        communityId: String,
        registrationResponseCallback: Callback<SendCommunityRequestResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        val params = HashMap<String, String>()
        params[Constants.Keys.communityId] = communityId
        val registrationResponseCall: Call<SendCommunityRequestResponse> =
            apiInterface.sendCommunityRequest(params,headers)
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

    fun updateDeviceToken(
        context: Context,
        device_token: String,
        device_type: String,
        language: String,
        registrationResponseCallback: Callback<UpdateDeviceTokenResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        val params = HashMap<String, String>()
        params[Constants.Keys.device_token] = device_token
        params[Constants.Keys.device_type] = device_type
        params[Constants.Keys.language] = language
        val registrationResponseCall: Call<UpdateDeviceTokenResponse> =
            apiInterface.updateDeviceToken(params,headers)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }

    fun acceptRejectRequest(
        context: Context,
        request_id: String,
        status: String,
        registrationResponseCallback: Callback<AcceptRejectRequestResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        val params = HashMap<String, String>()
        params[Constants.Keys.request_id] = request_id
        params[Constants.Keys.status] = status
        val registrationResponseCall: Call<AcceptRejectRequestResponse> =
            apiInterface.acceptRejectRequest(params,headers)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }

    fun deleteAccount(
        context: Context,
        registrationResponseCallback: Callback<DeleteAccountResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        val registrationResponseCall: Call<DeleteAccountResponse> =
            apiInterface.deleteAccount(headers)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }

    fun leaveCommunity(
        context: Context,
        communityId: String,
        registrationResponseCallback: Callback<LeaveCommunityResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        val params = HashMap<String, String>()
        params[Constants.Keys.communityId] = communityId
        val registrationResponseCall: Call<LeaveCommunityResponse> =
            apiInterface.leaveCommunity(params,headers)
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

    fun getHelplineDetails(
        context: Context,
        registrationResponseCallback: Callback<GetHelplineNumberResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        val registrationResponseCall: Call<GetHelplineNumberResponse> =
            apiInterface.getHelplineDetails(headers)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }

    fun logout(
        context: Context,
        registrationResponseCallback: Callback<LogoutResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        val registrationResponseCall: Call<LogoutResponse> =
            apiInterface.logout(headers)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }

    fun userDetails(
        context: Context,
        registrationResponseCallback: Callback<UserDetailsResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        val registrationResponseCall: Call<UserDetailsResponse> =
            apiInterface.getUserDetails(headers)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }

    fun getAllCommunityList(
        context: Context,
        page: String,
        limit: String,
        search: String,
        type: String,
        registrationResponseCallback: Callback<CommunityListResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        var params = HashMap<String,String>()
        params[Constants.Keys.page] = page
        params[Constants.Keys.limit] = limit
        params[Constants.Keys.search] = search
        params[Constants.Keys.type] = type
        val registrationResponseCall: Call<CommunityListResponse> =
            apiInterface.getAllCommunityList(page,limit,search,type,headers)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }

    fun getAllInterest(
        context: Context,
        page: String,
        limit: String,
        registrationResponseCallback: Callback<InterestListResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        var params = HashMap<String,String>()
        params[Constants.Keys.page] = page
        params[Constants.Keys.limit] = limit
        val registrationResponseCall: Call<InterestListResponse> =
            apiInterface.getAllInterest(page,limit,headers)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }

    fun getCommunityDetails(
        context: Context,
        communityId: String,
        registrationResponseCallback: Callback<CommunityDetailsResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        var params = HashMap<String,String>()
        params[Constants.Keys.communityId] = communityId
        val registrationResponseCall: Call<CommunityDetailsResponse> =
            apiInterface.getCommunityDetails(communityId,headers)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }

    fun getAllCommunityRequest(
        context: Context,
        communityId: String,
        page: String,
        limit: String,
        registrationResponseCallback: Callback<RequestListResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        var params = HashMap<String,String>()
        params[Constants.Keys.communityId] = communityId
        params[Constants.Keys.page] = page
        params[Constants.Keys.limit] = limit
        val registrationResponseCall: Call<RequestListResponse> =
            apiInterface.getAllCommunityRequest(communityId,page,limit,headers)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }

    fun getNotificationList(
        context: Context,
        page: String,
        limit: String,
        registrationResponseCallback: Callback<NotificationListResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        var params = HashMap<String,String>()
        params[Constants.Keys.page] = page
        params[Constants.Keys.limit] = limit
        val registrationResponseCall: Call<NotificationListResponse> =
            apiInterface.getNotificationList(page,limit,headers)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }
}

