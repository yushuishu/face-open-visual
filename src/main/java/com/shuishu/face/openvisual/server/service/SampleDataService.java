package com.shuishu.face.openvisual.server.service;



import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shuishu.face.openvisual.server.entity.dto.*;
import com.shuishu.face.openvisual.server.entity.vo.SampleDataVo;


/**
 * @Author ：谁书-ss
 * @Date   ： 2024-08-28 15:52
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
public interface SampleDataService {

    /**
     * 添加样本
     *
     * @param sampleDataAddDto -
     * @return -
     */
    Boolean addSample(SampleDataAddDto sampleDataAddDto);

    /**
     * 更新样本
     *
     * @param sampleDataUpdateDto -
     * @return -
     */
    Boolean updateSample(SampleDataUpdateDto sampleDataUpdateDto);

    /**
     * 删除样本
     *
     * @param sampleDataDeleteDto -
     * @return -
     */
    Boolean deleteSample(SampleDataDeleteDto sampleDataDeleteDto);

    /**
     * 查询样本详情
     *
     * @param sampleDataDetailsDto -
     * @return -
     */
    SampleDataVo findSampleDetails(SampleDataDetailsDto sampleDataDetailsDto);

    /**
     * 查询样本分页
     *
     * @param sampleDataPageDto -
     * @param page -
     * @return -
     */
    Page<SampleDataVo> findSamplePage(SampleDataPageDto sampleDataPageDto, Page<SampleDataVo> page);
}
