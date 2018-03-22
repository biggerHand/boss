package com.itheima.bos.web.action.base;

import java.io.File;
import java.io.FileInputStream;
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
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.service.base.SubAreaService;
import com.itheima.bos.web.action.CommonAction;
import com.itheima.utils.PinYin4jUtils;

/**  
 * ClassName:SubAreaAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月22日 上午10:46:11 <br/>       
 */
@Controller
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
public class SubAreaAction extends CommonAction<SubArea> {

    public SubAreaAction() {
        super(SubArea.class);  
    }
    @Autowired
    private SubAreaService subAreaService;
    private File file;
    public void setFile(File file) {
        this.file = file;
    }
    @Action(value="subAreaAction_importXLS",
            results={@Result(name="success",location="/pages/base/sub_area.html",type="redirect")})
    public String importXLS(){
        ArrayList<SubArea> list = new ArrayList<>();
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));
            
            HSSFSheet sheetAt = workbook.getSheetAt(0);
            for (Row row : sheetAt) {
                if(row.getRowNum()==0){
                    continue;
                }
                
                String keyWords = row.getCell(5).getStringCellValue();
                String startNum = row.getCell(6).getStringCellValue();
                String endNum = row.getCell(7).getStringCellValue();
                String single = row.getCell(8).getStringCellValue();
                String assistKeyWords = row.getCell(9).getStringCellValue();
                
                
                Area area = new Area();
                
                SubArea subArea = new SubArea();
                
                
                list.add(subArea);
            }
            subAreaService.save(list);
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();  
        }
        return SUCCESS;
    }
}
  
