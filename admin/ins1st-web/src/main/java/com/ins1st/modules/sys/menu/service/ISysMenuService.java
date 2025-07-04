package com.ins1st.modules.sys.menu.service;

import com.ins1st.core.R;
import com.ins1st.modules.sys.menu.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统菜单表 服务类
 * </p>
 *
 * @author sun
 * @since 2019-05-07
 */
public interface ISysMenuService extends IService<SysMenu> {

    /**
     * 查询主页菜单
     * @return
     */
    public List<SysMenu> queryIndexMenus(Integer id);

    /**
     * 查询ztree数据
     * @return
     */
    public List<Map<String,Object>> queryZtree();

    /**
     * 删除菜单
     * @param ids
     * @return
     */
    public boolean delMenu(String ids);


    /**
     * 保存菜单
     * @param sysMenu
     * @return
     */
    public R menuSave(SysMenu sysMenu);
}
