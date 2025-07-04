// pages/ai_chat/ai_chat.js
const utils = require('../../utils/util.js')

Page({
  /**
   * 页面的初始数据
   */
  data: {
    appInstance: {},
    msgList: [],
    timeList: [],
    tempMsg: '',
    quickQuestions: [
      '如何发布二手物品?',
      '如何联系卖家?',
      '如何修改个人信息?',
      '如何查看我的订单?'
    ],
    loading: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let app = getApp()
    this.setData({ appInstance: app })
    
    // 设置导航栏标题
    wx.setNavigationBarTitle({ title: 'AI客服' })

    // 加载聊天记录
    this.loadChatHistory()
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    // 如果没有聊天记录，显示欢迎消息
    if (this.data.msgList.length === 0) {
      const welcomeMsg = {
        content: '您好！我是二货来了的智能客服助手——小二，有什么可以帮您的吗？',
        time: new Date().getTime(),
        isAI: true
      }
      this.setData({
        msgList: [welcomeMsg],
        timeList: [utils.formatTime(new Date()).slice(0, -3)]
      })
    }
  },

  /**
   * 加载聊天历史记录
   */
  loadChatHistory: function () {
    const that = this
    const userId = this.data.appInstance.globalData.userInfo.userId
    
    wx.request({
      url: this.data.appInstance.globalData.host + 'AIChat?type=history&userId=' + userId,
      dataType: 'json',
      success(res) {
        if (res.data && res.data.length > 0) {
          that.setData({ 
            msgList: res.data
          })
          that.setTimeList()
        }
      }
    })
  },

  /**
   * 发送消息
   */
  sendMsg: function (data) {
    if (this.data.loading) return
    
    const msg = data.detail.value.msg
    if (!msg || msg.trim() === '') return
    
    this.setData({ loading: true })
    
    // 添加用户消息到列表
    const userMsg = {
      content: msg,
      time: new Date().getTime(),
      isAI: false
    }
    
    const msgList = this.data.msgList.concat(userMsg)
    this.setData({ 
      msgList: msgList,
      tempMsg: ''
    })
    this.setTimeList()
    
    // 请求AI回答
    this.requestAIResponse(msg)
  },

  /**
   * 请求AI回答
   */
  requestAIResponse: function (question) {
    const that = this
    const userId = this.data.appInstance.globalData.userInfo.userId
    
    wx.request({
      url: this.data.appInstance.globalData.ai_chat_api + '/api/v1/chat',
      method: 'POST',
      data: {
        question: question
      },
      success(res) {
        if (res.data && res.data.answer) {
          // 添加AI回答到列表
          const aiMsg = {
            content: res.data.answer,
            time: new Date().getTime(),
            isAI: true
          }
          
          const msgList = that.data.msgList.concat(aiMsg)
          that.setData({ 
            msgList: msgList,
            loading: false
          })
          that.setTimeList()
        } else {
          that.handleAIError()
        }
      },
      fail() {
        that.handleAIError()
      }
    })
  },

  /**
   * 处理AI回答错误
   */
  handleAIError: function () {
    const errorMsg = {
      content: '抱歉，我现在出了一点状况，原谅我稍后再回复您。',
      time: new Date().getTime(),
      isAI: true
    }
    
    const msgList = this.data.msgList.concat(errorMsg)
    this.setData({ 
      msgList: msgList,
      loading: false
    })
    this.setTimeList()
  },

  /**
   * 更新时间列表
   */
  setTimeList: function () {
    const msgList = this.data.msgList
    const timeList = []
    
    for (let i = 0; i < msgList.length; i++) {
      timeList.push(utils.formatTime(new Date(msgList[i].time)).slice(0, -3))
    }
    
    this.setData({ timeList: timeList })
  },

  /**
   * 点击快速问题
   */
  tapQuickQuestion: function (e) {
    const question = e.currentTarget.dataset.question
    this.setData({
      tempMsg: question
    })
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    this.loadChatHistory()
    wx.stopPullDownRefresh()
  }
}) 