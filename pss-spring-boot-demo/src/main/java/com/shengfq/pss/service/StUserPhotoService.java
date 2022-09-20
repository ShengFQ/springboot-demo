package com.shengfq.pss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shengfq.pss.dto.StUserPhotoDto;
import com.shengfq.pss.entity.StUserPhoto;
import com.shengfq.pss.mapper.StUserPhotoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

/**
* @description 用户照片(警官证)表
* @author shengfq
* @date 2022年09月19日 13:34:53
*/
@Service
public class StUserPhotoService  {
    private static final String PREFIX_PATH="mydata/zijinchakong/task/2022-09-19/";
    @Autowired
    StUserPhotoMapper stUserPhotoMapper;
    /**
     * 分页读取待办任务数据
     * */
    public List<StUserPhoto> list(int pageNo,int pageSize){
        IPage<StUserPhoto> page=new Page<StUserPhoto>(pageNo,pageSize);
        QueryWrapper<StUserPhoto> queryWrapper=new QueryWrapper<StUserPhoto>();
        queryWrapper.eq("DEL_FLAG","0");
        IPage<StUserPhoto>  result= this.stUserPhotoMapper.selectPage(page,queryWrapper);
        return result.getRecords();
    }
    /**
     * 将文件流序列化到本地返回路径信息
     * */
    public StUserPhotoDto writeFile(StUserPhoto entity) {
        boolean writeSuccess=false;
        StUserPhotoDto dto=new StUserPhotoDto();
        dto.setPhotoId(entity.getPhotoId());
        dto.setUserId(entity.getUserId());
        dto.setPhotoName(entity.getPhotoName());
        dto.setPhotoPath(buildPath(entity));
        dto.setPhotoType(entity.getPhotoType());
        try {
            writeSuccess = write(entity);
        }catch (IOException e){
            e.printStackTrace();
        }
        if(writeSuccess){
            return dto;
        }else{
            return null;
        }
    }
    /**
     * 修改任务完成状态
     *
     * */
    public int updateByPrimaryKey(StUserPhoto entity) throws Exception{
        if(entity.getPhotoId()==null){
            throw new Exception("图片id不能为空");
        }
        if(entity.getUserId()==null){
            throw new Exception("用户id不能为空");
        }
        entity.setDelFlag("1");
       return this.stUserPhotoMapper.updateById(entity);
    }

    /**
     *构建文件路径
     * /mydata/zijinchakong/task/2022-07-25/police_test_20220704091553_20220725142430.jpg
    */
    private String buildPath(StUserPhoto entity){
        StringBuffer sb=new StringBuffer(PREFIX_PATH);
        sb.append(entity.getUserId());
        if(entity.getPhotoType().equals("0")){
            sb.append("_0");
        }else if (entity.getPhotoType().equals("1")){
            sb.append("_1");
        }
        sb.append(".jpg");
        return sb.toString();
    }
    /**
     * 将数据流序列化到本地文件
     * */
    private boolean write(StUserPhoto entity) throws IOException {
        String targetPath=buildPath(entity);
        if(entity.getPhoto()==null){
            return false;
        }
        OutputStream out=null;
        InputStream is=null;
        try{
            byte[] target=entity.getPhoto();
            File file=new File(targetPath);
            if(!file.exists()){
                file.createNewFile();
            }
             out = new FileOutputStream(targetPath);
             is = new ByteArrayInputStream(target);
            byte[] buff = new byte[1024];
            int len = 0;
            while((len=is.read(buff))!=-1){
                out.write(buff, 0, len);
            }
        }catch(IOException exception){
            exception.printStackTrace();
            System.out.println(exception.getMessage());
            return false;
        }finally{
            if(is!=null) {
                is.close();
            }
            if(out!=null) {
                out.close();
            }
        }
        return true;
    }

}




