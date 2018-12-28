<%@ page import="cn.com.wz.vehcert.oil.Oil" %>

<div class="fieldcontain ${hasErrors(bean: oilInstance, field: 'veh_Clxh', 'error')} required" style="margin-top:8px;">
    <label for="veh_Clxh">
        <g:message code="oil.veh_Clxh.label" default="veh_Clxh" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="veh_Clxh" required="" value="${oilInstance.veh_Clxh}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: oilInstance, field: 'veh_Clmc', 'error')} required" style="margin-top:8px;">
    <label for="veh_Clmc">
        <g:message code="oil.veh_Clmc.label" default="veh_Clmc" />
    </label>
    <g:textField name="veh_Clmc"  value="${oilInstance.veh_Clmc}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: oilInstance, field: 'veh_Yh', 'error')} required" style="margin-top:8px;">
    <label for="veh_Yh">
        <g:message code="oil.veh_Yh.label" default="veh_Yh" />

    </label>
    <g:textField name="veh_Yh"  value="${oilInstance.veh_Yh}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: oilInstance, field: 'veh_Fdjxh', 'error')} required" style="margin-top:8px;">
    <label for="veh_Fdjxh">
        <g:message code="oil.veh_Fdjxh.label" default="veh_Fdjxh" />

    </label>
    <g:textField name="veh_Fdjxh"  value="${oilInstance.veh_Fdjxh}"/>
</div>



<div class="fieldcontain ${hasErrors(bean: oilInstance, field: 'veh_CarH', 'error')} required" style="margin-top:8px;">
    <label for="veh_CarH">
        <g:message code="oil.veh_CarH.label" default="veh_CarH" />

    </label>
    <g:textField name="veh_CarH"  value="${oilInstance.veh_CarH}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: oilInstance, field: 'veh_GoodH', 'error')} required" style="margin-top:8px;">
    <label for="veh_GoodH">
        <g:message code="oil.veh_GoodH.label" default="veh_GoodH" />

    </label>
    <g:textField name="veh_GoodH"  value="${oilInstance.veh_GoodH}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: oilInstance, field: 'kind', 'error')} required" style="margin-top:8px;">
    <label for="kind">
        <g:message code="oil.kind.label" default="kind" />

    </label>
    <g:select id="kind" name="kind.id" from="${cn.com.wz.parent.system.dictionary.DictionaryValue.createCriteria().list({
        dictionaryType{eq('code','oilKind') }
        order("ordernum","asc")
    })}" optionKey="id"  value="${oilInstance?.kind?.id}"  class="one-to-one" />
</div>

<div class="fieldcontain ${hasErrors(bean: oilInstance, field: 'veh_Bz', 'error')} required" style="margin-top:8px;">
    <label for="veh_Bz">
        <g:message code="oil.veh_Bz.label" default="veh_Bz" />

    </label>
    <g:textArea name="veh_Bz"  value="${oilInstance.veh_Bz}" style="width:138px;"/>
</div>




<script type="text/javascript">
    $(document).ready(function() {
        $('#serialNum').omSuggestion({
            dataSource : '${createLink(controller:'oil',action:'search')}?flag=0&',
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