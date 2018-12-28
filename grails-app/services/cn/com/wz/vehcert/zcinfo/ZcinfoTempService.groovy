package cn.com.wz.vehcert.zcinfo
import cn.com.wz.parent.system.user.User
import cn.com.wz.parent.ConstantsUtil
import javax.servlet.http.HttpSession
class ZcinfoTempService {
def sqlService
    def serviceMethod() {

    }


    /**
     *@Descriptions 查询当前插入数据是否存在表中
     *@Auther zouq
     *@createTime
     */
    def boolean isFindByID(def id)   ///查询ID信息
    {
        if (!id){
            return false;
        }
        def result = sqlService.executeList("SELECT * FROM WZH_ZCINFO_TEMP WHERE ZCINFOID like '"+id+"'")
        return (result.size()>0) ? false : true
    }

    /**
     *@Descriptions 更新、插入下载后的合格证信息
     *@Auther zouq
     *@createTime
     */
    def addZCInfoToZCInfoTemp(def id,HttpSession session)
    {
        if (isFindByID(id))
        {
            User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
            def result = sqlService.executeUpdate("INSERT INTO WZH_ZCINFO_TEMP (ID,ZCINFOID,CREATE_TIME," +
                    "CREATER_ID,USER_NAME) VALUES ('"+id+"','"+id+"',to_char(Sysdate,'yyyy-mm-dd hh24:mi'),'"+loginUser.id+"','"+loginUser.userName+"')")
            return (result>0) ? "合格证信息下载成功!" : "合格证信息下载失败!"
        } else{
            def result = sqlService.executeUpdate("UPDATE WZH_ZCINFO_TEMP SET CREATE_TIME = to_char(Sysdate,'yyyy-mm-dd hh24:mi') WHERE ZCINFOID='"+id+"'");
            return (result>0) ? "合格证信息下载成功!" : "合格证信息下载失败!"
        }
    }
    /**
     *@Descriptions 查询和导出的公用方法
     *@Auther liuly
     *@createTime
     * @Update TODO 使用sql语句方式查询 ZCInfoTemp.findAll('',[max: 10, offset: 20])
     */
    def search(params){

        def distributor=params.distributor?.id
        def userR=params.user?.id
        def user=[]
        if (userR==''){
            def celU={
                organs{
                    if(params.distributor){
                        eq('id',distributor)
                    }
                }
            }
            user=User.createCriteria().list(celU)
        }else{
            def celU={
                organs{
                    if(params.distributor){
                        eq('id',distributor)
                    }
                }
                userDetail{
                    if(params.user){
                        eq('id',userR)
                    }
                }
            }
            user=User.createCriteria().list(celU)
        }
        //重组id
        def downList=[]
        user.each{
            downList.add(it.id)
        }
        def sta = params.firstTime+" 00:00:00"
        def end = params.secondTime+" 23:59:59"
        def cel={
            if (params.veh_Zchgzbh){
                like('veh_Zchgzbh',"%${params.veh_Zchgzbh}%")
            }
            if(params.firstTime){
                ge('insertTime',sta)
            }
            if(params.secondTime){
                le('insertTime',end)
            }
            if(params.distributor&&distributor){
                if (user.size()>0){
                    inList('user_down',downList)
                }
            }

        }
        def rows=ZCInfoTemp.createCriteria().list(params,cel)
        def lst=[]
        int i=1

        rows?.each {u->
            def m=[:]
            m.id=u.id
            m.veh_Type=u.veh_Type
            m.veh_Zchgzbh=u.veh_Zchgzbh
            m.veh_Dphgzbh=u.veh_Dphgzbh
            m.veh_Fzrq=u.veh_Fzrq
            m.veh_Clzzqymc=u.veh_Clzzqymc
            m.veh_Qyid=u.veh_Qyid
            m.veh_Clfl=u.veh_Clfl
            m.veh_Clmc=u.veh_Clmc
            m.veh_Clpp=u.veh_Clpp
            m.veh_Clxh=u.veh_Clxh
            m.veh_Csys=u.veh_Csys
            m.veh_Dpxh=u.veh_Dpxh
            m.veh_Dpid=u.veh_Dpid
            m.veh_Clsbdh=u.veh_Clsbdh
            m.veh_Cjh=u.veh_Cjh
            m.veh_Fdjh=u.veh_Fdjh

            m.veh_Fdjxh=u.veh_Fdjxh
            m.veh_Rlzl=u.veh_Rlzl
            m.veh_Pl=u.veh_Pl
            m.veh_Gl=u.veh_Gl
            m.veh_zdjgl=u.veh_zdjgl
            m.veh_Zxxs=u.veh_Zxxs
            m.veh_Qlj=u.veh_Qlj
            m.veh_Hlj=u.veh_Hlj
            m.veh_Lts=u.veh_Lts
            m.veh_Ltgg=u.veh_Ltgg
            m.veh_Gbthps=u.veh_Gbthps
            m.veh_Zj=u.veh_Zj
            m.veh_Zh=u.veh_Zh
            m.veh_Zs=u.veh_Zs
            m.veh_Wkc=u.veh_Wkc
            m.veh_Wkk=u.veh_Wkk
            m.veh_Wkg=u.veh_Wkg
            m.veh_Hxnbc=u.veh_Hxnbc
            m.veh_Hxnbk=u.veh_Hxnbk
            m.veh_Hxnbg=u.veh_Hxnbg
            m.veh_Zzl=u.veh_Zzl
            m.veh_Edzzl=u.veh_Edzzl
            m.veh_Zbzl=u.veh_Zbzl
            m.veh_Zzllyxs=u.veh_Zzllyxs
            m.veh_Zqyzzl=u.veh_Zqyzzl
            m.veh_Edzk=u.veh_Edzk
            m.veh_Bgcazzdyxzzl=u.veh_Bgcazzdyxzzl
            m.veh_Jsszcrs=u.veh_Jsszcrs
            m.veh_Qzdfs=u.veh_Qzdfs
            m.veh_Hzdfs=u.veh_Hzdfs
            m.veh_Qzdczfs=u.veh_Qzdczfs
            m.veh_Cpggh=u.veh_Cpggh
            m.veh_Ggsxrq=u.veh_Ggsxrq
            m.veh_Zzbh=u.veh_Zzbh
            m.veh_Dywym=u.veh_Dywym
            m.veh_Zgcs=u.veh_Zgcs
            m.veh_Clzzrq=u.veh_Clzzrq
            m.veh_Bz=u.veh_Bz
            m.veh_Qybz=u.veh_Qybz
            m.veh_Cpscdz=u.veh_Cpscdz
            m.veh_Qyqtxx=u.veh_Qyqtxx
            m.veh_Pfbz=u.veh_Pfbz
            m.veh_Clztxx=u.veh_Clztxx
            m.veh_Jss=u.veh_Jss
            m.veh_Jsslx=u.veh_Jsslx
            m.veh_Zchgzbh_0=u.veh_Zchgzbh_0
            m.used=u.used
            m.used2=u.used2
            m.upload=u.upload
            m.veh_Yh=u.veh_Yh
            m.veh_VinFourBit=u.veh_VinFourBit
            m.veh_Ggpc=u.veh_Ggpc

            m.createTime=u.createTime
            def userC=User.get(u.createrId)
            if (userC){
                m.createrId=userC.userDetail.realName
            }else{
                m.createrId=''
            }
            m.updateTime=u.updateTime
            def userU=User.get(u.updaterId)
            if (userU){
                m.updaterId=userU.userDetail.realName
            }else{
                m.updaterId=''
            }
            def userD=User.get(u.user_down)
            if (userD){
                m.user_down=userD.userDetail.realName
            }else{
                m.user_down=''
            }
            m.insertTime = u.insertTime
            i++;
            lst.add(m)

        }
        def result = [rows:lst,total:rows.totalCount]
        return result
    }
}
