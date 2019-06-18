package greencode.ir.consulant.retrofit;

public enum MyMethods {
    Authentication("Authentication", 0),
    GetCats("GetCats", 1),
    GetSubCat("GetSubCat", 2),
    Login("Login", 3),
    GetActiveList("GetActiveList", 4),
    GetWaitedDetail("GetWaitedDetail", 5),
    InsertRequest("InsertRequest", 6),
    GetActivatedDetail("GetActivatedDetail", 7),
    AcceptRequest("AcceptRequest", 8),
    CancelReq("CancelReq", 9),
    ProfileInfo("ProfileInfo", 10),
    UpdateProfile("UpdateProfile", 11),
    GetDoneDetial("doneDetial", 12),
    GetAdvocacyType("GetAdvocacyType", 13),
    getDegreeTypeNames("getDegreeTypeNames",14),
    getCountries("getCountries", 15),
    getProvinces("getProvinces", 16),
    getCities("getCities", 17),
    getArea("getArea", 18),
    getBanks("getBanks", 19),
    IdentityConfirmationUser("IdentityConfirmationUser", 20),
    getActiveOffers("getActiveOffers", 21),
    getRequests("getOffers", 22),
    getWaitedRequestDetial("getWaitedRequestDetial", 23),
    acceptRequest("acceptRequest", 24),
    getHistoryOffers("getHistoryOffers", 25),
    getReadyToAnswerRequestDetial("getReadyToAnswerRequestDetial", 26),
    cancelOffer("cancelOffer", 27),
    getAssignedRequestDetail("getAssignedRequestDetail", 28),
    getCanceledRequestBeforeAssignedDetial("getCanceledRequestBeforeAssignedDetial", 29),
    UPDATECALL("updateCall", 30),
    UserState("GetUserState", 31),
    SendGCMToken("sendGCMToken" , 32),
    Logout("logout" , 33),
    GetCallState("GetCallState", 34),
    GetUserStatistic("GetUserStatistics" , 22),
    CreateInvoiceOffline("CreateInvoiceOffline" , 23),
    GetMessage("GetMessage" , 23),
    GetUserConversation("getUserConversation" , 24),
    ResetPassword("ResetPassword" , 25)
    ,GetPaymentReport("PaymetReport" , 26)
    ,CreateLottery("CreateLottory" , 27)
    ,PrepareLottery("PrepareLottory" , 28)
    ,GetLotteryGeneral("GetLotteryGeneral" , 29)
    ,PrepareWithdrawal("PrepareWithdrawal" , 30)
    ,setTransactionResult("setTransactionResult" , 31)
    ,CreateWithdrawal("CreateWithdrawal" , 32)
    ,GetWithdrawalDetails("GetWithdrawalDetails" , 32)
    ,ResetPasswordSetNewPassword("ResetPasswordSetNewPassword" , 33)
    ,ResetPasswordActiveCode("ResetPasswordActiveCode" , 34)
    ,GetAlaets("GetAlarts" , 35)
    ,GetAlaetsDetail("GetAlartsDetail" , 36)
    ,GetConversation("GetConversation" , 37)
    ,GetAnswerResult("GetAnswerResult" , 38)
    ,DeleteMessage("DeleteMessage" , 39)
    ,GetterWiner("GetterWinner" , 40)
    ,UnSeen("unseen" , 41)
    ;
    

    private String methodName;
    private int methodValue;

    public int getMethodValue() {
        return this.methodValue;
    }

    private MyMethods(String toString, int value) {
        this.methodName = toString;
        this.methodValue = value;
    }

    public String toString() {
        return this.methodName;
    }
}
