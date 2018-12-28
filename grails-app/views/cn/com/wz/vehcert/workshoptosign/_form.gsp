<%@ page import="cn.com.wz.vehcert.workshop.Workshop" %>
<%@ page import="cn.com.wz.vehcert.workshop.Symbol" %>

<div class="fieldcontain ${hasErrors(bean: symbolInstance, field: 'serialNum', 'error')} required" style="margin-top:8px;">
    <label for="serialNum">
        <g:message code="Workshop.serialNum.label" default="serialNum" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="serialNum" required="" value="${serialNum}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: symbolInstance, field: 'kind', 'error')} required" style="margin-top:8px;">
    <label for="kind">
        <g:message code="Workshop.kind.label" default="kind" />

    </label>
    <g:select id="kind" name="kind.id" from="${cn.com.wz.parent.system.dictionary.DictionaryValue.createCriteria().list({
        dictionaryType{eq('code','kind') }
        order("ordernum","asc")
    })}" optionKey="id"  value="${symbolInstance?.kind?.id}"  class="one-to-one" />
</div>

<div class="fieldcontain ${hasErrors(bean: symbolInstance, field: 'symbol', 'error')} required" style="margin-top:8px;">
    <label for="symbol">
        <g:message code="Workshop.symbol.label" default="symbol" />

    </label>
    <g:textField name="symbol" required="" value="${symbolInstance.symbol}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: symbolInstance, field: 'symbol', 'error')} required" style="margin-top:8px;">
    <label for="symbol">
        <g:message code="Workshop.start.label" default="Start Num" />

    </label>
    <g:textField name="startNum" required="" value="${symbolInstance.startNum}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: symbolInstance, field: 'symbol', 'error')} required" style="margin-top:8px;">
    <label for="symbol">
        <g:message code="Workshop.max.label" default="Max Num" />

    </label>
    <g:textField name="maxNum" required="" value="${symbolInstance.maxNum}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: symbolInstance, field: 'note', 'error')} " style="margin-top:8px;">
    <label for="symbolNote">
        <g:message code="Workshop.symbolNote.label" default="SymbolNote" />：
    </label>
    <g:textArea name="symbolNote" value="${symbolInstance.symbolNote}"  style="width:130px;height:85px;"/>
</div>

<script type="text/javascript">
    $(document).ready(function() {
        $('#serialNum').omSuggestion({
            dataSource : '${createLink(controller:'Workshop',action:'search')}?flag=0&',
            queryName:"serialNum",
            onSuccess:function(data, textStatus, event){
                if(data.length==0){
                    $('#serialNum').omSuggestion('showMessage','<div style="margin-left:5px;color:#ff0000;">无提示数据！</div>');
                    return false;
                }

            },
            clientFormatter:function(data,index){
                return '<b>'+data.serialNum+'</b>';
            },
            onSelect:function(rowData,text,index,event){
                $('#serialNum').val(rowData.serialNum); //选择完后自动点击“查询”按钮
            }
        });
    });
</script>