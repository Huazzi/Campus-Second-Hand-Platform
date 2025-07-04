Page({
  /**
   * 页面的初始数据
   */
  data: {
    goodImgs: [],
    catagoryList: ['请选择', '学习用品', '电子产品', '生活日用', '服饰鞋包', '教材教辅', '考研资料', '课外书籍', '图书音像', '手机', '电脑配件', '耳机音响', '其它'],
    index: 0,
    tradeMethodList: ['自提柜', '当面交易', '送至宿舍楼下'], // 新增交易方式列表
    tradeMethodIndex: 0, // 新增当前选择的交易方式索引
    description: '', // 新增：用于存储商品描述内容
    descriptionLength: 0, // 描述字符计数
    conditionList: ['请选择', '全新', '轻微使用痕迹', '明显使用痕迹'], // 新增：成色选项
    conditionIndex: 0, // 新增：当前选择的成色索引
    functionStatusList: ['请选择', '功能完好', '有小问题但不影响使用', '维修过可正常使用', '无法正常使用'], // 新增：功能状态选项
    functionStatusIndex: 0, // 新增：当前选择的功能状态索引
    buyTime: '', // 新增：购买时间
    isSubmitting: false, // 提交状态
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
    // 防止重复提交
    if (this.data.isSubmitting) {
      return
    }

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
    var Url = app.globalData.host + 'AddGoodServlet'
    var parameters = e.detail.value
    parameters.ownerId = app.globalData.userInfo.userId
    parameters.num = 1
    parameters.catagory = this.data.catagoryList[this.data.index]

    // 新的标准字段映射
    parameters.originalPrice = parameters.foreprice || 0 // 原价
    parameters.conditionStatus = this.data.conditionList[this.data.conditionIndex] // 商品成色
    parameters.functionStatus = this.data.functionStatusList[this.data.functionStatusIndex] // 功能状态
    parameters.purchaseTime = this.data.buyTime // 购买时间
    parameters.tradeMethod = this.data.tradeMethodList[this.data.tradeMethodIndex] // 交易方式
    parameters.brand = parameters.brand || '' // 品牌
    parameters.model = parameters.model || '' // 型号
    parameters.specifications = parameters.specifications || '' // 规格参数
    parameters.tags = parameters.tags || '' // 标签
    parameters.isNegotiable = parameters.isNegotiable || false // 是否可议价

    // 为了兼容后端，保留原字段映射作为备用
    parameters.chubanshe = this.data.conditionList[this.data.conditionIndex] // 成色（兼容）
    parameters.author = this.data.functionStatusList[this.data.functionStatusIndex] // 功能状态（兼容）
    parameters.chubantime = this.data.buyTime // 购买时间（兼容）

    //空参检查
    if (parameters.goodName == '') {
      wx.showModal({
        title: '提示',
        content: '请输入商品名称',
        showCancel: false
      })
      return
    }
    if (parameters.description == '') {
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
    // ISBN为可选字段，不再强制验证
    if (!this.data.goodImgs || this.data.goodImgs.length == 0) {
      wx.showModal({
        title: '提示',
        content: '请选择商品图片',
        showCancel: false
      })
      return
    }

    // 检查成色和功能状态是否选择
    if (this.data.conditionIndex == 0) {
      wx.showModal({
        title: '提示',
        content: '请选择商品成色',
        showCancel: false
      })
      return
    }
    if (this.data.functionStatusIndex == 0) {
      wx.showModal({
        title: '提示',
        content: '请选择功能状态',
        showCancel: false
      })
      return
    }

    // 设置提交状态
    this.setData({ isSubmitting: true })

    // 显示加载提示
    wx.showLoading({
      title: '发布中...',
      mask: true
    })

    wx.request({
      url: Url,
      method: 'POST',
      header: {
        'content-type': 'application/x-www-form-urlencoded'
      },
      data: parameters,
      dataType: 'json',
      success(res) {
        if (res.statusCode == 200) {
          if (res.data.status == 'OK') {
            wx.hideLoading()
            wx.showLoading({
              title: '上传图片中...',
              mask: true
            })

            //上传图片
            that.uploadImgs({
              url: Url,
              imgs: that.data.goodImgs,
              goodid: res.data.goodid.toString(),

              log: function (s) {
                //全部成功上传
                if (s == this.imgs.length) {
                  wx.hideLoading()
                  wx.showToast({
                    title: '发布成功',
                    icon: 'success',
                    duration: 2000,
                    success: function () {
                      setTimeout(function () {
                        wx.navigateBack({})
                      }, 1500)
                    }
                  })
                }
              }
            })
          } else {
            wx.hideLoading()
            that.setData({ isSubmitting: false })
            wx.showToast({
              title: '发布失败，请重试',
              icon: 'none'
            })
          }
        } else {
          wx.hideLoading()
          that.setData({ isSubmitting: false })
          wx.showToast({
            title: '网络错误，请重试',
            icon: 'none'
          })
        }
      },
      fail: function(err) {
        wx.hideLoading()
        that.setData({ isSubmitting: false })
        wx.showToast({
          title: '网络错误，请重试',
          icon: 'none'
        })
        console.error('发布商品失败:', err)
      }
    })
  },

  //上传商品图片
  uploadImgs: function (data) {
    var n = data.imgs.length
    var id = data.goodid
    var succeed = 0
    var failed = 0

    console.log('开始上传图片，总数:', n)

    for (var i = 0; i < n; i++) {
      wx.uploadFile({
        url: data.url,
        filePath: data.imgs[i],
        name: 'goodimg',
        formData: {
          "goodid": id
        },
        success: function (res) {
          console.log('图片上传响应:', res)
          try {
            var result = JSON.parse(res.data)
            if (result.status == "OK") {
              succeed++
              console.log('图片上传成功，当前成功数:', succeed)
              data.log(succeed)
            } else {
              failed++
              console.error('图片上传失败，服务器返回:', result)
            }
          } catch (e) {
            failed++
            console.error('解析上传响应失败:', e, res.data)
          }
        },
        fail: function (err) {
          failed++
          console.error('图片上传请求失败:', err)
          if (failed + succeed === n) {
            wx.showToast({
              title: '部分图片上传失败',
              icon: 'none'
            })
          }
        }
      })
    }
  },

  //选择图片
  chooseImg: function (e) {
    let that = this
    const currentCount = this.data.goodImgs.length
    const maxCount = 9
    const remainingCount = maxCount - currentCount

    if (remainingCount <= 0) {
      wx.showToast({
        title: '最多只能上传9张图片',
        icon: 'none'
      })
      return
    }

    wx.chooseImage({
      count: remainingCount,
      sizeType: ['compressed'], // 压缩图片
      sourceType: ['album', 'camera'],
      success: function (res) {
        if (res.errMsg == "chooseImage:ok") {
          let newImgs = that.data.goodImgs.concat(res.tempFilePaths)
          that.setData({ goodImgs: newImgs })
        }
      },
      fail: function (err) {
        console.error('选择图片失败:', err)
        wx.showToast({
          title: '选择图片失败',
          icon: 'none'
        })
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

  // 设定商品成色
  changeCondition: function (e) {
    this.setData({ conditionIndex: e.detail.value })
  },

  // 设定功能状态
  changeFunctionStatus: function (e) {
    this.setData({ functionStatusIndex: e.detail.value })
  },

  // 设定购买时间
  changeBuyTime: function (e) {
    this.setData({ buyTime: e.detail.value })
  },

  // 描述输入监听
  onDescriptionInput: function (e) {
    this.setData({
      descriptionLength: e.detail.value.length
    })
  },

  // 删除图片
  removeImage: function (e) {
    const index = e.currentTarget.dataset.index
    let goodImgs = this.data.goodImgs
    goodImgs.splice(index, 1)
    this.setData({ goodImgs: goodImgs })
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
