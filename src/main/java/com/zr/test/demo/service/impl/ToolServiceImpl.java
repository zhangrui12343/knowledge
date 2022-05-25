package com.zr.test.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.component.exception.CustomException;
import com.zr.test.demo.config.enums.ErrorCode;
import com.zr.test.demo.model.entity.Tool;
import com.zr.test.demo.dao.ToolMapper;
import com.zr.test.demo.model.entity.ToolAppRelation;
import com.zr.test.demo.model.pojo.TeachingTools;
import com.zr.test.demo.model.vo.ToolVO;
import com.zr.test.demo.service.IToolService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zr.test.demo.util.FileUtil;
import com.zr.test.demo.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zr
 * @since 2022-05-25
 */
@Service
public class ToolServiceImpl extends ServiceImpl<ToolMapper, Tool> implements IToolService {
    private final ToolAppRelationServiceImpl toolAppRelationService;

    @Autowired
    public ToolServiceImpl(ToolAppRelationServiceImpl toolAppRelationService) {
        this.toolAppRelationService = toolAppRelationService;
    }

    @Override
    public Result<Object> add(Tool dto, HttpServletRequest request) {
        return Result.success(this.baseMapper.insert(dto));
    }

    @Override
    public Result<List<ToolVO>> queryAll() {
        List<TeachingTools> list=this.baseMapper.selectAll();
        if (ListUtil.isEmpty(list)) {
            return Result.success(new ArrayList<>());
        }
        Map<String,List<String>> map=new HashMap<>();
        List<ToolVO> vos=new ArrayList<>();
        list.forEach(e->{
            String key=e.getId().toString()+"~!@#*"+e.getName();
            if(map.containsKey(key)){
                map.get(key).add(FileUtil.getBase64FilePath(e.getPath()));
            }else {
                List<String> logo=new ArrayList<>();
                logo.add(FileUtil.getBase64FilePath(e.getPath()));
                map.put(key,logo);
            }
        });
        map.forEach((k,v)->{
            String[] str=k.split("~!@#*");
            ToolVO vo=new ToolVO();
            vo.setId(Long.parseLong(str[0]));
            vo.setName(str[1]);
            vo.setLogo(v);
            vos.add(vo);
        });
        return Result.success(vos);
    }

    @Override
    public Result<Object> addRelation(ToolAppRelation dto, HttpServletRequest request) {
        return Result.success(toolAppRelationService.getBaseMapper().insert(dto));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> delete(Long id, HttpServletRequest request) {
        //删除关联表
        QueryWrapper<ToolAppRelation> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("tool_id",id);
        toolAppRelationService.getBaseMapper().delete(queryWrapper);
        return Result.success(this.baseMapper.deleteById(id));
    }

    @Override
    public Result<Object> deleteRelation(ToolAppRelation relation, HttpServletRequest request) {
        return Result.success(toolAppRelationService.getBaseMapper().delete(new QueryWrapper<>(relation)));
    }

    @Override
    public Result<Object> updateByDto(Tool dto, HttpServletRequest request) {
        if(dto.getId()==null){
            throw new CustomException(ErrorCode.SYS_PARAM_ERR);
        }
        return Result.success(this.baseMapper.updateById(dto));
    }
}
