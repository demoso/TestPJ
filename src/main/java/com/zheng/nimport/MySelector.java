package com.zheng.nimport;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * ImportSelector : 返回需要导入的组件的全类名数组
 */
public class MySelector implements ImportSelector {
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{"com.example.demo.nimport.SelectBean"};
    }
}
