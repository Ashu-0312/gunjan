package app.gunjan.twilio;

import com.twilio.chat.CallbackListener;
import com.twilio.chat.Channel;
import com.twilio.chat.ChannelDescriptor;
import com.twilio.chat.ErrorInfo;

import java.util.Date;


public class MyChannelModel {

    ChannelDescriptor channelDescriptor;
    Channel channel;

    public MyChannelModel(ChannelDescriptor channelDescriptor){
            this.channelDescriptor  =channelDescriptor;
    }

    public MyChannelModel(Channel channel){
        this.channel = channel;
    }




    public String getSid() {
        if (channel!= null) return  channel.getSid();
        else if (channelDescriptor!= null) return channelDescriptor.getSid();
        return "";
    }

    public String getFriendlyName() {
        if (channel!= null) return  channel.getFriendlyName();
        else if (channelDescriptor!= null) return channelDescriptor.getFriendlyName();
        return "";
    }


    public String getUniqueName() {
        if (channel!= null) return  channel.getUniqueName();
        else if (channelDescriptor!= null) return channelDescriptor.getUniqueName();
        return "";
    }

    public Date getDateUpdatedAsDate() {
            if (channel!= null) return  channel.getDateUpdatedAsDate();
            else if (channelDescriptor!= null) return channelDescriptor.getDateUpdated();
            return null;
    }

    public Date getDateCreatedAsDate() {
        if (channel!= null) return  channel.getDateCreatedAsDate();
        else if (channelDescriptor!= null) return channelDescriptor.getDateCreated();
        return null;
    }


    public Date getLastMessageDate() {
        if (channel!= null) return  channel.getLastMessageDate();
        return null;
    }


    public Channel.ChannelStatus getStatus() {
        if (channel!= null) return  channel.getStatus();
        else if (channelDescriptor!= null) return channelDescriptor.getStatus();
        return null;
    }

    public Channel.NotificationLevel getNotificationLevel() {
        if (channel!= null) return  channel.getNotificationLevel();
        return null;
    }


    public void getMyChannel(CallbackListener<Channel> listener) {
        if (channel!= null){
            listener.onSuccess(channel);
            return;
        }else if (channelDescriptor!= null){
            channelDescriptor.getChannel(listener);
            return;
        }else listener.onError(new ErrorInfo(-10005, "No channel in model"));
    }
}
