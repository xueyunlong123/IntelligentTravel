package com.alpha.common.api;

import com.alpha.common.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenwen on 17/2/17.
 */
public abstract class AbstractResultSelected<T, P1, P2> {
    /**
     * 设置是否被选中
     * @param p 对象
     * @return
     */
    public boolean isSelected(P1 p){
        return false;
    }
    /**
     * 获取比对Key
     * @param p 对象
     * @return
     */
    public abstract P2 getKey(P1 p);

    /**
     * 创建生成选择对象
     * @param p
     * @return
     */
    public abstract T create(P1 p, boolean selected);


    /**
     * 获取返回结果
     * @param all 所有
     * @param areaCodes 覆盖的
     * @return
     */
    public List<T> convert(Collection<P1> all, Collection<P2> areaCodes){
        final Map<P2,Boolean> map = new HashMap<>();

        if (CollectionUtils.isNotEmpty(areaCodes)){
            areaCodes.stream().forEach(areaCode -> map.put(areaCode,true));
        }

        List<T> result = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(all)){
            for(P1 p : all){
                if (isSelected(p) || map.containsKey(getKey(p))){
                    result.add(create(p, true));
                }else {
                    result.add(create(p, false));
                }
            }
        }

        return result;
    }

}
