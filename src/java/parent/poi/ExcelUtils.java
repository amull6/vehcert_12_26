package parent.poi;
import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.*;

import cn.com.wz.parent.ConstantsUtil;

/**
 *@Description 对Excel的操作集合
 *@Auther huxx
 *@createTime 2013-2-24 下午2:15:54
 */
public class ExcelUtils {
    private String fileEncoding="GBK";
    private ExcelUtils(String fileEncode){
        if(fileEncode!=null&&!fileEncode.isEmpty()){
            fileEncoding=fileEncode;
        }
    }
    /**
     *@Description 获取workbook
     *@param
     *@return
     *@Auther huxx
     *@createTime 2013-2-24 下午4:05:48
     */
    private Workbook getWorkbook(InputStream  fin,String fileExtension){
        Workbook workbook = null;
        if(ConstantsUtil.getEXCEL2007().equals(fileExtension.toUpperCase()))
        {
            try {
                workbook = new XSSFWorkbook(fin);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            try {

                workbook = new HSSFWorkbook(fin);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return workbook;
    }
    /**
     *@Description 获取workbook
     *@param
     *@return
     *@Auther huxx
     *@createTime 2013-2-24 下午4:05:48
     */
    private Workbook getWorkbook(String filePath) throws FileNotFoundException {
        String fileExtension=filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
        Workbook workbook = null;
        InputStream fin=new FileInputStream(new File(filePath));

        return this.getWorkbook(fin,fileExtension);
    }
    /**
     *@Description 从excel读取数据，并将数据列对应
     *@params filePath文件路径
     *config_column_map 存储了参数信息。sheet从第几个sheet中取，从0开始；startRow开始读取数据的行的行号
     *columnMap保存了列与实体属性的对应关系，第一列的列号为0.
     *@return
     *@Auther huxx
     *@createTime 2013-2-24 下午4:14:47
     *@update huxx 2013-07-02  1、修改了空行的判断，2、当有空列时继续读取后面的列，3、当不是非空行时，加入list
     */
    public List<Map<String,Object>> readExcel(String filePath,Map<String,Object> config_column_map){
        //根据文件流获取工作薄
        Workbook wb=null;
        try {
            wb=getWorkbook(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        //获取指定的sheet
//		//根据sheet的名字获取sheet
//		Sheet sheet1=wb.getSheet("个人邮箱");
        Sheet sheet=wb.getSheetAt(Integer.parseInt(config_column_map.get("sheet").toString()));
        //获取sheet的总行数
        int totalCount=sheet.getLastRowNum();
        //获取开始行数
        int startRow=Integer.parseInt(config_column_map.get("startRow").toString())-1;
        //存放读取的数据记录
        List<Map<String,Object>> lst=new ArrayList<Map<String,Object>>();
        for(int i=startRow;i<totalCount+1;i++){
            Row r=sheet.getRow(i);
            if(r!=null){
                Map<String,Object> m=new HashMap<String,Object>();

                //size和count用于判断是否是空行，当size>count时表示不是空行，size=count表示空行
                int size=0;//记录读取的总列数
                int count=0;//记录实际空列的个数
                //遍历传入的列号以及列号对应的属性
                for (Iterator<Map.Entry<String, String>> it =((Map<String,String>) config_column_map.get("columnMap")).entrySet().iterator(); it.hasNext();) {
                    Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
                    Cell c=r.getCell(Integer.parseInt(entry.getKey()));
                    size+=1;
                    if(c==null ||c.toString().trim().length()<=0){
                        m.put(entry.getValue(),null);
                        count+=1;
                    }else{
                        if(c.getCellType()== HSSFCell.CELL_TYPE_NUMERIC){
                            c.setCellType(HSSFCell.CELL_TYPE_STRING);
                            m.put(entry.getValue(),c.toString());
                            //将科学计数法表示的转换为数字类型的
//                            NumberFormat.getInstance().format(c.getNumericCellValue())
                        }else{
                            m.put(entry.getValue(),c.toString());
                        }

                    }

                }
                if(size>count){
                    lst.add(m);
                }
            }

        }

        return lst;
    }

    /**
     *@Description 根据参数信息将内容写入excel中
     *@params filePath文件路径 ,如果不传入就随机生成并最后删除
     *config_column_map 表头信息，list中第几个元素就是第几个sheet的表头信息 ，
     * 每个sheet的表头信息又是一个list，每个list中有一个map记录；map.titleValue就是这一列的表头名，map.titleKey是填充列的属性
     *sheetNames记录了每个sheet的名称
     *content 要写入excel中的记录信息第一层list中元素是每个sheet的内容list，第二层list中每个元素是map类型，每个map存储了一行的记录，通过config_column_map中的key获取内容值
     *@return
     *@Auther huxx
     *@createTime 2013-3-13 上午10:54:20
     * //TODO 扩展可以自定义表格样式
     */
    public  Map<String,Object> writeExcel(OutputStream outputStream,String filePath,ArrayList<ArrayList<Map<String,Object>>> config_column_map,String[] sheetnames,ArrayList<ArrayList<Map<String,Object>>> contentList) throws IOException {
        Map<String,Object> result=null;

        SXSSFWorkbook wb = new SXSSFWorkbook(10000);//内存中保留 10000 条数
        //获取指定的sheet
        for(int sn=0;sn<config_column_map.size();sn++){
            Sheet sheet = wb.createSheet(String.valueOf(sn));
            wb.setSheetName(sn, sheetnames[sn]);
            ArrayList<Map<String,Object>> titleList = config_column_map.get(sn);

            //设置表头样式
            CellStyle style5 = wb.createCellStyle();
            Font headerFont = wb.createFont();
            headerFont.setBoldweight(Short.valueOf("14"));
            style5.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            style5.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
            style5.setFont(headerFont);

            //生成表头信息
            Row row = sheet.createRow(0);
            row.setRowStyle(style5);
            for(int cols=0;cols<titleList.size();cols++){
                Map<String,Object> title = titleList.get(cols);
                Cell cell = row.createCell(cols);
                cell.setCellType(XSSFCell.CELL_TYPE_STRING);//文本格式
                cell.setCellStyle(style5);
                cell.setCellValue(title.get("titleValue").toString());//写入内容
            }

            //填充每一行的内容
            ArrayList<Map<String,Object>> cList = contentList.get(sn);
            for(int c=0;c<cList.size();c++){
                Row r = sheet.createRow(c+1);
                Map<String,Object> content=cList.get(c);
                for(int cols=0;cols<titleList.size();cols++){
                    Map<String,Object> title = titleList.get(cols);
                    Object value=content.get(title.get("titleKey").toString());
                    Cell cell = r.createCell(cols);
                    cell.setCellType(XSSFCell.CELL_TYPE_STRING);//文本格式
                    if(value!=null){
                        cell.setCellValue(value.toString());//写入内容
                    }else{
                        cell.setCellValue("");//写入内容
                    }

                }
            }

        }

        if (outputStream!=null){
            wb.write(outputStream);
        }else{
            boolean isDelete=false;
            if(filePath==null || filePath.length()<0){
                filePath=getUniqueFileName(".xlsx");
                isDelete=true;
            }
            File file=new File(filePath);
            FileOutputStream output = new FileOutputStream(file);  //读取的文件路径
            wb.write(output);
            output.close();

            if(isDelete){
                file.delete();
            }
        }

        config_column_map.clear();
        contentList.clear();

        return result;
    }

    /**
     *@Description 根据参数信息将内容写入excel中     (此方法是为了兼容以前的写法没有添加单元格样式控制，如果要控制单元格的样式可以使用writeExcel)
     *@params filePath文件路径 ,如果不传入就随机生成并最后删除
     *config_column_map 表头信息，list中第几个元素就是第几个sheet的表头信息 ，
     * 每个sheet的表头信息是一个map
     *sheetNames记录了每个sheet的名称
     *content 要写入excel中的记录信息第一层list中的元素师每个sheet的内容list，第二层list中每个元素是map，每个map记录了一行的记录，通过config_column_map中的key获取内容值
     *@return
     *@Auther huxx
     *@createTime 2013-3-13 上午10:54:20
     */
    public  Map<String,Object> preWriteExcel(OutputStream outputStream,String filePath,ArrayList<Map<String,Object>> config_column_map,ArrayList<String>  sheetnames,ArrayList<ArrayList<Map<String,Object>>> contentList) throws IOException {
        Map<String,Object> result=null;

        SXSSFWorkbook wb = new SXSSFWorkbook(10000);//内存中保留 10000 条数
        //获取指定的sheet
        for(int sn=0;sn<config_column_map.size();sn++){
            Sheet sheet = wb.createSheet(String.valueOf(sn));
            wb.setSheetName(sn, sheetnames.get(sn));
            Map<String,Object> titleList = config_column_map.get(sn);

            //设置表头样式
            CellStyle style5 = wb.createCellStyle();
            Font headerFont = wb.createFont();
            headerFont.setBoldweight(Short.valueOf("14"));
            style5.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            style5.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
            style5.setFont(headerFont);

            //生成表头信息
            Row row = sheet.createRow(0);
            row.setRowStyle(style5);
            for(int cols=0;cols<titleList.keySet().size();cols++){
                Object title = titleList.get(titleList.keySet().toArray()[cols]);
                Cell cell = row.createCell(cols);
                cell.setCellStyle(style5);
                cell.setCellType(XSSFCell.CELL_TYPE_STRING);//文本格式
                cell.setCellValue(title.toString());//写入内容
            }

            //填充每一行的内容
            ArrayList<Map<String,Object>> cList = contentList.get(sn);
            for(int c=0;c<cList.size();c++){
                Row r = sheet.createRow(c+1);
                Map<String,Object> content=cList.get(c);
                for(int cols=0;cols<titleList.keySet().size();cols++){
                    Object value=content.get(titleList.keySet().toArray()[cols].toString());
                    Cell cell = r.createCell(cols);
                    cell.setCellType(XSSFCell.CELL_TYPE_STRING);//文本格式
                    if(value!=null){
                        cell.setCellValue(value.toString());//写入内容
                    }else{
                        cell.setCellValue("");//写入内容
                    }

                }
            }

        }

        if (outputStream!=null){
            wb.write(outputStream);
        }else{
            boolean isDelete=false;
            if(filePath==null || filePath.length()<0){
                filePath=getUniqueFileName(".xlsx");
                isDelete=true;
            }
            File file=new File(filePath);
            FileOutputStream output = new FileOutputStream(file);  //读取的文件路径
            wb.write(output);
            output.close();

            if(isDelete){
                file.delete();
            }
        }
        config_column_map.clear();
        contentList.clear();
        return result;
    }

    /**
     * @Description 使用先导出为xml文件在写入到excel中的形式导出数据到excel中
     * @param outputStream
     * @param data  [[key:value,key1:value1] ,[key:value,key1:value1]]
     * @param fields  ['key':'value','key1':'value1']
     * @problem 导出文件再2010中无法打开
     */
    public void exportExcel(OutputStream outputStream, List data, Map<String,String> fields,String sheetName) throws Exception {
        // Step 1. Create a template file. Setup sheets and workbook-level objects such as
        // cell styles, number formats, etc.

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(sheetName);

        Map<String, XSSFCellStyle> styles = createStyles(wb);
        //name of the zip entry holding sheet data, e.g. /xl/worksheets/sheet1.xml
        String sheetRef = sheet.getPackagePart().getPartName().getName();

        //save the template
        FileOutputStream os = null;
        String templateFile=getUniqueFileName(".xlsx");
        File template=new File(templateFile);
        os = new FileOutputStream(template);
        wb.write(os);
        os.close();


        //Step 2. Generate XML file.
//        tmp = File.createTempFile("sheet", ".xml");
        String xmlFile=getUniqueFileName(".xml");
        File tmp=new File(xmlFile);
        Writer fw = new FileWriter(tmp);

        generate(fw, styles,data,fields,fileEncoding);
        fw.close();

        String fileName=getUniqueFileName(".xlsx");
        //Step 3. Substitute the template entry with the generated data
        FileOutputStream out = new FileOutputStream(fileName);
        substitute(template, tmp, sheetRef.substring(1), out);
        out.close();

        InputStream inputStream = new FileInputStream(fileName);
        copyStream(inputStream,outputStream);
        inputStream.close();

        //将生成的临时文件删除
        File f=new File(fileName);
        f.delete();
        template.delete();
        tmp.delete();
    }

    /**
     * @Description 生成唯一的文件名称
     * @return
     */
    private static String  getUniqueFileName(String ext){
        Random rnd = new Random();
        long curTime=System.currentTimeMillis();
        String fileName="poi_"+curTime+"_"+rnd.nextInt()+ext;
        return fileName;
    }

    private static void substitute(File zipfile, File tmpfile, String entry, OutputStream out) throws IOException {
        ZipFile zip = new ZipFile(zipfile);

        ZipOutputStream zos = new ZipOutputStream(out);

        @SuppressWarnings("unchecked")
        Enumeration<ZipEntry> en = (Enumeration<ZipEntry>) zip.entries();
        while (en.hasMoreElements()) {
            ZipEntry ze = en.nextElement();
            if(!ze.getName().equals(entry)){
                zos.putNextEntry(new ZipEntry(ze.getName()));
                InputStream is = zip.getInputStream(ze);
                copyStream(is, zos);
                is.close();
            }
        }
        zos.putNextEntry(new ZipEntry(entry));
        InputStream is = new FileInputStream(tmpfile);
        copyStream(is, zos);
        is.close();

        zos.close();
        zip.close();
    }

    private static void copyStream(InputStream in, OutputStream out) throws IOException {
        byte[] chunk = new byte[1024];
        int count;
        while ((count = in.read(chunk)) >=0 ) {
            out.write(chunk,0,count);
        }
    }

    private static Map<String, XSSFCellStyle> createStyles(XSSFWorkbook wb){
        Map<String, XSSFCellStyle> styles = new HashMap<String, XSSFCellStyle>();
        XSSFDataFormat fmt = wb.createDataFormat();

        XSSFCellStyle style1 = wb.createCellStyle();
        style1.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
        style1.setDataFormat(fmt.getFormat("0.0%"));
        styles.put("percent", style1);

        XSSFCellStyle style2 = wb.createCellStyle();
        style2.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        style2.setDataFormat(fmt.getFormat("0.0X"));
        styles.put("coeff", style2);

        XSSFCellStyle style3 = wb.createCellStyle();
        style3.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
        style3.setDataFormat(fmt.getFormat("$#,##0.00"));
        styles.put("currency", style3);

        XSSFCellStyle style4 = wb.createCellStyle();
        style4.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
        style4.setDataFormat(fmt.getFormat("mmm dd"));
        styles.put("date", style4);

        XSSFCellStyle style5 = wb.createCellStyle();
        XSSFFont headerFont = wb.createFont();
        headerFont.setBold(true);
        style5.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style5.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style5.setFont(headerFont);
        styles.put("header", style5);

        return styles;
    }

    private  void generate(Writer out, Map<String, XSSFCellStyle> styles,List<Map<String,Object>> data,Map<String,String>fields,String fileEncoding) throws Exception {
        SpreadsheetWriter sw = new SpreadsheetWriter(out,fileEncoding);
        sw.beginSheet();

        //insert header row
        sw.insertRow(0);
        int styleIndex = styles.get("header").getIndex();
        if(fields!=null&&fields.size()>0){
            for(int j=0;j<fields.keySet().size();j++){
                String t=fields.keySet().toArray()[j].toString();
                sw.createCell(j,fields.get(t),styleIndex);
            }
        }
        sw.endRow();

        if(data!=null&&data.size()>0){
            for(int i=0;i<data.size();i++){
                sw.insertRow(i+1);
                Map<String,Object> m=data.get(i);
                if(fields!=null&&!fields.isEmpty()){
                    for(int j=0;j<fields.keySet().size();j++){
                        String t=fields.keySet().toArray()[j].toString();
                        if(m.get(t)!=null){
                            String v=(m.get(t)).toString();
                            sw.createCell(j,v);
                        }else{
                            sw.createCell(j,"");
                        }

                    }
                }

                sw.endRow();

            }
        }
        sw.endSheet();
    }

    public static class SpreadsheetWriter {
        private final Writer _out;
        private int _rownum;
        private String fileEncoding="GBK";

        public SpreadsheetWriter(Writer out,String fileEncode){
            _out = out;
            fileEncoding=fileEncode;
        }

        public void beginSheet() throws IOException {
            _out.write("<?xml version=\"1.0\" encoding=\""+fileEncoding+"\"?>" +
                    "<worksheet xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\">" +
                    "xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\" mc:Ignorable=\"x14ac\" xmlns:x14ac=\"http://schemas.microsoft.com/office/spreadsheetml/2009/9/ac\">");
//                    " <dimension ref=\"A1:D1\"/>"+
//                    "<sheetViews><sheetView workbookViewId=\"0\"><selection sqref=\"A1:XFD1\"/></sheetView></sheetViews>"+
//                   "<sheetFormatPr defaultRowHeight=\"13.5\" x14ac:dyDescent=\"0.15\"/> ");

            _out.write("<sheetData>\n");
        }

        public void endSheet() throws IOException {
            _out.write("</sheetData>");
//            _out.write("<phoneticPr fontId=\"1\" type=\"noConversion\"/><pageMargins left=\"0.7\" right=\"0.7\" top=\"0.75\" bottom=\"0.75\" header=\"0.3\" footer=\"0.3\"/><pageSetup paperSize=\"0\" orientation=\"portrait\" horizontalDpi=\"0\" verticalDpi=\"0\" copies=\"0\"/>");
            _out.write("</worksheet>");
        }

        /**
         * Insert a new row
         *
         * @param rownum 0-based row number
         */
        public void insertRow(int rownum) throws IOException {
            _out.write("<row r=\""+(rownum+1)+"\">\n");
            this._rownum = rownum;
        }

        /**
         * Insert row end marker
         */
        public void endRow() throws IOException {
            _out.write("</row>\n");
        }

        public void createCell(int columnIndex, String value, int styleIndex) throws IOException {
            String ref = new CellReference(_rownum, columnIndex).formatAsString();
            _out.write("<c r=\""+ref+"\" t=\"inlineStr\"");
            if(styleIndex != -1) _out.write(" s=\""+styleIndex+"\"");
            _out.write(">");
            _out.write("<is><t>"+ XMLEncoder.encode(value)+"</t></is>");
            _out.write("</c>");
        }

        public void createCell(int columnIndex, String value) throws IOException {
            createCell(columnIndex, value, -1);
        }

        public void createCell(int columnIndex, double value, int styleIndex) throws IOException {
            String ref = new CellReference(_rownum, columnIndex).formatAsString();
            _out.write("<c r=\""+ref+"\" t=\"n\"");
            if(styleIndex != -1) _out.write(" s=\""+styleIndex+"\"");
            _out.write(">");
            _out.write("<v>"+value+"</v>");
            _out.write("</c>");
        }

        public void createCell(int columnIndex, double value) throws IOException {
            createCell(columnIndex, value, -1);
        }

        public void createCell(int columnIndex, Calendar value, int styleIndex) throws IOException {
            createCell(columnIndex, DateUtil.getExcelDate(value, false), styleIndex);
        }
    }

}
