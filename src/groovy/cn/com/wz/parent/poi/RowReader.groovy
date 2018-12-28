package cn.com.wz.parent.poi

/**
 * @Description:
 * @Create: 13-9-12 下午5:26   huxx
 * @Update:
 * @To change this template use File | Settings | File Templates.
 */
class RowReader {
    public void getRows(int sheetIndex, int curRow, List<String> rowlist) {
        // TODO Auto-generated method stub
        System.out.print(curRow+" ");
        for (int i = 0; i < rowlist.size(); i++) {
            System.out.print(rowlist.get(i) + " ");
        }
        System.out.println();
    }

}
