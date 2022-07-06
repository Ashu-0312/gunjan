package app.gunjan.entity;

import java.io.Serializable;

public class CommunityGuidelineResponse implements Serializable {

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    /**
     * code : 1
     * message : success
     * data : {"id":1,"community_guideline":"<p><strong>Community Guideline<\/strong><\/p>\n<p>&nbsp;<\/p>\n<p>Gunjan helps its users connect with others. Gunjan enables meaningful engagement within the Gunjan community. To ensure that this objective is effectively achieved, Gunjan requires users to comply with these Community Guidelines along with the Terms of Use, and we enforce these Community Guidelines to enable us to communicate with the community and It help create a safe environment and ensure that everyone can participate freely and safely in public conversations.<\/p>\n<p>&nbsp;<\/p>\n<p>We are committed to these guidelines and we hope you are too. Not following these guidelines may result in deleted content, disabled accounts or other restrictions. In some cases, we allow content for public awareness that would otherwise go against our Community Guidelines &ndash; if it is newsworthy and in the public interest. We only do this after weighing the public interest value against the risk of harm and we look to international human rights standards to make these judgments.<\/p>\n<p>&nbsp;<\/p>\n<p><strong>Respect the members of the community<\/strong><\/p>\n<p>We want to develop a multiple community. We remove content that contains credible threats or hate speech, content that targets private individuals to degrade or shame them, personal information meant to blackmail or harass someone, and repeated unwanted messages. We allow the strong conversation of the people who are renowned or have a large audience because of their profession or chosen activities.<\/p>\n<p>It's never ok, to encourage violence or attack anyone based on their race, ethnicity, national origin, sex, gender, gender identity, sexual orientation, religious affiliation, disabilities or diseases. When hate speech is being shared to challenge it or to raise awareness, we may allow it. In those instances, we ask that you express your intent clearly.<\/p>\n<p>We never allow anything that harms public safety. This includes threats of theft, vandalism or financial harm. We review the reports on these issues and take strict actions on the accused if found guilty.<\/p>\n<p><strong>Follow the Law<\/strong><\/p>\n<p>We never support any kinds of terrorism, crime or hatred groups. Offering sexual services, buying or selling of tobacco between private individuals, buying or selling non- medical drugs are not allowed. We also remove content that attempts to trade, co-ordinate the trade of, donate, gift, or ask for non-medical drugs, as well as content that either admits to personal use (unless in the recovery context) or coordinates or promotes the use of non-medical drugs. We never support in selling any live animals between the individuals. No one can coordinate poaching or selling of endangered species or their parts.<\/p>\n<p>Never try to violate the laws during selling or buying other regulated goods. The accounts dealing with gambling, Money lotteries should take prior written from us before getting into such services.<\/p>\n<p>We never tolerate, when there is sharing of sexual content of minors or threatening them by posting imitate images of others.<\/p>\n<p>&nbsp;<\/p>\n<p><strong>Encourage meaningful and genuine interactions<\/strong><\/p>\n<p>Let's make this free from spam by not artificially collecting likes, shares, followers, repeatedly posting comments, contacting people for business purposes without their consent. Don't do many financial transactions instead of likes, shares or followers. Don't post any content which is engaged in promoting, encouraging, offering or admitting fake and misleading user reviews or ratings. We always need the up to date information from the users. Don't try to make fake accounts for solely violating our guidelines and misleading others.<\/p>\n<p><strong>Share only the photos and videos that you have clicked and you have the right to share<\/strong><\/p>\n<p>Always, you have the right to the contents that you have posted on our platform. Always remember that authentic content should be posted on our platform, there shouldn't be any copied content or collected from any third party that you don't have the right to post.<\/p>\n<p>&nbsp;<\/p>\n<p><strong>Post photos and videos that are appropriate for the multiple audience <\/strong><\/p>\n<p>There are many people who want to share nude images because it looks creative in nature, but for certain reasons we don't allow such nude photos on our platform. This includes photos, videos, various content which shows sexual intercourse, close-ups of nude parts etc. We allow such photos or videos in some cases like breastfeeding, birth giving, health related issues etc. Nudity in various paintings and sculptures are fine. People also share some photos or videos of their children with nudity, we may remove such pictures from our platform. Even if there is a good intention in sharing those photos, others may use it with evil intentions.<\/p>\n<p><strong>Maintain our supportive environment by not hampering yourself<\/strong><\/p>\n<p>Our community cares for each other, it is a place where people face various issues which may result in self-injury, come together to support and create awareness. We give education through our app and add information in the Help Centre to help the people as per their needs. Encouraging or compelling people for self-injury is unavoidable, we will remove or disable the accounts if we receive any complaints. We may also remove the contents which contain such acts.<\/p>\n<p>&nbsp;<\/p>\n<p><strong>Thoughtful in posting newsworthy events<\/strong><\/p>\n<p>We understand that many people use our services to share important information or events which include graphic images. There are so many different people from various age groups who use our services, we may remove the videos which create violence to make sure of keeping appropriate services for everyone. We know that people share these kinds of images to create awareness and to educate people. If you are sharing such images, you must mention the warning about the graphic violence. Sharing graphic images for sadist pleasure or to glorify violence is never allowed.<\/p>\n<p>&nbsp;<\/p>\n<p><strong>Maintaining our strong community<\/strong><\/p>\n<p>Each and every one is part of our community. If you see or receive any complaints of any violations of our guidelines, please reach out to us. We are working as a team; we will try to delete those contents or remove the account from our community. Try to provide full information about the account while giving the report, so that we can quickly check it. If someone violates our guidelines we will remove those contents or disable that account.<\/p>\n<p>You find anything irrelevant or you don't like that post and also it is not violating our guidelines, then you can remove him/her from your friends list or block. You can also delete the comment which you don't like.<\/p>\n<p>The members themselves can solve the disputes or misunderstandings. You can file a copyright report, if someone uses your content or image without your prior consent. If there is any trademark infringement, you can file a trademark report.<\/p>\n<p>We may work with law enforcement, including when we believe that there's risk of physical harm or threat to public safety.<\/p>\n<p><strong>For Community Admin and Manager <\/strong><\/p>\n<p>Community Admin and managers should be well-versed with their community laws and guidelines and must take action to prevent such privacy breaches and ensure safe community activity.<\/p>","image":"","language":"en"}
     */

