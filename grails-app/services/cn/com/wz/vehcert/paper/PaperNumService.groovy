package cn.com.wz.vehcert.paper
import cn.com.wz.parent.system.user.User
class PaperNumService {
    def sqlService
    def paperNumRows(params){
        def dealerNum=''
        if (params.dealerNum){
            dealerNum=params.dealerNum
        }
        def dealerName=''
        if (params.dealerName){
            dealerName=params.dealerName
        }
        //当选则全部，空，已打印，已回收时
        def flag1=true //显示已回收
        def flag2=true //显示已打印
        def flag3=true  //显示空
        if (params.status=='1'){
            flag3=false
            flag2=false
        }
        if (params.status=='2'){
            flag1=false
            flag3=false
        }
        if (params.status=='3'){
            flag1=false
            flag2=false
        }
        def rows=[]
        def temp=[]
        //已回收
        if(flag1){
            StringBuffer sql_sbf=new StringBuffer()
            sql_sbf = new StringBuffer("SELECT T.DEALER_ID,T.DEALER_NAME,count(*) as NUM FROM TBL_PAPER_REVERT T,(SELECT V.DEALER_NAME FROM TBL_PAPER_REVERT V GROUP BY V.DEALER_NAME) C WHERE T.DEALER_NAME=C.DEALER_NAME  and T.DEALER_ID LIKE '%"+dealerNum+"%' and T.DEALER_NAME LIKE '%"+dealerName+"%' ");
            if (!params.firstTime&&!params.lastTime){
                sql_sbf =sql_sbf
            }else if (params.firstTime==' '&&params.lastTime==' '){
                sql_sbf =sql_sbf
            } else{
                def firstTime= params.firstTime.toString()
                def lastTime= params.lastTime.toString()
                sql_sbf.append("    and to_date(T.RECOVER_TIME,'yyyy-MM-dd HH24-mi-ss') between to_date('"+firstTime+"','yyyy-MM-dd HH24-mi-ss') and to_date('"+lastTime+"','yyyy-MM-dd HH24-mi-ss') ")

            }
            def type='全部类型'
            if (params.type&&params.type!='all'){
                  if (params.type=='0'){
                      type = '汽车整车'
                      sql_sbf.append(' and T.TYPE=0 ')
                  }else if (params.type=='1'){
                      sql_sbf.append(' and T.TYPE=1 ')
                      type = '农用车整车'
                  }else{
                      sql_sbf.append(' and T.TYPE=2 ')
                      type = '二类底盘'
                  }
            }
            sql_sbf.append("group by T.DEALER_NAME,T.DEALER_ID");
            def list = sqlService.executeList(sql_sbf.toString())

            if (list.size()>0){
                def listResult=list.unique()
                listResult.each { r->
                    def m=[:]
                    m.dealerNum=r.DEALER_ID
                    m.dealerName=r.DEALER_NAME
                    m.type=type
                    m.num=r.NUM
                    m.status='已回收'
                    m.note='已选时间段内实际回收的合格证数量'
                    rows.add(m)
                }
            }
        }

        //已打印的合格证
        if (flag2){
            StringBuffer sql_sbfZ=new StringBuffer()
            sql_sbfZ = new StringBuffer("select t.user_down,count(*) as num from wzh_zcinfo_temp t,(select z.user_down from wzh_zcinfo_temp z group by z.user_down) c where t.user_down=c.user_down  " +
                    "and t.user_down in (SELECT id from sys_user s where s.user_name LIKE '%"+dealerNum+"%' and s.id in (select user_id from sys_userdetail d where d.real_name  LIKE '%"+dealerName+"%')) ");
            if (!params.firstTime&&!params.lastTime){
                sql_sbfZ=sql_sbfZ
            }else if (params.firstTime==' '&&params.lastTime==' '){
                sql_sbfZ = sql_sbfZ
            } else{
                def firstTime= params.firstTime.toString()
                def lastTime= params.lastTime.toString()
               sql_sbfZ.append(" and to_date(t.create_time,'yyyy-MM-dd HH24-mi-ss') between to_date('"+firstTime+"','yyyy-MM-dd HH24-mi-ss') and to_date('"+lastTime+"','yyyy-MM-dd HH24-mi-ss') ")
            }
            //打印错误的合格证纸张
            StringBuffer sql_sbfE=new StringBuffer()
            sql_sbfE = new StringBuffer("SELECT T.DEALER_NUM,T.DEALER_NAME,T.num as numT  FROM TBL_PAPER_ERROR T,(SELECT V.DEALER_NAME FROM TBL_PAPER_ERROR V GROUP BY V.DEALER_NAME) C WHERE T.DEALER_NAME=C.DEALER_NAME  and T.DEALER_NUM LIKE '%"+dealerNum+"%' and T.DEALER_NAME LIKE '%"+dealerName+"%' ");
            def type='全部类型'
            if (params.type&&params.type!='all'){
                if (params.type=='0'){
                    type = '汽车整车'
                    sql_sbfZ.append(" and T.VEH_TYPE=0 ")
                    sql_sbfE.append(" and T.TYPE=0 ")
                }else if (params.type=='1'){
                    sql_sbfZ.append(" and T.VEH_TYPE=1 AND T.VEH_CLZTXX='QX' ")
                    sql_sbfE.append(" and T.TYPE=1 ")
                    type = '农用车整车'
                }else{
                    sql_sbfZ.append(" and T.VEH_TYPE=1 AND T.VEH_CLZTXX='DP' ")
                    sql_sbfE.append(" and T.TYPE=2 ")
                    type = '二类底盘'
                }
            }
            sql_sbfZ.append(' group by t.user_down');
            def listZ = sqlService.executeList(sql_sbfZ.toString())
            def listE = sqlService.executeList(sql_sbfE.toString())
            if (listZ.size()>0){
                def listResult=listZ.unique()
                listResult.each { r->
                    def user=User.get(r.user_down)
                    int b=0
                    int c=0
                    listE.each {t->
                        if (user.userName==t.DEALER_NUM){
                            if (t.numT>0){
                                c+=t.numT
                            }else if (t.numT<0){
                                b+=t.numT
                            }
                        }
                    }
                    def total=r.num+b+c
                    temp.add(["DEALER_NUM":user.userName?.toString(),"num":total ])

                    def z="${r.num}+(${b})+${c}=${total}"
                    def m=[:]
                    m.dealerNum=user.userName
                    m.dealerName=user.userDetail.realName
                    m.type=type
                    m.num=z
                    m.status='已打印'
                    m.note='已选时间段内打印的合格证数量(下载数+下载未打印数+下载一次打印多次=总的已打印数)'
                    rows.add(m)
                }
            }
        }
        //空合格证
        if (flag3) {
            StringBuffer sql_sbfP=new StringBuffer()
            sql_sbfP = new StringBuffer("SELECT T.DEALER_NUM,T.DEALER_NAME,SUM(T.nums) as num FROM TBL_PAPER T,(SELECT V.DEALER_NUM FROM TBL_PAPER V GROUP BY V.DEALER_NUM) C WHERE T.DEALER_NUM=C.DEALER_NUM  and CONFIRM_TIME is not null and T.DEALER_NUM LIKE '%"+dealerNum+"%' and T.DEALER_NAME LIKE '%"+dealerName+"%' ");
            def type='全部类型'
            if (params.type&&params.type!='all'){
                if (params.type=='0'){
                    type = '汽车整车'
                    sql_sbfP.append(' and T.TYPE=0 ')
                }else if (params.type=='1'){
                    sql_sbfP.append(' and T.TYPE=1 ')
                    type = '农用车整车'
                }else{
                    sql_sbfP.append(' and T.TYPE=2 ')
                    type = '二类底盘'
                }
            }
            sql_sbfP.append(' group by T.DEALER_NAME,T.DEALER_NUM')
            def listP = sqlService.executeList(sql_sbfP.toString())
            if (listP.size()>0){
                listP.each{r->
                    temp.each {t->
                        if (r.DEALER_NUM==t.DEALER_NUM){
                            r.num=r.num-t.num
                        }
                    }
                }
            }

            listP.each {r->
                def m=[:]
                m.dealerNum=r.DEALER_NUM
                m.dealerName=r.DEALER_NAME
                m.type=type
                m.num=r.num
                m.status='空'
                m.note='截止到当前时间经销商手中的空合格证数量'
                rows.add(m)
            }
        }
        return rows
    }
}
