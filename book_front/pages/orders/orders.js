var app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    orders: [],
    waitshipments:[],     //待发货的商品的数组
    waitreceive:[],       //等待收获的商品
    accomplish:[],         //订单完成的数组
    status:0,         //订单的状态
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // this.data.options = options.item
    let that = this

    //获取用户信息
    let user = app.globalData.userInfo
    this.setData({ appInstance: app });

    console.log(options.item)
    this.setData({
      status:options.item
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

    //将三个数组置空，防止页面无法刷新
    this.data.waitreceive = []
    this.data.accomplish = []
    this.data.waitshipments = []
    this.data.status =0;
    var that = this;
    //console.log(app.globalData.user.user.uid)
    console.log(this.data.status)
    let Url = app.globalData.host + 'Orders?loadBy=condition&userId=' + app.globalData.userInfo.userId +'&status=' + this.data.status
    wx.request({
      url: '' + Url,
      method:'GET',
      data:{

      },
      header:{
        'content-type': 'application/json' // 默认值
      },
      success:res=>{
        console.log(res.data)
        this.setData({
          orders:res.data
        })
        if (this.data.status == 0 || this.data.status == 1 || this.data.status == 2) {

          //处理orders数据，让orders 的 status == 0 的显示

          let i = 0
          for (i; i < that.data.orders.length; i++) {
         // console.log(this.data.orders[i])
            //让状态为0的进入未发货的数组
            if (this.data.orders[i].status == 0) {
              this.data.waitshipments.push(this.data.orders[i])
            } else if (this.data.orders[i].status == 1) {
              //代收获
              this.data.waitreceive.push(this.data.orders[i])
            } else {
              //订单完成
              this.data.accomplish.push(this.data.orders[i])
            }
          }
        }
        
      }
    })
  },
/**
 * 付款
 */
  topay: function (event){
    console.log(event);
    var id = event.currentTarget.dataset.orderid;
    var Url = app.globalData.host + "Orders?loadBy=id";
    wx.request({
      url: Url,
      data: {
        action: 'update',
        id: id,
        status: 1
      },
      dataType: 'json',
      success: function (res) {
        if (res.statusCode == 200) {
          if (res.data.status == 'OK') {
            wx.showToast({
              title: '支付成功'
            })
            
          } else {
            wx.showToast({
              title: '支付失败',
              image: '/image/close.png'
            })
        }
        }
      },
      fail(res) {
        console.log(res)
      }
    })
  },
  confirmReceive: function(event){
    var id = event.currentTarget.dataset.orderid;
    var Url = app.globalData.host + "Orders?loadBy=id";
    wx.request({
      url: Url,
      data: {
        action: 'update',
        id: id,
        status: 3
      },
      dataType: 'json',
      success: function (res) {
        if (res.statusCode == 200) {
          if (res.data.status == 'OK') {
            wx.showToast({
              title: '收货成功'
            })
            
          } else {
            wx.showToast({
              title: '收货失败',
              image: '/image/close.png'
            })
          }
        }
      },
      fail(res) {
        console.log(res)
      }
    })
  },
  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
    
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
    
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
    
  }
})