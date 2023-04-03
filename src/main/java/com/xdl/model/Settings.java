package com.xdl.model;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.*;
import com.intellij.util.xmlb.annotations.Transient;
import com.xdl.util.MethodExcludeParam;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 全局配置信息
 *
 * @author Bx_Hu
 */
@Data
@State(name = "XHttpSetting", storages = @Storage("x-http-setting.xml"))
public class Settings implements PersistentStateComponent<Settings> {
    /**
     * 默认名称
     */
    @Transient
    public static final String DEFAULT_NAME = "Default";

    private static final String LOCALHOST = "http://localhost:8080";

    /**
     * 域名
     */
    private String doMain;

    /**
     * 排除的headers
     */
    private String[] exclude;
    /**
     * 是否开启接口缓存
     */
    private Boolean orCache = true;

    /**
     * 获取单例实例对象
     *
     * @return 实例对象
     */
    public static Settings getInstance() {
        return ApplicationManager.getApplication().getService(Settings.class);
    }

    public Settings() {
        this.doMain = LOCALHOST;
        this.exclude = MethodExcludeParam.EXCLUDE;
    }


    public void restart() {
        this.doMain = LOCALHOST;
        this.exclude = MethodExcludeParam.EXCLUDE;
    }

    @Nullable
    @Override
    public Settings getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull Settings settings) {
        this.exclude = settings.exclude;
        this.doMain = settings.doMain;
        this.orCache = settings.orCache;
    }
}
