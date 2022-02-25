package app.gunjan.twilio;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.twilio.chat.CallbackListener;
import com.twilio.chat.Channel;
import com.twilio.chat.ChannelDescriptor;
import com.twilio.chat.ChannelListener;
import com.twilio.chat.ChatClient;
import com.twilio.chat.ChatClientListener;
import com.twilio.chat.ErrorInfo;
import com.twilio.chat.Member;
import com.twilio.chat.Message;
import com.twilio.chat.Paginator;
import com.twilio.chat.ProgressListener;
import com.twilio.chat.StatusListener;
import com.twilio.chat.User;

import org.jetbrains.annotations.Nullable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import app.gunjan.entity.GenerateTokenResponse;
import app.gunjan.utill.FCSharedPreferances;
import app.gunjan.webservices.WebServiceRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuickstartChatManager extends AppCompatActivity {

    // This is the unique name of the chat channel we are using
    private final static String DEFAULT_CHANNEL_NAME = "general";

    final public ArrayList<Message> messagesList = new ArrayList<>();
    ArrayList<MyChannelModel> channelList = new ArrayList<>();
    private ChatClient chatClient;
    private QuickstartChatManagerListener chatManagerListener;
    private ClientCreated clientCreatedListener;
    private ChannelCreated channelCreatedListener;
    private MessagesFetched messageFetchedListener;

    private String token = "";
    public Channel channel;
    String memberName, fcmToken;


    public void retrieveAccessTokenFromServer(final Context context, String token,
                                              final
                                              TokenResponseListener listener, String fcmToken) {

        this.token = token;
        this.fcmToken = fcmToken;
        new Thread(new Runnable() {
            @Override
            public void run() {
                retrieveToken(new AccessTokenListener() {
                    @Override
                    public void receivedAccessToken(@Nullable String token,
                                                    @Nullable Exception exception) {
                        if (token != null) {
                            Log.d("TOKENNNNN:::::::::", ":::" + token);
                            ChatClient.Properties.Builder builder = new ChatClient.Properties.Builder();
//                        builder.setDeferCertificateTrustToPlatform(true);

                            builder.setRegion("us1");
                            ChatClient.Properties props = builder.createProperties();
                           ChatClient.create(context, FCSharedPreferances.getSharedPreferance(context).getCHAT_TOKEN(), props, mChatClientCallback);
                            listener.receivedTokenResponse(true, null);
                        } else {
                            listener.receivedTokenResponse(false, exception);
                        }
                    }
                });
            }
        }).start();
    }

    private void retrieveToken(final AccessTokenListener listener) {

        WebServiceRequest.Companion.getInstance().generateToken(QuickstartChatManager.this,
                "chat","","",new Callback<GenerateTokenResponse>() {
            @Override
            public void onResponse(Call<GenerateTokenResponse> call, Response<GenerateTokenResponse> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                            Log.d("tokengenerated", response.body().getData().getToken().getToken());
                            listener.receivedAccessToken(response.body().getData().getToken().getToken(), null);
                    } else {
                        listener.receivedAccessToken(null, null);
                    }
                } else {
                    listener.receivedAccessToken(null, null);
                }
            }

            @Override
            public void onFailure(Call<GenerateTokenResponse> call, Throwable t) {
                listener.receivedAccessToken(null, null);
            }
        });
    }

    public void sendChatMessage(String messageBody) {
        if (channel != null) {
            Message.Options options = Message.options().withBody(messageBody);
            Log.d("TAG", "Message created");
            channel.getMessages().sendMessage(options, new CallbackListener<Message>() {
                @Override
                public void onSuccess(Message message) {
                    if (chatManagerListener != null) {
                        chatManagerListener.messageSentCallback();
                    }
                }
            });
        }
    }

    public void sendMediaMessage(String path, String fileName) throws FileNotFoundException {
        if (channel != null) {
            Message.Options options = Message.options().withMedia(new FileInputStream(path), "image/jpeg")
                    .withMediaFileName(fileName).withMediaProgressListener(new ProgressListener() {
                        @Override
                        public void onStarted() {

                        }

                        @Override
                        public void onProgress(long l) {

                        }

                        @Override
                        public void onCompleted(String s) {

                        }
                    });

            channel.getMessages().sendMessage(options, new CallbackListener<Message>() {
                @Override
                public void onSuccess(Message message) {
                    if (chatManagerListener != null) {
                        chatManagerListener.messageSentCallback();
                    }
                }
            });

        }
    }

    public void sendVideoMessage(String path, String fileName) throws FileNotFoundException {
        if (channel != null) {
            Message.Options options = Message.options().withMedia(new FileInputStream(path), "video/mp4")
                    .withMediaFileName(fileName).withMediaProgressListener(new ProgressListener() {
                        @Override
                        public void onStarted() {

                        }

                        @Override
                        public void onProgress(long l) {

                        }

                        @Override
                        public void onCompleted(String s) {

                        }
                    });

            channel.getMessages().sendMessage(options, new CallbackListener<Message>() {
                @Override
                public void onSuccess(Message message) {
                    if (chatManagerListener != null) {
                        chatManagerListener.messageSentCallback();
                    }
                }
            });

        }
    }

    public void sendPDFMessage(String path, String fileName) throws FileNotFoundException {
        if (channel != null) {
            Message.Options options = Message.options().withMedia(new FileInputStream(path), "application/pdf")
                    .withMediaFileName(fileName).withMediaProgressListener(new ProgressListener() {
                        @Override
                        public void onStarted() {

                        }

                        @Override
                        public void onProgress(long l) {

                        }

                        @Override
                        public void onCompleted(String s) {

                        }
                    });

            channel.getMessages().sendMessage(options, new CallbackListener<Message>() {
                @Override
                public void onSuccess(Message message) {
                    if (chatManagerListener != null) {
                        chatManagerListener.messageSentCallback();
                    }
                }
            });

        }
    }


    private void addChannelPage(Paginator<ChannelDescriptor> channelDescriptorPaginator) {
        for (ChannelDescriptor xhannelDesc : channelDescriptorPaginator.getItems()) {
            channelList.add(new MyChannelModel(xhannelDesc));

        }
        Logger.show("channelSIXESS", String.valueOf(channelList.size()));
        for (int i = 0; i < channelList.size(); i++) {
            Logger.show("channelName", i + channelList.get(i).getUniqueName());

        }

        if (channelDescriptorPaginator.hasNextPage()) {
            channelDescriptorPaginator.requestNextPage(new CallbackListener<Paginator<ChannelDescriptor>>() {
                @Override
                public void onSuccess(Paginator<ChannelDescriptor> channelDescriptorPaginator) {
                    addChannelPage(channelDescriptorPaginator);
                }
            });
        }
    }


    public void getChannelMessage() {
        if (channel == null) {
            Logger.e("Channel is null");
        } else if (channel.getMessages() == null) {
            getChannelMessage();
            Logger.e("Channel.getMessages() is null");
        } else {
            channel.getMessages().getLastMessages(50, new CallbackListener<List<Message>>() {
                @Override
                public void onSuccess(List<Message> messages) {
                    messagesList.clear();
                    messagesList.addAll(messages);
                    messageFetchedListener.messagesFetched(true, null);
                }
            });
        }
    }


    public void loadChannels(final String channelName, String memberName,String type) {
        this.memberName = memberName;
        chatClient.getChannels().getChannel(channelName, new CallbackListener<Channel>() {
            @Override
            public void onSuccess(Channel channel) {
                if (channel != null) {
                    if (channel.getStatus() == Channel.ChannelStatus.JOINED
                            || channel.getStatus() == Channel.ChannelStatus.NOT_PARTICIPATING) {
                        Log.d("TAG", "Already Exists in Channel: " + channelName);

                        QuickstartChatManager.this.channel = channel;
                        QuickstartChatManager.this.channel.addListener(mDefaultChannelListener);
                        channelCreatedListener.channelCreated(true, null);

                    } else {
                        Log.d("TAG", "Joining Channel: " + channelName);
                        joinChannel(channel);
                    }
                } else {
                    Log.d("TAG", "Creating Channel: " + channelName);

                    chatClient.getChannels().channelBuilder().withFriendlyName(channelName).withUniqueName(channelName)
                            .withType(Channel.ChannelType.PRIVATE)
                            .build(new CallbackListener<Channel>() {
                                @Override
                                public void onSuccess(Channel channel) {
                                    if (channel != null) {
                                        if (!TextUtils.isEmpty(channel.getUniqueName())) {
                                            QuickstartChatManager.this.channel = channel;
                                            joinChannel(channel);
                                        }
                                    }
                                }

                                @Override
                                public void onError(ErrorInfo errorInfo) {
                                    Log.e("TAG", "Error creating channel: " + errorInfo.getMessage());
                                }
                            });
                }
            }

            @Override
            public void onError(ErrorInfo errorInfo) {
                Log.e("TAG", "Error retrieving channel: " + errorInfo.getMessage());
                if (type=="group_chat") {
                    Log.d("BitData",type);
                }else {
                    createChannel(channelName);
                }
            }

        });
    }

    private void createChannel(String channelName) {
        Log.d("TAG", "Creating Channel:::: " + channelName);
        chatClient.getChannels().channelBuilder().withFriendlyName(channelName).withUniqueName(channelName)
                .withType(Channel.ChannelType.PRIVATE)
                .build(new CallbackListener<Channel>() {
                    @Override
                    public void onSuccess(Channel channel) {
                        if (channel != null) {
                            if (!TextUtils.isEmpty(channel.getUniqueName())) {
                                QuickstartChatManager.this.channel = channel;
                                joinChannel(channel);
                            }
                        }
                    }

                    @Override
                    public void onError(ErrorInfo errorInfo) {
                        Log.e("TAG", "Error creating channel: " + errorInfo.getMessage());

                    }
                });

    }

    private void joinChannel(final Channel channel) {
        Log.d("TAGggg", "Joining Channel: " + channel.getUniqueName());
        channel.join(new StatusListener() {
            @Override
            public void onSuccess() {
                QuickstartChatManager.this.channel = channel;
                Log.d("TAG", "Joined default channel");
                QuickstartChatManager.this.channel.addListener(mDefaultChannelListener);

                try {
                    channel.getMembers().addByIdentity(memberName, new StatusListener() {
                        @Override
                        public void onSuccess() {
                            Logger.e("added");
                        }

                        @Override
                        public void onError(ErrorInfo errorInfo) {
                            super.onError(errorInfo);
                            Logger.e("error" + errorInfo.getMessage());
                        }
                    });
                } catch (Exception exp) {
                    Logger.e("exp" + exp.getMessage());
                }
                channelCreatedListener.channelCreated(true, null);
            }

            @Override
            public void onError(ErrorInfo errorInfo) {
                Log.e("TAG", "Error joining channel: " + errorInfo.getMessage());
            }
        });
    }

    private final ChatClientListener mChatClientListener =
            new ChatClientListener() {
                @Override
                public void onChannelJoined(Channel channel) {
                    Logger.e("onChannelJoined");
                    try {
                        channel.getMembers().addByIdentity(memberName, memberAddedListener);
                    } catch (Exception exp) {
                        Logger.e("exp" + exp.getMessage());
                    }

                }

                @Override
                public void onChannelInvited(Channel channel) {
                    Logger.e("onChannelInvited");
                }

                @Override
                public void onChannelAdded(Channel channel) {
                    Logger.e("onChannelAdded");
                }

                @Override
                public void onChannelUpdated(Channel channel, Channel.UpdateReason updateReason) {
                    Logger.e("onChannelUpdated");
                }

                @Override
                public void onChannelDeleted(Channel channel) {
                    Logger.e("onChannelDeleted");
                }

                @Override
                public void onChannelSynchronizationChange(Channel channel) {
                    Logger.e("onChannelSynchronizationChange");
                }

                @Override
                public void onError(ErrorInfo errorInfo) {
                    Logger.e("onError");
                }

                @Override
                public void onUserUpdated(User user, User.UpdateReason updateReason) {
                    Logger.e("onUserUpdated");
                }

                @Override
                public void onUserSubscribed(User user) {
                    Logger.e("onUserSubscribed");
                }

                @Override
                public void onUserUnsubscribed(User user) {
                    Logger.e("onUserUnsubscribed");
                }

                @Override
                public void onClientSynchronization(ChatClient.SynchronizationStatus synchronizationStatus) {
                    if (synchronizationStatus == ChatClient.SynchronizationStatus.COMPLETED) {
                        //   loadClientChannel();
                    }
                }

                @Override
                public void onNewMessageNotification(String s, String s1, long l) {

                }

                @Override
                public void onAddedToChannelNotification(String s) {

                }

                @Override
                public void onInvitedToChannelNotification(String s) {

                }

                @Override
                public void onRemovedFromChannelNotification(String s) {

                }

                @Override
                public void onNotificationSubscribed() {

                }

                @Override
                public void onNotificationFailed(ErrorInfo errorInfo) {

                }

                @Override
                public void onConnectionStateChange(ChatClient.ConnectionState connectionState) {

                }

                @Override
                public void onTokenExpired() {

                }

                @Override
                public void onTokenAboutToExpire() {
                    retrieveToken(new AccessTokenListener() {
                        @Override
                        public void receivedAccessToken(@Nullable String token, @Nullable Exception exception) {
                            if (token != null) {
                                chatClient.updateToken(token, new StatusListener() {
                                    @Override
                                    public void onSuccess() {
                                        Log.d("TAG", "Refreshed access token.");
                                    }

                                    @Override
                                    public void onError(ErrorInfo errorInfo) {
                                        super.onError(errorInfo);
                                        Log.d("TAG", "Refreshed access token. Error" + errorInfo.getMessage());
                                    }
                                });
                            }
                        }
                    });
                }
            };

    private final StatusListener memberAddedListener = new StatusListener() {
        @Override
        public void onSuccess() {
            Logger.e("member Added");
        }
    };

    private final CallbackListener<ChatClient> mChatClientCallback =
            new CallbackListener<ChatClient>() {
                @Override
                public void onSuccess(ChatClient chatClient) {
                    QuickstartChatManager.this.chatClient = chatClient;
                    chatClient.setListener(QuickstartChatManager.this.mChatClientListener);
                    Log.d("TAG", "Success creating Twilio Chat Client");
                    Logger.show("chatClient", chatClient.getMyIdentity());
                    clientCreatedListener.clientCreated(chatClient,true, null);
                    chatClient.registerFCMToken(new ChatClient.FCMToken(fcmToken), new StatusListener() {
                        @Override
                        public void onSuccess() {
                            Logger.show("registered for notification", "On succcess");
                        }

                        @Override
                        public void onError(ErrorInfo errorInfo) {
                            super.onError(errorInfo);
                            Logger.show("registered for notification", errorInfo.getMessage());
                        }
                    });
                }

                @Override
                public void onError(ErrorInfo errorInfo) {
                    Log.e("TAG", "Error creating Twilio Chat Client: " + errorInfo.getMessage());
                    clientCreatedListener.clientCreated(chatClient,false, null);
                }
            };


    private final ChannelListener mDefaultChannelListener = new ChannelListener() {


        @Override
        public void onMessageAdded(final Message message) {
            Log.d("TAG", "Message added");
            messagesList.add(message);
            if (chatManagerListener != null) {
                chatManagerListener.receivedNewMessage();
            }
        }

        @Override
        public void onMessageUpdated(Message message, Message.UpdateReason updateReason) {
            Log.d("TAG", "Message updated: " + message.getMessageBody());
        }

        @Override
        public void onMessageDeleted(Message message) {
            Log.d("TAG", "Message deleted");
        }

        @Override
        public void onMemberAdded(Member member) {
            Log.d("TAG", "Member added: " + member.getIdentity());
        }

        @Override
        public void onMemberUpdated(Member member, Member.UpdateReason updateReason) {
            Log.d("TAG", "Member updated: " + member.getIdentity());
        }

        @Override
        public void onMemberDeleted(Member member) {
            Log.d("TAG", "Member deleted: " + member.getIdentity());
        }

        @Override
        public void onTypingStarted(Channel channel, Member member) {
            Log.d("TAG", "Started Typing: " + member.getIdentity());
        }

        @Override
        public void onTypingEnded(Channel channel, Member member) {
            Log.d("TAG", "Ended Typing: " + member.getIdentity());
        }

        @Override
        public void onSynchronizationChanged(Channel channel) {

        }
    };


    public ArrayList<Message> getMessagesList() {
        getChannelMessage();
        return messagesList;
    }

    public void setChatManagerListener(QuickstartChatManagerListener listener) {
        this.chatManagerListener = listener;
    }

    public void setClientListener(ClientCreated listener) {
        this.clientCreatedListener = listener;
    }

    public void setChannelListener(ChannelCreated listener) {
        this.channelCreatedListener = listener;
    }

    public void setMessageFetchedListener(MessagesFetched messageFetchedListener) {
        this.messageFetchedListener = messageFetchedListener;
    }

    void sendAudioMessage(String path, String fileName) throws FileNotFoundException {
        if (channel != null) {
            Message.Options options = Message.options().withMedia(new FileInputStream(path), "audio/basic")
                    .withMediaFileName(fileName).withMediaProgressListener(new ProgressListener() {
                        @Override
                        public void onStarted() {

                        }

                        @Override
                        public void onProgress(long l) {

                        }

                        @Override
                        public void onCompleted(String s) {

                        }
                    });

            channel.getMessages().sendMessage(options, new CallbackListener<Message>() {
                @Override
                public void onSuccess(Message message) {
                    if (chatManagerListener != null) {
                        chatManagerListener.messageSentCallback();
                    }
                }
            });

        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