    private int code;
    private String message;
    private DataBean data;

    public static class DataBean implements Serializable {
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCommunity_guideline() {
            return community_guideline;
        }

        public void setCommunity_guideline(String community_guideline) {
            this.community_guideline = community_guideline;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        /**
         * id : 1
         * community_guideline : <p><strong>Community Guideline</strong></p>
         <p>&nbsp;</p>
         <p>Gunjan helps its users connect with others. Gunjan enables meaningful engagement within the Gunjan community. To ensure that this objective is effectively achieved, Gunjan requires users to comply with these Community Guidelines along with the Terms of Use, and we enforce these Community Guidelines to enable us to communicate with the community and It help create a safe environment and ensure that everyone can participate freely and safely in public conversations.</p>
         <p>&nbsp;</p>
         <p>We are committed to these guidelines and we hope you are too. Not following these guidelines may result in deleted content, disabled accounts or other restrictions. In some cases, we allow content for public awareness that would otherwise go against our Community Guidelines &ndash; if it is newsworthy and in the public interest. We only do this after weighing the public interest value against the risk of harm and we look to international human rights standards to make these judgments.</p>
         <p>&nbsp;</p>
         <p><strong>Respect the members of the community</strong></p>
         <p>We want to develop a multiple community. We remove content that contains credible threats or hate speech, content that targets private individuals to degrade or shame them, personal information meant to blackmail or harass someone, and repeated unwanted messages. We allow the strong conversation of the people who are renowned or have a large audience because of their profession or chosen activities.</p>
         <p>It's never ok, to encourage violence or attack anyone based on their race, ethnicity, national origin, sex, gender, gender identity, sexual orientation, religious affiliation, disabilities or diseases. When hate speech is being shared to challenge it or to raise awareness, we may allow it. In those instances, we ask that you express your intent clearly.</p>
         <p>We never allow anything that harms public safety. This includes threats of theft, vandalism or financial harm. We review the reports on these issues and take strict actions on the accused if found guilty.</p>
         <p><strong>Follow the Law</strong></p>
         <p>We never support any kinds of terrorism, crime or hatred groups. Offering sexual services, buying or selling of tobacco between private individuals, buying or selling non- medical drugs are not allowed. We also remove content that attempts to trade, co-ordinate the trade of, donate, gift, or ask for non-medical drugs, as well as content that either admits to personal use (unless in the recovery context) or coordinates or promotes the use of non-medical drugs. We never support in selling any live animals between the individuals. No one can coordinate poaching or selling of endangered species or their parts.</p>
         <p>Never try to violate the laws during selling or buying other regulated goods. The accounts dealing with gambling, Money lotteries should take prior written from us before getting into such services.</p>
         <p>We never tolerate, when there is sharing of sexual content of minors or threatening them by posting imitate images of others.</p>
         <p>&nbsp;</p>
         <p><strong>Encourage meaningful and genuine interactions</strong></p>
         <p>Let's make this free from spam by not artificially collecting likes, shares, followers, repeatedly posting comments, contacting people for business purposes without their consent. Don't do many financial transactions instead of likes, shares or followers. Don't post any content which is engaged in promoting, encouraging, offering or admitting fake and misleading user reviews or ratings. We always need the up to date information from the users. Don't try to make fake accounts for solely violating our guidelines and misleading others.</p>
         <p><strong>Share only the photos and videos that you have clicked and you have the right to share</strong></p>
         <p>Always, you have the right to the contents that you have posted on our platform. Always remember that authentic content should be posted on our platform, there shouldn't be any copied content or collected from any third party that you don't have the right to post.</p>
         <p>&nbsp;</p>
         <p><strong>Post photos and videos that are appropriate for the multiple audience </strong></p>
         <p>There are many people who want to share nude images because it looks creative in nature, but for certain reasons we don't allow such nude photos on our platform. This includes photos, videos, various content which shows sexual intercourse, close-ups of nude parts etc. We allow such photos or videos in some cases like breastfeeding, birth giving, health related issues etc. Nudity in various paintings and sculptures are fine. People also share some photos or videos of their children with nudity, we may remove such pictures from our platform. Even if there is a good intention in sharing those photos, others may use it with evil intentions.</p>
         <p><strong>Maintain our supportive environment by not hampering yourself</strong></p>
         <p>Our community cares for each other, it is a place where people face various issues which may result in self-injury, come together to support and create awareness. We give education through our app and add information in the Help Centre to help the people as per their needs. Encouraging or compelling people for self-injury is unavoidable, we will remove or disable the accounts if we receive any complaints. We may also remove the contents which contain such acts.</p>
         <p>&nbsp;</p>
         <p><strong>Thoughtful in posting newsworthy events</strong></p>
         <p>We understand that many people use our services to share important information or events which include graphic images. There are so many different people from various age groups who use our services, we may remove the videos which create violence to make sure of keeping appropriate services for everyone. We know that people share these kinds of images to create awareness and to educate people. If you are sharing such images, you must mention the warning about the graphic violence. Sharing graphic images for sadist pleasure or to glorify violence is never allowed.</p>
         <p>&nbsp;</p>
         <p><strong>Maintaining our strong community</strong></p>
         <p>Each and every one is part of our community. If you see or receive any complaints of any violations of our guidelines, please reach out to us. We are working as a team; we will try to delete those contents or remove the account from our community. Try to provide full information about the account while giving the report, so that we can quickly check it. If someone violates our guidelines we will remove those contents or disable that account.</p>
         <p>You find anything irrelevant or you don't like that post and also it is not violating our guidelines, then you can remove him/her from your friends list or block. You can also delete the comment which you don't like.</p>
         <p>The members themselves can solve the disputes or misunderstandings. You can file a copyright report, if someone uses your content or image without your prior consent. If there is any trademark infringement, you can file a trademark report.</p>
         <p>We may work with law enforcement, including when we believe that there's risk of physical harm or threat to public safety.</p>
         <p><strong>For Community Admin and Manager </strong></p>
         <p>Community Admin and managers should be well-versed with their community laws and guidelines and must take action to prevent such privacy breaches and ensure safe community activity.</p>
         * image :
         * language : en
         */

        private int id;
        private String community_guideline;
        private String image;
        private String language;
    }
}
