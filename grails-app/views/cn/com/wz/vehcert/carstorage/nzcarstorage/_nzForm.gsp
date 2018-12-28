
    <div  class="om-grid om-widget om-widget-content">
        <div class="bDiv" style="width: auto;">
            <table id="grid" style="" cellpadding="0" cellspacing="0" border="0">
            <tbody>
            <tr>
                <td align="left"><div style="text-align: left; width: 95px;" class="om-resizable">公告类型</div></td>
                <td align="left"  abbr="city" class="col1"><div align="left" class="innerCol ">
                    %{--<g:select style="width:510px;" name="car_storage_type" from="${['0':'汽车公告','1':'农用车公告']}"  value="${carStorage?.car_storage_type}" optionKey="key" optionValue="value"></g:select>--}%
                    <g:select style="width:200px;" name="car_storage_type" from="${['0':'小拖','1':'中拖','2':'大拖','3':'玉米收','4':'稻麦机','5':'青贮机']}"  value="${nzCarStorage?.car_storage_type}" optionKey="key" optionValue="value"></g:select>
                </div></td>

                <td align="left"><div style="text-align: left;; width: 95px; " class="om-resizable">机械大类</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Jxdl" type="text" value="${nzCarStorage?.veh_Jxdl}" style="width:200px;"/>
                </div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; width: 95px;" class="om-resizable">燃料类型</div></td>
                <td align="left"  abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Rllx" type="text" value="${nzCarStorage?.veh_Rllx}" style="width:200px;" />
                </div></td>
                <td align="left"><div style="text-align: left; " class="om-resizable">机械小类别</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Jxxlb" type="text" value="${nzCarStorage?.veh_Jxxlb}" style="width:200px;"/>
                </div></td>
            </tr>
            <tr>

                <td align="left"><div style="text-align: left; " class="om-resizable">机械产品的主要参数</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Zycs" type="text" value="${nzCarStorage?.veh_Zycs}" style="width:200px;"/>
                </div></td>
                <td align="left"><div style="text-align: left; " class="om-resizable">排放阶段</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Pfjd" type="text" value="${nzCarStorage?.veh_Pfjd}" style="width:200px;"/>
                </div></td>

            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">车辆型号</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Clxh" type="text" value="${nzCarStorage?.veh_Clxh}" style="width:200px;"/>
                </div></td>
                <td align="left"><div style="text-align: left; " class="om-resizable">发动机型号</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Fdjxh" type="text" value="${nzCarStorage?.veh_Fdjxh}" style="width:200px;"/>
                </div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">类型</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Lx" type="text" value="${nzCarStorage?.veh_Lx}" style="width:200px;"/>
                </div></td>
                <td align="left"><div style="text-align: left; " class="om-resizable">功率</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Gl" type="text" value="${nzCarStorage?.veh_Gl}" style="width:200px;"/>
                </div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">排放标准</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Pfbz" type="text" value="${nzCarStorage?.veh_Pfbz}" style="width:200px;"/>
                </div></td>
                <td align="left"><div style="text-align: left; " class="om-resizable">机身颜色</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Jsys" type="text" value="${nzCarStorage?.veh_Jsys}" style="width:200px;"/>
                </div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">转向操纵形式</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Zxczxs" type="text" value="${nzCarStorage?.veh_Zxczxs}" style="width:200px;"/>
                </div></td>
                <td align="left"><div style="text-align: left; " class="om-resizable">准乘人数</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Zcrs" type="text" value="${nzCarStorage?.veh_Zcrs}" style="width:200px;"/>
                </div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">轮轴数</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Lzs" type="text" value="${nzCarStorage?.veh_Lzs}" style="width:200px;"/>
                </div></td>
                <td align="left"><div style="text-align: left; " class="om-resizable">轮胎规格</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Ltgg" type="text" value="${nzCarStorage?.veh_Ltgg}" style="width:200px;"/>
                </div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">前轮距</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Qlj" type="text" value="${nzCarStorage?.veh_Qlj}" style="width:200px;"/>
                </div></td>
                <td align="left"><div style="text-align: left; " class="om-resizable">后轮距</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Hlj" type="text" value="${nzCarStorage?.veh_Hlj}" style="width:200px;"/>
                </div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">履带数</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Lds" type="text" value="${nzCarStorage?.veh_Lds}" style="width:200px;"/>
                </div></td>
                <td align="left"><div style="text-align: left; " class="om-resizable">履带规格</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Ldgg" type="text" value="${nzCarStorage?.veh_Ldgg}" style="width:200px;"/>
                </div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">轨距</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Gj" type="text" value="${nzCarStorage?.veh_Gj}" style="width:200px;"/>
                </div></td>
                <td align="left"><div style="text-align: left; " class="om-resizable">外廓长</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Wkc" type="text" value="${nzCarStorage?.veh_Wkc}" style="width:200px;"/>
                </div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">外廓宽</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Wkk" type="text" value="${nzCarStorage?.veh_Wkk}" style="width:200px;"/>
                </div></td>
                <td align="left"><div style="text-align: left; " class="om-resizable">外扩高</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Wkg" type="text" value="${nzCarStorage?.veh_Wkg}" style="width:200px;"/>
                </div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">标定牵引力</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Bdqyl" type="text" value="${nzCarStorage?.veh_Bdqyl}" style="width:200px;"/>
                </div></td>
                <td align="left"><div style="text-align: left; " class="om-resizable">最小使用质量</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Zxsyzl" type="text" value="${nzCarStorage?.veh_Zxsyzl}" style="width:200px;"/>
                </div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">最大允许载质量</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Zdyyzzl" type="text" value="${nzCarStorage?.veh_Zdyyzzl}" style="width:200px;"/>
                </div></td>
                <td align="left"><div style="text-align: left; " class="om-resizable">生产企业名称</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Scqymc" type="text" value="${nzCarStorage?.veh_Scqymc}" style="width:200px;"/>
                </div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">品牌</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Pp" type="text" value="${nzCarStorage?.veh_Pp}" style="width:200px;"/>
                </div></td>
                <td align="left"><div style="text-align: left; " class="om-resizable">生产企业地址</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Scqydz" type="text" value="${nzCarStorage?.veh_Scqydz}" style="width:200px;"/>
                </div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">联系方式</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Lxfs" type="text" value="${nzCarStorage?.veh_Lxfs}" style="width:200px;"/>
                </div></td>
                <td align="left"><div style="text-align: left; " class="om-resizable">公告唯一号</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="car_storage_no" type="text" value="${nzCarStorage?.car_storage_no}" style="width:200px;"/>
                </div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">离合器型式</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Lhqxs" type="text" value="${nzCarStorage?.veh_Lhqxs}" style="width:200px;"/>
                </div></td>
                <td align="left"><div style="text-align: left; " class="om-resizable">前配重型式</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Qpzxs" type="text" value="${nzCarStorage?.veh_Qpzxs}" style="width:200px;"/>
                </div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">后配重型式</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Hpzxs" type="text" value="${nzCarStorage?.veh_Hpzxs}" style="width:200px;"/>
                </div></td>
                <td align="left"><div style="text-align: left; " class="om-resizable">机罩型式</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Jzxs" type="text" value="${nzCarStorage?.veh_Jzxs}" style="width:200px;"/>
                </div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">后配重型式</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Hpzxs" type="text" value="${nzCarStorage?.veh_Hpzxs}" style="width:200px;"/>
                </div></td>
                <td align="left"><div style="text-align: left; " class="om-resizable">板簧型式</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Bhxs" type="text" value="${nzCarStorage?.veh_Bhxs}" style="width:200px;"/>
                </div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">订单轮距</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Ddlj" type="text" value="${nzCarStorage?.veh_Ddlj}" style="width:200px;"/>
                </div></td>
                <td align="left"><div style="text-align: left; width: 95px;" class="om-resizable">是否出口</div></td>
                <td align="left"  abbr="city" class="col1"><div align="left" class="innerCol ">
                    %{--<g:select style="width:510px;" name="car_storage_type" from="${['0':'汽车公告','1':'农用车公告']}"  value="${carStorage?.car_storage_type}" optionKey="key" optionValue="value"></g:select>--}%
                    <g:select style="width:200px;" name="is_Exp" from="${['0':'否','1':'是']}"  value="${nzCarStorage?.is_Exp}" optionKey="key" optionValue="value"></g:select>
                </div></td>
            </tr>
            </tbody>
            </table>
    </div>
    </div>
    <script>
        $("body").parent().parent().attr('height',$("body").parent().parent().height()-10);
    </script>
