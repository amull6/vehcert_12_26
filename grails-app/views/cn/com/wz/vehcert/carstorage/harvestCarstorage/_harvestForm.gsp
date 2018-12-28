
    <div  class="om-grid om-widget om-widget-content">
        <div class="bDiv" style="width: auto;">
            <table id="grid" style="" cellpadding="0" cellspacing="0" border="0">
            <tbody>
            <tr>
                <td align="left"><div style="text-align: left; width: 95px;" class="om-resizable">公告类型</div></td>
                <td align="left"  abbr="city" class="col1"><div align="left" class="innerCol ">
                    %{--<g:select style="width:510px;" name="car_storage_type" from="${['0':'汽车公告','1':'农用车公告']}"  value="${carStorage?.car_storage_type}" optionKey="key" optionValue="value"></g:select>--}%
                    <g:select style="width:200px;" name="car_storage_type" from="${['0':'玉米收','1':'稻麦机']}"  value="${harvestCarStorage?.car_storage_type}" optionKey="key" optionValue="value"></g:select>
                </div></td>
                <td align="left"><div style="text-align: left;; width: 95px; " class="om-resizable">机械大类</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Jxdl" type="text" value="${harvestCarStorage?.veh_Jxdl}" style="width:200px;"/>
                </div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; width: 95px;" class="om-resizable">燃料类型</div></td>
                <td align="left"  abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Rllx" type="text" value="${harvestCarStorage?.veh_Rllx}" style="width:200px;" />
                </div></td>
                <td align="left"><div style="text-align: left; " class="om-resizable">机械小类别</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Jxxlb" type="text" value="${harvestCarStorage?.veh_Jxxlb}" style="width:200px;"/>
                </div></td>
            </tr>
            <tr>

                <td align="left"><div style="text-align: left; " class="om-resizable">机械产品的主要参数</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Zycs" type="text" value="${harvestCarStorage?.veh_Zycs}" style="width:200px;"/>
                </div></td>
                <td align="left"><div style="text-align: left; " class="om-resizable">排放阶段</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Pfjd" type="text" value="${harvestCarStorage?.veh_Pfjd}" style="width:200px;"/>
                </div></td>

            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">车辆型号</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Clxh" type="text" value="${harvestCarStorage?.veh_Clxh}" style="width:200px;"/>
                </div></td>
                <td align="left"><div style="text-align: left; " class="om-resizable">公告唯一号</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="car_storage_no" type="text" value="${harvestCarStorage?.car_storage_no}" style="width:200px;"/>
                </div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">发动机型号</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Fdjxh" type="text" value="${harvestCarStorage?.veh_Fdjxh}" style="width:200px;"/>
                </div></td>
            </tr>
            </tbody>
            </table>
    </div>
    </div>
    <script>
        $("body").parent().parent().attr('height',$("body").parent().parent().height()-10);
    </script>
