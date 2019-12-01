package io.renren.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.renren.modules.distribution.service.DistributionService;
import io.renren.modules.sys.entity.TextEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;


public class ReadExcelUtil {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String EXCEL_XLS = ".xls";
    private static final String EXCEL_XLSX = ".xlsx";
    @Autowired
    private DistributionService distributionService;
    /**
     *读取excel数据
     * @throws Exception
     *
     */
    public static List<List<String>> readExcelInfo(String url) throws Exception{
        /*
         * workbook:工作簿,就是整个Excel文档
         * sheet:工作表
         * row:行
         * cell:单元格
         */

//        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(url)));
//        支持excel2003、2007
        File excelFile = new File(url);//创建excel文件对象
        InputStream is = new FileInputStream(excelFile);//创建输入流对象
        checkExcelVaild(excelFile);
        Workbook workbook = getWorkBook(is, excelFile);
//        Workbook workbook = WorkbookFactory.create(is);//同时支持2003、2007、2010
//        获取Sheet数量
        int sheetNum = workbook.getNumberOfSheets();
//      创建二维数组保存所有读取到的行列数据，外层存行数据，内层存单元格数据
        List<List<String>> dataList = new ArrayList<List<String>>();
//        FormulaEvaluator formulaEvaluator = null;
//        遍历工作簿中的sheet,第一层循环所有sheet表
        for(int index = 0;index<sheetNum;index++){
            Sheet sheet = workbook.getSheetAt(index);
            if(sheet==null){
                continue;
            }
            System.out.println("表单行数："+sheet.getLastRowNum());
//            如果当前行没有数据跳出循环，第二层循环单sheet表中所有行
            for(int rowIndex=0;rowIndex<=sheet.getLastRowNum();rowIndex++){
                Row row = sheet.getRow(rowIndex);
//                根据文件头可以控制从哪一行读取，在下面if中进行控制
                if(row==null){
                    continue;
                }
//                遍历每一行的每一列，第三层循环行中所有单元格
                List<String> cellList = new ArrayList<String>();
                for(int cellIndex=0;cellIndex<row.getLastCellNum();cellIndex++){
                    Cell cell = row.getCell(cellIndex);
                    System.out.println("遍历行中cell数据:"+getCellValue(cell));
                    cellList.add(getCellValue(cell));
                    System.out.println("第"+cellIndex+"个:     cell个数："+cellList.size());
                }
                dataList.add(cellList);
                System.out.println("第"+rowIndex+"行:     共几行："+dataList.size());
            }

        }
        is.close();
        return dataList;
    }
    /**
     *获取单元格的数据,暂时不支持公式
     *
     *
     */
    public static String getCellValue(Cell cell){
        CellType cellType = cell.getCellTypeEnum();
        String cellValue = "";
        if(cell==null || cell.toString().trim().equals("")){
            return null;
        }

        if(cellType==CellType.STRING){
            cellValue = cell.getStringCellValue().trim();
            return cellValue = StringUtils.isEmpty(cellValue)?"":cellValue;
        }
        if(cellType==CellType.NUMERIC){
            if (HSSFDateUtil.isCellDateFormatted(cell)) {  //判断日期类型
                cellValue = DateUtils.longToString(cell.getDateCellValue().getTime());
            } else {  //否
                cellValue = new DecimalFormat("#.######").format(cell.getNumericCellValue());
            }
            return cellValue;
        }
        if(cellType==CellType.BOOLEAN){
            cellValue = String.valueOf(cell.getBooleanCellValue());
            return cellValue;
        }
        return null;

    }
    /**
     *判断excel的版本，并根据文件流数据获取workbook
     * @throws IOException
     *
     */
    public static Workbook getWorkBook(InputStream is,File file) throws Exception{

        Workbook workbook = null;
        if(file.getName().endsWith(EXCEL_XLS)){
            workbook = new HSSFWorkbook(is);
        }else if(file.getName().endsWith(EXCEL_XLSX)){
            workbook = new XSSFWorkbook(is);
        }

        return workbook;
    }
    /**
     *校验文件是否为excel
     * @throws Exception
     *
     *
     */
    public static void checkExcelVaild(File file) throws Exception {
        String message = "该文件是EXCEL文件！";
        if(!file.exists()){
            message = "文件不存在！";
            throw new Exception(message);
        }
        if(!file.isFile()||((!file.getName().endsWith(EXCEL_XLS)&&!file.getName().endsWith(EXCEL_XLSX)))){
            System.out.println(file.isFile()+"==="+file.getName().endsWith(EXCEL_XLS)+"==="+file.getName().endsWith(EXCEL_XLSX));
            System.out.println(file.getName());
            message = "文件不是Excel";
            throw new Exception(message);
        }
    }

    public static List<TextEntity> sortList(String path) throws Exception {
        List<List<String>>  se = readExcelInfo(path);
        List<String> titlelst= se.get(0);
        List<TextEntity> textEntityList = new ArrayList<>();
        for(int i =1;i<se.size();i++){
            TextEntity  textEntity = new TextEntity();
            String tempText = "";
            textEntity.setName(se.get(i).get(0));
            for(int j=0;j<se.get(i).size();j++){
                tempText = tempText + titlelst.get(j)+":" + se.get(i).get(j) + "\r\n";
            }
            textEntity.setText(tempText);
            textEntityList.add(textEntity);
        }
        return textEntityList;
    }

    public static Map<String, String> parseXml(HttpServletRequest request)throws Exception{

        // 将解析结果存储在HashMap中
        Map<String, String>map =new HashMap<String, String>();
        // 从request中得到输入流
        InputStream  inputStream=request.getInputStream();
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到XML的根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        @SuppressWarnings("unchecked")
        List<Element> elementList = root.elements();
        // 判断又没有子元素列表
        if (elementList.size()==0){
            map.put(root.getName(), root.getText());
        }else {
            for (Element e : elementList)
                map.put(e.getName(), e.getText());
        }
        // 释放资源
        inputStream.close();
        inputStream = null;
        System.out.println("---------xml转换为map-----:"+map);
        return map;
    }


    public static void main(String[] args) throws Exception {
        List<List<String>>  se = readExcelInfo("C:\\Users\\86186\\Downloads\\导入信息.xlsx");
        List<String> titlelst= se.get(0);
        List<TextEntity> textEntityList = new ArrayList<>();
        for(int i =1;i<se.size();i++){
            TextEntity  textEntity = new TextEntity();
            String tempText = "";
            textEntity.setName(se.get(i).get(0));
            for(int j=0;j<se.get(i).size();j++){
                tempText = tempText + titlelst.get(j)+":" + se.get(i).get(j) + "|";
            }
            textEntity.setText(tempText);
            textEntityList.add(textEntity);
        }
         System.out.println(textEntityList);
    }
}
