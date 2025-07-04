package com.ins1st.core.config.beetl;

import com.ins1st.core.config.beetl.ext.DictExt;
import com.ins1st.core.config.beetl.ext.ShiroExt;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;

public class BeetlConfiguration extends BeetlGroupUtilConfiguration {

    @Override
    protected void initOther() {
        groupTemplate.registerFunctionPackage("shiro", new ShiroExt());
        groupTemplate.registerFunctionPackage("dict", new DictExt());
    }
}
