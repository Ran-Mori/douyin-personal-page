package zy.douyinpersonalpage.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import zy.douyinpersonalpage.model.UserProfile
import zy.douyinpersonalpage.model.UserProfileResponse
import zy.douyinpersonalpage.retrofitservice.impl.UserProfileService

/**
 * PersonalInformationFragment对应的ViewModel
 */
class PersonalInformationFragmentViewModel(val inputUserProfile: UserProfile?):ViewModel() {
    /**
     * 固定模式，一个'_name'的MutableLiveData，一个'name'的LiveData
     */
    private var _userProfile = MutableLiveData<UserProfile>()
    val userProfile:LiveData<UserProfile>
        get() = _userProfile

    /**
     * ViewModel初始化操作，即如果为空就去获取
     */
    init {
        if (inputUserProfile == null){
            updateUserProfile()
        }
    }

    /**
     * 通过retrofit + rxjava来从URL获取内容并做更新
     */
    fun updateUserProfile(){
        UserProfileService.getUserProfile()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<UserProfileResponse> {
                override fun onSubscribe(d: Disposable) {
                    //Log.d("MainActivity","onSubscribe")
                }

                override fun onNext(t: UserProfileResponse) {
                    //Log.d("MainActivity","onNext")
                    /**
                     * 对'_userProfile'进行一次赋值操作，不然观察者感知不到
                     */
                    val userProfile = t.userProfile
                    _userProfile.value = userProfile
                }

                override fun onError(e: Throwable) {
                    //Log.d("MainActivity","onError${e.printStackTrace()}")
                }

                override fun onComplete() {
                    //Log.d("MainActivity","onComplete")
                }
            })
    }
}