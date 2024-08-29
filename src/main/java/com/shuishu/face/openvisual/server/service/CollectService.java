package com.shuishu.face.openvisual.server.service;



import com.shuishu.face.openvisual.server.entity.dto.CollectAddDto;
import com.shuishu.face.openvisual.server.entity.dto.CollectDeleteDto;
import com.shuishu.face.openvisual.server.entity.dto.CollectDetailsDto;
import com.shuishu.face.openvisual.server.entity.dto.CollectListDto;
import com.shuishu.face.openvisual.server.entity.vo.CollectVo;

import java.util.List;

public interface CollectService {


    /**
     * 添加集合
     *
     * @param collectAddDto -
     * @return -
     */
    Boolean addCollect(CollectAddDto collectAddDto);

    /**
     * 删除集合
     *
     * @param collectDeleteDto -
     * @return -
     */
    Boolean deleteCollect(CollectDeleteDto collectDeleteDto);

    /**
     * 查询集合详情
     *
     * @param collectDetailsDto -
     * @return -
     */
    CollectVo findCollectDetails(CollectDetailsDto collectDetailsDto);

    /**
     * 查询集合列表
     *
     * @param collectListDto -
     * @return -
     */
    List<CollectVo> findCollectList(CollectListDto collectListDto);
}
