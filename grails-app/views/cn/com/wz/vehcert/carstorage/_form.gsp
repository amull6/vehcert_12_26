
    <div  class="om-grid om-widget om-widget-content">
        <div class="bDiv" style="width: auto;">
            <table id="grid" style="" cellpadding="0" cellspacing="0" border="0">
            <tbody>
            <tr>
                <td align="left"><div style="text-align: left; width: 95px;" class="om-resizable">公告类型</div></td>
                <td align="left"  abbr="city" class="col1"><div align="left" class="innerCol ">
                    %{--<g:select style="width:510px;" name="carStorageType" from="${['0':'汽车公告','1':'农用车公告']}"  value="${carStorage?.carStorageType}" optionKey="key" optionValue="value"></g:select>--}%
                    <g:select style="width:200px;" name="carStorageType" from="${['0':'汽车公告','1':'农用车公告']}"  value="${carStorage?.carStorageType}" optionKey="key" optionValue="value"></g:select>
                </div></td>
                <td align="left"><div style="text-align: left;; width: 95px; " class="om-resizable">油耗类型</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Yhlx" type="text" value="${carStorage?.veh_Yhlx}" style="width:200px;"/>
                </div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; width: 95px;" class="om-resizable">车辆制造企业名称</div></td>
                <td align="left"  abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Clzzqymc" type="text" value="${carStorage?.veh_Clzzqymc}" style="width:200px;" />
                </div></td>
                <td align="left"><div style="text-align: left; " class="om-resizable">产品公告号</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Cpggh" type="text" value="${carStorage?.veh_Cpggh}" style="width:200px;"/>
                </div></td>
            </tr>
            <tr>

                <td align="left"><div style="text-align: left; " class="om-resizable">公告生效日期</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Ggsxrq" type="text" value="${carStorage?.veh_Ggsxrq}" style="width:200px;"/>
                </div></td>
                <td align="left"><div style="text-align: left; " class="om-resizable">批次</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Ggpc" type="text" value="${carStorage?.veh_Ggpc}" style="width:200px;"/>
                </div></td>

            </tr>
            <tr>

                <td align="left"><div style="text-align: left; " class="om-resizable">产品ID</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_cpid" type="text" value="${carStorage?.veh_cpid}" style="width:200px;"/>
                </div></td>
                <td align="left"><div style="text-align: left;" class="om-resizable">企业ID</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol " style="width:200px">
                    <input name="veh_Qyid" type="text" value="${carStorage?.veh_Qyid}" style="width:200px;"/>
                </div></td>

            </tr>

            <tr>

                <td align="left"><div style="text-align: left; width: 80px;" class="om-resizable">车辆分类</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol " style="width:200px">
                    <input  name="veh_Clfl" type="text" value="${carStorage?.veh_Clfl}" style="width:200px;"/>
                </div></td>
                <td align="left"><div style="text-align: left; width: 80px;" class="om-resizable">VIN第四位</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol " style="width:200px">
                    <input  name="veh_VinFourBit" type="text" value="${carStorage?.veh_VinFourBit}" style="width:200px;"/>
                </div></td>
            </tr>

            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">车辆名称</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Clmc" type="text" value="${carStorage?.veh_Clmc}" style="width:200px;"/>
                </div></td>

                <td align="left"><div style="text-align: left; " class="om-resizable">车辆品牌</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Clpp" type="text" value="${carStorage?.veh_Clpp}" style="width:200px;"/>
                </div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">车辆型号</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input id="veh_Clxh" name="veh_Clxh" value="${carStorage?.veh_Clxh}" type="text" style="width:200px;"/>
                </div></td>

                <td align="left"><div style="text-align: left; " class="om-resizable">车身颜色</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Csys" type="text" value="${carStorage?.veh_Csys}" style="width:200px;"/>
                </div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">底盘型号</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input id="veh_Dpxh" name="veh_Dpxh" type="text" value="${carStorage?.veh_Dpxh}" style="width:200px;"/>
                </div></td>

                <td align="left"><div style="text-align: left; " class="om-resizable">底盘ID</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Dpid" type="text" value="${carStorage?.veh_Dpid}" style="width:200px;"/>
                </div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">发动机型号</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Fdjxh" type="text"  value="${carStorage?.veh_Fdjxh}" style="width:200px;"/>
                </div></td>

                <td align="left"><div style="text-align: left; " class="om-resizable">燃料种类</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Rlzl" type="text" value="${carStorage?.veh_Rlzl}" style="width:200px;"/></div>
                </td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">排量</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Pl" type="text" value="${carStorage?.veh_Pl}" style="width:200px;"/>
                </div></td>

                <td align="left"><div style="text-align: left; " class="om-resizable">功率</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Gl" type="text" value="${carStorage?.veh_Gl}" style="width:200px;"/>
                </div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">转向形式</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Zxxs" type="text" value="${carStorage?.veh_Zxxs}" style="width:200px;"/>
                </div></td>

                <td align="left"><div style="text-align: left; " class="om-resizable">前轮距</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Qlj" type="text" value="${carStorage?.veh_Qlj}" style="width:200px;"/>
                </div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">后轮距</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Hlj" type="text" value="${carStorage?.veh_Hlj}" style="width:200px;"/></div>
                </td>

                <td align="left"><div style="text-align: left; " class="om-resizable">轮胎数</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Lts" type="text" value="${carStorage?.veh_Lts}" style="width:200px;"/></div>
                </td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">轮胎规格</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Ltgg" type="text" value="${carStorage?.veh_Ltgg}" style="width:200px;"/>
                </div></td>

                <td align="left"><div style="text-align: left; " class="om-resizable">钢板弹簧片数</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Gbthps" type="text" value="${carStorage?.veh_Gbthps}" style="width:200px;"/></div>
                </td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">轴距</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Zj" type="text" value="${carStorage?.veh_Zj}" style="width:200px;"/></div>
                </td>

                <td align="left"><div style="text-align: left; " class="om-resizable">轴荷</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Zh" type="text" value="${carStorage?.veh_Zh}" style="width:200px;"/>
                </div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left;" class="om-resizable">轴数</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Zs" type="text" value="${carStorage?.veh_Zs}" style="width:200px;"/></div>
                </td>

                <td align="left"><div style="text-align: left; " class="om-resizable">外廓长</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Wkc" type="text"  value="${carStorage?.veh_Wkc}" style="width:200px;"/></div>
                </td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">外廓宽</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Wkk" type="text" value="${carStorage?.veh_Wkk}" style="width:200px;"/></div>
                </td>
                <td align="left"><div style="text-align: left; " class="om-resizable">外廓高</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Wkg" type="text"  value="${carStorage?.veh_Wkg}" style="width:200px;"/></div>
                </td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">货箱内部长</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Hxnbc" type="text" value="${carStorage?.veh_Hxnbc}" style="width:200px;"/>
                </div></td>
                <td align="left"><div style="text-align: left; " class="om-resizable">货箱内部宽</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Hxnbk" type="text" value="${carStorage?.veh_Hxnbk}" style="width:200px;"/></div>
                </td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">货箱内部高</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Hxnbg" type="text"  value="${carStorage?.veh_Hxnbg}" style="width:200px;"/>
                </div></td>
                <td align="left"><div style="text-align: left; " class="om-resizable">最高车速</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Zgcs" type="text"  value="${carStorage?.veh_Zgcs}" style="width:200px;"/></div>
                </td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">总质量</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Zzl" type="text"  value="${carStorage?.veh_Zzl}" style="width:200px;"/></div>
                </td>
                <td align="left"><div style="text-align: left; " class="om-resizable">额定载质量</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Edzzl" type="text"  value="${carStorage?.veh_Edzzl}" style="width:200px;"/></div>
                </td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">整备质量</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Zbzl" type="text" value="${carStorage?.veh_Zbzl}" style="width:200px;"/></div>
                </td>
                <td align="left"><div style="text-align: left; " class="om-resizable">载质量利用系数</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Zzllyxs" type="text"  value="${carStorage?.veh_Zzllyxs}" style="width:200px;"/></div>
                </td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">准牵引总质量</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Zqyzzl" type="text" value="${carStorage?.veh_Zqyzzl}" style="width:200px;"/></div></td>
                <td align="left"><div style="text-align: left; " class="om-resizable">额定载客</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Edzk" type="text" value="${carStorage?.veh_Edzk}"  style="width:200px;"/></div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">半挂车按座</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Bgcazzdyxzzl" value="${carStorage?.veh_Bgcazzdyxzzl}" type="text" style="width:200px;"/></div>
                </td>
                <td align="left"><div style="text-align: left; " class="om-resizable">驾驶室准乘人数</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Jsszcrs" type="text"  value="${carStorage?.veh_Jsszcrs}"  style="width:200px;"/></div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">最大净功率</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_zdjgl" type="text" value="${carStorage?.veh_zdjgl}"  style="width:200px;"/></div></td>
                <td align="left"><div style="text-align: left; " class="om-resizable">后制动方式</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Hzdfs" type="text" value="${carStorage?.veh_Hzdfs}"  style="width:200px;"/></div></td>
            </tr>

            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">接近离去角</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_jjlqj" type="text" value="${carStorage?.veh_jjlqj}" style="width:200px;"/></div>
                </td>
                <td align="left"><div style="text-align: left; " class="om-resizable">前悬后悬</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_qxhx" style="width:200px;"  type="text"  value="${carStorage?.veh_qxhx}"/>
                </div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left;" class="om-resizable">底盘企业</div></td>
                <td align="left" colspan="3" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_dpqy" type="text" value="${carStorage?.veh_dpqy}"  style="width:510px;"/></div>
                </td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">企业标准</div></td>
                <td align="left" colspan="3" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Qybz" type="text" value="${carStorage?.veh_Qybz}" style="width:510px;"/></div>
                </td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">产品生产地址</div></td>
                <td align="left" abbr="city" colspan="3" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Cpscdz" type="text" value="${carStorage?.veh_Cpscdz}" style="width:510px;"/></div>
                </td></tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">企业其它信息</div></td>
                <td align="left" colspan="3" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Qyqtxx"  type="text" value="${carStorage?.veh_Qyqtxx}"  style="width:510px;"/></div>
                </td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">排放标准</div></td>
                <td align="left" colspan="3" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Pfbz" type="text" value="${carStorage?.veh_Pfbz}"  style="width:510px;"/></div>
                </td>
            </tr>

            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">驾驶室</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Jss" type="text"  value="${carStorage?.veh_Jss}"  style="width:200px;"/></div>
                </td>
                <td align="left"><div style="text-align: left; " class="om-resizable">驾驶室类型</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Jsslx" type="text" value="${carStorage?.veh_Jsslx}" style="width:200px;"/></div>
                </td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">油耗</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Yh" type="text"   value="${carStorage?.veh_Yh}" style="width:200px;"/>
                </div></td>
                <td align="left"><div style="text-align: left; " class="om-resizable">反光标示企业</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_fgbsqy" type="text" value="${carStorage?.veh_fgbsqy}" style="width:200px;"/>
                </div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">反光标示商标</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_fgbssb" type="text" value="${carStorage?.veh_fgbssb}" style="width:200px;"/></div>
                </td>
                <td align="left"><div style="text-align: left; " class="om-resizable">反光标示型号</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_fgbsxh" type="text"  value="${carStorage?.veh_fgbsxh}" style="width:200px;"/>
                </div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">右45度照片</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_y45pic" type="text" value="${carStorage?.veh_y45pic}"  style="width:200px;"/></div></td>
                <td align="left"><div style="text-align: left; " class="om-resizable">正后照片</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_zhpic" type="text" value="${carStorage?.veh_zhpic}" style="width:200px;"/></div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">防护照片</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_fhpic" type="text" value="${carStorage?.veh_fhpic}" style="width:200px;"/></div></td>
                <td align="left"><div style="text-align: left; " class="om-resizable">选择照片1</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_pic1" type="text" value="${carStorage?.veh_pic1}"  style="width:200px;"/></div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">选择照片2</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_pic2" type="text" value="${carStorage?.veh_pic2}"    style="width:200px;"/></div></td>
                <td align="left"><div style="text-align: left; " class="om-resizable">车辆状态信息</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Clztxx" type="text" value="${carStorage?.veh_Clztxx}"  style="width:200px;"/>
                </div></td>
            </tr>
            <tr>

                <td align="left"><div style="text-align: left; " class="om-resizable">配置序列号</div></td>
                <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol ">
                    <input name="veh_pzxlh"  type="text" value="${carStorage?.veh_pzxlh}" style="width:510px;"/></div>
                </td>
            </tr>
            <tr>

                <td align="left"><div style="text-align: left; " class="om-resizable">公告唯一号</div></td>
                <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol ">
                    <input name="car_storage_no"  type="text" value="${carStorage?.car_storage_no}" style="width:510px;"/></div>
                </td>
            </tr>
            <tr>

                <td align="left"><div style="text-align: left; " class="om-resizable">其它</div></td>
                <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol ">
                    <input name="veh_other"  type="text" value="${carStorage?.veh_other}" style="width:510px;"/></div>
                </td>
            </tr>
            <tr>

                <td align="left"><div style="text-align: left; " class="om-resizable">备注</div></td>
                <td align="left" colspan='3' abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Bz" type="text" value="${carStorage?.veh_Bz}" style="width:510px;"/>
                </div></td>
            </tr>


            </tbody>
            </table>
    </div>
    </div>
    <script>
        $("body").parent().parent().attr('height',$("body").parent().parent().height()-10);
    </script>
