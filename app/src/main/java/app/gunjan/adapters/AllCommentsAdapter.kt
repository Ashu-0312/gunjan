package app.gunjan.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.gunjan.R
import app.gunjan.entity.CommentListResponse
import app.gunjan.entity.LikeDislikeCommentResponse
import app.gunjan.fragments.HomeFragment
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllCommentsAdapter(
    var context: Context?,
    data: MutableList<CommentListResponse.DataBean.CommentsBean>,
    homeFragment: HomeFragment
) :RecyclerView.Adapter<AllCommentsAdapter.ViewHolder>() {
    private var data: MutableList<CommentListResponse.DataBean.CommentsBean> = data
    private var homeFragment: HomeFragment?=homeFragment
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View = layoutInflater.inflate(R.layout.comment_item, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            context?.let { Glide.with(it).load(data[position].commented_by.image).placeholder(R.drawable.user_avatar).into(holder.pic!!) }
            holder.name!!.text=data[position].commented_by.first_name+" "+data[position].commented_by.last_name
            holder.comment!!.text=data[position].message
            holder.totalLike!!.text=data[position].total_like.toString()
            holder.totaldisLike!!.text=data[position].total_unlike.toString()
        }catch (e:Exception){}

        holder.like!!.setOnClickListener {
            val myDialog = ProjectUtill.showProgressDialog(context)
            context?.let { it1 ->
                WebServiceRequest.getInstance().likeDislikeComments(
                    it1,data[position].id.toString(),"love","1",
                    object : Callback<LikeDislikeCommentResponse> {
                        override fun onResponse(
                            call: Call<LikeDislikeCommentResponse>,
                            response: Response<LikeDislikeCommentResponse>
                        ) {
                            myDialog.dismiss()
                            if (response != null) {
                                if (response.isSuccessful) {
                                    if (response.body()!!.code == 1) {
                                        holder.totalLike!!.text=response.body()!!.data.total_like.toString()
                                        holder.totaldisLike!!.text=response.body()!!.data.total_unlike.toString()
                                    } else {
                                        ProjectUtill.printMessage(
                                            (context as Activity).window.decorView,
                                            response.body()?.message
                                        )
                                    }
                                } else {
                                    ProjectUtill.printErrorMessage(
                                        (context as Activity).window.decorView,
                                        ""
                                    )
                                }
                            } else {
                                ProjectUtill.printErrorMessage(
                                    (context as Activity).window.decorView,
                                    ""
                                )
                            }
                        }

                        override fun onFailure(
                            call: Call<LikeDislikeCommentResponse>,
                            t: Throwable
                        ) {
                            myDialog.dismiss()
                            ProjectUtill.printErrorMessage(
                                (context as Activity).window.decorView,
                                ""
                            )
                        }
                    })
            }
        }

        holder.dislike!!.setOnClickListener {
            val myDialog = ProjectUtill.showProgressDialog(context)
            context?.let { it1 ->
                WebServiceRequest.getInstance().likeDislikeComments(
                    it1,data[position].id.toString(),"love","0",
                    object : Callback<LikeDislikeCommentResponse> {
                        override fun onResponse(
                            call: Call<LikeDislikeCommentResponse>,
                            response: Response<LikeDislikeCommentResponse>
                        ) {
                            myDialog.dismiss()
                            if (response != null) {
                                if (response.isSuccessful) {
                                    if (response.body()!!.code == 1) {
                                        holder.totalLike!!.text=response.body()!!.data.total_like.toString()
                                        holder.totaldisLike!!.text=response.body()!!.data.total_unlike.toString()
                                    } else {
                                        ProjectUtill.printMessage(
                                            (context as Activity).window.decorView,
                                            response.body()?.message
                                        )
                                    }
                                } else {
                                    ProjectUtill.printErrorMessage(
                                        (context as Activity).window.decorView,
                                        ""
                                    )
                                }
                            } else {
                                ProjectUtill.printErrorMessage(
                                    (context as Activity).window.decorView,
                                    ""
                                )
                            }
                        }

                        override fun onFailure(
                            call: Call<LikeDislikeCommentResponse>,
                            t: Throwable
                        ) {
                            myDialog.dismiss()
                            ProjectUtill.printErrorMessage(
                                (context as Activity).window.decorView,
                                ""
                            )
                        }
                    })
            }
        }

        holder.deleteComment!!.setOnLongClickListener {
            homeFragment!!.deleteCommentDialog(data[position].id.toString())
             true
        }
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pic:CircleImageView?=null
        var name:TextView?=null
        var comment:TextView?=null
        var totalLike: TextView? =null
        var totaldisLike: TextView? =null
        var like: ImageView? =null
        var dislike: ImageView? =null
        var deleteComment: LinearLayout? =null
        init {
            pic=itemView.findViewById(R.id.user_pic)
            name=itemView.findViewById(R.id.name)
            comment=itemView.findViewById(R.id.comment)
            totalLike=itemView.findViewById(R.id.total_like)
            totaldisLike=itemView.findViewById(R.id.total_dislike)
            like=itemView.findViewById(R.id.like)
            dislike=itemView.findViewById(R.id.dislike)
            deleteComment=itemView.findViewById(R.id.delete_comment)
        }
    }

}