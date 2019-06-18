package greencode.ir.consulant.Pojo;

import android.telecom.Call;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.List;

import greencode.ir.consulant.addinfo.AddInfoPresenter;
import greencode.ir.consulant.addinfo.AddressPresenter;
import greencode.ir.consulant.addinfo.PresenterStepOne;
import greencode.ir.consulant.addinfo.StepTwoPresenter;
import greencode.ir.consulant.addinfo.WaitingPresenter;
import greencode.ir.consulant.calling.CallPresenter;
import greencode.ir.consulant.main.MainPresenter;
import greencode.ir.consulant.objects.Category;
import greencode.ir.consulant.objects.ProfileInfo;
import greencode.ir.consulant.offers.AdvicePresenter;
import greencode.ir.consulant.offers.OfferPresenter;
import greencode.ir.consulant.requests.RequestDetailPresenter;
import greencode.ir.consulant.requests.RequestPresenter;
import greencode.ir.consulant.retrofit.CallerService;
import greencode.ir.consulant.retrofit.MyMethods;
import greencode.ir.consulant.retrofit.ServerListener;
import greencode.ir.consulant.retrofit.reqobject.AcceptReq;
import greencode.ir.consulant.retrofit.reqobject.AddInfoRequest;
import greencode.ir.consulant.retrofit.reqobject.AssignedResponse;
import greencode.ir.consulant.retrofit.reqobject.AuthReq;
import greencode.ir.consulant.retrofit.reqobject.CallUpdateReq;
import greencode.ir.consulant.retrofit.reqobject.CancelOfferReq;
import greencode.ir.consulant.retrofit.reqobject.CatReq;
import greencode.ir.consulant.retrofit.reqobject.DoneAdviceDetailReq;
import greencode.ir.consulant.retrofit.reqobject.GetWaitedDetailsReq;
import greencode.ir.consulant.retrofit.reqobject.ProfileInfoReq;
import greencode.ir.consulant.retrofit.respObject.AdviceDetail;
import greencode.ir.consulant.retrofit.respObject.AdvocancyTypeName;
import greencode.ir.consulant.retrofit.respObject.Area;
import greencode.ir.consulant.retrofit.respObject.AuthenticationRes;
import greencode.ir.consulant.retrofit.respObject.Bank;
import greencode.ir.consulant.retrofit.respObject.CallStateResponse;
import greencode.ir.consulant.retrofit.respObject.City;
import greencode.ir.consulant.retrofit.respObject.Country;
import greencode.ir.consulant.retrofit.respObject.DegreesName;
import greencode.ir.consulant.retrofit.respObject.Offer;
import greencode.ir.consulant.retrofit.respObject.Province;
import greencode.ir.consulant.retrofit.respObject.Request;
import greencode.ir.consulant.retrofit.respObject.ResultState;
import greencode.ir.consulant.retrofit.respObject.WaitedRequestDetial;
import greencode.ir.consulant.signin.SignInPresenter;


public class POJOModel  implements ServerListener {

    SignInPresenter signInPresenter;
    StepTwoPresenter stepTwoPresenter;
    AddressPresenter addressPresenter;
    PresenterStepOne presenterStepOne;
    AddInfoPresenter addInfoPresenter;
    MainPresenter mainPresenter;
    WaitingPresenter waitingPresenter;
    RequestPresenter requestPresenter;
    RequestDetailPresenter requestDetailPresenter;
    OfferPresenter offerPresenter;
    AdvicePresenter advicePresenter;
    CallPresenter callPresenter;

    public POJOModel(SignInPresenter signInPresenter) {
        this.signInPresenter = signInPresenter;
    }

    public POJOModel(StepTwoPresenter stepTwoPresenter) {
        this.stepTwoPresenter = stepTwoPresenter;
    }

    public POJOModel(AddressPresenter addressPresenter) {
        this.addressPresenter = addressPresenter;
    }

    public POJOModel(PresenterStepOne presenterStepOne) {
        this.presenterStepOne = presenterStepOne;
    }

    public POJOModel(AddInfoPresenter addInfoPresenter) {
        this.addInfoPresenter =addInfoPresenter;
    }

    public POJOModel(MainPresenter mainPresenter) {
        this.mainPresenter = mainPresenter;
    }

    public POJOModel(WaitingPresenter waitingPresenter) {
        this.waitingPresenter = waitingPresenter;
    }

    public POJOModel(RequestPresenter requestPresenter) {
        this.requestPresenter = requestPresenter;
    }

