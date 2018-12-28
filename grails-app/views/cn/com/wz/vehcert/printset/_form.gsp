<div class="fieldcontain ${hasErrors(bean: printSetInstance, field: 'top', 'error')} required" style="margin-top:4px;">
    <label for="top">
        上边距
    </label>
    <g:textField name="top" required="" value="${printSetInstance?.top}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: printSetInstance, field: 'left', 'error')} required" style="margin-top:8px;">
    <label for="left">
        左边距
    </label>
    <g:textField name="left" required="" value="${printSetInstance?.left}"/>
</div>


<div class="fieldcontain ${hasErrors(bean: printSetInstance, field: 'printerName', 'error')} " style="margin-top:8px;">
    <label for="printerName">
打印机名称
    </label>
    <g:textField name="printerName" required="" value="${printSetInstance?.printerName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: printSetInstance, field: 'location', 'error')} " style="margin-top:8px;">
    <label for="location">
        pdf存放位置
    </label>
    <g:textField name="location" required="" value="${printSetInstance?.location}"/>
</div>