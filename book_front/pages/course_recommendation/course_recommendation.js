// pages/course_recommendation/course_recommendation.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    semesters: ['第一学期', '第二学期'],
    currentSemesterIndex: 0,
    userMajor: '',
    recommendedBooks: [],
    isLoading: true,
    errorMsg: '',
    isCourseGroupDisplay: false, // 是否按课程分组显示
    courseGroups: [] // 按课程分组的教材
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (options.semester) {
      const semesterIndex = parseInt(options.semester);
      if (semesterIndex === 0 || semesterIndex === 1) {
        this.setData({
          currentSemesterIndex: semesterIndex
        });
      }
    }
    
    this.loadUserInfo();
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.loadRecommendations();
  },

  /**
   * 加载用户信息
   */
  loadUserInfo: function() {
    const appInstance = getApp();
    if (appInstance.globalData.userInfo) {
      const userInfo = appInstance.globalData.userInfo;
      // 假设用户信息中有major字段，实际情况可能需要修改
      this.setData({
        userMajor: userInfo.major || ''
      });
    }
  },

  /**
   * 加载推荐教材
   */
  loadRecommendations: function() {
    this.setData({
      isLoading: true,
      errorMsg: '',
      recommendedBooks: [],
      courseGroups: []
    });
    
    const appInstance = getApp();
    // 默认图片设置为一个已存在的图片
    const defaultBookCover = '../../images/default_cover.png';
    
    console.log("开始加载教材推荐，学期:", this.data.currentSemesterIndex);
    
    wx.request({
      url: appInstance.globalData.host + 'CourseRecommend',
      data: {
        action: 'getRecommendBySemester',
        semester: this.data.currentSemesterIndex,
        major: this.data.userMajor
      },
      success: (res) => {
        console.log("服务器返回推荐数据:", res.data);
        
        if (res.data && res.data.status === 'success') {
          // 处理返回数据
          if (res.data.recommendedTextbooks && res.data.recommendedTextbooks.length > 0) {
            // 处理图片路径
            const processedTextbooks = res.data.recommendedTextbooks.map(book => {
              // 处理ID兼容问题
              if (!book.bookId && book.id) {
                book.bookId = book.id;
              } else if (!book.bookId && !book.id && book.goodId) {
                book.bookId = book.goodId;
              } else if (!book.bookId && !book.id && !book.goodId) {
                // 如果所有ID都不存在，生成一个临时ID
                book.bookId = "temp_" + new Date().getTime() + "_" + Math.floor(Math.random() * 1000);
              }
              
              // 确保每本书都有商品名称
              if (!book.goodname && book.goodName) {
                book.goodname = book.goodName;
              } else if (!book.goodname && !book.goodName) {
                book.goodname = book.textbookName || "未知教材";
              }
              
              let imgArr = [];
              if (book.image) {
                imgArr = [book.image];
              } else if (book.images && typeof book.images === 'string') {
                // 处理字符串格式的images
                imgArr = book.images.split(";");
                // 移除末尾可能的空字符串
                if (imgArr.length > 0 && imgArr[imgArr.length - 1] === "") {
                  imgArr.pop();
                }
              } else if (Array.isArray(book.images) && book.images.length > 0 && book.images[0]) {
                imgArr = book.images;
              } else if (book.coverImage) {
                imgArr = [book.coverImage];
              } else if (book.picurl) {
                imgArr = [book.picurl];
              } else {
                imgArr = [defaultBookCover];
              }
              
              // 处理图片路径 - 增强版
              const firstImg = imgArr[0] || '';
              if (!firstImg) {
                // 空路径使用默认图片
                imgArr = [defaultBookCover];
              } else if (/^\/course_recommendation\//.test(firstImg) || 
                       firstImg.indexOf('undefined') !== -1 ||
                       firstImg === '/' ||
                       firstImg.startsWith('//') ||
                       !firstImg.match(/\.(png|jpg|jpeg|gif|svg|webp)$/i)) {
                // 无效路径使用默认图片
                imgArr = [defaultBookCover];
              } else {
                // 有效路径，检查是否需要添加服务器域名前缀
                const formattedImgUrl = this.formatImageUrl(firstImg, appInstance.globalData.host);
                imgArr = [formattedImgUrl];
              }
              
              return {
                ...book,
                images: imgArr
              };
            });
            
            console.log("处理后的教材数据:", processedTextbooks);
            
            this.setData({
              recommendedBooks: processedTextbooks
            });
            
            // 按课程分组数据
            this.groupBooksByCourse(processedTextbooks);
          } else {
            this.setData({
              errorMsg: '暂无该学期推荐教材'
            });
          }
        } else {
          this.setData({
            errorMsg: res.data?.message || '获取推荐失败'
          });
        }
      },
      fail: (err) => {
        this.setData({
          errorMsg: '网络请求失败'
        });
        console.error('教材推荐请求失败:', err);
      },
      complete: () => {
        this.setData({
          isLoading: false
        });
      }
    });
  },

  /**
   * 格式化图片URL，确保包含完整的服务器域名
   */
  formatImageUrl: function(imageUrl, serverHost) {
    // 如果已经是完整URL（以http或https开头），直接返回
    if (/^https?:\/\//.test(imageUrl)) {
      return imageUrl;
    }
    
    // 处理相对路径
    if (imageUrl.startsWith('./') || imageUrl.startsWith('../')) {
      return '../../images/default_cover.png'; // 前端相对路径难以解析，使用默认图片
    }
    
    // 提取服务器域名和端口
    let baseHost = serverHost;
    // 移除末尾的斜杠（如果有）
    if (baseHost.endsWith('/')) {
      baseHost = baseHost.slice(0, -1);
    }
    
    // 如果是以/img/goods/开头的标准路径
    if (imageUrl.startsWith('/img/goods/')) {
      return baseHost + '/img/goods/' + imageUrl.split('/').pop();
    }
    
    // 如果是以img/goods/开头（没有前导斜杠）
    if (imageUrl.startsWith('img/goods/')) {
      return baseHost + '/img/goods/' + imageUrl.split('/').pop();
    }
    
    // 处理文件名直接作为路径的情况（服务器可能只返回文件名）
    // 增强：支持长字符串格式的文件名，如：8Rvv36mE0Z9Gb979bee03fe8ca44b33291290efc5c2c.png
    if (/^[A-Za-z0-9_\-]{10,}\.(png|jpg|jpeg|gif|webp)$/i.test(imageUrl)) {
      return baseHost + '/img/goods/' + imageUrl;
    }
    
    // 其他常规格式的文件名
    if (/^[A-Za-z0-9_\-]+\.(png|jpg|jpeg|gif|webp)$/i.test(imageUrl)) {
      return baseHost + '/img/goods/' + imageUrl;
    }
    
    // 如果以/开头但不是/img/goods/格式，可能是其他格式的路径
    if (imageUrl.startsWith('/')) {
      return baseHost + '/img/goods/' + imageUrl.split('/').pop(); // 提取文件名
    }
    
    // 其他情况，尝试作为相对于服务器的路径处理
    return baseHost + '/img/goods/' + imageUrl;
  },

  /**
   * 按课程分组
   */
  groupBooksByCourse: function(books) {
    const groups = {};
    
    books.forEach(book => {
      if (book.courseName) {
        if (!groups[book.courseName]) {
          groups[book.courseName] = {
            courseName: book.courseName,
            books: []
          };
        }
        groups[book.courseName].books.push(book);
      }
    });
    
    const courseGroupsArray = Object.values(groups);
    this.setData({
      courseGroups: courseGroupsArray
    });
  },

  /**
   * 切换显示模式
   */
  toggleDisplayMode: function() {
    this.setData({
      isCourseGroupDisplay: !this.data.isCourseGroupDisplay
    });
  },

  /**
   * 更改学期
   */
  changeSemester: function(e) {
    this.setData({
      currentSemesterIndex: parseInt(e.detail.value)
    }, () => {
      this.loadRecommendations();
    });
  },

  /**
   * 查看书籍详情
   */
  viewBookDetail: function(e) {
    const bookId = e.currentTarget.dataset.id;
    
    // 检查ID是否有效
    if (!bookId || bookId === 'undefined' || bookId.startsWith('temp_')) {
      wx.showToast({
        title: '该教材信息不完整',
        icon: 'none',
        duration: 2000
      });
      return;
    }
    
    console.log("跳转到商品详情，商品ID:", bookId);
    
    wx.navigateTo({
      url: '/pages/good/good?goodId=' + bookId
    });
  },

  /**
   * 上传课程表
   */
  navigateToCourseSchedule: function() {
    wx.navigateTo({
      url: '/pages/course_schedule/course_schedule'
    });
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
    return {
      title: this.data.semesters[this.data.currentSemesterIndex] + '教材推荐',
      path: `/pages/course_recommendation/course_recommendation?semester=${this.data.currentSemesterIndex}`
    };
  }
}) 