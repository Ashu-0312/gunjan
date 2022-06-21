package app.gunjan.entity;

import java.io.Serializable;
import java.util.List;

public class CoinFaqListResponse implements Serializable {

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
     * message : FAQs list!
     * data : {"FAQ_image":"","total_question":6,"question":[{"id":1,"language":"en","question":"What are coins?","answer":"Coins are our virtual good, you can use them to reward an extraordinary post or comment, and then give the reward in the form of coins. And we will add new feature where you can spend it in future.","question_type":"coin"},{"id":2,"language":"en","question":"What is Give Reward?","answer":"Offering a reward is a way of showing appreciation for an outstanding contribution to Gunjan. You can give reward to anyone by clicking on \"Reward\" below their post. This sets it apart from a prize for all to see, and some even offer special bonus benefits to the honoured person.","question_type":"coin"},{"id":3,"language":"en","question":"Can I transfer coins?","answer":"No you cannot, it\u2019s your virtual asset so you cannot transfer","question_type":"coin"},{"id":4,"language":"en","question":"Can I get coins free?","answer":"Yes, we may offer coins on special occasions and outstanding contributions. ","question_type":"coin"},{"id":5,"language":"en","question":"Can we spend these Coins?","answer":"We will add new features to spend coins in the future.","question_type":"coin"},{"id":6,"language":"en","question":"Can we exchange Gunjan coin for real money?","answer":"No, it is a virtual asset so you cannot exchange it with real money, but very soon we will be adding nice feature to spend it.","question_type":"coin"}]}
     */

    private int code;
    private String message;
    private DataBean data;

    public static class DataBean implements Serializable {
        public String getFAQ_image() {
            return FAQ_image;
        }

        public void setFAQ_image(String FAQ_image) {
            this.FAQ_image = FAQ_image;
        }

        public int getTotal_question() {
            return total_question;
        }

        public void setTotal_question(int total_question) {
            this.total_question = total_question;
        }

        public List<QuestionBean> getQuestion() {
            return question;
        }

        public void setQuestion(List<QuestionBean> question) {
            this.question = question;
        }

        /**
         * FAQ_image :
         * total_question : 6
         * question : [{"id":1,"language":"en","question":"What are coins?","answer":"Coins are our virtual good, you can use them to reward an extraordinary post or comment, and then give the reward in the form of coins. And we will add new feature where you can spend it in future.","question_type":"coin"},{"id":2,"language":"en","question":"What is Give Reward?","answer":"Offering a reward is a way of showing appreciation for an outstanding contribution to Gunjan. You can give reward to anyone by clicking on \"Reward\" below their post. This sets it apart from a prize for all to see, and some even offer special bonus benefits to the honoured person.","question_type":"coin"},{"id":3,"language":"en","question":"Can I transfer coins?","answer":"No you cannot, it\u2019s your virtual asset so you cannot transfer","question_type":"coin"},{"id":4,"language":"en","question":"Can I get coins free?","answer":"Yes, we may offer coins on special occasions and outstanding contributions. ","question_type":"coin"},{"id":5,"language":"en","question":"Can we spend these Coins?","answer":"We will add new features to spend coins in the future.","question_type":"coin"},{"id":6,"language":"en","question":"Can we exchange Gunjan coin for real money?","answer":"No, it is a virtual asset so you cannot exchange it with real money, but very soon we will be adding nice feature to spend it.","question_type":"coin"}]
         */

        private String FAQ_image;
        private int total_question;
        private List<QuestionBean> question;

        public static class QuestionBean implements Serializable {
            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getLanguage() {
                return language;
            }

            public void setLanguage(String language) {
                this.language = language;
            }

            public String getQuestion() {
                return question;
            }

            public void setQuestion(String question) {
                this.question = question;
            }

            public String getAnswer() {
                return answer;
            }

            public void setAnswer(String answer) {
                this.answer = answer;
            }

            public String getQuestion_type() {
                return question_type;
            }

            public void setQuestion_type(String question_type) {
                this.question_type = question_type;
            }

            /**
             * id : 1
             * language : en
             * question : What are coins?
             * answer : Coins are our virtual good, you can use them to reward an extraordinary post or comment, and then give the reward in the form of coins. And we will add new feature where you can spend it in future.
             * question_type : coin
             */

            private int id;
            private String language;
            private String question;
            private String answer;
            private String question_type;
        }
    }
}
