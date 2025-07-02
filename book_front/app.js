App({

  globalData:{
    userInfo:{},
    APPID:"wx0ff276098bd13b71",
    APPSERCET:"e320bcae5db72c81b8af57c04dad7c2c",
    host: "http://localhost:8080/book_api/",
    // baseUrl: "http://localhost:8000" // 添加推荐系统API的基础URL
    baseUrl: "http://localhost:8080/book_api" // 修正：指向Java后端API
  },
  /**
   * 当小程序初始化完成时，会触发 onLaunch（全局只触发一次）
   */
  onLaunch: function () {
    let that = this;
    //登陆小程序
    wx.login({

      //登录成功
      success(res){
        if(res.errMsg == 'login:ok'){

          let js_code = res.code
          console.log(res.code)
          that.globalData.js_code=res.code

          //获取用户信息权限
          // wx.authorize({
          //   scope: 'scope.userInfo',
          //   success(res) {
          //     console.log("获取用户信息权限");
          //   }
          // })

          //获取用户信息
          // wx.getUserInfo({
          //   success(res) {
          //     console.log("获取用户信息")
          //     console.log(res)
          //   },
          //   fail(res) {
          //     console.log(res)
          //   }
          // })
        }
        else
          //登陆失败
          wx.showModal({
            title: '提示',
            content: '登陆失败',
            success: function (res) {
              if (res.confirm) {
                console.log('用户点击确定')
              }
            }
          })

      }
    })
  },

  /**
   * 当小程序启动，或从后台进入前台显示，会触发 onShow
   */
  onShow: function (options) {
    
  },

  /**
   * 当小程序从前台进入后台，会触发 onHide
   */
  onHide: function () {
    
  },

  /**
   * 当小程序发生脚本错误，或者 api 调用失败时，会触发 onError 并带上错误信息
   */
  onError: function (msg) {
    
  },
  getUser:function(user){
    // console.log(user)
    let that = this
    console.log('打印user:', user);
    that.globalData.userInfo = JSON.parse(user)
    console.log(that.globalData.userInfo)

    //获取小程序内用户信息
    var Url = that.globalData.host + 'Login?code=' + that.globalData.js_code + "&nickname=" + that.globalData.userInfo.nickName + "&head=" + that.globalData.userInfo.avatarUrl + "&gender=" + that.globalData.userInfo.gender
    console.log(Url)
    wx.request({
      url: that.globalData.host + 'Login?code=' + that.globalData.js_code + "&nickname=" + that.globalData.userInfo.nickName + "&head=" + that.globalData.userInfo.avatarUrl + "&gender=" + that.globalData.userInfo.gender,
      success(res) {

        var userData = res.data;
        console.log("userData")
        console.log(userData)
        if (res.statusCode == 200) {

          that.globalData.userInfo.avatarUrl = userData.head
          that.globalData.userInfo.nickName = userData.nickname
          that.globalData.userInfo.gender = userData.sex
          that.globalData.userInfo.userName = userData.username
          that.globalData.userInfo.mobilePhone = userData.mobilephone
          that.globalData.userInfo.userId = userData.userid
          that.globalData.userInfo.address = userData.address
          that.globalData.userInfo.college = userData.college
          console.log("用户信息+")
          console.log(that.globalData.userInfo)
          wx.switchTab({
            url: '/pages/index/index',
          })
        } else {
          wx.showToast({
            title: '网络错误'
          })
        }
      }
    })
  }
})
