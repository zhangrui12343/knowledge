package com.zr.test.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.component.exception.CustomException;
import com.zr.test.demo.config.enums.ErrorCode;
import com.zr.test.demo.model.entity.App;
import com.zr.test.demo.model.entity.Tool;
import com.zr.test.demo.dao.ToolMapper;
import com.zr.test.demo.model.entity.ToolAppRelation;
import com.zr.test.demo.model.pojo.TeachingTools;
import com.zr.test.demo.model.pojo.ToolApps;
import com.zr.test.demo.model.vo.FileVO;
import com.zr.test.demo.model.vo.ToolAppVO;
import com.zr.test.demo.model.vo.ToolVO;
import com.zr.test.demo.service.IToolService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zr.test.demo.support.FileRouterBiz;
import com.zr.test.demo.util.FileUtil;
import com.zr.test.demo.util.ListUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

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
    private final FileRouterBiz fileRouterBiz;

    @Autowired
    public ToolServiceImpl(ToolAppRelationServiceImpl toolAppRelationService, FileRouterBiz fileRouterBiz) {
        this.toolAppRelationService = toolAppRelationService;
        this.fileRouterBiz = fileRouterBiz;
    }

    @Override
    public Result<Object> add(Tool dto, HttpServletRequest request) {
        return Result.success(this.baseMapper.insert(dto));
    }

    @Override
    public Result<List<ToolVO>> queryAll() {
        List<TeachingTools> list = this.baseMapper.selectAll();
        if (ListUtil.isEmpty(list)) {
            return Result.success(new ArrayList<>());
        }
        LinkedHashMap<String, List<Map<String, String>>> map = new LinkedHashMap<>();
        List<ToolVO> vos = new ArrayList<>();
        list.forEach(e -> {
            String key = e.getId().toString() + "~!@#" + e.getName();
            if (e.getAppid() != null ) {
                Map<String, String> vo = new HashMap<>(3);
                vo.put("appid", e.getAppid().toString());
                vo.put("logoid", e.getLogo().toString());
                vo.put("path", FileUtil.getBase64FilePath(fileRouterBiz.selectPath(e.getLogo())));
                if (map.containsKey(key)) {
                    map.get(key).add(vo);
                } else {
                    List<Map<String, String>> logos = new ArrayList<>();
                    logos.add(vo);
                    map.put(key, logos);
                }
            }else {
                map.put(key,new ArrayList<>());
            }
        });
        map.forEach((k, v) -> {
            String[] str = k.split("~!@#");
            ToolVO vo = new ToolVO();
            vo.setId(Long.parseLong(str[0]));
            vo.setName(str[1]);
            vo.setLogos(v);
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
        QueryWrapper<ToolAppRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tool_id", id);
        toolAppRelationService.getBaseMapper().delete(queryWrapper);
        return Result.success(this.baseMapper.deleteById(id));
    }

    @Override
    public Result<Object> deleteRelation(ToolAppRelation relation, HttpServletRequest request) {
        return Result.success(toolAppRelationService.getBaseMapper().delete(new QueryWrapper<>(relation)));
    }

    @Override
    public Result<Object> updateByDto(Tool dto, HttpServletRequest request) {
        if (dto.getId() == null) {
            throw new CustomException(ErrorCode.SYS_PARAM_ERR);
        }
        return Result.success(this.baseMapper.updateById(dto));
    }

    @Override
    public Result<List<ToolAppVO>> findAll() {
        //查询所有矩阵
        List<ToolApps> list = this.baseMapper.findAll();
        if (ListUtil.isEmpty(list)) {
            return Result.success(new ArrayList<>());
        }
        Map<String, List<App>> map = new HashMap<>();
        list.forEach(e -> {
            if(e.getId()==null){
                map.put(e.getTool(),new ArrayList<>());
            }else{
                App app = new App();
                BeanUtils.copyProperties(e, app);
                if (map.containsKey(e.getTool())) {
                    map.get(e.getTool()).add(app);
                } else {
                    List<App> apps = new ArrayList<>();
                    apps.add(app);
                    map.put(e.getTool(), apps);
                }
            }
        });
        List<ToolAppVO> res = new ArrayList<>();
        map.forEach((k, v) -> {
            ToolAppVO vo = new ToolAppVO();
            vo.setName(k);
            vo.setApps(v);
            res.add(vo);
        });
        return Result.success(res);
    }
}
