package app.gunjan.webservices

import app.gunjan.activities.PostListResponse
import app.gunjan.entity.*
import okhttp3.MultipartBody
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

    @POST(Constants.Partial.addQuery)
    fun addQuery(
        @Body params:HashMap<String,String>,
        @HeaderMap headers:Map<String,String>
    ): Call<AddQueryResponse>

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

    @POST(Constants.Partial.likePost)
    fun likePost(
        @Body params:HashMap<String,String>,
        @HeaderMap headers:Map<String,String>
    ): Call<LikeDislikePostResponse>

    @POST(Constants.Partial.likeUnlikeComments)
    fun likeUnlikeComments(
        @Body params:HashMap<String,String>,
        @HeaderMap headers:Map<String,String>
    ): Call<LikeDislikeCommentResponse>

    @POST(Constants.Partial.likeUnlikeCommentReply)
    fun likeUnlikeCommentReply(
        @Body params:HashMap<String,String>,
        @HeaderMap headers:Map<String,String>
    ): Call<LikeUnlikeReplyResponse>

    @POST(Constants.Partial.addPost)
    fun addPost(
        @Body params:HashMap<String,String>,
        @HeaderMap headers:Map<String,String>
    ): Call<AddPostResponse>

    @POST(Constants.Partial.addCommentOnPost)
    fun addCommentOnPost(
        @Body params:HashMap<String,String>,
        @HeaderMap headers:Map<String,String>
    ): Call<AddCommentResponse>

    @POST(Constants.Partial.addCommentOnReply)
    fun addCommentOnReply(
        @Body params:HashMap<String,String>,
        @HeaderMap headers:Map<String,String>
    ): Call<AddReplyResponse>

    @POST(Constants.Partial.deletePostComments)
    fun deletePostComments(
        @Body params:HashMap<String,String>,
        @HeaderMap headers:Map<String,String>
    ): Call<DeleteCommentResponse>

    @POST(Constants.Partial.reportUser)
    fun reportUser(
        @Body params:HashMap<String,String>,
        @HeaderMap headers:Map<String,String>
    ): Call<ReportReasonResponse>

    @POST(Constants.Partial.followUser)
    fun followUser(
        @Body params:HashMap<String,String>,
        @HeaderMap headers:Map<String,String>
    ): Call<FollowUserResponse>

    @POST(Constants.Partial.unFollowUser)
    fun unFollowUser(
        @Body params:HashMap<String,String>,
        @HeaderMap headers:Map<String,String>
    ): Call<UnfollowUserResponse>

    @POST(Constants.Partial.addCommunityMember)
    fun addCommunityMember(
        @Body params:HashMap<String,String>,
        @HeaderMap headers:Map<String,String>
    ): Call<AddMemberinGroupResponse>

    @POST(Constants.Partial.joinEvent)
    fun joinEvent(
        @Body params:HashMap<String,String>,
        @HeaderMap headers:Map<String,String>
    ): Call<JoinEventResponse>

    @POST(Constants.Partial.addCoin)
    fun addCoin(
        @Body params:HashMap<String,String>,
        @HeaderMap headers:Map<String,String>
    ): Call<AddCoinResponse>

    @POST(Constants.Partial.addPostCoin)
    fun addPostCoin(
        @Body params:HashMap<String,String>,
        @HeaderMap headers:Map<String,String>
    ): Call<DonateCoinResponse>

    @POST(Constants.Partial.addSocialMedia)
    fun addSocialMedia(
        @Body params:HashMap<String,String>,
        @HeaderMap headers:Map<String,String>
    ): Call<AddSocialMediaResponse>

    @POST(Constants.Partial.generateCashFreeToken)
    fun generateCashFreeToken(
        @Body params: java.util.HashMap<String, String>,
        @HeaderMap headers: Map<String, String>
    ): Call<PaymentTokenGenerateResponse>

    @Multipart
    @POST(Constants.Partial.uploadFile)
    fun uploadFile(
        @Part file: MultipartBody.Part
    ): Call<UploadS3FileResponse>

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

    @PUT(Constants.Partial.updateDeviceToken)
    fun updateDeviceToken(
        @Body params:HashMap<String,String>,
        @HeaderMap headers:Map<String,String>
    ): Call<UpdateDeviceTokenResponse>

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

    @GET(Constants.Partial.communityGuideline)
    fun communityGuideline(
        @Query(Constants.Keys.language) language: String?,
    ): Call<CommunityGuidelineResponse>

    @GET(Constants.Partial.privacyAndPolicy)
    fun privacyAndPolicy(
        @Query(Constants.Keys.language) language: String?,
    ): Call<PrivacyPolicyResponse>

    @GET(Constants.Partial.getAllCategoryList)
    fun getAllCategoryList(
        @HeaderMap headers: Map<String, String>,
    ): Call<CategoryListResponse>

    @GET(Constants.Partial.getHelplineDetails)
    fun getHelplineDetails(
        @HeaderMap headers: Map<String, String>,
    ): Call<GetHelplineNumberResponse>

    @GET(Constants.Partial.logout)
    fun logout(
        @HeaderMap headers: Map<String, String>,
    ): Call<LogoutResponse>

    @GET(Constants.Partial.getAllReportReason)
    fun getAllReportReason(
        @HeaderMap headers: Map<String, String>,
    ): Call<ReasonListResponse>

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

    @GET(Constants.Partial.followingUserList)
    fun followingUserList(
        @Query(Constants.Keys.page) page: String?,
        @Query(Constants.Keys.limit) limit: String?,
        @Query(Constants.Keys.userId) userId: String?,
        @HeaderMap headers: Map<String, String>,
    ): Call<FollowingListResponse>

    @GET(Constants.Partial.followerUserList)
    fun followerUserList(
        @Query(Constants.Keys.page) page: String?,
        @Query(Constants.Keys.limit) limit: String?,
        @Query(Constants.Keys.userId) userId: String?,
        @HeaderMap headers: Map<String, String>,
    ): Call<FollowerListResponse>

    @GET(Constants.Partial.getAllMemberList)
    fun getAllMemberList(
        @Query(Constants.Keys.page) page: String?,
        @Query(Constants.Keys.limit) limit: String?,
        @Query(Constants.Keys.state) state: String?,
        @Query(Constants.Keys.city) city: String?,
        @Query(Constants.Keys.member_type) member_type: String?,
        @Query(Constants.Keys.search) search: String?,
        @HeaderMap headers: Map<String, String>,
    ): Call<MemberListResponse>

    @GET(Constants.Partial.stateList)
    fun getStateList(
        @HeaderMap headers: Map<String, String>,
    ): Call<StateListResponse>

    @GET(Constants.Partial.cityList)
    fun getCityList(
        @Query(Constants.Keys.state_code) state_code: String?,
        @HeaderMap headers: Map<String, String>,
    ): Call<CityListResponse>

    @GET(Constants.Partial.getFAQs)
    fun getFAQs(
        @Query(Constants.Keys.language) language: String?,
        @HeaderMap headers: Map<String, String>,
    ): Call<CoinFaqListResponse>

    @GET(Constants.Partial.pincodeList)
    fun pincodeList(
        @Query(Constants.Keys.state) state: String?,
        @Query(Constants.Keys.city) city: String?,
        @HeaderMap headers: Map<String, String>,
    ): Call<PincodeListResponse>

    @GET(Constants.Partial.getOtherUserDetails)
    fun otherUserProfile(
        @Query(Constants.Keys.user_id) user_id: String?,
        @HeaderMap headers: Map<String, String>,
    ): Call<OtherUserDetailsResponse>

    @GET(Constants.Partial.getUnreadNotificationCount)
    fun getUnreadNotificationCount(
        @HeaderMap headers: Map<String, String>,
    ): Call<NotificationCountResponse>

    @GET(Constants.Partial.postCommentList)
    fun postCommentList(
        @Query(Constants.Keys.postId) postId: String?,
        @HeaderMap headers: Map<String, String>,
    ): Call<CommentListResponse>

    @GET(Constants.Partial.replyCommentList)
    fun replyCommentList(
        @Query(Constants.Keys.commentId) commentId: String?,
        @HeaderMap headers: Map<String, String>,
    ): Call<ReplyListResponse>

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

    @GET(Constants.Partial.postList)
    fun postList(
        @Query(Constants.Keys.page) page: String?,
        @Query(Constants.Keys.limit) limit: String?,
        @Query(Constants.Keys.badge_type) badge_type: String?,
        @HeaderMap headers: Map<String, String>,
    ): Call<PostListResponse>

    @GET(Constants.Partial.getNotificationList)
    fun getNotificationList(
        @Query(Constants.Keys.page) page: String?,
        @Query(Constants.Keys.limit) limit: String?,
        @HeaderMap headers: Map<String, String>,
    ): Call<NotificationListResponse>

    @GET(Constants.Partial.getAllBlockedMemberList)
    fun getAllBlockedMemberList(
        @Query(Constants.Keys.page) page: String?,
        @Query(Constants.Keys.limit) limit: String?,
        @Query(Constants.Keys.communityId) communityId: String?,
        @HeaderMap headers: Map<String, String>,
    ): Call<BlockedUserListResponse>

    @GET(Constants.Partial.generateToken)
    fun generateToken(
        @Query(Constants.Keys.token_type) token_type: String?,
        @Query(Constants.Keys.device) device: String?,
        @Query(Constants.Keys.room) room: String?,
        @HeaderMap headers: Map<String, String>
    ): Call<GenerateTokenResponse>

    @GET(Constants.Partial.getGroupList)
    fun getGroupList(
        @HeaderMap headers: Map<String, String>
    ): Call<GroupListResponse>

    @GET(Constants.Partial.getAllNonMemberList)
    fun getAllNonMemberList(
        @Query(Constants.Keys.page) page: String?,
        @Query(Constants.Keys.limit) limit: String?,
        @HeaderMap headers: Map<String, String>
    ): Call<AllMembersListResponse>

    @GET("post/joinedMemberList/{input}")
    fun joinedUserList(
        @Path("input") input: String?,
        @HeaderMap headers: Map<String, String>
    ): Call<EventJoinedUsersResponse>

    @GET(Constants.Partial.getAllDonationList)
    fun getAllDonationList(
        @Query(Constants.Keys.type) type: String?,
        @HeaderMap headers: Map<String, String>
    ): Call<DonationListResponse>

    @GET(Constants.Partial.getAllDonationList)
    fun getAllReceivedDonationList(
        @Query(Constants.Keys.type) type: String?,
        @HeaderMap headers: Map<String, String>
    ): Call<ReceivedCoinListResponse>

    @PATCH(Constants.Partial.acceptRejectRequest)
    fun acceptRejectRequest(
        @Body params: java.util.HashMap<String, String>,
        @HeaderMap headers: Map<String, String>
    ): Call<AcceptRejectRequestResponse>

    @PATCH(Constants.Partial.reportComment)
    fun reportComment(
        @Body params: java.util.HashMap<String, String>,
        @HeaderMap headers: Map<String, String>
    ): Call<ReportCommentRes>

    @PATCH(Constants.Partial.reportPost)
    fun reportPost(
        @Body params: java.util.HashMap<String, String>,
        @HeaderMap headers: Map<String, String>
    ): Call<ReportCommentRes>

    @PATCH(Constants.Partial.makeAdmin)
    fun makeAdmin(
        @Body params: java.util.HashMap<String, String>,
        @HeaderMap headers: Map<String, String>
    ): Call<MakeAdminResponse>

    @PATCH(Constants.Partial.deleteAccount)
    fun deleteAccount(
        @HeaderMap headers: Map<String, String>
    ): Call<DeleteAccountResponse>

    @PATCH(Constants.Partial.leaveCommunity)
    fun leaveCommunity(
        @Body params: java.util.HashMap<String, String>,
        @HeaderMap headers: Map<String, String>
    ): Call<LeaveCommunityResponse>

    @PATCH(Constants.Partial.switchCommunity)
    fun switchCommunity(
        @Body params: java.util.HashMap<String, String>,
        @HeaderMap headers: Map<String, String>
    ): Call<SwitchCommunityResponse>

    @PATCH(Constants.Partial.blockUnblockUser)
    fun blockUnblockUser(
        @Body params: java.util.HashMap<String, String>,
        @HeaderMap headers: Map<String, String>
    ): Call<BlockUnblockUserResponse>
}