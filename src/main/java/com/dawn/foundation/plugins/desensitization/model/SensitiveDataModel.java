package com.dawn.foundation.plugins.desensitization.model;

import com.dawn.foundation.plugins.desensitization.enumeration.SensitiveType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @Author : leonwang
 * @Descpriction 敏感数据的模型
 * @Date:created 2018/9/18
 */
@Getter
@Setter
@Builder
public class SensitiveDataModel {
    private String name;
    private SensitiveType sensitiveType;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SensitiveDataModel)) {
            return false;
        }
        SensitiveDataModel sensitiveDataModel = (SensitiveDataModel) obj;
        return new EqualsBuilder()
                .append(name, sensitiveDataModel.name)
                .append(sensitiveType, sensitiveDataModel.sensitiveType)
                .isEquals();

    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .append(sensitiveType)
                .toHashCode();
    }
}
