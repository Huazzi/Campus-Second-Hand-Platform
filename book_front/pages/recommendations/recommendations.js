// pages/recommendations/recommendations.js
const app = getApp();
const baseUrl = app.globalData.baseUrl;

Page({
  /**
   * 页面的初始数据
   */
  data: {
    goods: [],       // 商品列表
    loading: true,   // 是否正在加载
    noMore: false,   // 是否没有更多数据
    page: 1,         // 当前页码
    pageSize: 10,    // 每页显示数量
    activeTab: 'content', // 当前激活的标签: 'content', 'popular', 'new'
    userId: null     // 用户ID
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // 检查用户登录状态
    if (app.globalData.userInfo && app.globalData.userInfo.userId) {
      this.setData({
        userId: app.globalData.userInfo.userId
      });
    }
    
    // 加载推荐商品
    this.loadRecommendations();
  },

  /**
   * 切换标签
   */
  switchTab: function(e) {
    const tab = e.currentTarget.dataset.tab;
    
    if (tab !== this.data.activeTab) {
      this.setData({
        activeTab: tab,
        goods: [],
        page: 1,
        noMore: false
      });
      
      this.loadRecommendations();
    }
  },

  /**
   * 加载推荐商品
   */
  loadRecommendations: function() {
    this.setData({ loading: true });
    
    let url = '';
    const { activeTab, page, pageSize, userId } = this.data;
    
    switch (activeTab) {
      case 'content':
        // 基于内容的推荐需要用户ID
        if (!userId) {
          wx.showToast({
            title: '请先登录',
            icon: 'none'
          });
          this.setData({ 
            loading: false,
            goods: []
          });
          return;
        }
        url = `${baseUrl}/api/recommendations/content-based?userId=${userId}&limit=${pageSize}&offset=${(page - 1) * pageSize}`;
        break;
      case 'popular':
        url = `${baseUrl}/api/recommendations/popular?limit=${pageSize}&offset=${(page - 1) * pageSize}`;
        // 如果用户已登录，传递用户ID以记录推荐日志
        if (userId) {
          url += `&userId=${userId}`;
        }
        break;
      case 'new':
        url = `${baseUrl}/api/recommendations/new-arrivals?days=7&limit=${pageSize}&offset=${(page - 1) * pageSize}`;
        // 如果用户已登录，传递用户ID以记录推荐日志
        if (userId) {
          url += `&userId=${userId}`;
        }
        break;
    }
    
    wx.request({
      url: url,
      method: 'GET',
      success: (res) => {
        if (res.statusCode === 200 && res.data) {
          // 处理商品数据
          const newGoods = res.data;
          
          // 处理图片路径
          newGoods.forEach(item => {
            if (item.images) {
              // 将图片路径字符串转换为数组
              item.images = item.images.split(';').filter(img => img !== '');
              
              // 为图片添加完整URL前缀（如果需要）
              if (item.images.length > 0) {
                item.images = item.images.map(img => {
                  if (!img.startsWith('http')) {
                    return baseUrl + '/img/goods/' + img;
                  }
                  return img;
                });
              } else {
                // 如果没有图片，添加默认图片
                item.images = [baseUrl + '/img/goods/default_cover.png'];
              }
            } else {
              item.images = [baseUrl + '/img/goods/default_cover.png'];
            }
          });
          
          // 合并商品数据
          const goods = page === 1 ? newGoods : this.data.goods.concat(newGoods);
          
          this.setData({
            goods: goods,
            noMore: newGoods.length < pageSize,
            loading: false
          });
          
          // 如果有用户点击了商品，记录点击事件
          this.handleRecommendationClick = (goodId) => {
            if (this.data.userId) {
              this.recordRecommendationClick(goodId);
            }
          };
        } else {
          wx.showToast({
            title: '加载失败，请重试',
            icon: 'none'
          });
          this.setData({ loading: false });
        }
      },
      fail: () => {
        wx.showToast({
          title: '网络错误，请重试',
          icon: 'none'
        });
        this.setData({ loading: false });
      }
    });
  },
  
  /**
   * 加载更多
   */
  loadMore: function() {
    if (!this.data.loading && !this.data.noMore) {
      this.setData({
        page: this.data.page + 1
      });
      this.loadRecommendations();
    }
  },
  
  /**
   * 记录商品浏览行为
   */
  recordUserView: function(goodId) {
    if (!this.data.userId) {
      return;
    }
    
    wx.request({
      url: `${baseUrl}/api/recommendations/view`,
      method: 'POST',
      data: {
        userId: this.data.userId,
        goodId: goodId,
        duration: 0 // 初始化为0，可以在商品详情页面计算实际浏览时长
      },
      success: () => {
        console.log('浏览记录已保存');
      }
    });
  },
  
  /**
   * 记录推荐点击事件
   */
  recordRecommendationClick: function(goodId) {
    wx.request({
      url: `${baseUrl}/api/recommendations/click`,
      method: 'POST',
      data: {
        userId: this.data.userId,
        goodId: goodId,
        type: this.data.activeTab
      },
      success: () => {
        console.log('推荐点击已记录');
      }
    });
  },
  
  /**
   * 跳转到商品详情页
   */
  goToDetail: function(e) {
    const goodId = e.currentTarget.dataset.id;
    
    // 记录用户点击推荐商品的事件
    if (this.data.userId) {
      this.recordRecommendationClick(goodId);
    }
    
    // 跳转到商品详情页
    wx.navigateTo({
      url: `/pages/good/good?id=${goodId}`
    });
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    // 检查用户登录状态是否已更改
    if (app.globalData.userInfo && app.globalData.userInfo.userId !== this.data.userId) {
      this.setData({
        userId: app.globalData.userInfo.userId,
        page: 1,
        goods: []
      });
      this.loadRecommendations();
    }
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    this.setData({
      page: 1,
      goods: [],
      noMore: false
    });
    this.loadRecommendations();
    wx.stopPullDownRefresh();
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    this.loadMore();
  }
})