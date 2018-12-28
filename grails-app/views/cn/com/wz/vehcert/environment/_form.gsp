<%@ page import="cn.com.wz.vehcert.environment.Environment" %>

<div class="fieldcontain ${hasErrors(bean: environmentInstance, field: 'veh_Clxh', 'error')} required" style="margin-top:8px;">
    <label for="veh_Clxh">
        <g:message code="environment.veh_Clxh.label" default="veh_Clxh" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="veh_Clxh" required="" value="${environmentInstance.veh_Clxh}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: environmentInstance, field: 'veh_Clmc', 'error')} required" style="margin-top:8px;">
    <label for="veh_Clmc">
        <g:message code="environment.veh_Clmc.label" default="veh_Clmc" />
    </label>
    <g:textField name="veh_Clmc"  value="${environmentInstance.veh_Clmc}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: environmentInstance, field: 'veh_Clfl', 'error')} required" style="margin-top:8px;">
    <label for="veh_Clfl">
        <g:message code="environment.veh_Clfl.label" default="veh_Clfl" />

    </label>
    <g:textField name="veh_Clfl"  value="${environmentInstance.veh_Clfl}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: environmentInstance, field: 'veh_Fdjxh', 'error')} required" style="margin-top:8px;">
    <label for="veh_Fdjxh">
        <g:message code="environment.veh_Fdjxh.label" default="veh_Fdjxh" />

    </label>
    <g:textField name="veh_Fdjxh"  value="${environmentInstance.veh_Fdjxh}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: environmentInstance, field: 'veh_Ggpx', 'error')} required" style="margin-top:8px;">
    <label for="veh_Ggpx">
        <g:message code="environment.veh_Ggpx.label" default="veh_Ggpx" />

    </label>
    <g:textField name="veh_Ggpx"  value="${environmentInstance.veh_Ggpx}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: environmentInstance, field: 'kind', 'error')} required" style="margin-top:8px;">
    <label for="kind">
        <g:message code="environment.kind.label" default="kind" />

    </label>
    <g:select id="kind" name="kind.id" from="${cn.com.wz.parent.system.dictionary.DictionaryValue.createCriteria().list({
        dictionaryType{eq('code','envirKind') }
        order("ordernum","asc")
    })}" optionKey="id"  value="${environmentInstance?.kind?.id}"  class="one-to-one" />
</div>

<div class="fieldcontain ${hasErrors(bean: environmentInstance, field: 'veh_Bz', 'error')} required" style="margin-top:8px;">
    <label for="veh_Bz">
        <g:message code="environment.veh_Bz.label" default="veh_Bz" />

    </label>
    <g:textArea name="veh_Bz"  value="${environmentInstance.veh_Bz}" style="width:138px;"/>
</div>




<script type="text/javascript">
    $(document).ready(function() {
        $('#serialNum').omSuggestion({
            dataSource : '${createLink(controller:'environment',action:'search')}?flag=0&',
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