Page({
  /**
   * 页面的初始数据
   */
  data: {
    goodImgs: [],
    catagoryList: ['请选择', '图书音像', '学习办公', '数码产品', '服饰', '运动户外', '居家日用', '其它'],
    index: 0,
    tradeMethodList: ['自提柜', '当面交易', '送至宿舍楼下'], // 新增交易方式列表
    tradeMethodIndex: 0, // 新增当前选择的交易方式索引
    description: '', // 新增：用于存储商品描述内容
    conditionList: ['请选择', '全新', '轻微使用痕迹', '明显使用痕迹'], // 新增：成色选项
    conditionIndex: 0, // 新增：当前选择的成色索引
    functionStatusList: ['请选择', '功能完好', '有小问题但不影响使用', '维修过可正常使用', '无法正常使用'], // 新增：功能状态选项
    functionStatusIndex: 0, // 新增：当前选择的功能状态索引
    buyTime: '', // 新增：购买时间
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.appInstance = getApp()
  },

  /**
   * 确定发布
   */
  addGood: function (e) {
    //空参检查
    if (this.data.index == 0) {
      wx.showModal({
        title: '提示',
        content: '请选择商品分类',
        showCancel: false
      })
      return
    }

    let app = getApp()
    let that = this
    var Url = app.globalData.host + 'AddGood'
    var parameters = e.detail.value
    parameters.ownerId = app.globalData.userInfo.userId
    parameters.num = 1
    parameters.catagory = this.data.catagoryList[this.data.index]
    // parameters.tradeMethod = this.data.tradeMethodList[this.data.tradeMethodIndex] // 新增设置交易方式参数
    parameters.chubanshe = this.data.conditionList[this.data.conditionIndex] // 新增：成色
    parameters.author = this.data.functionStatusList[this.data.functionStatusIndex] // 新增：功能状态
    parameters.chubantime = this.data.buyTime // 新增：购买时间

    //空参检查
    if (parameters.name == '') {
      wx.showModal({
        title: '提示',
        content: '请输入商品名',
        showCancel: false
      })
      return
    }
    if (parameters.detail == '') {
      wx.showModal({
        title: '提示',
        content: '请输入商品描述',
        showCancel: false
      })
      return
    }
    if (parameters.price == '') {
      wx.showModal({
        title: '提示',
        content: '请输入商品价格',
        showCancel: false
      })
      return
    }
    if (parameters.foreprice == '') {
      wx.showModal({
        title: '提示',
        content: '请输入商品原价',
        showCancel: false
      })
      return
    }
    if (parameters.isbn == '') {
      wx.showModal({
        title: '提示',
        content: '请输入ISBN',
        showCancel: false
      })
      return
    }
    if (!this.data.goodImgs || this.data.goodImgs.length == 0) {
      wx.showModal({
        title: '提示',
        content: '请选择商品图片',
      })
      return
    }

    wx.request({
      url: Url,
      data: parameters,
      dataType: 'json',
      success(res) {
        if (res.statusCode == 200) {
          if (res.data.status == 'OK') {
            //上传图片
            that.uploadImgs({
              url: Url,
              imgs: that.data.goodImgs,
              goodid: res.data.goodid.toString(),

              log: function (s) {
                //全部成功上传
                if (s == this.imgs.length) {
                  wx.showToast({
                    title: '发布成功',
                    icon: 'success',
                    duration: 5000,
                    success: function () {
                      setTimeout(function () {
                        wx.navigateBack({})
                      }, 1500)
                    }
                  })
                }
              }
            })
          }
        }
      }
    })
  },

  //上传商品图片
  uploadImgs: function (data) {
    var n = data.imgs.length
    var id = data.goodid
    var succeed = 0

    for (var i = 0; i < n; i++) {
      wx.uploadFile({
        url: data.url,
        filePath: data.imgs[i],
        name: 'goodimg',
        formData: {
          "goodid": id
        },
        success: function (res) {
          var result = JSON.parse(res.data)
          if (result.status == "OK") {
            succeed++
            data.log(succeed)
          }
        }
      })
    }
  },

  //选择图片
  chooseImg: function (e) {
    let that = this
    wx.chooseImage({
      count: 7,
      success: function (res) {
        if (res.errMsg == "chooseImage:ok") {
          that.setData({ goodImgs: res.tempFilePaths })
        }
      }
    })
  },

  //设定分类
  changeCatagory: function (e) {
    this.setData({ index: e.detail.value })
  },

  // 新增：设定交易方式
  changeTradeMethod: function (e) {
    this.setData({ tradeMethodIndex: e.detail.value })
  },

  // 新增：监听成色选择
  changeCondition: function (e) {
    this.setData({ conditionIndex: e.detail.value })
  },

  // 新增：监听功能状态选择
  changeFunctionStatus: function (e) {
    this.setData({ functionStatusIndex: e.detail.value })
  },

  // 新增：监听购买时间选择
  changeBuyTime: function (e) {
    this.setData({ buyTime: e.detail.value })
  },

  // 新增：监听商品描述输入事件
  onDescriptionInput: function (e) {
    const value = e.detail.value
    this.setData({
      description: value
    })
    if (value.length >= 100) {
      wx.showToast({
        title: '已达到 100 字上限',
        icon: 'none'
      })
    }
  }
})
