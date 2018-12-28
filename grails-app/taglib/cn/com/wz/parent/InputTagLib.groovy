package cn.com.wz.parent

import grails.converters.JSON

/**
 * 封装的用于输入的控件
 * @author haojia
 *
 */
class InputTagLib {
	static namespace="c"
	def jqueryTagLibService
	/***
	 * 时间选择控件
	 *
	 * update huxx 2012-10-26
	 * 1、 修改了默认值的显示格式
	 * 2、修改了控件的id，如果设置了控件的id就使用设置的id，如果没有设置控件id就自动生成控件的id
     * @Update 2013-09-02 修改了标签，支持日期格式yy-mm-dd H:i:s 和yy年mm月dd日
     * TODO 添加更多的时间格式支持如yy-m-d等格式
	 */

	def dataPicker = {attrs,body->
		def id=''
		if(attrs.id){
			id=attrs.id
		}else{
			id=jqueryTagLibService.getUniqueId()
		}
        attrs = jqueryTagLibService.establishDefaultValues(
	                [
	                        id: id,
	                ],
	                attrs
        	)
		def jsonAttrs = attrs as JSON
		if(attrs.value==null){
            String now=DateUtil.getCurrentTime()
            String time=DateUtil.getCurrentTime("yyyy年MM月dd日")
            def dateFormat=attrs.dateFormat
            switch(dateFormat?.size()){
                case 2 :   //yy
                    attrs.value=now.substring(0,4);
                    break;
                case 5:  //yy-mm
                    attrs.value=now.substring(0,7);
                    break;
                case 6:  //yy年mm月
                    attrs.value=time.substring(0,8);
                    break;
                case 8: //yy-mm-dd
                    attrs.value=now.substring(0,10);
                    break;
                case 9: //yy年mm月dd日
                    attrs.value=time.substring(0,11);
                    break;
                case 10: //yy-mm-dd H
                    attrs.value=now.substring(0,13);
                    break;
                case 12: //yy-mm-dd H:i
                    attrs.value=now.substring(0,16);
                    break ;
                case 14: //yy-mm-dd H:i:s
                    attrs.value=now.substring(0,19);
                    break;
                default:
                    attrs.value=now;
                    break;

            }

		}
        out << """
			<input id="${attrs.id}" style="${attrs.style}" name="${attrs.name}" value="${attrs.value}"/>
		"""
        out << """
		        <script>
					\$(function() {
				        \$('#${attrs.id}').omCalendar(${jsonAttrs});
					});
		        </script>
	        """
		}
	/***
	 * 富文本编辑框
	 */
	def richEditor = {attrs ->
		attrs = jqueryTagLibService.establishDefaultValues(
				[
						id: jqueryTagLibService.getUniqueId(),
						value: '',
						height: '330px',
						width: '522px',
				],
				attrs,
				[]
		)
		def id = attrs.remove('id')
		def value = attrs.remove('value')
		def jsonAttrs = attrs as JSON
		out << """
			<textarea id="cn.com.wz:VehCert:grails-app:1.0-SNAPSHOT">${value}</textarea>
			"""
		out << """
			<script>
				\$(function() {
				        \$('#cn.com.wz:VehCert:grails-app:1.0-SNAPSHOT').omEditor(${jsonAttrs});
					});
			</script>
		"""
	}
	/***
	* 下拉框
	*/
   def comboBox = {attrs ->
	   attrs = jqueryTagLibService.establishDefaultValues(
			   [
					   id: jqueryTagLibService.getUniqueId(),
			   ],
			   attrs,
			   []
	   )
	   def id = attrs.remove('id')
	   def jsonAttrs = attrs as JSON
	   out << """
			<input id="cn.com.wz:VehCert:grails-app:1.0-SNAPSHOT"/>
		"""
	   out << """
		   <script>
			   \$(function() {
					   \$('#cn.com.wz:VehCert:grails-app:1.0-SNAPSHOT').omCombo(${jsonAttrs});
				   });
		   </script>
	   """
   }
   /**
    * 只能输入数字的编辑框
    * @return
    */
   def numberField={attrs->
	   attrs = jqueryTagLibService.establishDefaultValues(
		   [
				   id: jqueryTagLibService.getUniqueId(),
				   value:0
		   ],
		   attrs,
		   []
	   )
	   def id = attrs.remove('id')
	   def value=attrs.remove('value')
	   for(attr in attrs){
		   def key = attr.key
		   def val = attr.value
		   if(key.startsWith("on")){
//			   	attrs.${key}=
		   		println 'jsVal'+val
			}
	   }
	   def jsonAttrs = attrs as JSON
	   out << """
			<input id="cn.com.wz:VehCert:grails-app:1.0-SNAPSHOT" value="${value}">
		"""
	   out << """
		   <script>
			   \$(function() {
					   \$('#cn.com.wz:VehCert:grails-app:1.0-SNAPSHOT').omNumberField(${jsonAttrs});
				   });
		   </script>
	   """
   }
   /**
   * 只能输入数字的编辑框
   * @return
   */
  def button={attrs->
	  attrs = jqueryTagLibService.establishDefaultValues(
		  [
				  id: jqueryTagLibService.getUniqueId(),
				  value:0
		  ],
		  attrs,
		  []
	  )
	  def id = attrs.remove('id')
	  def value=attrs.remove('value')
	  def jsonAttrs = attrs as JSON
	  out << """
		   <input id="cn.com.wz:VehCert:grails-app:1.0-SNAPSHOT" type="button" value="${value}"/>
	   """
	  out << """
		  <script>
			  \$(function() {
					  \$('#cn.com.wz:VehCert:grails-app:1.0-SNAPSHOT').omButton(${jsonAttrs});
				  });
		  </script>
	  """
  }
  /**
   * @Description 下拉内容是树结构的下拉框
   * @params id控件text的id； value控件text的value；class控件text的calss；url树结构的数据源；
   * hidden_id 隐藏域的id；hidden_name 隐藏域的name；hidden_value隐藏域的value
   * checkType onlyLeaf只有在选中叶子节点时回填数据；
   * noSelection未选中时显示的内容
   * @createTime 2013-02-22
   * @creater huxx
   */
  def ajaxTreeSelect = {attrs,body->
	  def id=''
	  if(attrs.id){
		  id=attrs.id
	  }else{
		  id=jqueryTagLibService.getUniqueId()
	  }

	  attrs = jqueryTagLibService.establishDefaultValues(
				  [
						  id: id,
				  ],
				  attrs
		  )
	  def jsonAttrs = attrs as JSON

	  if(!attrs.noSelection){
		  attrs.noSelection="请选择"
	  }
	  if(!attrs.value){
		  attrs.value=attrs.noSelection
	  }
	  out << """
		   <span class="om-combo om-widget om-state-default">
	        	<input type="text" id="${attrs.id}" readonly="${attrs.readonly}" value="${attrs.value}" class='${attrs.class}' style="${attrs.style}">
		        <span id="choose" class="om-combo-trigger"></span>
		        <input type="hidden" id="${attrs.hidden_id}"  name='${attrs.hidden_name}' value="${attrs.hidden_value}">
		    </span>
		     <div id="droplist" style="">
			    <ul id="tree" style="text-align:left;" class='om-tree om-widget'></ul>
			</div>
		"""
	  out << """
		        <script>
					\$(function() {
				        \$("#tree").omTree({
			                dataSource : "${attrs.url}",
			                simpleDataModel:true,
			                 onSelect: function(nodedata){
			                	   var ndata = nodedata, text = ndata.text;
			            		   ndata = \$("#tree").omTree("getParent",ndata);
			                	   while(ndata){
			                		   text = ndata.text +"-" +text;
			                		   ndata = \$("#tree").omTree("getParent",ndata);
			                	   }
			                	   \$("#${attrs.id}").val(text);
			                	   \$("#${attrs.hidden_id}").val(nodedata.id);
			                	   hideDropList();
			                },
			               onBeforeSelect: function(nodedata){
							   if("${attrs.checkType}"=="onlyLeaf"){
							   		//如果选中的是非叶子叶节点不回填
				            	   if(nodedata.children){
				            		   return false;
				            	   }
							    }
			            	   
			               }
			            });

			            //点击下拉按钮显示下拉列表
			            \$("#choose").click(function(){
			            	if(\$("#droplist").css("display")=='block'){
							 	hideDropList();
							}else{
			            		showDropList();
							}
			            });
			            //点击输入框显示下拉列表
			            \$("#${attrs.id}").mousedown(function(){
							\$("#choose").click();return false;
			            });
			            function showDropList(){
	  							var cityInput = \$("#${attrs.id}");
				            	var cityOffset = cityInput.offset();
				            	var topnum = cityOffset.top+cityInput.outerHeight();
				            	if(\$.browser.msie&&(\$.browser.version == "6.0"||\$.browser.version == "7.0")){
				            		topnum = topnum + 2;
				            	}
	  								//测试是否有作用
				            	\$("#droplist").css({left: (cityOffset.left-24) + "px",top: (topnum-60) +"px",display:"block",width:(cityInput.width()+24)+"px"});
				            	//body绑定mousedown事件，当事件对象非下拉框、下拉按钮等下拉列表隐藏。
				            	\$("body").bind("mousedown", onBodyDown);
			            }
			            function hideDropList() {
			    			\$("#droplist").hide();
			    			\$("body").unbind("mousedown", onBodyDown);
			    		}
			    		function onBodyDown(event) {
			    			if (!(event.target.id == "choose" || event.target.id == "droplist" || \$(event.target).parents("#droplist").length>0)) {
			    				hideDropList();
			    			}
			    		}
					});
		        </script>
	  				
	  			<style type="text/css">
				     #droplist{
				        display:none; 
				        position: absolute; 
				       
				        min-height:50px;
	  					max-height:200px;
				        border:1px solid;
				        overflow: auto;
				        background:#EEE;
				        z-index:9999;
				     }
				</style>
	        """
	  }
}
