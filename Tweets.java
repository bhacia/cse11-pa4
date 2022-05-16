import tester.*;

interface Tweet {
    public boolean isReplyTo(Tweet other);
    public int totalLikes();
    public String allAuthors();
    public boolean textAppearsOnThread(String text);
}

class User {
    String username; //@ of account
    String fullname; //display name
    int followers; //followers count
    User(String username, String fullname, int followers) {
        this.username = username;
        this.fullname = fullname;
        this.followers = followers;
    }
}

class TextTweet implements Tweet {
    User author; //User author reference of the tweet
    String contents; //text of the tweet
    int likes; //likes of the tweet
    TextTweet(User author, String contents, int likes) {
        this.author = author;
        this.contents = contents;
        this.likes = likes;
    }

    public boolean isReplyTo(Tweet other) {
        return false; //returns false no matter what tweet is in other
    }
    public int totalLikes() {
        return this.likes; //returns likes of this TextTweet object
    }
    public String allAuthors() {
        return this.author.username; //username of the author of this TextTweet object
    }
    public boolean textAppearsOnThread(String text) {
        if(this.contents.contains(text)) { //given String (text) is in the contents of this TextTweet object
            return true;
        }
        else {
            return false;
        }
    }
}

class ReplyTweet implements Tweet {
    User author; //User author reference of the tweet
    String contents; //text of the tweet
    int likes; //likes of the tweet
    Tweet replyTo; //Tweet replyTo reference of the tweet
    ReplyTweet(User author, String contents, int likes, Tweet replyTo) {
        this.author = author;
        this.contents = contents;
        this.likes = likes;
        this.replyTo = replyTo;
    }

    public boolean isReplyTo(Tweet other) {
        if(this.replyTo == other) { //the replyTo of this ReplyTweet object is the given Tweet (other)
            return true;
        }
        else {
            return false;
        }
    }
    public int totalLikes() {
        return this.likes + this.replyTo.totalLikes();
    }
    public String allAuthors() {
        return this.author.username + ";" + this.replyTo.allAuthors();
    }
    public boolean textAppearsOnThread(String text) {
        if(this.contents.contains(text)) {
            return true;
        }
        else if(this.replyTo.textAppearsOnThread(text)) {
            return true;
        }
        else {
            return false;
        }
    }
}

class Tweets {
    User joe = new User("joepolitz", "Joe Gibbs Politz", 999);
    User greg = new User("gregory_miranda", "Greg Miranda", 9999);
    User rachel = new User("Rachel__Lim", "Rachel Lim", 1000000);
    Tweet t1 = new TextTweet(this.joe, "Java 17 has a cool feature called records", 77);
    Tweet t2 = new ReplyTweet(this.greg, "Hmm I wonder if we could use it for CSE11", 12, this.t1);
    Tweet t3 = new ReplyTweet(this.greg, "Thought about this more, probably not yet, too new.", 73, this.t2);
    Tweet t4 = new ReplyTweet(this.joe, "Yeah, good point. Maybe in 2022.", 10, this.t3);
    Tweet t5 = new ReplyTweet(this.rachel, "Yeah... I don't want to rewrite the book right this minute", 1005, this.t2);

    void testIsReplyTo(Tester t) {
        t.checkExpect(this.t1.isReplyTo(this.t2), false);
        t.checkExpect(this.t2.isReplyTo(this.t1), true);
        t.checkExpect(this.t5.isReplyTo(this.t2), true);
        t.checkExpect(this.t2.isReplyTo(this.t2), false);
        t.checkExpect(this.t4.isReplyTo(this.t3), true);
    }

    void testTotalLikes(Tester t) {
        t.checkExpect(this.t5.totalLikes(), 1005 + 12 + 77);
        t.checkExpect(this.t4.totalLikes(), 10 + 73 + 12 + 77);
        t.checkExpect(this.t1.totalLikes(), 77);
    }

    void testAllAuthors(Tester t) {
        t.checkExpect(this.t1.allAuthors(), "joepolitz");
        t.checkExpect(this.t2.allAuthors(), "gregory_miranda;joepolitz");
        t.checkExpect(this.t3.allAuthors(), "gregory_miranda;gregory_miranda;joepolitz");
        t.checkExpect(this.t5.allAuthors(), "Rachel__Lim;gregory_miranda;joepolitz");
    }

