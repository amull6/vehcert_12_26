<div  class="om-grid om-widget om-widget-content">
<div class="bDiv" style="width: auto;">
<table id="grid" style="" cellpadding="0" cellspacing="0" border="0">
<tbody>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">完整合格证编号</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">${distributorPrintCount?.veh_Zchgzbh_0}</div></td>
    <td align="left"></td>
    <td align="left"></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">经销商已打印次数</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">${distributorPrintCount?.printCount}</div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">限制经销商打印次数</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: distributorPrintCount, field: 'limitPrintCount', 'error')} required" ><g:textField name="limitPrintCount" id="limitPrintCount" maxlength="50"  value="${distributorPrintCount?.limitPrintCount}"/></div></td>
</tr>
</tbody>
</table>
</div>
</div>
<script>
    $("body").parent().parent().attr('height',$("body").parent().parent().height()-10);
</script>
