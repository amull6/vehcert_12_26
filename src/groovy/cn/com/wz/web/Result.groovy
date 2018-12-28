package cn.com.wz.web

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 *Description 车辆维修网站调用VIN查询接口返回的信息Domian
 *CreateTime by zhuwei 2016-04-13
 * Date: 16-4-13
 * Time: 上午9:08
 * To change this template use File | Settings | File Templates.
 */
class Result {
    String flag
    String message

    String  body_color     //车身颜色
    String  car_name        //车辆名称
    String  carvin    //VIN
    String  car_type  //底盘型号
    String  displacement //排量
    String  engine_num //发动机号
    String  engine_type //发动机型号
    String  fuel    //燃料
    String  manufacture  //制造企业名称
    String  power  //功率
    String  tires_num //轮胎数
    String vehicle_brand //车辆品牌
    String vehicle_manufacturing_date // 车辆制造日期
    String vehicle_model //车辆型号
    String wheel_base //轴距
    String tire_size  //轮胎规格
    String text1       //预留字段1
    String text2     //预留字段2
}