    public POJOModel(RequestDetailPresenter requestDetailPresenter) {
        this.requestDetailPresenter = requestDetailPresenter;
    }

    public POJOModel(OfferPresenter offerPresenter) {
        this.offerPresenter = offerPresenter;
    }

    public POJOModel(AdvicePresenter advicePresenter) {
            this.advicePresenter = advicePresenter;
    }

    public POJOModel(CallPresenter callPresenter) {
        this.callPresenter = callPresenter;
    }


    @Override
    public void onFailure(int i, String str) {
        if(i== MyMethods.Authentication.getMethodValue()){
            signInPresenter.onErrorReady(str);
        }else if(i == MyMethods.GetCats.getMethodValue()){
            addInfoPresenter.onErrorReady(str);
        }else if(i==MyMethods.GetAdvocacyType.getMethodValue()){

                addInfoPresenter.onErrorReady(str);

        }else if(i==MyMethods.getDegreeTypeNames.getMethodValue()){

                addInfoPresenter.onErrorReady(str);

        }else if(i==MyMethods.getCountries.getMethodValue()){

                addressPresenter.onErrorReady(str);

        }else if(i==MyMethods.getProvinces.getMethodValue()){

                addressPresenter.onErrorReady(str);

        }else if(i==MyMethods.getCities.getMethodValue()){

                addressPresenter.onErrorReady(str);

        }else if(i==MyMethods.getBanks.getMethodValue()){

            addInfoPresenter.onErrorReady(str);

        }else if(i==MyMethods.IdentityConfirmationUser.getMethodValue()){

                addInfoPresenter.onErrorReady(str);

        }else if(i==MyMethods.ProfileInfo.getMethodValue()){
            if(mainPresenter!=null){
                mainPresenter.onErrorReady(str);
            }else if(waitingPresenter!=null){
                waitingPresenter.onErrorReady(str);

            }
        }else if(i==MyMethods.getActiveOffers.getMethodValue()){
            mainPresenter.onErrorReady(str);
        }else if(i==MyMethods.getRequests.getMethodValue()){
            requestPresenter.onErrorReady(str);
        }else if(i==MyMethods.getWaitedRequestDetial.getMethodValue()){
            requestDetailPresenter.onError(str);
        }else if(i==MyMethods.acceptRequest.getMethodValue()){
            requestDetailPresenter.onError(str);

        }else if(i==MyMethods.getHistoryOffers.getMethodValue()){
            offerPresenter.onErrorReady(str);
        }else if(i==MyMethods.getReadyToAnswerRequestDetial.getMethodValue()) {
            advicePresenter.onError(str);
        }else if(i==MyMethods.cancelOffer.getMethodValue()){
            advicePresenter.onError(str);
        }else if(i==MyMethods.SendGCMToken.getMethodValue()){

            signInPresenter.onErrorReady(str);

        }else if(i==MyMethods.Logout.getMethodValue()){
            mainPresenter.onErrorReady(str);
        }else if(i==MyMethods.GetCallState.getMethodValue()){
            advicePresenter.onError(str);
        }

       /* else if(i == MyMethods.GetActiveList.getMethodValue()){
            mainPresenter.onErrorReady(str);
        }else if(i==MyMethods.GetWaitedDetail.getMethodValue()){
            choseLawerPresenter.onErrorReady(str);
        }else if(i==MyMethods.GetActivatedDetail.getMethodValue()){
            choseLawerPresenter.onErrorReady(str);

        }
        else if(i==MyMethods.InsertRequest.getMethodValue()){

                insertRequestPresenter.onError(str);

        }else if(i==MyMethods.AcceptRequest.getMethodValue()){
            lawyerPresenter.onError(str);
        }else if(i==MyMethods.CancelReq.getMethodValue()){
            if(lawyerPresenter!=null) {
                lawyerPresenter.onError(str);
            }else if(choseLawerPresenter!=null){
                choseLawerPresenter.onErrorReady(str);
            }else if(preCallPresenter!=null){
                preCallPresenter.onError(str);
            }

        }else if(i==MyMethods.ProfileInfo.getMethodValue()){


                if(mainPresenter!=null){

                    mainPresenter.onErrorReady(str);

            }
        }else if(i==MyMethods.UpdateProfile.getMethodValue()){

                profilePresenter.onError(str);

        }else if(i==MyMethods.GetDoneDetial.getMethodValue()){
            preCallPresenter.onError(str);
        }else if(i==MyMethods.GetMyRequest.getMethodValue()){
            myRequestPresenter.onError(str);

        }*/
    }

