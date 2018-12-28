package cn.com.wz.parent.home

/**
 * @Description:
 * @Create: 13-8-19 上午9:22   huxx
 * @Update:
 * @To change this template use File | Settings | File Templates.
 */
class HomeController {
    def toHome(){
        render(view: "/index",model: [:])
    }
}