    void testTextAppearsOnThread(Tester t) {
        t.checkExpect(this.t1.textAppearsOnThread("joepolitz"), false);
        t.checkExpect(this.t1.textAppearsOnThread("2022"), false);
        t.checkExpect(this.t1.textAppearsOnThread("cool"), true);
        t.checkExpect(this.t4.textAppearsOnThread("wonder"), true);
        t.checkExpect(this.t4.textAppearsOnThread("Java"), true);
        t.checkExpect(this.t4.textAppearsOnThread("rewrite"), false);
        t.checkExpect(this.t4.textAppearsOnThread("2022"), true);
    }
}

//MY EXAMPLES
class ExamplesTweets {
    //3 authors
    User naz = new User("wewantsq", "Naz", 685);
    User gavin = new User("addierox", "Gavin", 241);
    User zota = new User("louisloki", "Zota", 2858);

    //10 tweets
    Tweet TT1 = new TextTweet(this.naz, "besties i am tired", 14);
    Tweet TT2 = new TextTweet(this.gavin, "i refuse to believe this is her last ep", 4);
    Tweet RT1 = new ReplyTweet(this.zota, "ikr", 2, this.TT2);
    Tweet RT2 = new ReplyTweet(this.gavin, "me too bestie:(", 3, this.TT1);
    Tweet RT3 = new ReplyTweet(this.naz, "sighh", 1, this.RT2);
    Tweet RT4 = new ReplyTweet(this.gavin, "i swear she'll be here next week", 2, this.RT1);
    Tweet RT5 = new ReplyTweet(this.naz, "i have slept", 11, this.TT1);
    Tweet RT6 = new ReplyTweet(this.zota, "she better>:(", 4, this.RT4);
    Tweet RT7 = new ReplyTweet(this.zota, "yay i'm glad!", 1, this.RT5);
    Tweet RT8 = new ReplyTweet(this.gavin, "CONGRATS", 5, this.RT5);

    //testIsReplyTo
    void testIsReplyTo(Tester t) {
        t.checkExpect(this.RT1.isReplyTo(this.TT2), true);
        t.checkExpect(this.TT2.isReplyTo(this.TT1), false);
        t.checkExpect(this.RT8.isReplyTo(this.TT1), false);
        t.checkExpect(this.RT6.isReplyTo(this.RT4), true);
        t.checkExpect(this.RT5.isReplyTo(this.RT2), false);
        t.checkExpect(this.RT3.isReplyTo(this.RT7), false);
        t.checkExpect(this.RT4.isReplyTo(this.RT1), true);
    }

    //testTotalLikes
    void testTotalLikes(Tester t) {
    t.checkExpect(this.TT1.totalLikes(), 14);
    t.checkExpect(this.RT4.totalLikes(), 2 + 2 + 4);
    t.checkExpect(this.RT7.totalLikes(), 1 + 11 + 14);
    t.checkExpect(this.RT2.totalLikes(), 3 + 14);
    t.checkExpect(this.RT8.totalLikes(), 5 + 11 + 14);
    }

    //testAllAuthors
    void testAllAuthors(Tester t) {
    t.checkExpect(this.RT3.allAuthors(), "wewantsq;addierox;wewantsq");
    t.checkExpect(this.RT6.allAuthors(), "louisloki;addierox;louisloki;addierox");
    t.checkExpect(this.TT2.allAuthors(), "addierox");
    t.checkExpect(this.RT5.allAuthors(), "wewantsq;wewantsq");
    t.checkExpect(this.TT1.allAuthors(), "wewantsq");
    }

    //testTextAppearsOnThread
    void testTextAppearsOnThread(Tester t) {
    t.checkExpect(this.TT1.textAppearsOnThread("tired"), true);
    t.checkExpect(this.RT2.textAppearsOnThread("boo"), false);
    t.checkExpect(this.RT7.textAppearsOnThread("iPhone"), false);
    t.checkExpect(this.RT5.textAppearsOnThread("have"), true);
    t.checkExpect(this.RT4.textAppearsOnThread("swear"), true);
    }
}