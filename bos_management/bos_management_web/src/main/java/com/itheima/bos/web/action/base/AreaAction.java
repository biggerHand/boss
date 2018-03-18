package com.itheima.bos.web.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.Area;
import com.itheima.bos.service.base.AreaService;
import com.itheima.utils.PinYin4jUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**  
 * ClassName:AreaAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午2:53:21 <br/>       
 */
@Controller
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
public class AreaAction extends ActionSupport implements ModelDriven<Area> {
    private Area model;
    @Autowired
    private AreaService areaService;
    private File file;
    public void setFile(File file) {
        this.file = file;
    }
    @Override
    public Area getModel() {
        if(model==null){
            model = new Area();
        }
        return model;
    }
    
    @Action(value="areaAction_importXLS",
            results={@Result(name="success",location="/pages/base/area.html",type="redirect")})
    public String importXLS(){
        ArrayList<Area> list = new ArrayList<>();
        
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));
            
            HSSFSheet sheetAt = workbook.getSheetAt(0);
            for (Row row : sheetAt) {
                if(row.getRowNum()==0){
                    continue;
                }
                String province = row.getCell(1).getStringCellValue();
                String city = row.getCell(2).getStringCellValue();
                String district = row.getCell(3).getStringCellValue();
                String postcode = row.getCell(4).getStringCellValue();
                
                province = province.substring(0, province.length()-1);
                city = city.substring(0, city.length()-1);
                district = district.substring(0, district.length()-1);
                String citycode = PinYin4jUtils.hanziToPinyin(city,"").toUpperCase();
                String[] headByString = PinYin4jUtils.getHeadByString(postcode+city+district, true);
                String shortcode = PinYin4jUtils.stringArrayToString(headByString);
                
                Area area = new Area();
                area.setCity(city);
                area.setProvince(province);
                area.setDistrict(district);
                area.setPostcode(postcode);
                area.setCitycode(citycode);
                area.setShortcode(shortcode);
                
                list.add(area);
            }
            areaService.save(list);
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();  
        }
        return SUCCESS;
    }
}
  