    @Override
    public void onSuccess(int i, JsonObject jsonObject) throws JSONException {
        Gson gson = new Gson();
        JsonObject error =  jsonObject.getAsJsonObject("resultState");
        ResultState errorRes = gson.fromJson(error,ResultState.class);

        if(i== MyMethods.Authentication.getMethodValue()){
            AuthenticationRes res = gson.fromJson(jsonObject,AuthenticationRes.class);

            if(res.getResultState().isSuccess()){
                signInPresenter.onRespReady(res);
            }else {
                signInPresenter.onErrorReady(res.getResultState().getMessage());
            }
        }else if(i==MyMethods.GetCats.getMethodValue()){
            if(errorRes.isSuccess()){
                JsonArray jsonArray = jsonObject.get("result").getAsJsonArray();
                Type listType = new TypeToken<List<Category>>(){}.getType();
                List<Category> posts = gson.fromJson(jsonArray.toString(), listType);
                addInfoPresenter.onReadyCats(posts);
            }else {
                addInfoPresenter.onErrorReady(errorRes.getMessage());
            }
        }else if(i==MyMethods.GetAdvocacyType.getMethodValue()){
            if(errorRes.isSuccess()){
                JsonArray jsonArray = jsonObject.get("result").getAsJsonArray();
                Type listType = new TypeToken<List<AdvocancyTypeName>>(){}.getType();
                List<AdvocancyTypeName> posts = gson.fromJson(jsonArray.toString(), listType);
                addInfoPresenter.onReadyAdvocatedTYPE(posts);
            }else {
                addInfoPresenter.onErrorReady(errorRes.getMessage());
            }
        }else if(i==MyMethods.getDegreeTypeNames.getMethodValue()){
            if(errorRes.isSuccess()){
                JsonArray jsonArray = jsonObject.get("result").getAsJsonArray();
                Type listType = new TypeToken<List<DegreesName>>(){}.getType();
                List<DegreesName> posts = gson.fromJson(jsonArray.toString(), listType);
                addInfoPresenter.onReadyDegrees(posts);
            }else {
                addInfoPresenter.onErrorReady(errorRes.getMessage());
            }
        }else if(i==MyMethods.getCountries.getMethodValue()){
            if(errorRes.isSuccess()){
                JsonArray jsonArray = jsonObject.get("result").getAsJsonArray();
                Type listType = new TypeToken<List<Country>>(){}.getType();
                List<Country> posts = gson.fromJson(jsonArray.toString(), listType);
                addressPresenter.onReadyCountry(posts);
            }else {
                addressPresenter.onErrorReady(errorRes.getMessage());
            }
        }else if(i==MyMethods.getProvinces.getMethodValue()){
            if(errorRes.isSuccess()){
                JsonArray jsonArray = jsonObject.get("result").getAsJsonArray();
                Type listType = new TypeToken<List<Province>>(){}.getType();
                List<Province> posts = gson.fromJson(jsonArray.toString(), listType);
                addressPresenter.onReadyProvince(posts);
            }else {
                addressPresenter.onErrorReady(errorRes.getMessage());
            }
        }else if(i==MyMethods.getCities.getMethodValue()){
            if(errorRes.isSuccess()){
                JsonArray jsonArray = jsonObject.get("result").getAsJsonArray();
                Type listType = new TypeToken<List<City>>(){}.getType();
                List<City> posts = gson.fromJson(jsonArray.toString(), listType);
                addressPresenter.onReadyCities(posts);
            }else {
                addressPresenter.onErrorReady(errorRes.getMessage());
            }
        }else if(i==MyMethods.getArea.getMethodValue()){
            if(errorRes.isSuccess()){
                JsonArray jsonArray = jsonObject.get("result").getAsJsonArray();
                Type listType = new TypeToken<List<Area>>(){}.getType();
                List<Area> posts = gson.fromJson(jsonArray.toString(), listType);
                addressPresenter.onReadyArea(posts);
            }else {
                addressPresenter.onErrorReady(errorRes.getMessage());
            }
        }else if(i==MyMethods.getBanks.getMethodValue()){
            if(errorRes.isSuccess()){
                JsonArray jsonArray = jsonObject.get("result").getAsJsonArray();
                Type listType = new TypeToken<List<Bank>>(){}.getType();
                List<Bank> posts = gson.fromJson(jsonArray.toString(), listType);
                addInfoPresenter.onReadyBanks(posts);
            }else {
                addInfoPresenter.onErrorReady(errorRes.getMessage());
            }
        }else if(i==MyMethods.IdentityConfirmationUser.getMethodValue()){
            if(errorRes.isSuccess()){
                addInfoPresenter.onSuccessFull();

            }else {
                addInfoPresenter.onErrorReady(errorRes.getMessage());
            }
        }else if(i==MyMethods.ProfileInfo.getMethodValue()){
            if(errorRes.isSuccess()){
                if(mainPresenter!=null){
                    JsonObject obj = jsonObject.get("result").getAsJsonObject();
                    ProfileInfo profile = gson.fromJson(obj,ProfileInfo.class);
                    mainPresenter.onSuccessProfile(profile);
                }else if(waitingPresenter!=null){
                    JsonObject obj = jsonObject.get("result").getAsJsonObject();
                    ProfileInfo profile = gson.fromJson(obj,ProfileInfo.class);
                    waitingPresenter.onSuccessProfile(profile);

                }
            }else {
                if(mainPresenter!=null){
                    mainPresenter.onErrorReady(errorRes.getMessage());
                }else if(waitingPresenter!=null){
                    waitingPresenter.onErrorReady(errorRes.getMessage());
                }
            }
        }else if(i==MyMethods.getActiveOffers.getMethodValue()){
            if(errorRes.isSuccess()){
                JsonArray jsonArray = jsonObject.get("result").getAsJsonArray();
                Type listType = new TypeToken<List<Offer>>(){}.getType();
                List<Offer> posts = gson.fromJson(jsonArray.toString(), listType);
                mainPresenter.onListReady(posts);
            }else {
                mainPresenter.onErrorReady(errorRes.getMessage());
            }
        }else if(i==MyMethods.getRequests.getMethodValue()){
            if(errorRes.isSuccess()){
                JsonArray jsonArray = jsonObject.get("result").getAsJsonArray();
                Type listType = new TypeToken<List<Request>>(){}.getType();
                List<Request> posts = gson.fromJson(jsonArray.toString(), listType);
                requestPresenter.onListReady(posts);
            }else {
                requestPresenter.onErrorReady(errorRes.getMessage());
            }
        }else if(i==MyMethods.getWaitedRequestDetial.getMethodValue()){
            if(errorRes.isSuccess()) {
                JsonObject obj = jsonObject.get("result").getAsJsonObject();
                WaitedRequestDetial detail = gson.fromJson(obj, WaitedRequestDetial.class);
                requestDetailPresenter.onGetDateil(detail);
            }else {
                requestDetailPresenter.onError(errorRes.getMessage());

            }
        }else if(i==MyMethods.acceptRequest.getMethodValue()){
            if(errorRes.isSuccess()) {

                requestDetailPresenter.onAcceptRequest();
            }else {
                requestDetailPresenter.onError(errorRes.getMessage());

            }
        }else if(i==MyMethods.getHistoryOffers.getMethodValue()){
            if(errorRes.isSuccess()){
                JsonArray jsonArray = jsonObject.get("result").getAsJsonArray();
                Type listType = new TypeToken<List<Offer>>(){}.getType();
                List<Offer> posts = gson.fromJson(jsonArray.toString(), listType);
                offerPresenter.onListReady(posts);
            }else {
                offerPresenter.onErrorReady(errorRes.getMessage());
            }
        }else if(i==MyMethods.getReadyToAnswerRequestDetial.getMethodValue()){
            if(errorRes.isSuccess()) {
                JsonObject obj = jsonObject.get("result").getAsJsonObject();
                AdviceDetail detail = gson.fromJson(obj, AdviceDetail.class);
                advicePresenter.onGetDateil(detail);
            }else {
                advicePresenter.onError(errorRes.getMessage());

            }
        }else if(i==MyMethods.getCanceledRequestBeforeAssignedDetial.getMethodValue()){
            if(errorRes.isSuccess()) {
                JsonObject obj = jsonObject.get("result").getAsJsonObject();
                AdviceDetail detail = gson.fromJson(obj, AdviceDetail.class);
                advicePresenter.onGetDateil(detail);
            }else {
                advicePresenter.onError(errorRes.getMessage());

            }
        }else if(i==MyMethods.getAssignedRequestDetail.getMethodValue()){
            if(errorRes.isSuccess()) {
                JsonObject obj = jsonObject.get("result").getAsJsonObject();
                AssignedResponse detail = gson.fromJson(obj, AssignedResponse.class);
                advicePresenter.onGetDateil(detail);
            }else {
                advicePresenter.onError(errorRes.getMessage());

            }
        }else if(i==MyMethods.cancelOffer.getMethodValue()){
            if(errorRes.isSuccess()) {
                advicePresenter.onCancell();
            }else {
                advicePresenter.onError(errorRes.getMessage());

            }
        }else if(i==MyMethods.SendGCMToken.getMethodValue()){
            if(errorRes.isSuccess()){
                signInPresenter.onGCMOK();
            }else {
                signInPresenter.onGCMOK();
            }
        }else if(i==MyMethods.Logout.getMethodValue()){
            if(errorRes.isSuccess()){
                mainPresenter.onSuccessLogout();
            }
        }else if(i==MyMethods.GetCallState.getMethodValue()){
            if(errorRes.isSuccess()){

                JsonObject object = jsonObject.get("result").getAsJsonObject();
                CallStateResponse res = gson.fromJson(object, CallStateResponse.class);

                advicePresenter.onreadyCallRes(res);
            }else {
                advicePresenter.onError(errorRes.getMessage());
            }
        }
    /*    else if(i==MyMethods.GetSubCat.getMethodValue()){
            if(errorRes.isSuccess()){
                JsonArray jsonArray = jsonObject.get("result").getAsJsonArray();
                Type listType = new TypeToken<List<SubCat>>(){}.getType();
                List<SubCat> posts = gson.fromJson(jsonArray.toString(), listType);
                subCatPresenter.onReadySubCat(posts);
            }else {
                subCatPresenter.onErrorReady(errorRes.getMessage());
            }
        }else if(i == MyMethods.GetActiveList.getMethodValue()){
            JsonArray jsonArray = jsonObject.get("result").getAsJsonArray();
            Type listType = new TypeToken<List<Advice>>(){}.getType();
            List<Advice> posts = gson.fromJson(jsonArray.toString(), listType);

            mainPresenter.onListReady(posts);
        }else if(i==MyMethods.GetWaitedDetail.getMethodValue()){
            if(errorRes.isSuccess()){
                JsonObject obj = jsonObject.get("result").getAsJsonObject();
                WaitedAdviceDetial res = gson.fromJson(obj,WaitedAdviceDetial.class);
                choseLawerPresenter.readyLawyer(res);
            }
        }else if(i==MyMethods.GetActivatedDetail.getMethodValue()){
            if(errorRes.isSuccess()){
                JsonObject obj = jsonObject.get("result").getAsJsonObject();
                WaitedAdviceDetial res = gson.fromJson(obj,WaitedAdviceDetial.class);
                choseLawerPresenter.readyLawyer(res);
            }
        }
        else if(i==MyMethods.InsertRequest.getMethodValue()){
            if(errorRes.isSuccess()){
                insertRequestPresenter.onReadyCountry();
            }else {
                insertRequestPresenter.onError(errorRes.getMessage());
            }
        }else if(i==MyMethods.AcceptRequest.getMethodValue()){
            if(errorRes.isSuccess()){
                JsonObject obj = jsonObject.get("result").getAsJsonObject();
                String paymentUrl = obj.get("paymentUrl").getAsString();
                lawyerPresenter.onSuccessAccept(paymentUrl);
            }else {
                lawyerPresenter.onError(errorRes.getMessage());
            }
        }else if(i==MyMethods.CancelReq.getMethodValue()){
            if(errorRes.isSuccess()){
                if(lawyerPresenter!=null) {
                    lawyerPresenter.onSuccessCancel();
                }else if(choseLawerPresenter!=null){
                    choseLawerPresenter.onSuccessCancel();
                }else if(preCallPresenter!=null){
                    preCallPresenter.onSuccessCancel();
                }
            }else {
                if(lawyerPresenter!=null) {
                    lawyerPresenter.onError(errorRes.getMessage());
                }else if(choseLawerPresenter!=null){
                    choseLawerPresenter.onErrorReady(errorRes.getMessage());
                }else if(preCallPresenter!=null){
                    preCallPresenter.onError(errorRes.getMessage());
                }

            }
        }else if(i==MyMethods.ProfileInfo.getMethodValue()){
            if(errorRes.isSuccess()){
                if(mainPresenter!=null){
                    JsonObject obj = jsonObject.get("result").getAsJsonObject();
                    Profile profile = gson.fromJson(obj,Profile.class);
                    mainPresenter.onSuccessProfile(profile);
                }
            }else {
                if(mainPresenter!=null){
                    JsonObject obj = jsonObject.get("result").getAsJsonObject();
                    Profile profile = gson.fromJson(obj,Profile.class);
                    mainPresenter.onErrorReady(errorRes.getMessage());
                }
            }
        }else if(i==MyMethods.UpdateProfile.getMethodValue()){
            if(errorRes.isSuccess()){
                JsonObject obj = jsonObject.get("result").getAsJsonObject();
                Profile profile = gson.fromJson(obj,Profile.class);
                profilePresenter.onReadyCountry(profile);
            }else {
                profilePresenter.onError(errorRes.getMessage());
            }
        }else if(i==MyMethods.GetDoneDetial.getMethodValue()){
            if(errorRes.isSuccess()){
                JsonObject object = jsonObject.get("result").getAsJsonObject();
                DoneAdviceDetailRes resp = gson.fromJson(object,DoneAdviceDetailRes.class);
                preCallPresenter.onSuccessRes(resp);
            }else {
                preCallPresenter.onError(errorRes.getMessage());
            }
        }else if(i==MyMethods.GetMyRequest.getMethodValue()){
            if(errorRes.isSuccess()){
                JsonArray jsonArray = jsonObject.get("result").getAsJsonArray();
                Type listType = new TypeToken<List<MyRequest>>(){}.getType();
                List<MyRequest> posts = gson.fromJson(jsonArray.toString(), listType);
                myRequestPresenter.onListReady(posts);
            }else {
                myRequestPresenter.onError(errorRes.getMessage());
            }
        }*/
    }

