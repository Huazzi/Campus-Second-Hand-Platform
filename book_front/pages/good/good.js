// pages/good/good.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    good:{},
    goodImgs:[],
    appInstance:{},
    msgList:[],
    leave_msg:false,
    _options:{},
    tempMsg:"",
    startViewTime: 0 // 保存页面加载时间
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // 记录页面加载时间
    this.data.startViewTime = Date.now();
    this.setData({_options:options})
    let that = this
    let app = getApp()
    this.setData({appInstance:app})
    
    // 获取商品ID，支持多种参数名称：goodId、id
    let itemId = options.goodId || options.id;
    
    console.log("获取到的商品ID:", itemId);
    
    // 检查商品ID是否有效
    if (!itemId || itemId === 'undefined' || itemId.startsWith('temp_')) {
      wx.showToast({
        title: '商品ID无效',
        icon: 'none',
        duration: 2000
      });
      setTimeout(function() {
        wx.navigateBack({
          delta: 1
        });
      }, 2000);
      return;
    }
    
    let Url = app.globalData.host + 'Good?loadBy=id&id=' + itemId;
    wx.request({
      url: Url,
      success(res){
        // 检查返回的数据是否有效
        if (!res.data || (!res.data.goodId && !res.data.id)) {
          wx.showToast({
            title: '未找到商品信息',
            icon: 'none',
            duration: 2000
          });
          setTimeout(function() {
            wx.navigateBack({
              delta: 1
            });
          }, 2000);
          return;
        }

        //加载商品信息
        console.log("获取到的商品数据:", res.data);
        let bean = res.data
        that.setData({good:bean})

        //加载商品图片
        var imgs = []
        if (bean.images) {
          imgs = bean.images.split(";")
          // 移除末尾可能的空字符串
          if (imgs.length > 0 && imgs[imgs.length - 1] === "") {
            imgs.pop();
          }
          for (var i = 0; i < imgs.length; i++)
            imgs[i] = that.data.appInstance.globalData.host + "img/goods/" + imgs[i]
        } else {
          // 处理没有图片的情况
          imgs = [that.data.appInstance.globalData.host + "img/goods/default_cover.png"] // 使用默认图片
        }
        that.setData({ goodImgs: imgs })

        // 确保有效的商品ID用于后续请求
        const goodId = bean.goodId || bean.id;
        if (!goodId) {
          console.error("商品数据中缺少有效的ID");
          return;
        }

        //加载收藏信息
        Url = app.globalData.host + 'CollectGood'
        wx.request({
          url: Url,
          data: {
            action:'isCollected',
            userId: app.globalData.userInfo.userId,
            goodId: goodId
          },
          dataType:'json',
          success(res){
            that.setData(res.data)
          }
        })

        //加载留言列表
        Url = app.globalData.host + "Messages?type=getLeaveMsg&goodid=" + goodId
        wx.request({
          url: Url,
          dataType: 'json',
          success(res) {
            console.log(res.data)
            that.setData({msgList:res.data})
          }
        })
      },
      fail(err) {
        console.error("请求商品数据失败:", err);
        wx.showToast({
          title: '加载商品信息失败',
          icon: 'none',
          duration: 2000
        });
        setTimeout(function() {
          wx.navigateBack({
            delta: 1
          });
        }, 2000);
      }
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
  
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
  
  },

  /**
   * 生命周期函数--监听页面卸载
   * 在页面卸载时记录用户浏览行为
   * 计算浏览时长并发送到服务器
   */
  onUnload: function () {
    const app = getApp();
    // 计算浏览时长（秒）
    const viewDuration = Math.floor((Date.now() - this.data.startViewTime) / 1000);
  
    // 记录用户浏览行为
    if (app.globalData.userInfo && app.globalData.userInfo.userId && this.data.good) {
      this.recordUserView(this.data.good.goodId, viewDuration);
    }
  },

  // 记录用户浏览商品行为
  recordUserView: function(goodId, duration) {
    const app = getApp();
    if (!app.globalData.userInfo || !app.globalData.userInfo.userId) {
      return;
    }
  
    wx.request({
      url: app.globalData.baseUrl + '/api/recommendations/view',
      method: 'POST',
      data: {
        userId: app.globalData.userInfo.userId,
        goodId: goodId,
        duration: duration
      },
      success: () => {
        console.log('浏览记录已保存，时长:', duration, '秒');
      }
    });
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    this.onLoad(this.data._options)
    wx.stopPullDownRefresh()
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
  
  },

  /**
   * 显示留言输入框
   * 该函数用于显示留言输入框
  */ 
  leave_msg:function(){

    this.setData({ leave_msg: true })
  },

  cancel_msg:function(){

    this.setData({ leave_msg: false })
  },

  //用户点击发送按钮
  send:function(res){
    console.log(res.detail.value)
    var Url = this.data.appInstance.globalData.host + "Messages"
    var send = this.data.appInstance.globalData.userInfo.userId
    let that = this

    console.log(send)

    wx.request({
      url: Url,
      data:{
        'type':'leave_msg',
        'send':send,
        'receive':that.data.good.goodOwner.userid,
        'good':that.data.good.goodId,
        'content':res.detail.value.content
      },
      success(res){
        console.log(res.data)
        that.onLoad(that.data._options)
        that.setData({ tempMsg: '' })
      }
    })
  },

  chat(res){
    wx.navigateTo({
      url: '/pages/chat/chat?sendId=' + this.data.good.goodOwner.userid + '&sendUser=' + this.data.good.goodOwner.username + '&sendNick=' + this.data.good.goodOwner.nickname + '&goodId=' + this.data.good.goodId
    })
  },
  //收藏商品
  collect:function(e){
    let app = getApp()
    let that = this
    let Url = app.globalData.host + 'CollectGood'
    wx.request({
      url: Url,
      data: {
        action: 'collect',
        userId: app.globalData.userInfo.userId,
        goodId: this.data.good.goodId
      },
      dataType: 'json',
      success(res) {
        
        console.log(res.data)
        if (res.statusCode == 200) {
          if (res.data.status == 'OK') {
            that.setData({ isCollect: true })
            wx.showToast({
              title: '收藏成功'
            })
            that.setData({"good.collect":that.data.good.collect + 1})
          } else {
            wx.showToast({
              title: '收藏失败',
              image: '/image/close.png'
            })
          }
        }else {
          wx.showToast({
            title: '收藏失败',
            image: '/image/close.png'
          })
        }
      }
    })
  },
  //取消收藏商品
  unCollect: function (e) {
    let app = getApp()
    let that = this
    let Url = app.globalData.host + 'CollectGood'
    wx.request({
      url: Url,
      data: {
        action: 'unCollect',
        userId: app.globalData.userInfo.userId,
        goodId: this.data.good.goodId
      },
      dataType: 'json',
      success(res) {

        console.log(res.data)
        if (res.statusCode == 200) {
          if (res.data.status == 'OK') {
            that.setData({ isCollect: false })
            wx.showToast({
              title: '取消成功'
            })
            that.setData({ "good.collect": that.data.good.collect - 1})
          } else {
            wx.showToast({
              title: '取消失败',
              image: '/image/close.png'
            })
          }
        } else {
          wx.showToast({
            title: '取消失败',
            image: '/image/close.png'
          })
        }
      }
    })
  },
  //购买商品
  buy: function (e) {
    let app = getApp()
    let that = this
    let Url = app.globalData.host + 'AddOrder'
    wx.request({
      url: Url,
      data: {
        userId: app.globalData.userInfo.userId,
        goodid: this.data.good.goodId,
        num:1
      },
      dataType: 'json',
      success(res) {

        console.log(res.data)
        if (res.statusCode == 200) {
          if (res.data.status == 'OK') {
            that.setData({ isCollect: true })
            wx.showToast({
              title: '提交订单成功'
            })
            that.setData({ "good.collect": that.data.good.collect + 1 })
          } else {
            wx.showToast({
              title: '提交失败',
              image: '/image/close.png'
            })
          }
        } else {
          wx.showToast({
            title: '提交订单失败',
            image: '/image/close.png'
          })
        }
      }
    })
  },
  viewImage:function(e){
    let that = this
    wx.previewImage({
      current:e.currentTarget.dataset.imgurl,
      urls: that.data.goodImgs
    })
  }
})