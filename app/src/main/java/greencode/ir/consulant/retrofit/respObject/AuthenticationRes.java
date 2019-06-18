package greencode.ir.consulant.retrofit.respObject;

public class AuthenticationRes extends ErrorResult {

    AuthRes result;

    public AuthRes getResult() {
        return result;
    }

   public class AuthRes {
        String token;
        String code;
        int state;

       public int getState() {
           return state;
       }

       String userName;
        String userFamily;
        String userImage;
        int userId;
        int active;// 1 no info 2 pending 3 active
       public int getId() {
           return userId;
       }

       public int getActive() {
           return active;
       }

       public String getToken() {
            return token;
        }

        public String getCode() {
            return code;
        }

        public String getUserName() {
            return userName;
        }

        public String getUserFamily() {
            return userFamily;
        }

        public String getUserImage() {
            return userImage;
        }
    }


}