    public void sendGCM(String token, String gcmToken) {
        CallerService.sendGCMToken(this,token,gcmToken);
    }

    public void signOut(String token) {
        CallerService.logOut(this,token);
    }
    public void callAuthentication(AuthReq authReq) {
        CallerService.signUp(this,authReq);
    }

    public void getCats(CatReq req) {
        CallerService.getCats(this, req);
    }

    public void getAdvocacyType(long timeStamp) {
        CallerService.getAdvocacyTypesName(this,timeStamp);
    }

    public void getDegreeTypeNames(int timeStamp) {
        CallerService.getDegreeTypeNames(this,timeStamp);
    }

    public void getContries(int timeStamp) {
        CallerService.getCountries(this,timeStamp);
    }

    public void getProvinces(int countryId, long timeStamp) {
        CallerService.getProvinces(this,timeStamp,countryId);
    }

    public void getCities(int id, long timeStamp) {
        CallerService.getCities(this,timeStamp,id);
    }

    public void getAreas(int id, int timeStamp) {
        CallerService.getAreas(this,timeStamp,id);
    }

    public void getBanks(long timeStamp) {
        CallerService.getBanks(this,timeStamp);
    }

    public void AddInfo(AddInfoRequest request) {
        CallerService.IdentityConfirmationUser(this,request);
    }
    public void getUserInfo(ProfileInfoReq profileInfoReq) {
        CallerService.getProfileInfo(this,profileInfoReq);
    }
    public void getActiveOffers(String token) {
        CallerService.getActiveOffers(this,token);
    }


    public void getRequests(String token) {
        CallerService.getRequests(this,token);
    }

    public void getWaitedDetails(GetWaitedDetailsReq req) {
        CallerService.getRequestsWaitedDetail(this,req);
    }

    public void acceptReq(AcceptReq req) {
        CallerService.acceptRequest(this,req);
    }

    public void getOffers(String token) {
        CallerService.getHistoryOffers(this,token);
    }

    public void getReadyToAnswerRequestDetial(DoneAdviceDetailReq req) {
        CallerService.getReadyToAnswerRequestDetial(this,req);
    }
    public void getCanceledRequestBeforeAssignedDetial(DoneAdviceDetailReq req) {
        CallerService.getCanceledRequestBeforeAssignedDetial(this,req);
    }

    public void cancelOffer(CancelOfferReq req) {
        CallerService.cancelOffer(this,req);
    }

    public void getAssignedRequestDetail(DoneAdviceDetailReq req) {
        CallerService.getAssignedRequestDetail(this,req);
    }

    public void updateCall(CallUpdateReq req) {
        CallerService.updateCall(this,req);
    }
    public void getCallState(String token, String offerId) {
        CallerService.getCallState(this,token,offerId);
    }
}
