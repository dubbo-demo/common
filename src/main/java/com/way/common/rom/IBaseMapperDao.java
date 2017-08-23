// BaseDao
package com.way.common.rom;

import com.way.common.rom.annotation.Pagination;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 功能描述：IBaseMapperDao接口
 *
 * @ClassName IBaseMapperDao
 * @Author：xinpei.xu
 * @Date：2017/08/14 22:20
 */
public interface IBaseMapperDao<E, PK> extends IBaseMapper {
    /**
     * @param e
     * @description 详细说明
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void add(final E e);


    /**
     * @param e
     * @description 详细说明
     */
    Integer remove(@Param(value = "id") final PK e);

    /**
     * @param e
     * @description 详细说明
     */
    Integer update(final E e);

    /**
     * @param id
     * @return
     * @throws Exception
     * @description 详细说明
     */
    E getEntity(@Param(value = "id") final PK id);

    /**
     * @return
     * @description 详细说明
     */
    List<E> getEntities(@Param(value = "page") Pagination<E> page, @Param(value = "e") final E e);

}
