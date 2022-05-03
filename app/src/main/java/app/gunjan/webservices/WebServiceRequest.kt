package app.gunjan.webservices

import android.content.Context
import app.gunjan.activities.PostListResponse
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
        state: String,
        city: String,
        designation: String,
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
        params[Constants.Keys.state] = state
        params[Constants.Keys.city] = city
        params[Constants.Keys.designation] = designation
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
        state: String,
        city: String,
        designation: String,
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
        params[Constants.Keys.state] = state
        params[Constants.Keys.city] = city
        params[Constants.Keys.designation] = designation
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

    fun addPost(
        context: Context,
        description: String,
        file: String,
        content_type: String,
        feed_type: String,
        start_date: String,
        end_date: String,
        start_time: String,
        end_time: String,
        registrationResponseCallback: Callback<AddPostResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        val params = HashMap<String, String>()
        params[Constants.Keys.description] = description
        params[Constants.Keys.file] = file
        params[Constants.Keys.content_type] = content_type
        params[Constants.Keys.feed_type] = feed_type
        params[Constants.Keys.start_date] = start_date
        params[Constants.Keys.end_date] = end_date
        params[Constants.Keys.start_time] = start_time
        params[Constants.Keys.end_time] = end_time
        val registrationResponseCall: Call<AddPostResponse> =
            apiInterface.addPost(params,headers)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }

    fun addComment(
        context: Context,
        postId: String,
        commentType: String,
        message: String,
        registrationResponseCallback: Callback<AddCommentResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        val params = HashMap<String, String>()
        params[Constants.Keys.postId] = postId
        params[Constants.Keys.commentType] = commentType
        params[Constants.Keys.message] = message
        val registrationResponseCall: Call<AddCommentResponse> =
            apiInterface.addCommentOnPost(params,headers)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }

    fun addCommentOnReply(
        context: Context,
        commentId: String,
        commentType: String,
        message: String,
        registrationResponseCallback: Callback<AddReplyResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        val params = HashMap<String, String>()
        params[Constants.Keys.commentId] = commentId
        params[Constants.Keys.commentType] = commentType
        params[Constants.Keys.message] = message
        val registrationResponseCall: Call<AddReplyResponse> =
            apiInterface.addCommentOnReply(params,headers)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }

    fun deleteComment(
        context: Context,
        commentId: String,
        registrationResponseCallback: Callback<DeleteCommentResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        val params = HashMap<String, String>()
        params[Constants.Keys.commentId] = commentId
        val registrationResponseCall: Call<DeleteCommentResponse> =
            apiInterface.deletePostComments(params,headers)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }

    fun reportUser(
        context: Context,
        reportedUserId: String,
        reasonId: String,
        other_reason: String,
        registrationResponseCallback: Callback<ReportReasonResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        val params = HashMap<String, String>()
        params[Constants.Keys.reportedUserId] = reportedUserId
        params[Constants.Keys.reasonId] = reasonId
        params[Constants.Keys.other_reason] = other_reason
        val registrationResponseCall: Call<ReportReasonResponse> =
            apiInterface.reportUser(params,headers)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }

    fun followUser(
        context: Context,
        partnerId: String,
        registrationResponseCallback: Callback<FollowUserResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        val params = HashMap<String, String>()
        params[Constants.Keys.partnerId] = partnerId
        val registrationResponseCall: Call<FollowUserResponse> =
            apiInterface.followUser(params,headers)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }

    fun unFollowUser(
        context: Context,
        partnerId: String,
        registrationResponseCallback: Callback<UnfollowUserResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        val params = HashMap<String, String>()
        params[Constants.Keys.partnerId] = partnerId
        val registrationResponseCall: Call<UnfollowUserResponse> =
            apiInterface.unFollowUser(params,headers)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }

    fun addCommunityMember(
        context: Context,
        user_ids: String,
        registrationResponseCallback: Callback<AddMemberinGroupResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        val params = HashMap<String, String>()
        params[Constants.Keys.user_ids] = user_ids
        val registrationResponseCall: Call<AddMemberinGroupResponse> =
            apiInterface.addCommunityMember(params,headers)
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

    fun joinEvent(
        context: Context,
        postId: String,
        registrationResponseCallback: Callback<JoinEventResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        val params = HashMap<String, String>()
        params[Constants.Keys.postId] = postId
        val registrationResponseCall: Call<JoinEventResponse> =
            apiInterface.joinEvent(params,headers)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }


    fun likeDislikePost(
        context: Context,
        postId: String,
        like_type: String,
        isLiked: String,
        registrationResponseCallback: Callback<LikeDislikePostResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        val params = HashMap<String, String>()
        params[Constants.Keys.postId] = postId
        params[Constants.Keys.like_type] = like_type
        params[Constants.Keys.isLiked] = isLiked
        val registrationResponseCall: Call<LikeDislikePostResponse> =
            apiInterface.likePost(params,headers)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }

    fun likeDislikeComments(
        context: Context,
        commentId: String,
        like_type: String,
        isLiked: String,
        registrationResponseCallback: Callback<LikeDislikeCommentResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        val params = HashMap<String, String>()
        params[Constants.Keys.commentId] = commentId
        params[Constants.Keys.like_type] = like_type
        params[Constants.Keys.isLiked] = isLiked
        val registrationResponseCall: Call<LikeDislikeCommentResponse> =
            apiInterface.likeUnlikeComments(params,headers)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }

    fun likeUnlikeCommentReply(
        context: Context,
        commentId: String,
        like_type: String,
        isLiked: String,
        registrationResponseCallback: Callback<LikeUnlikeReplyResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        val params = HashMap<String, String>()
        params[Constants.Keys.replyId] = commentId
        params[Constants.Keys.like_type] = like_type
        params[Constants.Keys.isLiked] = isLiked
        val registrationResponseCall: Call<LikeUnlikeReplyResponse> =
            apiInterface.likeUnlikeCommentReply(params,headers)
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

    fun makeAdmin(
        context: Context,
        member_id: String,
        registrationResponseCallback: Callback<MakeAdminResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        val params = HashMap<String, String>()
        params[Constants.Keys.member_id] = member_id
        val registrationResponseCall: Call<MakeAdminResponse> =
            apiInterface.makeAdmin(params,headers)
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

    fun switchCommunity(
        context: Context,
        communityId: String,
        registrationResponseCallback: Callback<SwitchCommunityResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        val params = HashMap<String, String>()
        params[Constants.Keys.communityId] = communityId
        val registrationResponseCall: Call<SwitchCommunityResponse> =
            apiInterface.switchCommunity(params,headers)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }

    fun blockUnblockUser(
        context: Context,
        member_id: String,
        status: String,
        registrationResponseCallback: Callback<BlockUnblockUserResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        val params = HashMap<String, String>()
        params[Constants.Keys.member_id] = member_id
        params[Constants.Keys.status] = status
        val registrationResponseCall: Call<BlockUnblockUserResponse> =
            apiInterface.blockUnblockUser(params,headers)
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

    fun reasonList(
        context: Context,
        registrationResponseCallback: Callback<ReasonListResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        val registrationResponseCall: Call<ReasonListResponse> =
            apiInterface.getAllReportReason(headers)
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

    fun followingUserList(
        context: Context,
        page: String,
        limit: String,
        userId: String,
        registrationResponseCallback: Callback<FollowingListResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        var params = HashMap<String,String>()
        params[Constants.Keys.page] = page
        params[Constants.Keys.limit] = limit
        params[Constants.Keys.userId] = userId
        val registrationResponseCall: Call<FollowingListResponse> =
            apiInterface.followingUserList(page,limit,userId,headers)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }

    fun followerUserList(
        context: Context,
        page: String,
        limit: String,
        userId: String,
        registrationResponseCallback: Callback<FollowerListResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        var params = HashMap<String,String>()
        params[Constants.Keys.page] = page
        params[Constants.Keys.limit] = limit
        params[Constants.Keys.userId] = userId
        val registrationResponseCall: Call<FollowerListResponse> =
            apiInterface.followerUserList(page,limit,userId,headers)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }

    fun getAllMemberList(
        context: Context,
        page: String,
        limit: String,
        state: String,
        city: String,
        member_type: String,
        search: String,
        registrationResponseCallback: Callback<MemberListResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        var params = HashMap<String,String>()
        params[Constants.Keys.page] = page
        params[Constants.Keys.limit] = limit
        params[Constants.Keys.state] = state
        params[Constants.Keys.city] = city
        params[Constants.Keys.member_type] = member_type
        params[Constants.Keys.search] = search
        val registrationResponseCall: Call<MemberListResponse> =
            apiInterface.getAllMemberList(page,limit,state,city,member_type,search,headers)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }

    fun getStateList(
        context: Context,
        registrationResponseCallback: Callback<StateListResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        val registrationResponseCall: Call<StateListResponse> =
            apiInterface.getStateList(headers)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }

    fun getCityList(
        context: Context,
        state_code:String,
        registrationResponseCallback: Callback<CityListResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        var params = HashMap<String,String>()
        params[Constants.Keys.state_code] = state_code
        val registrationResponseCall: Call<CityListResponse> =
            apiInterface.getCityList(state_code,headers)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }

    fun pincodeList(
        context: Context,
        state:String,
        city:String,
        registrationResponseCallback: Callback<PincodeListResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        var params = HashMap<String,String>()
        params[Constants.Keys.state] = state
        params[Constants.Keys.city] = city
        val registrationResponseCall: Call<PincodeListResponse> =
            apiInterface.pincodeList(state,city,headers)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }

    fun otherUserProfile(
        context: Context,
        user_id: String,
        registrationResponseCallback: Callback<OtherUserDetailsResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        var params = HashMap<String,String>()
        params[Constants.Keys.user_id] = user_id
        val registrationResponseCall: Call<OtherUserDetailsResponse> =
            apiInterface.otherUserProfile(user_id,headers)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }

    fun getUnreadNotificationCount(
        context: Context,
        registrationResponseCallback: Callback<NotificationCountResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        val registrationResponseCall: Call<NotificationCountResponse> =
            apiInterface.getUnreadNotificationCount(headers)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }

    fun commentList(
        context: Context,
        postId: String,
        registrationResponseCallback: Callback<CommentListResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        var params = HashMap<String,String>()
        params[Constants.Keys.postId] = postId
        val registrationResponseCall: Call<CommentListResponse> =
            apiInterface.postCommentList(postId,headers)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }

    fun replyCommentList(
        context: Context,
        commentId: String,
        registrationResponseCallback: Callback<ReplyListResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        var params = HashMap<String,String>()
        params[Constants.Keys.commentId] = commentId
        val registrationResponseCall: Call<ReplyListResponse> =
            apiInterface.replyCommentList(commentId,headers)
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

    fun getAllBlockedMemberList(
        context: Context,
        page: String,
        limit: String,
        communityId: String,
        registrationResponseCallback: Callback<BlockedUserListResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        var params = HashMap<String,String>()
        params[Constants.Keys.page] = page
        params[Constants.Keys.limit] = limit
        params[Constants.Keys.communityId] = communityId
        val registrationResponseCall: Call<BlockedUserListResponse> =
            apiInterface.getAllBlockedMemberList(page,limit,communityId,headers)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }

    fun generateToken(
        context: Context,
        token_type: String,
        device: String,
        room: String,
        registrationResponseCallback: Callback<GenerateTokenResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        var params = HashMap<String,String>()
        params[Constants.Keys.token_type] = token_type
        params[Constants.Keys.device] = device
        params[Constants.Keys.room] = room
        val registrationResponseCall: Call<GenerateTokenResponse> =
            apiInterface.generateToken(token_type,device,room,headers)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }

    fun getGroupList(
        context: Context,
        registrationResponseCallback: Callback<GroupListResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        val registrationResponseCall: Call<GroupListResponse> =
            apiInterface.getGroupList(headers)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }

    fun getAllNonMemberList(
        context: Context,
        page: String,
        limit: String,
        registrationResponseCallback: Callback<AllMembersListResponse>
    ) {
        var params = HashMap<String,String>()
        params[Constants.Keys.page] = page
        params[Constants.Keys.limit] = limit
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        val registrationResponseCall: Call<AllMembersListResponse> =
            apiInterface.getAllNonMemberList(page,limit,headers)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }

    fun joinedUserList(
        context: Context,
        input: String,
        registrationResponseCallback: Callback<EventJoinedUsersResponse>
    ) {
        var params = HashMap<String,String>()
        params["input"] = input
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        val registrationResponseCall: Call<EventJoinedUsersResponse> =
            apiInterface.joinedUserList(input,headers)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }

    fun postList(
        context: Context,
        page: String,
        limit: String,
        badge_type: String,
        registrationResponseCallback: Callback<PostListResponse>
    ) {
        val headers= HashMap<String,String>()
        headers[Constants.Keys.token]=FCSharedPreferances.getSharedPreferance(context).token
        var params = HashMap<String,String>()
        params[Constants.Keys.page] = page
        params[Constants.Keys.limit] = limit
        params[Constants.Keys.badge_type] = badge_type
        val registrationResponseCall: Call<PostListResponse> =
            apiInterface.postList(page,limit,badge_type,headers)
        registrationResponseCall.enqueue(registrationResponseCallback)
    }
}

