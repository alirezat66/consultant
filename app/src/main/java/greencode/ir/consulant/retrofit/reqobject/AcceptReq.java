package greencode.ir.consulant.retrofit.reqobject;

public class AcceptReq {
    String token;
    int id;
    int adviserId;


    public AcceptReq(String token, int id, int adviserId) {
        this.token = token;
        this.id = id;
        this.adviserId = adviserId;

    }

    public String getToken() {
        return token;
    }

    public int getId() {
        return id;
    }

    public int getAdviserId() {
        return adviserId;
    }

}
