package cn.com.wz.vehcert.environment
/**
 * @Description环保信息VIN数据上传Domain
 * CreateTime 2017-01-06 by zhuwei
 *@Update 2017-01-10 by zhuwei 增加车辆型号、发动机型号、汽车分类三个参数
 */

class EnvirUpload {
    String id
    String en_clsbdh //  车辆识别代码
    String en_xxgkbh  //信息公开号
    String en_sb = '飞碟'      //商标
    String en_sccdz = '山东省五莲县潮河镇驻地'  //生产厂地址
    String en_fdjh    //发动机编号
    String en_scrq   //生产日期
    String en_ccrq   //出厂日期
    String en_ccsy   //出厂试验
    String en_ccjl = '合格'   //出厂试验结论
    String en_gfwz   //官方网站
    String en_fdjsb  //发动机商标，只有重型车该字段才必填，取自发动机厂牌
    String en_fdjsccdz  //发动机生产厂地址，只有重型车才必填，取自发动机生产地址
    String create_time //创建时间，这个数据没有编辑过程，只需要保存创建人和创建时间即可
    String creater_id //创建人id
    String type       //保存改环保清单数据类型，是重型柴油车还是轻型汽油车，还是其他，直接保存名称

    String en_clxh  //车辆型号
    String en_fdjxh   //发动机型号
    String en_qcfl   //汽车分类，N1、N2还是M1

    /**
     /**
     * 上传状态 0未上传 1 已上传 2上传失败
     */
    int status=0 ;

    static constraints = {
         en_clsbdh (nullable: false,unique: true)//  车辆识别代码,该字段是唯一－
         en_xxgkbh (nullable: true)  //信息公开号
         en_sb     (nullable: true)       //商标
         en_sccdz  (nullable: true)   //生产厂地址
         en_fdjh    (nullable: true)  //发动机编号
         en_scrq   (nullable: true)  //生产日期
         en_ccrq   (nullable: true)  //出厂日期
         en_ccsy   (nullable: true)  //出厂试验
         en_ccjl   (nullable: true)   //出厂试验结论
         en_gfwz   (nullable: true)  //官方网站
         en_fdjsb  (nullable: true)  //发动机商标，只有重型车该字段才必填，取自发动机厂牌
         en_fdjsccdz(nullable: true)  //发动机生产厂地址，只有重型车才必填，取自发动机生产地址
         create_time(nullable: true)  //创建时间，这个数据没有编辑过程，只需要保存创建人和创建时间即可
         creater_id (nullable: true) //创建人id
         type       (nullable: true)

        en_clxh     (nullable: true) //车辆型号
        en_fdjxh    (nullable: true)//发动机型号
        en_qcfl     (nullable: true)//汽车分类，N1、N2还是M1

        status      (nullable: true)
    }
   static mapping = {
       table 'WZH_EN_UPLOAD'
       id generator:'assigned', column:'ID'
       version false
       en_clsbdh (comment:'车辆识别代码')//  车辆识别代码,该字段是唯一－
       en_xxgkbh (comment:'信息公开号') //信息公开号
       en_sb     (comment:'商标')      //商标
       en_fdjh   (comment:'发动机编号')      //发动机编号
       en_sccdz  (comment:'生产厂地址')  //生产厂地址
       en_scrq   (comment:'生产日期') //生产日期
       en_ccrq   (comment:'出厂日期') //出厂日期
       en_ccsy   (comment:'出厂试验') //出厂试验
       en_ccjl   (comment:'出厂试验结论')  //出厂试验结论
       en_gfwz   (comment:'官方网站') //官方网站
       en_fdjsb  (comment:'发动机商标') //发动机商标，只有重型车该字段才必填，取自发动机厂牌
       en_fdjsccdz(comment:'发动机生产厂地址') //发动机生产厂地址，只有重型车才必填，取自发动机生产地址
       create_time(comment:'创建时间') //创建时间，这个数据没有编辑过程，只需要保存创建人和创建时间即可
       creater_id (comment:'创建人id')//创建人id
       type       (comment:'环保清单类型')

       en_clxh     (comment:'车辆型号') //车辆型号
       en_fdjxh    (comment:'发动机型号')//发动机型号
       en_qcfl     (comment:'汽车分类')//汽车分类，N1、N2还是M1

       status      (comment:'状态')
   }

}
