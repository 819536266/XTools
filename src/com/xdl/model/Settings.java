package com.xdl.model;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.annotations.Transient;
import com.xdl.util.MethodExcludeParam;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 全局配置信息
 *
 * @author makejava
 * @version 1.0.0
 * @since 2018/07/18 09:33
 */
@Data
@State(name = "XHttpSetting", storages = @Storage("x-http-setting.xml"))
public class Settings implements PersistentStateComponent<Settings> {
    /**
     * 默认名称
     */
    @Transient
    public static final String DEFAULT_NAME = "Default";


    private String doMain;

    private String[] exclude;


    /**
     * 获取单例实例对象
     *
     * @return 实例对象
     */
    public static Settings getInstance() {
        return ServiceManager.getService(Settings.class);
    }

    public Settings() {
        this.doMain = "http://localhost:8080";
        this.exclude = MethodExcludeParam.exclude;
    }


    public  void restart(){
        this.doMain = "http://localhost:8080";
        this.exclude = MethodExcludeParam.exclude;
        System.out.println(exclude);
    }

    @Nullable
    @Override
    public Settings getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull Settings settings) {
    }
}
