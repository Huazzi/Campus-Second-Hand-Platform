Page({
  /**
   * 页面的初始数据
   */
  data: {
    semesters: ['第一学期', '第二学期'],
    currentSemesterIndex: 0,
    currentWeek: 1,
    weekdays: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
    timeSlots: [
      '8:00-8:45', '8:55-9:40', '10:10-10:55', '11:05-11:50',
      '14:00-14:45', '14:55-15:40', '16:00-16:45', '16:55-17:40',
      '19:00-19:45', '19:55-20:40'
    ],
    courses: [],
    displayedCourses: [], // 新增：用于优化课程表显示的课程数组
    unitHeight: 120, // 新增：每节课在视图上的高度，单位px，请根据WXML/CSS调整
    showCourseDetail: false,
    selectedCourse: null,
    showAddCourseForm: false,
    isEdit: false,
    editingCourse: null,
    courseColors: [
      '#FF9966', '#99CCCC', '#CC9999', '#99CC99', 
      '#9999CC', '#CCCC99', '#CC99CC', '#99CCFF'
    ],
    weeks: Array.from({length: 16}, (_, i) => `第${i + 1}周`), // 添加周数数组
    // 新增：智能推荐相关
    smartRecommendedBooks: [],
    isLoadingRecommendations: false,
    uniqueTextbookNamesCount: 0,
    currentSemesterCourses: [], // 添加：当前学期的课程
    hasUpcomingSemesterReminder: false, // 添加：是否有即将开始的学期提醒
    upcomingSemesterDate: '', // 添加：下一学期的开始时间
    userMajor: '', // 添加：用户专业
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.loadCourses();
    // 获取当前学期和周次
    const now = new Date();
    const month = now.getMonth() + 1;
    // 根据月份判断学期
    let semesterIndex = 0;
    if (month >= 3 && month <= 7) {
      semesterIndex = 1; // 第二学期
    } else if (month >= 8 || month <= 2) {
      semesterIndex = 0; // 第一学期
    }
    
    // 计算当前教学周
    // 这里使用简单算法，实际使用时应该根据校历计算
    let currentWeek = 1;
    if (month >= 9 || month <= 2) {
      // 第一学期，9月初始第1周
      if (month >= 9) {
        currentWeek = Math.floor(((now - new Date(now.getFullYear(), 8, 1)) / (7 * 24 * 60 * 60 * 1000))) + 1;
      } else {
        currentWeek = Math.floor(((now - new Date(now.getFullYear() - 1, 8, 1)) / (7 * 24 * 60 * 60 * 1000))) + 1;
      }
    } else {
      // 第二学期，3月初始第1周
      currentWeek = Math.floor(((now - new Date(now.getFullYear(), 2, 1)) / (7 * 24 * 60 * 60 * 1000))) + 1;
    }
    
    // 确保周次在合理范围内
    currentWeek = Math.min(Math.max(currentWeek, 1), 20);
    
    this.setData({
      currentSemesterIndex: semesterIndex,
      currentWeek: currentWeek
    });
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
    this.loadCourses();
    this.checkForSemesterReminders(); // 添加：检查学期提醒
    this.loadUserInfo(); // 添加：加载用户信息
  },

  /**
   * 加载用户信息，获取用户专业
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
   * 检查学期提醒
   */
  checkForSemesterReminders: function() {
    const now = new Date();
    const month = now.getMonth() + 1;
    const day = now.getDate();
    
    // 假设第一学期从9月开始，第二学期从3月开始
    // 在学期开始前一个月提醒
    let hasReminder = false;
    let reminderDate = '';
    
    if ((month === 8 && this.data.currentSemesterIndex === 1) || 
        (month === 2 && this.data.currentSemesterIndex === 0)) {
      hasReminder = true;
      
      if (month === 8) {
        reminderDate = `${now.getFullYear()}年9月1日`;
      } else {
        reminderDate = `${now.getFullYear()}年3月1日`;
      }
    }
    
    this.setData({
      hasUpcomingSemesterReminder: hasReminder,
      upcomingSemesterDate: reminderDate
    });
    
    if (hasReminder) {
      // 显示学期提醒
      wx.showModal({
        title: '学期提醒',
        content: `新学期即将于${reminderDate}开始，请提前准备所需教材。是否查看教材推荐？`,
        confirmText: '查看推荐',
        success: (res) => {
          if (res.confirm) {
            this.loadServerRecommendations();
          }
        }
      });
    }
  },

  /**
   * 更新用于优化显示的课程列表
   */
  updateDisplayedCourses: function() {
    const filtered = this.data.courses.filter(course => {
      if (course.semester !== this.data.currentSemesterIndex) return false;
      return this.isInWeeks(course.weeks, this.data.currentWeek);
    });

    const unitWidth = (100 / this.data.weekdays.length); // 每天宽度百分比

    const displayedCourses = filtered.map(course => {
      return {
        ...course,
        // styleTop, styleLeft, styleHeight, styleWidth 用于WXML中绝对定位
        styleTop: (course.startSection - 1) * this.data.unitHeight,
        styleLeft: (course.day - 1) * unitWidth, // course.day 是 1-7
        styleHeight: course.duration * this.data.unitHeight,
        styleWidth: unitWidth,
      };
    });

    this.setData({
      displayedCourses: displayedCourses
    });
  },

  /**
   * 选择学期
   */
  changeSemester: function(e) {
    this.setData({
      currentSemesterIndex: parseInt(e.detail.value) // 确保是数字
    }, () => {
      this.loadCourses(); // loadCourses 会调用 updateDisplayedCourses
      this.loadRecommendedBooks();
    });
  },

  /**
   * 选择周次
   */
  selectWeek: function(e) {
    const week = e.currentTarget.dataset.week;
    this.setData({
      currentWeek: week
    });
  },

  /**
   * 添加周数选择器的事件处理函数
   */
  changeWeek(e) {
    this.setData({
      currentWeek: parseInt(e.detail.value) + 1
    }, () => {
      this.updateDisplayedCourses(); // 直接更新显示，因为课程数据本身未变
    });
  },

  /**
   * 获取指定位置的课程 (此函数现在主要用于逻辑判断，而非直接渲染定位)
   */
  getCoursesByPosition: function(row, col) {
    const filteredCourses = this.data.courses.filter(course => {
      // 过滤当前学期的课程
      if (course.semester !== this.data.currentSemesterIndex) return false;
      
      // 过滤当前星期的课程
      // course.day 是 1-7 (周一到周日), col 应该是 1-7
      if (course.day !== col) return false; 
      
      // 过滤当前时间段的课程
      // row 是 1-12 (对应 timeSlots 的索引+1)
      if (course.startSection > row || (course.startSection + course.duration - 1) < row) return false;
      
      // 过滤当前周次的课程
      return this.isInWeeks(course.weeks, this.data.currentWeek);
    });
    
    // 不再计算相对top值，渲染将由 displayedCourses 控制
    return filteredCourses;
  },

  /**
   * 判断当前周次是否在课程的周次范围内
   */
  isInWeeks: function(weeksStr, currentWeek) {
    if (!weeksStr) return false;
    
    // 解析周次字符串，如 "1-16" 或 "1,3,5-10"
    const weekRanges = weeksStr.split(',');
    for (let range of weekRanges) {
      if (range.includes('-')) {
        const [start, end] = range.split('-').map(Number);
        if (currentWeek >= start && currentWeek <= end) {
          return true;
        }
      } else {
        if (parseInt(range) === currentWeek) {
          return true;
        }
      }
    }
    return false;
  },

  /**
   * 显示课程详情
   */
  showCourseDetail: function(e) {
    const course = e.currentTarget.dataset.course;
    this.setData({
      showCourseDetail: true,
      selectedCourse: course
    });
  },

  /**
   * 关闭课程详情
   */
  closeCourseDetail: function() {
    this.setData({
      showCourseDetail: false,
      selectedCourse: null
    });
  },

  /**
   * 编辑课程
   */
  editCourse: function() {
    this.setData({
      showCourseDetail: false,
      showAddCourseForm: true,
      isEdit: true,
      editingCourse: JSON.parse(JSON.stringify(this.data.selectedCourse)) // 深拷贝以避免直接修改selectedCourse
    });
  },

  /**
   * 删除课程
   */
  deleteCourse: function() {
    const that = this;
    wx.showModal({
      title: '提示',
      content: '确定要删除该课程吗？',
      success(res) {
        if (res.confirm) {
          const courseId = that.data.selectedCourse.id;
          const newCourses = that.data.courses.filter(course => course.id !== courseId);
          that.setData({
            courses: newCourses,
            showCourseDetail: false
          }, () => {
            that.updateDisplayedCourses(); // 更新显示
          });
          that.saveCourses();
          wx.showToast({
            title: '删除成功',
            icon: 'success'
          });
        }
      }
    });
  },

  /**
   * 添加课程
   */
  addCourse: function() {
    // 随机颜色
    const randomColor = this.data.courseColors[Math.floor(Math.random() * this.data.courseColors.length)];
    
    this.setData({
      showAddCourseForm: true,
      isEdit: false,
      editingCourse: { // 初始化新课程
        id: '', // id 在提交时生成
        semester: this.data.currentSemesterIndex,
        day: 1, // 默认周一
        startSection: 1, // 默认第一节
        duration: 1, // 默认一节课
        color: randomColor,
        name: '',
        teacher: '',
        location: '',
        weeks: '', // 默认为空，提示用户填写
        textbook: ''
      }
    });
  },

  /**
   * 关闭添加/编辑课程表单
   */
  closeAddCourseForm: function() {
    this.setData({
      showAddCourseForm: false
    });
  },

  /**
   * 选择星期
   */
  dayChange: function(e) {
    this.setData({
      'editingCourse.day': parseInt(e.detail.value) + 1 // 索引从0开始，实际值从1开始
    });
  },

  /**
   * 选择开始节次
   */
  startSectionChange: function(e) {
    this.setData({
      'editingCourse.startSection': parseInt(e.detail.value) + 1 // 索引从0开始，实际值从1开始
    });
  },

  /**
   * 选择持续节数
   */
  durationChange: function(e) {
    this.setData({
      'editingCourse.duration': parseInt(e.detail.value) + 1 // 索引从0开始，实际值从1开始
    });
  },

  /**
   * 查看教材详情 (保留一个实现即可)
   */
  viewTextbook: function() {
    if (this.data.selectedCourse && this.data.selectedCourse.textbook) {
      this.setData({
        showCourseDetail: false, // 关闭课程详情弹窗
        showTextbookInfo: true,
        selectedTextbook: this.data.selectedCourse.textbook
      });
      
      // 根据教材名称搜索相关二手教材
      this.searchRelatedBooks(this.data.selectedCourse.textbook);
    } else {
      wx.showToast({
        title: '未设置教材信息',
        icon: 'none'
      });
    }
  },

  /**
   * 关闭教材详情
   */
  closeTextbookInfo: function() {
    this.setData({
      showTextbookInfo: false,
      selectedTextbook: null
    });
  },

  /**
   * 搜索相关教材
   */
  searchRelatedBooks: function(bookName) {
    const appInstance = getApp();
    const that = this;
    
    wx.showLoading({
      title: '搜索相关教材中...',
    });
    
    // 调用搜索API
    wx.request({
      url: appInstance.globalData.host + 'Good',
      data: {
        loadBy: "condition",
        key: bookName,
        catagory: "图书音像",
        start: (1 - 1) * 3,
        end: 3
      },
      success(res) {
        wx.hideLoading();
        if (res.data && res.data.bookList) {
          that.setData({
            recommendedBooks: res.data.bookList
          });
        } else {
          that.setData({
            recommendedBooks: []
          });
          wx.showToast({
            title: '未找到相关教材',
            icon: 'none'
          });
        }
      },
      fail() {
        wx.hideLoading();
        wx.showToast({
          title: '搜索失败',
          icon: 'none'
        });
      }
    });
  },

  /**
   * 查看教材详情
   */
  viewBookDetail: function(e) {
    const bookId = e.currentTarget.dataset.id;
    if (!bookId) {
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
   * 提交课程表单
   */
  submitCourseForm: function(e) {
    const formData = e.detail.value;
    
    // 表单验证
    if (!formData.name || !formData.location || !formData.weeks) {
      wx.showToast({
        title: '课程名称、地点、周次为必填项',
        icon: 'none'
      });
      return;
    }
    // 可以在此添加更复杂的周次格式校验，如 this.isValidWeeks(formData.weeks)
    
    // 构建课程对象
    // editingCourse 已通过 dayChange, startSectionChange, durationChange 更新
    const courseData = {
      ...this.data.editingCourse, // 包含 id (如果是编辑), day, startSection, duration, color, semester
      name: formData.name,
      teacher: formData.teacher,
      location: formData.location,
      weeks: formData.weeks,
      textbook: formData.textbook
    };
    
    let courses = [...this.data.courses];
    
    if (this.data.isEdit) {
      // 编辑现有课程
      const index = courses.findIndex(c => c.id === courseData.id);
      if (index !== -1) {
        courses[index] = courseData;
      } else {
        // 如果正在编辑的课程ID未找到（理论上不应发生），则按新课程处理，或报错
        console.error('Editing course not found with id:', courseData.id);
        // 为避免数据丢失，可以考虑作为新课程添加，但最好有错误提示
        courseData.id = Date.now().toString(); // 重新生成ID
        courses.push(courseData);
      }
    } else {
      // 添加新课程
      courseData.id = Date.now().toString(); // 确保ID是唯一的
      courses.push(courseData);
    }
    
    this.setData({
      courses: courses,
      showAddCourseForm: false
    }, () => {
      this.updateDisplayedCourses(); // 更新显示
    });
    
    // 保存到本地存储
    this.saveCourses();
    
    wx.showToast({
      title: this.data.isEdit ? '修改成功' : '添加成功',
      icon: 'success'
    });
  },

  /**
   * 修改导入课程功能
   */
  importCourse: function() {
    wx.showActionSheet({
      itemList: ['表格文件导入', '教务系统导入'],
      success(res) {
        let message = '该功能正在开发中,敬请期待!';
        if(res.tapIndex === 0) {
          message = '表格文件导入功能将在后续版本推出,敬请期待!';
        } else if(res.tapIndex === 1) {
          message = '教务系统导入功能将在后续版本推出,敬请期待!';
        }
        wx.showToast({
          title: message,
          icon: 'none',
          duration: 2000
        });
      }
    });
  },

  /**
   * 导出课程表
   */
  exportCourse: function() {
    wx.showModal({
      title: '提示',
      content: '课程表导出功能即将上线，敬请期待！',
      showCancel: false
    });
  },

  /**
   * 保存课程数据到本地存储
   */
  saveCourses: function() {
    const appInstance = getApp();
    const userId = appInstance.globalData.userInfo ? appInstance.globalData.userInfo.userId : '';
    
    if (!userId) {
      wx.showToast({
        title: '请先登录',
        icon: 'none'
      });
      return;
    }
    
    wx.setStorage({
      key: `courses_${userId}_${this.data.currentSemesterIndex}`,
      data: this.data.courses,
      success: function() {
        console.log('课程数据保存成功');
      },
      fail: function(err) {
        console.error('保存课程数据失败：', err);
        wx.showToast({
          title: '保存失败',
          icon: 'none'
        });
      }
    });
  },

  /**
   * 从本地存储加载课程数据
   */
  loadCourses: function() {
    const that = this;
    const appInstance = getApp();
    const userId = appInstance.globalData.userInfo ? appInstance.globalData.userInfo.userId : '';
    
    if (!userId) {
      console.error('用户未登录');
      return;
    }
    
    wx.getStorage({
      key: `courses_${userId}_${that.data.currentSemesterIndex}`,
      success: function(res) {
        that.setData({
          courses: res.data || []
        }, () => { // setData 的回调中更新显示
          that.updateDisplayedCourses();
          that.loadSmartTextbookRecommendations(); // 加载智能推荐
        });
      },
      fail: function(err) { // fail 回调接收错误对象
        console.warn(`加载课程数据 courses_${userId}_${that.data.currentSemesterIndex} 失败:`, err);
        that.setData({
          courses: []
        }, () => {
          that.updateDisplayedCourses();
          that.loadSmartTextbookRecommendations(); // 即使课程为空，也尝试（此时会因uniqueTextbookNames为空而直接返回）
          // 只有在明确知道是首次加载（例如，通过检查 err 类型或特定标志）
          // 且 courses 为空时才适合创建初始存储。
          // 为避免不必要的写入，可以检查storage中key是否存在，但wx.getStorage的fail本身就暗示了可能不存在或读取错误
          // that.saveCourses(); // 这一行可以考虑是否真的每次fail都需要
        });
      }
    });
  },

  /**
   * 加载推荐教材
   */
  loadRecommendedBooks: function() {
    const that = this;
    const appInstance = getApp();
    
    // 获取当前学期的所有课程的教材名称
    const textbooks = that.data.courses
      .filter(course => course.semester === that.data.currentSemesterIndex && course.textbook)
      .map(course => course.textbook);
    
    if (textbooks.length === 0) {
      return;
    }
    
    // 这里可以调用后端API获取推荐教材
    // 目前先使用模拟数据
    const recommendedBooks = [];
    
    that.setData({
      recommendedBooks: recommendedBooks
    });
  },

  /**
   * 查看教材推荐
   */
  viewRecommendations: function() {
    wx.navigateTo({
      url: '/pages/search_result/search_result?query=推荐教材&type=textbook'
    });
  },

  /**
   * 加载基于当前学期课程的智能推荐教材
   */
  loadSmartTextbookRecommendations: function() {
    this.setData({ isLoadingRecommendations: true, smartRecommendedBooks: [], uniqueTextbookNamesCount: 0 });
    const appInstance = getApp();
    const currentSemesterCourses = this.data.courses.filter(
      course => course.semester === this.data.currentSemesterIndex && course.textbook && course.textbook.trim() !== ""
    );
    
    this.setData({ currentSemesterCourses: currentSemesterCourses });
    
    const uniqueTextbookNames = [...new Set(currentSemesterCourses.map(course => course.textbook.trim()))];
    
    this.setData({ uniqueTextbookNamesCount: uniqueTextbookNames.length });

    if (uniqueTextbookNames.length === 0) {
      this.setData({ isLoadingRecommendations: false });
      console.log("No textbooks specified for current semester courses to make smart recommendations.");
      return;
    }

    // 如果有课程教材，首先尝试调用服务器的智能推荐API
    this.loadServerRecommendations();
  },
  
  /**
   * 调用服务器的智能推荐API获取课程教材推荐
   */
  loadServerRecommendations: function() {
    const appInstance = getApp();
    const courseNames = this.data.currentSemesterCourses.map(course => course.name);
    
    // 默认图片设置为一个已存在的图片
    const defaultBookCover = '../../images/default_cover.png';
    
    if (courseNames.length === 0) {
      this.loadClientRecommendations(); // 回退到客户端搜索
      return;
    }
    
    console.log("向服务器请求课程推荐，课程列表:", courseNames);
    
    // 调用服务器API
    wx.request({
      url: appInstance.globalData.host + 'CourseRecommend',
      data: {
        action: 'getRecommendByCourseList',
        courseList: JSON.stringify(courseNames),
        semester: this.data.currentSemesterIndex,
        major: this.data.userMajor
      },
      success: (res) => {
        console.log("服务器推荐返回数据:", res.data);
        
        if (res.data && res.data.status === 'success' && res.data.allRecommendations && res.data.allRecommendations.length > 0) {
          // 过滤掉没有有效ID的项目，但增加ID字段兼容处理
          const validRecommendations = res.data.allRecommendations.map(book => {
            // 处理id和bookId不一致的问题，确保bookId存在
            if (!book.bookId && book.id) {
              book.bookId = book.id;
            } else if (!book.bookId && !book.id && book.goodId) {
              book.bookId = book.goodId;
            } else if (!book.bookId && !book.id && !book.goodId) {
              // 如果所有ID都不存在，生成一个临时ID
              book.bookId = "temp_" + new Date().getTime() + "_" + Math.floor(Math.random() * 1000);
            }
            return book;
          }).filter(book => book && book.bookId);
          
          if (validRecommendations.length === 0) {
            console.log("服务器返回的推荐中没有有效的bookId，回退到客户端搜索");
            this.loadClientRecommendations();
            return;
          }
          
          // 处理服务器返回结果中的图片字段，确保图片正确显示
          const processedRecommendations = validRecommendations.map(book => {
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
            
            // 确保每本书都有商品名称
            if (!book.goodname && book.goodName) {
              book.goodname = book.goodName;
            } else if (!book.goodname && !book.goodName) {
              book.goodname = book.textbookName || "未知教材";
            }
            
            return {
              ...book,
              images: imgArr
            };
          });
          
          console.log("处理后的服务器推荐教材列表:", processedRecommendations);
          
          // 服务器返回了推荐结果
          this.setData({
            smartRecommendedBooks: processedRecommendations,
            isLoadingRecommendations: false
          });
        } else {
          // 服务器未返回结果，回退到客户端搜索
          console.log("Server returned no recommendations, falling back to client-side search");
          this.loadClientRecommendations();
        }
      },
      fail: (err) => {
        console.error("Error fetching server recommendations:", err);
        // 服务器请求失败，回退到客户端搜索
        this.loadClientRecommendations();
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

  loadClientRecommendations: function() {
    const currentSemesterCourses = this.data.currentSemesterCourses;
    const uniqueTextbookNames = [...new Set(currentSemesterCourses.map(course => course.textbook.trim()))];
    const appInstance = getApp();
    
    // 默认图片设置为一个已存在的图片
    const defaultBookCover = '../../images/default_cover.png';
    
    // 如果没有教材名称，提前返回
    if (uniqueTextbookNames.length === 0) {
      this.setData({
        smartRecommendedBooks: [],
        isLoadingRecommendations: false
      });
      return;
    }
    
    // 输出调试信息，显示要查询的教材名称
    console.log("开始搜索教材:", uniqueTextbookNames);
    
    const searchPromises = uniqueTextbookNames.map(bookName => {
      return new Promise((resolve, reject) => {
        wx.request({
          url: appInstance.globalData.host + 'Good',
          data: {
            loadBy: "condition",
            key: bookName,
            catagory: "图书音像",
            start: 0,  // 从第一条开始
            end: 10    // 每个教材最多获取10条结果
          },
          success: (res) => {
            console.log(`教材"${bookName}"搜索结果:`, res.data);
            if (res.data && res.data.bookList && res.data.bookList.length > 0) {
              resolve(res.data.bookList);
            } else {
              resolve([]); 
            }
          },
          fail: (err) => {
            console.error(`Failed to search for textbook (smart rec): ${bookName}`, err);
            resolve([]); // 失败也resolve空数组，不中断Promise.all
          }
        });
      });
    });

    Promise.all(searchPromises)
      .then(results => {
        const allRecommendedBooks = [].concat(...results);
        console.log("获取到的所有教材数据:", allRecommendedBooks);
        
        // 处理图片字段
        const processedBooks = allRecommendedBooks
          .map(book => {
            // 处理id和bookId不一致的问题，确保bookId存在
            if (!book.bookId && book.id) {
              book.bookId = book.id;
            } else if (!book.bookId && !book.id) {
              // 如果两者都不存在，生成一个临时ID
              book.bookId = "temp_" + new Date().getTime() + "_" + Math.floor(Math.random() * 1000);
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
            
            // 确保每本书都有商品名称
            if (!book.goodname && book.goodName) {
              book.goodname = book.goodName;
            } else if (!book.goodname && !book.goodName) {
              book.goodname = book.textbookName || "未知教材";
            }
            
            return {
              ...book,
              images: imgArr
            };
          });
        
        // 过滤掉重复的书籍，基于bookId
        const uniqueBookIds = {};
        const uniqueBooks = [];
        
        processedBooks.forEach(book => {
          if (!uniqueBookIds[book.bookId]) {
            uniqueBookIds[book.bookId] = true;
            uniqueBooks.push(book);
          }
        });
        
        console.log("处理后的推荐教材列表:", uniqueBooks);
        
        this.setData({
          smartRecommendedBooks: uniqueBooks,
          isLoadingRecommendations: false
        });
      })
      .catch(error => {
        console.error("Error fetching smart textbook recommendations:", error);
        this.setData({ isLoadingRecommendations: false });
        wx.showToast({ title: '智能推荐加载失败', icon: 'none' });
      });
  },

  /**
   * 查看教材推荐列表
   */
  viewAllRecommendations: function() {
    const semester = this.data.semesters[this.data.currentSemesterIndex];
    
    wx.navigateTo({
      url: `/pages/course_recommendation/course_recommendation?semester=${this.data.currentSemesterIndex}`
    });
  },

  /**
   * 跳转到添加二手书页面
   */
  navigateToAddGood: function() {
    wx.navigateTo({
      url: '/pages/add_good/add_good?type=book'
    });
  },

  stopPropagation: function(e) {
  // 在微信小程序中阻止事件冒泡
  // return false; // WXML中可以直接使用 catchtap 替代该方法
  }
})