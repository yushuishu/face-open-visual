package com.shuishu.face.openvisual.server.service.impl;

import com.shuishu.face.openvisual.server.entity.extend.TableColumn;
import com.shuishu.face.openvisual.server.mapper.OperateTableMapper;
import com.shuishu.face.openvisual.server.service.OperateTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author ：谁书-ss
 * @Date   ： 2024-08-27 22:58
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
@Service
@RequiredArgsConstructor
public class OperateTableServiceImpl implements OperateTableService {

    public final OperateTableMapper operateTableMapper;


    @Override
    public boolean exist(String table) {
        String info = operateTableMapper.showTable(table);
        return null != info && !info.isEmpty();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean dropTable(String table) {
        return operateTableMapper.dropTable(table) > 0;
    }

    @Override
    public boolean createImageTable(String table) {
        return operateTableMapper.createImageTable(table) >= 0;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean createSampleTable(String table, List<TableColumn> columns) {
        if(null == columns){
            columns = new ArrayList<>();
        }
        return operateTableMapper.createSampleTable(table, columns) >= 0;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean createFaceTable(String table, List<TableColumn> columns) {
        if(null == columns){
            columns = new ArrayList<>();
        }
        return operateTableMapper.createFaceTable(table, columns) >= 0;
    }

}
